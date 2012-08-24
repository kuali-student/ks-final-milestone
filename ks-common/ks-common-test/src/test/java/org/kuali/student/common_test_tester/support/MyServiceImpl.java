/**
 * Copyright 2010 The Kuali Foundation Licensed under the
 * Educational Community License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may
 * obtain a copy of the License at
 *
 * http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */

package org.kuali.student.common_test_tester.support;

import javax.jws.WebService;

import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Transactional;

@Deprecated
@WebService(endpointInterface = "org.kuali.student.common_test_tester.support.MyService", serviceName = "MyService", portName = "MyService", targetNamespace = "http://student.kuali.org/poc/wsdl/test/my")
@Transactional
public class MyServiceImpl implements MyService {
	final Logger LOG = Logger.getLogger(MyServiceImpl.class);
	private MyDao dao0;
	private OtherDao otherDao;
	private SomeClass myClass;

	/**
	 * @return the dao
	 */
	public MyDao getDao0() {
		return dao0;
	}

	/**
	 * @param dao
	 *            the dao to set
	 */
	public void setDao0(MyDao dao0) {
		this.dao0 = dao0;
	}

	/**
	 * @return the otherDao
	 */
	public OtherDao getOtherDao() {
		return otherDao;
	}

	/**
	 * @param otherDao
	 *            the otherDao to set
	 */
	public void setOtherDao(OtherDao otherDao) {
		this.otherDao = otherDao;
	}

	@Override
	public String saveString(String value) {
		if(myClass==null){
			throw new RuntimeException("ListIsNull");
		}
		LOG.info("######==" + dao0);
		if (dao0 == null) {
			return "";
		}
		String id = this.dao0.createValue(new Value(value));
		this.otherDao.foo();
		return id;
	}

	@Override
	public String findStringId(String id) {
		return this.dao0.findValue(id);
	}

	@Override
	public boolean updateValue(String id, String value) {
		return this.dao0.updateValue(id, value);
	}

	@Override
	public String findValueFromValue(String value) {
		return this.dao0.findValueFromValue(value).getValue();
	}

	public SomeClass getMyClass() {
		return myClass;
	}

	public void setMyClass(SomeClass myClass) {
		this.myClass = myClass;
	}

	@Override
	public String echo(String string) {
		return "Echo: "+ string;
	}

}
