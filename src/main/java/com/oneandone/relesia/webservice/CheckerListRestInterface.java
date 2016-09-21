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
import com.oneandone.relesia.database.model.entity.CheckerListEntity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.security.PermitAll;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * Created by octavian on 15.09.2016.
 *
 */

@Path("checkerlists")
public class CheckerListRestInterface {

	private Logger logger = LoggerFactory.getLogger(CheckerListRestInterface.class);

	@PermitAll
	@GET
	@Path("list")
	@Produces(MediaType.APPLICATION_JSON)
	public String listJSONCheckerLists() {
		logger.info("GET: /checkerlists/list");
		ObjectMapper mapper = new ObjectMapper();
		List<CheckerListEntity> checkerLists;
		String result;

		checkerLists = PersistenceControllerFactory.getController(CheckerListEntity.class).getAll(CheckerListEntity.class);
		RestInterfaceUtils.verifyResult(checkerLists);

		// Object to JSON in String
		try {
			result = mapper.writeValueAsString(checkerLists);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			result = "An error occured:\n" + e.getStackTrace();
		}
		return RestInterfaceUtils.verifyResult(result);
	}

	@PermitAll
	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public String getCheckerListById(@PathParam("id") Long checkerListID) {
		logger.info("GET: /checkerlists/{id}");
		logger.debug("CheckerList ID = " + checkerListID);
		ObjectMapper mapper = new ObjectMapper();
		CheckerListEntity checkerList;
		String result;

		checkerList = PersistenceControllerFactory.getController(CheckerListEntity.class)
				.getById(CheckerListEntity.class, checkerListID);
		RestInterfaceUtils.verifyResult(checkerList);

		// Object to JSON in String
		try {
			result = mapper.writeValueAsString(checkerList);
		} catch (JsonProcessingException e) {
			result = "An error occured:\n" + e.getStackTrace();
		}
		return RestInterfaceUtils.verifyResult(result);
	}

	@PermitAll
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response createCheckerList(CheckerListEntity checkerList) {
		logger.info("POST: /checkerlists/");
		ObjectMapper mapper = new ObjectMapper();
		String result;
		CheckerListEntity newCheckerList;
		Response.ResponseBuilder responseBuilder;
		RestInterfaceUtils.verifyInput(checkerList);

		newCheckerList = PersistenceControllerFactory.getController(CheckerListEntity.class)
				.create(checkerList);
		RestInterfaceUtils.verifyResult(newCheckerList);

		// Object to JSON in String
		try {
			result = mapper.writeValueAsString(newCheckerList);
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
	public String updateCheckerList(CheckerListEntity checkerList) {
		logger.info("PUT: /checkerlists/");
		ObjectMapper mapper = new ObjectMapper();
		String result;
		CheckerListEntity newCheckerList;
		RestInterfaceUtils.verifyInput(checkerList);

		newCheckerList = PersistenceControllerFactory.getController(CheckerListEntity.class)
				.update(checkerList);
		RestInterfaceUtils.verifyResult(newCheckerList);

		// Object to JSON in String
		try {
			result = mapper.writeValueAsString(newCheckerList);
		} catch (JsonProcessingException e) {
			result = "An error occured:\n" + e.getStackTrace();
		}
		return RestInterfaceUtils.verifyResult(result);
	}

	@PermitAll
	@DELETE
	@Path("/{id}")
	@Produces(MediaType.TEXT_PLAIN)
	public Response deleteCheckerList(@PathParam("id") Long checkerListID) {
		logger.info("DELETE: /checkerlists/{id}");
		logger.debug("CheckerList ID = " + checkerListID);

		PersistenceControllerFactory.getController(CheckerListEntity.class)
				.delete(CheckerListEntity.class, checkerListID);
		// Object to JSON in String
		return Response.ok().status(Response.Status.NO_CONTENT).build();
	}

}
