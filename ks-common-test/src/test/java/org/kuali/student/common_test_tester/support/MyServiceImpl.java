package org.kuali.student.common_test_tester.support;

import javax.jws.WebService;

import org.springframework.transaction.annotation.Transactional;

@WebService(endpointInterface = "org.kuali.student.common_test_tester.support.MyService", serviceName = "MyService", portName = "MyService", targetNamespace = "http://student.kuali.org/poc/wsdl/test/my")
@Transactional
public class MyServiceImpl implements MyService {

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
		System.out.println("######==" + dao0);
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
