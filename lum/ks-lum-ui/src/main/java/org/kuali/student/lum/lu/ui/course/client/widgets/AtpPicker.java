package org.kuali.student.lum.lu.ui.course.client.widgets;

import org.kuali.student.common.ui.client.service.BaseRpcServiceAsync;
import org.kuali.student.common.ui.client.widgets.KSButton;
import org.kuali.student.common.ui.client.widgets.KSLabel;
import org.kuali.student.common.ui.client.widgets.focus.FocusGroup;
import org.kuali.student.common.ui.client.widgets.search.AdvancedSearchWindow;
import org.kuali.student.common.ui.client.widgets.search.SearchPanel;
import org.kuali.student.common.ui.client.widgets.suggestbox.KSSuggestBox;
import org.kuali.student.common.ui.client.widgets.suggestbox.SearchSuggestOracle;
import org.kuali.student.core.assembly.data.Metadata;
import org.kuali.student.core.organization.ui.client.view.OrgSearchTypeWidget;
import org.kuali.student.lum.lu.assembly.data.client.refactorme.orch.CreditCourseMetadata;
import org.kuali.student.lum.lu.ui.course.client.service.AtpRpcService;
import org.kuali.student.lum.lu.ui.course.client.service.AtpRpcServiceAsync;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.BlurHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.FocusHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class AtpPicker extends VerticalPanel implements HasText {
    private static AtpRpcServiceAsync atpRpcServiceAsync = GWT.create(AtpRpcService.class);
    private HasText textWidget;
    private SearchSuggestOracle oracle;
    private KSSuggestBox sb;
    private KSButton selectButton = new KSButton("Select");
    private final FocusGroup focus = new FocusGroup(this);
    
    public AtpPicker() {
        init();
    }
    
    private void init() {
        oracle = new SearchSuggestOracle("atp.search.atpByShortName", "atp.queryParam.atpShortName",
                "atp.queryParam.atpId", "atp.resultColumn.atpId", "atp.resultColumn.atpShortName");     //
        sb = new KSSuggestBox(oracle);
        oracle.setTextWidget(sb.getTextBox());
        HorizontalPanel advancedSearches = new HorizontalPanel();
        final KSLabel searchLink = new KSLabel("search");
        final KSLabel searchByDateLink = new KSLabel("choose by date");
        searchLink.addStyleName("action");
        searchByDateLink.addStyleName("action");
        advancedSearches.add(searchLink);
        advancedSearches.add(new KSLabel(" | "));
        advancedSearches.add(searchByDateLink);
//        final OrgSearchTypeWidget searchWindow = 
//            new OrgSearchTypeWidget(
//                    atpRpcServiceAsync, 
//                    "atp.search.basicAtpByShortName", 
//                    "atp.resultColumn.atpId",
//                    atpRpcServiceAsync,
//                    "atp.search.advancedAtpSearch", 
//                    "atp.resultColumn.atpId",
//            "Find Session");
        final AdvancedSearchWindow atpSearchWindow = createAtpSearchWindow();
        
        final AtpDateSearchLightBox dtpSearchByDate =
            new AtpDateSearchLightBox("Find Time Period",
                    atpRpcServiceAsync,
                    "atp.search.atpByDate",
                    "atp.resultColumn.atpId",
                    "atp.resultColumn.atpByDateDisplay");
        
        searchLink.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                atpSearchWindow.show();
            }
        });
        searchByDateLink.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                dtpSearchByDate.show();
            }
        });
        addTextWidget(sb);
        add(advancedSearches);
    }
    
    private static AdvancedSearchWindow createAtpSearchWindow(){

        Metadata searchMetadata = new CreditCourseMetadata().getMetadata("", "");  //no type or state at this point
        SearchPanel searchPicker = new SearchPanel(searchMetadata.getProperties().get("firstExpectedOffering").getInitialLookup());
        final AdvancedSearchWindow atpSearchWindow = new AdvancedSearchWindow("Find Session", searchPicker);

//        atpSearchWindow.addSelectionCompleteCallback(new Callback<List<String>>(){
//            public void exec(List<String> event) {
//                final String selected = event.get(0);
//                if (selected.length() > 0){
////                  List<String> selectedItems = event;
////                  ChangeViewStateEvent tempEvent = new ChangeViewStateEvent(LUMViews.VIEW_COURSE, selectedItems);
////                  FindPanel.this.getController().fireApplicationEvent(new ChangeViewStateEvent<LUMViews>(LUMViews.VIEW_COURSE, event));
//                    atpSearchWindow.hide();
//                }                
//            }            
//        });
        return atpSearchWindow;
    }
    
    public void addTextWidget(Widget textWidget) {
        this.textWidget = (HasText)textWidget;
        add(textWidget);
    }

    @Override
    public String getText() {
        return textWidget.getText();
    }

    @Override
    public void setText(String text) {
        textWidget.setText(text);
    }
    
    public HandlerRegistration addFocusHandler(FocusHandler handler) {
        return focus.addFocusHandler(handler);
    }

    public HandlerRegistration addBlurHandler(BlurHandler handler) {
        return focus.addBlurHandler(handler);
    }

}
