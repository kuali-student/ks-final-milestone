/**
 * Copyright 2013 The Kuali Foundation Licensed under the
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
 * Created by Charles on 9/22/13
 */
package org.kuali.student.poc.eventproc.event.subclass.event;

import org.kuali.student.poc.eventproc.event.KSEvent;
import org.kuali.student.poc.eventproc.event.KSEventAttributeKey;
import org.kuali.student.poc.eventproc.event.KSEventFactory;
import org.kuali.student.poc.eventproc.event.KSEventType;
import org.kuali.student.r2.common.exceptions.OperationFailedException;

/**
 * Event for requesting AO state change.
 *
 * @author Kuali Student Team
 */
public class KSAOStateChangeEvent extends KSEvent {
    private String aoId;
    private String toState;

    public KSAOStateChangeEvent(String aoId, String toState) throws OperationFailedException {
        super(KSEventFactory.AO_CHANGE_STATE_EVENT_TYPE);
        this.aoId = aoId;
        this.toState = toState;
    }

    public String getAoId() {
        return aoId;
    }

    public String getToState() {
        return toState;
    }

    @Override
    public boolean hasAttribute(KSEventAttributeKey key) {
        return key.isEqualTo(KSEventFactory.EVENT_ATTRIBUTE_KEY_AO_ID) ||
                key.isEqualTo(KSEventFactory.EVENT_ATTRIBUTE_KEY_AO_TO_STATE);
    }

    @Override
    public String getAttributeValueByKey(KSEventAttributeKey key) {
        if (key.isEqualTo(KSEventFactory.EVENT_ATTRIBUTE_KEY_AO_ID)) {
            return aoId;
        } else if (key.isEqualTo(KSEventFactory.EVENT_ATTRIBUTE_KEY_AO_TO_STATE)) {
            return toState;
        }
        throw new RuntimeException("Invalid key name: " + key.getAttribute());
    }
}
