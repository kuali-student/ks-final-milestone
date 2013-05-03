package org.kuali.student.enrollment.main.view;

import org.apache.commons.lang.StringUtils;
import org.kuali.rice.kim.api.KimConstants;
import org.kuali.rice.kim.api.identity.Person;
import org.kuali.rice.krad.maintenance.MaintenanceDocument;
import org.kuali.rice.krad.maintenance.MaintenanceViewAuthorizerBase;
import org.kuali.rice.krad.messages.MessageService;
import org.kuali.rice.krad.service.KRADServiceLocatorWeb;
import org.kuali.rice.krad.uif.component.Component;
import org.kuali.rice.krad.uif.component.ComponentSecurity;
import org.kuali.rice.krad.uif.container.Group;
import org.kuali.rice.krad.uif.element.Action;
import org.kuali.rice.krad.uif.field.Field;
import org.kuali.rice.krad.uif.view.View;
import org.kuali.rice.krad.uif.view.ViewModel;
import org.kuali.rice.krad.uif.widget.Widget;
import org.kuali.rice.krad.util.GlobalVariables;
import org.kuali.rice.krad.web.form.DocumentFormBase;
import org.kuali.rice.krad.web.form.MaintenanceDocumentForm;
import org.kuali.student.enrollment.class2.courseoffering.dto.ActivityOfferingWrapper;
import org.kuali.student.enrollment.class2.courseoffering.dto.CourseOfferingCreateWrapper;
import org.kuali.student.enrollment.class2.courseoffering.dto.CourseOfferingEditWrapper;
import org.kuali.student.enrollment.class2.courseoffering.service.impl.ActivityOfferingMaintainableImpl;
import org.kuali.student.enrollment.class2.courseoffering.service.impl.CourseOfferingCreateMaintainableImpl;
import org.kuali.student.enrollment.class2.courseoffering.service.impl.CourseOfferingEditMaintainableImpl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: huangb
 * Date: 1/14/13
 * Time: 3:49 PM
 * To change this template use File | Settings | File Templates.
 */
public class KsMaintenanceViewAuthorizerBase extends MaintenanceViewAuthorizerBase {

    protected MessageService messageService = null;

    @Override
    protected void addRoleQualification(Object primaryDataObjectOrDocument, Map<String, String> attributes) {
        if (primaryDataObjectOrDocument != null && primaryDataObjectOrDocument instanceof MaintenanceDocument) {
            MaintenanceDocument document = (MaintenanceDocument)primaryDataObjectOrDocument;
            if(document.getOldMaintainableObject() instanceof CourseOfferingEditMaintainableImpl){
                CourseOfferingEditMaintainableImpl theForm = (CourseOfferingEditMaintainableImpl) document.getOldMaintainableObject();
                CourseOfferingEditWrapper wrapper = (CourseOfferingEditWrapper) theForm.getDataObject();
                if(wrapper.getAdminOrg() != null){
                    attributes.putAll(wrapper.getAdminOrg());
                }
                if(wrapper.getCourseOfferingInfo()!= null){
                    attributes.put("subjectArea", wrapper.getCourseOfferingInfo().getSubjectArea());
                }
            }
            if(document.getOldMaintainableObject() instanceof ActivityOfferingMaintainableImpl){
                ActivityOfferingMaintainableImpl theForm = (ActivityOfferingMaintainableImpl)document.getOldMaintainableObject();
                ActivityOfferingWrapper wrapper = (ActivityOfferingWrapper)theForm.getDataObject();
                if(wrapper.getAdminOrg() != null){
                    attributes.put("offeringAdminOrgId", wrapper.getAdminOrg());
                }
                if(wrapper.getCourse() != null){
                    attributes.put("subjectArea", wrapper.getCourse().getSubjectArea());
                }

            }
        }
        if (primaryDataObjectOrDocument != null && primaryDataObjectOrDocument instanceof MaintenanceDocumentForm) {
            MaintenanceDocumentForm mntnForm = (MaintenanceDocumentForm)primaryDataObjectOrDocument;
            MaintenanceDocument document = mntnForm.getDocument();
            if(document != null && document.getNewMaintainableObject() instanceof CourseOfferingCreateMaintainableImpl) {
                CourseOfferingCreateMaintainableImpl theForm = (CourseOfferingCreateMaintainableImpl)document.getNewMaintainableObject();
                CourseOfferingCreateWrapper wrapper = (CourseOfferingCreateWrapper) theForm.getDataObject();
                if(wrapper.getAdminOrg() != null){
                    attributes.put("offeringAdminOrgId", wrapper.getAdminOrg());
                }
                if(wrapper.getCourse() != null){
                    attributes.put("subjectArea", wrapper.getCourse().getSubjectArea());
                }
            }
        }

        super.addRoleQualification(primaryDataObjectOrDocument, attributes);
    }

