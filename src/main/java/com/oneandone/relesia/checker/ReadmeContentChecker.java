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

import java.io.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.oneandone.relesia.database.model.entity.IssueEntity;
import com.oneandone.relesia.database.model.entity.WorkApplicationEntity;

public class ReadmeContentChecker extends CheckerAbstract {

	@Override
	public Set<IssueEntity> checkApp(WorkApplicationEntity application) {
		Set<IssueEntity> issues = new HashSet<>();
		String path = CheckerUtils.workspace + "/" + application.getLocalPath();
		File directory = new File(path);
		if (!directory.isDirectory()) {
			try {
				throw new Exception("Not a folder, cannot check, verify path");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		File[] name = directory.listFiles(ReadmeCheckerUtils.filter);
		if (name.length == 0) {
			name = directory.listFiles(ReadmeCheckerUtils.filterWithExtensions);
		}
		if (name.length > 0) {
			File readme = new File(String.valueOf(name[0]));

			boolean empty = !readme.exists() || readme.length() == 0;
			if (empty) {
				issues.add(new IssueEntity(application.getApplication(), "[README] Readme is empty",
						"Readme checker reported that the readme file is empty", "", "", "", ""));
			} else {
				FileReader in = null;
				try {
					in = new FileReader(readme);
					BufferedReader br = new BufferedReader(in);
					char[] chars = new char[101];
					br.read(chars, 0, 100);
					br.close();
					int j = 0;
					for (int i = 0; i < chars.length; i++) {
						if (0 <= chars[i] && chars[i] <= 255) { // daca
																// caracterele
																// sunt intre 0
																// si 255
																// respecta
							// intervalul ASCII -> e human readeable intr-o
							// oarecare masura
							j++;
						}
					}
					float result = j / 100;
					if (result < 0.75) {
						issues.add(new IssueEntity(application.getApplication(),
								"[README] Readme is not human readable",
								"Readme checker reported that the readme file is not human readable", "", "", "", ""));
					}
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return issues;
	}

	@Override
	public Set<IssueEntity> checkAll() {
		return null;
	}

	@Override
	public Set<IssueEntity> checkList(List<WorkApplicationEntity> workApplicationEntityList) {
		return null;
	}
}
