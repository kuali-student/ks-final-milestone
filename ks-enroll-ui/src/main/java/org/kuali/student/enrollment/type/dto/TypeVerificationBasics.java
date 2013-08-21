/**
 * Copyright 2012 The Kuali Foundation Licensed under the
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
 *
 * Created by cmuller on 8/30/12
 */
package org.kuali.student.enrollment.type.dto;

/**
 * This class //TODO ...
 *
 * @author Kuali Student Team
 */
public class TypeVerificationBasics {
    private String typeKey;
    private String service;
    private String constant;


    public TypeVerificationBasics(){
        typeKey=null;
        service=null;
        constant=null;
    }

    public TypeVerificationBasics(String typeKey, String service, String constant){
        this.typeKey=typeKey;
        this.service=service;
        this.constant=constant;
    }


    public String getTypeKey() {
        return typeKey;
    }

    public void setTypeKey(String typeKey) {
        this.typeKey = typeKey;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public String getConstant() {
        return constant;
    }

    public void setConstant(String constant) {
        this.constant = constant;
    }
}
