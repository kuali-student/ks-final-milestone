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

package org.kuali.student.common.ui.client.service.exceptions;

/**
 * This is redefiniton of the org.kuali.student.common.OperationFailedException. This is
 * so exception can be used in GWT client side. The GWT compiler doesn't like the 
 * service annotations found in the core exceptions classes. 
 * 
 * TODO: A better solution for exceptions on client side.
 *   
 * @author Kuali Student Team
 *
 */
public class OperationFailedException extends Exception{
    private static final long serialVersionUID = 1L;

    public OperationFailedException() {
        super();
    }

    public OperationFailedException(String message) {
         super(message);
     }
    
    public OperationFailedException(String message, Throwable t) {
        super(message, t);
   }
}
