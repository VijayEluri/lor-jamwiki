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
package org.jamwiki.persistency.db;

import java.util.Properties;
import org.jamwiki.Environment;
import org.jamwiki.utils.WikiLogger;

/**
 *
 */
public class DB2QueryHandler extends DefaultQueryHandler {

	private static WikiLogger logger = WikiLogger.getLogger(DB2QueryHandler.class.getName());
	private static final String SQL_PROPERTY_FILE_NAME = "sql.db2.properties";
	private static Properties props = null;
	private static Properties defaults = null;

	/**
	 *
	 */
	protected DB2QueryHandler() {
		defaults = Environment.loadProperties(DefaultQueryHandler.SQL_PROPERTY_FILE_NAME);
		props = Environment.loadProperties(SQL_PROPERTY_FILE_NAME, defaults);
		super.init(props);
	}
}