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

import java.io.File;
import java.util.HashSet;
import java.util.Set;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;

import com.oneandone.relesia.database.model.entity.IssueEntity;
import com.oneandone.relesia.database.model.entity.WorkApplicationEntity;

public class PomChecker extends CheckerAbstract {

	Logger logger = LoggerFactory.getLogger(PomChecker.class);

	@Override
	public Set<IssueEntity> checkApp(WorkApplicationEntity wApplication) {

		Set<IssueEntity> issues = new HashSet<>();
		String path = CheckerUtils.workspace + "/" + wApplication.getLocalPath() + "/" + "pom.xml";
		File pom = new File(path);
		System.out.println(path);
		System.out.println(pom.getAbsolutePath());
		if (!pom.exists()) {
			issues.add(new IssueEntity(wApplication.getApplication(), "[POM] pom.xml is missing",
					"Pom checker reported that the pom.xml file is missing", "", "", "", ""));
		}
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		factory.setValidating(false);
		factory.setNamespaceAware(true);

		DocumentBuilder builder = null;
		try {
			builder = factory.newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			logger.error("Could not instantiate XML Parser", e);
		}

		try {
			Document document = builder.parse(path);
		} catch (Exception e) {
			issues.add(new IssueEntity(wApplication.getApplication(), "[POM] pom.xml is poorly formatted",
					"Pom checker reported that the pom.xml file is missing", "", "", "", ""));
		}
		return issues;
	}

}
