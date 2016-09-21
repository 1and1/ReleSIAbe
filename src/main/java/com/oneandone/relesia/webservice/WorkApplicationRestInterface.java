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
import java.util.HashSet;
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

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.oneandone.relesia.checker.Checker;
import com.oneandone.relesia.checker.CheckerFactory;
import com.oneandone.relesia.connector.scm.SCMConnector;
import com.oneandone.relesia.connector.scm.SCMFactory;
import com.oneandone.relesia.database.controller.PersistenceControllerFactory;
import com.oneandone.relesia.database.model.entity.ApplicationEntity;
import com.oneandone.relesia.database.model.entity.CheckerListEntity;
import com.oneandone.relesia.database.model.entity.CheckerPluginEntity;
import com.oneandone.relesia.database.model.entity.IssueEntity;
import com.oneandone.relesia.database.model.entity.PluginEntity;
import com.oneandone.relesia.database.model.entity.SCMEntity;
import com.oneandone.relesia.database.model.entity.WorkApplicationEntity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Path("workapps")
public class WorkApplicationRestInterface {
	
	private Logger logger = LoggerFactory.getLogger("Rest Interface: ");
	
	@PermitAll
	@GET
	@Path("list")
	@Produces(MediaType.APPLICATION_JSON)
	public String listJSONWorkApplication() {
		logger.info("GET: /workapps/list");
		ObjectMapper mapper = new ObjectMapper();
		List<WorkApplicationEntity> workApplications;
		String result;
		
		workApplications = PersistenceControllerFactory.getController(WorkApplicationEntity.class)
				.getAll(WorkApplicationEntity.class);
		RestInterfaceUtils.verifyResult(workApplications);
		
		// Object to JSON in String
		try {
			result = mapper.writeValueAsString(workApplications);
		} catch (JsonProcessingException e) {
			result = "An error occured:\n" + e.getStackTrace();
		}
		return RestInterfaceUtils.verifyResult(result);
	}
	
	@PermitAll
	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public String getWorkApplicationById(@PathParam("id") Long workApplicationId) {
		logger.info("GET: /workapps/{id}");
		logger.debug("Workapplication ID = " + workApplicationId);
		ObjectMapper mapper = new ObjectMapper();
		WorkApplicationEntity workApplication;
		String result;
		
		workApplication = PersistenceControllerFactory
				.getController(WorkApplicationEntity.class)
				.getById(WorkApplicationEntity.class, workApplicationId);
		RestInterfaceUtils.verifyResult(workApplication);
		
		// Object to JSON in String
		try {
			result = mapper.writeValueAsString(workApplication);
		} catch (JsonProcessingException e) {
			result = "An error occured:\n" + e.getStackTrace();
		}
		return RestInterfaceUtils.verifyResult(result);
	}
	