    @Override
    protected void addPermissionDetails(Object primaryDataObjectOrDocument, Map<String, String> attributes) {
        if (primaryDataObjectOrDocument !=null && primaryDataObjectOrDocument instanceof MaintenanceDocumentForm) {
            MaintenanceDocumentForm form = (MaintenanceDocumentForm)primaryDataObjectOrDocument;
            MaintenanceDocument document = form.getDocument();
            if(document.getOldMaintainableObject() instanceof CourseOfferingEditMaintainableImpl){
                CourseOfferingEditMaintainableImpl theForm = (CourseOfferingEditMaintainableImpl) document.getOldMaintainableObject();
                CourseOfferingEditWrapper wrapper = (CourseOfferingEditWrapper) theForm.getDataObject();

                // permission based on socState
                String socState = wrapper.getSocInfo().getStateKey();
                socState = socState==null?null:socState.substring(socState.lastIndexOf('.')+1);
                attributes.put("socState", socState);
            }
            if(document.getOldMaintainableObject() instanceof ActivityOfferingMaintainableImpl){
                ActivityOfferingMaintainableImpl theForm = (ActivityOfferingMaintainableImpl) document.getOldMaintainableObject();
                ActivityOfferingWrapper wrapper = (ActivityOfferingWrapper) theForm.getDataObject();

                // permission based on socState
                String socState = wrapper.getSocInfo().getStateKey();
                socState = socState==null?null:socState.substring(socState.lastIndexOf('.')+1);
                attributes.put("socState", socState);
            }
        }
        super.addPermissionDetails(primaryDataObjectOrDocument, attributes);    //To change body of overridden methods use File | Settings | File Templates.
    }

    @Override
    protected boolean isAuthorizedByTemplate(View view, Component component, ViewModel model,
                                             String permissionTemplateName, Person user, Map<String, String> additionalPermissionDetails,
                                             Map<String, String> additionalRoleQualifications, boolean checkPermissionExistence) {
        if(additionalRoleQualifications == null){
            //Instantiate if null was passed in
            additionalRoleQualifications = new HashMap<String, String>();
        }
        addRoleQualification(model, additionalRoleQualifications);

        Map<String, String> permissionDetails = new HashMap<String, String>();
        Map<String, String> roleQualifications = new HashMap<String, String>();

        if (additionalPermissionDetails != null) {
            permissionDetails.putAll(additionalPermissionDetails);
        }

        if (additionalRoleQualifications != null) {
            roleQualifications.putAll(additionalRoleQualifications);
        }

        Object dataObjectForContext = getDataObjectContext(view, model);

        // add permission details depending on the type of component
        if (component instanceof Field) {
            permissionDetails.putAll(getFieldPermissionDetails(view, dataObjectForContext, (Field) component));
        } else if (component instanceof Group) {
            permissionDetails.putAll(getGroupPermissionDetails(view, dataObjectForContext, (Group) component));
        } else if (component instanceof Widget) {
            permissionDetails.putAll(getWidgetPermissionDetails(view, dataObjectForContext, (Widget) component));
        } else if (component instanceof Action) {
            permissionDetails.putAll(getActionPermissionDetails(view, dataObjectForContext, (Action) component));
        }

        // pick up additional attributes and overrides from component security
        ComponentSecurity componentSecurity = component.getComponentSecurity();

        // add configured overrides
        if (StringUtils.isNotBlank(componentSecurity.getNamespaceAttribute())) {
            permissionDetails.put(KimConstants.AttributeConstants.NAMESPACE_CODE,
                    componentSecurity.getNamespaceAttribute());
        }
        if (StringUtils.isNotBlank(componentSecurity.getComponentAttribute())) {
            permissionDetails.put(KimConstants.AttributeConstants.COMPONENT_NAME,
                    componentSecurity.getComponentAttribute());
        }
        if (StringUtils.isNotBlank(componentSecurity.getIdAttribute())) {
            if (component instanceof Field) {
                permissionDetails.put(KimConstants.AttributeConstants.FIELD_ID, componentSecurity.getIdAttribute());
            } else if (component instanceof Group) {
                permissionDetails.put(KimConstants.AttributeConstants.GROUP_ID, componentSecurity.getIdAttribute());
            } else if (component instanceof Widget) {
                permissionDetails.put(KimConstants.AttributeConstants.WIDGET_ID, componentSecurity.getIdAttribute());
            }
        }

        if (componentSecurity.getAdditionalPermissionDetails() != null) {
            permissionDetails.putAll(componentSecurity.getAdditionalPermissionDetails());
        }

        if (componentSecurity.getAdditionalRoleQualifiers() != null) {
            roleQualifications.putAll(componentSecurity.getAdditionalRoleQualifiers());
        }

        // Permissions based on socState (for CO and AO), and term registration start date (for AO)
        if (dataObjectForContext instanceof ActivityOfferingWrapper) {
            ActivityOfferingWrapper theForm = (ActivityOfferingWrapper) dataObjectForContext;
            // Term Registration Start Date
            Date termRegStartDate = theForm.getTermRegStartDate();
            Date now = new Date();
            if (termRegStartDate == null || now.before(termRegStartDate)) {
                permissionDetails.put("termRegStartDateLater", "true");
            }
            // SOC State
            if (theForm.getSocInfo() != null) {
                String socState = theForm.getSocInfo().getStateKey();
                socState = socState==null?null:socState.substring(socState.lastIndexOf('.')+1);
                permissionDetails.put("socState", socState);
            }
        }
        if (dataObjectForContext instanceof CourseOfferingEditWrapper) {
            CourseOfferingEditWrapper theForm = (CourseOfferingEditWrapper) dataObjectForContext;
            // SOC State
            if (theForm.getSocInfo() != null) {
                String socState = theForm.getSocInfo().getStateKey();
                socState = socState==null?null:socState.substring(socState.lastIndexOf('.')+1);
                permissionDetails.put("socState", socState);
            }
        }
        if (dataObjectForContext instanceof CourseOfferingCreateWrapper) {
            CourseOfferingCreateWrapper theForm = (CourseOfferingCreateWrapper) dataObjectForContext;
            // SOC State
            if (theForm.getSocInfo() != null) {
                String socState = theForm.getSocInfo().getStateKey();
                socState = socState==null?null:socState.substring(socState.lastIndexOf('.')+1);
                permissionDetails.put("socState", socState);
            }
        }

        boolean result = true;
        if (!checkPermissionExistence || (checkPermissionExistence && permissionExistsByTemplate(dataObjectForContext,
                "KS-ENR", permissionTemplateName, permissionDetails))) {
            result = isAuthorizedByTemplate(dataObjectForContext, "KS-ENR", permissionTemplateName,
                    user.getPrincipalId(), permissionDetails, roleQualifications);

            if (LOG.isDebugEnabled()) {
                LOG.debug("Performed permission check for: " + permissionTemplateName + " and got result: " + result);
            }
        }

        return result;
    }

