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

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "PLUGIN", catalog = "siadb", uniqueConstraints = { @UniqueConstraint(columnNames = "ID") }, indexes = {
		@Index(columnList = "ID") })
public class PluginEntity {

	public static final String TABLE_NAME = "PLUGIN";
	public static final String TYPE_CONNECTOR = "CONNECTOR";
	public static final String TYPE_CHECKER = "CHECKER";

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", unique = true, nullable = false)
	private long id;

	@Column(name = "NAME", unique = true, nullable = false, length = 150)
	private String name;

	@Column(name = "TYPE", unique = false, nullable = false, length = 50)
	private String type;

	@Column(name = "CLASS_NAME", unique = false, nullable = false, length = 150)
	private String className;

	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "PLUGIN_ID")
	private Set<PluginPropertyEntity> properties;

	@ManyToMany
	@JoinTable(name = "PLUGIN_DEPENDENCY", catalog = "siadb", joinColumns = {
			@JoinColumn(name = "PLUGIN_ID", nullable = false, updatable = false) },
			   inverseJoinColumns = { @JoinColumn(name = "DEPENDENCY_ID",
												  nullable = false, updatable = false) })
	private List<PluginEntity> dependencies = new ArrayList<>();

	@OneToMany(mappedBy = "plugin")
	@JsonManagedReference(value="plugin-link")
	private Set<CheckerPluginEntity> checkerLists;
	
	public PluginEntity() {
	}

	public PluginEntity(String name, String type, String className) {
		this.name = name;
		this.type = type;
		this.className = className;
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
	

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public Set<PluginPropertyEntity> getProperties() {
		return properties;
	}

	public List<PluginEntity> getDependencies() {
		return dependencies;
	}

	public void setProperty(Set<PluginPropertyEntity> properties) {
		this.properties = properties;
	}

	public void addDependency (PluginEntity dependency){
		if ( dependency.type.equals(TYPE_CHECKER) ) {
			if (!this.className.equals(dependency.className)) {
				if (!dependencies.contains(dependency)) {
					if (!dependency.getDependencies().contains(this)) {
						try {
							this.getClass().getClassLoader().loadClass(dependency.className);
						} catch (ClassNotFoundException e) {
							throw new RuntimeException("Class does not exist!");
						} dependencies.add(dependency);
					} else {
						throw new RuntimeException("Circular Dependency!");
					}
				} else {
					throw new RuntimeException("Dependency already exists!");
				}
			}
		}
		else {
			throw new RuntimeException("Format not supported!");
		}
	}
	@Override
	public String toString() {
		return "Plugin [id=" + id + ", name=" + name + ", type=" + type + ", className=" + className + ", properties="
				+ properties + "]";
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		PluginEntity that = (PluginEntity) o;

		if (id != that.id) return false;
		if (!name.equals(that.name)) return false;
		if (!type.equals(that.type)) return false;
		if (!className.equals(that.className)) return false;
		if (!properties.equals(that.properties)) return false;
		return dependencies != null ? dependencies.equals(that.dependencies) : that.dependencies == null;
	}

	public Set<CheckerPluginEntity> getCheckerLists() {
		return checkerLists;
	}

	public void setCheckerLists(Set<CheckerPluginEntity> checkerLists) {
		this.checkerLists = checkerLists;
	}

}