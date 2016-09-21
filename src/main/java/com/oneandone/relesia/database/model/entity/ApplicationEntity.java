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
@Table(name = "APPLICATION", catalog = "siadb", uniqueConstraints = {
		@UniqueConstraint(columnNames = "ID") }, indexes = {@Index(columnList = "ID, PRODUCT_ID, NAME")})
public class ApplicationEntity {

		public static final String TABLE_NAME = "APPLICATION";

		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		@Column(name = "ID", unique = true, nullable = false)
		private long id;
		
		@Column(name = "NAME", unique = true, nullable = false, length = 150)
		private String name;
		
		@ManyToOne(fetch = FetchType.EAGER)
		@JoinColumn(name = "PRODUCT_ID", nullable = false)
		private ProductEntity product;
		
		@Column(name = "SOURCE_PATH", unique = false, nullable = true, length = 250)
		private String sourcePath;
		
		@ManyToOne(fetch = FetchType.EAGER)
		@JoinColumn(name = "SCM_ID", nullable = false)
		private SCMEntity scm;
		
		@ManyToOne(fetch = FetchType.EAGER)
		@JoinColumn(name = "LANGUAGE_ID", nullable = false)
		private ProgrammingLanguageEntity language;
		
		@ManyToOne(fetch = FetchType.EAGER)
		@JoinColumn(name = "BUILD_TYPE_ID", nullable = false)
		private BuildTypeEntity buildType;
		
		@ManyToOne(fetch = FetchType.EAGER)
		@JoinColumn(name = "RELEASE_REPO_ID", nullable = false)
		private ReleaseRepoEntity releaseRepo;
		
		@Column(name = "RELEASE_LOCATION", unique = false, nullable = true, length = 250)
		private String releaseLocation;
		
		@Column(name = "SNAPSHOT_LOCATION", unique = false, nullable = true, length = 250)
		private String snapshotLocation;

		public ApplicationEntity() {
		}
		
		
		
		public ApplicationEntity(String name, ProductEntity product, String sourcePath, SCMEntity scm,
				ProgrammingLanguageEntity language, BuildTypeEntity buildType, ReleaseRepoEntity releaseRepo,
				String releaseLocation, String snapshotLocation) {
			this.name = name;
			this.product = product;
			this.sourcePath = sourcePath;
			this.scm = scm;
			this.language = language;
			this.buildType = buildType;
			this.releaseRepo = releaseRepo;
			this.releaseLocation = releaseLocation;
			this.snapshotLocation = snapshotLocation;
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
		
		
		public ProductEntity getProduct() {
			return product;
		}

		public void setProduct(ProductEntity product) {
			this.product = product;
		}

		
		public String getSourcePath() {
			return sourcePath;
		}

		public void setSourcePath(String sourcePath) {
			this.sourcePath = sourcePath;
		}

		
		public SCMEntity getScm() {
			return scm;
		}

		public void setScm(SCMEntity scm) {
			this.scm = scm;
		}

		
		public ProgrammingLanguageEntity getLanguage() {
			return language;
		}

		public void setLanguage(ProgrammingLanguageEntity language) {
			this.language = language;
		}

		
		public BuildTypeEntity getBuildType() {
			return buildType;
		}

		public void setBuildType(BuildTypeEntity buildType) {
			this.buildType = buildType;
		}

		
		public ReleaseRepoEntity getReleaseRepo() {
			return releaseRepo;
		}

		public void setReleaseRepo(ReleaseRepoEntity releaseRepo) {
			this.releaseRepo = releaseRepo;
		}
		
		public String getReleaseLocation() {
			return releaseLocation;
		}

		public void setReleaseLocation(String releaseLocation) {
			this.releaseLocation = releaseLocation;
		}

		
		public String getSnapshotLocation() {
			return snapshotLocation;
		}

		public void setSnapshotLocation(String snapshotLocation) {
			this.snapshotLocation = snapshotLocation;
		}

		@Override
		public String toString() {
			return "Application [id=" + id + ", name=" + name + ", product=" + product + ", sourcePath="
					+ sourcePath + ", scm=" + scm + ", language=" + language + ", buildType=" + buildType
					+ ", releaseRepo=" + releaseRepo + ", releaseLocation=" + releaseLocation
					+ ", snapshotLocation=" + snapshotLocation + "]";
		}


		
		
}
