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
package org.kuali.student.common.test.spring;

/**
 * Implement this class if you want to load data to a test db using a dao.
 * This is an alternative to using the dao data beans xml file. 
 * 
 *
 */
public abstract class DaoLoader {
    Object dao;
    
    public void setDao(Object dao){
        this.dao = dao;
    }
    
    public Object getDao(){
        return this.dao;
    }
    
    
    /**
     * Implement this method to load data into the db
     * 
     */
    public abstract void run();
}
