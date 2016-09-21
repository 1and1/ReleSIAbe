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

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.oneandone.relesia.database.controller.PersistenceControllerFactory;
import com.oneandone.relesia.database.model.entity.IssueEntity;
import com.oneandone.relesia.database.model.entity.WorkApplicationEntity;

public abstract class CheckerAbstract implements Checker{

	public abstract Set<IssueEntity> checkApp(WorkApplicationEntity application);

	public Set<IssueEntity> checkAll() {
		Set<IssueEntity> issues = new HashSet<>();
		List<WorkApplicationEntity> wapps = PersistenceControllerFactory
				.getController(WorkApplicationEntity.class).getAll(WorkApplicationEntity.class);
		for (WorkApplicationEntity wapp : wapps){
			issues.addAll(checkApp(wapp));
		}
		return issues;
	}

	public Set<IssueEntity> checkList(List<WorkApplicationEntity> workApplicationEntityList) {
		Set<IssueEntity> issues = new HashSet<>();
		for (WorkApplicationEntity wapp : workApplicationEntityList){
			issues.addAll(checkApp(wapp));
		}
		return issues;
	}
}
