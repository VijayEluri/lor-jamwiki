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
package org.jamwiki.db;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import org.jamwiki.DataAccessException;
import org.jamwiki.WikiBase;
import org.jamwiki.WikiException;
import org.jamwiki.WikiMessage;
import org.jamwiki.utils.WikiLogger;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

/**
 * This class simply contains utility methods for upgrading database schemas
 * (if needed) between JAMWiki versions.  These methods are typically called automatically
 * by the UpgradeServlet when an upgrade is detected and will automatically upgrade the
 * database schema without the need for manual intervention from the user.
 *
 * In general upgrade methods will only be maintained for two major releases and then
 * deleted - for example, JAMWiki version 0.9.0 will not support upgrading from versions
 * prior to 0.7.0.
 */
public class DatabaseUpgrades {

	private static final WikiLogger logger = WikiLogger.getLogger(DatabaseUpgrades.class.getName());

	/**
	 *
	 */
	private DatabaseUpgrades() {
	}

	private static TransactionDefinition getTransactionDefinition() {
		DefaultTransactionDefinition def = new DefaultTransactionDefinition();
		def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
		return def;
	}

	/**
	 * Per http://hsqldb.sourceforge.net/doc/2.0/guide/deployment-chapt.html#deployment_upgrade-sect
	 * ("It is strongly recommended to execute SHTUDOWN COMPACT after an automatic
	 * upgrade from previous versions") after upgrading from HSQL from 1.8 to 2.2
	 * run a "SHUTDOWN COMPACT" to ensure that file data is successfully upgraded.
	 */
	public static void upgradeHsql22(List<WikiMessage> messages) throws WikiException {
		TransactionStatus status = null;
		try {
			status = DatabaseConnection.startTransaction(getTransactionDefinition());
			Connection conn = DatabaseConnection.getConnection();
			logger.info("Compacting HSQL database after upgrade to HSQL 2.2");
			WikiBase.getDataHandler().executeUpgradeUpdate("UPGRADE_110_HSQL_SHUTDOWN_COMPACT", conn);
		} catch (SQLException e) {
			DatabaseConnection.rollbackOnException(status, e);
			logger.error("Database failure during upgrade", e);
			throw new WikiException(new WikiMessage("upgrade.error.fatal", e.getMessage()));
		}
		DatabaseConnection.commit(status);
		try {
			// database has been shut down, so close the current connection pool to
			// refresh all database connections.
			DatabaseConnection.closeConnectionPool();
		} catch (SQLException e) {
			logger.error("Unable to close database connection pool", e);
			throw new WikiException(new WikiMessage("upgrade.error.fatal", e.getMessage()));
		}
	}

