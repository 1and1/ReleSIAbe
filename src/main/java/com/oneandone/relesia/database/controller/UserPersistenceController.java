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

import java.util.Date;

import javax.persistence.EntityManager;

import com.oneandone.relesia.database.model.entity.UserEntity;
import com.oneandone.relesia.database.util.EntityManagerUtil;

public class UserPersistenceController extends AbstractPersistenceController {

	private static final UserPersistenceController instance = new UserPersistenceController();

	public static UserPersistenceController getInstance() {
		return instance;
	}

	@Override
	public UserEntity create(Object newUser) {
		UserEntity user = null;

		if (newUser instanceof UserEntity) {
			user = (UserEntity) newUser;
		} else {
			return null;
		}

		EntityManager em = EntityManagerUtil.getEntityManager();

		user.setLastModified(new Date());

		em.getTransaction().begin();
		em.persist(user);
		em.getTransaction().commit();

		return user;
	}

	@Override
	public UserEntity update(Object user) {
		EntityManager em = EntityManagerUtil.getEntityManager();

		if (!(user instanceof UserEntity)) {
			return null;
		}

		((UserEntity) user).setLastModified(new Date());
		em.getTransaction().begin();
		em.merge(user);
		em.getTransaction().commit();

		return (UserEntity) user;
	}
}
