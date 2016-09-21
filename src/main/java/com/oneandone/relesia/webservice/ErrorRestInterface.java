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

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.oneandone.relesia.database.controller.PersistenceControllerFactory;
import com.oneandone.relesia.database.model.entity.ErrorEntity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.security.PermitAll;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import java.util.List;

/**
 * Created by octavian on 08.09.2016.
 *
 */

@Path("errors")
public class ErrorRestInterface {

	private Logger logger = LoggerFactory.getLogger(ErrorRestInterface.class);

	@PermitAll
	@GET
	@Path("list")
	@Produces(MediaType.APPLICATION_JSON)
	public String listJSONErrors() {
		logger.info("GET: /errors/list");
		ObjectMapper mapper = new ObjectMapper();
		List<ErrorEntity> errors;
		String result;

		errors = PersistenceControllerFactory.getController(ErrorEntity.class).getAll(ErrorEntity.class);
		RestInterfaceUtils.verifyResult(errors);

		// Object to JSON in String
		try {
			result = mapper.writeValueAsString(errors);
		} catch (JsonProcessingException e) {
			result = "An error occured:\n" + e.getStackTrace();
		}
		return RestInterfaceUtils.verifyResult(result);
	}

	@PermitAll
	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public String getErrorById(@PathParam("id") Long errorID) {
		logger.info("GET: /errors/{id}");
		logger.debug("Error ID = " + errorID);
		ObjectMapper mapper = new ObjectMapper();
		ErrorEntity error;
		String result;

		error = PersistenceControllerFactory.getController(ErrorEntity.class)
				.getById(ErrorEntity.class, errorID);
		RestInterfaceUtils.verifyResult(error);

		// Object to JSON in String
		try {
			result = mapper.writeValueAsString(error);
		} catch (JsonProcessingException e) {
			result = "An error occured:\n" + e.getStackTrace();
		}
		return RestInterfaceUtils.verifyResult(result);
	}

	@PermitAll
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response createError(ErrorEntity error) {
		logger.info("POST: /errors/");
		ObjectMapper mapper = new ObjectMapper();
		String result;
		ErrorEntity newError;
		Response.ResponseBuilder responseBuilder;
		RestInterfaceUtils.verifyInput(error);

		newError = PersistenceControllerFactory.getController(ErrorEntity.class)
				.create(error);
		RestInterfaceUtils.verifyResult(newError);

		// Object to JSON in String
		try {
			result = mapper.writeValueAsString(newError);
		} catch (JsonProcessingException e) {
			result = "An error occured:\n" + e.getStackTrace();
		}
		responseBuilder = Response.ok().status(Response.Status.CREATED);
		responseBuilder.entity(RestInterfaceUtils.verifyResult(result));
		return responseBuilder.build();
	}

	@PermitAll
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String updateError(ErrorEntity error) {
		logger.info("PUT: /errors/");
		ObjectMapper mapper = new ObjectMapper();
		String result;
		ErrorEntity newError;
		RestInterfaceUtils.verifyInput(error);

		newError = PersistenceControllerFactory.getController(ErrorEntity.class)
				.update(error);
		RestInterfaceUtils.verifyResult(newError);

		// Object to JSON in String
		try {
			result = mapper.writeValueAsString(newError);
		} catch (JsonProcessingException e) {
			result = "An error occured:\n" + e.getStackTrace();
		}
		return RestInterfaceUtils.verifyResult(result);
	}

	@PermitAll
	@DELETE
	@Path("/{id}")
	@Produces(MediaType.TEXT_PLAIN)
	public Response deleteError(@PathParam("id") Long errorID) {
		logger.info("DELETE: /errors/{id}");
		logger.debug("Error ID = " + errorID);

		PersistenceControllerFactory.getController(ErrorEntity.class)
				.delete(ErrorEntity.class, errorID);
		// Object to JSON in String
		return Response.ok().status(Response.Status.NO_CONTENT).build();
	}
}
