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

package com.oneandone.relesia.checker;

import java.util.HashMap;

public class CheckerFactory {

	private static HashMap<String, Checker> checkers = initMap();
	
	
	private static HashMap<String, Checker> initMap () {
		checkers = new HashMap<String, Checker>();
		
		checkers.put(ChangelogChecker.class.getCanonicalName(), new ChangelogChecker());
		checkers.put(PomChecker.class.getCanonicalName(), new PomChecker());
		checkers.put(ReadmeChecker.class.getCanonicalName(), new ReadmeChecker());
		checkers.put(ReadmeContentChecker.class.getCanonicalName(), new ReadmeContentChecker());
		
		return checkers;
	}
	
	
	public static Checker getChecker( String className) {
		return checkers.get(className);
	}
}
