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
package org.kuali.student.lum.lu.dao.impl;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;
import org.kuali.student.common.test.spring.AbstractTransactionalDaoTest;
import org.kuali.student.common.test.spring.Dao;
import org.kuali.student.common.test.spring.PersistenceFileLocation;
import org.kuali.student.core.exceptions.DoesNotExistException;
import org.kuali.student.lum.lu.dao.LuDao;
import org.kuali.student.lum.lu.entity.CluLoRelationType;
import org.kuali.student.lum.lu.entity.Lui;

@PersistenceFileLocation("classpath:META-INF/lu-persistence.xml")
public class TestLuDaoImpl extends AbstractTransactionalDaoTest {
	@Dao(value = "org.kuali.student.lum.lu.dao.impl.LuDaoImpl", testSqlFile = "classpath:ks-lu.sql")
	public LuDao dao;

	@Test
	public void testGetLuLuRelationTypeInfo(){
		List<Lui> luis = dao.getLuisByRelationType("LUI-3", "luLuType.type1");
		assertEquals(1, luis.size());
		assertEquals("LUI-1", luis.get(0).getId());
	}
	
	@Test
	public void testGetCluLoRelationTypeInfo() throws DoesNotExistException{
		CluLoRelationType relType = dao.fetch(CluLoRelationType.class, "cluLuType.default");
		assertEquals("Default Clu-Lo relation type", relType.getDescr());
	}
	
	@Test
	public void testCreateCluLoRelation() {
	}
}
