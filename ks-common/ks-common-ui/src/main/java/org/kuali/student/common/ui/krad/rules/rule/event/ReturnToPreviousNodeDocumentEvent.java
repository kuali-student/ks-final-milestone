/**
 * Copyright 2014 The Kuali Foundation Licensed under the
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
 * Created by delyea on 8/4/14
 */
package org.kuali.student.common.ui.krad.rules.rule.event;

import org.kuali.rice.krad.document.Document;
import org.kuali.rice.krad.rules.rule.event.ApproveDocumentEvent;

/**
 * This class represents the return to previous document event that is part of an eDoc in Kuali Student. This is triggered
 * when a user wants to return a document to a previous node in the route path. This is an extension of the {@link ApproveDocumentEvent}
 * because when this operation occurs in the Kuali Student sphere we need to make sure the document is in a state that
 * matches the state when an approval occurs with all the same validations and rules being run.
 *
 * NOTE: This class is only in Kuali Student and does not exist in base KRAD code. It may be that KRAD implements this in a
 * future release but until then KS will use this.
 */
public class ReturnToPreviousNodeDocumentEvent extends ApproveDocumentEvent {

    /**
     * Constructs a ReturnToPreviousNodeDocumentEvent with the specified errorPathPrefix and document
     *
     * @param errorPathPrefix
     * @param document
     */
    public ReturnToPreviousNodeDocumentEvent(String errorPathPrefix, Document document) {
        super("returnToPreviousNode", errorPathPrefix, document);
    }

    /**
     * Constructs a ReturnToPreviousNodeDocumentEvent with the given document
     *
     * @param document
     */
    public ReturnToPreviousNodeDocumentEvent(Document document) {
        super("returnToPreviousNode", "", document);
    }

}