	/**
	 *
	 */
	public static void upgrade100(List<WikiMessage> messages) throws WikiException {
		TransactionStatus status = null;
		try {
			status = DatabaseConnection.startTransaction(getTransactionDefinition());
			Connection conn = DatabaseConnection.getConnection();
			// update the jam_virtual_wiki table
			WikiBase.getDataHandler().executeUpgradeUpdate("UPGRADE_100_ADD_VIRTUAL_WIKI_LOGO_URL", conn);
			messages.add(new WikiMessage("upgrade.message.db.column.added", "logo_image_url", "jam_virtual_wiki"));
			WikiBase.getDataHandler().executeUpgradeUpdate("UPGRADE_100_ADD_VIRTUAL_WIKI_SITE_NAME", conn);
			messages.add(new WikiMessage("upgrade.message.db.column.added", "site_name", "jam_virtual_wiki"));
			WikiBase.getDataHandler().executeUpgradeUpdate("UPGRADE_100_ADD_VIRTUAL_WIKI_META_DESCRIPTION", conn);
			messages.add(new WikiMessage("upgrade.message.db.column.added", "meta_description", "jam_virtual_wiki"));
			// add the interwiki table
			WikiBase.getDataHandler().executeUpgradeUpdate("STATEMENT_CREATE_INTERWIKI_TABLE", conn);
			// populate the jam_interwiki table
			WikiDatabase.setupDefaultInterwikis();
			messages.add(new WikiMessage("upgrade.message.db.data.added", "jam_interwiki"));
			// add the jam_configuration table
			WikiBase.getDataHandler().executeUpgradeUpdate("STATEMENT_CREATE_CONFIGURATION_TABLE", conn);
			messages.add(new WikiMessage("upgrade.message.db.table.added", "jam_configuration"));
			// drop the not null constraints for jam_virtual_wiki.default_topic_name
			WikiBase.getDataHandler().executeUpgradeUpdate("UPGRADE_100_DROP_VIRTUAL_WIKI_DEFAULT_TOPIC_NOT_NULL", conn);
			messages.add(new WikiMessage("upgrade.message.db.column.modified", "default_topic_name", "jam_virtual_wiki"));
		} catch (DataAccessException e) {
			DatabaseConnection.rollbackOnException(status, e);
			logger.error("Database failure during upgrade", e);
			throw new WikiException(new WikiMessage("upgrade.error.fatal", e.getMessage()));
		} catch (SQLException e) {
			DatabaseConnection.rollbackOnException(status, e);
			logger.error("Database failure during upgrade", e);
			throw new WikiException(new WikiMessage("upgrade.error.fatal", e.getMessage()));
		}
		DatabaseConnection.commit(status);
		try {
			// perform a separate transaction to update existing data.  this code is in its own
			// transaction since if it fails the upgrade can still be considered successful.
			status = DatabaseConnection.startTransaction(getTransactionDefinition());
			Connection conn = DatabaseConnection.getConnection();
			// add an index to the jam_category table
			WikiBase.getDataHandler().executeUpgradeUpdate("STATEMENT_CREATE_CATEGORY_INDEX", conn);
			messages.add(new WikiMessage("upgrade.message.db.data.updated", "jam_category"));
			// add indexes to the jam_topic table
			WikiBase.getDataHandler().executeUpgradeUpdate("STATEMENT_CREATE_TOPIC_CURRENT_VERSION_INDEX", conn);
			WikiBase.getDataHandler().executeUpgradeUpdate("STATEMENT_CREATE_TOPIC_NAMESPACE_INDEX", conn);
			WikiBase.getDataHandler().executeUpgradeUpdate("STATEMENT_CREATE_TOPIC_VIRTUAL_WIKI_INDEX", conn);
			messages.add(new WikiMessage("upgrade.message.db.data.updated", "jam_topic"));
			// add several indexes to the jam_topic_version table
			WikiBase.getDataHandler().executeUpgradeUpdate("STATEMENT_CREATE_TOPIC_VERSION_PREVIOUS_INDEX", conn);
			WikiBase.getDataHandler().executeUpgradeUpdate("STATEMENT_CREATE_TOPIC_VERSION_USER_DISPLAY_INDEX", conn);
			WikiBase.getDataHandler().executeUpgradeUpdate("STATEMENT_CREATE_TOPIC_VERSION_USER_ID_INDEX", conn);
			messages.add(new WikiMessage("upgrade.message.db.data.updated", "jam_topic_version"));
		} catch (SQLException e) {
			messages.add(new WikiMessage("upgrade.error.nonfatal", e.getMessage()));
			// do not throw this error and halt the upgrade process - populating the field
			// is not required for existing systems.
			logger.warn("Non-fatal error while upgrading.", e);
			try {
				DatabaseConnection.rollbackOnException(status, e);
			} catch (Exception ex) {
				// ignore
			}
			status = null; // so we do not try to commit
		}
		if (status != null) {
			DatabaseConnection.commit(status);
		}
	}

