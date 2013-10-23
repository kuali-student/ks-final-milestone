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
 * Created by Charles on 9/17/13
 */
package org.kuali.student.poc.eventproc.event;

import org.kuali.student.poc.eventproc.event.subclass.event.KSAOStateChangeEvent;
import org.kuali.student.poc.eventproc.event.subclass.event.KSAOStateModifiedEvent;
import org.kuali.student.poc.eventproc.event.subclass.event.KSFOStateModifiedEvent;
import org.kuali.student.poc.eventproc.event.subclass.event.KSInvalidateRGStateEvent;
import org.kuali.student.poc.eventproc.event.subclass.event.KSRecomputeCOStateEvent;
import org.kuali.student.poc.eventproc.event.subclass.event.KSRecomputeFOStateEvent;
import org.kuali.student.poc.eventproc.event.subclass.event.KSRecomputeRGStateEvent;
import org.kuali.student.poc.eventproc.event.subclass.event.KSValidateRGStateEvent;
import org.kuali.student.poc.eventproc.event.subclass.type.KSAOStateChangeEventType;
import org.kuali.student.poc.eventproc.event.subclass.type.KSAOStateModifiedEventType;
import org.kuali.student.poc.eventproc.event.subclass.type.KSFOStateModifiedEventType;
import org.kuali.student.poc.eventproc.event.subclass.type.KSInvalidateRGStateEventType;
import org.kuali.student.poc.eventproc.event.subclass.type.KSRecomputeCOStateEventType;
import org.kuali.student.poc.eventproc.event.subclass.type.KSRecomputeFOStateEventType;
import org.kuali.student.poc.eventproc.event.subclass.type.KSRecomputeRGStateEventType;
import org.kuali.student.poc.eventproc.event.subclass.type.KSValidateRGStateEventType;
import org.kuali.student.r2.common.exceptions.OperationFailedException;

/**
 * A central location to create the events in the system.
 *
 * @author Kuali Student Team
 */
public class KSEventFactory {
    private KSEventFactory() {
    }
    // -------------------------------- Event attributes ---------------------------------------------------
    // AO ID
    public static final String EVENT_ATTRIBUTE_NAME_AO_ID = "kuali.event.attribute.aoId";
    public static final KSEventAttributeKey EVENT_ATTRIBUTE_KEY_AO_ID =
            new KSEventAttributeKey(EVENT_ATTRIBUTE_NAME_AO_ID, "aoId", "String");

    // AO fromState
    public static final String EVENT_ATTRIBUTE_NAME_AO_FROM_STATE = "kuali.event.attribute.aoFromState";
    public static final KSEventAttributeKey EVENT_ATTRIBUTE_KEY_AO_FROM_STATE =
            new KSEventAttributeKey(EVENT_ATTRIBUTE_NAME_AO_FROM_STATE, "fromState", "String");

    // AO toState
    public static final String EVENT_ATTRIBUTE_NAME_AO_TO_STATE = "kuali.event.attribute.aoToState";
    public static final KSEventAttributeKey EVENT_ATTRIBUTE_KEY_AO_TO_STATE =
            new KSEventAttributeKey(EVENT_ATTRIBUTE_NAME_AO_TO_STATE, "toState", "String");

    // FO ID
    public static final String EVENT_ATTRIBUTE_NAME_FO_ID = "kuali.event.attribute.foId";
    public static final KSEventAttributeKey EVENT_ATTRIBUTE_KEY_FO_ID =
            new KSEventAttributeKey(EVENT_ATTRIBUTE_NAME_FO_ID, "foId", "String");

    // CO ID
    public static final String EVENT_ATTRIBUTE_NAME_CO_ID = "kuali.event.attribute.coId";
    public static final KSEventAttributeKey EVENT_ATTRIBUTE_KEY_CO_ID =
            new KSEventAttributeKey(EVENT_ATTRIBUTE_NAME_CO_ID, "coId", "String");

