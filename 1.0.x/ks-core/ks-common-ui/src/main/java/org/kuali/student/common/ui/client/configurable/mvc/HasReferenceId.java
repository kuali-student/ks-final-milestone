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
package org.kuali.student.common.ui.client.configurable.mvc;

/**
 * This allows getting/setting of a reference id and reference key on any widget
 * implementing this interface. 
 * 
 * @author Kuali Student Team
 *
 */
public interface HasReferenceId {

    public void setReferenceId(String id);
    
    public String getReferenceId();
    
    public void setReferenceTypeKey(String key);
    
    public String getReferenceTypeKey();
    
    public void setReferenceType(String type);
    
    public String getReferenceType();
    
    public void setReferenceState(String state);
    
    public String getReferenceState();
}
