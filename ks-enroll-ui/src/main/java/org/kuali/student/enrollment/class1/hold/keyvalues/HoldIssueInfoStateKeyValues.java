package org.kuali.student.enrollment.class1.hold.keyvalues;

import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.rice.core.api.util.ConcreteKeyValue;
import org.kuali.rice.core.api.util.KeyValue;
import org.kuali.rice.krad.uif.control.UifKeyValuesFinderBase;
import org.kuali.rice.krad.uif.view.ViewModel;
import org.kuali.student.mock.utilities.TestHelper;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.core.class1.state.dto.StateInfo;
import org.kuali.student.r2.core.class1.state.service.StateService;
import org.kuali.student.r2.core.constants.HoldServiceConstants;
import org.kuali.student.r2.core.constants.StateServiceConstants;

import javax.xml.namespace.QName;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * This class does the lookups for the states associated with HoldIssueInfo's lifecycle
 *
 * @author Kuali Student Team
 */
public class HoldIssueInfoStateKeyValues extends UifKeyValuesFinderBase implements Serializable {

    private static final long serialVersionUID = 1L;

    private transient StateService stateService;

    @Override
    public List<KeyValue> getKeyValues(ViewModel model) {
        List<KeyValue> keyValues = new ArrayList<KeyValue>();

        //TODO:Build real context.
        ContextInfo context = TestHelper.getContext1();

        try {
            List<StateInfo> states = getStateService().getStatesByLifecycle(HoldServiceConstants.ISSUE_PROCESS_KEY, context);
            for (StateInfo state : states) {
                if(state.getKey().startsWith("kuali.hold.issue.state")) { //TODO remove check after data is fixed
                    ConcreteKeyValue keyValue = new ConcreteKeyValue();
                    keyValue.setKey(state.getKey());
                    keyValue.setValue(state.getName());
                    keyValues.add(keyValue);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return keyValues;
    }

    public KeyValue getStateKeyValue(String stateKey) {
        ConcreteKeyValue keyValue = new ConcreteKeyValue();

        ContextInfo context = TestHelper.getContext1();

        try {
            List<StateInfo> states = getStateService().getStatesByLifecycle(HoldServiceConstants.ISSUE_PROCESS_KEY, context);
            for (StateInfo state : states) {
                if(state.getKey().equals(stateKey)) { //TODO remove check after data is fixed
                    keyValue.setKey(state.getKey());
                    keyValue.setValue(state.getName());
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return keyValue;
    }

    protected StateService getStateService() {
        if(stateService == null) {
            stateService = (StateService) GlobalResourceLoader.getService(new QName(StateServiceConstants.NAMESPACE, StateServiceConstants.SERVICE_NAME_LOCAL_PART));
        }
        return this.stateService;
    }
}
