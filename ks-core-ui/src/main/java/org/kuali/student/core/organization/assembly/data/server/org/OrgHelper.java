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


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;
import org.kuali.student.r1.common.assembly.data.Data;
import org.kuali.student.r1.common.assembly.helper.PropertyEnum;
import org.kuali.student.core.organization.assembly.data.client.RuntimeDataHelper;

public class OrgHelper{
	final Logger LOG = Logger.getLogger(OrgHelper.class);
	private static final long serialVersionUID = 1L;

	public enum Properties implements PropertyEnum{
		ID("id"),TYPE("type"),NAME("longName"),ABBREVIATION("shortName"),DESCRIPTION("longDesc"),
				EFFECTIVE_DATE("effectiveDate"), EXPIRATION_DATE("expirationDate"),_RUNTIME_DATA ("_runtimeData"),
				VERSION_CODES("versionCodes");

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
    
    private OrgHelper(Data data){
        this.data=data;
    }

    public static OrgHelper wrap (Data data)
    {
        if (data == null)
        {
             return null;
        }
        return new OrgHelper(data);
    }
    
    public Data getData(){
        return data;
    }
	
	public void setId(String id){
	    data.set(Properties.ID.getKey(), id);
	}
	
    public String getId() {
        return data.get(Properties.ID.getKey());
    }
    
	public void setType(String type) {
	    data.set(Properties.TYPE.getKey(), type);
    }
    
    public String getType() {
        return data.get(Properties.TYPE.getKey());
    }

    public void setName(String name) {
        data.set(Properties.NAME.getKey(), name);
    }
    
    public String getName() {
        return data.get(Properties.NAME.getKey());
    }
    
    public void setAbbreviation(String abbr) {
        data.set(Properties.ABBREVIATION.getKey(), abbr);
    }
    
    public String getAbbreviation() {
        return data.get(Properties.ABBREVIATION.getKey());
    }
    
    public void setDescription(String description) {
        data.set(Properties.DESCRIPTION.getKey(), description);
    }
    
    public String getDescription() {
        return data.get(Properties.DESCRIPTION.getKey());
    }
    
    public Date getEffectiveDate() {
        if(data.get(Properties.EFFECTIVE_DATE.getKey()) instanceof String){
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            try {
                return df.parse((String) data.get(Properties.EFFECTIVE_DATE.getKey()));
            } catch (Exception e) {
                LOG.error(e);
            }
        }
        return data.get(Properties.EFFECTIVE_DATE.getKey());
    }
    
    public void setEffectiveDate(Date value)  {
        data.set(Properties.EFFECTIVE_DATE.getKey(), value);
    }
    
    public Date getExpirationDate() {
        if(data.get(Properties.EXPIRATION_DATE.getKey()) instanceof String){
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            try {
                return df.parse((String) data.get(Properties.EXPIRATION_DATE.getKey()));
            } catch (Exception e) {
                LOG.error(e);
            }
        }
        return data.get(Properties.EXPIRATION_DATE.getKey());
    }
    
    public void setExpirationDate(Date value)  {
        data.set(Properties.EXPIRATION_DATE.getKey(), value);
    }
    
    public void set_runtimeData (RuntimeDataHelper value)
    {
        data.set (Properties._RUNTIME_DATA.getKey (), (value == null) ? null : value.getData ());
    }
    
    
    public RuntimeDataHelper get_runtimeData ()
    {
        return RuntimeDataHelper.wrap ((Data) data.get (Properties._RUNTIME_DATA.getKey ()));
    }

	
}
