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

import org.kuali.student.common.test.spring.DaoLoader;

/**
 * This is test dao loader for MyDao 
 * 
 * @author Kuali Rice Team (kuali-rice@googlegroups.com)
 *
 */
@Deprecated
public class MyDaoLoader extends DaoLoader {

    /**
     * This overridden method populates the dao
     * 
     * @see org.kuali.student.common.test.spring.DaoLoader#run()
     */
    @Override
    public void run() {
        MyDao myDao = (MyDao)this.getDao();
        
        Value value=new Value("loaded-value");
        myDao.createValue(value);
    }

}
