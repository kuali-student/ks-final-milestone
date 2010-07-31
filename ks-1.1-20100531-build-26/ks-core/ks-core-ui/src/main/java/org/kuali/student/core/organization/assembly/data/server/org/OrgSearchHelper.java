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

package org.kuali.student.core.organization.assembly.data.server.org;



import org.kuali.student.core.assembly.data.Data;
import org.kuali.student.core.assembly.helper.PropertyEnum;

public class OrgSearchHelper {
    private static final long serialVersionUID = 1L;

    public enum Properties implements PropertyEnum{
        ORG_ID("searchOrgs");

        private final String key;

        private Properties(final String key) {
            this.key = key;
        }

        @Override
        public String getKey() {
            return this.key;
        }
    }

    private Data data;
    
    private OrgSearchHelper(Data data){
        this.data=data;
    }

    public static OrgSearchHelper wrap (Data data)
    {
        if (data == null)
        {
             return null;
        }
        return new OrgSearchHelper(data);
    }
    
    public Data getData(){
        return data;
    }
    
    public void setOrgId(String id){
        data.set(Properties.ORG_ID.getKey(), id);
    }
    
    public String getOrgId() {
        return data.get(Properties.ORG_ID.getKey());
    }
    
   
    

}
