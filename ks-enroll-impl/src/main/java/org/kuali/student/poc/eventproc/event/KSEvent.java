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
import org.kuali.student.poc.eventproc.api.KSHandler;
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
public class KSEvent implements KSEventAuditTrail {
    private static final Logger LOGGER = Logger.getLogger(KSEvent.class);

    private KSEventType eventType;
    private Map<KSEventAttributeKey, String> eventAttributeKeyValueMap;

    private List<KSHandler> handlersSeen = new ArrayList<KSHandler>();
    private List<KSEventResult> resultsSeen = new ArrayList<KSEventResult>();

    private List<KSEvent> downstreamEvents = new ArrayList<KSEvent>();

    public KSEvent(KSEventType eventType) {
        this.eventType = eventType;
        this.eventAttributeKeyValueMap = new HashMap<KSEventAttributeKey, String>();
    }

    public boolean hasAttribute(KSEventAttributeKey key) {
        return eventAttributeKeyValueMap.containsKey(key);
    }

    public KSEventType getEventType() {
        return eventType;
    }

    public String getValueByAttributeKey(KSEventAttributeKey key) {
        return eventAttributeKeyValueMap.get(key);
    }

    public boolean addEventAttribute(KSEventAttributeKey key, String value) throws OperationFailedException {
        if (eventType.getRequiredAttributeKeys().contains(key) || eventType.getOptionalAttributeKeys().contains(key)) {
            // Only allow permissible keys (overwrites previous entries)
            if (eventAttributeKeyValueMap.containsKey(key)) {
                LOGGER.warn("Adding an existing key, " + key.getShortName() + ", to this event: " + eventType.getEventName());
            }
            eventAttributeKeyValueMap.put(key, value);
            return true;
        }
        throw new OperationFailedException("Illegal key for event type: " + eventType.getEventName());
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder(eventType.getEventName() + ": [");
        boolean isFirst = true;
        for (Map.Entry<KSEventAttributeKey, String> entry: eventAttributeKeyValueMap.entrySet()) {
            if (isFirst) {
                isFirst = false;
            } else {
                result.append(", ");
            }
            result.append(entry.getKey().getShortName());
            result.append(" = ");
            result.append(entry.getValue());
        }
        result.append("]");
        return result.toString();
    }

    @Override
    public void addHandlerAndEventResult(KSHandler handler, KSEventResult result) {
        handlersSeen.add(handler);
        resultsSeen.add(result);
    }

    @Override
    public void addDownstreamEvent(KSEvent event) {
        downstreamEvents.add(event);
    }
}
