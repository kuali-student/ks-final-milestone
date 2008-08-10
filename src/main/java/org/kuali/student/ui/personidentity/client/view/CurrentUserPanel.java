/**
 * 
 */
package org.kuali.student.ui.personidentity.client.view;

import org.kuali.student.commons.ui.mvc.client.ApplicationContext;
import org.kuali.student.commons.ui.propertychangesupport.PropertyChangeEvent;
import org.kuali.student.commons.ui.propertychangesupport.PropertyChangeListener;
import org.kuali.student.commons.ui.propertychangesupport.PropertyChangeListenerProxy;
import org.kuali.student.ui.personidentity.client.ModelState;

import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.Label;

/**
 * @author Garey
 *
 */
public class CurrentUserPanel extends HorizontalPanel {

	Label	userName	= new Label();
	
	FlowPanel	fPanel	= new FlowPanel();
	//Hyperlink	login	= new Hyperlink();
	Hyperlink	logout	= new Hyperlink();
	
	private PropertyChangeListener currUserListener  
	= new PropertyChangeListenerProxy(
            "currUser",
            new PropertyChangeListener() {
                public void propertyChange(
                    PropertyChangeEvent propertyChangeEvent) {
                    //updateView( (List<GwtPersonInfo>)propertyChangeEvent.getNewValue());
                	updateDisplay();
                }
            });
	
	/**
	 * 
	 */
	public CurrentUserPanel() {
		setup();				
	}

	protected void setup(){
		//login.setText("Login");
		//login.setText(I18N.i18nConstant.login());
		//login.setText(ApplicationContext.getViews().get(AdminEditPanel.VIEW_NAME).getMessages().get("login"));
		
		//logout.setText("Logout");
		logout.setText(ApplicationContext.getViews().get(AdminEditPanel.VIEW_NAME).getMessages().get("logout"));
		
		//fPanel.add(login);
		fPanel.add(logout);
		
		//login.setVisible(true);
		logout.setVisible(false);	
		
		this.add(userName);
		this.add(fPanel);
		
		ModelState.getInstance().addPropertyChangeListener(currUserListener);
	}
	
	public void updateDisplay(){
		
		if(ModelState.getInstance().getCurrUser() != null){
			String name = "";
        	name += ModelState.getInstance().getCurrUser().getPreferredName().getGivenName();
        	name += " ";
        	name += ModelState.getInstance().getCurrUser().getPreferredName().getMiddleName();
        	name += " ";
        	name += ModelState.getInstance().getCurrUser().getPreferredName().getSurname();
        	
        	userName.setText(name);
        	//login.setVisible(false);
        	logout.setVisible(true);						
		}else{
			userName.setText("");
			//login.setVisible(true);
			logout.setVisible(false);	
		}
	}
}
