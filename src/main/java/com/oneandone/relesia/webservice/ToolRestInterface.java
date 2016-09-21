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

import javax.annotation.security.PermitAll;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.oneandone.relesia.database.util.DBTool;

@Path("tools")
public class ToolRestInterface {

	private Logger logger = LoggerFactory.getLogger("Rest Interface: ");
    
	@PermitAll
    @GET
    @Path("init")
    @Produces(MediaType.TEXT_PLAIN)
    public String initSampleData() {
		logger.info("GET: /tools/init");
    	String result;
    	
    	try {
    		DBTool.initData();    		
    		result = "Data initialized!";
    		DBTool.initSampleData();
    		result += "\n Sample Data created!";
		} catch (Exception e) {
			result = "An error occured:\n";
			logger.error("An error occured",e);
		}
		return RestInterfaceUtils.verifyResult(result);
    }
}