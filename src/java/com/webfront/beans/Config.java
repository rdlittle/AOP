/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.webfront.beans;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * Configure the webapplication context. This is to be placed in the application scope.
 * As far now this example only sets the startup time.
 * @author BalusC
 * @see http://balusc.blogspot.com/2009/09/webapplication-performance-tips-and.html
 */
public class Config implements ServletContextListener {

    // Constants ----------------------------------------------------------------------------------

    private static final String CONFIG_ATTRIBUTE_NAME = "config";

    // Properties ---------------------------------------------------------------------------------

    private long startupTime;

    // Actions ------------------------------------------------------------------------------------

    /**
     * Obtain startup time and put Config itself in the application scope.
     * @see ServletContextListener#contextInitialized(ServletContextEvent)
     */
   public void contextInitialized(ServletContextEvent event) {
        this.startupTime = System.currentTimeMillis() / 1000;
        event.getServletContext().setAttribute(CONFIG_ATTRIBUTE_NAME, this);
    }

    /**
     * @see ServletContextListener#contextDestroyed(ServletContextEvent)
     */
    public void contextDestroyed(ServletContextEvent event) {
        // Nothing to do here.
    }

    // Getters ------------------------------------------------------------------------------------

    /**
     * Returns the startup time associated with this configuration.
     * @return The startup time associated with this configuration.
     */
    public long getStartupTime() {
        return this.startupTime;
    }

}
