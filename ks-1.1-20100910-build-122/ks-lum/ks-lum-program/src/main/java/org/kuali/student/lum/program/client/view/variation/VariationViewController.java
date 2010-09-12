package org.kuali.student.lum.program.client.view.variation;

import org.kuali.student.common.ui.client.application.ViewContext;
import org.kuali.student.common.ui.client.mvc.DataModel;
import org.kuali.student.lum.program.client.ProgramConstants;
import org.kuali.student.lum.program.client.ProgramController;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.dom.client.Style;

public class VariationViewController extends ProgramController{

	public static final String parentLinkbaseURL = GWT.getModuleBaseURL() + "LUMMain.jsp#/HOME/CURRICULUM_HOME/PROGRAM_VIEW";
	
	public VariationViewController(DataModel programModel, ViewContext viewContext) {
		super(programModel, viewContext);
        configurer = GWT.create(VariationViewConfigurer.class);   
	}

    @Override
    protected void configureView() {
        super.configureView();
        
        String mdId = "";
        String mdTitle = "";

        //TODO: Hard-coded values of mdId & mdTitle for testing; will be removed
        mdId = "d4ea77dd-b492-4554-b104-863e42c5f8b7";
        mdTitle = "Undergrad Astronomy";
        if(this.programModel.getRoot()!= null){
        	mdId = this.programModel.get("/id");
        	mdTitle = this.programModel.get("/" + ProgramConstants.LONG_TITLE);
        }
        
        this.setContentTitle("Specialization of " + mdTitle);

        HTML parentLink = new HTML("Parent Program");
		parentLink.setHTML("<br><b>Parent Program: </b><a href=\"" + parentLinkbaseURL + "&docId=" + mdId + "\"><b>" + mdTitle + "</b></a>");
        parentLink.getElement().getStyle().setPaddingRight(20d, Style.Unit.PX);
        parentLink.getElement().getStyle().setFontSize(3, Style.Unit.PX);
        this.addContentWidget(parentLink);
        
        initialized = true;
    }
}
