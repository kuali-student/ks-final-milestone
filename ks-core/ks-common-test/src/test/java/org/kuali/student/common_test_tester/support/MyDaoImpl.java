/*
 * Copyright 2009 The Kuali Foundation Licensed under the
 * Educational Community License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may
 * obtain a copy of the License at
 * 
 * http://www.osedu.org/licenses/ECL-2.0
 * 
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */
package org.kuali.student.common_test_tester.support;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

public class MyDaoImpl implements MyDao {
	
	private EntityManager entityManager;

	@PersistenceContext
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
	
	public String createValue(Value value) {
		entityManager.persist(value);
		return value.getId();
	}

	public String findValue(String id) {
		Value tv =  entityManager.find(Value.class, id);
		if(tv==null){
			return null;
		}
		return tv.getValue();
	}

	@SuppressWarnings("unchecked")
	public Value findValueFromValue(String value) {
		Query q = entityManager.createQuery("SELECT v FROM Value v WHERE v.value=:valueIn");
		q.setParameter("valueIn", value);
		for(Value tv : (List<Value>)q.getResultList()){
			return tv;
		}
		return null;
	}

	public boolean updateValue(String id, String value) {
		Value v = entityManager.find(Value.class, id);
		v.setValue(value);
		entityManager.merge(v);
		return true;
	}
	
}
