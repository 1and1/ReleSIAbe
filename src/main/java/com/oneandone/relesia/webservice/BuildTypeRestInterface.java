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
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.oneandone.relesia.database.controller.PersistenceControllerFactory;
import com.oneandone.relesia.database.model.entity.BuildTypeEntity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Path("buildTypes")
public class BuildTypeRestInterface {
	
	private Logger logger = LoggerFactory.getLogger("Rest Interface: ");
	
	@PermitAll
	@GET
	@Path("list")
	@Produces(MediaType.APPLICATION_JSON)
	public String listJSONBuildTypes() {
		logger.info("GET: /buildTypes/list");
		ObjectMapper mapper = new ObjectMapper();
		List<BuildTypeEntity> buildTypes;
		String result;
		
		buildTypes = PersistenceControllerFactory.getController(BuildTypeEntity.class).getAll(BuildTypeEntity.class);
		RestInterfaceUtils.verifyResult(buildTypes);
		
		// Object to JSON in String
		try {
			result = mapper.writeValueAsString(buildTypes);
		} catch (JsonProcessingException e) {
			result = "An error occured:\n" + e.getStackTrace();
		}
		return RestInterfaceUtils.verifyResult(result);
	}
	
	@PermitAll
	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public String getBuildTypeById(@PathParam("id") Long buildTypeId) {
		logger.info("GET: /buildTypes/{id}");
		logger.debug("Build Type ID = " + buildTypeId);
		ObjectMapper mapper = new ObjectMapper();
		BuildTypeEntity buildType;
		String result;
		
		buildType = PersistenceControllerFactory.getController(BuildTypeEntity.class)
				.getById(BuildTypeEntity.class, buildTypeId);
		RestInterfaceUtils.verifyResult(buildType);
		
		// Object to JSON in String
		try {
			result = mapper.writeValueAsString(buildType);
		} catch (JsonProcessingException e) {
			result = "An error occured:\n" + e.getStackTrace();
		}
		return RestInterfaceUtils.verifyResult(result);
	}
	
	@PermitAll
	@POST
	//@Path("/")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response createBuildType(BuildTypeEntity buildType) {
		logger.info("POST: /buildTypes/");
		ObjectMapper mapper = new ObjectMapper();
		String result;
		BuildTypeEntity newBuildType;
		Response.ResponseBuilder responseBuilder;
		RestInterfaceUtils.verifyInput(buildType);
		
		newBuildType = PersistenceControllerFactory.getController(BuildTypeEntity.class)
				.create(buildType);
		RestInterfaceUtils.verifyResult(newBuildType);
		
		// Object to JSON in String
		try {
			result = mapper.writeValueAsString(newBuildType);
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
	public String updateBuildType(BuildTypeEntity buildType) {
		logger.info("PUT: /buildTypes/");
		ObjectMapper mapper = new ObjectMapper();
		String result;
		BuildTypeEntity newBuildType;
		RestInterfaceUtils.verifyInput(buildType);
		newBuildType = PersistenceControllerFactory.getController(BuildTypeEntity.class)
				.update(buildType);
		RestInterfaceUtils.verifyResult(newBuildType);
		
		// Object to JSON in String
		try {
			result = mapper.writeValueAsString(newBuildType);
		} catch (JsonProcessingException e) {
			result = "An error occured:\n" + e.getStackTrace();
		}
		return RestInterfaceUtils.verifyResult(result);
	}
	
	@PermitAll
	@DELETE
	@Path("/{id}")
	@Produces(MediaType.TEXT_PLAIN)
	public Response deleteBuildType(@PathParam("id") Long buildTypeId) {
		logger.info("DELETE: /buildTypes/{id}");
		logger.debug("Build Type ID = " + buildTypeId);
		
		PersistenceControllerFactory.getController(BuildTypeEntity.class).delete(BuildTypeEntity.class,
				buildTypeId);
		// Object to JSON in String
		return Response.ok().status(Response.Status.NO_CONTENT).build();
	}
}