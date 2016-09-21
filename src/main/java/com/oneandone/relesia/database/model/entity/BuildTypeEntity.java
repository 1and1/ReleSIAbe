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

package com.oneandone.relesia.database.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "BUILD_TYPE", catalog = "siadb", uniqueConstraints = {
		@UniqueConstraint(columnNames = "ID") }, indexes = { @Index(columnList = "ID, TOOL_NAME") })
public class BuildTypeEntity {

	public static final String TABLE_NAME = "BUILD_TYPE";

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", unique = true, nullable = false)
	private long id;

	@Column(name = "TOOL_NAME", unique = false, nullable = false, length = 150)
	private String toolName;

	@Column(name = "TOOL_VERSION", unique = false, nullable = false, length = 150)
	private String version;

	@Column(name = "HAS_DEPENDENCY", nullable = false)
	private boolean supportsDependency;

	@Column(name = "HAS_BUILD", nullable = false)
	private boolean supportsBuild;

	@Column(name = "HAS_STAGING", nullable = false)
	private boolean supportsStaging;

	@Column(name = "HAS_RELEASE", nullable = false)
	private boolean supportsRelease;

	@Column(name = "HAS_AUTH", nullable = false)
	private boolean supportsAuth;

	public BuildTypeEntity() {
	}

	public BuildTypeEntity(String toolName, String version, boolean supportsDependency, boolean supportsBuild,
			boolean supportsStaging, boolean supportsRelease, boolean supportsAuth) {
		this.toolName = toolName;
		this.version = version;
		this.supportsDependency = supportsDependency;
		this.supportsBuild = supportsBuild;
		this.supportsStaging = supportsStaging;
		this.supportsRelease = supportsRelease;
		this.supportsAuth = supportsAuth;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getToolName() {
		return toolName;
	}

	public void setToolName(String toolName) {
		this.toolName = toolName;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public boolean isSupportsDependency() {
		return supportsDependency;
	}

	public void setSupportsDependency(boolean supportsDependency) {
		this.supportsDependency = supportsDependency;
	}

	public boolean isSupportsBuild() {
		return supportsBuild;
	}

	public void setSupportsBuild(boolean supportsBuild) {
		this.supportsBuild = supportsBuild;
	}

	public boolean isSupportsStaging() {
		return supportsStaging;
	}

	public void setSupportsStaging(boolean supportsStaging) {
		this.supportsStaging = supportsStaging;
	}

	public boolean isSupportsRelease() {
		return supportsRelease;
	}

	public void setSupportsRelease(boolean supportsRelease) {
		this.supportsRelease = supportsRelease;
	}

	public boolean isSupportsAuth() {
		return supportsAuth;
	}

	public void setSupportsAuth(boolean supportsAuth) {
		this.supportsAuth = supportsAuth;
	}

	@Override
	public String toString() {
		return "BuildType [" + toolName + ": " + version + "; supportsDependency=" + supportsDependency
				+ ", supportsBuild=" + supportsBuild + ", supportsStaging=" + supportsStaging + ", supportsRelease="
				+ supportsRelease + ", supportsAuth=" + supportsAuth + "]";
	}

}
