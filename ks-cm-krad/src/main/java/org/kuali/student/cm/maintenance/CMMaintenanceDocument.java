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
 * Created by venkat on 3/13/14
 */
package org.kuali.student.cm.maintenance;

import org.apache.commons.lang.StringUtils;
import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.rice.kew.api.KewApiConstants;
import org.kuali.rice.kew.api.KewApiServiceLocator;
import org.kuali.rice.kew.api.WorkflowDocument;
import org.kuali.rice.kew.api.WorkflowDocumentFactory;
import org.kuali.rice.kew.api.action.ActionRequest;
import org.kuali.rice.kew.api.action.ActionTaken;
import org.kuali.rice.kew.api.document.WorkflowDocumentService;
import org.kuali.rice.kew.framework.postprocessor.ActionTakenEvent;
import org.kuali.rice.kew.framework.postprocessor.DocumentRouteLevelChange;
import org.kuali.rice.kew.framework.postprocessor.DocumentRouteStatusChange;
import org.kuali.rice.kew.framework.postprocessor.IDocumentEvent;
import org.kuali.rice.kim.api.identity.principal.Principal;
import org.kuali.rice.kim.api.services.KimApiServiceLocator;
import org.kuali.rice.krad.maintenance.Maintainable;
import org.kuali.rice.krad.maintenance.MaintenanceDocumentBase;
import org.kuali.rice.krad.rules.rule.event.KualiDocumentEvent;
import org.kuali.rice.krad.rules.rule.event.SaveEvent;
import org.kuali.student.cm.course.form.wrapper.CourseInfoWrapper;
import org.kuali.student.common.util.security.ContextUtils;
import org.kuali.student.core.rice.authorization.DocumentCollaboratorHelper;
import org.kuali.student.lum.workflow.CourseStateChangeServiceImpl;
import org.kuali.student.r1.common.rice.StudentIdentityConstants;
import org.kuali.student.r1.common.rice.StudentProposalRiceConstants;
import org.kuali.student.r1.core.proposal.ProposalConstants;
import org.kuali.student.r1.core.statement.dto.ReqComponentInfo;
import org.kuali.student.r1.core.statement.dto.StatementTreeViewInfo;
import org.kuali.student.r2.common.dto.AttributeInfo;
import org.kuali.student.r2.common.dto.DtoConstants;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.util.AttributeHelper;
import org.kuali.student.r2.core.proposal.dto.ProposalInfo;
import org.kuali.student.r2.core.proposal.service.ProposalService;
import org.kuali.student.r2.lum.clu.CLUConstants;
import org.kuali.student.r2.lum.course.dto.CourseInfo;
import org.kuali.student.r2.lum.course.service.CourseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.Entity;
import javax.xml.namespace.QName;
import java.util.Iterator;
import java.util.List;

/**
 * Document class for all CM maintenance documents which skips the xml serialization of <class>MaintainableImpl</class>
 * Also, on document save, the data will be stored in KS tables instead of serializing the data to KRNS_MAINT_DOC_T.
 * As we skipped the serialization, on retrieve, this class should take care of loading the data from KS tables
 * whenever we load the maintenace document. This happens at <method>processAfterRetrieve()</method> which should
 * be calling <method>CMMaintainable.retrieveDataObject()</method> to load the data.
 *
 * @author Kuali Student Team
 */

@Entity
public class CMMaintenanceDocument extends MaintenanceDocumentBase {

    private static final long serialVersionUID = -505085142412593315L;
    private static final Logger LOG = LoggerFactory.getLogger(CMMaintenanceDocument.class);

    private CourseService courseService;
    private ProposalService proposalService;

    public CMMaintenanceDocument() {
        super();
    }

    public CMMaintenanceDocument(String documentTypeName) {
        super(documentTypeName);
    }

