/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kuali.student.kim.identity.mock;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.kuali.rice.kim.bo.entity.dto.KimEntityDefaultInfo;
import org.kuali.rice.kim.bo.entity.dto.KimEntityInfo;
import org.kuali.rice.kim.bo.entity.dto.KimEntityPrivacyPreferencesInfo;
import org.kuali.rice.kim.bo.entity.dto.KimPrincipalInfo;
import org.kuali.rice.kim.bo.reference.dto.AddressTypeInfo;
import org.kuali.rice.kim.bo.reference.dto.AffiliationTypeInfo;
import org.kuali.rice.kim.bo.reference.dto.EmailTypeInfo;
import org.kuali.rice.kim.bo.reference.dto.EmploymentStatusInfo;
import org.kuali.rice.kim.bo.reference.dto.EmploymentTypeInfo;
import org.kuali.rice.kim.bo.reference.dto.EntityNameTypeInfo;
import org.kuali.rice.kim.bo.reference.dto.EntityTypeInfo;
import org.kuali.rice.kim.bo.reference.dto.ExternalIdentifierTypeInfo;
import org.kuali.rice.kim.bo.reference.dto.PhoneTypeInfo;
import org.kuali.student.kim.identity.mock.AddressTypeEnum;
import org.kuali.student.kim.identity.mock.AffiliationTypeEnum;
import org.kuali.student.kim.identity.mock.EmailTypeEnum;
import org.kuali.student.kim.identity.mock.EmployeeStatusEnum;
import org.kuali.student.kim.identity.mock.EmployeeTypeEnum;
import org.kuali.student.kim.identity.mock.EntityNameTypeEnum;
import org.kuali.student.kim.identity.mock.EntityTypeEnum;
import org.kuali.student.kim.identity.mock.ExternalIdentifierTypeEnum;
import org.kuali.student.kim.identity.mock.IdentityServiceConstants;
import org.kuali.student.kim.identity.mock.PersonEnum;
import org.kuali.student.kim.identity.mock.PhoneTypeEnum;

/**
 *
 * @author nwright
 */
public class TestIdentityServiceMockImpl {

