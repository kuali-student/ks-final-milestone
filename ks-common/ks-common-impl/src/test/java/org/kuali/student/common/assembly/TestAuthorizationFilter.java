package org.kuali.student.common.assembly;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.kuali.rice.core.util.AttributeSet;
import org.kuali.rice.kim.api.services.IdentityManagementService;
import org.kuali.rice.kim.bo.role.dto.KimPermissionInfo;
import org.kuali.student.common.assembly.data.Data;
import org.kuali.student.common.assembly.data.Metadata;
import org.kuali.student.common.assembly.dictionary.MetadataServiceImpl;
import org.kuali.student.common.assembly.dictionary.MockDictionaryService;
import org.kuali.student.common.assembly.transform.AuthorizationFilter;
import org.kuali.student.common.assembly.transform.AuthorizationFilter.Permission;
import org.kuali.student.common.assembly.transform.MetadataFilter;
import org.kuali.student.common.dictionary.service.impl.DictionaryServiceImpl;
import org.kuali.student.common.rice.StudentWorkflowConstants;
import org.kuali.student.common.test.mock.MockProxy;

public class TestAuthorizationFilter {
	final Logger LOG = Logger.getLogger(TestAuthorizationFilter.class);
	
	private static final String SIMPLE_STUDENT = "simpleStudent";
	
	DictionaryServiceImpl dictionaryDelegate = new DictionaryServiceImpl("classpath:test-validator-context.xml");
	Map<String, Object> methodReturnMap;
	IdentityManagementService mockPermissionService;
	MetadataServiceImpl metadataService;
	Map<String, Object> authzFilterProperties;
	AuthorizationFilter authzFilter;
	
	@Before
	public void setup() throws Exception{
		//Setup mock permission service
		methodReturnMap = new HashMap<String, Object>();
		mockPermissionService = (IdentityManagementService)MockProxy.newInstance(methodReturnMap, IdentityManagementService.class);
		
		//Setup metadata service
		MockDictionaryService mockDictionaryService = new MockDictionaryService();
		mockDictionaryService.setDictionaryServiceDelegate(dictionaryDelegate);
		metadataService = new MetadataServiceImpl(mockDictionaryService);

		//Setup authz filter properties
		authzFilterProperties = new HashMap<String, Object>();
		authzFilterProperties.put(MetadataFilter.METADATA_ID_TYPE, "objectId");
		authzFilterProperties.put(MetadataFilter.METADATA_ID_VALUE, "123456789");
        authzFilterProperties.put(AuthorizationFilter.DOC_LEVEL_PERM_CHECK, "true");
        authzFilterProperties.put(StudentWorkflowConstants.WORKFLOW_DOCUMENT_TYPE, "Dummy Document Type");
        
        //Setup authz filter
		authzFilter = new AuthorizationFilter();
		authzFilter.setPermissionService(mockPermissionService);        
	}
	
