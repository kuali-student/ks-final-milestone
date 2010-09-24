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

public class GpaContextImplTest {
	private GpaContextImpl gpaContext = new GpaContextImpl();
	private ReqComponent reqComponent1;

	private void setupReqComponent1() {
		reqComponent1 = new ReqComponent();
        List<ReqComponentField> reqCompFieldList = new ArrayList<ReqComponentField>();
        ReqComponentField reqCompField1 = new ReqComponentField();
        reqCompField1.setType(ReqComponentFieldTypes.GPA_KEY.getId());
        reqCompField1.setValue("2.75");
        reqCompFieldList.add(reqCompField1);
		reqComponent1.setReqComponentFields(reqCompFieldList);
	}

	@Before
	public void beforeMethod() {
		setupReqComponent1();
	}

	@Test
    public void testCreateContextMap() throws OperationFailedException {
		Map<String, Object> contextMap = gpaContext.createContextMap(reqComponent1);
		Double gpa = (Double) contextMap.get(GpaContextImpl.GPA_TOKEN);
		Assert.assertEquals(2.75d, gpa.doubleValue(), 0d);
    }
}
