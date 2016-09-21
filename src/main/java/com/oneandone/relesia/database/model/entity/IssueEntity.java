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
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "ISSUE", catalog = "siadb", uniqueConstraints = {
		@UniqueConstraint(columnNames = "ID") }, indexes = {@Index(columnList = "ID, APPLICATION_ID, STATUS, ISSUE_TYPE")})
public class IssueEntity {
	
	public static final String TABLE_NAME = "ISSUE";

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", unique = true, nullable = false)
	private long id;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "APPLICATION_ID", nullable = false)
	private ApplicationEntity application;
	
	@Column(name = "SUBJECT", unique = false, nullable = false, length = 250)
	private String subject;
	
	@Column(name = "DESCRIPTION", unique = false, nullable = true, length = 250)
	private String description;
	
	@Column(name = "REFFERENCE_PATH", unique = false, nullable = false, length = 250)
	private String refferencePath;
	
	@Column(name = "ISSUE_TYPE", unique = false, nullable = false, length = 150)
	private String issueType;
	
	@Column(name = "ASSIGNEE", unique = false, nullable = true, length = 150)
	private String assignee;
	
	@Column(name = "STATUS", unique = false, nullable = false, length = 50)
	private String status;

	public IssueEntity() {
	}
	
	public IssueEntity(ApplicationEntity application, String subject, String description, String refferencePath, String issueType,
			String assignee, String status) {
		this.application = application;
		this.subject = subject;
		this.description = description;
		this.refferencePath = refferencePath;
		this.issueType = issueType;
		this.assignee = assignee;
		this.status = status;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public ApplicationEntity getApplication() {
		return application;
	}

	public void setApplication(ApplicationEntity application) {
		this.application = application;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getRefferencePath() {
		return refferencePath;
	}

	public void setRefferencePath(String refferencePath) {
		this.refferencePath = refferencePath;
	}

	public String getIssueType() {
		return issueType;
	}

	public void setIssueType(String issueType) {
		this.issueType = issueType;
	}

	public String getAssignee() {
		return assignee;
	}

	public void setAssignee(String assignee) {
		this.assignee = assignee;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "Issue [id=" + id + ", application=" + application + ", subject=" + subject + ", description=" + description
				+ ", refferencePath=" + refferencePath + ", issueType=" + issueType + ", assignee=" + assignee
				+ ", status=" + status + "]";
	}	
}
