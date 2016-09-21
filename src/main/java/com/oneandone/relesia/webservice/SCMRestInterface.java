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
import com.oneandone.relesia.database.model.entity.SCMEntity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Path("scms")
public class SCMRestInterface {

	private Logger logger = LoggerFactory.getLogger("Rest Interface: ");

	@PermitAll
	@GET
	@Path("list")
	@Produces(MediaType.APPLICATION_JSON)
	public String listJSONSCMs() {
		logger.info("GET: /scms/list");
		ObjectMapper mapper = new ObjectMapper();
		List<SCMEntity> scms;
		String result;

		scms = PersistenceControllerFactory.getController(SCMEntity.class).getAll(SCMEntity.class);
		RestInterfaceUtils.verifyResult(scms);

		// Object to JSON in String
		try {
			result = mapper.writeValueAsString(scms);
		} catch (JsonProcessingException e) {
			result = "An error occured:\n" + e.getStackTrace();
		}
		return RestInterfaceUtils.verifyResult(result);
	}

	@PermitAll
	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public String getSCMById(@PathParam("id") Long scmId) {
		logger.info("GET: /scms/{id}");
		logger.debug("SCM id = " + scmId);
		ObjectMapper mapper = new ObjectMapper();
		SCMEntity scm;
		String result;

		scm = PersistenceControllerFactory.getController(SCMEntity.class)
				.getById(SCMEntity.class, scmId);
		RestInterfaceUtils.verifyResult(scm);

		// Object to JSON in String
		try {
			result = mapper.writeValueAsString(scm);
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
	public Response createSCM(SCMEntity scm) {
		logger.info("POST: /scms/");
		ObjectMapper mapper = new ObjectMapper();
		String result;
		SCMEntity newSCM;
		Response.ResponseBuilder responseBuilder;
		RestInterfaceUtils.verifyInput(scm);

		newSCM = PersistenceControllerFactory.getController(SCMEntity.class)
				.create(scm);
		RestInterfaceUtils.verifyResult(newSCM);

		// Object to JSON in String
		try {
			result = mapper.writeValueAsString(newSCM);
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
	public String updateSCM(SCMEntity scm) {
		logger.info("PUT: /scms/");
		logger.debug(scm.toString());
		ObjectMapper mapper = new ObjectMapper();
		String result;
		SCMEntity newSCM;
		RestInterfaceUtils.verifyInput(scm);

		newSCM = PersistenceControllerFactory.getController(SCMEntity.class)
				.update(scm);
		RestInterfaceUtils.verifyResult(newSCM);

		// Object to JSON in String
		try {
			result = mapper.writeValueAsString(newSCM);
		} catch (JsonProcessingException e) {
			result = "An error occured:\n" + e.getStackTrace();
		}
		return RestInterfaceUtils.verifyResult(result);
	}
	
	@PermitAll
	@DELETE
	@Path("/{id}")
	@Produces(MediaType.TEXT_PLAIN)
	public Response deleteSCM(@PathParam("id") Long scmId) {
		logger.info("DELETE: /scms/{id}");
		logger.debug("SCM id = " + scmId);
		PersistenceControllerFactory.getController(SCMEntity.class).delete(SCMEntity.class, scmId);
		// Object to JSON in String
		return Response.ok().status(Response.Status.NO_CONTENT).build();
	}
}