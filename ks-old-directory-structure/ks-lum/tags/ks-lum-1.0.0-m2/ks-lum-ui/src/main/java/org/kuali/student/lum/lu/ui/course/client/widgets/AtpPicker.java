package org.kuali.student.lum.lu.ui.course.client.widgets;

import org.kuali.student.common.ui.client.service.BaseRpcServiceAsync;
import org.kuali.student.common.ui.client.widgets.KSButton;
import org.kuali.student.common.ui.client.widgets.KSLabel;
import org.kuali.student.common.ui.client.widgets.suggestbox.KSSuggestBox;
import org.kuali.student.common.ui.client.widgets.suggestbox.SearchSuggestOracle;
import org.kuali.student.core.organization.ui.client.view.OrgSearchTypeWidget;
import org.kuali.student.lum.lu.ui.course.client.service.AtpRpcService;
import org.kuali.student.lum.lu.ui.course.client.service.AtpRpcServiceAsync;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
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
    
    public AtpPicker() {
        init();
    }
    
    private void init() {
        oracle = new SearchSuggestOracle((BaseRpcServiceAsync)atpRpcServiceAsync,  "atp.search.atpByShortName", "atp.queryParam.atpShortName",
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
        final OrgSearchTypeWidget searchWindow = 
            new OrgSearchTypeWidget(
                    atpRpcServiceAsync, 
                    "atp.search.basicAtpByShortName", 
                    "atp.resultColumn.atpId",
                    atpRpcServiceAsync,
                    "atp.search.advancedAtpSearch", 
                    "atp.resultColumn.atpId",
            "Find Session");
        final AtpDateSearchLightBox dtpSearchByDate =
            new AtpDateSearchLightBox("Find Time Period",
                    atpRpcServiceAsync,
                    "atp.search.atpByDate",
                    "atp.resultColumn.atpId",
                    "atp.resultColumn.atpByDateDisplay");
        
        searchLink.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                searchWindow.show();
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
    
}
