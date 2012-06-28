package org.kuali.student.enrollment.class2.courseoffering.keyvalue;

import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.rice.core.api.util.ConcreteKeyValue;
import org.kuali.rice.core.api.util.KeyValue;
import org.kuali.rice.krad.uif.control.UifKeyValuesFinderBase;
import org.kuali.rice.krad.uif.view.ViewModel;
import org.kuali.rice.krad.util.GlobalVariables;
import org.kuali.rice.krad.web.form.MaintenanceForm;
import org.kuali.student.enrollment.class2.courseoffering.dto.CourseOfferingEditWrapper;
import org.kuali.student.enrollment.class2.courseoffering.dto.FormatOfferingWrapper;
import org.kuali.student.enrollment.courseoffering.dto.CourseOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.FormatOfferingInfo;

import org.kuali.student.enrollment.courseoffering.service.CourseOfferingService;
import org.kuali.student.lum.course.dto.FormatInfo;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.LocaleInfo;
import org.kuali.student.r2.common.util.constants.CourseOfferingServiceConstants;

import javax.xml.namespace.QName;
import java.io.Serializable;
import java.util.List;
import java.util.ArrayList;
import java.util.Locale;

public class FormatOfferingTypeForEditCOTypeKeyValues extends UifKeyValuesFinderBase implements Serializable {
    private static final long serialVersionUID = 1L;
    private ContextInfo contextInfo;
    private transient CourseOfferingService courseOfferingService;

    @Override
    public List<KeyValue> getKeyValues(ViewModel model) {
        MaintenanceForm form = (MaintenanceForm)model;
        CourseOfferingEditWrapper coEditWrapper = (CourseOfferingEditWrapper)form.getDocument().getNewMaintainableObject().getDataObject();

        List<KeyValue> keyValues = new ArrayList<KeyValue>();
        List<FormatInfo> formatOptions = getFormatOptions(coEditWrapper);
        List<String> availableFormatTypes;
        try{
            availableFormatTypes = getAvailableFormatTypes(coEditWrapper);
        }catch(Exception e){
            availableFormatTypes = new ArrayList<String>();
        }

        for (FormatInfo format : formatOptions) {
            ConcreteKeyValue keyValue = new ConcreteKeyValue();
            if(!availableFormatTypes.contains(format.getId())){
                keyValue.setKey(format.getId());
                keyValue.setValue(format.getType());
                keyValues.add(keyValue);
            }
        }

        return keyValues;
    }

    private List<FormatInfo> getFormatOptions(CourseOfferingEditWrapper coWrapper) {
        if(coWrapper!=null && coWrapper.getCourse() != null){
            return coWrapper.getCourse().getFormats();
        }   else {
            return null;
        }

    }

    private List<String> getAvailableFormatTypes(CourseOfferingEditWrapper coEditWrapper) throws Exception {
        List<String> availableFormatTypes = new ArrayList<String>();
        List <FormatOfferingWrapper> formatOfferingWrapperList= coEditWrapper.getFormatOfferingWrapperList();
        for (FormatOfferingWrapper foWrapper:formatOfferingWrapperList){
            availableFormatTypes.add(foWrapper.getFormatOfferingInfo().getFormatId());
        }
//        CourseOfferingInfo coInfo = coWrapper.getCoInfo();
//        List<FormatOfferingInfo> formats = getCourseOfferingService().getFormatOfferingsByCourseOffering(coInfo.getId(), getContextInfo());
//        for (FormatOfferingInfo format : formats){
//            availableFormatTypes.add(format.getFormatId());
//        }
        return availableFormatTypes;

    }

    protected CourseOfferingService getCourseOfferingService() {
        if (courseOfferingService == null) {
            courseOfferingService = (CourseOfferingService) GlobalResourceLoader.getService(new QName(CourseOfferingServiceConstants.NAMESPACE, "CourseOfferingService"));
        }
        return courseOfferingService;
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

