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

import org.hibernate.HibernateException;

import com.oneandone.relesia.database.util.EntityManagerUtil;

public abstract class AbstractPersistenceController implements PersistenceController {

	public <T> T create(T newEntity) {
		EntityManager em = EntityManagerUtil.getEntityManager();

		try {
			em.getTransaction().begin();
			em.persist(newEntity);
			em.getTransaction().commit();
			
		} catch (HibernateException e) {
			em.getTransaction().rollback();
			e.printStackTrace();
			return null;
		} catch (RuntimeException e) {
			em.getTransaction().rollback();
			e.printStackTrace();
			return null;
		} finally {
		}

		em.refresh(newEntity);
		return newEntity;
	}

	public <T> T update(T entity) {
		EntityManager em = EntityManagerUtil.getEntityManager();

		try {
			em.getTransaction().begin();
			em.merge(entity);
			em.getTransaction().commit();
			
			return entity;
		} catch (HibernateException e) {
			em.getTransaction().rollback();
			e.printStackTrace();
			return null;
		} catch (RuntimeException e) {
			em.getTransaction().rollback();
			e.printStackTrace();
			return null;
		} finally {
		}
	}

	public <T> T getById(Class<T> type, long id) {
		T entity = null;
		EntityManager em = EntityManagerUtil.getEntityManager();
		
		try {
			entity = em.find(type, id);

		} catch (HibernateException e) {
			e.printStackTrace();
		} finally {
		}
		return entity;
	}
	
	public <T> List<T> getAll(Class<T> type) {
		EntityManager em = EntityManagerUtil.getEntityManager();

		List<T> entities = null;
		
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
		CriteriaQuery<T> criteria = (CriteriaQuery<T>) criteriaBuilder.createQuery(type);
		Root<T> root = (Root<T>) criteria.from( type );
		criteria.select(root);
		
		entities = em.createQuery(criteria).getResultList();

		return entities;
	}

	public <T> void delete(Class<T> type, long id) {
		EntityManager em = EntityManagerUtil.getEntityManager();

		try {
			em.getTransaction().begin();
			T entity = em.find(type, id);
			em.remove(entity);
			em.getTransaction().commit();
		} catch (HibernateException e) {
			em.getTransaction().rollback();
			e.printStackTrace();
		} catch (RuntimeException e) {
			em.getTransaction().rollback();
			e.printStackTrace();
		} finally {
		}
	}

	public <T>  void delete(T entity) {
		EntityManager em = EntityManagerUtil.getEntityManager();
		try {
			em.getTransaction().begin();
			em.remove(entity);
			em.getTransaction().commit();
		} catch (HibernateException e) {
			em.getTransaction().rollback();
			e.printStackTrace();
		} catch (RuntimeException e) {
			em.getTransaction().rollback();
			e.printStackTrace();
		} finally {
		}
	}
}
