package org.kuali.student.common.ui.client.widgets.rules;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.kuali.student.common.ui.client.mvc.Callback;
import org.kuali.student.common.ui.client.widgets.KSLabel;
import org.kuali.student.common.ui.client.widgets.list.ListItems;
import org.kuali.student.common.ui.client.widgets.tabs.KSTabPanel;
import org.kuali.student.core.statement.dto.ReqComponentInfo;
import org.kuali.student.core.statement.dto.ReqComponentTypeInfo;

import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Widget;

public class RuleManagementWidget extends FlowPanel {

    private KSTabPanel panel = new KSTabPanel();
	private Map<String, Widget> tabLayoutMap = new HashMap<String, Widget>();

    //widget's data
    private List<ReqComponentTypeInfo> reqCompTypeInfoList;     	//list of all Requirement Component Types based on selected Statement Type
    private ListItems listItemReqCompTypes;                 	//list of all Requirement Component Types
    private ReqComponentInfo editedReqComp;						//Req. Component that user is editing or adding
    private ReqComponentTypeInfo selectedReqType;
    private ReqComponentTypeInfo originalReqType;
    private boolean addingNewReqComp;                           //adding (true) or editing (false) req. component

    public RuleManagementWidget() {
        super();
        setStyleName("KS-Program-Rule-ReqComp-box");

        setupHandlers();
        //panel.selectTab(tabName);

        final KSLabel label = new KSLabel("Test");
        tabLayoutMap.put("My Test", label);
        panel.addTab("My Test", "My Test", label);

        //Handler for when tab is clicked
        panel.addTabCustomCallback("My Test", new Callback<String>(){

            @Override
            public void exec(String result) {
                label.setText("xxx");
            }

        });

        this.add(panel);
    }

    private void setupHandlers() {

    }
}
