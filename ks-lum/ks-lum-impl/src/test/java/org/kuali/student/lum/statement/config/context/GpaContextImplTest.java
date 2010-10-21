package org.kuali.student.lum.statement.config.context;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.kuali.student.core.exceptions.OperationFailedException;
import org.kuali.student.core.statement.dto.ReqCompFieldInfo;
import org.kuali.student.core.statement.dto.ReqComponentInfo;
import org.kuali.student.lum.statement.typekey.ReqComponentFieldTypes;

public class GpaContextImplTest {
	private GpaContextImpl gpaContext = new GpaContextImpl();
	private ReqComponentInfo reqComponent1;
	private ReqComponentInfo reqComponent2;

	private void setupReqComponent1() {
		reqComponent1 = new ReqComponentInfo();
        List<ReqCompFieldInfo> reqCompFieldList = new ArrayList<ReqCompFieldInfo>();
        ReqCompFieldInfo reqCompField1 = new ReqCompFieldInfo();
        reqCompField1.setType(ReqComponentFieldTypes.GPA_KEY.getId());
        reqCompField1.setValue("2.75");
        reqCompFieldList.add(reqCompField1);
		reqComponent1.setReqCompFields(reqCompFieldList);
	}

	private void setupReqComponent2() {
		reqComponent2 = new ReqComponentInfo();
        List<ReqCompFieldInfo> reqCompFieldList = new ArrayList<ReqCompFieldInfo>();
        ReqCompFieldInfo reqCompField1 = new ReqCompFieldInfo();
        reqCompField1.setType(ReqComponentFieldTypes.GPA_KEY.getId());
        reqCompField1.setValue(null);
        reqCompFieldList.add(reqCompField1);
        reqComponent2.setReqCompFields(reqCompFieldList);
	}

	@Before
	public void beforeMethod() {
		setupReqComponent1();
		setupReqComponent2();
	}

	@Test
    public void testCreateContextMap() throws OperationFailedException {
		Map<String, Object> contextMap = gpaContext.createContextMap(reqComponent1);
		Double gpa = (Double) contextMap.get(GpaContextImpl.GPA_TOKEN);
		Assert.assertEquals(2.75d, gpa.doubleValue(), 0d);
    }

	@Test
    public void testCreateContextMap_NullGpa() throws OperationFailedException {
		Map<String, Object> contextMap = gpaContext.createContextMap(reqComponent2);
		Double gpa = (Double) contextMap.get(GpaContextImpl.GPA_TOKEN);
		Assert.assertEquals(null, gpa);
    }
}
