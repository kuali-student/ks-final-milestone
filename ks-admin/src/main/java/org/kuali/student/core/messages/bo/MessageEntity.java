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
import javax.persistence.Table;
import javax.persistence.Transient;

import org.kuali.rice.krad.service.KRADServiceLocator;
import org.kuali.student.core.bo.KsBusinessObjectBase;
import org.kuali.student.core.enumerationmanagement.bo.EnumeratedValue;

@Entity
@Table(name = "KSMG_MESSAGE")
public class MessageEntity extends KsBusinessObjectBase {

    public static final String GROUP_NAME_ENUMERATION = "ks.message.groupNames";
    public static final String LOCALE_ENUMERATION = "ks.message.locales";
    
    private static final long serialVersionUID = 1L;

    
    private String messageId;
    private String locale;
    private String groupName;
    private String value;
    
    @Transient
    private transient EnumeratedValue localeEnumValue;
    
    @Transient
    private transient EnumeratedValue groupNameEnumValue;

    // now handled with reflection
//    @Override
//    protected LinkedHashMap<String, Object> toStringMapper() {
//
//        LinkedHashMap<String, Object> map = super.toStringMapper();
//
//        map.put("locale", locale);
//        map.put("groupName", groupName);
//        map.put("messageId", messageId);
//
//        return map;
//
//    }
    
    protected EnumeratedValue retrieveEnumeratedValue(String enumerationId, String enumerationCode) {
        Map<String, Object> criteria = new HashMap<String, Object>();
        criteria.put("enumerationId", enumerationId);
        criteria.put("code", enumerationCode);
        
        return (EnumeratedValue) KRADServiceLocator.getBusinessObjectService().findByPrimaryKey(EnumeratedValue.class, criteria);
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

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }


    public EnumeratedValue getLocaleEnumValue() {
        if(localeEnumValue == null) {
            localeEnumValue = retrieveEnumeratedValue(LOCALE_ENUMERATION, this.locale);            
        }
        
        return localeEnumValue;
    }

    public void setLocaleEnumValue(EnumeratedValue localeEnumValue) {
        this.localeEnumValue = localeEnumValue;
    }

    public EnumeratedValue getGroupNameEnumValue() {
        if(groupNameEnumValue == null) {
            groupNameEnumValue = retrieveEnumeratedValue(GROUP_NAME_ENUMERATION, this.groupName);
        }
        
        return groupNameEnumValue;
    }

    public void setGroupNameEnumValue(EnumeratedValue groupNameEnumValue) {
        this.groupNameEnumValue = groupNameEnumValue;
    }

}
