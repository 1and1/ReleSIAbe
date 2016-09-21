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

import java.util.Date;

import javax.persistence.*;

@Entity
@Table(name = "WORK_APPLICATION", catalog = "siadb", uniqueConstraints = {
		@UniqueConstraint(columnNames = "ID") }, indexes = {@Index(columnList = "ID, APPLICATION_ID")})
public class WorkApplicationEntity {

		
		public static final String TABLE_NAME = "WORK_APPLICATION";

		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		@Column(name = "ID", unique = true, nullable = false)
		private long id;
		
		@Column(name = "NAME", unique = false, nullable = false, length = 150)
		private String name;
		
		@ManyToOne(fetch = FetchType.EAGER)
		@JoinColumn(name = "APPLICATION_ID", nullable = false)
		private ApplicationEntity application;
		
		@Column(name = "LOCAL_PATH", unique = false, nullable = true, length = 250)
		private String localPath;
		
		@Column(name = "TAG", unique = false, nullable = true, length = 250)
		private String tag;
		
		@Column(name = "LAST_MODIFIED", unique = false, nullable = false)
	    private Date lastModified;

		public WorkApplicationEntity() {
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

		public ApplicationEntity getApplication() {
			return application;
		}

		public void setApplication(ApplicationEntity application) {
			this.application = application;
		}

		public String getLocalPath() {
			return localPath;
		}

		public void setLocalPath(String localPath) {
			this.localPath = localPath;
		}

		public String getTag() {
			return tag;
		}

		public void setTag(String tag) {
			this.tag = tag;
		}

		public Date getLastModified() {
			return lastModified;
		}

		public void setLastModified(Date lastModified) {
			this.lastModified = lastModified;
		}

		@Override
		public String toString() {
			return "WorkApplication [id=" + id + ", name=" + name + ", application=" + application
					+ ", localPath=" + localPath + ", tag=" + tag + ", lastModified=" + lastModified + "]";
		}
				
		
}
