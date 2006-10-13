/**
 * Licensed under the GNU LESSER GENERAL PUBLIC LICENSE, version 2.1, dated February 1999.
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the latest version of the GNU Lesser General
 * Public License as published by the Free Software Foundation;
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program (LICENSE.txt); if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */
package org.jamwiki.parser.jflex;

import java.io.StringReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.jamwiki.Environment;
import org.jamwiki.WikiBase;
import org.jamwiki.parser.AbstractParser;
import org.jamwiki.parser.ParserInput;
import org.jamwiki.parser.ParserDocument;
import org.jamwiki.utils.WikiLogger;
import org.jamwiki.utils.LinkUtil;
import org.jamwiki.utils.Utilities;
import org.jamwiki.utils.WikiLink;
import org.springframework.util.StringUtils;

/**
 * Parser used to implement MediaWiki syntax.
 */
public class JFlexParser extends AbstractParser {

	private static final WikiLogger logger = WikiLogger.getLogger(JFlexParser.class.getName());

	protected static final int MODE_SPLICE = 1;
	protected static final int MODE_SLICE = 2;
	/** Save mode indicates that the topic was edited and is being saved. */
	protected static final int MODE_SAVE = 3;
	protected static final int MODE_TEMPLATE = 4;
	protected static final int MODE_METADATA = 5;
	protected static final int MODE_PREPROCESS = 6;
	protected static final int MODE_PROCESS = 7;
	protected static final int MODE_LAYOUT = 8;

	private static Pattern REDIRECT_PATTERN = null;

	static {
		try {
			// is the topic a redirect?
			REDIRECT_PATTERN = Pattern.compile("#REDIRECT[ ]+\\[\\[([^\\n\\r\\]]+)\\]\\]", Pattern.CASE_INSENSITIVE);
		} catch (Exception e) {
			logger.severe("Unable to compile pattern", e);
		}
	}

	/**
	 * Sets the basics for this parser.
	 *
	 * @param parserInput General information about this parser.
	 */
	public JFlexParser(ParserInput parserInput) {
		super(parserInput);
	}

	/**
	 *
	 */
	public String buildRedirectContent(String topicName) {
		return "#REDIRECT [[" + topicName + "]]";
	}

	/**
	 *
	 */
	private String isRedirect(String content) {
		if (!StringUtils.hasText(content)) return null;
		Matcher m = REDIRECT_PATTERN.matcher(content.trim());
		return (m.matches()) ? Utilities.decodeFromURL(m.group(1).trim()) : null;
	}

	/**
	 * Utility method for executing a lexer parse.
	 */
	private ParserDocument lex(AbstractLexer lexer, String raw) throws Exception {
		this.parserInput.incrementDepth();
		// avoid infinite loops
		if (this.parserInput.getDepth() > 100) {
			String topicName = (StringUtils.hasText(this.parserInput.getTopicName())) ? this.parserInput.getTopicName() : null;
			throw new Exception("Infinite parsing loop - over " + this.parserInput.getDepth() + " parser iterations while parsing topic " + topicName);
		}
		StringBuffer content = new StringBuffer();
		while (true) {
			String line = lexer.yylex();
			if (line == null) break;
			content.append(line);
		}
		ParserDocument parserDocument = lexer.getParserDocument();
		parserDocument.setContent(content.toString());
		this.parserInput.decrementDepth();
		String redirect = this.isRedirect(raw);
		if (StringUtils.hasText(redirect)) {
			parserDocument.setRedirect(redirect);
		}
		return parserDocument;
	}

	/**
	 * This method parses content, performing all transformations except for
	 * layout changes such as adding paragraph tags.  It is suitable to be used
	 * when parsing the contents of a link or performing similar internal
	 * manipulation.
	 *
	 * @param raw The raw Wiki syntax to be converted into HTML.
	 */
	public ParserDocument parseFragment(String raw, int mode) throws Exception {
		// maintain the original output, which has all of the category and link info
		int preMode = (mode > JFlexParser.MODE_PREPROCESS) ? JFlexParser.MODE_PREPROCESS : mode;
		ParserDocument parserDocument = new ParserDocument();
		parserDocument = this.parsePreProcess(raw, parserDocument, preMode);
		if (mode >= JFlexParser.MODE_PROCESS) {
			parserDocument = this.parseProcess(parserDocument.getContent(), parserDocument, JFlexParser.MODE_PROCESS);
		}
		return parserDocument;
	}

