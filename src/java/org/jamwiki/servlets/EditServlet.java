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
package org.jamwiki.servlets;

import java.util.Collection;
import java.util.List;
import java.util.Vector;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import org.jamwiki.Environment;
import org.jamwiki.PseudoTopicHandler;
import org.jamwiki.WikiBase;
import org.jamwiki.model.Topic;
import org.jamwiki.model.TopicVersion;
import org.jamwiki.model.WikiUser;
import org.jamwiki.parser.ParserInput;
import org.jamwiki.parser.ParserOutput;
import org.jamwiki.utils.DiffUtil;
import org.jamwiki.utils.Utilities;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 */
public class EditServlet extends JAMWikiServlet {

	private static Logger logger = Logger.getLogger(EditServlet.class);

	/**
	 *
	 */
	public ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView next = new ModelAndView("wiki");
		try {
			if (isSave(request)) {
				save(request, next);
			} else {
				edit(request, next);
			}
		} catch (Exception e) {
			viewError(request, next, e);
		}
		loadDefaults(request, next, this.pageInfo);
		return next;
	}

	/**
	 *
	 */
	private void edit(HttpServletRequest request, ModelAndView next) throws Exception {
		String topicName = JAMWikiServlet.getTopicFromRequest(request);
		if (!Utilities.validateName(topicName)) {
			throw new WikiException(new WikiMessage("common.exception.name", topicName));
		}
		String virtualWiki = JAMWikiServlet.getVirtualWikiFromURI(request);
		if (loginRequired(request, next, virtualWiki, topicName)) {
			return;
		}
		loadTopic(request, virtualWiki, topicName);
		int lastTopicVersionId = retrieveLastTopicVersionId(request, virtualWiki, topicName);
		next.addObject("lastTopicVersionId", new Integer(lastTopicVersionId));
		loadEdit(request, next, topicName);
		String contents = null;
		if (isPreview(request)) {
			preview(request, next);
			return;
		}
		this.pageInfo.setPageAction(JAMWikiServlet.ACTION_EDIT);
		if (StringUtils.hasText(request.getParameter("topicVersionId"))) {
			// editing an older version
			int topicVersionId = Integer.parseInt(request.getParameter("topicVersionId"));
			TopicVersion topicVersion = WikiBase.getHandler().lookupTopicVersion(virtualWiki, topicName, topicVersionId);
			if (topicVersion == null) {
				throw new WikiException(new WikiMessage("common.exception.notopic"));
			}
			contents = topicVersion.getVersionContent();
			if (lastTopicVersionId != topicVersionId) {
				next.addObject("topicVersionId", new Integer(topicVersionId));
			}
		} else if (StringUtils.hasText(request.getParameter("section"))) {
			// editing a section of a topic
			int section = (new Integer(request.getParameter("section"))).intValue();
			ParserOutput parserOutput = Utilities.parseSlice(request, virtualWiki, topicName, section);
			contents = parserOutput.getContent();
		} else {
			// editing a full new or existing topic
			Topic topic = WikiBase.getHandler().lookupTopic(virtualWiki, topicName);
			contents = (topic == null) ? "" : topic.getTopicContent();
		}
		next.addObject("contents", contents);
	}

	/**
	 *
	 */
	private boolean isPreview(HttpServletRequest request) {
		return StringUtils.hasText(request.getParameter("preview"));
	}

	/**
	 *
	 */
	private boolean isSave(HttpServletRequest request) {
		return StringUtils.hasText(request.getParameter("save"));
	}

	/**
	 *
	 */
	private void loadEdit(HttpServletRequest request, ModelAndView next, String topicName) throws Exception {
		this.pageInfo.setPageTitle(new WikiMessage("edit.title", topicName));
		this.pageInfo.setTopicName(topicName);
		if (topicName.startsWith(WikiBase.NAMESPACE_CATEGORY)) {
			next.addObject("categoryName", topicName);
			Collection subtopics = WikiBase.getHandler().lookupCategoryTopics(topicName, Topic.TYPE_ARTICLE);
			next.addObject("subtopics", subtopics);
			Collection subcategories = WikiBase.getHandler().lookupCategoryTopics(topicName, Topic.TYPE_CATEGORY);
			next.addObject("subcategories", subcategories);
		}
		if (request.getParameter("editComment") != null) {
			next.addObject("editComment", request.getParameter("editComment"));
		}
		if (request.getParameter("section") != null) {
			next.addObject("section", request.getParameter("section"));
		}
		next.addObject("minorEdit", new Boolean(request.getParameter("minorEdit") != null));
	}

	/**
	 *
	 */
	private Topic loadTopic(HttpServletRequest request, String virtualWiki, String topicName) throws Exception {
		if (!StringUtils.hasText(topicName)) {
			throw new WikiException(new WikiMessage("common.exception.notopic"));
		}
		if (PseudoTopicHandler.isPseudoTopic(topicName)) {
			throw new WikiException(new WikiMessage("edit.exception.pseudotopic", topicName));
		}
		Topic topic = WikiBase.getHandler().lookupTopic(virtualWiki, topicName);
		if (topic == null) {
			topic = new Topic();
			topic.setName(topicName);
			topic.setVirtualWiki(virtualWiki);
		}
		if (topicName.startsWith(WikiBase.NAMESPACE_CATEGORY)) {
			topic.setTopicType(Topic.TYPE_CATEGORY);
		}
		if (topic.getReadOnly()) {
			throw new WikiException(new WikiMessage("error.readonly"));
		}
		return topic;
	}

	/**
	 *
	 */
	private boolean loginRequired(HttpServletRequest request, ModelAndView next, String virtualWiki, String topicName) throws Exception {
		if (Environment.getBooleanValue(Environment.PROP_TOPIC_FORCE_USERNAME) && Utilities.currentUser(request) == null) {
			next.addObject("errorMessage", new WikiMessage("edit.exception.login"));
			viewLogin(request, next, JAMWikiServlet.getTopicFromURI(request));
			return true;
		}
		Topic topic = WikiBase.getHandler().lookupTopic(virtualWiki, topicName);
		if (topic != null && topic.getAdminOnly() && !Utilities.isAdmin(request)) {
			next.addObject("errorMessage", new WikiMessage("edit.exception.loginadmin", topicName));
			viewLogin(request, next, JAMWikiServlet.getTopicFromURI(request));
			return true;
		}
		return false;
	}

	/**
	 *
	 */
	private void preview(HttpServletRequest request, ModelAndView next) throws Exception {
		String topicName = JAMWikiServlet.getTopicFromRequest(request);
		String virtualWiki = JAMWikiServlet.getVirtualWikiFromURI(request);
		WikiUser user = Utilities.currentUser(request);
		JAMWikiServlet.removeCachedContents();
		String contents = (String)request.getParameter("contents");
		ParserInput parserInput = new ParserInput();
		parserInput.setContext(request.getContextPath());
		parserInput.setLocale(request.getLocale());
		parserInput.setWikiUser(user);
		parserInput.setTopicName(topicName);
		parserInput.setUserIpAddress(request.getRemoteAddr());
		parserInput.setVirtualWiki(virtualWiki);
		parserInput.setMode(ParserInput.MODE_PREVIEW);
		parserInput.setAllowSectionEdit(false);
		ParserOutput parserOutput = Utilities.parse(parserInput, contents, topicName);
		if (parserOutput.getCategories().size() > 0) {
			next.addObject("categories", parserOutput.getCategories().keySet());
		}
		Topic previewTopic = new Topic();
		previewTopic.setName(topicName);
		previewTopic.setTopicContent(parserOutput.getContent());
		next.addObject(JAMWikiServlet.PARAMETER_TOPIC_OBJECT, previewTopic);
		this.pageInfo.setPageAction(JAMWikiServlet.ACTION_PREVIEW);
		next.addObject("contents", contents);
	}

	/**
	 *
	 */
	private void resolve(HttpServletRequest request, ModelAndView next) throws Exception {
		String topicName = JAMWikiServlet.getTopicFromRequest(request);
		String virtualWiki = JAMWikiServlet.getVirtualWikiFromURI(request);
		TopicVersion version = WikiBase.getHandler().lookupLastTopicVersion(virtualWiki, topicName);
		String contents1 = version.getVersionContent();
		String contents2 = request.getParameter("contents");
		next.addObject("lastTopicVersionId", new Integer(version.getTopicVersionId()));
		next.addObject("contents", contents1);
		next.addObject("contentsResolve", contents2);
		Vector diffs = DiffUtil.diff(contents1, contents2);
		next.addObject("diffs", diffs);
		loadEdit(request, next, topicName);
		this.pageInfo.setPageAction(JAMWikiServlet.ACTION_EDIT_RESOLVE);
	}

	/**
	 *
	 */
	private int retrieveLastTopicVersionId(HttpServletRequest request, String virtualWiki, String topicName) throws Exception {
		int lastTopicVersionId = 0;
		if (request.getParameter("lastTopicVersionId") == null) {
			TopicVersion version = WikiBase.getHandler().lookupLastTopicVersion(virtualWiki, topicName);
			if (version != null) lastTopicVersionId = version.getTopicVersionId();
		} else {
			lastTopicVersionId = new Integer(request.getParameter("lastTopicVersionId")).intValue();
		}
		return lastTopicVersionId;
	}

	/**
	 *
	 */
	private void save(HttpServletRequest request, ModelAndView next) throws Exception {
		String topicName = request.getParameter(JAMWikiServlet.PARAMETER_TOPIC);
		String virtualWiki = JAMWikiServlet.getVirtualWikiFromURI(request);
		if (loginRequired(request, next, virtualWiki, topicName)) {
			return;
		}
		Topic topic = loadTopic(request, virtualWiki, topicName);
		TopicVersion lastTopicVersion = WikiBase.getHandler().lookupLastTopicVersion(virtualWiki, topicName);
		if (lastTopicVersion != null && lastTopicVersion.getTopicVersionId() != retrieveLastTopicVersionId(request, virtualWiki, topicName)) {
			// someone else has edited the topic more recently
			resolve(request, next);
			return;
		}
		TopicVersion topicVersion = new TopicVersion();
		String contents = request.getParameter("contents");
		if (StringUtils.hasText(request.getParameter("section"))) {
			// load section of topic
			int section = (new Integer(request.getParameter("section"))).intValue();
			ParserOutput parserOutput = Utilities.parseSplice(request, virtualWiki, topicName, section, contents);
			contents = parserOutput.getContent();
		}
		if (contents == null) {
			logger.warn("The topic " + topicName + " has no content");
			throw new WikiException(new WikiMessage("edit.exception.nocontent", topicName));
		}
		if (lastTopicVersion != null && lastTopicVersion.getVersionContent().equals(contents)) {
			viewTopic(request, next, topicName);
			return;
		}
		// parse for signatures and other syntax that should not be saved in raw form
		WikiUser user = Utilities.currentUser(request);
		ParserInput parserInput = new ParserInput();
		parserInput.setContext(request.getContextPath());
		parserInput.setLocale(request.getLocale());
		parserInput.setWikiUser(user);
		parserInput.setTopicName(topicName);
		parserInput.setUserIpAddress(request.getRemoteAddr());
		parserInput.setVirtualWiki(virtualWiki);
		parserInput.setMode(ParserInput.MODE_SAVE);
		ParserOutput parserOutput = Utilities.parsePreSave(parserInput, contents);
		contents = parserOutput.getContent();
		topic.setTopicContent(contents);
		topicVersion.setVersionContent(contents);
		topicVersion.setEditComment(request.getParameter("editComment"));
		topicVersion.setAuthorIpAddress(request.getRemoteAddr());
		if (request.getParameter("minorEdit") != null) {
			topicVersion.setEditType(TopicVersion.EDIT_MINOR);
		}
		if (user != null) {
			topicVersion.setAuthorId(new Integer(user.getUserId()));
		}
		WikiBase.getHandler().writeTopic(topic, topicVersion, parserOutput);
		// a save request has been made
		JAMWikiServlet.removeCachedContents();
		viewTopic(request, next, topicName);
	}
}
