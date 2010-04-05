package org.kuali.student.common.ui.client.event;

import org.kuali.student.common.ui.client.mvc.ApplicationEvent;

import com.google.gwt.event.shared.GwtEvent.Type;

public class SubmitProposalEvent extends ApplicationEvent<SubmitProposalHandler>{
    public static final Type<SubmitProposalHandler> TYPE = new Type<SubmitProposalHandler>();
    
    
    @Override
    protected void dispatch(SubmitProposalHandler handler) {
        handler.onSubmitProposal();
    }

    @Override
    public Type<SubmitProposalHandler> getAssociatedType() {
        return TYPE;
    }
}
