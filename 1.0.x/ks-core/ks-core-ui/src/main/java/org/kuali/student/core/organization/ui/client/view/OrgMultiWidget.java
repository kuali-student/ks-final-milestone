/**
 * Copyright 2010 The Kuali Foundation Licensed under the
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
import java.util.Iterator;
import java.util.List;

import org.kuali.student.common.ui.client.widgets.KSButton;
import org.kuali.student.common.ui.client.widgets.KSDisclosureSection;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.CloseHandler;
import com.google.gwt.user.client.DeferredCommand;
import com.google.gwt.user.client.IncrementalCommand;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.IndexedPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

abstract class OrgMultiWidget extends OrgAbstractWidget implements HasWidgets, IndexedPanel {
        List<Widget> widgets = new ArrayList<Widget>();
        private VerticalPanel panel;

        public OrgMultiWidget() {
            this(null);
        }
        public OrgMultiWidget(KSDisclosureSection widget) {
            super(widget);
            panel = new VerticalPanel();

            panel.setHorizontalAlignment(VerticalPanel.ALIGN_CENTER);
            panel.setWidth("100%");
            w.setWidth("100%");

            w.add(panel);
            
            addStyleName("KS-Org-Multi");

            reinit();
        }

        public void reinit() {
            panel.clear();
            for (Widget widget : this) {
                panel.add(widget);
            }
            KSButton createNew = new KSButton("<sup>(+)</sup> Create New", new ClickHandler() {
                @Override
                public void onClick(ClickEvent event) {
                    create();
                }});
            panel.add(createNew);
        }
        
        protected abstract void create();

        @Override
        public void add(Widget w) {
            add(w,null);
        }
        
        public void add(Widget w, final CloseHandler<OrgMultiWidget> closeHandler) {
            final VerticalPanel p = new VerticalPanel();
            p.setHorizontalAlignment(VerticalPanel.ALIGN_RIGHT);
            p.addStyleName("KS-Org-Multi-Widget");
            
            KSButton closeButton = new KSButton("X", new ClickHandler() {
                @Override
                public void onClick(ClickEvent event) {
                    //TODO add back in once this doesn't throw NPEs
//                    KSConfirmationDialog confirm = new KSConfirmationDialog();
//                    confirm.setWidget(new KSLabel("Really delete this relation?"));
//                    confirm.setMessageTitle("Confirm delete");
//                    confirm.addConfirmHandler(new ClickHandler() {
//                        @Override
//                        public void onClick(ClickEvent event) {
//                            remove(p);
//                        }});
                    if(Window.confirm("Really delete this?")) {
                        remove(p);
                        if(closeHandler != null) {
                            closeHandler.onClose(null); //TODO hack hack hack
                        }
                    }
                }});
            closeButton.addStyleName("KS-Close-Button");
            SimplePanel closePanel = new SimplePanel();
            closePanel.addStyleName("KS-Close-Panel");
            closePanel.add(closeButton);
            p.add(closePanel);
            
            p.add(w);
            widgets.add(p);
//            reinit();
            panel.insert(p, panel.getWidgetCount() - 1);
        }

        @Override
        public void clear() {
            widgets.clear();
            reinit();
        }

        @Override
        public Iterator<Widget> iterator() {
            return widgets.iterator();
        }

        @Override
        public boolean remove(Widget w) {
            boolean remove = widgets.remove(w);
            reinit();
            return remove;
        }

        @Override
        public Widget getWidget(int index) {
            return widgets.get(index);
        }

        @Override
        public int getWidgetCount() {
            return widgets.size();
        }

        @Override
        public int getWidgetIndex(Widget child) {
            return widgets.indexOf(child);
        }

        @Override
        public boolean remove(int index) {
            Widget remove = widgets.remove(index);
            boolean b = remove != null;
            panel.remove(remove);            
            return b;
        }

        public List<Widget> getWidgets() {
            return widgets;
        }

        public void setWidgets(List<Widget> widgets) {
            this.widgets = widgets;
        }

        @Override
        public void onClick(ClickEvent event) {
            if(orgId != null)
                save();
            else {
                DeferredCommand.addCommand(new IncrementalCommand() {
                    @Override
                    public boolean execute() {
                        if(getOrgId() != null) {
                            save();
                            return false;
                        }
                        return true;
                    }});
            }
        }
        public abstract int calculateSaveableWidgetCount();
    }
