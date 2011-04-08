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

/**
 * Status object returned by operations to indicate that the operation succeeded
 *
 * Note: The success is always TRUE otherwise the method should have thrown an exception.
 *
 * Used for delete operations because they have nothing to return otherwise and the
 * standard is that all methods return something.
 *
 * TODO: switch this to hold a count or something, a boolean that always is true is confusing.
 * TODO: Figure out where the message came from because it is not in the R1 wiki
 * https://wiki.kuali.org/display/KULSTU/statusInfo+Structure
 *
 * @author nwright
 */
public interface Status {

    /**
     * Name: Success Indicator
     *
     * Indicates the success or failure of the operation
     */
    public Boolean isSuccess();

    /**
     * Name: Message
     *
     * Optional message indicating a reason
     */
    public String getMessage();
}