    @Override
    public void processAfterRetrieve() {

        if (documentHeader == null || !documentHeader.hasWorkflowDocument()) {
            throw new RuntimeException("Document Header or workflow document is null");
        }

        String documentTypeName = documentHeader.getWorkflowDocument().getDocumentTypeName();

        Class clazz = getDocumentDictionaryService().getMaintainableClass(documentTypeName);

        if (!CMMaintainable.class.isAssignableFrom(clazz)) {
            throw new RuntimeException("Maintainable should be of CMMaintainable type");
        }

        try {

            Class<?> dataObjectClazz = getDocumentDictionaryService().getMaintenanceDataObjectClass(documentTypeName);

            /**
             * Null check needed here as DocumentServiceImpl.validateAndPersistDocument() calls this method after
             * save. In that case, it's not needed to create a new instance.
             */
            if (oldMaintainableObject == null) {
                oldMaintainableObject = (CMMaintainable) clazz.newInstance();
                oldMaintainableObject.setDataObject(dataObjectClazz.newInstance());
                oldMaintainableObject.setDataObjectClass(dataObjectClazz);
            }

            if (newMaintainableObject == null) {
                newMaintainableObject = (CMMaintainable) clazz.newInstance();
                newMaintainableObject.setDataObject(dataObjectClazz.newInstance());
                newMaintainableObject.setDataObjectClass(dataObjectClazz);
            }

        } catch (InstantiationException e) {
            throw new RuntimeException("Unable to initialize maintainables of type " + clazz.getName(), e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException("Unable to initialize maintainables of type " + clazz.getName(), e);
        }

        super.processAfterRetrieve();

        ((CMMaintainable) newMaintainableObject).retrieveDataObject();

    }

    @Override
    public void prepareForSave(KualiDocumentEvent event) {
        super.prepareForSave(event);

        if (event instanceof SaveEvent) {
            // set the application document id to the proposal id since this is needed for some KEW and KIM processing related to collaborators
            if (getDocumentHeader() != null && getDocumentHeader().getWorkflowDocument() != null && getNewMaintainableObject().getDataObject() != null) {
                CourseInfoWrapper courseInfoWrapper = (CourseInfoWrapper)getNewMaintainableObject().getDataObject();
                if (courseInfoWrapper.getProposalInfo() != null) {
                    getDocumentHeader().getWorkflowDocument().setApplicationDocumentId(courseInfoWrapper.getProposalInfo().getId());
                }
            }
            getNewMaintainableObject().saveDataObject();
        }
    }

    /**
     * These methods deal with (de)serialization of the MaintainableImpl.
     */
    @Override
    public void populateXmlDocumentContentsFromMaintainables() {
        xmlDocumentContents = StringUtils.EMPTY;
    }

    @Override
    public void populateMaintainablesFromXmlDocumentContents() {
        xmlDocumentContents = StringUtils.EMPTY;
    }

    @Override
    public void doActionTaken(ActionTakenEvent actionTakenEvent) {
        Maintainable maintainable = getNewMaintainableObject();
        Class clazz = (maintainable!=null)?maintainable.getClass() : null;
        if (clazz==null || !CMMaintainable.class.isAssignableFrom(clazz)) {
            throw new RuntimeException("Maintainable should be of CMMaintainable type");
        }
        ((CMMaintainable)getNewMaintainableObject()).doActionTaken(actionTakenEvent);
    }

    @Override
    public void doRouteLevelChange(DocumentRouteLevelChange documentRouteLevelChange) {
        Maintainable maintainable = getNewMaintainableObject();
        Class clazz = (maintainable!=null)?maintainable.getClass() : null;
        if (clazz==null || !CMMaintainable.class.isAssignableFrom(clazz)) {
            throw new RuntimeException("Maintainable should be of CMMaintainable type");
        }
        ((CMMaintainable)getNewMaintainableObject()).doRouteLevelChange(documentRouteLevelChange);
    }

    /*
        This method is copied from KualiStudentPostProcessorBase
    */
    protected boolean processCustomRouteLevelChange(
            DocumentRouteLevelChange documentRouteLevelChange,
            ProposalInfo proposalInfo) throws Exception {
        //Update the proposal with the new node name
        new AttributeHelper(proposalInfo.getAttributes()).put("workflowNode", documentRouteLevelChange.getNewNodeName());
        getProposalService().updateProposal(proposalInfo.getId(), proposalInfo, ContextUtils.createDefaultContextInfo());
        return true;
    }

    @Override
    public void doRouteStatusChange(DocumentRouteStatusChange documentRouteStatusChange) {
        Maintainable maintainable = getNewMaintainableObject();
        Class clazz = (maintainable!=null)?maintainable.getClass() : null;
        if (clazz==null || !CMMaintainable.class.isAssignableFrom(clazz)) {
            throw new RuntimeException("Maintainable should be of CMMaintainable type");
        }
        ((CMMaintainable)getNewMaintainableObject()).doRouteStatusChange(documentRouteStatusChange);
    }

    /**
     * Helper method called when post processing should remove the Comment permission given to proposal Collaborators
     *
     * @param principalId id of the user who needs to be removed
     * @param doc the document from which the person's access should be removed
     */
    protected void removeCommentAdhocPermissions(String principalId, WorkflowDocument doc) throws OperationFailedException {
        DocumentCollaboratorHelper.removeCommentAdhocPermission(principalId, doc.getDocumentTypeName(), doc.getApplicationDocumentId());
    }

    /*
        This method is copied from KualiStudentPostProcessorBase
     */

    protected ProposalService getProposalService() {
        if (this.proposalService == null) {
            this.proposalService = (ProposalService) GlobalResourceLoader.getService(new QName("http://student.kuali.org/wsdl/proposal", "ProposalService"));
        }
        return this.proposalService;
    }

    protected CourseService getCourseService() {
        if (this.courseService == null) {
            this.courseService = (CourseService) GlobalResourceLoader.getService(new QName("http://student.kuali.org/wsdl/course", "CourseService"));
        }
        return this.courseService;
    }

}