	/**
	 *
	 */
	public static void upgrade110(List<WikiMessage> messages) throws WikiException {
		TransactionStatus status = null;
		try {
			status = DatabaseConnection.startTransaction(getTransactionDefinition());
			Connection conn = DatabaseConnection.getConnection();
			// add the log_sub_type column to the jam_log and jam_recent_change tables
			WikiBase.getDataHandler().executeUpgradeUpdate("UPGRADE_110_ADD_LOG_SUB_TYPE", conn);
			messages.add(new WikiMessage("upgrade.message.db.column.added", "log_sub_type", "jam_log"));
			WikiBase.getDataHandler().executeUpgradeUpdate("UPGRADE_110_ADD_RECENT_CHANGE_LOG_SUB_TYPE", conn);
			messages.add(new WikiMessage("upgrade.message.db.column.added", "log_sub_type", "jam_recent_change"));
			// add the jam_user_block table
			WikiBase.getDataHandler().executeUpgradeUpdate("STATEMENT_CREATE_USER_BLOCK_TABLE", conn);
			messages.add(new WikiMessage("upgrade.message.db.table.added", "jam_user_block"));
			// drop & re-add the jam_topic_links table
			try {
				WikiBase.getDataHandler().executeUpgradeUpdate("STATEMENT_DROP_TOPIC_LINKS_TABLE", conn);
				WikiBase.getDataHandler().executeUpgradeUpdate("UPGRADE_110_DROP_TOPIC_LINKS_INDEX", conn);
				messages.add(new WikiMessage("upgrade.message.db.table.dropped", "jam_topic_links"));
			} catch (SQLException e) {
				// table may not exist if upgrading from prior to JAMWiki 1.0
			}
			WikiBase.getDataHandler().executeUpgradeUpdate("STATEMENT_CREATE_TOPIC_LINKS_TABLE", conn);
			messages.add(new WikiMessage("upgrade.message.db.table.added", "jam_topic_links"));
		} catch (SQLException e) {
			DatabaseConnection.rollbackOnException(status, e);
			logger.error("Database failure during upgrade", e);
			throw new WikiException(new WikiMessage("upgrade.error.fatal", e.getMessage()));
		}
		DatabaseConnection.commit(status);
		try {
			// perform a separate transaction to update existing data.  this code is in its own
			// transaction since if it fails the upgrade can still be considered successful.
			status = DatabaseConnection.startTransaction(getTransactionDefinition());
			Connection conn = DatabaseConnection.getConnection();
			// set the log_sub_type value in the jam_log table
			WikiBase.getDataHandler().executeUpgradeUpdate("UPGRADE_110_UPDATE_LOG_SUB_TYPE_UNDELETE", conn);
			WikiBase.getDataHandler().executeUpgradeUpdate("UPGRADE_110_UPDATE_LOG_SUB_TYPE_DELETE", conn);
			messages.add(new WikiMessage("upgrade.message.db.data.updated", "jam_log"));
			// set the log_sub_type value in the jam_recent_change table
			WikiBase.getDataHandler().executeUpgradeUpdate("UPGRADE_110_UPDATE_RECENT_CHANGE_LOG_SUB_TYPE_UNDELETE", conn);
			WikiBase.getDataHandler().executeUpgradeUpdate("UPGRADE_110_UPDATE_RECENT_CHANGE_LOG_SUB_TYPE_DELETE", conn);
			messages.add(new WikiMessage("upgrade.message.db.data.updated", "jam_recent_change"));
			// add an index to the jam_topic_links table
			WikiBase.getDataHandler().executeUpgradeUpdate("STATEMENT_CREATE_TOPIC_LINKS_INDEX", conn);
			messages.add(new WikiMessage("upgrade.message.db.data.updated", "jam_topic_links"));
		} catch (SQLException e) {
			messages.add(new WikiMessage("upgrade.error.nonfatal", e.getMessage()));
			// do not throw this error and halt the upgrade process - populating the field
			// is not required for existing systems.
			logger.warn("Non-fatal error while upgrading.", e);
			try {
				DatabaseConnection.rollbackOnException(status, e);
			} catch (Exception ex) {
				// ignore
			}
			status = null; // so we do not try to commit
		}
		if (status != null) {
			DatabaseConnection.commit(status);
		}
	}
}
