package org.kuali.student.lum.statement.config.context;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.kuali.student.common.exceptions.OperationFailedException;
import org.kuali.student.core.statement.dto.ReqCompFieldInfo;
import org.kuali.student.core.statement.dto.ReqComponentInfo;
import org.kuali.student.lum.statement.typekey.ReqComponentFieldTypes;

public class BasicContextImplTest {
	private BasicContextImpl basicContext = new BasicContextImpl();
	private ReqComponentInfo reqComponent1;
	private ReqComponentInfo reqComponent2;
	private ReqComponentInfo reqComponent3;
	private ReqComponentInfo reqComponent4;
	
	private void setupReqComponent1() {
		reqComponent1 = new ReqComponentInfo();
        List<ReqCompFieldInfo> reqCompFieldList = new ArrayList<ReqCompFieldInfo>();
        ReqCompFieldInfo reqCompField1 = new ReqCompFieldInfo();
        reqCompField1.setType(ReqComponentFieldTypes.OPERATOR_KEY.getId());
        reqCompField1.setValue(">");
        reqCompFieldList.add(reqCompField1);
        ReqCompFieldInfo reqCompField2 = new ReqCompFieldInfo();
        reqCompField2.setType(ReqComponentFieldTypes.INTEGER_VALUE1_KEY.getId());
        reqCompField2.setValue("1");
        reqCompFieldList.add(reqCompField2);
		reqComponent1.setReqCompFields(reqCompFieldList);
	}

	private void setupReqComponent2() {
		reqComponent2 = new ReqComponentInfo();
        List<ReqCompFieldInfo> reqCompFieldList = new ArrayList<ReqCompFieldInfo>();
        ReqCompFieldInfo reqCompField1 = new ReqCompFieldInfo();
        reqCompField1.setType(ReqComponentFieldTypes.INTEGER_VALUE1_KEY.getId());
        reqCompField1.setValue("1");
        reqCompFieldList.add(reqCompField1);
		reqComponent2.setReqCompFields(reqCompFieldList);
	}
	
	private void setupReqComponent3() {
		reqComponent3 = new ReqComponentInfo();
        List<ReqCompFieldInfo> reqCompFieldList = new ArrayList<ReqCompFieldInfo>();
        ReqCompFieldInfo reqCompField1 = new ReqCompFieldInfo();
        reqCompField1.setType(ReqComponentFieldTypes.INTEGER_VALUE1_KEY.getId());
        reqCompField1.setValue(null);
        reqCompFieldList.add(reqCompField1);
		reqComponent3.setReqCompFields(reqCompFieldList);
	}
	
	private void setupReqComponent4() {
		reqComponent4 = new ReqComponentInfo();
        List<ReqCompFieldInfo> reqCompFieldList = new ArrayList<ReqCompFieldInfo>();
        ReqCompFieldInfo reqCompField1 = new ReqCompFieldInfo();
        reqCompField1.setType(ReqComponentFieldTypes.OPERATOR_KEY.getId());
        reqCompField1.setValue(null);
        reqCompFieldList.add(reqCompField1);
        ReqCompFieldInfo reqCompField2 = new ReqCompFieldInfo();
        reqCompField2.setType(ReqComponentFieldTypes.INTEGER_VALUE1_KEY.getId());
        reqCompField2.setValue(null);
        reqCompFieldList.add(reqCompField2);
		reqComponent4.setReqCompFields(reqCompFieldList);
	}
	
	@Before
	public void beforeMethod() {
		setupReqComponent1();
		setupReqComponent2();
		setupReqComponent3();
		setupReqComponent4();
	}
	
	@Test
    public void testCreateContextMap() throws OperationFailedException {
		Map<String, Object> contextMap = basicContext.createContextMap(reqComponent1);
		Assert.assertNotNull(contextMap);
		Assert.assertTrue(contextMap.containsKey(BasicContextImpl.NL_HELPER_TOKEN));
		Assert.assertTrue(contextMap.containsKey(BasicContextImpl.OPERATOR_TOKEN));
		Assert.assertTrue(contextMap.containsKey(BasicContextImpl.INTEGER_VALUE_TOKEN));
    }

	@Test
    public void testCreateContextMap_NullTokenValues() throws OperationFailedException {
		Map<String, Object> contextMap = basicContext.createContextMap(reqComponent4);
		Assert.assertNotNull(contextMap);
		Assert.assertFalse(contextMap.containsKey(BasicContextImpl.OPERATOR_TOKEN));
		Assert.assertFalse(contextMap.containsKey(BasicContextImpl.INTEGER_VALUE_TOKEN));
    }

	@Test
    public void testCreateContextMap_CheckTokenValueDataType_Integer() throws OperationFailedException {
		Map<String, Object> contextMap = basicContext.createContextMap(reqComponent2);
		Object value = contextMap.get(BasicContextImpl.INTEGER_VALUE_TOKEN);
		
		Assert.assertEquals(Integer.class.getName(), value.getClass().getName());
		Assert.assertEquals("1", value.toString());
    }

	@Test
    public void testCreateContextMap_CheckTokenValueDataType_NullInteger() throws OperationFailedException {
		Map<String, Object> contextMap = basicContext.createContextMap(reqComponent3);
		Object value = contextMap.get(BasicContextImpl.INTEGER_VALUE_TOKEN);
		
		Assert.assertEquals(null, value);
    }
}
