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
import com.oneandone.relesia.database.model.entity.IssueEntity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Path("issues")
public class IssueRestInterface {

	private Logger logger = LoggerFactory.getLogger("Rest Interface: ");

	@PermitAll
	@GET
	@Path("list")
	@Produces(MediaType.APPLICATION_JSON)
	public String listJSONIssues() {
		logger.info("GET: /issues/list");
		ObjectMapper mapper = new ObjectMapper();
		List<IssueEntity> issues;
		String result;

		issues = PersistenceControllerFactory.getController(IssueEntity.class).getAll(IssueEntity.class);
		RestInterfaceUtils.verifyResult(issues);

		// Object to JSON in String
		try {
			result = mapper.writeValueAsString(issues);
		} catch (JsonProcessingException e) {
			result = "An error occured:\n" + e.getStackTrace();
		}
		return RestInterfaceUtils.verifyResult(result);
	}

	@PermitAll
	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public String getIssueById(@PathParam("id") Long issueId) {
		logger.info("GET: /issues/{id}");
		logger.debug("Issue ID = " + issueId);
		ObjectMapper mapper = new ObjectMapper();
		IssueEntity issue;
		String result;

		issue = PersistenceControllerFactory.getController(IssueEntity.class)
				.getById(IssueEntity.class, issueId);
		RestInterfaceUtils.verifyResult(issue);

		// Object to JSON in String
		try {
			result = mapper.writeValueAsString(issue);
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
	public Response createIssue(IssueEntity issue) {
		logger.info("POST: /issues/");
		ObjectMapper mapper = new ObjectMapper();
		String result;
		IssueEntity newIssue;
		Response.ResponseBuilder responseBuilder;
		RestInterfaceUtils.verifyInput(issue);

		newIssue = PersistenceControllerFactory.getController(IssueEntity.class)
				.create(issue);
		RestInterfaceUtils.verifyResult(newIssue);

		// Object to JSON in String
		try {
			result = mapper.writeValueAsString(newIssue);
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
	public String updateIssue(IssueEntity issue) {
		logger.info("PUT: /issues/");
		ObjectMapper mapper = new ObjectMapper();
		String result;
		IssueEntity newIssue;
		RestInterfaceUtils.verifyInput(issue);

		newIssue = PersistenceControllerFactory.getController(IssueEntity.class)
				.update(issue);
		RestInterfaceUtils.verifyResult(newIssue);

		// Object to JSON in String
		try {
			result = mapper.writeValueAsString(newIssue);
		} catch (JsonProcessingException e) {
			result = "An error occured:\n" + e.getStackTrace();
		}
		return RestInterfaceUtils.verifyResult(result);
	}

	@PermitAll
	@DELETE
	@Path("/{id}")
	@Produces(MediaType.TEXT_PLAIN)
	public Response deleteIssue(@PathParam("id") Long issueId) {
		logger.info("DELETE: /issues/{id}");
		logger.debug("Issue ID = " + issueId);

		PersistenceControllerFactory.getController(IssueEntity.class)
				.delete(IssueEntity.class, issueId);
		// Object to JSON in String
		return Response.ok().status(Response.Status.NO_CONTENT).build();
	}
}