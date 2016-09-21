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

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "CHECKER_PLUGIN", catalog = "siadb", uniqueConstraints = { @UniqueConstraint(columnNames = "ID") },
	   indexes = {@Index(columnList = "ID")})
@AssociationOverrides({
	@AssociationOverride(name = "checkerList",
		joinColumns = @JoinColumn(name = "CHECKERLIST_ID")),
	@AssociationOverride(name = "plugin",
		joinColumns = @JoinColumn(name = "PLUGIN_ID")) })
public class CheckerPluginEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID")
	private Long id;

	@ManyToOne
	@JsonBackReference(value="checkerlist-link")
	private CheckerListEntity checkerList;

	@ManyToOne
	@JsonBackReference(value="plugin-link")
	private PluginEntity plugin;

	@Column(name = "POSITION")
	private Integer position;

	public CheckerPluginEntity(CheckerListEntity checkerList, PluginEntity plugin, Integer position) {
		this.checkerList = checkerList;
		this.plugin = plugin;
		this.position = position;
	}

	public CheckerPluginEntity(){

	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public CheckerListEntity getCheckerList() {
		return checkerList;
	}

	public void setCheckerList(CheckerListEntity checkerList) {
		this.checkerList = checkerList;
	}

	public PluginEntity getPlugin() {
		return plugin;
	}

	public void setPlugin(PluginEntity plugin) {
		this.plugin = plugin;
	}

	public Integer getPosition() {
		return position;
	}

	public void setPosition(Integer position) {
		this.position = position;
	}
}
