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
import org.kuali.student.lum.statement.typekey.ReqComponentFieldTypes;

public class BasicContextImplTest {
	private BasicContextImpl basicContext = new BasicContextImpl();
	private ReqComponent reqComponent1;
	private ReqComponent reqComponent2;
	
	private void setupReqComponent1() {
		reqComponent1 = new ReqComponent();
        List<ReqComponentField> reqCompFieldList = new ArrayList<ReqComponentField>();
        ReqComponentField reqCompField1 = new ReqComponentField();
        reqCompField1.setType(ReqComponentFieldTypes.OPERATOR_KEY.getId());
        reqCompField1.setValue(">");
        reqCompFieldList.add(reqCompField1);
        ReqComponentField reqCompField2 = new ReqComponentField();
        reqCompField2.setType(ReqComponentFieldTypes.INTEGER_VALUE1_KEY.getId());
        reqCompField2.setValue("1");
        reqCompFieldList.add(reqCompField2);
		reqComponent1.setReqComponentFields(reqCompFieldList);
	}

	private void setupReqComponent2() {
		reqComponent2 = new ReqComponent();
        List<ReqComponentField> reqCompFieldList = new ArrayList<ReqComponentField>();
        ReqComponentField reqCompField1 = new ReqComponentField();
        reqCompField1.setType(ReqComponentFieldTypes.INTEGER_VALUE1_KEY.getId());
        reqCompField1.setValue("1");
        reqCompFieldList.add(reqCompField1);
		reqComponent2.setReqComponentFields(reqCompFieldList);
	}
	
	@Before
	public void beforeMethod() {
		setupReqComponent1();
		setupReqComponent2();
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
    public void testCreateContextMap_CheckTokenValueDataType_Integer() throws OperationFailedException {
		Map<String, Object> contextMap = basicContext.createContextMap(reqComponent2);
		Object value = contextMap.get(BasicContextImpl.INTEGER_VALUE_TOKEN);
		
		Assert.assertEquals(Integer.class.getName(), value.getClass().getName());
		Assert.assertEquals("1", value.toString());
    }
}
