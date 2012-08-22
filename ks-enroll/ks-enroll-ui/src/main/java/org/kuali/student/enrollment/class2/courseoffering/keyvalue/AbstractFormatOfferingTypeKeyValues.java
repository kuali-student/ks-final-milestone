package org.kuali.student.enrollment.class2.courseoffering.keyvalue;

import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.rice.core.api.util.ConcreteKeyValue;
import org.kuali.rice.core.api.util.KeyValue;
import org.kuali.rice.krad.uif.control.UifKeyValuesFinderBase;
import org.kuali.rice.krad.uif.view.ViewModel;
import org.kuali.rice.krad.util.GlobalVariables;
import org.kuali.student.enrollment.class2.courseoffering.util.CourseOfferingResourceLoader;
import org.kuali.student.enrollment.courseoffering.service.CourseOfferingService;
import org.kuali.student.r2.core.class1.type.dto.TypeInfo;
import org.kuali.student.r2.core.class1.type.service.TypeService;
import org.kuali.student.r2.lum.course.dto.ActivityInfo;
import org.kuali.student.r2.lum.course.dto.FormatInfo;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.LocaleInfo;
import org.kuali.student.r2.common.util.constants.CourseOfferingServiceConstants;

import javax.xml.namespace.QName;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 *
 * @author: swedev
 * Date: 6/22/12
 * Time: 3:59 PM
 */
public abstract class AbstractFormatOfferingTypeKeyValues extends UifKeyValuesFinderBase implements Serializable {
    private static final long serialVersionUID = 1L;
    private ContextInfo contextInfo;
    private TypeService typeService;
    private transient CourseOfferingService courseOfferingService;

    @Override
    public List<KeyValue> getKeyValues(ViewModel model) {

        List<KeyValue> keyValues = new ArrayList<KeyValue>();
        keyValues.add(new ConcreteKeyValue("", "Select Format Type"));
        List<FormatInfo> formatOptions = getFormats(model);
        List<String> existingFormatIds;
        try{
            existingFormatIds = getExistingFormatIdsFromFormatOfferings(model);

            for (FormatInfo format : formatOptions) {
                ConcreteKeyValue keyValue = new ConcreteKeyValue();
                if(!existingFormatIds.contains(format.getId())){
                    keyValue.setKey(format.getId());
                    // TODO: fix R2 Format to include name and short name
                    //keyValue.setValue("FIX ME!");

                    //Bonnie: this is only a temporary walk-around solution.
                    //Still need to address the issue that FormatInfo does not include name and short name
                    List<ActivityInfo> activityInfos = format.getActivities();
                    StringBuffer st = new StringBuffer();
                    for (ActivityInfo activityInfo : activityInfos) {
                        TypeInfo activityType = getTypeService().getType(activityInfo.getTypeKey(), getContextInfo());
                        st.append(activityType.getName()+"/");
                    }
                    String name =st.toString();
                    name=name.substring(0,name.length()-1);
                    keyValue.setValue(name);
                    keyValues.add(keyValue);
                }
            }
        } catch(Exception e) {
            existingFormatIds = new ArrayList<String>();
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

    public TypeService getTypeService() {
        if(typeService == null) {
            typeService = CourseOfferingResourceLoader.loadTypeService();
        }
        return this.typeService;
    }

    protected abstract List<String> getExistingFormatIdsFromFormatOfferings(ViewModel model) throws Exception ;

    protected abstract List<FormatInfo> getFormats(ViewModel model);
}