	@Test
	public void testEditPermissions() throws Exception{
	
		//Check default permissions as defined in dictionary
		Metadata metadata = metadataService.getMetadata(SIMPLE_STUDENT);		
		Map<String, Metadata> properties = metadata.getProperties();
		
		assertTrue(metadata.isCanEdit());
		assertTrue(((Metadata)properties.get("firstName")).isCanEdit());
		assertFalse(((Metadata)properties.get("dob")).isCanEdit());
		assertFalse(((Metadata)properties.get("ssn")).isCanEdit());
		assertTrue(((Metadata)properties.get("gpa")).isCanEdit());

		
		//Check edit permission when user not authorized to edit document
		methodReturnMap.put("isAuthorizedByTemplateName", new Boolean(false));
		metadata = metadataService.getMetadata(SIMPLE_STUDENT);
		authzFilter.applyMetadataFilter(SIMPLE_STUDENT, metadata, authzFilterProperties);
		
		properties = metadata.getProperties();
		assertFalse(metadata.isCanEdit());
		assertFalse(((Metadata)properties.get("firstName")).isCanEdit());
		assertFalse(((Metadata)properties.get("dob")).isCanEdit());
		assertFalse(((Metadata)properties.get("ssn")).isCanEdit());
		assertFalse(((Metadata)properties.get("gpa")).isCanEdit());
		
		
		//Check individual field edit permission for dob edit access
		methodReturnMap.put("isAuthorizedByTemplateName", new Boolean(true));
		methodReturnMap.put("getAuthorizedPermissionsByTemplateName", getDobEditPermission());
		
		metadata = metadataService.getMetadata(SIMPLE_STUDENT);
		authzFilter.applyMetadataFilter(SIMPLE_STUDENT, metadata, authzFilterProperties);

		properties = metadata.getProperties();
		assertTrue(metadata.isCanEdit());
		assertTrue(((Metadata)properties.get("firstName")).isCanEdit());
		assertTrue(((Metadata)properties.get("dob")).isCanEdit());
		assertFalse(((Metadata)properties.get("ssn")).isCanEdit());
		assertTrue(((Metadata)properties.get("gpa")).isCanEdit());					
	}
	
	@Test
	public void testMaskPermissions() throws Exception{
		Metadata metadata = metadataService.getMetadata(SIMPLE_STUDENT);		
		
		//Check default masking as defined in the dictionary
		Map<String, Metadata> properties = metadata.getProperties();
		
		assertFalse(((Metadata)properties.get("ssn")).isCanEdit());
		assertTrue(StringUtils.isNotEmpty(((Metadata)properties.get("ssn")).getPartialMaskFormatter()));
		assertTrue(StringUtils.isNotEmpty(((Metadata)properties.get("ssn")).getMaskFormatter()));
		
		//Check to see if default masks are applied correctly
		Data studentData = getStudentData();
		authzFilter.applyOutboundDataFilter(studentData, metadata, authzFilterProperties);
		assertEquals("Jon", studentData.get("firstName"));
		assertEquals("*********", studentData.get("ssn"));
		
		//Check to see partial unmask permission applied correctly
		methodReturnMap.put("isAuthorizedByTemplateName", new Boolean(true));		
		methodReturnMap.put("getAuthorizedPermissionsByTemplateName", getSsnMaskPermission(Permission.PARTIAL_UNMASK));
		metadata = metadataService.getMetadata(SIMPLE_STUDENT);
		authzFilter.applyMetadataFilter(SIMPLE_STUDENT, metadata, authzFilterProperties);

		studentData = getStudentData();
		authzFilter.applyOutboundDataFilter(studentData, metadata, authzFilterProperties);
		assertEquals("*****6789", studentData.get("ssn"));
		
	}
	
	public List<KimPermissionInfo> getDobEditPermission(){
		List<KimPermissionInfo> permList = new ArrayList<KimPermissionInfo>();
		
		KimPermissionInfo dobEditPerm = new KimPermissionInfo();
		dobEditPerm.setDetails(new AttributeSet());
		dobEditPerm.getDetails().put("dtoFieldKey", "dob");
		dobEditPerm.getDetails().put("fieldAccessLevel", Permission.EDIT.toString());
		
		permList.add(dobEditPerm);
		return permList;
	}

	public List<KimPermissionInfo> getSsnMaskPermission(Permission perm){
		List<KimPermissionInfo> permList = new ArrayList<KimPermissionInfo>();
		
		KimPermissionInfo dobEditPerm = new KimPermissionInfo();
		dobEditPerm.setDetails(new AttributeSet());
		dobEditPerm.getDetails().put("dtoFieldKey", "ssn");
		dobEditPerm.getDetails().put("fieldAccessLevel", perm.toString());
		
		permList.add(dobEditPerm);
		return permList;
	}

	public Data getStudentData(){
		Data data = new Data();
		data.set("firstName", "Jon");
		data.set("ssn", "123456789");
		return data;
	}
}
