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
import javax.annotation.security.RolesAllowed;
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
import com.oneandone.relesia.database.model.entity.UserEntity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Path("users")
public class UserRestInterface {

	private Logger logger = LoggerFactory.getLogger("Rest Interface: ");

	@PermitAll
	@GET
	@Path("list")
	@Produces(MediaType.APPLICATION_JSON)
	public String listJSONUsers() {
		logger.info("GET: /users/list");
		ObjectMapper mapper = new ObjectMapper();
		List<UserEntity> users;
		String result;

		users = PersistenceControllerFactory.getController(UserEntity.class).getAll(UserEntity.class);
		RestInterfaceUtils.verifyResult(users);

		// Object to JSON in String
		try {
			result = mapper.writeValueAsString(users);
		} catch (JsonProcessingException e) {
			result = "An error occured:\n" + e.getStackTrace();
		}
		return RestInterfaceUtils.verifyResult(result);
	}
    
	@PermitAll
	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public String getUserById(@PathParam("id") Long userId) {
		logger.info("GET: /users/{id}");
		logger.debug("User ID = " + userId);
		ObjectMapper mapper = new ObjectMapper();
		UserEntity user;
		String result;

		user = PersistenceControllerFactory.getController(UserEntity.class)
				.getById(UserEntity.class, userId);
		RestInterfaceUtils.verifyResult(user);

		// Object to JSON in String
		try {
			result = mapper.writeValueAsString(user);
		} catch (JsonProcessingException e) {
			result = "An error occured:\n" + e.getStackTrace();
		}
		return RestInterfaceUtils.verifyResult(result);
	}

	@RolesAllowed("ADMIN")
	@POST
	//@Path("/")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response createUser(UserEntity user) {
		logger.info("POST: /users/");
		ObjectMapper mapper = new ObjectMapper();
		String result = null;
		UserEntity newUser = null;
		Response.ResponseBuilder responseBuilder;
		RestInterfaceUtils.verifyInput(user);

		newUser = PersistenceControllerFactory.getController(UserEntity.class)
				.create(user);
		RestInterfaceUtils.verifyResult(newUser);

		// Object to JSON in String
		try {
			result = mapper.writeValueAsString(newUser);
		} catch (JsonProcessingException e) {
			result = "An error occured:\n" + e.getStackTrace();
		}
		responseBuilder = Response.ok().status(Response.Status.CREATED);
		responseBuilder.entity(RestInterfaceUtils.verifyResult(result));
		return responseBuilder.build();
	}
	
	@RolesAllowed("ADMIN")
	@PUT
	//@Path("/")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String updateUser(UserEntity user) {
		logger.info("PUT: /users/");
		ObjectMapper mapper = new ObjectMapper();
		String result = null;
		UserEntity newUser = null;
		RestInterfaceUtils.verifyInput(user);

		newUser = PersistenceControllerFactory.getController(UserEntity.class)
				.update(user);
		RestInterfaceUtils.verifyResult(newUser);

		// Object to JSON in String
		try {
			result = mapper.writeValueAsString(newUser);
		} catch (JsonProcessingException e) {
			result = "An error occured:\n" + e.getStackTrace();
		}
		return RestInterfaceUtils.verifyResult(result);
	}
	
	@RolesAllowed("ADMIN")
	@DELETE
	@Path("/{id}")
	@Produces(MediaType.TEXT_PLAIN)
	public Response deleteUser(@PathParam("id") Long userId) {
		logger.info("DELETE: /users/{id}");
		logger.debug("User ID = " + userId);
		PersistenceControllerFactory.getController(UserEntity.class)
				.delete(UserEntity.class, userId);
		// Object to JSON in String
		return Response.ok().status(Response.Status.NO_CONTENT).build();
	}
}