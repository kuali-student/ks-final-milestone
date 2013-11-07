package org.kuali.student.enrollment.class2.courseoffering.keyvalue;

import org.kuali.rice.core.api.util.ConcreteKeyValue;
import org.kuali.rice.core.api.util.KeyValue;
import org.kuali.rice.krad.uif.control.UifKeyValuesFinderBase;
import org.kuali.rice.krad.uif.view.ViewModel;
import org.kuali.rice.krad.web.form.MaintenanceDocumentForm;
import org.kuali.student.enrollment.class2.courseoffering.dto.ActivityOfferingWrapper;
import org.kuali.student.enrollment.class2.courseoffering.dto.CourseOfferingEditWrapper;
import org.kuali.student.enrollment.class2.courseoffering.dto.CourseOfferingWrapper;
import org.kuali.student.enrollment.class2.courseoffering.util.CourseOfferingManagementUtil;
import org.kuali.student.enrollment.courseoffering.dto.ActivityOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.CourseOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.FormatOfferingInfo;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.util.ContextUtils;
import org.kuali.student.r2.core.class1.state.dto.StateInfo;
import org.kuali.student.r2.core.class1.type.dto.TypeInfo;
import org.kuali.student.r2.lum.course.dto.ActivityInfo;
import org.kuali.student.r2.lum.course.dto.FormatInfo;

import javax.xml.namespace.QName;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author: swedev
 * Date: 6/22/12
 * Time: 3:59 PM
 */
public abstract class AbstractFormatOfferingTypeKeyValues extends UifKeyValuesFinderBase implements Serializable {

    private static final long serialVersionUID = 1L;

    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(AbstractFormatOfferingTypeKeyValues.class);

    @Override
    public List<KeyValue> getKeyValues(ViewModel model) {
        MaintenanceDocumentForm form = (MaintenanceDocumentForm) model;
        CourseOfferingEditWrapper coEditWrapper = new CourseOfferingEditWrapper();
        CourseOfferingWrapper wrapper = (CourseOfferingWrapper) form.getDocument().getNewMaintainableObject().getDataObject();
        List<KeyValue> keyValues = new ArrayList<KeyValue>();
        keyValues.add(new ConcreteKeyValue("", "Select Format Type"));
        List<FormatInfo> formatOptions = getFormats(model);
        if(formatOptions!=null){
            List<String> existingFormatIds;

            try{
                if (wrapper instanceof CourseOfferingEditWrapper) {
                    coEditWrapper = (CourseOfferingEditWrapper) form.getDocument().getNewMaintainableObject().getDataObject();
                    if (wrapper.getCourseOfferingInfo().getId() != null && coEditWrapper.getAoWrapperList()== null) {
                        loadActivityOfferingsByCourseOffering(wrapper.getCourseOfferingInfo(), coEditWrapper);
                    }
                }
                existingFormatIds = getExistingFormatIdsFromFormatOfferings(model);
                ContextInfo contextInfo = ContextUtils.createDefaultContextInfo();
                for (FormatInfo format : formatOptions) {
                    ConcreteKeyValue keyValue = new ConcreteKeyValue();
                    if(!existingFormatIds.contains(format.getId())){
                        keyValue.setKey(format.getId());
                        // TODO: fix R2 Format to include name and short name
                        //keyValue.setValue("FIX ME!");

                        //Bonnie: this is only a temporary walk-around solution.
                        //Still need to address the issue that FormatInfo does not include name and short name
                        //JIRA FIX : KSENROLL-8731 - Replaced StringBuffer with StringBuilder
                        List<ActivityInfo> activityInfos = format.getActivities();
                        StringBuilder st = new StringBuilder();
                        if (coEditWrapper.getAoWrapperList() != null && activityInfos.size() > 0) {
                            FormatOfferingInfo aoformatOfferingInfo = coEditWrapper.getAoWrapperList().get(0).getFormatOffering();
                            if (!aoformatOfferingInfo.getFinalExamLevelTypeKey().equals(activityInfos.get(0).getTypeKey())) {
                                List<ActivityInfo> newactivityInfos = new ArrayList<ActivityInfo>();
                                newactivityInfos.add(activityInfos.get(0));
                                activityInfos.remove(0);
                                activityInfos.add(newactivityInfos.get(0));
                            }
                        }
                        for (ActivityInfo activityInfo : activityInfos) {
                            TypeInfo activityType = CourseOfferingManagementUtil.getTypeService().getType(activityInfo.getTypeKey(), contextInfo);

                            st.append(activityType.getName()+"/");
                        }
                        String name =st.toString();
                        name=name.substring(0,name.length()-1);
                        keyValue.setValue(name);
                        keyValues.add(keyValue);
                    }
                }
            } catch(Exception e) {
                LOG.error("Error finding format offering types", e);
            }
        }
        return keyValues;
    }

    private void loadActivityOfferingsByCourseOffering(CourseOfferingInfo theCourseOfferingInfo, CourseOfferingEditWrapper formObject) throws Exception {
        String courseOfferingId = theCourseOfferingInfo.getId();
        List<ActivityOfferingInfo> activityOfferingInfoList;
        List<ActivityOfferingWrapper> activityOfferingWrapperList;
        try {

            activityOfferingInfoList = CourseOfferingManagementUtil.getCourseOfferingService().getActivityOfferingsByCourseOffering(courseOfferingId, ContextUtils.createDefaultContextInfo());
            activityOfferingWrapperList = new ArrayList<ActivityOfferingWrapper>(activityOfferingInfoList.size());

            for (ActivityOfferingInfo info : activityOfferingInfoList) {
                ActivityOfferingWrapper aoWrapper = convertAOInfoToWrapper_Simple(info);
                activityOfferingWrapperList.add(aoWrapper);
            }
        } catch (Exception e) {
            throw new RuntimeException(String.format("Could not load AOs for course offering [%s].", courseOfferingId), e);
        }
        formObject.setAoWrapperList(activityOfferingWrapperList);
    }

    private ActivityOfferingWrapper convertAOInfoToWrapper_Simple(ActivityOfferingInfo aoInfo) throws Exception{

        ActivityOfferingWrapper aoWrapper = new ActivityOfferingWrapper(aoInfo);

        ContextInfo contextInfo = ContextUtils.createDefaultContextInfo();

        StateInfo state = CourseOfferingManagementUtil.getStateService().getState(aoInfo.getStateKey(), contextInfo);
        aoWrapper.setStateName(state.getName());

        TypeInfo typeInfo = CourseOfferingManagementUtil.getTypeService().getType(aoInfo.getTypeKey(), contextInfo);
        aoWrapper.setTypeName(typeInfo.getName());

        FormatOfferingInfo fo = CourseOfferingManagementUtil.getCourseOfferingService().getFormatOffering(aoInfo.getFormatOfferingId(), contextInfo);
        aoWrapper.setFormatOffering(fo);
        return aoWrapper;
    }

    protected abstract List<String> getExistingFormatIdsFromFormatOfferings(ViewModel model) throws Exception ;

    protected abstract List<FormatInfo> getFormats(ViewModel model);
}
