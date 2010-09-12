package org.kuali.student.lum.statement.config.context;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.kuali.student.core.exceptions.OperationFailedException;
import org.kuali.student.core.statement.entity.ReqComponent;
import org.kuali.student.core.statement.entity.ReqComponentField;
import org.kuali.student.lum.statement.config.context.util.NLHelper;
import org.kuali.student.lum.statement.typekey.ReqComponentFieldTypes;

public class BasicContextImplTest {
	private BasicContextImpl basicContext = new BasicContextImpl();
	private ReqComponent reqComponent1;
	private ReqComponent reqComponent2;
	private ReqComponent reqComponent3;
	
	private void setupReqComponent1() {
		reqComponent1 = new ReqComponent();
        List<ReqComponentField> reqCompFieldList = new ArrayList<ReqComponentField>();
        ReqComponentField reqCompField1 = new ReqComponentField();
        reqCompField1.setType(ReqComponentFieldTypes.OPERATOR_KEY.getId());
        reqCompField1.setValue(">");
        reqCompFieldList.add(reqCompField1);
        ReqComponentField reqCompField2 = new ReqComponentField();
        reqCompField2.setType(ReqComponentFieldTypes.VALUE_DATA_TYPE_KEY.getId());
        reqCompField2.setValue("String");
        reqCompFieldList.add(reqCompField2);
        ReqComponentField reqCompField3 = new ReqComponentField();
        reqCompField3.setType(ReqComponentFieldTypes.VALUE_KEY.getId());
        reqCompField3.setValue("1.23");
        reqCompFieldList.add(reqCompField3);
		reqComponent1.setReqComponentFields(reqCompFieldList);
	}

	private void setupReqComponent2() {
		reqComponent2 = new ReqComponent();
        List<ReqComponentField> reqCompFieldList = new ArrayList<ReqComponentField>();
        ReqComponentField reqCompField2 = new ReqComponentField();
        reqCompField2.setType(ReqComponentFieldTypes.VALUE_DATA_TYPE_KEY.getId());
        reqCompField2.setValue("Integer");
        reqCompFieldList.add(reqCompField2);
        ReqComponentField reqCompField3 = new ReqComponentField();
        reqCompField3.setType(ReqComponentFieldTypes.VALUE_KEY.getId());
        reqCompField3.setValue("1");
        reqCompFieldList.add(reqCompField3);
		reqComponent2.setReqComponentFields(reqCompFieldList);
	}

	private void setupReqComponent3() {
		reqComponent3 = new ReqComponent();
        List<ReqComponentField> reqCompFieldList = new ArrayList<ReqComponentField>();
        ReqComponentField reqCompField2 = new ReqComponentField();
        reqCompField2.setType(ReqComponentFieldTypes.VALUE_DATA_TYPE_KEY.getId());
        reqCompField2.setValue("Double");
        reqCompFieldList.add(reqCompField2);
        ReqComponentField reqCompField3 = new ReqComponentField();
        reqCompField3.setType(ReqComponentFieldTypes.VALUE_KEY.getId());
        reqCompField3.setValue("1.23");
        reqCompFieldList.add(reqCompField3);
		reqComponent3.setReqComponentFields(reqCompFieldList);
	}
	
	@Before
	public void beforeMethod() {
		setupReqComponent1();
		setupReqComponent2();
		setupReqComponent3();
	}
	
	@Test
    public void testCreateContextMap() throws OperationFailedException {
		Map<String, Object> contextMap = basicContext.createContextMap(reqComponent1);
		Assert.assertNotNull(contextMap);
		Assert.assertTrue(contextMap.containsKey(BasicContextImpl.NL_HELPER_TOKEN));
		Assert.assertTrue(contextMap.containsKey(BasicContextImpl.OPERATOR_TOKEN));
		Assert.assertTrue(contextMap.containsKey(BasicContextImpl.VALUE_DATA_TYPE_TOKEN));
		Assert.assertTrue(contextMap.containsKey(BasicContextImpl.VALUE_TOKEN));
    }

	@Test
    public void testCreateContextMap_CheckTokenValues() throws OperationFailedException {
		Map<String, Object> contextMap = basicContext.createContextMap(reqComponent1);
		@SuppressWarnings("unchecked")
		Class<NLHelper> nlHelper = (Class<NLHelper>) contextMap.get(BasicContextImpl.NL_HELPER_TOKEN);
		String operator = (String) contextMap.get(BasicContextImpl.OPERATOR_TOKEN);
		String valueDataType = (String) contextMap.get(BasicContextImpl.VALUE_DATA_TYPE_TOKEN);
		Object value = contextMap.get(BasicContextImpl.VALUE_TOKEN);
		
		Assert.assertEquals(Class.class.getName(), nlHelper.getClass().getName());
		Assert.assertEquals(">", operator);
		Assert.assertEquals("String", valueDataType);
		Assert.assertEquals("java.lang.String", value.getClass().getName());
		Assert.assertEquals("1.23", value);
    }

	@Test
    public void testCreateContextMap_CheckTokenValueDataType_Integer() throws OperationFailedException {
		Map<String, Object> contextMap = basicContext.createContextMap(reqComponent2);
		String valueDataType = (String) contextMap.get(BasicContextImpl.VALUE_DATA_TYPE_TOKEN);
		Object value = contextMap.get(BasicContextImpl.VALUE_TOKEN);
		
		Assert.assertEquals("Integer", valueDataType);
		Assert.assertEquals(Integer.class.getName(), value.getClass().getName());
		Assert.assertEquals("1", value.toString());
    }

	@Test
    public void testCreateContextMap_CheckTokenValueDataType_Double() throws OperationFailedException {
		Map<String, Object> contextMap = basicContext.createContextMap(reqComponent3);
		String valueDataType = (String) contextMap.get(BasicContextImpl.VALUE_DATA_TYPE_TOKEN);
		Object value = contextMap.get(BasicContextImpl.VALUE_TOKEN);
		
		Assert.assertEquals("Double", valueDataType);
		Assert.assertEquals(Double.class.getName(), value.getClass().getName());
		Assert.assertEquals("1.23", value.toString());
    }
}
