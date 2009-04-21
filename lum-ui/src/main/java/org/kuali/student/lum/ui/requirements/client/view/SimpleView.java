package org.kuali.student.lum.ui.requirements.client.view;

import java.util.ArrayList;
import java.util.Collection;

import org.kuali.student.common.ui.client.mvc.Controller;
import org.kuali.student.common.ui.client.mvc.Model;
import org.kuali.student.common.ui.client.mvc.ModelRequestCallback;
import org.kuali.student.common.ui.client.mvc.ViewComposite;
import org.kuali.student.common.ui.client.widgets.KSButton;
import org.kuali.student.common.ui.client.widgets.KSLabel;
import org.kuali.student.common.ui.client.widgets.KSRichEditor;
import org.kuali.student.lum.ui.requirements.client.controller.PrereqManager.PrereqViews;
import org.kuali.student.lum.ui.requirements.client.model.PrereqInfo;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;

public class SimpleView extends ViewComposite {
    
    private Panel mainPanel = new SimplePanel();
    private KSButton btnToComplexView = new KSButton("Complex View");
    private KSRichEditor rationalle = new KSRichEditor();
    private KSLabel linkToComplexView = new KSLabel("Show me more options...");
    
    private Model<PrereqInfo> model;

    public SimpleView(Controller controller) {
        super(controller, "Simple View");
        super.initWidget(mainPanel);
        setupHandlers();
    }    
    
    private void setupHandlers() {
        btnToComplexView.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                getController().showView(PrereqViews.COMPLEX);
            }
        });
        linkToComplexView.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                getController().showView(PrereqViews.COMPLEX);
            }
        });
    }
    
    public void beforeShow() {       
        if (model == null) {
            getController().requestModel(PrereqInfo.class, new ModelRequestCallback<PrereqInfo>() {
                public void onModelReady(Model<PrereqInfo> theModel) {
                    //printModel(theModel);
                    model = theModel;                    
                    setupHandlers();
                    redraw();
                }

                public void onRequestFail(Throwable cause) {
                    throw new RuntimeException("Unable to connect to model", cause);
                }
            });
        }
        else
        {
            redraw();
        }
    }    
    
    private void redraw() {
        Panel simpleView = new VerticalPanel();
        KSLabel Heading = new KSLabel("Rationale:");
        Heading.setStyleName("KS-ReqMgr-Heading");
        simpleView.add(Heading);
        Object[] temp = model.getValues().toArray();
        rationalle.setText(((PrereqInfo)temp[0]).getRationale());
        SimplePanel tempPanel = new SimplePanel();        
        tempPanel.add(rationalle);
        tempPanel.setStyleName("KS-Rules-Rationale");
        simpleView.add(tempPanel);
        //simpleView.add(btnToComplexView);
        linkToComplexView.addStyleName("KS-Rules-Link");
        simpleView.add(linkToComplexView);
        simpleView.setStyleName("Content-Margin");
        mainPanel.clear();
        mainPanel.add(simpleView);        
    }    
}
