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
@Table(name = "RELEASE_REPOSITORY", catalog = "siadb", uniqueConstraints = {
		@UniqueConstraint(columnNames = "ID") }, indexes = { @Index(columnList = "ID, TYPE") })
public class ReleaseRepoEntity {

	public static final String TABLE_NAME = "RELEASE_REPOSITORY";

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", unique = true, nullable = false)
	private long id;

	@Column(name = "NAME", unique = false, nullable = false, length = 150)
	private String name;

	@Column(name = "TYPE", unique = false, nullable = false, length = 150)
	private String type;

	@Column(name = "URL", unique = false, nullable = false, length = 150)
	private String url;

	@Column(name = "HAS_AUTH", nullable = false)
	private boolean supportsAuth;

	public ReleaseRepoEntity() {
	}

	public ReleaseRepoEntity(String name, String type, String url, boolean supportsAuth) {
		this.name = name;
		this.type = type;
		this.url = url;
		this.supportsAuth = supportsAuth;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public boolean isSupportsAuth() {
		return supportsAuth;
	}

	public void setSupportsAuth(boolean supportsAuth) {
		this.supportsAuth = supportsAuth;
	}

	@Override
	public String toString() {
		return "ReleaseRepo [id=" + id + ", name=" + name + ", type=" + type + ", url=" + url + ", supportsAuth="
				+ supportsAuth + "]";
	}

}