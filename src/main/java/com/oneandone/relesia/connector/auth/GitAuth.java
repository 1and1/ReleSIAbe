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

package com.oneandone.relesia.connector.auth;

import com.oneandone.relesia.util.properties.AppProperties;

public class GitAuth{
	
	private static GitAuth instance = null;
	private String user;
	private String pass;
	
	private GitAuth(){
		//TODO: CHANGE Authentication method 
		setUser(AppProperties.getProperty("user"));
		setPass(AppProperties.getProperty("password"));
	}
	
	public static GitAuth getInstance() {
		if(instance == null){
			instance = new GitAuth();
		}
		return instance;
	}
	
	public String getUser() {
		return user;
	}
	
	public void setUser(String user) {
		this.user = user;
	}
	
	public String getPass() {
		return pass;
	}
	
	public void setPass(String pass) {
		this.pass = pass;
	}
	
}
