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

package com.oneandone.relesia.test.connector.scm.git;

import java.io.File;
import java.io.IOException;

import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.oneandone.relesia.connector.scm.SCMConnector;
import com.oneandone.relesia.connector.scm.SCMFactory;
import com.oneandone.relesia.connector.scm.git.GitConnector;

public class GitConnectorTest {
	
	private static final String remotePath = "https://github.com/1and1/ReleSIAwebclient.git";
	private static final String localPath = "./Workspace/";
	private static final String targetFolder = ".";
	private static final String targetFile = "LICENSE";
	private static final String tag = "0.1.demo";
	private static final String scmType = "git";
	private SCMFactory scmFactory;
	private SCMConnector scmConnector;
	
	@BeforeTest
	public void setUp()  {
		scmFactory = new SCMFactory();
		scmConnector= scmFactory.getSCM(scmType);
		try {
			scmConnector.init(remotePath, localPath);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void checkRemotePath(){
		Assert.assertEquals(((GitConnector) scmConnector).getRemotePath(), remotePath);
	}
	
	@Test
	public void checkLocalPath(){
		Assert.assertTrue(((GitConnector) scmConnector).getLocalPath().equals(localPath));
	}
	
	@Test
	public void checkoutTest(){
		scmConnector.checkout(localPath, targetFolder);
		Assert.assertTrue(new File(localPath + targetFolder).exists());
	}
	
	@Test
	public void checkoutWithTagTest(){
		scmConnector.checkout(localPath, targetFolder, tag);
		Assert.assertTrue(new File(localPath + targetFolder).exists());
	}
	
	@Test
	public void getFileTest(){
		scmConnector.getFile(localPath, targetFile);
		Assert.assertTrue(new File(localPath + targetFile).exists());
	}
	
	@Test
	public void getFileWithTagTest(){
		scmConnector.getFile(localPath, targetFile, tag);
		Assert.assertTrue(new File(localPath + targetFile).exists());
	}
	
}
