package org.kuali.student.r2.core.class1.state.service.impl.mocks;

import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.StatusInfo;
import org.kuali.student.r2.core.class1.state.service.StateTransitionsHelper;

import java.util.HashMap;
import java.util.Map;

/**
 *  A service to store a Map of states for testing.
 */
public class StatefulItemServiceImpl {
    private Map<String, String> itemStorage = new HashMap<String, String>();

    private StateTransitionsHelper transitionsHelper;

    public void setStateTransitionsHelper(StateTransitionsHelper transitionsHelper) {
        this.transitionsHelper = transitionsHelper;
    }

    public StatusInfo updateItemState(String id, String nextState, ContextInfo context)  {
        String currentState = itemStorage.get(id);
        StatusInfo si = null;
        try {
            si = transitionsHelper.processStateConstraints(id, nextState, context);
        } catch (Exception e) {
            throw new RuntimeException("Error processing constraints.", e);
        }
        if ( ! si.getIsSuccess()) {
            return si;
        }
        itemStorage.put(id, nextState);
        Map<String, StatusInfo> sis = null;
        try {
            sis = transitionsHelper.processStatePropagations(id, currentState + ":" + nextState, context);
        } catch (Exception e) {
            throw new RuntimeException("Error processing propagations.", e);
        }
        //  If any of the propagations fail then return a false StatusInfo but don't try to roll back.
        for (StatusInfo s : sis.values()) {
            if ( ! s.getIsSuccess()) {
                si.setSuccess(false);
            }
        }
        return si;
    }

    /**
     * Update the item state, bypassing all of the constraint checks and whatnot.
     */
    public void updateItem(String id, String state) {
        itemStorage.put(id, state);
    }

    public void createItem(String id, String stateKey) {
        itemStorage.put(id, stateKey);
    }

    public String getItemId(String id) {
        return itemStorage.get(id);
    }

    public Map<String,String> getItemStorage() {
        return itemStorage;
    }

}
