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

package com.oneandone.relesia.database.controller;

import java.util.List;

public  interface PersistenceController{
	
	<T> T create(T newEntity);
	
	<T> T update(T entity);
	
	<T> T getById(Class<T> type, long id);
	
	<T> List<T> getAll(Class<T> type);
	
	<T> void delete(Class<T> type, long id);
	
	//void delete(Class<? extends SiaEntity> type, int id);
	
	<T> void delete(T entity);
}
