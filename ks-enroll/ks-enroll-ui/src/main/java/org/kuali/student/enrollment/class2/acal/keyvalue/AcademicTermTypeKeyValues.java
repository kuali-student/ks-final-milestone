package org.kuali.student.enrollment.class2.acal.keyvalue;

import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.rice.core.api.util.ConcreteKeyValue;
import org.kuali.rice.core.api.util.KeyValue;
import org.kuali.rice.krad.uif.control.UifKeyValuesFinderBase;
import org.kuali.rice.krad.uif.view.ViewModel;
import org.kuali.student.enrollment.acal.constants.AcademicCalendarServiceConstants;
import org.kuali.student.enrollment.acal.service.AcademicCalendarService;
import org.kuali.student.enrollment.class2.acal.dto.AcademicTermWrapper;
import org.kuali.student.enrollment.class2.acal.form.AcademicCalendarForm;
import org.kuali.student.r2.common.constants.CommonServiceConstants;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.core.type.dto.TypeInfo;

import javax.xml.namespace.QName;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class AcademicTermTypeKeyValues extends UifKeyValuesFinderBase implements Serializable {

    private static final long serialVersionUID = 1L;

    private transient AcademicCalendarService acalService;


    public AcademicCalendarService getAcalService() {
        if(acalService == null) {
            acalService = (AcademicCalendarService) GlobalResourceLoader.getService(new QName(CommonServiceConstants.REF_OBJECT_URI_GLOBAL_PREFIX + "acal", "AcademicCalendarService"));
        }
        return this.acalService;
    }

    @Override
    public List<KeyValue> getKeyValues(ViewModel model) {

        List<KeyValue> keyValues = new ArrayList<KeyValue>();
        List<String> availableTermTypes = new ArrayList();

        if (model instanceof AcademicCalendarForm){
            AcademicCalendarForm acalForm = (AcademicCalendarForm)model;

            for (AcademicTermWrapper termWrapper : acalForm.getTermWrapperList()) {
                 availableTermTypes.add(termWrapper.getTermType());
            }
        }

        //TODO:Build real context.
        ContextInfo context = new ContextInfo();
        List<TypeInfo> types = null;
        try {
            //FIXME: Should not call services for each collection row. Get all the available types once at the start.. not sure the init method to do that...
            types = getAcalService().getTermTypesForAcademicCalendarType(AcademicCalendarServiceConstants.ACADEMIC_CALENDAR_TYPE_KEY,context);

            for (TypeInfo type : types) {
                if (!availableTermTypes.contains(type.getKey())){
                    ConcreteKeyValue keyValue = new ConcreteKeyValue();
                    keyValue.setKey(type.getKey());
                    keyValue.setValue(type.getName());
                    keyValues.add(keyValue);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return keyValues;
    }
}
