package org.kuali.student.enrollment.class2.acal.keyvalue;

import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.rice.core.api.util.ConcreteKeyValue;
import org.kuali.rice.core.api.util.KeyValue;
import org.kuali.rice.krad.uif.control.UifKeyValuesFinderBase;
import org.kuali.rice.krad.uif.view.ViewModel;
import org.kuali.student.enrollment.acal.service.AcademicCalendarService;
import org.kuali.student.mock.utilities.TestHelper;
import org.kuali.student.r2.common.constants.CommonServiceConstants;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.state.dto.StateInfo;

import javax.xml.namespace.QName;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

//Core slice class.
@Deprecated
public class AcademicTermStateKeyValues extends UifKeyValuesFinderBase implements Serializable {

    private static final long serialVersionUID = 1L;

    private transient AcademicCalendarService acalService;

    private static List<StateInfo> termStates;

    @Override
    public List<KeyValue> getKeyValues(ViewModel model) {

        List<KeyValue> keyValues = new ArrayList<KeyValue>();

        try {
            List<StateInfo> states = getTermStates();
            for (StateInfo state : states) {
                ConcreteKeyValue keyValue = new ConcreteKeyValue();
                keyValue.setKey(state.getKey());
                keyValue.setValue(state.getName());
                keyValues.add(keyValue);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return keyValues;
    }

    private List<StateInfo> getTermStates() throws InvalidParameterException, MissingParameterException, PermissionDeniedException, OperationFailedException {
        if(termStates == null) {
            //TODO:Build real context.
            ContextInfo context = TestHelper.getContext1();

            termStates = Collections.unmodifiableList(getAcalService().getTermStates(context));
        }

        return termStates;
    }

    public AcademicCalendarService getAcalService() {
        if(acalService == null) {
            acalService = (AcademicCalendarService) GlobalResourceLoader.getService(new QName(CommonServiceConstants.REF_OBJECT_URI_GLOBAL_PREFIX + "acal", "AcademicCalendarService"));
        }
        return this.acalService;
    }
}
