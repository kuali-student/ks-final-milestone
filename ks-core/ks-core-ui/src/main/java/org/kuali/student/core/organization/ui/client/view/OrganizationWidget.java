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
import java.util.HashMap;
import java.util.List;

import org.kuali.student.common.ui.client.event.SaveEvent;
import org.kuali.student.common.ui.client.event.SaveHandler;
import org.kuali.student.common.ui.client.mvc.CollectionModel;
import org.kuali.student.common.ui.client.widgets.KSButton;
import org.kuali.student.common.ui.client.widgets.KSDialogPanel;
import org.kuali.student.common.ui.client.widgets.KSImage;
import org.kuali.student.common.ui.client.widgets.KSLabel;
import org.kuali.student.common.ui.client.widgets.KSModalDialogPanel;
import org.kuali.student.common.ui.client.widgets.KSProgressIndicator;
import org.kuali.student.common.ui.client.widgets.KSTextArea;
import org.kuali.student.core.organization.dto.OrgInfo;
import org.kuali.student.core.organization.dto.OrgPositionRestrictionInfo;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.HasSelectionHandlers;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.VerticalPanel;

public class OrganizationWidget extends Composite implements HasSelectionHandlers<OrgInfo>, HasStateChanges {

    public static class Scope extends JavaScriptObject {
        public static final Scope ORG = make(1);
        public static final Scope ORG_POSITIONS = make(2);
        public static final Scope ORG_RELATIONS = make(4);
        public static final Scope ORG_PERSON_RELATIONS = make(8);
        public static final Scope ORG_CREATE_ALL = build(ORG, ORG_POSITIONS, ORG_RELATIONS);
        public static final Scope MODIFY = make(256);
        public static final Scope ORG_MODIFY_ALL = build(ORG, ORG_POSITIONS, ORG_RELATIONS, MODIFY);

        protected Scope() {}

        public native static Scope make(int x) /*-{
                   return [x];
               }-*/;

        final public native int value() /*-{
                   return this[0];
               }-*/;

        public static Scope build(Scope... scopes) {
            int composite = 0;
            for (Scope scope : scopes)
                composite |= scope.value();
            return make(composite);
        }

        final public boolean isModifyingExisting() {
            return (value() & MODIFY.value()) != 0;
        }
    }

    Scope scope;
    String orgId;

    private Panel w = new VerticalPanel();
    private List<OrgAbstractWidget> widgets;
    private HorizontalPanel buttonBar;
    private KSButton closeButton;
    ProgressMeter meter = new ProgressMeter();
    
    public OrganizationWidget(Scope... scopes) {
        this(null, scopes);
    }

    public OrganizationWidget(String orgId, Scope... scopes) {
        this.scope = Scope.build(scopes);
        this.orgId = orgId;
        if(orgId != null) {
            this.scope = Scope.build(scope, Scope.MODIFY);
        }
        if (scope.isModifyingExisting() && orgId == null)
            throw new IllegalStateException("Can't modify without an orgId");

        super.initWidget(w);
        
        init(orgId);
    }

