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

package org.kuali.student.lum.lu.assembly.data.client.refactorme.orch;

import java.util.Date;

import org.kuali.student.core.assembly.data.Data;
import org.kuali.student.core.assembly.helper.PropertyEnum;
import org.kuali.student.lum.lu.assembly.data.client.refactorme.base.MetaInfoHelper;

public class CluSetHelper {

    private static final long serialVersionUID = 1;

    public enum Properties implements PropertyEnum
    {
        ID ("id"),
        ORGANIZATION ("organization"),
        DESCRIPTION ("description"),
        EFFECTIVE_DATE ("effectiveDate"),
        EXPIRATION_DATE ("expirationDate"),
        CLUS ("clus"),
        META_INFO ("metaInfo"),
        NAME ("name"),
        STATE ("state"),
        TYPE ("type");

        private final String key;

        private Properties (final String key) {
            this.key = key;
        }

        @Override
        public String getKey() {
            return this.key;
        }
    }
    private Data data;
    
    public CluSetHelper(Data data) {
        this.data = data;
    }
    
    public static CluSetHelper wrap(Data data) {
        if (data == null) {
            return null;
        }
        return new CluSetHelper(data);
    }
    
    public Data getData() {
        return this.data;
    }
    
    public void setId(String value) {
        data.set(Properties.ID.getKey(), value);
    }
    
    public String getId() {
        return (String) data.get(Properties.ID.getKey());
    }
    
    public void setOrganization(String value) {
        data.set(Properties.ORGANIZATION.getKey(), value);
    }
    public String getOrganization() {
        return (String) data.get(Properties.ORGANIZATION.getKey());
    }

    public void setDescription(String value) {
        data.set(Properties.DESCRIPTION.getKey(), value);
    }
    public String getDescription() {
        return (String) data.get (Properties.DESCRIPTION.getKey ());
    }

    public void setEffectiveDate(Date value) {
        data.set(Properties.EFFECTIVE_DATE.getKey(), value);
    }
    public Date getEffectiveDate() {
        return (Date) data.get(Properties.EFFECTIVE_DATE.getKey());
    }

    public void setExpirationDate(Date value) {
        data.set(Properties.EXPIRATION_DATE.getKey(), value);
    }
    public Date getExpirationDate() {
        return (Date) data.get(Properties.EXPIRATION_DATE.getKey());
    }
    
    public void setMetaInfo (MetaInfoHelper value) {
        data.set (Properties.META_INFO.getKey (), (value == null) ? null : value.getData ());
    }
    public MetaInfoHelper getMetaInfo () {
        return MetaInfoHelper.wrap ((Data) data.get (Properties.META_INFO.getKey ()));
    }

    public void setName(String name) {
        data.set(Properties.NAME.getKey(), name);
    }
    public String getName() {
        return (String) data.get(Properties.NAME.getKey());
    }

    public void setState(String state) {
        data.set(Properties.STATE.getKey(), state);
    }
    public String getState() {
        return (String) data.get(Properties.STATE.getKey());
    }

    public void setType(String type) {
        data.set(Properties.TYPE.getKey(), type);
    }
    public String getType() {
        return (String) data.get(Properties.TYPE.getKey());
    }

    public void setClus(Data value) {
        data.set(Properties.CLUS.getKey(), value);
    }
    public Data getClus() {
        Data clusData = data.get(Properties.CLUS.getKey());
        if (clusData == null) {
            clusData = new Data();
            setClus(clusData);
        }
        return clusData;
    }

}