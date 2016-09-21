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
import com.oneandone.relesia.database.model.entity.ReleaseRepoEntity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Path("releaseRepos")
public class ReleaseRepoRestInterface {

	private Logger logger = LoggerFactory.getLogger("Rest Interface: ");

	@PermitAll
	@GET
	@Path("list")
	@Produces(MediaType.APPLICATION_JSON)
	public String listJSONReleaseRepos() {
		logger.info("GET: /releaseRepos/list");
		ObjectMapper mapper = new ObjectMapper();
		List<ReleaseRepoEntity> releaseRepos;
		String result;

		releaseRepos = PersistenceControllerFactory.getController(ReleaseRepoEntity.class).getAll(ReleaseRepoEntity.class);
		// Object to JSON in String
		try {
			result = mapper.writeValueAsString(releaseRepos);
		} catch (JsonProcessingException e) {
			result = "An error occured:\n" + e.getStackTrace();
		}
		return RestInterfaceUtils.verifyResult(result);
	}

	@PermitAll
	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public String getReleaseRepoById(@PathParam("id") Long releaseRepoId) {
		logger.info("GET: /releaseRepo/{id}");
		logger.debug("Release repository ID = " + releaseRepoId);
		ObjectMapper mapper = new ObjectMapper();
		ReleaseRepoEntity releaseRepo;
		String result;

		releaseRepo = PersistenceControllerFactory.getController(ReleaseRepoEntity.class)
				.getById(ReleaseRepoEntity.class, releaseRepoId);
		RestInterfaceUtils.verifyResult(releaseRepo);

		// Object to JSON in String
		try {
			result = mapper.writeValueAsString(releaseRepo);
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
	public Response createReleaseRepo(ReleaseRepoEntity releaseRepo) {
		logger.info("POST: /releaseRepos/");
		ObjectMapper mapper = new ObjectMapper();
		String result;
		ReleaseRepoEntity newReleaseRepo;
		Response.ResponseBuilder responseBuilder;
		RestInterfaceUtils.verifyInput(releaseRepo);

		newReleaseRepo = PersistenceControllerFactory.getController(ReleaseRepoEntity.class)
				.create(releaseRepo);
		RestInterfaceUtils.verifyResult(newReleaseRepo);

		// Object to JSON in String
		try {
			result = mapper.writeValueAsString(newReleaseRepo);
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
	public String updateReleaseRepo(ReleaseRepoEntity releaseRepo) {
		logger.info("PUT: /releaseRepos/");
		ObjectMapper mapper = new ObjectMapper();
		String result;
		ReleaseRepoEntity newReleaseRepo;
		RestInterfaceUtils.verifyInput(releaseRepo);

		newReleaseRepo = PersistenceControllerFactory.getController(ReleaseRepoEntity.class)
				.update(releaseRepo);
		RestInterfaceUtils.verifyResult(newReleaseRepo);

		// Object to JSON in String
		try {
			result = mapper.writeValueAsString(newReleaseRepo);
		} catch (JsonProcessingException e) {
			result = "An error occured:\n" + e.getStackTrace();
		}
		return RestInterfaceUtils.verifyResult(result);
	}
	
	@PermitAll
	@DELETE
	@Path("/{id}")
	@Produces(MediaType.TEXT_PLAIN)
	public Response deleteReleaseRepo(@PathParam("id") Long releaseRepoId) {
		logger.info("DELETE: /releaseRepos/{id}");
		logger.debug("Release repository ID = " + releaseRepoId);
		PersistenceControllerFactory.getController(ReleaseRepoEntity.class)
				.delete(ReleaseRepoEntity.class, releaseRepoId);
		// Object to JSON in String
		return Response.ok().status(Response.Status.NO_CONTENT).build();
	}
}