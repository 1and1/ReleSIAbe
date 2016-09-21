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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

/**
 * Created by octavian on 19.08.2016.
 *
 */
public class RestInterfaceUtils {

	private static Logger logger = LoggerFactory.getLogger("Rest Interface: ");

	public static String verifyInput(String input){
		if (input != null){
			logger.debug(input);
			return input;
		}
		else {
			throw new WebApplicationException("Input is NULL", Response.Status.BAD_REQUEST);
		}
	}

	public static String verifyResult(String result){
		if ( result != null ){
			logger.debug(result);
			return result;
		}
		else {
			throw new WebApplicationException("Resource not found", Response.Status.NOT_FOUND);
		}
	}

	public static Object verifyResult(Object entity){
		if ( entity != null ){
			logger.debug(entity.toString());
			return entity;
		}
		else {
			throw new WebApplicationException("Resource not found", Response.Status.NOT_FOUND);
		}
	}

	public static Object verifyInput(Object entity) {
		if (entity != null){
			logger.debug(entity.toString());
			return entity;
		}
		else {
			throw new WebApplicationException("Entity is NULL", Response.Status.BAD_REQUEST);
		}
	}
}
