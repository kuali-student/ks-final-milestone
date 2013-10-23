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
import org.kuali.student.r2.common.exceptions.OperationFailedException;

/**
 * An event to ask the RG to recompute its state.  If RG state is invalid, this event will
 * do nothing
 *
 * @author Kuali Student Team
 */
public class KSRecomputeRGStateEvent extends KSEvent {
    private String rgId;

    public KSRecomputeRGStateEvent(String rgId) throws OperationFailedException {
        super(KSEventFactory.RG_RECOMPUTE_STATE_EVENT_TYPE);
        this.rgId = rgId;
    }

    public String getRgId() {
        return rgId;
    }

    @Override
    public boolean hasAttribute(KSEventAttributeKey key) {
        return key.isEqualTo(KSEventFactory.EVENT_ATTRIBUTE_KEY_RG_ID);
    }

    @Override
    public String getAttributeValueByKey(KSEventAttributeKey key) {
        if (key.isEqualTo(KSEventFactory.EVENT_ATTRIBUTE_KEY_RG_ID)) {
            return rgId;
        }
        throw new RuntimeException("Invalid key name: " + key);
    }
}
