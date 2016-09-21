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

import java.io.IOException;
import java.util.List;
import java.util.Set;

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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.oneandone.relesia.connector.scm.SCMConnector;
import com.oneandone.relesia.connector.scm.SCMFactory;
import com.oneandone.relesia.database.controller.PersistenceControllerFactory;
import com.oneandone.relesia.database.model.entity.ApplicationEntity;
import com.oneandone.relesia.database.model.entity.SCMEntity;

@Path("applications")
public class ApplicationRestInterface {
	
	private Logger logger = LoggerFactory.getLogger("Rest Interface: ");
	
	@PermitAll
	@GET
	@Path("list")
	@Produces(MediaType.APPLICATION_JSON)
	public String listJSONApplications() {
		logger.info("GET: /applications/list");
		ObjectMapper mapper = new ObjectMapper();
		List<ApplicationEntity> applications;
		String result;
		
		applications = PersistenceControllerFactory.getController(ApplicationEntity.class)
				.getAll(ApplicationEntity.class);
		RestInterfaceUtils.verifyResult(applications);
		
		// Object to JSON in String
		try {
			result = mapper.writeValueAsString(applications);
		} catch (JsonProcessingException e) {
			result = "An error occured:\n" + e.getStackTrace();
		}
		return RestInterfaceUtils.verifyResult(result);
	}
	
	@PermitAll
	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public String getApplicationById(@PathParam("id") Long applicationId) {
		logger.info("GET: /applications/{id}");
		logger.debug("Application id = " + applicationId);
		ObjectMapper mapper = new ObjectMapper();
		ApplicationEntity application;
		String result;
		application = PersistenceControllerFactory.getController(ApplicationEntity.class)
				.getById(ApplicationEntity.class, applicationId);
		RestInterfaceUtils.verifyResult(application);
		
		// Object to JSON in String
		try {
			result = mapper.writeValueAsString(application);
		} catch (JsonProcessingException e) {
			result = "An error occured:\n" + e.getStackTrace();
		}
		return RestInterfaceUtils.verifyResult(result);
	}
	
	@PermitAll
	@GET
	@Path("/{id}/tags")
	@Produces(MediaType.APPLICATION_JSON)
	public String getTagsbyId(@PathParam("id") Long applicationId) {
		logger.info("GET: /applications/{id}/tags");
		logger.debug("Application id = " + applicationId);
		ObjectMapper mapper = new ObjectMapper();
		ApplicationEntity application;
		SCMEntity scmEntity;
		String result;
		
		application = PersistenceControllerFactory.getController(ApplicationEntity.class)
				.getById(ApplicationEntity.class, applicationId);
		scmEntity = application.getScm();
		
		RestInterfaceUtils.verifyResult(application);
		
		SCMFactory scmFactory = new SCMFactory();
		SCMConnector scmConnector = scmFactory.getSCM(scmEntity.getType());
		
		try {
			scmConnector.init(application.getSourcePath(),application.getName());
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		Set<String> tags = scmConnector.getTags();
		// Object to JSON in String
		try {
			result = mapper.writeValueAsString(tags);
		} catch (JsonProcessingException e) {
			result = "An error occured:\n" + e.getStackTrace();
		}
		return RestInterfaceUtils.verifyResult(result);
	}
	
	@PermitAll
	@GET
	@Path("/{id}/branches")
	@Produces(MediaType.APPLICATION_JSON)
	public String getBranchesbyId(@PathParam("id") Long applicationId) {
		logger.info("GET: /applications/{id}/branches");
		logger.debug("Application id = " + applicationId);
		ObjectMapper mapper = new ObjectMapper();
		ApplicationEntity application;
		SCMEntity scmEntity;
		String result;
		
		application = PersistenceControllerFactory.getController(ApplicationEntity.class)
				.getById(ApplicationEntity.class, applicationId);
		scmEntity = application.getScm();
		
		RestInterfaceUtils.verifyResult(application);
		
		SCMFactory scmFactory = new SCMFactory();
		SCMConnector scmConnector = scmFactory.getSCM(scmEntity.getType());
		
		try {
			scmConnector.init(application.getSourcePath(),application.getName());
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		Set<String> tags = scmConnector.getBranches();
		// Object to JSON in String
		try {
			result = mapper.writeValueAsString(tags);
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
	public Response createApplication(ApplicationEntity application) {
		logger.info("POST: /applications/");
		RestInterfaceUtils.verifyInput(application);
		
		Response.ResponseBuilder responseBuilder;
		ObjectMapper mapper = new ObjectMapper();
		String result;
		ApplicationEntity newApplication;
		newApplication = PersistenceControllerFactory.getController(ApplicationEntity.class)
				.create(application);
		RestInterfaceUtils.verifyResult(newApplication);
		
		// Object to JSON in String
		try {
			result = mapper.writeValueAsString(newApplication);
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
	public String updateApplication(ApplicationEntity application) {
		logger.info("PUT: /applications/");
		RestInterfaceUtils.verifyInput(application);
		ObjectMapper mapper = new ObjectMapper();
		String result;
		ApplicationEntity newApplication;
		
		newApplication = PersistenceControllerFactory
				.getController(ApplicationEntity.class).update(application);
		RestInterfaceUtils.verifyResult(newApplication);
		
		// Object to JSON in String
		try {
			result = mapper.writeValueAsString(newApplication);
		} catch (JsonProcessingException e) {
			result = "An error occured:\n" + e.getStackTrace();
		}
		return RestInterfaceUtils.verifyResult(result);
	}
	
	@PermitAll
	@DELETE
	@Path("/{id}")
	@Produces(MediaType.TEXT_PLAIN)
	public Response deleteApplication(@PathParam("id") Long applicationId) {
		logger.info("DELETE: /applications/{id}");
		logger.debug("Application id = " + applicationId);
		PersistenceControllerFactory.getController(ApplicationEntity.class)
		.delete(ApplicationEntity.class, applicationId);
		// Object to JSON in String
		return Response.ok().status(Response.Status.NO_CONTENT).build();
	}
}