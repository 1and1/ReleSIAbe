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

package com.oneandone.relesia.connector.scm.git;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.InvalidRemoteException;
import org.eclipse.jgit.api.errors.TransportException;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;

import com.oneandone.relesia.connector.auth.GitAuth;
import com.oneandone.relesia.connector.scm.SCMConnector;
import com.oneandone.relesia.service.FileServices;
import com.oneandone.relesia.util.properties.AppProperties;

public class GitConnector implements SCMConnector {

	private static final String tagsPath = AppProperties.getProperty("tagsPath", "refs/tags/");
	private static final String branchesPath = AppProperties.getProperty("branchesPath", "refs/heads/");
	private static final String Workspace = AppProperties.getProperty("workspaceLocation", "./Workspace/");
	private static final String WorkspaceTemp = AppProperties.getProperty("workspaceTempLocation", "./Workspace/tmp");

	private String remotePath;
	private String localPath;
	private FileServices fileService;

	private String user;
	private String password;

	@Override
	public void init(String remotePath, String localPath) throws IOException {
		setRemotePath(remotePath);
		setLocalPath((Workspace).trim());
		this.fileService = new FileServices();
	}

	@Override
	public void authenticate() {
		// TODO We need a safe method to authenticate;
		setUser(GitAuth.getInstance().getUser());
		setPassword(GitAuth.getInstance().getPass());
	}

	@Override
	public Set<String> getTags() {

		Set<String> tags = new HashSet<String>();
		authenticate();
		Collection<Ref> refs = null;
		try {
			refs = Git.lsRemoteRepository().setTags(true)
					.setCredentialsProvider(new UsernamePasswordCredentialsProvider(this.user, this.password))
					.setRemote(remotePath).call();
		} catch (InvalidRemoteException e) {
			e.printStackTrace();
		} catch (TransportException e) {
			e.printStackTrace();
		} catch (GitAPIException e) {
			e.printStackTrace();
		}
		// TODO: conditie if
		if (refs != null) {
			for (Ref ref : refs) {
				tags.add(ref.getName().split(tagsPath)[1]);
			}
		}
		return tags;
	}

	@Override
	public Set<String> getBranches() {

		Set<String> branches = new HashSet<String>();
		authenticate();
		Collection<Ref> refs = null;
		try {
			refs = Git.lsRemoteRepository()
					.setCredentialsProvider(new UsernamePasswordCredentialsProvider(this.user, this.password))
					.setRemote(remotePath).call();
		} catch (InvalidRemoteException e) {
			e.printStackTrace();
		} catch (TransportException e) {
			e.printStackTrace();
		} catch (GitAPIException e) {
			e.printStackTrace();
		}

		if (refs != null) {
			for (Ref ref : refs) {
				if (ref.getName().contains(branchesPath)) {
					branches.add(ref.getName().split(branchesPath)[1]);
				}
			}
		}
		return branches;
	}

	@Override
	public void cloneProject(String location, String tag) {
		authenticate();
		String branch = "".equals(tag) ? "HEAD" : (tagsPath + tag).trim();
		File path = new File(localPath + location).getAbsoluteFile();
		fileService.delete(path);
		try (Git result = Git.cloneRepository().setURI(remotePath)
				.setCredentialsProvider(new UsernamePasswordCredentialsProvider(this.user, this.password))
				.setDirectory(path).setBranch(branch).call()) {
		} catch (InvalidRemoteException e) {
			e.printStackTrace();
		} catch (TransportException e) {
			e.printStackTrace();
		} catch (GitAPIException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void checkout(String path, String targetFolder) {
		checkout(path, targetFolder, "");
	}

	@Override
	public void checkout(String path, String targetFolder, String tag) {
		cloneProject(WorkspaceTemp, tag);
		String targetFolderPath = fileService.findDir(new File(WorkspaceTemp), targetFolder);
		fileService.copyFolder(targetFolderPath, Workspace + "/" + targetFolder);
		fileService.delete(new File(WorkspaceTemp));
	}

	@Override
	public void getFile(String path, String targetLocation) {
		getFile(path, targetLocation, "");
	}

	@Override
	public void getFile(String path, String targetLocation, String tag) {
		cloneProject(WorkspaceTemp, tag);
		File f = fileService.findFile(WorkspaceTemp, targetLocation);
		f.renameTo(new File(Workspace + f.getName()));
		fileService.delete(new File(WorkspaceTemp));
	}

	public String getRemotePath() {
		return remotePath;
	}

	public void setRemotePath(String remotePath) {
		this.remotePath = remotePath;
	}

	public String getLocalPath() {
		return localPath;
	}

	public void setLocalPath(String localPath) {
		this.localPath = localPath;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
