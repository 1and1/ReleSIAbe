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
import com.oneandone.relesia.database.model.entity.ProgrammingLanguageEntity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Path("prlangs")
public class ProgrammingLanguageRestInterface {

	private Logger logger = LoggerFactory.getLogger("Rest Interface: ");

	@PermitAll
	@GET
	@Path("list")
	@Produces(MediaType.APPLICATION_JSON)
	public String listJSONProgrammingLanguages() {
		logger.info("GET: /prlangs/list");
		ObjectMapper mapper = new ObjectMapper();
		List<ProgrammingLanguageEntity> prlangs;
		String result;

		prlangs = PersistenceControllerFactory.getController(ProgrammingLanguageEntity.class).getAll(ProgrammingLanguageEntity.class);
		RestInterfaceUtils.verifyResult(prlangs);

		// Object to JSON in String
		try {
			result = mapper.writeValueAsString(prlangs);
		} catch (JsonProcessingException e) {
			result = "An error occured:\n" + e.getStackTrace();
		}
		return RestInterfaceUtils.verifyResult(result);
	}

	@PermitAll
	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public String getProgrammingLanguageById(@PathParam("id") Long prlangId) {
		logger.info("GET: /prlangs/{id}");
		logger.debug("Programming Language ID = " + prlangId);
		ObjectMapper mapper = new ObjectMapper();
		ProgrammingLanguageEntity prlang;
		String result;

		prlang = PersistenceControllerFactory.getController(ProgrammingLanguageEntity.class)
				.getById(ProgrammingLanguageEntity.class, prlangId);
		RestInterfaceUtils.verifyResult(prlang);

		// Object to JSON in String
		try {
			result = mapper.writeValueAsString(prlang);
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
	public Response createProgrammingLanguage(ProgrammingLanguageEntity prlang) {
		logger.info("POST: /prlangs/");
		ObjectMapper mapper = new ObjectMapper();
		String result;
		ProgrammingLanguageEntity newPrLang;
		Response.ResponseBuilder responseBuilder;
		RestInterfaceUtils.verifyInput(prlang);

		newPrLang = PersistenceControllerFactory
				.getController(ProgrammingLanguageEntity.class).create(prlang);
		RestInterfaceUtils.verifyResult(newPrLang);

		// Object to JSON in String
		try {
			result = mapper.writeValueAsString(newPrLang);
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
	public String updateProgrammingLanguage(ProgrammingLanguageEntity prlang) {
		logger.info("PUT: /prlangs/");
		ObjectMapper mapper = new ObjectMapper();
		String result;
		ProgrammingLanguageEntity newPrLang;
		RestInterfaceUtils.verifyInput(prlang);

		newPrLang = PersistenceControllerFactory
				.getController(ProgrammingLanguageEntity.class).update(prlang);
		RestInterfaceUtils.verifyResult(newPrLang);

		// Object to JSON in String
		try {
			result = mapper.writeValueAsString(newPrLang);
		} catch (JsonProcessingException e) {
			result = "An error occured:\n" + e.getStackTrace();
		}
		return RestInterfaceUtils.verifyResult(result);
	}
	
	@PermitAll
	@DELETE
	@Path("/{id}")
	@Produces(MediaType.TEXT_PLAIN)
	public Response deleteProgrammingLanguage(@PathParam("id") Long prlangId) {
		logger.info("DELETE: /prlangs/{id}");
		logger.debug("Programming Language ID = " + prlangId);
		PersistenceControllerFactory.getController(ProgrammingLanguageEntity.class)
				.delete(ProgrammingLanguageEntity.class, prlangId);
		// Object to JSON in String
		return Response.ok().status(Response.Status.NO_CONTENT).build();
	}
}