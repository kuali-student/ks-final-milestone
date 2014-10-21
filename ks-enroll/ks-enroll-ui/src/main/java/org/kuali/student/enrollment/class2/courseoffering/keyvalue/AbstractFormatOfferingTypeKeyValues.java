package org.kuali.student.enrollment.class2.courseoffering.keyvalue;

import org.kuali.rice.core.api.util.ConcreteKeyValue;
import org.kuali.rice.core.api.util.KeyValue;
import org.kuali.rice.krad.uif.control.UifKeyValuesFinderBase;
import org.kuali.rice.krad.uif.view.ViewModel;
import org.kuali.student.enrollment.class2.courseoffering.util.CourseOfferingManagementUtil;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.common.util.security.ContextUtils;
import org.kuali.student.r2.core.class1.type.dto.TypeInfo;
import org.kuali.student.r2.lum.course.dto.ActivityInfo;
import org.kuali.student.r2.lum.course.dto.FormatInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

    private static final Logger LOG = LoggerFactory.getLogger(AbstractFormatOfferingTypeKeyValues.class);

    @Override
    public List<KeyValue> getKeyValues(ViewModel model) {
        List<KeyValue> keyValues = new ArrayList<KeyValue>();
        keyValues.add(new ConcreteKeyValue("", "Select Format Type"));
        List<FormatInfo> formatOptions = getFormats(model);
        if(formatOptions!=null){
            List<String> existingFormatIds;

            try{
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
    protected abstract List<String> getExistingFormatIdsFromFormatOfferings(ViewModel model) throws Exception ;

    protected abstract List<FormatInfo> getFormats(ViewModel model);
}
