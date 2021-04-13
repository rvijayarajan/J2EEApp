package org.appfuse.webapp.listener;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.appfuse.Constants;
import org.appfuse.service.LookupManager;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 * StartupListener class used to initialize and database settings
 * and populate any application-wide drop-downs.
 *
 * @author <a href="mailto:matt@raibledesigns.com">Matt Raible</a>
 *
 * @web.listener
 */
public class StartupListener extends ContextLoaderListener implements
		ServletContextListener {

	private static final Log log = LogFactory.getLog(StartupListener.class);

	public void contextInitialized(ServletContextEvent event) {
		if (log.isDebugEnabled()) {
			log.debug("initializing context...");
		}

		// call Spring's context ContextLoaderListener to initialize
		// all the context files specified in web.xml
		super.contextInitialized(event);

		ServletContext context = event.getServletContext();
		String daoType = context.getInitParameter(Constants.DAO_TYPE);

		// if daoType is not specified, use DAO as default
		if (daoType == null) {
			log.warn("No 'daoType' context carameter, using hibernate");
			daoType = Constants.DAO_TYPE_HIBERNATE;
		}

		// Orion starts Servlets before Listeners, so check if the config
		// object already exists
		Map config = (HashMap) context.getAttribute(Constants.CONFIG);

		if (config == null) {
			config = new HashMap();
		}

		// Create a config object to hold all the app config values
		config.put(Constants.DAO_TYPE, daoType);
		context.setAttribute(Constants.CONFIG, config);

		// output the retrieved values for the Init and Context Parameters
		if (log.isDebugEnabled()) {
			log.debug("daoType: " + daoType);
			log.debug("populating drop-downs...");
		}

		setupContext(context);
		removeMarks(context);
	}

	/***
	 * 处理发布容器突然死机的情况下，在下次启动时自动清除登录标记
	 * @param context
	 */
	private static void removeMarks(ServletContext context) {
		ApplicationContext ctx = WebApplicationContextUtils
				.getRequiredWebApplicationContext(context);

/*		DataSource ds = (DataSource) ctx.getBean("dataSource");
		Connection con = null;
		PreparedStatement ps = null;
		try {
			con = ds.getConnection();
			ps = con
					.prepareStatement("update t_sysuser set loginmark ='' where loginmark is not null");
			ps.executeQuery();
		} catch (SQLException e) {

			e.printStackTrace();
		} finally {
			try {
				ps.close();
			} catch (Exception ignore) {
				log.info(ignore);
			}
			try {
				con.close();
			} catch (Exception ignore) {
				log.info(ignore);
			}

		}
*/
	}

	public static void setupContext(ServletContext context) {
		ApplicationContext ctx = WebApplicationContextUtils
				.getRequiredWebApplicationContext(context);

		LookupManager mgr = (LookupManager) ctx.getBean("lookupManager");

		// get list of possible roles
		context.setAttribute(Constants.AVAILABLE_ROLES, mgr.getAllRoles());

		if (log.isDebugEnabled()) {
			log.debug("drop-down initialization complete [OK]");
		}
	}
}