    // RG ID
    public static final String EVENT_ATTRIBUTE_NAME_RG_ID = "kuali.event.attribute.rgId";
    public static final KSEventAttributeKey EVENT_ATTRIBUTE_KEY_RG_ID =
            new KSEventAttributeKey(EVENT_ATTRIBUTE_NAME_RG_ID, "rgId", "String");
    // ------------------------------------- AO CHANGE STATE EVENT -------------------------------------------
    public static final String AO_CHANGE_STATE_EVENT_NAME = KSAOStateChangeEventType.EVENT_NAME;
    public static final KSEventType AO_CHANGE_STATE_EVENT_TYPE = new KSAOStateChangeEventType();

    public static KSEvent createChangeActivityOfferingStateEvent(String aoId, String toState) throws OperationFailedException {
        return new KSAOStateChangeEvent(aoId, toState);
    }

    // -------------------------------- AO STATE MODIFIED EVENT -----------------------------------------
    public static final String AO_STATE_MODIFIED_EVENT_NAME = KSAOStateModifiedEventType.EVENT_NAME;
    public static final KSEventType AO_STATE_MODIFIED_EVENT_TYPE = new KSAOStateModifiedEventType();

    public static KSEvent createActivityOfferingStateModifiedEvent(String aoId, String fromState, String toState) throws OperationFailedException {
        return new KSAOStateModifiedEvent(aoId, fromState, toState);
    }

    // -------------------------------- FO RECOMPUTE STATE EVENT -----------------------------------------
    public static final String FO_RECOMPUTE_STATE_EVENT_NAME = KSRecomputeFOStateEventType.EVENT_NAME;
    public static final KSEventType FO_RECOMPUTE_STATE_EVENT_TYPE = new KSRecomputeFOStateEventType();

    public static KSEvent createRecomputeFormatOfferingStateEvent(String foId) throws OperationFailedException {
        return new KSRecomputeFOStateEvent(foId);
    }

    // -------------------------------- FO STATE MODIFIED EVENT -----------------------------------------
    public static final String FO_STATE_MODIFIED_EVENT_NAME = KSFOStateModifiedEventType.EVENT_NAME;
    public static final KSEventType FO_STATE_MODIFIED_EVENT_TYPE = new KSFOStateModifiedEventType();

    public static KSEvent createFormatOfferingStateModifiedEvent(String foId) throws OperationFailedException {
        return new KSFOStateModifiedEvent(foId);
    }

    // -------------------------------- CO RECOMPUTE STATE EVENT -----------------------------------------
    public static final String CO_RECOMPUTE_STATE_EVENT_NAME = KSRecomputeCOStateEventType.EVENT_NAME;
    public static final KSEventType CO_RECOMPUTE_STATE_EVENT_TYPE = new KSRecomputeCOStateEventType();

    public static KSEvent createRecomputeCourseOfferingStateEvent(String coId) throws OperationFailedException {
        return new KSRecomputeCOStateEvent(coId);
    }

    // -------------------------------- RG RECOMPUTE STATE EVENT -----------------------------------------
    public static final String RG_RECOMPUTE_STATE_EVENT_NAME = KSRecomputeRGStateEventType.EVENT_NAME;
    public static final KSEventType RG_RECOMPUTE_STATE_EVENT_TYPE = new KSRecomputeRGStateEventType();

    public static KSEvent createRecomputeRegGroupStateEvent(String rgId) throws OperationFailedException {
        return new KSRecomputeRGStateEvent(rgId);
    }

    // -------------------------------- RG VALIDATE STATE EVENT -----------------------------------------
    public static final String RG_VALIDATE_STATE_EVENT_NAME = KSValidateRGStateEventType.EVENT_NAME;
    public static final KSEventType RG_VALIDATE_STATE_EVENT_TYPE = new KSValidateRGStateEventType();

    public static KSEvent createValidateRegGroupStateEvent(String rgId) throws OperationFailedException {
        return new KSValidateRGStateEvent(rgId);
    }

    // -------------------------------- RG VALIDATE STATE EVENT -----------------------------------------
    public static final String RG_INVALIDATE_STATE_EVENT_NAME = KSInvalidateRGStateEventType.EVENT_NAME;
    public static final KSEventType RG_INVALIDATE_STATE_EVENT_TYPE = new KSInvalidateRGStateEventType();

    public static KSEvent createInvalidateRegGroupStateEvent(String rgId) throws OperationFailedException {
        return new KSInvalidateRGStateEvent(rgId);
    }
}