    @Override
    public boolean canOpenView(View view, ViewModel model, Person user) {
        Map<String, String> additionalPermissionDetails = new HashMap<String, String>();
        additionalPermissionDetails.put(KimConstants.AttributeConstants.NAMESPACE_CODE, view.getNamespaceCode());
        additionalPermissionDetails.put(KimConstants.AttributeConstants.VIEW_ID, model.getViewId());

        if (permissionExistsByTemplate(model, "KS-ENR",
                KimConstants.PermissionTemplateNames.OPEN_VIEW, additionalPermissionDetails)) {
            return isAuthorizedByTemplate(model, "KS-ENR",
                    KimConstants.PermissionTemplateNames.OPEN_VIEW, user.getPrincipalId(), additionalPermissionDetails,
                    null);
        }

        return true;
    }

    @Override
    public boolean canEditView(View view, ViewModel model, Person user) {
        DocumentFormBase documentForm = (DocumentFormBase) model;

        Map<String, String> additionalPermissionDetails = new HashMap<String, String>();
        additionalPermissionDetails.put(KimConstants.AttributeConstants.NAMESPACE_CODE, view.getNamespaceCode());
        additionalPermissionDetails.put(KimConstants.AttributeConstants.VIEW_ID, model.getViewId());

        if (permissionExistsByTemplate(model, "KS-ENR",
                KimConstants.PermissionTemplateNames.EDIT_VIEW, additionalPermissionDetails)) {
            if (getDocumentAuthorizer() != null) {
                return isAuthorizedByTemplate(model, "KS-ENR",
                        KimConstants.PermissionTemplateNames.EDIT_VIEW, user.getPrincipalId(), additionalPermissionDetails,
                        null) && canEdit(documentForm.getDocument(), user);
            } else {
                return isAuthorizedByTemplate(model, "KS-ENR",
                        KimConstants.PermissionTemplateNames.EDIT_VIEW, user.getPrincipalId(), additionalPermissionDetails,
                        null);
            }
        }

        return super.canEditView(view, model, user) && canEdit(documentForm.getDocument(), user);

    }

    @Override
    public boolean canViewGroup(View view, ViewModel model, Group group, String groupId, Person user) {
        boolean bRet = super.canViewGroup(view, model, group, groupId, user);    //To change body of overridden methods use File | Settings | File Templates.

        if(!bRet){
            String messageKey = "error." + view.getId() + "." + groupId;

            if(getMessageService().getMessageText(messageKey) != null){ // if the message isn't configured, then don't display any error
                GlobalVariables.getMessageMap().putError(view.getId(), messageKey);
            }

            if (model != null && model instanceof MaintenanceDocumentForm) {
                MaintenanceDocumentForm form = (MaintenanceDocumentForm) model;
                if (form.getDocument().getNewMaintainableObject().getDataObject() instanceof CourseOfferingCreateWrapper) {
                    CourseOfferingCreateWrapper coWrapper = ((CourseOfferingCreateWrapper) form.getDocument().getNewMaintainableObject().getDataObject());
                    coWrapper.setEnableCreateButton(false);
                }
            }
        }

        return bRet;
    }

    public MessageService getMessageService() {
        if(messageService == null){
            messageService = KRADServiceLocatorWeb.getMessageService();
        }

        return messageService;
    }

    public void setMessageService(MessageService messageService) {
        this.messageService = messageService;
    }

}