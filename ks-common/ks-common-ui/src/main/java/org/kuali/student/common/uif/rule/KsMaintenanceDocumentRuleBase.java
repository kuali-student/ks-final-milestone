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
 * Created by bobhurt on 6/13/13
 */
package org.kuali.student.common.uif.rule;

import org.kuali.rice.krad.maintenance.MaintenanceDocument;
import org.kuali.rice.krad.rules.MaintenanceDocumentRuleBase;

/**
 * This class provides a KS global rule base, in particular
 * for the processGlobalSaveDocumentBusinessRules() method.
 *
 * @author Kuali Student Team
 */
public class KsMaintenanceDocumentRuleBase extends MaintenanceDocumentRuleBase {

    /*
     * From the Rice docs:
     *     Note that although this method returns a true or false to indicate whether the save
     *     should happen or not, this result may not be followed by the calling method.  In other
     *     words, the boolean result will likely be ignored, and the document saved, regardless.
     *
     * Most editing should be done in the isDocumentValidForSave() method; this method is for
     * any edit that affects all documents.
     *
     * MaintenanceDocumentRulesBase.processGlobalSaveDocumentBusinessRules() does two things
     * which we don't really want in KS:
     *     1. Checks primary key for OJB implementation, but KS is SOA-based and calls services
     *        to persist the changes.
     *     2. Validates the data dictionary, but this already happens at our KS service
     *        implementation.
     * Consequently, the method here simply returns true all the time.
     */
    @Override
    protected boolean processGlobalSaveDocumentBusinessRules(MaintenanceDocument document) {
        return true;
    }

}
