/*
 * Copyright 2011 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may	obtain a copy of the License at
 *
 * 	http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.student.enrollment.lpr.service.adapter.checker;

import org.kuali.student.enrollment.lpr.service.adapter.validation.LuiPersonRelationReadOnlyFieldUpdatesCheckerAdapter;
import org.junit.*;
import org.kuali.student.common.infc.*;
import org.kuali.student.enrollment.lpr.infc.LuiPersonRelationInfc;
import org.kuali.student.enrollment.lpr.service.LuiPersonRelationServiceInfc;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import org.kuali.student.common.dto.ContextInfo;
import org.kuali.student.common.dto.MetaInfo;
import org.kuali.student.core.exceptions.ReadOnlyException;
import org.kuali.student.enrollment.lpr.dto.LuiPersonRelationInfo;
import org.kuali.student.enrollment.lpr.mock.CopierHelper;
import org.kuali.student.enrollment.lpr.mock.LuiPersonRelationServiceMockPersistenceImpl;
import org.kuali.student.enrollment.lpr.mock.LuiPersonRelationStateEnum;
import org.kuali.student.enrollment.lpr.mock.LuiPersonRelationTypeEnum;

import static org.junit.Assert.*;

/**
 *
 * @author nwright
 */
public class LuiPersonRelationReadOnlyFieldUpdatesCheckerAdapterTest {

	public LuiPersonRelationReadOnlyFieldUpdatesCheckerAdapterTest() {
	}

	@BeforeClass
	public static void setUpClass() throws Exception {
	}

	@AfterClass
	public static void tearDownClass() throws Exception {
	}

	@Before
	public void setUp() {
	}

	@After
	public void tearDown() {
	}
	private LuiPersonRelationServiceInfc service = null;

	public LuiPersonRelationServiceInfc getService() {
		if (service == null) {
			// TODO: configure this via spring testing framework instead so we can test other persistence impls instead of just the mock
			LuiPersonRelationReadOnlyFieldUpdatesCheckerAdapter adapter = new LuiPersonRelationReadOnlyFieldUpdatesCheckerAdapter();
			adapter.setLprService(new LuiPersonRelationServiceMockPersistenceImpl());
			service = adapter;
		}
		return service;
	}

	public void setService(LuiPersonRelationServiceInfc service) {
		this.service = service;
	}

	private ContextInfc getContext1() {
		return new ContextInfo.Builder().setPrincipalId("principalId.1").setLocaleLanguage("en").setLocaleRegion("us").build();
	}

	private ContextInfc getContext2() {
		return new ContextInfo.Builder().setPrincipalId("principalId.2").setLocaleLanguage("fr").setLocaleRegion("ca").build();
	}

	private Date parseDate(String str) {
		DateFormat df = new SimpleDateFormat("yyyy-mm-dd");
		Date date = null;
		try {
			date = df.parse(str);
		} catch (ParseException ex) {
			throw new IllegalArgumentException(str, ex);
		}
		return date;
	}

