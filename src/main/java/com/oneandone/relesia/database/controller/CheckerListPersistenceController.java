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

import java.util.ArrayList;

import com.oneandone.relesia.database.model.entity.CheckerListEntity;
import com.oneandone.relesia.database.model.entity.CheckerPluginEntity;
import com.oneandone.relesia.database.model.entity.PluginEntity;

public class CheckerListPersistenceController extends AbstractPersistenceController {

	private static final CheckerListPersistenceController instance = new CheckerListPersistenceController();

	public static CheckerListPersistenceController getInstance() {
		return instance;
	}

	@Override
	public <T> T create(T newEntity) {
		verify((CheckerListEntity) newEntity);

		return super.create(newEntity);
	}

	@Override
	public <T> T update(T entity) {
		verify((CheckerListEntity) entity);

		return super.update(entity);
	}

	private void verify(CheckerListEntity entity) {
		ArrayList<CheckerPluginEntity> checkers = new ArrayList<CheckerPluginEntity>((entity).getCheckers());
		ArrayList<PluginEntity> verify = new ArrayList<>();
		for ( CheckerPluginEntity current : checkers ) {
			if (current.getCheckerList() != null) {
				for (PluginEntity currentDependency : current.getPlugin().getDependencies()) {
					if (!verify.contains(currentDependency)) {
						throw new RuntimeException("List not valid");
					}
				}
			}
		}
	}
}
