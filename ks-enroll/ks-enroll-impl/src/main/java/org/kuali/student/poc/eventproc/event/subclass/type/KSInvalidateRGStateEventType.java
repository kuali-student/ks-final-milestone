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
 * Created by Charles on 9/23/13
 */
package org.kuali.student.poc.eventproc.event.subclass.type;

import org.kuali.student.poc.eventproc.event.KSEventAttributeKey;
import org.kuali.student.poc.eventproc.event.KSEventFactory;
import org.kuali.student.poc.eventproc.event.KSEventNamespace;
import org.kuali.student.poc.eventproc.event.KSEventType;

import java.util.ArrayList;
import java.util.List;

/**
 * Event type for invalidating an RG state
 *
 * @author Kuali Student Team
 */
public class KSInvalidateRGStateEventType extends KSEventType {
    public static final String EVENT_NAME = "kuali.event.invalidate.rg.state";
    public static final List<KSEventAttributeKey> REQUIRED_KEYS;
    static {
        REQUIRED_KEYS = new ArrayList<KSEventAttributeKey>();
        REQUIRED_KEYS.add(KSEventFactory.EVENT_ATTRIBUTE_KEY_RG_ID);
    }

    public KSInvalidateRGStateEventType() {
        super(KSEventNamespace.NAMESPACE_CHANGE_STATE, EVENT_NAME, REQUIRED_KEYS, null);
    }
}
