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

package org.kuali.student.core.enumerationmanagement.dao;

import java.util.Date;
import java.util.List;

import org.kuali.student.core.dao.CrudDao;
import org.kuali.student.core.dao.SearchableDao;
import org.kuali.student.core.enumerationmanagement.entity.EnumeratedValue;
import org.kuali.student.core.enumerationmanagement.entity.Enumeration;

public interface EnumerationManagementDAO extends CrudDao, SearchableDao{

    public List<Enumeration> findEnumerations();

    public Enumeration fetchEnumeration(String enumerationKey);
    
    public Enumeration addEnumeration(Enumeration entity);

    public boolean removeEnumeration(String enumerationKey);
    
    public List<EnumeratedValue> fetchEnumeratedValuesWithContextAndDate(String enumerationKey, String enumContextKey, String contextValue, Date contextDate);
   
    public List<EnumeratedValue> fetchEnumeratedValuesWithContext(String enumerationKey, String enumContextKey, String contextValue);
   
    public List<EnumeratedValue> fetchEnumeratedValuesWithDate(String enumerationKey, Date contextDate);
    
    public List<EnumeratedValue> fetchEnumeratedValues(String enumerationKey);
    
    public EnumeratedValue addEnumeratedValue(String enumerationKey, EnumeratedValue value);

    public EnumeratedValue updateEnumeratedValue(String enumerationKey, String code, EnumeratedValue value);

    public boolean removeEnumeratedValue(String enumerationKey, String code);

    
}