	/**
	 * Returns a HTML representation of the given wiki raw text for online representation.
	 *
	 * @param raw The raw Wiki syntax to be converted into HTML.
	 */
	public ParserDocument parseHTML(String raw) throws Exception {
		long start = System.currentTimeMillis();
		// some parser expressions require that lines end in a newline, so add a newline
		// to the end of the content for good measure
		raw += '\n';
		// maintain the original output, which has all of the category and link info
		ParserDocument parserDocument = new ParserDocument();
		parserDocument = this.parsePreProcess(raw, parserDocument, JFlexParser.MODE_PREPROCESS);
		parserDocument = this.parseProcess(parserDocument.getContent(), parserDocument, JFlexParser.MODE_PROCESS);
		parserDocument = this.parsePostProcess(parserDocument.getContent(), parserDocument, JFlexParser.MODE_LAYOUT);
		if (StringUtils.hasText(this.isRedirect(raw))) {
			// redirects are parsed differently
			parserDocument = this.parseRedirect(raw, parserDocument);
		}
		String topicName = (StringUtils.hasText(this.parserInput.getTopicName())) ? this.parserInput.getTopicName() : null;
		logger.info("Parse time (parseHTML) for " + topicName + " (" + ((System.currentTimeMillis() - start) / 1000.000) + " s.)");
		return parserDocument;
	}

	/**
	 * This method provides a way to parse content and set all output metadata,
	 * such as link values used by the search engine.
	 *
	 * @param raw The raw Wiki syntax to be converted into HTML.
	 * @return A ParserDocument object containing results of the parsing process.
	 */
	public ParserDocument parseMetadata(String raw) throws Exception {
		StringReader reader = new StringReader(raw);
		JAMWikiPreProcessor lexer = new JAMWikiPreProcessor(reader);
		ParserDocument parserDocument = new ParserDocument();
		lexer.init(this.parserInput, parserDocument, JFlexParser.MODE_METADATA);
		return this.lex(lexer, raw);
	}

	/**
	 * First stage of the parser, this method parses templates and signatures
	 * and builds metadata.
	 *
	 * @param raw The raw Wiki syntax to be converted into HTML.
	 * @return A ParserDocument object containing results of the parsing process.
	 */
	private ParserDocument parsePreProcess(String raw, ParserDocument parserDocument, int mode) throws Exception {
		StringReader reader = new StringReader(raw);
		JAMWikiPreProcessor lexer = new JAMWikiPreProcessor(reader);
		int preMode = (mode > JFlexParser.MODE_PREPROCESS) ? JFlexParser.MODE_PREPROCESS : mode;
		lexer.init(this.parserInput, parserDocument, preMode);
		return this.lex(lexer, raw);
	}

	/**
	 * Second stage of the parser, this method parses most Wiki syntax, validates
	 * HTML, and performs the majority of the parser conversion.
	 *
	 * @param raw The raw Wiki syntax to be converted into HTML.
	 * @return A ParserDocument object containing results of the parsing process.
	 */
	private ParserDocument parseProcess(String raw, ParserDocument parserDocument, int mode) throws Exception {
		StringReader reader = new StringReader(raw);
		JAMWikiProcessor lexer = new JAMWikiProcessor(reader);
		lexer.init(this.parserInput, parserDocument, JFlexParser.MODE_PROCESS);
		return this.lex(lexer, raw);
	}

	/**
	 * In most cases this method is the second and final stage of the parser,
	 * adding paragraph tags and other layout elements that for various reasons
	 * cannot be added during the first parsing stage.
	 *
	 * @param raw The raw Wiki syntax to be converted into HTML.
	 * @return A ParserDocument object containing results of the parsing process.
	 */
	private ParserDocument parsePostProcess(String raw, ParserDocument parserDocument, int mode) throws Exception {
		StringReader reader = new StringReader(raw);
		JAMWikiPostProcessor lexer = new JAMWikiPostProcessor(reader);
		lexer.init(this.parserInput, parserDocument, JFlexParser.MODE_LAYOUT);
		return this.lex(lexer, raw);
	}