	/**
	 * Test testLuiPersonRelationLifeCycle
	 */
	@Test
	public void testReadOnlyChecking() throws Exception {
		System.out.println("testReadOnlyChecking");


		String personId = "personId.1";
		String luiId = "luiId.1";
		String luiPersonRelationType = LuiPersonRelationTypeEnum.STUDENT.getKey();
		LuiPersonRelationInfc orig =
			new LuiPersonRelationInfo.Builder()
				.setState(LuiPersonRelationStateEnum.APPLIED.getKey())
				.setEffectiveDate(parseDate("2010-01-01"))
				.setId("Id not allowed")
				.build();
		ContextInfc context = getContext1();

		try {
			getService().createLuiPersonRelation(personId, luiId, luiPersonRelationType, orig, context);
			fail("should have thrown a readOnly Exception because the id is not allowed to be supplied on the create");
		} catch (ReadOnlyException ex) {
			// expected
		}
		// TODO: decide if an empty MetaInfo is bad or just one that is filled in with some values
		orig = new LuiPersonRelationInfo.Builder().setId(null).setMetaInfo(new MetaInfo.Builder().build()).build();
		try {
			getService().createLuiPersonRelation(personId, luiId, luiPersonRelationType, orig, context);
			fail("should have thrown a readOnly Exception because the metaInfo is not allowed to be supplied on the create");
		} catch (ReadOnlyException ex) {
			// expected
		}		
		orig = new LuiPersonRelationInfo.Builder().setId(null).setMetaInfo(null).build();


		// do the create call
		String lprId = getService().createLuiPersonRelation(personId, luiId, luiPersonRelationType, orig, context);
		assertNotNull(lprId);

		// fetch
		LuiPersonRelationInfc fetched = getService().fetchLUIPersonRelation(lprId, context);

		// check readonly fields on update
		LuiPersonRelationInfc bad = null;
		String badField = null;

		// bad = new CopierHelper().makeCopy(fetched);
		bad = new LuiPersonRelationInfo.Builder(fetched).setId("Readonly Id").build();
		badField = "id";
		try {
			LuiPersonRelationInfc badReturn = getService().updateLuiPersonRelation(fetched.getId(), bad, context);
			fail("should have thrown a readOnly Exception because the " + badField + " was changed");
		} catch (ReadOnlyException ex) {
			// expected
		}

		bad = new LuiPersonRelationInfo.Builder(fetched).setType(LuiPersonRelationTypeEnum.INSTRUCTOR_MAIN.getKey()).build();
		badField = "type";
		try {
			LuiPersonRelationInfc badReturn = getService().updateLuiPersonRelation(fetched.getId(), bad, context);
			fail("should have thrown a readOnly Exception because the " + badField + " was changed");
		} catch (ReadOnlyException ex) {
			// expected
		}

		bad = new LuiPersonRelationInfo.Builder(fetched)
						.setType(LuiPersonRelationTypeEnum.INSTRUCTOR_MAIN.getKey())
						.setMetaInfo(new MetaInfo.Builder(fetched.getMetaInfo()).setCreateId("ReadonlyCreateId").build())
						.build();
		badField = "createId";
		try {
			LuiPersonRelationInfc badReturn = getService().updateLuiPersonRelation(fetched.getId(), bad, context);
			fail("should have thrown a readOnly Exception because the " + badField + " was changed");
		} catch (ReadOnlyException ex) {
			// expected
		}

		bad = new LuiPersonRelationInfo.Builder(fetched)
						.setMetaInfo(new MetaInfo.Builder(fetched.getMetaInfo()).setCreateTime(parseDate("2010-01-01")).build())
						.build();
		badField = "createTime";
		try {
			LuiPersonRelationInfc badReturn = getService().updateLuiPersonRelation(fetched.getId(), bad, context);
			fail("should have thrown a readOnly Exception because the " + badField + " was changed");
		} catch (ReadOnlyException ex) {
			// expected
		}

		bad = new LuiPersonRelationInfo.Builder(fetched)
						.setMetaInfo(new MetaInfo.Builder(fetched.getMetaInfo()).setUpdateId("ReadonlyUpdated").build())
						.build();
		badField = "updateId";
		try {
			LuiPersonRelationInfc badReturn = getService().updateLuiPersonRelation(fetched.getId(), bad, context);
			fail("should have thrown a readOnly Exception because the " + badField + " was changed");
		} catch (ReadOnlyException ex) {
			// expected
		}

		bad = new LuiPersonRelationInfo.Builder(fetched)
						.setMetaInfo(new MetaInfo.Builder(fetched.getMetaInfo()).setUpdateTime(parseDate("2010-01-01")).build())
						.build();
		badField = "updateTime";
		try {
			LuiPersonRelationInfc badReturn = getService().updateLuiPersonRelation(fetched.getId(), bad, context);
			fail("should have thrown a readOnly Exception because the " + badField + " was changed");
		} catch (ReadOnlyException ex) {
			// expected
		}

	}

	private AttributeInfc findMatching(AttributeInfc search, List<AttributeInfc> list) {
		// TODO: when AttributeInfo gets it's own ID do the find by ID instead of values
		for (AttributeInfc da : list) {
			if (search.getKey().equals(da.getKey())) {
				if (search.getValue().equals(da.getValue())) {
					return da;
				}
			}
		}
		return null;
	}
}
