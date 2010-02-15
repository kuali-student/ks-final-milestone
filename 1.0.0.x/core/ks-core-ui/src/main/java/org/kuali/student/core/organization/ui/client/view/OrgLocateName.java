/*
 * Copyright 2009 The Kuali Foundation Licensed under the
 * Educational Community License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may
 * obtain a copy of the License at
 * 
 * http://www.osedu.org/licenses/ECL-2.0
 * 
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */
package org.kuali.student.core.organization.ui.client.view;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.kuali.student.common.ui.client.widgets.KSImage;
import org.kuali.student.common.ui.client.widgets.KSLabel;
import org.kuali.student.common.ui.client.widgets.list.KSCheckBoxList;
import org.kuali.student.common.ui.client.widgets.list.KSSelectItemWidgetAbstract;
import org.kuali.student.common.ui.client.widgets.list.KSSelectableTableList;
import org.kuali.student.common.ui.client.widgets.list.ListItems;
import org.kuali.student.common.ui.client.widgets.list.SelectionChangeHandler;
import org.kuali.student.core.organization.dto.OrgTypeInfo;
import org.kuali.student.core.organization.ui.client.service.OrgRpcService;
import org.kuali.student.core.organization.ui.client.service.OrgRpcServiceAsync;
import org.kuali.student.core.search.dto.QueryParamValue;
import org.kuali.student.core.search.dto.Result;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DeckPanel;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.ToggleButton;
import com.google.gwt.user.client.ui.VerticalPanel;

public class OrgLocateName extends Composite implements HasStateChanges {
    private OrgRpcServiceAsync orgRpcServiceAsync = GWT.create(OrgRpcService.class);
    
    DeckPanel w = new DeckPanel();
    Panel root = new HorizontalPanel();
    final Grid grid = new Grid(3, 9);
    private KSCheckBoxList orgTypes;
    SimplePanel resultPanel = new SimplePanel();
    KSSelectableTableList resultTable = null;
    KSLabel noResults = new KSLabel("No organizations found.");
    ToggleButton source = null;

    boolean loaded = false;
    private ClickHandler handler = new ClickHandler() {


        @Override
        public void onClick(ClickEvent event) {
            KSImage image = new KSImage("images/loading.gif");
            resultPanel.setWidget(image);
            
            if(source != null)
                source.setDown(false);
            if(event != null)
                source = (ToggleButton) event.getSource();
            source.setDown(true);

            List<QueryParamValue> queryParamValues = new ArrayList<QueryParamValue>();
            QueryParamValue qpv1 = new QueryParamValue();
            qpv1.setKey("org.queryParam.orgShortName");
            qpv1.setValue(source.getText() + '%');
            queryParamValues.add(qpv1);

            orgRpcServiceAsync.searchForResults("org.search.orgQuickLongViewByFirstLetter", queryParamValues, new AsyncCallback<List<Result>>() {

                public void onFailure(Throwable caught) {
                    Window.alert(caught.getMessage());
                }

                public void onSuccess(List<Result> result) {
                    if (result == null || result.size() <= 0) {
                        resultPanel.setWidget(noResults);
                    } else {
                        final Map<String, Result> orgInfoMap = new HashMap<String, Result>();
                        for (Result r : result) {
                            List<String> selectedItems = orgTypes.getSelectedItems();
                            if (selectedItems.isEmpty() || selectedItems.contains(r.getResultCells().get(2).getValue()))
                                orgInfoMap.put(r.getResultCells().get(0).getValue(), r);
                        }
                        ListItems items = new ListItems() {

                            public List<String> getAttrKeys() {
                                return Arrays.asList("Organization Name");
                            }

                            public String getItemAttribute(String id, String attrkey) {
                                Result r = orgInfoMap.get(id);

                                if (attrkey.equals("Organization Name")) {
                                    return r.getResultCells().get(1).getValue();
                                }

                                return null;
                            }

                            @Override
                            public int getItemCount() {
                                return orgInfoMap.size();
                            }

                            @Override
                            public List<String> getItemIds() {
                                List<String> keys = new ArrayList<String>();

                                for (String s : orgInfoMap.keySet()) {
                                    keys.add(s);
                                }

                                return keys;
                            }

                            @Override
                            public String getItemText(String id) {
                                return ((Result) orgInfoMap.get(id)).getResultCells().get(1).getValue();
                            }

                        };
                        // OrgInfoList orgInfoList = new OrgInfoList(result);
                        resultTable = new KSSelectableTableList();
                        resultTable.setMultipleSelect(false);
                        resultTable.setListItems(items);
                        resultPanel.setWidget(resultTable);
                        resultTable.addSelectionChangeHandler(new SelectionChangeHandler() {

                            @Override
                            public void onSelectionChange(final KSSelectItemWidgetAbstract selected) {
                                if(selected.getSelectedItem() != null) {
                                    final OrganizationWidget orgCreatePanel = new OrganizationWidget(selected.getSelectedItem(), OrganizationWidget.Scope.ORG_MODIFY_ALL);
                                    orgCreatePanel.addCloseButton("Back", new ClickHandler() {
                                        @Override
                                        public void onClick(ClickEvent event) {
                                            w.remove(w.getWidgetCount() - 1);
                                            w.showWidget(w.getWidgetCount() - 1);
                                        }
                                    });
                                    w.add(orgCreatePanel);
                                    w.showWidget(w.getWidgetCount() - 1);
                                }
                            }});
                        // selectButton.setVisible(true);
                    }
                }
            });
        }
    };