	/**
	 * Parse a topic that is a redirect.  Ordinarily the contents of the redirected
	 * topic would be displayed, but in some cases (such as when explicitly viewing
	 * a redirect) the redirect page contents need to be displayed.
	 *
	 * @param raw The raw Wiki syntax to be converted into HTML.
	 * @return A ParserDocument object containing results of the parsing process.
	 */
	private ParserDocument parseRedirect(String raw, ParserDocument parserDocument) throws Exception {
		String redirect = this.isRedirect(raw);
		String style = "redirect";
		if (!WikiBase.exists(this.parserInput.getVirtualWiki(), redirect.trim(), true)) {
			style = "edit redirect";
		}
		WikiLink wikiLink = new WikiLink();
		wikiLink.setDestination(redirect);
		String content = LinkUtil.buildInternalLinkHtml(this.parserInput.getContext(), this.parserInput.getVirtualWiki(), wikiLink, null, style, false);
		parserDocument.setContent(content);
		return parserDocument;
	}

	/**
	 * Parse MediaWiki signatures and other tags that should not be
	 * saved as part of the topic source.  This method is usually only called
	 * during edits.
	 *
	 * @param raw The raw Wiki syntax to be converted into HTML.
	 * @return A ParserDocument object containing results of the parsing process.
	 */
	public ParserDocument parseSave(String raw) throws Exception {
		StringReader reader = new StringReader(raw);
		JAMWikiPreProcessor lexer = new JAMWikiPreProcessor(reader);
		ParserDocument parserDocument = new ParserDocument();
		lexer.init(this.parserInput, parserDocument, JFlexParser.MODE_SAVE);
		return this.lex(lexer, raw);
	}

	/**
	 * This method provides the capability for retrieving a section of Wiki markup
	 * from an existing document.  It is used primarily when editing a section of
	 * a topic.  This method will return all content from the specified section, up
	 * to the either the next section of the same or greater level or the end of the
	 * document.  For example, if the specified section is an &lt;h3&gt;, all content
	 * up to the next &lt;h1&gt;, &lt;h2&gt;, &lt;h3&gt; or the end of the document
	 * will be returned.
	 *
	 * @param raw The raw Wiki syntax from which a section is to be retrieved.
	 * @param topicName The name of the topic that is being parsed.
	 * @param targetSection The section of the document to be replaced (first section is 1).
	 * @return All markup from the target section, contained within a ParserDocument
	 *  object.
	 */
	public ParserDocument parseSlice(String raw, int targetSection) throws Exception {
		long start = System.currentTimeMillis();
		StringReader reader = new StringReader(raw);
		JAMWikiSpliceProcessor lexer = new JAMWikiSpliceProcessor(reader);
		ParserDocument parserDocument = new ParserDocument();
		lexer.init(this.parserInput, parserDocument, JFlexParser.MODE_SLICE);
		lexer.setTargetSection(targetSection);
		parserDocument = this.lex(lexer, raw);
		String topicName = (StringUtils.hasText(this.parserInput.getTopicName())) ? this.parserInput.getTopicName() : null;
		logger.fine("Parse time (parseSlice) for " + topicName + " (" + ((System.currentTimeMillis() - start) / 1000.000) + " s.)");
		return parserDocument;
	}

	/**
	 * This method provides the capability for splicing a section of new content back
	 * into a document.  It is used primarily when editing a section of a topic.  This
	 * method will replace all content in a specified section, up to the either the next
	 * section of the same or greater level or the end of the document.  For example, if
	 * the specified section is an &lt;h3&gt;, all content up to the next &lt;h1&gt;,
	 * &lt;h2&gt;, &lt;h3&gt; or the end of the document will be replaced with the
	 * specified text.
	 *
	 * @param raw The raw Wiki syntax from which a section is to be replaced.
	 * @param topicName The name of the topic that is being parsed.
	 * @param targetSection The section of the document to be replaced (first section is 1).
	 * @param replacementText The text to replace the specified section text with.
	 * @return The new topic markup, contained within a ParserDocument object.
	 */
	public ParserDocument parseSplice(String raw, int targetSection, String replacementText) throws Exception {
		long start = System.currentTimeMillis();
		StringReader reader = new StringReader(raw);
		JAMWikiSpliceProcessor lexer = new JAMWikiSpliceProcessor(reader);
		ParserDocument parserDocument = new ParserDocument();
		lexer.init(this.parserInput, parserDocument, JFlexParser.MODE_SPLICE);
		lexer.setReplacementText(replacementText);
		lexer.setTargetSection(targetSection);
		parserDocument = this.lex(lexer, raw);
		String topicName = (StringUtils.hasText(this.parserInput.getTopicName())) ? this.parserInput.getTopicName() : null;
		logger.fine("Parse time (parseSplice) for " + topicName + " (" + ((System.currentTimeMillis() - start) / 1000.000) + " s.)");
		return parserDocument;
	}
}
