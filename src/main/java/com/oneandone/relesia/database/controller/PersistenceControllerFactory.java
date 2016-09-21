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

package com.oneandone.relesia.database.controller;

import java.util.HashMap;
import java.util.Map;

import com.oneandone.relesia.database.model.entity.*;

public class PersistenceControllerFactory {

	private static final PersistenceControllerFactory instance = new PersistenceControllerFactory();

	private Map<Class<?>, PersistenceController> controllersMap = null;

	private PersistenceControllerFactory() {
		controllersMap = new HashMap<>();

		controllersMap.put(ApplicationEntity.class, ApplicationPersistenceController.getInstance());
		controllersMap.put(BuildTypeEntity.class, BuildTypePersistenceController.getInstance());
		controllersMap.put(IssueEntity.class, IssuePersistenceController.getInstance());
		controllersMap.put(PluginEntity.class, PluginPersistenceController.getInstance());
		controllersMap.put(ProductEntity.class, ProductPersistenceController.getInstance());
		controllersMap.put(ProgrammingLanguageEntity.class, ProgrammingLanguagePersistenceController.getInstance());
		controllersMap.put(ReleaseRepoEntity.class, ReleaseRepoPersistenceController.getInstance());
		controllersMap.put(SCMEntity.class, SCMPersistenceController.getInstance());
		controllersMap.put(UserEntity.class, UserPersistenceController.getInstance());
		controllersMap.put(WorkApplicationEntity.class, WorkApplicationPersistenceController.getInstance());
		controllersMap.put(ErrorEntity.class, ErrorPersistenceController.getInstance());
		controllersMap.put(CheckerListEntity.class, CheckerListPersistenceController.getInstance());
	}

	public static PersistenceController getController(Class<?> type) {
		return instance.controllersMap.get(type);
	}

}
