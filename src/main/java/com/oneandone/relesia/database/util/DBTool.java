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

package com.oneandone.relesia.database.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.persistence.EntityManager;

import com.oneandone.relesia.database.SiaPersistenceException;

public class DBTool {

	public static void initData() {
		EntityManager em = EntityManagerUtil.getEntityManager();

		em.getTransaction().begin();

		InputStream sqlInput;

		sqlInput = DBTool.class.getClassLoader().getResourceAsStream("db/db_init.sql");

		BufferedReader in = new BufferedReader(new InputStreamReader(sqlInput));
		String line = null;
		int result;

		try {
			while((line = in.readLine()) != null) {
				if (null != line && !line.isEmpty() && !line.startsWith("--")) {
					result = em.createNativeQuery(line).executeUpdate();
					if (result == 0) {
						em.getTransaction().rollback();
						throw new SiaPersistenceException("Error importing Initialization Data: " + line);
					}
				}
			}
			em.getTransaction().commit();
		} catch (IOException e) {
			em.getTransaction().rollback();
			throw new SiaPersistenceException("Error parsing input SQL file.", e);
		} catch (RuntimeException e) {
			em.getTransaction().rollback();
			throw new SiaPersistenceException("Error occured while updating database.", e);
		}
	}
	
	public static void initSampleData() {
		EntityManager em = EntityManagerUtil.getEntityManager();

		em.getTransaction().begin();

		InputStream sqlInput;

		sqlInput = DBTool.class.getClassLoader().getResourceAsStream("db/db_sample_data.sql");

		BufferedReader in = new BufferedReader(new InputStreamReader(sqlInput));
		String line = null;
		int result;

		try {
			while((line = in.readLine()) != null) {
				if (null != line && !line.isEmpty() && !line.startsWith("--")) {
					result = em.createNativeQuery(line).executeUpdate();
					if (result == 0) {
						em.getTransaction().rollback();
						throw new SiaPersistenceException("Error importing Sample Data: " + line);
					}
				}
			}
			em.getTransaction().commit();
		} catch (IOException e) {
			em.getTransaction().rollback();
			throw new SiaPersistenceException("Error parsing input SQL file.", e);
		} catch (RuntimeException e) {
			em.getTransaction().rollback();
			throw new SiaPersistenceException("Error occured while updating database.", e);
		}
	}
}