    public TestIdentityServiceMockImpl() {
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

    /**
     * Test of getAddressType method, of class MockIdentityServiceImpl.
     */
    @Test
    public void testGetAddressType() {
        System.out.println("getAddressType");
        String code = AddressTypeEnum.HOME.getCode();
        IdentityServiceMockImpl instance = new IdentityServiceMockImpl();
        AddressTypeInfo result = instance.getAddressType(code);
        assertEquals(code, result.getCode());
    }

    /**
     * Test of getAffiliationType method, of class MockIdentityServiceImpl.
     */
    @Test
    public void testGetAffiliationType() {
        System.out.println("getAffiliationType");
        String code = AffiliationTypeEnum.STAFF.getCode();
        IdentityServiceMockImpl instance = new IdentityServiceMockImpl();
        AffiliationTypeInfo result = instance.getAffiliationType(code);
        assertEquals(code, result.getCode());

    }

    /**
     * Test of getCitizenshipStatus method, of class MockIdentityServiceImpl.
     */
    @Test
    public void testGetCitizenshipStatus() {
        System.out.println("getCitizenshipStatus");
//        String code = "";
//        MockIdentityServiceImpl instance = new MockIdentityServiceImpl();
//        CitizenshipStatusInfo expResult = null;
//        CitizenshipStatusInfo result = instance.getCitizenshipStatus(code);
//        assertEquals(expResult, result);
    }

    /**
     * Test of getDefaultNamesForEntityIds method, of class MockIdentityServiceImpl.
     */
    @Test
    public void testGetDefaultNamesForEntityIds() {
        System.out.println("getDefaultNamesForEntityIds");
        List<String> entityIds = Arrays.asList(PersonEnum.STAFF1.getEntityId(), PersonEnum.STUDENT10.getEntityId());
        IdentityServiceMockImpl instance = new IdentityServiceMockImpl();
        Map result = instance.getDefaultNamesForEntityIds(entityIds);
        assertEquals(2, result.size());
    }

    /**
     * Test of getDefaultNamesForPrincipalIds method, of class MockIdentityServiceImpl.
     */
    @Test
    public void testGetDefaultNamesForPrincipalIds() {
        System.out.println("getDefaultNamesForPrincipalIds");
        List<String> principalIds = Arrays.asList(PersonEnum.STAFF1.getPrincipalId(), PersonEnum.STUDENT10.getPrincipalId());
        IdentityServiceMockImpl instance = new IdentityServiceMockImpl();
        Map result = instance.getDefaultNamesForPrincipalIds(principalIds);
        assertEquals(2, result.size());

    }

    /**
     * Test of getEmailType method, of class MockIdentityServiceImpl.
     */
    @Test
    public void testGetEmailType() {
        System.out.println("getEmailType");
        String code = EmailTypeEnum.HOME.getCode();
        IdentityServiceMockImpl instance = new IdentityServiceMockImpl();
        EmailTypeInfo result = instance.getEmailType(code);
        assertEquals(code, result.getCode());
    }

    /**
     * Test of getEmploymentStatus method, of class MockIdentityServiceImpl.
     */
    @Test
    public void testGetEmploymentStatus() {
        System.out.println("getEmploymentStatus");
        String code = EmployeeStatusEnum.ACTIVE.getCode();
        IdentityServiceMockImpl instance = new IdentityServiceMockImpl();
        EmploymentStatusInfo result = instance.getEmploymentStatus(code);
        assertEquals(code, result.getCode());
    }

    /**
     * Test of getEmploymentType method, of class MockIdentityServiceImpl.
     */
    @Test
    public void testGetEmploymentType() {
        System.out.println("getEmploymentType");
        String code = EmployeeTypeEnum.PROFESSIONAL.getCode();
        IdentityServiceMockImpl instance = new IdentityServiceMockImpl();
        EmploymentTypeInfo result = instance.getEmploymentType(code);
        assertEquals(code, result.getCode());
    }

    /**
     * Test of getEntityDefaultInfo method, of class MockIdentityServiceImpl.
     */
    @Test
    public void testGetEntityDefaultInfo() {
        System.out.println("getEntityDefaultInfo");
        String entityId = PersonEnum.STAFF1.getEntityId();
        IdentityServiceMockImpl instance = new IdentityServiceMockImpl();
        KimEntityDefaultInfo result = instance.getEntityDefaultInfo(entityId);
        assertEquals(entityId, result.getEntityId());
    }

    /**
     * Test of getEntityDefaultInfoByPrincipalId method, of class MockIdentityServiceImpl.
     */
    @Test
    public void testGetEntityDefaultInfoByPrincipalId() {
        System.out.println("getEntityDefaultInfoByPrincipalId");
        String principalId = PersonEnum.STUDENT10.getPrincipalId();
        IdentityServiceMockImpl instance = new IdentityServiceMockImpl();
        KimEntityDefaultInfo result = instance.getEntityDefaultInfoByPrincipalId(principalId);
        assertEquals(principalId, result.getPrincipals().get(0).getPrincipalId());
    }

    /**
     * Test of getEntityDefaultInfoByPrincipalName method, of class MockIdentityServiceImpl.
     */
    @Test
    public void testGetEntityDefaultInfoByPrincipalName() {
        System.out.println("getEntityDefaultInfoByPrincipalName");
        String principalName = PersonEnum.STUDENT10.getPrincipalName();
        IdentityServiceMockImpl instance = new IdentityServiceMockImpl();
        KimEntityDefaultInfo result = instance.getEntityDefaultInfoByPrincipalName(principalName);
        assertEquals(principalName, result.getPrincipals().get(0).getPrincipalName());
    }

    /**
     * Test of getEntityInfo method, of class MockIdentityServiceImpl.
     */
    @Test
    public void testGetEntityInfo() {
        System.out.println("getEntityInfo");
        String entityId = PersonEnum.FACULTY12.getEntityId();
        IdentityServiceMockImpl instance = new IdentityServiceMockImpl();
        KimEntityInfo result = instance.getEntityInfo(entityId);
        assertEquals(entityId, result.getEntityId());
    }

    /**
     * Test of getEntityInfoByPrincipalId method, of class MockIdentityServiceImpl.
     */
    @Test
    public void testGetEntityInfoByPrincipalId() {
        System.out.println("getEntityInfoByPrincipalId");
        String principalId = PersonEnum.FACULTY12.getPrincipalId();
        IdentityServiceMockImpl instance = new IdentityServiceMockImpl();
        KimEntityInfo result = instance.getEntityInfoByPrincipalId(principalId);
        assertEquals(principalId, result.getPrincipals().get(0).getPrincipalId());
    }

    /**
     * Test of getEntityInfoByPrincipalName method, of class MockIdentityServiceImpl.
     */
    @Test
    public void testGetEntityInfoByPrincipalName() {
        System.out.println("getEntityInfoByPrincipalName");
        String principalName = PersonEnum.FACULTY12.getPrincipalName();
        IdentityServiceMockImpl instance = new IdentityServiceMockImpl();
        KimEntityInfo result = instance.getEntityInfoByPrincipalName(principalName);
        assertEquals(principalName, result.getPrincipals().get(0).getPrincipalName());
    }

    /**
     * Test of getEntityNameType method, of class MockIdentityServiceImpl.
     */
    @Test
    public void testGetEntityNameType() {
        System.out.println("getEntityNameType");
        String code = EntityNameTypeEnum.PRIMARY.getCode();
        IdentityServiceMockImpl instance = new IdentityServiceMockImpl();
        EntityNameTypeInfo result = instance.getEntityNameType(code);
        assertEquals(code, result.getCode());
    }

    /**
     * Test of getEntityPrivacyPreferences method, of class MockIdentityServiceImpl.
     */
    @Test
    public void testGetEntityPrivacyPreferences() {
        System.out.println("getEntityPrivacyPreferences");
        String entityId = PersonEnum.AFFILIATE4.getEntityId();
        IdentityServiceMockImpl instance = new IdentityServiceMockImpl();
        KimEntityPrivacyPreferencesInfo result = instance.getEntityPrivacyPreferences(entityId);
        assertEquals(false, result.isSuppressAddress());
    }

    /**
     * Test of getEntityType method, of class MockIdentityServiceImpl.
     */
    @Test
    public void testGetEntityType() {
        System.out.println("getEntityType");
        String code = EntityTypeEnum.PERSON.getCode();
        IdentityServiceMockImpl instance = new IdentityServiceMockImpl();
        EntityTypeInfo result = instance.getEntityType(code);
        assertEquals(code, result.getCode());
    }

    /**
     * Test of getExternalIdentifierType method, of class MockIdentityServiceImpl.
     */
    @Test
    public void testGetExternalIdentifierType() {
        System.out.println("getExternalIdentifierType");
        String code = ExternalIdentifierTypeEnum.TAX.getCode();
        IdentityServiceMockImpl instance = new IdentityServiceMockImpl();
        ExternalIdentifierTypeInfo result = instance.getExternalIdentifierType(code);
        assertEquals(code, result.getCode());
    }

    /**
     * Test of getPhoneType method, of class MockIdentityServiceImpl.
     */
    @Test
    public void testGetPhoneType() {
        System.out.println("getPhoneType");
        String code = PhoneTypeEnum.MOBILE.getCode();
        IdentityServiceMockImpl instance = new IdentityServiceMockImpl();
        PhoneTypeInfo result = instance.getPhoneType(code);
        assertEquals(code, result.getCode());
    }

    /**
     * Test of getPrincipal method, of class MockIdentityServiceImpl.
     */
    @Test
    public void testGetPrincipal() {
        System.out.println("getPrincipal");
        String principalId = PersonEnum.STAFF2.getPrincipalId();
        IdentityServiceMockImpl instance = new IdentityServiceMockImpl();
        KimPrincipalInfo result = instance.getPrincipal(principalId);
        assertEquals(principalId, result.getPrincipalId());

    }

    /**
     * Test of getPrincipalByPrincipalName method, of class MockIdentityServiceImpl.
     */
    @Test
    public void testGetPrincipalByPrincipalName() {
        System.out.println("getPrincipalByPrincipalName");
        String principalName = PersonEnum.FACULTY3.getPrincipalName();
        IdentityServiceMockImpl instance = new IdentityServiceMockImpl();
        KimPrincipalInfo result = instance.getPrincipalByPrincipalName(principalName);
        assertEquals(principalName, result.getPrincipalName());
    }

    /**
     * Test of getPrincipalByPrincipalNameAndPassword method, of class MockIdentityServiceImpl.
     */
    @Test
    public void testGetPrincipalByPrincipalNameAndPassword() {
        System.out.println("getPrincipalByPrincipalNameAndPassword");
        String principalName = PersonEnum.FACULTY3.getPrincipalName();
        String password = PersonEnum.FACULTY3.getPassword();
        IdentityServiceMockImpl instance = new IdentityServiceMockImpl();
        KimPrincipalInfo result = instance.getPrincipalByPrincipalNameAndPassword(principalName, password);
        assertEquals(principalName, result.getPrincipalName());
        assertEquals(password, result.getPassword());
        assertNull(instance.getPrincipalByPrincipalNameAndPassword(principalName, password + "XXXXX"));
    }

    /**
     * Test of lookupEntityDefaultInfo method, of class MockIdentityServiceImpl.
     */
    @Test
    public void testLookupEntityDefaultInfo() {
        System.out.println("lookupEntityDefaultInfo");
        Map<String, String> searchCriteria = null;
        boolean unbounded = true;
        IdentityServiceMockImpl instance = new IdentityServiceMockImpl();

        // all
        searchCriteria = new HashMap<String, String>();
        unbounded = true;
        List<KimEntityDefaultInfo> results = instance.lookupEntityDefaultInfo(searchCriteria, unbounded);
        assertEquals(212, results.size());

        // bounded
        searchCriteria = new HashMap<String, String>();
        unbounded = false;
        results = instance.lookupEntityDefaultInfo(searchCriteria, unbounded);
        assertEquals(100, results.size());

        // just students
        searchCriteria = new HashMap<String, String>();
        searchCriteria.put(IdentityServiceConstants.KIM_PERSON_AFFILIATION_TYPE_CODE, AffiliationTypeEnum.STUDENT.getCode());
        unbounded = true;
        results = instance.lookupEntityDefaultInfo(searchCriteria, unbounded);
        assertEquals(80, results.size());

        // just faculty
        searchCriteria = new HashMap<String, String>();
        searchCriteria.put(IdentityServiceConstants.KIM_PERSON_AFFILIATION_TYPE_CODE, AffiliationTypeEnum.FACULTY.getCode());
        unbounded = true;
        results = instance.lookupEntityDefaultInfo(searchCriteria, unbounded);
        assertEquals(60, results.size());

        // just staff
        searchCriteria = new HashMap<String, String>();
        searchCriteria.put(IdentityServiceConstants.KIM_PERSON_AFFILIATION_TYPE_CODE, AffiliationTypeEnum.STAFF.getCode());
        unbounded = true;
        results = instance.lookupEntityDefaultInfo(searchCriteria, unbounded);
        assertEquals(62, results.size());

        // just affiliates
        searchCriteria = new HashMap<String, String>();
        searchCriteria.put(IdentityServiceConstants.KIM_PERSON_AFFILIATION_TYPE_CODE, AffiliationTypeEnum.AFFILIATE.getCode());
        unbounded = true;
        results = instance.lookupEntityDefaultInfo(searchCriteria, unbounded);
        assertEquals(10, results.size());

        // just students with name = "Manning"
        searchCriteria = new HashMap<String, String>();
        searchCriteria.put(IdentityServiceConstants.KIM_PERSON_AFFILIATION_TYPE_CODE, AffiliationTypeEnum.STUDENT.getCode());
        searchCriteria.put(IdentityServiceConstants.KIM_PERSON_LAST_NAME, "Manning");
        unbounded = true;
        results = instance.lookupEntityDefaultInfo(searchCriteria, unbounded);
        assertEquals(1, results.size());

        // just students with name like "Ma*"
        searchCriteria = new HashMap<String, String>();
        searchCriteria.put(IdentityServiceConstants.KIM_PERSON_AFFILIATION_TYPE_CODE, AffiliationTypeEnum.STUDENT.getCode());
        searchCriteria.put(IdentityServiceConstants.KIM_PERSON_LAST_NAME, "Ma*");
        unbounded = true;
        results = instance.lookupEntityDefaultInfo(searchCriteria, unbounded);
        for (KimEntityDefaultInfo info : results) {
            assertEquals(AffiliationTypeEnum.STUDENT.getCode(), info.getDefaultAffiliation().getAffiliationTypeCode());
            assertEquals("Ma", info.getDefaultName().getLastName().substring(0, 2));
        }
        assertEquals(3, results.size());
    }

    /**
     * Test of lookupEntityInfo method, of class MockIdentityServiceImpl.
     */
    @Test
    public void testLookupEntityInfo() {
        System.out.println("lookupEntityInfo");
        Map<String, String> searchCriteria = null;
        boolean unbounded = true;
        IdentityServiceMockImpl instance = new IdentityServiceMockImpl();

        // all
        searchCriteria = new HashMap<String, String>();
        unbounded = true;
        List<KimEntityInfo> results = instance.lookupEntityInfo(searchCriteria, unbounded);
        assertEquals(212, results.size());
    }

    /**
     * Test of getMatchingEntityCount method, of class MockIdentityServiceImpl.
     */
    @Test
    public void testGetMatchingEntityCount() {
        System.out.println("getMatchingEntityCount");
        Map<String, String> searchCriteria = null;
        IdentityServiceMockImpl instance = new IdentityServiceMockImpl();
        
        // all
        searchCriteria = new HashMap<String, String>();
        int results = instance.getMatchingEntityCount(searchCriteria);
        assertEquals(212, results);
    }
}
