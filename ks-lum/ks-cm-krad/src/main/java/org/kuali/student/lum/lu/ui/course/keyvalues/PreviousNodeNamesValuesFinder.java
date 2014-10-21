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
package org.kuali.student.lum.lu.ui.course.keyvalues;

import org.kuali.rice.core.api.util.ConcreteKeyValue;
import org.kuali.rice.core.api.util.KeyValue;
import org.kuali.rice.kew.api.KewApiServiceLocator;
import org.kuali.rice.kew.api.document.WorkflowDocumentService;
import org.kuali.rice.krad.uif.control.UifKeyValuesFinderBase;
import org.kuali.rice.krad.uif.view.ViewModel;
import org.kuali.rice.krad.web.form.MaintenanceDocumentForm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * This class returns all the previous node names for a particular document in an ordered list. This
 * class assumes the node list is sequential.
 *
 * @author Kuali Student Team
 */
public class PreviousNodeNamesValuesFinder extends UifKeyValuesFinderBase {

    private static final Logger LOG = LoggerFactory.getLogger(PreviousNodeNamesValuesFinder.class);
    private WorkflowDocumentService workflowDocumentService;

    @Override
    public List<KeyValue> getKeyValues(ViewModel model) {
        List<KeyValue> nodeNames = new ArrayList<KeyValue>();
        final MaintenanceDocumentForm form = (MaintenanceDocumentForm) model;
        // loop the node names from the workflow service to build the list
        for (String nodeName : getWorkflowDocumentService().getPreviousRouteNodeNames(form.getDocument().getDocumentNumber())) {
            // both the key and value should be set to the node name
            nodeNames.add(new ConcreteKeyValue(nodeName, nodeName));
        }
        return nodeNames;
    }

    public WorkflowDocumentService getWorkflowDocumentService() {
        if (workflowDocumentService == null) {
            workflowDocumentService = KewApiServiceLocator.getWorkflowDocumentService();
        }
        if (null == workflowDocumentService) {
            throw new RuntimeException("Workflow Document Service is unavailable");
        }
        return workflowDocumentService;
    }

}
