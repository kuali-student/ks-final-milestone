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
package org.kuali.student.common.ui.client.mvc;

/**
 * Passed to ModelRequestCallback when the model is not found.
 * 
 * @author Kuali Student Team
 */
public class ModelNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 1631884923519293731L;
    private final Class<?> requestedType;

    /**
     * This constructs a ModelNotFoundException
     * 
     * @param message
     * @param requestedType
     *            the type associated with the model request
     */
    public ModelNotFoundException(String message, Class<?> requestedType) {
        super(message);
        this.requestedType = requestedType;
    }

    /**
     * Returns the type associated with the model request
     * 
     * @return the type associated with the model request
     */
    public Class<?> getRequestedType() {
        return requestedType;
    }

}
