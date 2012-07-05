package org.kuali.student.enrollment.class2.courseoffering.keyvalue;

import org.kuali.rice.core.api.util.ConcreteKeyValue;
import org.kuali.rice.core.api.util.KeyValue;
import org.kuali.rice.krad.uif.control.UifKeyValuesFinderBase;
import org.kuali.rice.krad.uif.view.ViewModel;
import org.kuali.rice.krad.util.GlobalVariables;
import org.kuali.rice.krad.web.form.MaintenanceForm;

import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.LocaleInfo;
import org.kuali.student.enrollment.class2.courseoffering.dto.CourseOfferingEditWrapper;
import org.kuali.student.enrollment.class2.courseoffering.dto.FormatOfferingWrapper;
import org.kuali.student.enrollment.class2.courseoffering.util.CourseOfferingResourceLoader;
import org.kuali.student.enrollment.courseoffering.service.CourseOfferingService;
import org.kuali.student.lum.course.dto.FormatInfo;
import org.kuali.student.lum.course.service.CourseService;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class FormatOfferingTypeForEditCOTypeKeyValues extends UifKeyValuesFinderBase implements Serializable {
    private static final long serialVersionUID = 1L;
    private ContextInfo contextInfo;
    private transient CourseOfferingService courseOfferingService;
    private transient CourseService courseService;

    @Override
    public List<KeyValue> getKeyValues(ViewModel model) {
        MaintenanceForm form = (MaintenanceForm)model;
        CourseOfferingEditWrapper coEditWrapper = (CourseOfferingEditWrapper)form.getDocument().getNewMaintainableObject().getDataObject();

        List<KeyValue> keyValues = new ArrayList<KeyValue>();
        keyValues.add(new ConcreteKeyValue("", "Select Format Type"));

        List<FormatInfo> formatOptions;
        List<String> availableFormatTypes; 
        
        try {
            formatOptions = getFormatOptions(coEditWrapper);
            availableFormatTypes = getAvailableFormatTypes(coEditWrapper);
        } catch (Exception e) {
            throw new RuntimeException(e);            
        }
        for (FormatInfo format : formatOptions) {
            ConcreteKeyValue keyValue = new ConcreteKeyValue();
            if(!availableFormatTypes.contains(format.getId())){
                keyValue.setKey(format.getId());
                keyValue.setValue(format.getName());
                keyValues.add(keyValue);
            }
        }

        return keyValues;
    }

    private List<FormatInfo> getFormatOptions(CourseOfferingEditWrapper coEditWrapper) throws Exception{
        if(coEditWrapper!=null && coEditWrapper.getCourse() != null){
            return coEditWrapper.getCourse().getFormats();
        }else if(coEditWrapper!=null && coEditWrapper.getCourse() == null) {
            return (getCourseService().getCourse(coEditWrapper.getCoInfo().getCourseId())).getFormats();
        }
        else{
            throw new Exception("Error: CourseOfferingEditWrapper in this page is null");
        }
    }
    
    private List<String> getAvailableFormatTypes(CourseOfferingEditWrapper coEditWrapper) throws Exception {
        List<String> availableFormatTypes = new ArrayList<String>();
        List <FormatOfferingWrapper> formatOfferingWrapperList= coEditWrapper.getFormatOfferingWrapperList();
        for (FormatOfferingWrapper foWrapper:formatOfferingWrapperList){
            availableFormatTypes.add(foWrapper.getFormatOfferingInfo().getFormatId());
        }
        return availableFormatTypes;
    }

    public CourseService getCourseService() {
        if(courseService == null) {
            courseService = CourseOfferingResourceLoader.loadCourseService();
        }
        return this.courseService;
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
}

