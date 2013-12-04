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
 * Created by Charles on 9/13/13
 */
package org.kuali.student.poc.eventproc.event;

import org.apache.log4j.Logger;
import org.kuali.student.poc.eventproc.api.KSEventAuditTrail;
import org.kuali.student.r2.common.exceptions.OperationFailedException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Base class for event.  Subclassing is convenient, but not required.  Can always use this base class.
 *
 * @author Kuali Student Team
 */
public abstract class KSEvent implements KSEventAuditTrail {
    private static final Logger LOGGER = Logger.getLogger(KSEvent.class);

    private KSEventType eventType;
    private Map<KSEventAttributeKey, String> eventAttributeKeyValueMap;

    private List<KSHandlerResult> handlerResults = new ArrayList<KSHandlerResult>();

    private List<KSEvent> downstreamEvents = new ArrayList<KSEvent>();

    public KSEvent(KSEventType eventType) {
        this.eventType = eventType;
        this.eventAttributeKeyValueMap = new HashMap<KSEventAttributeKey, String>();
    }

    public abstract boolean hasAttribute(KSEventAttributeKey key);

    public KSEventType getEventType() {
        return eventType;
    }

    public abstract String getAttributeValueByKey(KSEventAttributeKey key);

    @Override
    public void addHandlerResult(KSHandlerResult result) {
        handlerResults.add(result);
    }

    @Override
    public void addDownstreamEvent(KSEvent event) {
        downstreamEvents.add(event);
    }
}
