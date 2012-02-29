package org.kuali.student.enrollment.class2.acal.keyvalue;

import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.rice.core.api.util.ConcreteKeyValue;
import org.kuali.rice.core.api.util.KeyValue;
import org.kuali.rice.krad.uif.control.UifKeyValuesFinderBase;
import org.kuali.rice.krad.uif.view.ViewModel;
import org.kuali.student.enrollment.acal.constants.AcademicCalendarServiceConstants;
import org.kuali.student.enrollment.acal.service.AcademicCalendarService;
import org.kuali.student.enrollment.class2.acal.form.AcademicCalendarForm;
import org.kuali.student.r2.common.constants.CommonServiceConstants;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.core.type.dto.TypeInfo;
import org.kuali.student.test.utilities.TestHelper;

import javax.xml.namespace.QName;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class TermTypeKeyValues  extends UifKeyValuesFinderBase implements Serializable {

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

        //TODO:Build real context.
        ContextInfo context = TestHelper.getContext1();
        List<TypeInfo> types = null;
        try {
            types = getAcalService().getTermTypesForAcademicCalendarType(AcademicCalendarServiceConstants.ACADEMIC_CALENDAR_TYPE_KEY,context);

            for (TypeInfo type : types) {
                ConcreteKeyValue keyValue = new ConcreteKeyValue();
                keyValue.setKey(type.getKey());
                keyValue.setValue(type.getName());
                keyValues.add(keyValue);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return keyValues;
    }
}
