package org.kuali.student.r1.common.assembly;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.kuali.rice.kim.api.common.template.Template;
import org.kuali.rice.kim.api.permission.Permission;
import org.kuali.rice.kim.api.permission.PermissionService;
import org.kuali.student.r1.common.assembly.data.Data;
import org.kuali.student.r1.common.assembly.data.Metadata;
import org.kuali.student.r1.common.assembly.dictionary.MetadataServiceImpl;
import org.kuali.student.r1.common.assembly.dictionary.MockDictionaryService;
import org.kuali.student.r1.common.assembly.transform.AuthorizationFilter;
import org.kuali.student.r1.common.assembly.transform.MetadataFilter;
import org.kuali.student.r1.common.assembly.transform.AuthorizationFilter.PermissionEnum;
import org.kuali.student.r1.common.dictionary.service.impl.DictionaryServiceImpl;
import org.kuali.student.r1.common.rice.StudentWorkflowConstants;
import org.kuali.student.common.test.mock.MockProxy;

public class TestAuthorizationFilter {
	final Logger LOG = Logger.getLogger(TestAuthorizationFilter.class);
	
	private static final String SIMPLE_STUDENT = "simpleStudent";
	
	DictionaryServiceImpl dictionaryDelegate = new DictionaryServiceImpl("classpath:test-validator-context.xml");
	Map<String, Object> methodReturnMap;
	PermissionService mockPermissionService;
	MetadataServiceImpl metadataService;
	Map<String, Object> authzFilterProperties;
	AuthorizationFilter authzFilter;
	
	@Before
	public void setup() throws Exception{
		//Setup mock permission service
		methodReturnMap = new HashMap<String, Object>();
		mockPermissionService = (PermissionService)MockProxy.newInstance(methodReturnMap, PermissionService.class);
		
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
		methodReturnMap.put("isAuthorizedByTemplate", new Boolean(false));
		metadata = metadataService.getMetadata(SIMPLE_STUDENT);
		authzFilter.applyMetadataFilter(SIMPLE_STUDENT, metadata, authzFilterProperties);
		
		properties = metadata.getProperties();
		assertFalse(metadata.isCanEdit());
		assertFalse(((Metadata)properties.get("firstName")).isCanEdit());
		assertFalse(((Metadata)properties.get("dob")).isCanEdit());
		assertFalse(((Metadata)properties.get("ssn")).isCanEdit());
		assertFalse(((Metadata)properties.get("gpa")).isCanEdit());
		
		
		//Check individual field edit permission for dob edit access
		methodReturnMap.put("isAuthorizedByTemplate", new Boolean(true));
		methodReturnMap.put("getAuthorizedPermissionsByTemplate", getDobEditPermission());
		
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
		methodReturnMap.put("isAuthorizedByTemplate", new Boolean(true));		
		methodReturnMap.put("getAuthorizedPermissionsByTemplate", getSsnMaskPermission(PermissionEnum.PARTIAL_UNMASK));
		metadata = metadataService.getMetadata(SIMPLE_STUDENT);
		authzFilter.applyMetadataFilter(SIMPLE_STUDENT, metadata, authzFilterProperties);

		studentData = getStudentData();
		authzFilter.applyOutboundDataFilter(studentData, metadata, authzFilterProperties);
		assertEquals("*****6789", studentData.get("ssn"));
		
	}
	
	public List<Permission> getDobEditPermission(){
		List<Permission> permList = new ArrayList<Permission>();
		String namespaceCode = "test-namespace";
        String permissionName = "test-permission-name";
        String templateName = "test-templateName";
        String kimTypeId = "test-kimTypeId";
                       
        Template.Builder template = Template.Builder.create (namespaceCode, templateName, kimTypeId);
		Permission.Builder dobEditPerm = Permission.Builder.create (namespaceCode, permissionName);
        dobEditPerm.setTemplate(template);
        Map<String,String> attrs = new LinkedHashMap <String,String> ();
		attrs.put("dtoFieldKey", "dob");
		attrs.put("fieldAccessLevel", PermissionEnum.EDIT.toString());                
		dobEditPerm.setAttributes(attrs);		
		permList.add(dobEditPerm.build());
		return permList;
	}

	public List<Permission> getSsnMaskPermission(PermissionEnum perm){
		List<Permission> permList = new ArrayList<Permission>();

		String namespaceCode = "test-namespace";
        String permissionName = "test-permission-name";
        String templateName = "test-templateName";
        String kimTypeId = "test-kimTypeId";
                
        Template.Builder template = Template.Builder.create (namespaceCode, templateName, kimTypeId);
		Permission.Builder dobEditPerm = Permission.Builder.create (namespaceCode, permissionName);
        dobEditPerm.setTemplate(template);
        Map<String,String> attrs = new LinkedHashMap <String,String> ();
		attrs.put("dtoFieldKey", "ssn");
		attrs.put("fieldAccessLevel", perm.toString());
		dobEditPerm.setAttributes(attrs);		
		permList.add(dobEditPerm.build());
		return permList;
	}

	public Data getStudentData(){
		Data data = new Data();
		data.set("firstName", "Jon");
		data.set("ssn", "123456789");
		return data;
	}
}