    public OrgLocateName() {
        super.initWidget(w);
        w.add(root);

        for (int i = 0; i < grid.getRowCount(); i++)
            for (int j = 0; j < grid.getColumnCount(); j++) {
                grid.setWidget(i, j, new ToggleButton(i * grid.getColumnCount() + j == 26 ? "#" : Character.toString((char) (65 + i * grid.getColumnCount() + j)), handler));
            }

        orgTypes = new KSCheckBoxList();
        orgTypes.addStyleName("KS-CheckBox-List");
        orgTypes.addSelectionChangeHandler(new SelectionChangeHandler() {
            @Override
            public void onSelectionChange(KSSelectItemWidgetAbstract w) {
                handler.onClick(null);
            }});

        Panel browsePanel = new VerticalPanel();
        browsePanel.add(grid);
        browsePanel.add(new HTML("<hr/>"));
        KSLabel subheader = new KSLabel("Limit Results");
        subheader.addStyleName("KS-Label-Subheader");
        browsePanel.add(subheader);
        KSLabel checkboxLbl = new KSLabel("Type of Organization");
        checkboxLbl.addStyleName("KS-Label-Checkbox");
        browsePanel.add(checkboxLbl);
        browsePanel.add(orgTypes);

        resultPanel.addStyleName("KS-Org-Search-Widget-Results");

        root.add(browsePanel);
        root.add(resultPanel);

    }

    protected void onLoad() {
        if (!loaded) {
            orgRpcServiceAsync.getOrgTypes(new AsyncCallback<List<OrgTypeInfo>>() {

                @Override
                public void onFailure(Throwable caught) {}

                @Override
                public void onSuccess(List<OrgTypeInfo> result) {
                    final Map<String, String> values = new LinkedHashMap<String, String>();
                    for (OrgTypeInfo orgTypeInfo : result) {
                        values.put(orgTypeInfo.getId(), orgTypeInfo.getName());
                    }
                    final ListItems items = new ListItems() {

                        @Override
                        public List<String> getAttrKeys() {
                            return null;
                        }

                        @Override
                        public String getItemAttribute(String id, String attrkey) {
                            return null;
                        }

                        @Override
                        public int getItemCount() {
                            return values.size();
                        }

                        @Override
                        public List<String> getItemIds() {
                            return new ArrayList<String>(values.keySet());
                        }

                        @Override
                        public String getItemText(String id) {
                            return values.get(id);
                        }

                    };
                    orgTypes.setListItems(items);
                }
            });
            loaded = true;
        }
        while(w.getWidgetCount() != 1)
            w.remove(w.getWidgetCount() - 1);
        w.showWidget(0);

    }

    @Override
    public void loadState(String state) {
        if(state == null || state.equals(""))
            return;
        String[] split = state.split("&");
        int x = split[0].toUpperCase().charAt(0)-'A';
        if(source != null)
            source.setDown(false);
        (source = (ToggleButton)grid.getWidget(x/3, x%3)).setDown(true);
        handler.onClick(null); //TODO hacky
        orgTypes.getSelectedItems().clear(); //TODO this is bad
        orgTypes.redraw(); //TODO this is very bad
        for(int i = 1; i < split.length; i++)
            orgTypes.selectItem(split[i]);
    }

    @Override
    public String saveState() {
        if(source == null)
            return null;
        String checks = "";
        for(String type : orgTypes.getSelectedItems()) {
            checks += "&" + type;
        }
        System.out.println(source.getText()+checks);
        return source.getText()+checks;
    }

}
