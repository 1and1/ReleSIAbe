/*
 * 
 * Copyright (c) 2016 1&1 Internet SE.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *        http://www.apache.org/licenses/LICENSE-2.0
 *        
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 */

package com.oneandone.relesia.server;

import org.apache.log4j.Level;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.ContextHandler;
import org.eclipse.jetty.server.handler.HandlerCollection;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.servlet.ServletContainer;

import com.oneandone.relesia.util.properties.AppProperties;
import com.oneandone.relesia.webservice.security.AuthenticationFilter;

public class BEServer {
	public static void main(String[] args) throws Exception {

		//BasicConfigurator.configure();
		org.apache.log4j.LogManager.getLogger("org.eclipse.jetty").setLevel(Level.INFO);
		ResourceConfig config = new ResourceConfig();
		config.packages("com.oneandone.relesia.webservice");
		config.register(AuthenticationFilter.class);
		ServletHolder servlet = new ServletHolder(new ServletContainer(config));


		HandlerCollection handlers = new HandlerCollection();
		ResourceHandler resourceHandler = new ResourceHandler();
		resourceHandler.setWelcomeFiles(new String[] { "index.html" });
		String resourceBase = AppProperties.getProperty("resourceBase");
		resourceHandler.setResourceBase(resourceBase);
		ContextHandler contextHandler = new ContextHandler(handlers, "/");
		contextHandler.setHandler(resourceHandler);
		
		ServletContextHandler servletContext = new ServletContextHandler(handlers, "/*");
		servletContext.addServlet(servlet, "/*");

		
		int port = Integer.parseInt(AppProperties.getProperty("port"));
		Server jettyServer = new Server(port);
		jettyServer.setHandler(handlers);

		try {
			jettyServer.start();
			jettyServer.join();
		} finally {
			jettyServer.destroy();
		}
	}
}
