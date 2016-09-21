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

import com.oneandone.relesia.database.model.entity.IssueEntity;
import com.oneandone.relesia.database.model.entity.WorkApplicationEntity;

public class ReadmeChecker extends CheckerAbstract {

	@Override
	public Set<IssueEntity> checkApp(WorkApplicationEntity wApplication) {

		Set<IssueEntity> issues = new HashSet<>();
		String path = CheckerUtils.workspace + "/" + wApplication.getLocalPath();
		System.out.println(path);
		File directory = new File(path);
		System.out.println(directory.getAbsolutePath());
		if (!directory.isDirectory()){
			try {
				throw new Exception("Not a folder, cannot check, verify path");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		int countReadmeFiles = directory.listFiles(ReadmeCheckerUtils.filter).length + 
				directory.listFiles(ReadmeCheckerUtils.filterWithExtensions).length;
						
		if (countReadmeFiles < 1) {
			issues.add(new IssueEntity(wApplication.getApplication(),
					"[README] No readme found",
					"Readme Checker reported that no readme file is present",
					"", "", "", ""));
		} else if (countReadmeFiles > 1) {
			issues.add(new IssueEntity(wApplication.getApplication(),
					"[README] Multiple readme found",
					"Readme Checker reported multiple"
							+ " readme are present\n Number of files: " + countReadmeFiles,
					"", "", "", ""));
		}
		
		return issues;
	}
}
