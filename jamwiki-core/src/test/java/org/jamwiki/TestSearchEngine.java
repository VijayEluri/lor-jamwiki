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
package org.jamwiki;

import java.util.ArrayList;
import java.util.List;
import org.jamwiki.model.SearchResultEntry;
import org.jamwiki.model.Topic;
import org.jamwiki.model.VirtualWiki;

/**
 * Empty search engine implementation for use in unit tests.
 */
public class TestSearchEngine implements SearchEngine {

	/**
	 *
	 */
	public void addToIndex(Topic topic, List<String> links) {
	}

	/**
	 *
	 */
	public void deleteFromIndex(Topic topic) {
	}

	/**
	 *
	 */
	public List<SearchResultEntry> findLinkedTo(String virtualWiki, String topicName) {
		return new ArrayList<SearchResultEntry>();
	}

	/**
	 *
	 */
	public List<SearchResultEntry> findResults(String virtualWiki, String text) {
		return new ArrayList<SearchResultEntry>();
	}

	/**
	 *
	 */
	public void refreshIndex() throws Exception {
	}

	/**
	 *
	 */
	public void updateInIndex(Topic topic, List<String> links) {
	}
}