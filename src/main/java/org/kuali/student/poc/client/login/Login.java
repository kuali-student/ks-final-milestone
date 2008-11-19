package org.kuali.student.poc.client.login;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

public class Login implements EntryPoint {

	final VerticalPanel root = new VerticalPanel();
	final VerticalPanel main = new VerticalPanel();
	 
	final HorizontalPanel header = new HorizontalPanel();
	final HorizontalPanel headerLeft = new HorizontalPanel();
    final HorizontalPanel headerRight = new HorizontalPanel();
    final HorizontalPanel left = new HorizontalPanel();
    final VerticalPanel right = new VerticalPanel();
    final VerticalPanel center = new VerticalPanel();
    final HorizontalPanel footer = new HorizontalPanel();
    final HorizontalPanel footer2 = new HorizontalPanel();
       
    final Button btn = new Button();
  
    final Label lFooter = new Label();
    
    // header items
    final Image logo = new Image("images/logo.gif");
    final Image iFooter = new Image("images/border_bottom.gif");
    final Image iLoginBackground = new Image("images/background.gif");
  
	 public void onModuleLoad() {
	    	this.initStyles();
	    	this.doLayout();
	    	
	  }
	  
	  
	  public void doLayout() {
	   
		logo.setHeight("66");
		logo.setWidth("160");
		headerLeft.add(logo);
		headerRight.add(btn);
		header.add(headerLeft);
		header.add(headerRight);
		root.add(header);
	 
	    left.add(iLoginBackground);
	    
	    //footer
	    lFooter.setText("Kauli Student System");
	    footer.add(lFooter);
	    footer2.add(iFooter);

	    main.add(left);
	    
	    RootPanel.get().add(root);
	    RootPanel.get().add(main);
	    //RootPanel.get().add(footer);
	    //RootPanel.get().add(footer2);
	   
	  }
	  

	private void initStyles() {
		
		headerLeft.addStyleName("gwt-base-header-left");
		headerRight.addStyleName("gwt-base-header-right");
		root.addStyleName("gwt-base-header");
	    //main.addStyleName("gwt-main");
	    lFooter.setStyleName("gwt-Label");
	    footer.setStyleName("gwt-base-footer");
	    footer2.setStyleName("gwt-base-footer2");
	    left.addStyleName("gwt-left-login-side");
	    main.setStyleName("");
		
	}
		

}
