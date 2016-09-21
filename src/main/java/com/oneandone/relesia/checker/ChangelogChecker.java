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
import java.io.FileFilter;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.io.filefilter.RegexFileFilter;

import com.oneandone.relesia.database.model.entity.IssueEntity;
import com.oneandone.relesia.database.model.entity.WorkApplicationEntity;

public class ChangelogChecker extends CheckerAbstract{

	FileFilter filter = new RegexFileFilter("[cC][hH][aA][nN][gG][eE][lL][oO][gG]");
	@Override
	public Set<IssueEntity> checkApp(WorkApplicationEntity wApplication) {

		Set<IssueEntity> issues = new HashSet<>();
		File[] files;
		String path = CheckerUtils.workspace + "/" + wApplication.getLocalPath();
		File directory = new File(path);
		if (!directory.isDirectory()){
			try {
				throw new Exception("Not a folder, cannot check, verify path");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		files = directory.listFiles(filter);

		if (files.length>1){
			issues.add(new IssueEntity(wApplication.getApplication(),
					"[CHANGELOG] Multiple changelogs found",
					"Changelog Checker reported multiple"
							+ " changelogs are present\n Number of files: "+files.length,
					"", "", "", ""));
		}else if (files.length<1){
			issues.add(new IssueEntity(wApplication.getApplication(),
					"[CHANGELOG] No readme found",
					"Changelog Checker reported that no readme file is present",
					"", "", "", ""));
		}
		return issues;

	}
}
