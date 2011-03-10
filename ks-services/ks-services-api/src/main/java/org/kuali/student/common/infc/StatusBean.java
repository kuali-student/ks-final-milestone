/*
* Copyright 2011 The Kuali Foundation
*
* Licensed under the Educational Community License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may	obtain a copy of the License at
*
* 	http://www.osedu.org/licenses/ECL-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/
package org.kuali.student.common.infc;

import java.io.Serializable;

public class StatusBean
        implements StatusInfc, Serializable {

    private static final long serialVersionUID = 1L;
    private Boolean success;

    /**
     * Set ????
     * <p/>
     * Type: Boolean
     * <p/>
     * ???
     */
    @Override
    public void setSuccess(Boolean success) {
        this.success = success;
    }

    /**
     * Get ????
     * <p/>
     * Type: Boolean
     * <p/>
     * ???
     */
    @Override
    public Boolean isSuccess() {
        return this.success;
    }

    private String message;

    /**
     * Set ????
     * <p/>
     * Type: String
     * <p/>
     * ???
     */
    @Override
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * Get ????
     * <p/>
     * Type: String
     * <p/>
     * ???
     */
    @Override
    public String getMessage() {
        return this.message;
    }
}

