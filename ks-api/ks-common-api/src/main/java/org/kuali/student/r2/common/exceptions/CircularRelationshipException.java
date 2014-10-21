/**
 * Copyright 2010 The Kuali Foundation 
 *
 * Licensed under the Educational Community License, Version 2.0 (the
 * "License"); you may not use this file except in compliance with the
 * License. You may obtain a copy of the License at
 *
 * http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 * implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */

package org.kuali.student.r2.common.exceptions;

import javax.xml.ws.WebFault;

@WebFault(name="CircularRelationship")
public class CircularRelationshipException 
    extends Exception {

    private static final long serialVersionUID = -6652661226236017610L;

    
    public CircularRelationshipException() {
    }

    public CircularRelationshipException(String message, Throwable cause) {
        super(message, cause);
    }
    
    public CircularRelationshipException(String message) {
        super(message);
    }

    public CircularRelationshipException(Throwable cause) {
        super(cause);
    }
}
