package org.kuali.student.enrollment.class2.courseoffering.keyvalue;

import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.rice.core.api.util.ConcreteKeyValue;
import org.kuali.rice.core.api.util.KeyValue;
import org.kuali.rice.krad.uif.control.UifKeyValuesFinderBase;
import org.kuali.rice.krad.uif.view.ViewModel;
import org.kuali.rice.krad.util.GlobalVariables;
import org.kuali.student.enrollment.courseoffering.service.CourseOfferingService;
import org.kuali.student.lum.course.dto.FormatInfo;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.LocaleInfo;
import org.kuali.student.r2.common.util.constants.CourseOfferingServiceConstants;

import javax.xml.namespace.QName;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created with IntelliJ IDEA.
 * User: swedev
 * Date: 6/22/12
 * Time: 3:59 PM
 * To change this template use File | Settings | File Templates.
 */
public abstract class AbstractFormatOfferingTypeKeyValues extends UifKeyValuesFinderBase implements Serializable {

    private ContextInfo contextInfo;
    private transient CourseOfferingService courseOfferingService;

    @Override
    public List<KeyValue> getKeyValues(ViewModel model) {

        List<KeyValue> keyValues = new ArrayList<KeyValue>();
        List<FormatInfo> formatOptions = getFormats(model);
        List<String> availableFormatTypes;
        try{
            availableFormatTypes = getAvailableFormatTypes(model);
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

    protected abstract List<String> getAvailableFormatTypes(ViewModel model) throws Exception ;

    protected abstract List<FormatInfo> getFormats(ViewModel model);
}
