package org.kuali.student.lum.ui.requirements.client.view;

import java.util.ArrayList;
import java.util.List;

import org.kuali.student.common.ui.client.mvc.Controller;
import org.kuali.student.common.ui.client.mvc.ViewComposite;
import org.kuali.student.common.ui.client.widgets.KSButton;
import org.kuali.student.common.ui.client.widgets.KSLabel;
import org.kuali.student.common.ui.client.widgets.KSListBox;
import org.kuali.student.common.ui.client.widgets.list.ListItems;
import org.kuali.student.core.search.dto.Result;
import org.kuali.student.lum.ui.requirements.client.controller.PrereqManager.PrereqViews;
import org.kuali.student.lum.ui.requirements.client.service.RequirementsService;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;

public class SearchView extends ViewComposite {
    
    private Panel mainPanel = new SimplePanel();
    private KSButton btnToComplexView = new KSButton("Complex View");
    KSListBox cluList = new KSListBox();    

    public SearchView(Controller controller) {
        super(controller, "Search View");
        super.initWidget(mainPanel);
        Panel testPanel = new VerticalPanel();
        testPanel.add(new KSLabel("Search View"));
        testPanel.add(cluList);        
        testPanel.add(btnToComplexView);
        mainPanel.add(testPanel);
        setupHandlers();
        layoutWidgets();        
    }
    
    public void layoutWidgets() {
        
        RequirementsService.Util.getInstance().getAllClus(new AsyncCallback<List<Result>>() {
            public void onFailure(Throwable caught) {
                // just re-throw it and let the uncaught exception handler deal with it
                Window.alert(caught.getMessage());
                // throw new RuntimeException("Unable to load BusinessRuleInfo objects", caught);
            }

            public void onSuccess(final List<Result> clus) {
                ListItems listItemClus = new ListItems() {
                    private List<Result> results = clus;
                    @Override
                    public List<String> getAttrKeys() {
                        List<String> attributes = new ArrayList<String>();
                        attributes.add("Key");
                        return attributes;
                    }

                    @Override
                    public String getItemAttribute(String id, String attrkey) {
                        String value = null;
                        Integer index;
                        try{
                            index = Integer.valueOf(id);
                            value = results.get(index).getResultCells().get(0).getValue();
                        } catch (Exception e) {
                        }

                        return value;
                    }

                    @Override
                    public int getItemCount() {    
                        return results.size();
                    }

                    @Override
                    public List<String> getItemIds() {
                        List<String> ids = new ArrayList<String>();
                        for(int i=0; i < results.size(); i++){
                            ids.add(String.valueOf(i));
                        }
                        return ids;
                    }

                    @Override
                    public String getItemText(String id) {
                        return getItemAttribute(id, "?");
                    }
                };                    
                cluList.setListItems(listItemClus);
            }
        });
    }

    private void setupHandlers() {
        btnToComplexView.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                getController().showView(PrereqViews.COMPLEX);
            }
        });
    }
    
}
