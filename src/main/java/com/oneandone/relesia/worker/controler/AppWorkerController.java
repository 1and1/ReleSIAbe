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

package com.oneandone.relesia.worker.controler;

import java.util.List;

import com.oneandone.relesia.connector.scm.git.GitConnector;
import com.oneandone.relesia.database.controller.PersistenceControllerFactory;
import com.oneandone.relesia.database.controller.WorkApplicationPersistenceController;
import com.oneandone.relesia.database.model.entity.ApplicationEntity;
import com.oneandone.relesia.database.model.entity.WorkApplicationEntity;
import com.oneandone.relesia.util.properties.AppProperties;

public class AppWorkerController {

	private ApplicationEntity app = null;
	private String appVersion = null;
	private WorkApplicationEntity workApp = null;

	public AppWorkerController(ApplicationEntity app) {
		this.app = app;
	}

	public AppWorkerController(ApplicationEntity app, String appVersion) {
		this.app = app;
		this.appVersion = appVersion;
	}

	public void initWorkerConfig() {
		List<WorkApplicationEntity> workAppsResult = null;

		workAppsResult = WorkApplicationPersistenceController.getInstance().getByAppAndTag(this.app.getId(),
				this.appVersion);

		if (workAppsResult == null || workAppsResult.isEmpty()) {
			workApp = new WorkApplicationEntity();
			workApp.setApplication(app);
			workApp.setTag(appVersion);
			workApp.setName(app.getName());
			workApp.setLocalPath(AppProperties.getProperty("work.dir") + "/" + app.getName());

			PersistenceControllerFactory.getController(WorkApplicationEntity.class).create(workApp);
		} else if (workAppsResult.size() == 1) {
			workApp = workAppsResult.get(0);
		} else {
			System.out.println("Multiple worker apps found for App '" + app.getName()
					+ "'. Please clean Worker Apps and try again.");
		}
	}

	public void initWorkEnvironment() {
		GitConnector git = new GitConnector();

		git.checkout(app.getSourcePath(), workApp.getLocalPath());
	}
}
