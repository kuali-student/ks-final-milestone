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
import org.kuali.student.enrollment.lpr.service.LuiPersonRelationService;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import org.kuali.student.common.dto.AttributeInfo;
import org.kuali.student.common.dto.ContextInfo;
import org.kuali.student.common.dto.MetaInfo;
import org.kuali.student.common.exceptions.ReadOnlyException;
import org.kuali.student.enrollment.lpr.dto.LuiPersonRelationInfo;
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
	private LuiPersonRelationService service = null;

	public LuiPersonRelationService getService() {
		if (service == null) {
			// TODO: configure this via spring testing framework instead so we can test other persistence impls instead of just the mock
			LuiPersonRelationReadOnlyFieldUpdatesCheckerAdapter adapter = new LuiPersonRelationReadOnlyFieldUpdatesCheckerAdapter();
			adapter.setLprService(new LuiPersonRelationServiceMockPersistenceImpl());
			service = adapter;
		}
		return service;
	}

	public void setService(LuiPersonRelationService service) {
		this.service = service;
	}

	private ContextInfo getContext1() {
		return new ContextInfo.Builder().principalId("principalId.1").localeLanguage("en").localeRegion("us").build();
	}

	private ContextInfo getContext2() {
		return new ContextInfo.Builder().principalId("principalId.2").localeLanguage("fr").localeRegion("ca").build();
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
		String luiPersonRelationType = LuiPersonRelationTypeEnum.REGISTRANT.getKey();
		LuiPersonRelationInfo orig =
			new LuiPersonRelationInfo.Builder()
				.state(LuiPersonRelationStateEnum.APPLIED.getKey())
				.effectiveDate(parseDate("2010-01-01"))
				.id("Id not allowed")
				.build();
		ContextInfo context = getContext1();

		try {
			getService().createLuiPersonRelation(personId, luiId, luiPersonRelationType, orig, context);
			fail("should have thrown a readOnly Exception because the id is not allowed to be supplied on the create");
		} catch (ReadOnlyException ex) {
			// expected
		}
		// TODO: decide if an empty MetaInfo is bad or just one that is filled in with some values
		orig = new LuiPersonRelationInfo.Builder().id(null).metaInfo(new MetaInfo.Builder().build()).build();
		try {
			getService().createLuiPersonRelation(personId, luiId, luiPersonRelationType, orig, context);
			fail("should have thrown a readOnly Exception because the metaInfo is not allowed to be supplied on the create");
		} catch (ReadOnlyException ex) {
			// expected
		}		
		orig = new LuiPersonRelationInfo.Builder().id(null).metaInfo(null).build();


		// do the create call
		String lprId = getService().createLuiPersonRelation(personId, luiId, luiPersonRelationType, orig, context);
		assertNotNull(lprId);

		// fetch
		LuiPersonRelationInfo fetched = getService().fetchLUIPersonRelation(lprId, context);

		// check readonly fields on update
		LuiPersonRelationInfo bad = null;
		String badField = null;

		// bad = new CopierHelper().makeCopy(fetched);
		bad = new LuiPersonRelationInfo.Builder(fetched).id("Readonly Id").build();
		badField = "id";
		try {
			LuiPersonRelationInfo badReturn = getService().updateLuiPersonRelation(fetched.getId(), bad, context);
			fail("should have thrown a readOnly Exception because the " + badField + " was changed");
		} catch (ReadOnlyException ex) {
			// expected
		}

		bad = new LuiPersonRelationInfo.Builder(fetched).type(LuiPersonRelationTypeEnum.INSTRUCTOR_MAIN.getKey()).build();
		badField = "type";
		try {
			LuiPersonRelationInfo badReturn = getService().updateLuiPersonRelation(fetched.getId(), bad, context);
			fail("should have thrown a readOnly Exception because the " + badField + " was changed");
		} catch (ReadOnlyException ex) {
			// expected
		}

		bad = new LuiPersonRelationInfo.Builder(fetched)
						.type(LuiPersonRelationTypeEnum.INSTRUCTOR_MAIN.getKey())
						.metaInfo(new MetaInfo.Builder(fetched.getMetaInfo()).createId("ReadonlyCreateId").build())
						.build();
		badField = "createId";
		try {
			LuiPersonRelationInfo badReturn = getService().updateLuiPersonRelation(fetched.getId(), bad, context);
			fail("should have thrown a readOnly Exception because the " + badField + " was changed");
		} catch (ReadOnlyException ex) {
			// expected
		}

		bad = new LuiPersonRelationInfo.Builder(fetched)
						.metaInfo(new MetaInfo.Builder(fetched.getMetaInfo()).createTime(parseDate("2010-01-01")).build())
						.build();
		badField = "createTime";
		try {
			LuiPersonRelationInfo badReturn = getService().updateLuiPersonRelation(fetched.getId(), bad, context);
			fail("should have thrown a readOnly Exception because the " + badField + " was changed");
		} catch (ReadOnlyException ex) {
			// expected
		}

		bad = new LuiPersonRelationInfo.Builder(fetched)
						.metaInfo(new MetaInfo.Builder(fetched.getMetaInfo()).updateId("ReadonlyUpdated").build())
						.build();
		badField = "updateId";
		try {
			LuiPersonRelationInfo badReturn = getService().updateLuiPersonRelation(fetched.getId(), bad, context);
			fail("should have thrown a readOnly Exception because the " + badField + " was changed");
		} catch (ReadOnlyException ex) {
			// expected
		}

		bad = new LuiPersonRelationInfo.Builder(fetched)
						.metaInfo(new MetaInfo.Builder(fetched.getMetaInfo()).updateTime(parseDate("2010-01-01")).build())
						.build();
		badField = "updateTime";
		try {
			LuiPersonRelationInfo badReturn = getService().updateLuiPersonRelation(fetched.getId(), bad, context);
			fail("should have thrown a readOnly Exception because the " + badField + " was changed");
		} catch (ReadOnlyException ex) {
			// expected
		}

	}

	private AttributeInfo findMatching(AttributeInfo search, List<AttributeInfo> list) {
		// TODO: when AttributeInfo gets it's own ID do the find by ID instead of values
		for (AttributeInfo da : list) {
			if (search.getKey().equals(da.getKey())) {
				if (search.getValue().equals(da.getValue())) {
					return da;
				}
			}
		}
		return null;
	}
}
