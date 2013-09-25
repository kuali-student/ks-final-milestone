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
 * Created by Charles on 9/11/13
 */
package org.kuali.student.poc.eventproc.event;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Base class for an event type (subclassing is only done for convenience--can always use base class).
 *
 * @author Kuali Student Team
 */
public class KSEventType {
    private String namespace;
    private String eventName;
    private List<KSEventAttributeKey> requiredAttributeKeys;
    private List<KSEventAttributeKey> optionalAttributeKeys;

    public KSEventType(String namespace, String eventName,
                       List<KSEventAttributeKey> requiredAttributeKeys,
                       List<KSEventAttributeKey> optionalAttributeKeys) {
        this.namespace = namespace;
        this.eventName = eventName;
        if (requiredAttributeKeys == null) {
            this.requiredAttributeKeys = new ArrayList<KSEventAttributeKey>();
        } else {
            this.requiredAttributeKeys = requiredAttributeKeys;
        }
        if (optionalAttributeKeys == null) {
            this.optionalAttributeKeys = new ArrayList<KSEventAttributeKey>();
        } else {
            this.optionalAttributeKeys = optionalAttributeKeys;
        }
    }

    public String getNamespace() {
        return namespace;
    }

    public String getEventName() {
        return eventName;
    }

    public List<KSEventAttributeKey> getRequiredAttributeKeys() {
        return Collections.unmodifiableList(requiredAttributeKeys);
    }

    public List<KSEventAttributeKey> getOptionalAttributeKeys() {
        return Collections.unmodifiableList(optionalAttributeKeys);
    }

    public boolean hasSameEventTypeAs(KSEventType eventType) {
        return this.equals(eventType);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof KSEventType)) return false;

        KSEventType that = (KSEventType) o;

        if (!eventName.equals(that.eventName)) {
            return false;
        }
        if (namespace != null ? !namespace.equals(that.namespace) : that.namespace != null) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = namespace != null ? namespace.hashCode() : 0;
        result = 31 * result + eventName.hashCode();
        return result;
    }
}