    private void init(String orgId) {
        buttonBar = new HorizontalPanel();
        KSButton save = new KSButton("Save", new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                int numSteps = 0;
                for(OrgAbstractWidget w : widgets) {
                    if(w instanceof OrgWidget) {
                        numSteps++;
                    } else if(w instanceof OrgMultiWidget) {
                        OrgMultiWidget m = (OrgMultiWidget)w;
                        numSteps += m.calculateSaveableWidgetCount();
                    }
                }
                meter = new ProgressMeter(numSteps);
                meter.show();
            }
        });
        buttonBar.add(save);
        
        widgets = new ArrayList<OrgAbstractWidget>();
        
        boolean openFirst = true;
        CollectionModel<OrgPositionRestrictionInfo> model = null;

        if ((scope.value() & Scope.ORG.value()) != 0) {
            OrgWidget orgWidget = new OrgWidget(orgId,openFirst);
            orgWidget.addSelectionHandler(new SelectionHandler<OrgInfo>() {
                @Override
                public void onSelection(SelectionEvent<OrgInfo> event) {
                    String orgId = event.getSelectedItem().getId();
                    for(OrgAbstractWidget orgw : widgets) {
                        orgw.setOrgId(orgId);
                    }
                    SelectionEvent.fire(OrganizationWidget.this, event.getSelectedItem());
                }
            });
            addOrgWidget(orgWidget,save);
            openFirst = false;
        }
        if ((scope.value() & Scope.ORG_RELATIONS.value()) != 0) {
            addOrgWidget(new OrgRelationWidget(orgId,openFirst),save);
            openFirst = false;
        }
        if ((scope.value() & Scope.ORG_POSITIONS.value()) != 0) {
            OrgPositionWidget positionWidget = new OrgPositionWidget(orgId,openFirst);
            addOrgWidget(positionWidget,save);
            model = positionWidget.getModel();
            openFirst = false;
        }
        if ((scope.value() & Scope.ORG_PERSON_RELATIONS.value()) != 0 && scope.isModifyingExisting()) {
            addOrgWidget(new OrgPersonRelationWidget(orgId,model,openFirst),save);
        }
        w.add(buttonBar);
    }
    
    SaveHandler handler = new SaveHandler() {

        @Override
        public void onSave(SaveEvent event) {
            meter.addMessage(event.toString());
        }
    };

    class ProgressMeter {
        
        private KSTextArea console;
        private KSDialogPanel modal;
        private KSProgressIndicator progress;
        private Grid progressBar;
        int progressBarCompleted = 0;
        private KSLabel progressBarLbl;
        private KSImage image;
        private KSButton dismiss;

        public ProgressMeter() {
            this(-1);
        }
        
        public ProgressMeter(int numSteps) {
            modal = new KSModalDialogPanel();
            
            VerticalPanel pending = new VerticalPanel();
            console = new KSTextArea();
            console.setReadOnly(true);
            pending.add(console);
            
            HorizontalPanel bottom = new HorizontalPanel();
            if(numSteps < 1)
            {
                progress = new KSProgressIndicator();
                bottom.add(progress);
            } else {
                image = new KSImage("images/loading.gif");
                image.addStyleName("KS-Org-Progress-Bar-Image");
                bottom.add(image);
                progressBar = new Grid(1, numSteps);
                progressBar.addStyleName("KS-Org-Progress-Bar");
                bottom.add(progressBar);
                progressBarLbl = new KSLabel();
                progressBarLbl.addStyleName("KS-Org-Progress-Bar-Label");
                bottom.add(progressBarLbl);
            }
            bottom.add(dismiss = new KSButton("Dismiss", new ClickHandler() {
                @Override
                public void onClick(ClickEvent event) {
                    clearMessages();
                    modal.hide();
                    if(closeButton != null) {
                        closeButton.click();
                    }
                    if(w.isAttached()) {
                        if(!scope.isModifyingExisting()) {
                            w.clear();
                            init(null);
                        } else {
                            String orgId = null;
                            for(OrgAbstractWidget orgw : widgets) {
                                orgId = orgw.getOrgId();
                                if(orgId != null)
                                    break;
                            }
                            w.clear();
                            if(orgId != null) {
                                init(orgId);
                            } 
                        }
                    }
                }
            }));
            if(numSteps > 0) {
                dismiss.setText("Wait...");
                dismiss.setEnabled(false);
            }
            pending.add(bottom);

            modal.setHeader("Saving...");
            modal.setModal(true);
            modal.setResizable(false);
            modal.setWidget(pending);
            
            modal.addStyleName("KS-Org-Progress-Meter");
        }
        
        public void addMessage(String msg) {
            if(progress != null)
                progress.setText(msg);
            if(progressBar != null) {
                progressBar.getCellFormatter().addStyleName(0, progressBarCompleted++, "KS-Org-Progress-Bar-Completed");
                if(progressBarCompleted == progressBar.getColumnCount()) {
                    image.addStyleName("KS-Org-Progress-Bar-Image-Done");
                    dismiss.setText("Finished");
                    dismiss.setEnabled(true);
                }
            }
            if(progressBarLbl != null)
                progressBarLbl.setText(msg);
            console.setText(console.getText() + msg + "\n");
            if(!modal.isShowing())
                modal.show();
        }
        
        public void clearMessages() {
            console.setText("");
        }
        
        public void show() {
            modal.show();
        }
        
        public void hide() {
            modal.hide();
        }
        
        public boolean isShowing() {
            return modal.isShowing();
        }

    }
    
    protected void addOrgWidget(OrgAbstractWidget w, KSButton save) {
        this.w.add(w);
        widgets.add(w);
        save.addClickHandler(w);
        w.addSaveHandler(handler);
    }

    @Override
    public HandlerRegistration addSelectionHandler(SelectionHandler<OrgInfo> handler) {
        return addHandler(handler, SelectionEvent.getType());
    }

    public void addAdditionalButton(KSButton button) {
        buttonBar.insert(button, buttonBar.getWidgetCount() - 1);
    }
    
    public void addCloseButton(String html, ClickHandler handler) {
        if(closeButton == null) {
            closeButton = new KSButton(html, handler);
            buttonBar.add(closeButton);
        } else {
            buttonBar.remove(closeButton);
            closeButton = new KSButton(html, handler);
            buttonBar.add(closeButton);
        }
    }

    @Override
    public void loadState(String state) {
    }

    @Override
    public String saveState() {
        return null;
    }

}
