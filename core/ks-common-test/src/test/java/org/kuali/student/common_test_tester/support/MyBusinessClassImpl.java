/*
 * Copyright 2009 The Kuali Foundation Licensed under the
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
