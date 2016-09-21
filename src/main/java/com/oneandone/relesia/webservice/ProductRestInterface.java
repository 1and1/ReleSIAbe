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
import com.oneandone.relesia.database.model.entity.ProductEntity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Path("products")
public class ProductRestInterface {

	private Logger logger = LoggerFactory.getLogger("Rest Interface: ");

	@PermitAll
	@GET
	@Path("list")
	@Produces(MediaType.APPLICATION_JSON)
	public String listJSONProducts() {
		logger.info("GET: /products/list");
		ObjectMapper mapper = new ObjectMapper();
		List<ProductEntity> products;
		String result;

		products = PersistenceControllerFactory.getController(ProductEntity.class).getAll(ProductEntity.class);
		RestInterfaceUtils.verifyResult(products);

		// Object to JSON in String
		try {
			result = mapper.writeValueAsString(products);
		} catch (JsonProcessingException e) {
			result = "An error occured:\n" + e.getStackTrace();
		}
		return RestInterfaceUtils.verifyResult(result);
	}

	@PermitAll
	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public String getProductById(@PathParam("id") Long productId) {
		logger.info("GET: /products/{id}");
		logger.debug("Product id = " + productId);
		ObjectMapper mapper = new ObjectMapper();
		ProductEntity product;
		String result;

		product = PersistenceControllerFactory.getController(ProductEntity.class)
				.getById(ProductEntity.class, productId);
		RestInterfaceUtils.verifyResult(product);

		// Object to JSON in String
		try {
			result = mapper.writeValueAsString(product);
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
	public Response createProduct(ProductEntity product) {
		logger.info("POST: /products/");
		Response.ResponseBuilder responseBuilder;
		ObjectMapper mapper = new ObjectMapper();
		String result;
		ProductEntity newProduct;
		RestInterfaceUtils.verifyInput(product);

		newProduct = PersistenceControllerFactory.getController(ProductEntity.class)
				.create(product);
		RestInterfaceUtils.verifyResult(newProduct);


		// Object to JSON in String
		try {
			result = mapper.writeValueAsString(newProduct);
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
	public String updateProduct(ProductEntity product) {
		logger.info("PUT: /products/");
		logger.debug("Product ID = " + product);
		ObjectMapper mapper = new ObjectMapper();
		String result;
		ProductEntity newProduct;
		RestInterfaceUtils.verifyInput(product);

		newProduct = PersistenceControllerFactory.getController(ProductEntity.class)
				.update(product);
		RestInterfaceUtils.verifyResult(newProduct);

		// Object to JSON in String
		try {
			result = mapper.writeValueAsString(newProduct);
		} catch (JsonProcessingException e) {
			result = "An error occured:\n" + e.getStackTrace();
		}
		return RestInterfaceUtils.verifyResult(result);
	}
	
	@PermitAll
	@DELETE
	@Path("/{id}")
	@Produces(MediaType.TEXT_PLAIN)
	public Response deleteProduct(@PathParam("id") Long productId) {
		logger.info("DELETE: /products/{id}");
		logger.debug("Product ID = " + productId);
		PersistenceControllerFactory.getController(ProductEntity.class).delete(ProductEntity.class, productId);
		// Object to JSON in String
		return Response.ok().status(Response.Status.NO_CONTENT).build();
	}
}