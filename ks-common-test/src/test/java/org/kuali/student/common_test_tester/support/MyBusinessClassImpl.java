package org.kuali.student.common_test_tester.support;

import org.springframework.transaction.annotation.Transactional;

@Transactional
public class MyBusinessClassImpl implements MyBusinessClass{
	private MyDao myDao;
	private OtherDao otherDao;
	
	public int doBusinessThing(){
		myDao.createValue(new Value("lalala"));
		otherDao.foo();
		if(myDao.findValueFromValue("Value Number One")!=null){
			return 1;
		}
		return 0;
	}
	
	/**
	 * @return the myDao
	 */
	public MyDao getMyDao() {
		return myDao;
	}
	/**
	 * @param myDao the myDao to set
	 */
	public void setMyDao(MyDao myDao) {
		this.myDao = myDao;
	}
	/**
	 * @return the otherDao
	 */
	public OtherDao getOtherDao() {
		return otherDao;
	}
	/**
	 * @param otherDao the otherDao to set
	 */
	public void setOtherDao(OtherDao otherDao) {
		this.otherDao = otherDao;
	}
}
