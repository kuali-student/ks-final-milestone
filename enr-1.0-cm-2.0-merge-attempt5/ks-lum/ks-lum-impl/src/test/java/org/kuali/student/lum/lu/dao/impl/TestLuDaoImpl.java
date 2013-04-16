/**
 * Copyright 2010 The Kuali Foundation Licensed under the
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

import org.junit.Test;
import org.kuali.student.common.test.spring.AbstractTransactionalDaoTest;
import org.kuali.student.common.test.spring.Dao;
import org.kuali.student.common.test.spring.PersistenceFileLocation;
import org.kuali.student.r1.common.dao.impl.AbstractCrudDaoImpl;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.util.date.DateFormatters;
import org.kuali.student.r2.core.versionmanagement.dto.VersionDisplayInfo;
import org.kuali.student.r2.lum.lu.dao.LuDao;
import org.kuali.student.r2.lum.lu.entity.CluLoRelationType;
import org.kuali.student.r2.lum.lu.entity.CluSet;
import org.kuali.student.r2.lum.lu.entity.MembershipQuery;
import org.kuali.student.r2.lum.lu.entity.SearchParameter;
import org.kuali.student.r2.lum.lu.entity.SearchParameterValue;

import javax.persistence.Query;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

//import org.kuali.student.r2.lum.lu.entity.Lui;

@PersistenceFileLocation("classpath:META-INF/lu-persistence.xml")
public class TestLuDaoImpl extends AbstractTransactionalDaoTest {
    @Dao(value = "org.kuali.student.r2.lum.lu.dao.impl.LuDaoImpl", testSqlFile = "classpath:ks-lu.sql")
    public LuDao dao;

    @Test
    public void testJPQL() {

        Query query = ((AbstractCrudDaoImpl) dao).getEm().createQuery(
                "SELECT clu.id, clu.officialIdentifier.code, rel.loId " +
                        "FROM CluLoRelation rel " +
                        "JOIN rel.clu clu " +
                        "LEFT JOIN clu.adminOrgs adminOrg ");

        query.getResultList();
    }

    /*
     @Test
     public void testGetLuLuRelationTypeInfo(){
         List<Lui> luis = dao.getLuisByRelationType("LUI-3", "luLuType.type1");
         assertEquals(1, luis.size());
         assertEquals("LUI-1", luis.get(0).getId());
     }
              */
    @Test
    public void testGetCluLoRelationTypeInfo() throws DoesNotExistException {
        CluLoRelationType relType = dao.fetch(CluLoRelationType.class, "kuali.lu.lo.relation.type.includes");
        assertEquals("Default Clu-Lo relation type", relType.getDescr());
    }

    @Test
    public void testCreateCluLoRelation() throws Exception {
    }

    @Test
    public void testCreateMembershipQuery_Simple() throws Exception {
        MembershipQuery mq = new MembershipQuery();
        mq.setSearchTypeKey("searchKey1");
        mq = dao.create(mq);

        mq = dao.fetch(MembershipQuery.class, mq.getId());

        assertEquals("searchKey1", mq.getSearchTypeKey());
        assertNotNull(mq.getSearchParameters());
        assertEquals(0, mq.getSearchParameters().size());
    }

    @Test
    public void testCreateMembershipQuery_Complex() throws Exception {
        MembershipQuery mq = createMembershipQuery();
        mq = dao.fetch(MembershipQuery.class, mq.getId());

        assertEquals("searchKey1", mq.getSearchTypeKey());
        assertEquals(1, mq.getSearchParameters().size());
        assertEquals("key-1", mq.getSearchParameters().get(0).getKey());
        assertEquals(2, mq.getSearchParameters().get(0).getValues().size());
        assertEquals("value-1", mq.getSearchParameters().get(0).getValues().get(0).getValue());
        assertEquals("value-2", mq.getSearchParameters().get(0).getValues().get(1).getValue());
    }

    @Test
    public void testCreateClu() throws Exception {
        CluSet cluSet = new CluSet();
        MembershipQuery mq = createMembershipQuery();
        cluSet.setMembershipQuery(mq);
        cluSet = dao.create(cluSet);

        assertEquals("searchKey1", cluSet.getMembershipQuery().getSearchTypeKey());
        assertNotNull(cluSet.getMembershipQuery().getSearchParameters());
        assertEquals(1, cluSet.getMembershipQuery().getSearchParameters().size());
        assertEquals("key-1", cluSet.getMembershipQuery().getSearchParameters().get(0).getKey());
        assertEquals(2, cluSet.getMembershipQuery().getSearchParameters().get(0).getValues().size());
        assertEquals("value-1", cluSet.getMembershipQuery().getSearchParameters().get(0).getValues().get(0).getValue());
        assertEquals("value-2", cluSet.getMembershipQuery().getSearchParameters().get(0).getValues().get(1).getValue());
    }

    @Test
    public void testVersionQueries() throws Exception {
        List<VersionDisplayInfo> versions;
        versions = dao.getVersions("CLU-VERSIONTEST-IND", "foo.com");
        assertEquals(6, versions.size());

        VersionDisplayInfo versionDisplay;
        versionDisplay = dao.getCurrentCluVersionInfo("CLU-VERSIONTEST-IND", "foo.com");
        assertEquals("CLU-VERSIONTEST-V4", versionDisplay.getId());

        versionDisplay = dao.getCurrentVersionOnDate("CLU-VERSIONTEST-IND", "foo.com", DateFormatters.YEAR_MONTH_DAY_CONCAT_DATE_FORMATTER.parse("20100110"));
        assertEquals("CLU-VERSIONTEST-V0", versionDisplay.getId());

        versionDisplay = dao.getCurrentVersionOnDate("CLU-VERSIONTEST-IND", "foo.com", DateFormatters.YEAR_MONTH_DAY_CONCAT_DATE_FORMATTER.parse("20110210"));
        assertEquals("CLU-VERSIONTEST-V4", versionDisplay.getId());

        versionDisplay = dao.getFirstVersion("CLU-VERSIONTEST-IND", "foo.com");
        assertEquals("CLU-VERSIONTEST-V0", versionDisplay.getId());

        versionDisplay = dao.getLatestVersion("CLU-VERSIONTEST-IND", "foo.com");
        assertEquals("CLU-VERSIONTEST-V5", versionDisplay.getId());

        versionDisplay = dao.getVersionBySequenceNumber("CLU-VERSIONTEST-IND", "foo.com", Long.valueOf(3));
        assertEquals("CLU-VERSIONTEST-V3", versionDisplay.getId());

        versions = dao.getVersionsInDateRange("CLU-VERSIONTEST-IND", "foo.com", DateFormatters.YEAR_MONTH_DAY_CONCAT_DATE_FORMATTER.parse("20100210"), DateFormatters.YEAR_MONTH_DAY_CONCAT_DATE_FORMATTER.parse("20100410"));
        assertEquals(3, versions.size());

        versions = dao.getVersionsInDateRange("CLU-VERSIONTEST-IND", "foo.com", DateFormatters.YEAR_MONTH_DAY_CONCAT_DATE_FORMATTER.parse("20100210"), DateFormatters.YEAR_MONTH_DAY_CONCAT_DATE_FORMATTER.parse("20100310"));
        assertEquals(2, versions.size());

        versions = dao.getVersionsInDateRange("CLU-VERSIONTEST-IND", "foo.com", DateFormatters.YEAR_MONTH_DAY_CONCAT_DATE_FORMATTER.parse("20100310"), DateFormatters.YEAR_MONTH_DAY_CONCAT_DATE_FORMATTER.parse("20100310"));
        assertEquals(1, versions.size());

        versions = dao.getVersionsInDateRange("CLU-VERSIONTEST-IND", "foo.com", DateFormatters.YEAR_MONTH_DAY_CONCAT_DATE_FORMATTER.parse("20100310"), DateFormatters.YEAR_MONTH_DAY_CONCAT_DATE_FORMATTER.parse("20100410"));
        assertEquals(2, versions.size());

        versions = dao.getVersionsInDateRange("CLU-VERSIONTEST-IND", "foo.com", DateFormatters.YEAR_MONTH_DAY_CONCAT_DATE_FORMATTER.parse("20090310"), DateFormatters.YEAR_MONTH_DAY_CONCAT_DATE_FORMATTER.parse("20090410"));
        assertEquals(0, versions.size());

    }

    private MembershipQuery createMembershipQuery() {
        List<SearchParameterValue> list1 = new ArrayList<SearchParameterValue>();
        SearchParameterValue v1 = new SearchParameterValue();
        v1.setValue("value-1");
        list1.add(v1);
        SearchParameterValue v2 = new SearchParameterValue();
        v2.setValue("value-2");
        list1.add(v2);

        SearchParameter sp = new SearchParameter();
        sp.setKey("key-1");
        sp.setValues(list1);

        List<SearchParameter> list2 = Arrays.asList(new SearchParameter[]{sp});

        MembershipQuery mq = new MembershipQuery();
        mq.setSearchTypeKey("searchKey1");
        mq.setSearchParameters(list2);
        mq = dao.create(mq);

        return mq;
    }

}
