/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kuali.student.enrollment.academicrecord.service;

import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.kuali.student.enrollment.academicrecord.dto.StudentCourseRecordInfo;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.StatusInfo;

/**
 *
 * @author nwright
 */
public class AcademicRecordServiceSubscriptionDecoratorTest {

    public AcademicRecordServiceSubscriptionDecoratorTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    private static class AcademicRecordCallbackServiceImpl implements AcademicRecordCallbackService {

        private String method;
        private List<String> studentCourseRecordIds;

        public String getMethod() {
            return method;
        }

        public void setMethod(String method) {
            this.method = method;
        }

        public List<String> getStudentCourseRecordIds() {
            return studentCourseRecordIds;
        }

        public void setStudentCourseRecordIds(List<String> studentCourseRecordIds) {
            this.studentCourseRecordIds = studentCourseRecordIds;
        }

        @Override
        public StatusInfo newStudentCourseRecords(List<String> studentCourseRecordIds, ContextInfo contextInfo) {
            this.method = "newStudentCourseRecords";
            this.studentCourseRecordIds = studentCourseRecordIds;
            System.out.println(method + "=" + studentCourseRecordIds);
            StatusInfo status = new StatusInfo();
            return status;
        }

        @Override
        public StatusInfo updateStudentCourseRecords(List<String> studentCourseRecordIds, ContextInfo contextInfo) {
            this.method = "updateStudentCourseRecords";
            this.studentCourseRecordIds = studentCourseRecordIds;
            System.out.println(method + "=" + studentCourseRecordIds);
            StatusInfo status = new StatusInfo();
            return status;
        }

        @Override
        public StatusInfo deleteStudentCourseRecords(List<String> studentCourseRecordIds, ContextInfo contextInfo) {
            this.method = "deleteStudentCourseRecords";
            this.studentCourseRecordIds = studentCourseRecordIds;
            System.out.println(method + "=" + studentCourseRecordIds);
            StatusInfo status = new StatusInfo();
            return status;
        }

    }

    /**
     * Test of subscribeToNewStudentCourseRecords method, of class AcademicRecordServiceSubscriptionDecorator.
     */
    @Test
    public void testSubscribeToNewStudentCourseRecords() throws Exception {
        System.out.println("subscribeToNewStudentCourseRecords");
        ContextInfo contextInfo = new ContextInfo();
        contextInfo.setPrincipalId("TEST-USER");
        AcademicRecordServiceSubscriptionDecorator instance = new AcademicRecordServiceSubscriptionDecorator();
        AcademicRecordServiceMapImpl mapImpl = new AcademicRecordServiceMapImpl();
        instance.setNextDecorator(mapImpl);

        StudentCourseRecordInfo rec1 = this.createStudentCourseRecord(instance, "term1", "person1", "typeKey1", contextInfo);
        AcademicRecordCallbackServiceImpl academicRecordCallbackService = new AcademicRecordCallbackServiceImpl();
        
        String result = instance.subscribeToNewStudentCourseRecords(academicRecordCallbackService, contextInfo);
        
        StudentCourseRecordInfo rec2 = this.createStudentCourseRecord(instance, "term2", "person2", "typeKey2", contextInfo);
        assertEquals ("newStudentCourseRecords", academicRecordCallbackService.getMethod());

    }

    private StudentCourseRecordInfo createStudentCourseRecord(
            AcademicRecordService service,
            String termId, String personId, String typeKey, ContextInfo contextInfo) {

        StudentCourseRecordInfo record = new StudentCourseRecordInfo();
        record.setTermId(termId);
        record.setPersonId(personId);
        record.setTypeKey(typeKey);

        StudentCourseRecordInfo created;
        try {
            created = service.createStudentCourseRecord(record.getTypeKey(), record.getPersonId(), record,
                    contextInfo);
        } catch (Exception ex) {
            throw new RuntimeException (ex);
        } 
        return created;
    }

}
