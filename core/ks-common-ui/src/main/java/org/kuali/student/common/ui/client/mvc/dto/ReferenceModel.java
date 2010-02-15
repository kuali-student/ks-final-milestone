/*
 * Copyright 2009 The Kuali Foundation Licensed under the
 * Educational Community License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may
 * obtain a copy of the License at
 * 
 * http://www.osedu.org/licenses/ECL-2.0
 * 
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */
package org.kuali.student.common.ui.client.mvc.dto;

import org.kuali.student.common.ui.client.configurable.mvc.HasReferenceId;
import org.kuali.student.common.ui.client.mvc.Model;
import org.kuali.student.common.ui.client.mvc.ModelChangeEvent;
import org.kuali.student.common.ui.client.mvc.ModelChangeHandler;
import org.kuali.student.common.ui.client.mvc.ModelChangeEvent.Action;

import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.event.shared.HandlerRegistration;

/**
 * This is a description of what this class does - Will Gomes don't forget to fill this in. 
 * 
 * @author Kuali Student Team
 *
 */
public class ReferenceModel implements HasReferenceId, Model {
    String referenceId;
    String referenceTypeKey;
    String referenceType;
    String referenceState;
	private HandlerManager handlers = new HandlerManager(this);

	@Override
	public HandlerRegistration addModelChangeHandler(
			ModelChangeHandler handler) {
		return handlers.addHandler(ModelChangeEvent.TYPE, handler);
	}

    /**
     * @see org.kuali.student.common.ui.client.configurable.mvc.HasReferenceId#setReferenceId(java.lang.String)
     */
    @Override
    public void setReferenceId(String id) {
        this.referenceId = id;
		handlers.fireEvent(new ModelChangeEvent(Action.RELOAD, this));
    }

    /**
     * @see org.kuali.student.common.ui.client.configurable.mvc.HasReferenceId#setReferenceKey(java.lang.String)
     */
    @Override
    public String getReferenceId() {
        return referenceId;
    }

    @Override
	public String getReferenceTypeKey() {
		return referenceTypeKey;
	}

    @Override
	public void setReferenceTypeKey(String referenceTypeKey) {
		this.referenceTypeKey = referenceTypeKey;
		handlers.fireEvent(new ModelChangeEvent(Action.RELOAD, this));
	}

    @Override
	public String getReferenceType() {
		return referenceType;
	}

    @Override
	public void setReferenceType(String referenceType) {
		this.referenceType = referenceType;
		handlers.fireEvent(new ModelChangeEvent(Action.RELOAD, this));
	}

    @Override
	public String getReferenceState() {
		return referenceState;
	}

    @Override
	public void setReferenceState(String referenceState) {
		this.referenceState = referenceState;
		handlers.fireEvent(new ModelChangeEvent(Action.RELOAD, this));
	}
    
}
