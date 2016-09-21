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

import javax.persistence.*;

@Entity
@Table(name = "ERROR", catalog = "siadb", uniqueConstraints = {
		@UniqueConstraint(columnNames = "ID") })
public class ErrorEntity {

	public static final String TABLE_NAME = "HOST";

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", unique = true, nullable = false)
	private long id;

	@Column(name = "ERRORCODE", unique = true, nullable = false)
	private int errorCode;

	@Column(name = "DESCRIPTION", nullable = false, length = 250)
	private String description;

	@Column(name = "ERRORTYPE", nullable = false, length = 50)
	@Enumerated(EnumType.STRING)
	private ErrorType errorType;

	@Column(name = "SEVERITY", nullable = false, length = 50)
	@Enumerated(EnumType.STRING)
	private ErrorSeverity severity;

	public ErrorEntity() {
	}

	public ErrorEntity(int errorCode, String description, ErrorType errorType, ErrorSeverity severity) {
		this.errorCode = errorCode;
		this.description = description;
		this.errorType = errorType;
		this.severity = severity;
	}


	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}


	public int getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}


	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public ErrorType getErrorType() {
		return errorType;
	}
	public void setErrorType(ErrorType errorType) {
		this.errorType = errorType;
	}
	public ErrorSeverity getSeverity() {
		return severity;
	}
	public void setSeverity(ErrorSeverity severity) {
		this.severity = severity;
	}

	@Override
	public String toString() {
		return "ErrorEntity{" + "id=" + id + ", errorCode=" + errorCode + ", description='" + description + '\''
				+ ", errorType=" + errorType + ", severity=" + severity + '}';
	}
}
