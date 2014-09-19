/**
 * Copyright 2012 The Kuali Foundation Licensed under the
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
 *
 * Created by Charles on 2/28/12
 */
package org.kuali.student.enrollment.class2.academicrecord.service.impl;

import edu.emory.mathcs.backport.java.util.Arrays;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kuali.student.common.UUIDHelper;
import org.kuali.student.common.test.spring.AbstractTransactionalDaoTest;
import org.kuali.student.common.test.spring.Dao;
import org.kuali.student.common.test.spring.PersistenceFileLocation;
import org.kuali.student.enrollment.academicrecord.dao.StudentCourseRecordDao;
import org.kuali.student.enrollment.academicrecord.dto.StudentCourseRecordInfo;
import org.kuali.student.enrollment.academicrecord.model.StudentCourseRecordEntity;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * TestStudentCourseRecordDao
 * @author Kuali Student Team
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:ar-studentcourserecorddao-jpa-persistence-test-context.xml"})
@PersistenceFileLocation("classpath:META-INF/ar-persistence.xml")
public class TestStudentCourseRecordDao extends AbstractTransactionalDaoTest {

    @Dao(value = "org.kuali.student.enrollment.academicrecord.dao.StudentCourseRecordDao")
    private StudentCourseRecordDao studentCourseRecordDao;

    public Map<String, List<StudentCourseRecordInfo>> getStudentToCourseRecordsMap() {
        return studentToCourseRecordsMap;
    }

    public void setStudentToCourseRecordsMap(Map<String, List<StudentCourseRecordInfo>> studentToCourseRecordsMap) {
        this.studentToCourseRecordsMap = studentToCourseRecordsMap;
    }

    @Resource
    private Map<String, List<StudentCourseRecordInfo>> studentToCourseRecordsMap;

    private void persistStudentCourseRecord(StudentCourseRecordInfo studentCourseRecord) {
        studentCourseRecord.setTypeKey("kuali.academicrecord.studentcourserecord.type.course");
        studentCourseRecord.setId(UUIDHelper.genStringUUID());
        StudentCourseRecordEntity entity = new StudentCourseRecordEntity(studentCourseRecord);
        entity.setEntityCreated(new ContextInfo());
        entity.setCreateId("SYSTEMLOADER");
        entity.setUpdateId("SYSTEMLOADER");
        studentCourseRecordDao.persist(entity);
        studentCourseRecordDao.getEm().flush();
    }

    @Before
    public void setUp() {
        // load mock data from ks-enroll-mock-academic-service.xml
        Set<String> keys = studentToCourseRecordsMap.keySet();
        for(String key : keys) {
            Object entry = studentToCourseRecordsMap.get(key);
            if(entry instanceof StudentCourseRecordInfo) {
                persistStudentCourseRecord((StudentCourseRecordInfo) entry);
            } else {
                for (StudentCourseRecordInfo studentCourseRecord : (List<StudentCourseRecordInfo>)entry) {
                    persistStudentCourseRecord(studentCourseRecord);
                }
            }
        }
    }

    @Test
    public void testGetForCourse() throws Exception {
        String personId = "R.JESSICAL"; // R.JESSICAL  KS-5213
        String courseCode = "HIST499";
        List<StudentCourseRecordEntity> scrs = studentCourseRecordDao.getForCourse(personId, courseCode);

        org.junit.Assert.assertNotNull(scrs);
        Assert.assertNotSame("No StudentCourseRecordEntity results found. Expected > 0", scrs.size(), 0);
    }

    @Test
    public void testGetForCourses() throws Exception {
        String personId = "R.JESSICAR"; // R.JESSICAR
        String[] courseCodes = { "CHEM689", "CHEM689A" };
        List<StudentCourseRecordEntity> scrs = studentCourseRecordDao.getForCourses(personId, Arrays.asList(courseCodes));

        org.junit.Assert.assertNotNull(scrs);
        Assert.assertNotSame("No StudentCourseRecordEntity results found. Expected > 0", scrs.size(), 0);
    }

    @Test
    public void testGetCompletedCourseRecordsForCourse() throws Exception {
        String personId = "R.JESSICAL"; // R.JESSICAL  KS-5213
        String courseCode = "HIST499";
        List<StudentCourseRecordEntity> scrs = studentCourseRecordDao.getCompletedCourseRecordsForCourse(personId, courseCode);

        Assert.assertNotNull("No StudentCourseRecordEntity results found. Expected not null", scrs);
        Assert.assertNotEquals("No StudentCourseRecordEntity results found. Expected > 0", scrs.size(), 0);
    }

    @Test
    public void testGetAttemptedCourseRecordsForTerm() throws Exception {
        String personId = "R.JESSICAL"; // R.JESSICAL  KS-5213
        String termId = "kuali.atp.2011Fall";
        List<StudentCourseRecordEntity> scrs = studentCourseRecordDao.getAttemptedCourseRecordsForTerm(personId, termId);

        Assert.assertNotNull("No StudentCourseRecordEntity results found. Expected not null", scrs);
        Assert.assertNotEquals("No StudentCourseRecordEntity results found. Expected > 0", scrs.size(), 0);
    }

    @Test
    public void testGetCompletedCourseRecordsForTerm() throws Exception {
        String personId = "R.JESSICAL"; // R.JESSICAL  KS-5213
        String termId = "kuali.atp.2011Fall";
        List<StudentCourseRecordEntity> scrs = studentCourseRecordDao.getCompletedCourseRecordsForTerm(personId, termId);

        Assert.assertNotNull("No StudentCourseRecordEntity results found. Expected not null", scrs);
        Assert.assertNotEquals("No StudentCourseRecordEntity results found. Expected > 0", scrs.size(), 0);
    }

    @Test
    public void testGetIdsByType() throws Exception {
        String type = "kuali.academicrecord.studentcourserecord.type.course";
        List<String> scrIds = studentCourseRecordDao.getIdsByType(type);

        Assert.assertNotNull("No StudentCourseRecordEntity results found. Expected not null", scrIds);
        Assert.assertNotEquals("No StudentCourseRecordEntity results found. Expected > 0", scrIds.size(), 0);
    }
}
