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

package org.kuali.student.core.messages.bo;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.kuali.rice.kns.bo.PersistableBusinessObjectBase;
import org.kuali.rice.kns.service.KNSServiceLocator;
import org.kuali.student.core.enumerationmanagement.bo.EnumeratedValue;

@Entity
@Table(name = "KSMG_MESSAGE")
public class MessageEntity extends PersistableBusinessObjectBase {

    public static final String GROUP_NAME_ENUMERATION = "ks.message.groupNames";
    public static final String LOCALE_ENUMERATION = "ks.message.locales";
    
    private static final long serialVersionUID = 1L;

    @Id
    private Long databaseId;
    private String id;
    private String locale;
    private String groupName;
    private String value;
    
    @Transient
    private transient EnumeratedValue localeEnumValue;
    
    @Transient
    private transient EnumeratedValue groupNameEnumValue;

    
    @Override
    protected LinkedHashMap<String, Object> toStringMapper() {

        LinkedHashMap<String, Object> toStringMap = new LinkedHashMap<String, Object>();

        toStringMap.put("databaseId", databaseId);
        toStringMap.put("locale", locale);
        toStringMap.put("groupName", groupName);
        toStringMap.put("id", id);
        toStringMap.put("value", value);

        return toStringMap;

    }
    
    
    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Long getDatabaseId() {
        return databaseId;
    }

    public void setDatabaseId(Long databaseId) {
        this.databaseId = databaseId;
    }

    public EnumeratedValue getLocaleEnumValue() {
        if(localeEnumValue == null) {
            Map<String, Object> criteria = new HashMap<String, Object>();
            criteria.put("enumerationKey", LOCALE_ENUMERATION);
            criteria.put("code", this.locale);
            
            localeEnumValue = (EnumeratedValue) KNSServiceLocator.getBusinessObjectService().findByPrimaryKey(EnumeratedValue.class, criteria);
        }
        
        return localeEnumValue;
    }

    public void setLocaleEnumValue(EnumeratedValue localeEnumValue) {
        this.localeEnumValue = localeEnumValue;
    }

    public EnumeratedValue getGroupNameEnumValue() {
        if(groupNameEnumValue == null) {
            Map<String, Object> criteria = new HashMap<String, Object>();
            criteria.put("enumerationKey", GROUP_NAME_ENUMERATION);
            criteria.put("code", this.groupName);
            
            groupNameEnumValue = (EnumeratedValue) KNSServiceLocator.getBusinessObjectService().findByPrimaryKey(EnumeratedValue.class, criteria);
        }
        
        return groupNameEnumValue;
    }

    public void setGroupNameEnumValue(EnumeratedValue groupNameEnumValue) {
        this.groupNameEnumValue = groupNameEnumValue;
    }

}
