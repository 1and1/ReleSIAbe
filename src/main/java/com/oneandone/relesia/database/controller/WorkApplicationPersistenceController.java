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

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import com.oneandone.relesia.database.model.entity.WorkApplicationEntity;
import com.oneandone.relesia.database.util.EntityManagerUtil;;

public class WorkApplicationPersistenceController extends AbstractPersistenceController {

	private static final WorkApplicationPersistenceController instance = new WorkApplicationPersistenceController();

	public static WorkApplicationPersistenceController getInstance() {
		return instance;
	}
	
	public List<WorkApplicationEntity> getByAppAndTag(long appId, String tag) {
		EntityManager em = EntityManagerUtil.getEntityManager();

		List<WorkApplicationEntity> entities = null;
		
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
		CriteriaQuery<WorkApplicationEntity> criteria = criteriaBuilder.createQuery(WorkApplicationEntity.class);
		Root<WorkApplicationEntity> root = criteria.from( WorkApplicationEntity.class );
		criteria.select(root);
		criteria.where(criteriaBuilder.equal(root.get("TAG"), tag));
		criteria.where(criteriaBuilder.equal(root.get("APPLICATION_ID"), tag));
		
		entities = em.createQuery(criteria).getResultList();

		return entities;
	}
}
