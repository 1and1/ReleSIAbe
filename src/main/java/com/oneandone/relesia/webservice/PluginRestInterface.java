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

package com.oneandone.relesia.webservice;

import java.util.List;

import javax.annotation.security.PermitAll;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.oneandone.relesia.database.controller.PersistenceControllerFactory;
import com.oneandone.relesia.database.model.entity.PluginEntity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Path("plugins")
public class PluginRestInterface {

	private Logger logger = LoggerFactory.getLogger("Rest Interface: ");

	@PermitAll
	@GET
	@Path("list")
	@Produces(MediaType.APPLICATION_JSON)
	public String listJSONPlugins() {
		logger.info("GET: /plugins/list");
		ObjectMapper mapper = new ObjectMapper();
		List<PluginEntity> plugins;
		String result;

		plugins = PersistenceControllerFactory.getController(PluginEntity.class).getAll(PluginEntity.class);
		RestInterfaceUtils.verifyResult(plugins);


		// Object to JSON in String
		try {
			result = mapper.writeValueAsString(plugins);
		} catch (JsonProcessingException e) {
			throw new WebApplicationException("Cannot write plugin as String");
		}
		return RestInterfaceUtils.verifyResult(result);
	}

	@PermitAll
	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public String getPluginById(@PathParam("id") Long pluginId) {
		logger.info("GET: /plugins/{id}");
		logger.debug("Plugin ID = " + pluginId);
		ObjectMapper mapper = new ObjectMapper();
		PluginEntity plugin;
		String result;

		plugin = PersistenceControllerFactory.getController(PluginEntity.class)
				.getById(PluginEntity.class, pluginId);
		RestInterfaceUtils.verifyResult(plugin);

		// Object to JSON in String
		try {
			result = mapper.writeValueAsString(plugin);
		} catch (JsonProcessingException e) {
			throw new WebApplicationException("Cannot write plugin as String");
		}
		return RestInterfaceUtils.verifyResult(result);
	}

	@PermitAll
	@POST
	//@Path("/")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response createPlugin(PluginEntity plugin) {
		logger.info("POST: /plugins/");
		ObjectMapper mapper = new ObjectMapper();
		String result;
		PluginEntity newPlugin;
		Response.ResponseBuilder responseBuilder;
		RestInterfaceUtils.verifyInput(plugin);

		newPlugin = PersistenceControllerFactory.getController(PluginEntity.class)
				.create(plugin);
		RestInterfaceUtils.verifyResult(newPlugin);

		// Object to JSON in String
		try {
			result = mapper.writeValueAsString(newPlugin);
		} catch (JsonProcessingException e) {
			result = "An error occured:\n" + e.getStackTrace();
		}
		responseBuilder = Response.ok().status(Response.Status.CREATED);
		responseBuilder.entity(RestInterfaceUtils.verifyResult(result));
		return responseBuilder.build();
	}
	
	@PermitAll
	@PUT
	//@Path("/")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String updatePlugin(PluginEntity plugin) {
		logger.info("PUT: /plugins/");
		ObjectMapper mapper = new ObjectMapper();
		String result;
		PluginEntity newPlugin;
		RestInterfaceUtils.verifyInput(plugin);

		newPlugin = PersistenceControllerFactory.getController(PluginEntity.class)
				.update(plugin);
		RestInterfaceUtils.verifyResult(newPlugin);

		// Object to JSON in String
		try {
			result = mapper.writeValueAsString(newPlugin);
		} catch (JsonProcessingException e) {
			result = "An error occured:\n" + e.getStackTrace();
		}
		return RestInterfaceUtils.verifyResult(result);
	}
	
	@PermitAll
	@DELETE
	@Path("/{id}")
	@Produces(MediaType.TEXT_PLAIN)
	public Response deletePlugin(@PathParam("id") Long pluginId) {
		logger.info("DELETE: /plugins/{id}");
		logger.debug("Plugin ID = " + pluginId);

		PersistenceControllerFactory.getController(PluginEntity.class)
				.delete(PluginEntity.class, pluginId);
		// Object to JSON in String
		return Response.ok().status(Response.Status.NO_CONTENT).build();
	}
}