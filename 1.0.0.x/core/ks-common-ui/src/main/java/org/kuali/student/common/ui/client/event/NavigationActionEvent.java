package org.kuali.student.common.ui.client.event;

import org.kuali.student.common.ui.client.mvc.HasActionState;

public class NavigationActionEvent extends ActionEvent<NavigationActionHandler> implements HasActionState{
    public static final Type<NavigationActionHandler> TYPE = new Type<NavigationActionHandler>();
    
    private ActionState actionState;
    private String navKey;
    private Enum<?> navEnum;
    
    public NavigationActionEvent(String navKey){
    	this.navKey = navKey;
    }
    
    public NavigationActionEvent(Enum<?> navEnum){
    	this.navKey = navEnum.toString();
    	this.setNavEnum(navEnum);
    }
   
	@Override
	protected void dispatch(NavigationActionHandler handler) {
		handler.processNavigation(this);
	}

	@Override
	public Type<NavigationActionHandler> getAssociatedType() {
		return TYPE;
	}
        

    public void setActionState(ActionState state){
        this.actionState = state;
    }
    
    /**
     * @see org.kuali.student.common.ui.client.mvc.HasActionState#getActionState()
     */
    @Override
    public ActionState getActionState() {
        return this.actionState;
    }

	public String getNavKey() {
		return navKey;
	}

	public void setNavEnum(Enum<?> navEnum) {
		this.navEnum = navEnum;
	}

	public Enum<?> getNavEnum() {
		return navEnum;
	}
    
}