	@PermitAll
	@GET
	@Path("/{id}/init")
	@Produces(MediaType.APPLICATION_JSON)
	public String initWorkApplication(@PathParam("id") Long workApplicationId) {
		logger.info("GET: /workapps/{id}");
		logger.debug("Workapplication ID = " + workApplicationId);
		ObjectMapper mapper = new ObjectMapper();
		SCMFactory scmFactory = new SCMFactory();
		SCMConnector scmConnector;
		WorkApplicationEntity workApplication;
		ApplicationEntity application;
		SCMEntity scmEntity;
		String result;
		
		workApplication = PersistenceControllerFactory
				.getController(WorkApplicationEntity.class)
				.getById(WorkApplicationEntity.class, workApplicationId);
		application = workApplication.getApplication();
		scmEntity = application.getScm();
		scmConnector = scmFactory.getSCM(scmEntity.getType());
		
		RestInterfaceUtils.verifyResult(workApplication);
		RestInterfaceUtils.verifyResult(application);
		
		try {
			scmConnector.init(application.getSourcePath(), workApplication.getLocalPath());
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		scmConnector.cloneProject(workApplication.getLocalPath(), workApplication.getTag());
		// Object to JSON in String
		try {
			result = mapper.writeValueAsString(workApplication);
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
	public Response createWorkApplication(WorkApplicationEntity workApplication) {
		logger.info("POST: /workapps/");
		ObjectMapper mapper = new ObjectMapper();
		String result;
		WorkApplicationEntity newWorkApplication;
		Response.ResponseBuilder responseBuilder;
		RestInterfaceUtils.verifyInput(workApplication);
		
		newWorkApplication = PersistenceControllerFactory
				.getController(WorkApplicationEntity.class).create(workApplication);
		RestInterfaceUtils.verifyResult(workApplication);
		
		// Object to JSON in String
		try {
			result = mapper.writeValueAsString(newWorkApplication);
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
	public String updateWorkApplication(WorkApplicationEntity workApplication) {
		logger.info("PUT: /workapps/");
		logger.debug(workApplication.toString());
		ObjectMapper mapper = new ObjectMapper();
		String result;
		WorkApplicationEntity newWorkApplication;
		RestInterfaceUtils.verifyInput(workApplication);
		
		newWorkApplication = PersistenceControllerFactory
				.getController(WorkApplicationEntity.class).update(workApplication);
		RestInterfaceUtils.verifyResult(workApplication);
		
		// Object to JSON in String
		try {
			result = mapper.writeValueAsString(newWorkApplication);
		} catch (JsonProcessingException e) {
			result = "An error occured:\n" + e.getStackTrace();
		}
		return RestInterfaceUtils.verifyResult(result);
	}
	
	@PermitAll
	@DELETE
	@Path("/{id}")
	@Produces(MediaType.TEXT_PLAIN)
	public Response deleteWorkApplication(@PathParam("id") Long workApplicationId) {
		logger.info("DELETE: /workapps/{id}");
		logger.debug("Workapplication ID = " + workApplicationId);
		PersistenceControllerFactory.getController(WorkApplicationEntity.class)
		.delete(WorkApplicationEntity.class, workApplicationId);
		// Object to JSON in String
		return Response.ok().status(Response.Status.NO_CONTENT).build();
	}
	
	@PermitAll
	@GET
	@Path("/{id}/check/{clid}")
	@Produces(MediaType.APPLICATION_JSON)
	public String checkWorkApplicationWithCheckerList(@PathParam("id") Long workApplicationId, @PathParam("clid") Long checkListId) {
		logger.info("GET: /{id}/check/{clid}");
		logger.debug("Workapplication ID = " + workApplicationId);
		logger.debug("CheckerList ID = " + checkListId);

		ObjectMapper mapper = new ObjectMapper();
		WorkApplicationEntity workApplication;
		String result;
		
		workApplication = PersistenceControllerFactory
				.getController(WorkApplicationEntity.class)
				.getById(WorkApplicationEntity.class, workApplicationId);
		RestInterfaceUtils.verifyResult(workApplication);
		
		CheckerListEntity checkerList;
		checkerList = PersistenceControllerFactory
				.getController(CheckerListEntity.class)
				.getById(CheckerListEntity.class, checkListId);
		
		List<CheckerPluginEntity> checkerListPlugins = checkerList.getCheckers();
		
		PluginEntity plugin = null;
		Set<IssueEntity> issues = new HashSet<IssueEntity>();
		Checker checker = null;
		
		for (CheckerPluginEntity checkerLink : checkerListPlugins) {
			plugin = checkerLink.getPlugin();
			checker = CheckerFactory.getChecker(plugin.getClassName());
			issues.addAll(checker.checkApp(workApplication));
		}
		
		// Object to JSON in String
		try {
			result = mapper.writeValueAsString(issues);
		} catch (JsonProcessingException e) {
			result = "An error occured:\n" + e.getStackTrace();
		}
		return RestInterfaceUtils.verifyResult(result);
	}
	
	
}