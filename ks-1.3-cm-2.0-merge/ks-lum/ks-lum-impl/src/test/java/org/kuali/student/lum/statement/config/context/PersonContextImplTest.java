package org.kuali.student.lum.statement.config.context;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r1.core.statement.dto.ReqCompFieldInfo;
import org.kuali.student.r1.core.statement.dto.ReqComponentInfo;
import org.kuali.student.r1.lum.statement.typekey.ReqComponentFieldTypes;
import org.kuali.student.r2.lum.statement.config.context.PersonContextImpl;

public class PersonContextImplTest {

	private PersonContextImpl personContext = new PersonContextImpl();

	private ReqComponentInfo reqComponent1;
	
	private void setupReqComponent1() {
		reqComponent1 = new ReqComponentInfo();
        List<ReqCompFieldInfo> reqCompFieldList = new ArrayList<ReqCompFieldInfo>();
        ReqCompFieldInfo reqCompField1 = new ReqCompFieldInfo();
        reqCompField1.setType(ReqComponentFieldTypes.PERSON_KEY.getId());
        reqCompField1.setValue("1234567890");
        reqCompFieldList.add(reqCompField1);
		reqComponent1.setReqCompFields(reqCompFieldList);
	}

	@Before
	public void beforeMethod() {
		setupReqComponent1();
	}

	@Test
    public void testCreateContextMap() throws OperationFailedException {
		Map<String, Object> contextMap = personContext.createContextMap(reqComponent1, new org.kuali.student.r2.common.dto.ContextInfo());

		Assert.assertNotNull(contextMap);
		Assert.assertEquals("1234567890", contextMap.get(PersonContextImpl.PERSON_TOKEN));
	}
}
