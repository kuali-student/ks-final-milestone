/*
 * Copyright 2007 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 1.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl1.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.student.common.ui.client.mvc;

/**
 * This callback is used to inform a source widget of an event of the event's
 * status.
 * 
 * @author Kuali Student Team
 *
 */
public interface EventStateCallback {
    
    /**
     * Call this method upon successful handling of an event
     *
     */
    public void onEventComplete(HasEventState event);
    
}
