/**
 * Copyright 2012 The Kuali Foundation Licensed under the
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
 * Created by vgadiyak on 5/30/12
 */
package org.kuali.student.enrollment.class2.courseoffering.service.impl;

import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.rice.core.api.util.KeyValue;
import org.kuali.rice.krad.maintenance.MaintainableImpl;
import org.kuali.rice.krad.maintenance.MaintenanceDocument;
import org.kuali.rice.krad.uif.control.CheckboxGroupControl;
import org.kuali.rice.krad.uif.field.InputField;
import org.kuali.rice.krad.uif.field.DataField;
import org.kuali.rice.krad.util.GlobalVariables;
import org.kuali.rice.krad.util.KRADConstants;
import org.kuali.rice.krad.web.form.MaintenanceForm;
import org.kuali.student.enrollment.class2.courseoffering.dto.ActivityOfferingWrapper;
import org.kuali.student.enrollment.class2.courseoffering.dto.CourseOfferingEditWrapper;
import org.kuali.student.enrollment.class2.courseoffering.keyvalue.StudentRegistrationOptionsKeyValues;
import org.kuali.student.enrollment.class2.courseoffering.service.CourseOfferingEditMaintainable;
import org.kuali.student.enrollment.courseoffering.dto.ActivityOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.CourseOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.FormatOfferingInfo;
import org.kuali.student.enrollment.courseoffering.service.CourseOfferingService;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.LocaleInfo;
import org.kuali.student.r2.common.util.constants.CourseOfferingServiceConstants;
import org.kuali.student.r2.common.util.constants.LuiServiceConstants;
import org.kuali.student.r2.common.util.constants.StateServiceConstants;
import org.kuali.student.r2.common.util.constants.TypeServiceConstants;
import org.kuali.student.r2.core.state.service.StateService;
import org.kuali.student.r2.core.type.service.TypeService;

import javax.xml.namespace.QName;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * This class //TODO ...
 *
 * @author Kuali Student Team
 */
public class CourseOfferingEditMaintainableImpl extends MaintainableImpl implements CourseOfferingEditMaintainable {

    private transient CourseOfferingService courseOfferingService;
    private ContextInfo contextInfo;
    private transient TypeService typeService;
    private transient StateService stateService;


    //TODO : implement the functionality for Personnel section and its been delayed now since the backend implementation is not yet ready (06/06/2012).

    @Override
    public void saveDataObject() {
        if(getMaintenanceAction().equals(KRADConstants.MAINTENANCE_NEW_ACTION) ||
                getMaintenanceAction().equals(KRADConstants.MAINTENANCE_COPY_ACTION)) {
            try {
                ActivityOfferingWrapper activityOfferingWrapper = (ActivityOfferingWrapper) getDataObject();
                ActivityOfferingInfo activityOfferingInfo = getCourseOfferingService().createActivityOffering(activityOfferingWrapper.getAoInfo().getFormatOfferingId(), activityOfferingWrapper.getAoInfo().getActivityId(), LuiServiceConstants.LECTURE_ACTIVITY_OFFERING_TYPE_KEY, activityOfferingWrapper.getAoInfo(),getContextInfo());
                setDataObject(new ActivityOfferingWrapper(activityOfferingInfo));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        else {   //should be edit action
            ActivityOfferingWrapper activityOfferingWrapper = (ActivityOfferingWrapper) getDataObject();
            try {
                ActivityOfferingInfo activityOfferingInfo = getCourseOfferingService().updateActivityOffering(activityOfferingWrapper.getAoInfo().getId(), activityOfferingWrapper.getAoInfo(), getContextInfo());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public Object retrieveObjectForEditOrCopy(MaintenanceDocument document, Map<String, String> dataObjectKeys) {
        try {
            CourseOfferingInfo info = getCourseOfferingService().getCourseOffering(dataObjectKeys.get("coInfo.id"), getContextInfo());
            CourseOfferingEditWrapper formObject = new CourseOfferingEditWrapper(info);
            List<FormatOfferingInfo> formats = getCourseOfferingService().getFormatOfferingsByCourseOffering(dataObjectKeys.get("coInfo.id"), getContextInfo());
            formObject.setFormatOfferings(formats);
            document.getNewMaintainableObject().setDataObject(formObject);
            document.getOldMaintainableObject().setDataObject(formObject);
//            StateInfo state = getStateService().getState(formObject.getDto().getStateKey(), getContextInfo());
//            formObject.setStateName(state.getName());
            return formObject;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void populateStudentRegOptionsKeyValues (InputField field, MaintenanceForm mForm) {

        StudentRegistrationOptionsKeyValues studentRegistrationOptions = new StudentRegistrationOptionsKeyValues();
        List<KeyValue> keyValues = new ArrayList<KeyValue>();

        keyValues = studentRegistrationOptions.getKeyValues(mForm);
        if (keyValues.isEmpty()) {
            field.setRender(false);
            return;
        }

        ((CheckboxGroupControl) field.getControl()).setOptions(keyValues);
    }

    public void populateStudentRegOptionsNone (DataField field, MaintenanceForm mForm) {

        StudentRegistrationOptionsKeyValues studentRegistrationOptions = new StudentRegistrationOptionsKeyValues();
        List<KeyValue> keyValues = new ArrayList<KeyValue>();

        keyValues = studentRegistrationOptions.getKeyValues(mForm);
        if (!keyValues.isEmpty()) {
            field.setRender(false);
            return;
        }
    }

    @Override
    public void processAfterNew(MaintenanceDocument document, Map<String, String[]> requestParameters) {
        ActivityOfferingWrapper wrapper = (ActivityOfferingWrapper)document.getNewMaintainableObject().getDataObject();
        document.getDocumentHeader().setDocumentDescription("Activity Offering");
        try {
//            StateInfo state = getStateService().getState(wrapper.getDto().getStateKey(), getContextInfo());
//            wrapper.setStateName(state.getName());
        } catch (Exception e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

    public ContextInfo getContextInfo() {
        if (null == contextInfo) {
            contextInfo = new ContextInfo();
            contextInfo.setAuthenticatedPrincipalId(GlobalVariables.getUserSession().getPrincipalId());
            contextInfo.setPrincipalId(GlobalVariables.getUserSession().getPrincipalId());
            LocaleInfo localeInfo = new LocaleInfo();
            localeInfo.setLocaleLanguage(Locale.getDefault().getLanguage());
            localeInfo.setLocaleRegion(Locale.getDefault().getCountry());
            contextInfo.setLocale(localeInfo);
        }
        return contextInfo;
    }

    public TypeService getTypeService() {
        if(typeService == null) {
            typeService = (TypeService) GlobalResourceLoader.getService(new QName(TypeServiceConstants.NAMESPACE, TypeServiceConstants.SERVICE_NAME_LOCAL_PART));
        }
        return this.typeService;
    }

    public StateService getStateService() {
        if(stateService == null) {
            stateService = (StateService) GlobalResourceLoader.getService(new QName(StateServiceConstants.NAMESPACE, StateServiceConstants.SERVICE_NAME_LOCAL_PART));
        }
        return stateService;
    }

    protected CourseOfferingService getCourseOfferingService() {
        if (courseOfferingService == null) {
            courseOfferingService = (CourseOfferingService) GlobalResourceLoader.getService(new QName(CourseOfferingServiceConstants.NAMESPACE, "CourseOfferingService"));
        }
        return courseOfferingService;
    }
}
