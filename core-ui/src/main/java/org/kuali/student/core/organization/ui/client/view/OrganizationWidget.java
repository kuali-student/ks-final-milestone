package org.kuali.student.core.organization.ui.client.view;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.kuali.student.common.ui.client.dto.HelpInfo;
import org.kuali.student.common.ui.client.event.SaveEvent;
import org.kuali.student.common.ui.client.event.SaveHandler;
import org.kuali.student.common.ui.client.widgets.KSButton;
import org.kuali.student.common.ui.client.widgets.KSDatePicker;
import org.kuali.student.common.ui.client.widgets.KSDialogPanel;
import org.kuali.student.common.ui.client.widgets.KSDisclosureSection;
import org.kuali.student.common.ui.client.widgets.KSDropDown;
import org.kuali.student.common.ui.client.widgets.KSModalDialogPanel;
import org.kuali.student.common.ui.client.widgets.KSProgressIndicator;
import org.kuali.student.common.ui.client.widgets.KSTextArea;
import org.kuali.student.common.ui.client.widgets.KSTextBox;
import org.kuali.student.common.ui.client.widgets.forms.KSFormField;
import org.kuali.student.common.ui.client.widgets.forms.KSFormLayoutPanel;
import org.kuali.student.common.ui.client.widgets.list.ListItems;
import org.kuali.student.core.dto.MetaInfo;
import org.kuali.student.core.dto.StatusInfo;
import org.kuali.student.core.organization.dto.OrgInfo;
import org.kuali.student.core.organization.dto.OrgOrgRelationInfo;
import org.kuali.student.core.organization.dto.OrgOrgRelationTypeInfo;
import org.kuali.student.core.organization.dto.OrgPersonRelationInfo;
import org.kuali.student.core.organization.dto.OrgPersonRelationTypeInfo;
import org.kuali.student.core.organization.dto.OrgPositionRestrictionInfo;
import org.kuali.student.core.organization.dto.OrgTypeInfo;
import org.kuali.student.core.organization.ui.client.service.OrgRpcService;
import org.kuali.student.core.person.dto.PersonInfo;
import org.kuali.student.core.person.ui.client.service.PersonRpc;
import org.kuali.student.core.person.ui.client.view.PersonSearchWidget;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.CloseEvent;
import com.google.gwt.event.logical.shared.CloseHandler;
import com.google.gwt.event.logical.shared.HasSelectionHandlers;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.DeferredCommand;
import com.google.gwt.user.client.IncrementalCommand;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FocusWidget;
import com.google.gwt.user.client.ui.HasName;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.IndexedPanel;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class OrganizationWidget extends Composite implements HasSelectionHandlers<OrgInfo> {

    public static class Scope extends JavaScriptObject {
        public static final Scope ORG = make(1);
        public static final Scope ORG_POSITIONS = make(2);
        public static final Scope ORG_RELATIONS = make(4);
        public static final Scope ORG_PERSON_RELATIONS = make(8);
        public static final Scope ORG_CREATE_ALL = build(ORG, ORG_POSITIONS, ORG_RELATIONS, ORG_PERSON_RELATIONS);
        public static final Scope MODIFY = make(256);
        public static final Scope ORG_MODIFY_ALL = build(ORG, ORG_POSITIONS, ORG_RELATIONS, ORG_PERSON_RELATIONS, MODIFY);

        protected Scope() {}

        private native static Scope make(int x) /*-{
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

    private Scope scope;

    private Panel w = new VerticalPanel();
    private List<OrgAbstractWidget> widgets;
    private HorizontalPanel buttonBar;

    public OrganizationWidget(Scope... scopes) {
        this(null, scopes);
    }

    public OrganizationWidget(String orgId, Scope... scopes) {
        this.scope = Scope.build(scopes);
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
        KSButton save = new KSButton("Save");
        buttonBar.add(save);
        
        widgets = new ArrayList<OrgAbstractWidget>();
        
        boolean openFirst = true;

        if ((scope.value() & Scope.ORG.value()) != 0) {
            addOrgWidget(new OrgWidget(orgId,openFirst),save);
            openFirst = false;
        }
        if ((scope.value() & Scope.ORG_RELATIONS.value()) != 0) {
            addOrgWidget(new OrgRelationWidget(orgId,openFirst),save);
            openFirst = false;
        }
        if ((scope.value() & Scope.ORG_POSITIONS.value()) != 0) {
            addOrgWidget(new OrgPositionWidget(orgId,openFirst),save);
            openFirst = false;
        }
        if ((scope.value() & Scope.ORG_PERSON_RELATIONS.value()) != 0 && scope.isModifyingExisting()) {
            addOrgWidget(new OrgPersonRelationWidget(orgId,openFirst),save);
        }
        w.add(buttonBar);
    }
    
    SaveHandler handler = new SaveHandler() {
        ProgressMeter meter = new ProgressMeter();

        @Override
        public void onSave(SaveEvent event) {
            meter.addMessage(event.toString());
            if(event.toString().startsWith("Organization created with id: ")) {
                String orgId = event.toString().substring("Organization created with id: ".length());
                for(OrgAbstractWidget orgw : widgets) {
                    orgw.setOrgId(orgId);
                }
            }
        }
        
        class ProgressMeter {
            
            private KSTextArea console;
            private KSDialogPanel modal;
            private KSProgressIndicator progress;

            public ProgressMeter() {
                modal = new KSModalDialogPanel();
                
                VerticalPanel pending = new VerticalPanel();
                console = new KSTextArea();
                console.setReadOnly(true);
                pending.add(console);
                
                HorizontalPanel bottom = new HorizontalPanel();
                progress = new KSProgressIndicator();
                bottom.add(progress);
                bottom.add(new KSButton("Dismiss", new ClickHandler() {
                    @Override
                    public void onClick(ClickEvent event) {
                        clearMessages();
                        modal.hide();
                        if(buttonBar.getWidgetCount() > 1) {
                            ((KSButton)buttonBar.getWidget(1)).click(); //TODO bad assumption
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
                pending.add(bottom);

                modal.setHeader("Saving...");
                modal.setModal(true);
                modal.setResizable(false);
                modal.setWidget(pending);
                
                modal.addStyleName("KS-Org-Progress-Meter");
            }
            
            public void addMessage(String msg) {
                progress.setText(msg);
                console.setText(console.getText() + msg + "\n");
                if(!modal.isShowing())
                    modal.show();
            }
            
            public void clearMessages() {
                console.setText("");
            }
        }
    };
    
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

    public void addButton(KSButton button) {
        buttonBar.add(button);
    }
    
    static abstract class OrgAbstractWidget extends Composite implements ClickHandler {
        protected KSDisclosureSection w;
        protected String orgId;

        public OrgAbstractWidget() {
            this(null);
        }

        public OrgAbstractWidget(KSDisclosureSection widget) {
            if(widget == null)
                widget = new KSDisclosureSection("Organization Information", null, false);
            super.initWidget(w = widget);
        }

        protected abstract void save();

        protected static void addFormField(Widget w, String label, String name, KSFormLayoutPanel formPanel) {
            KSFormField ff = new KSFormField();
            ff.setLabelText(label);
            ff.setWidget(w);
            if (w instanceof HasName)
                ((HasName) w).setName(name);
            ff.setHelpInfo(new HelpInfo());
            ff.setName(name);

            formPanel.addFormField(ff);
        }
        
        @Override
        public void onClick(ClickEvent event) {
            save();
        }

        public HandlerRegistration addSaveHandler(SaveHandler handler) {
            return addHandler(handler, SaveEvent.TYPE);
        }

        public String getOrgId() {
            return orgId;
        }

        public void setOrgId(String orgId) {
            this.orgId = orgId;
        }
    }

    static class OrgWidget extends OrgAbstractWidget {
        private String orgVersion;
        private KSFormLayoutPanel orgForm;
        
        public OrgWidget() {
            this(null);
        }
        public OrgWidget(String orgId) {
            this(null,false);
        }
        public OrgWidget(String orgId, boolean open) {
            super(new KSDisclosureSection("Organization", null, open));
            this.orgId = orgId;
            
            VerticalPanel panel = new VerticalPanel();

            orgForm = new KSFormLayoutPanel();

            addFormField(new KSDropDown(), "Type", "orgType", orgForm);
            addFormField(new KSTextBox(), "Name", "orgName", orgForm);
            addFormField(new KSTextBox(), "Abbreviation", "orgAbbrev", orgForm);
            addFormField(new KSTextArea(), "Description", "orgDesc", orgForm);
            addFormField(new KSDatePicker(), "Effective Date", "orgEffDate", orgForm);
            addFormField(new KSDatePicker(), "Expiration Date", "orgExpDate", orgForm);

            panel.setHorizontalAlignment(VerticalPanel.ALIGN_CENTER);
            panel.setWidth("100%");
            w.setWidth("100%");
            panel.add(orgForm);

            w.add(panel);
            
            if(orgId != null)
                populateOrgInfo();
            
            populateOrgTypes();
        }
        
        private void populateOrgTypes() {
            OrgRpcService.Util.getInstance().getOrgTypes(new AsyncCallback<List<OrgTypeInfo>>(){
                public void onFailure(Throwable caught) {
                    Window.alert(caught.getMessage());
                }

                public void onSuccess(final List<OrgTypeInfo> orgTypes) {
                    final Map<String,String> ids = new LinkedHashMap<String,String>();
                    for(OrgTypeInfo orgTypeInfo:orgTypes){
                        ids.put(orgTypeInfo.getId(),orgTypeInfo.getName());
                    }
                    ListItems list = new ListItems() {

                        @Override
                        public List<String> getAttrKeys() {
                            return null; //apparently unused
                        }

                        @Override
                        public String getItemAttribute(String id, String attrkey) {
                            return null; //apparently unused
                        }

                        @Override
                        public int getItemCount() {
                            return orgTypes.size();
                        }

                        @Override
                        public List<String> getItemIds() {
                            return new ArrayList<String>(ids.keySet());
                        }

                        @Override
                        public String getItemText(String id) {
                            return ids.get(id);
                        }};
                    KSDropDown orgTypeDropDown = (KSDropDown) orgForm.getFieldWidget("orgType");
                    orgTypeDropDown.setEnabled(true);
                    ListItems items = orgTypeDropDown.getListItems();
                    orgTypeDropDown.setListItems(list);
                    if (items != null) {
                        orgTypeDropDown.selectItem(((SingleListItem)items).getItemIds().get(0));
                    }
                }
          });
        }
        
        protected void save(){       
            OrgInfo orgInfo = new OrgInfo();
            
            MetaInfo orgMetaInfo = new MetaInfo();
            orgMetaInfo.setVersionInd(orgVersion);
            orgInfo.setMetaInfo(orgMetaInfo);
            
            KSDropDown orgTypeDropDown = (KSDropDown) orgForm.getFieldWidget("orgType");
            
            orgInfo.setType(orgTypeDropDown.getSelectedItems().get(0));        
            orgInfo.setShortDesc(orgForm.getFieldValue("orgDesc"));
            orgInfo.setLongName(orgForm.getFieldValue("orgName"));
            orgInfo.setShortName(orgForm.getFieldValue("orgAbbrev"));

            orgInfo.setEffectiveDate(((KSDatePicker)orgForm.getFieldWidget("orgEffDate")).getValue());
            orgInfo.setExpirationDate(((KSDatePicker)orgForm.getFieldWidget("orgExpDate")).getValue());
            
            if (orgId == null){
                OrgRpcService.Util.getInstance().createOrganization(orgInfo,new AsyncCallback<OrgInfo>(){
                    public void onFailure(Throwable caught) {
                        Window.alert(caught.getMessage());
                    }
        
                    public void onSuccess(OrgInfo result) {
                        orgId = result.getId();
//                        fireSelectEvent(result);
                        fireEvent(new SaveEvent() { //TODO fix this terrible terrible hack
                            public String toString() {
                                return "Organization created with id: " + orgId;
                            }
                        });
                    }
                });
            } else {
                orgInfo.setId(orgId);
                OrgRpcService.Util.getInstance().updateOrganization(orgInfo,new AsyncCallback<OrgInfo>(){
                    public void onFailure(Throwable caught) {
                        Window.alert(caught.getMessage());
                    }
        
                    public void onSuccess(OrgInfo result) {
                        fireEvent(new SaveEvent() { //TODO fix this terrible terrible hack
                            public String toString() {
                                return "Organization saved";
                            }
                        });
                    }
                });            
            }

        }
        
        protected void populateOrgInfo() {
            OrgRpcService.Util.getInstance().getOrganization(orgId, new AsyncCallback<OrgInfo>() {

                public void onFailure(Throwable caught) {
                    Window.alert(caught.getMessage());
                }

                public void onSuccess(OrgInfo orgInfo) {
                    KSDropDown orgTypeDropDown = (KSDropDown) orgForm.getFieldWidget("orgType");
                    
                    orgVersion = orgInfo.getMetaInfo().getVersionInd();
                    String orgType = orgInfo.getType();
                    if (orgTypeDropDown.getListItems() == null) {
                        orgTypeDropDown.setListItems(new SingleListItem(orgType));
                        orgTypeDropDown.setEnabled(false);
                    }
                    orgTypeDropDown.selectItem(orgType);
                    orgForm.setFieldValue("orgName", orgInfo.getLongName());
                    orgForm.setFieldValue("orgAbbrev", orgInfo.getShortName());
                    orgForm.setFieldValue("orgDesc", orgInfo.getShortDesc());

                    ((KSDatePicker) orgForm.getFieldWidget("orgEffDate")).setValue(orgInfo.getEffectiveDate());
                    ((KSDatePicker) orgForm.getFieldWidget("orgExpDate")).setValue(orgInfo.getExpirationDate());
//                    System.out.println(orgForm.getFieldValue("orgType"));
                }
            });
        }
    }

    static abstract class OrgMultiWidget extends OrgAbstractWidget implements HasWidgets, IndexedPanel {
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
    }

    static class OrgPositionWidget extends OrgMultiWidget {
        private ListItems orgPosTypeList;
        List<Map<String,Object>> forms = new ArrayList<Map<String,Object>>();
        
        public OrgPositionWidget() {
            this(null);
        }
        public OrgPositionWidget(String orgId) {
            this(orgId,false);
        }
        public OrgPositionWidget(String orgId, boolean open) {
            super(new KSDisclosureSection("Positions", null, open));
            if(orgPosTypeList == null)
                populateOrgPositionTypes();
            this.orgId = orgId;
            if(orgId == null)
                createBlankPosition();
            else {
                populatePositionInfos();
            }
        }
        
        public KSFormLayoutPanel createBlankPosition() {
            KSFormLayoutPanel orgForm = new KSFormLayoutPanel();

            final KSDropDown posTypeDropDown = new KSDropDown();

            addFormField(posTypeDropDown, "Position", "position", orgForm);
            addFormField(new KSTextBox(), "Title", "title", orgForm);
            addFormField(new KSTextArea(), "Description", "desc", orgForm);
            addFormField(new KSTextBox(), "Min people", "min", orgForm);
            addFormField(new KSTextBox(), "Max people", "max", orgForm);

            final HashMap<String, Object> map = new HashMap<String, Object>();
            
            add(orgForm, new CloseHandler<OrgMultiWidget>() {
                @Override
                public void onClose(CloseEvent<OrgMultiWidget> event) {
                    map.put("deleted", Boolean.TRUE);
                }});
            
            if(orgPosTypeList == null)
                DeferredCommand.addCommand(new IncrementalCommand() {
                    @Override
                    public boolean execute() {
                        if(orgPosTypeList != null) {
                            posTypeDropDown.setListItems(orgPosTypeList);
                            return false;
                        }
                        return true;
                    }});
            else
                posTypeDropDown.setListItems(orgPosTypeList);

            map.put("form",orgForm);
            forms.add(map);

            return orgForm;
        }

        private void populateOrgPositionTypes() {
            OrgRpcService.Util.getInstance().getOrgPersonRelationTypes(new AsyncCallback<List<OrgPersonRelationTypeInfo>>(){
                public void onFailure(Throwable caught) {
                    Window.alert(caught.getMessage());
                }

                public void onSuccess(List<OrgPersonRelationTypeInfo> posTypes) {
                    final Map<String,String> map = new LinkedHashMap<String, String>();
                    for(OrgPersonRelationTypeInfo info : posTypes) {
                        map.put(info.getId(), info.getName());
                    }
                    orgPosTypeList = new ListItems() {

                        @Override
                        public List<String> getAttrKeys() {
                            return null; //apparently unused
                        }

                        @Override
                        public String getItemAttribute(String id, String attrkey) {
                            return null; //apparently unused
                        }

                        @Override
                        public int getItemCount() {
                            return map.size();
                        }

                        @Override
                        public List<String> getItemIds() {
                            return new ArrayList<String>(map.keySet());
                        }

                        @Override
                        public String getItemText(String id) {
                            return map.get(id);
                        }
                    };
                }
            });
        }
        
        private void populatePositionInfos() {
            OrgRpcService.Util.getInstance().getPositionRestrictionsByOrg(orgId,
                    new AsyncCallback<List<OrgPositionRestrictionInfo>>(){
            
                        public void onFailure(Throwable caught) {
                            Window.alert(caught.getMessage());
                        }
            
                        public void onSuccess(List<OrgPositionRestrictionInfo> orgPositions) {
                            for(OrgPositionRestrictionInfo info : orgPositions) {
                                final KSFormLayoutPanel orgPosForm = createBlankPosition();
                                Map<String,Object> map = forms.get(forms.size() - 1); //should always be the last one
                                map.put("posId",info.getId());
                                map.put("posVersion",info.getMetaInfo().getVersionInd());
                                
                                final String posType = info.getOrgPersonRelationTypeKey();
                                
                                orgPosForm.setFieldValue("title", info.getTitle());        
                                orgPosForm.setFieldValue("desc", info.getDesc());
                                
                                //TODO: Need to fix this
                                try{
                                    //setFormValue("duration", orgPosRestriction.getStdDuration().getTimeQuantity());
                                } catch (Exception e){
                                }
                                try {
                                    orgPosForm.setFieldValue("min", info.getMinNumRelations().toString());
                                } catch (Exception e) {
                                    orgPosForm.setFieldValue("min", "");
                                    // TODO: handle exception
                                }
                                
                                orgPosForm.setFieldValue("max",info.getMaxNumRelations()); 
                                
                                final KSDropDown posTypeDropDown = (KSDropDown) orgPosForm.getFieldWidget("position");
                                
                                if(posTypeDropDown.getListItems() != null)
                                    posTypeDropDown.selectItem(posType);
                                else
                                    DeferredCommand.addCommand(new IncrementalCommand() {
                                        @Override
                                        public boolean execute() {
                                            if(posTypeDropDown.getListItems() != null) {
                                                posTypeDropDown.selectItem(posType);
                                                return false;
                                            }
                                            return true;
                                        }});
                                posTypeDropDown.setEnabled(false);
                            }
                        }            
            });
        }
        
        @Override
        protected void save() {
            for (Map<String,Object> formMap : forms) {
                KSFormLayoutPanel orgPosForm = (KSFormLayoutPanel) formMap.get("form");
                if (orgPosForm.getFieldValue("title").trim().length() == 0)
                    continue; //skipping this one
                OrgPositionRestrictionInfo orgPosRestriction = new OrgPositionRestrictionInfo();
                
                orgPosRestriction.setId((String) formMap.get("posId"));
                orgPosRestriction.setOrgId(orgId);
                
                MetaInfo posMetaInfo = new MetaInfo();
                posMetaInfo.setVersionInd((String) formMap.get("posVersion"));        
                orgPosRestriction.setMetaInfo(posMetaInfo);
                
                orgPosRestriction.setOrgPersonRelationTypeKey(orgPosForm.getFieldValue("position"));
                orgPosRestriction.setTitle(orgPosForm.getFieldValue("title"));
                orgPosRestriction.setDesc(orgPosForm.getFieldValue("desc"));
                
                /*
                TimeAmountInfo durationTimeAmtInfo = new TimeAmountInfo();
                durationTimeAmtInfo.setAtpDurationTypeKey(atpDurationType.getText());
                try {
                    durationTimeAmtInfo.setTimeQuantity(Integer.valueOf(posDuration.getText()));            
                } catch (NumberFormatException e) {
                }      
                orgPosRestriction.setStdDuration(durationTimeAmtInfo);
                */
                
                try {
                    orgPosRestriction.setMinNumRelations(Integer.valueOf(orgPosForm.getFieldValue("min")));
                } catch (NumberFormatException e) {

                }
                orgPosRestriction.setMaxNumRelations(orgPosForm.getFieldValue("max"));
                
                if (orgPosRestriction.getId() == null){
                    OrgRpcService.Util.getInstance().addPositionRestrictionToOrg(orgPosRestriction,
                            new AsyncCallback<OrgPositionRestrictionInfo>(){
                        public void onFailure(Throwable caught) {
                            Window.alert(caught.getMessage());
                        }
            
                        public void onSuccess(final OrgPositionRestrictionInfo result) {
                            fireEvent(new SaveEvent() { //TODO fix this terrible terrible hack
                                public String toString() {
                                    return "Org position created with id: " + result.getId();
                                }
                            });
                        }
                    });
                }else if(formMap.get("deleted") != null){
                    OrgRpcService.Util.getInstance().removePositionRestrictionFromOrg(orgPosRestriction.getOrgId(), orgPosRestriction.getOrgPersonRelationTypeKey(), 
                            new AsyncCallback<StatusInfo>(){
                        public void onFailure(Throwable caught) {
                            Window.alert(caught.getMessage());
                        }
            
                        public void onSuccess(final StatusInfo result) {
                            fireEvent(new SaveEvent() { //TODO fix this terrible terrible hack
                                public String toString() {
                                    return "Org position deleted: " + result.getMessage();
                                }
                            });
                        }
                    }); 
                }else{
                    OrgRpcService.Util.getInstance().updatePositionRestrictionForOrg(orgPosRestriction,
                            new AsyncCallback<OrgPositionRestrictionInfo>(){
                        public void onFailure(Throwable caught) {
                            Window.alert(caught.getMessage());
                        }
            
                        public void onSuccess(final OrgPositionRestrictionInfo result) {
                            fireEvent(new SaveEvent() { //TODO fix this terrible terrible hack
                                public String toString() {
                                    return "Org position updated for: " + result.getId();
                                }
                            });
                        }
                    });                    
                }
            }
        }

        @Override
        protected void create() {
            createBlankPosition();
        }

    }

    static class OrgRelationWidget extends OrgMultiWidget {
        private ListItems orgRelTypeList;
        
        List<Map<String,Object>> forms = new ArrayList<Map<String,Object>>();
        
        public OrgRelationWidget() {
            this(null);
        }
        public OrgRelationWidget(String orgId) {
            this(orgId,false);
        }
        public OrgRelationWidget(String orgId, boolean open) {
            super(new KSDisclosureSection("Relationships / Links", null, open));
            if(orgRelTypeList == null)
                populateOrgRelationTypes();
            this.orgId = orgId;
            if(orgId == null)
                createBlankRelation();
            else {
                populateRelationInfos();
            }
        }

        private void populateRelationInfos() {
            OrgRpcService.Util.getInstance().getOrgOrgRelationsByOrg(orgId, 
                    new AsyncCallback<List<OrgOrgRelationInfo>>(){

                        public void onFailure(Throwable caught) {
                            Window.alert(caught.getMessage());
                        }

                        public void onSuccess(List<OrgOrgRelationInfo> orgRelations) {                   
                            for (OrgOrgRelationInfo orgRelationInfo:orgRelations) {
                                final KSFormLayoutPanel orgRelForm = createBlankRelation();
                                Map<String,Object> map = forms.get(forms.size() - 1); //should always be the last one
                                orgRelForm.setFieldValue("relOrgId",orgRelationInfo.getRelatedOrgId());
                                map.put("orgRelId",orgRelationInfo.getId());
                                map.put("orgRelVersion",orgRelationInfo.getMetaInfo().getVersionInd());
                                final String orgRelType = orgRelationInfo.getType();
                                
                                //TODO would be nicer to just have this info fetched with initial request
                                OrgRpcService.Util.getInstance().getOrganization(orgRelationInfo.getRelatedOrgId(), 
                                        new AsyncCallback<OrgInfo>(){

                                            public void onFailure(Throwable caught) {
                                            }

                                            public void onSuccess(OrgInfo orgInfo) {
                                                orgRelForm.setFieldValue("relOrgName", orgInfo.getLongName());                       
                                            }            
                                });
                                
                                final KSDropDown orgRelTypeDropDown = (KSDropDown) orgRelForm.getFieldWidget("relType");
                                
                                if(orgRelTypeDropDown.getListItems() != null)
                                    orgRelTypeDropDown.selectItem(orgRelType);
                                else
                                    DeferredCommand.addCommand(new IncrementalCommand() {
                                        @Override
                                        public boolean execute() {
                                            if(orgRelTypeDropDown.getListItems() != null) {
                                                orgRelTypeDropDown.selectItem(orgRelType);
                                                return false;
                                            }
                                            return true;
                                        }});

                                //Disable editing of rel org id and name
                                ((FocusWidget)orgRelForm.getFieldWidget("relOrgId")).setEnabled(false);
                                ((FocusWidget)orgRelForm.getFieldWidget("relOrgName")).setEnabled(false);

                                ((KSDatePicker)orgRelForm.getFieldWidget("relEffDate")).setValue(orgRelationInfo.getEffectiveDate());
                                ((KSDatePicker)orgRelForm.getFieldWidget("relExpDate")).setValue(orgRelationInfo.getExpirationDate());
                            }
                        }            
                });        
        }
        
        private void populateOrgRelationTypes() {
            OrgRpcService.Util.getInstance().getOrgOrgRelationTypes(new AsyncCallback<List<OrgOrgRelationTypeInfo>>(){
                public void onFailure(Throwable caught) {
                    Window.alert(caught.getMessage());
                }

                public void onSuccess(List<OrgOrgRelationTypeInfo> orgRelTypes) {
                    final Map<String,String> map = new LinkedHashMap<String, String>();
                    for(OrgOrgRelationTypeInfo info : orgRelTypes) {
                        map.put(info.getId(), info.getName());
                    }
                    orgRelTypeList = new ListItems() {

                        @Override
                        public List<String> getAttrKeys() {
                            return null; //apparently unused
                        }

                        @Override
                        public String getItemAttribute(String id, String attrkey) {
                            return null; //apparently unused
                        }

                        @Override
                        public int getItemCount() {
                            return map.size();
                        }

                        @Override
                        public List<String> getItemIds() {
                            return new ArrayList<String>(map.keySet());
                        }

                        @Override
                        public String getItemText(String id) {
                            return map.get(id);
                        }
                    };
                }
            });                
        }

        public KSFormLayoutPanel createBlankRelation() {
            HorizontalPanel panel = new HorizontalPanel();
            final KSFormLayoutPanel orgRelForm = new KSFormLayoutPanel();

            final KSDropDown orgRelTypeDropDown = new KSDropDown();

            addFormField(new KSTextBox(), "Organization", "relOrgName", orgRelForm);
            KSTextBox relOrgId = new KSTextBox();
            relOrgId.setEnabled(true);
            addFormField(relOrgId, "Organization Id", "relOrgId", orgRelForm);
            addFormField(orgRelTypeDropDown, "Relationship", "relType", orgRelForm);
            addFormField(new KSDatePicker(), "Effective date", "relEffDate", orgRelForm);
            addFormField(new KSDatePicker(), "Expiration date", "relExpDate", orgRelForm);
            addFormField(new KSTextArea(), "Note", "relNote", orgRelForm);

            panel.add(orgRelForm);
            panel.add(new KSButton("Find Org", new ClickHandler() {
                @Override
                public void onClick(ClickEvent event) {
                    final KSModalDialogPanel searchPopup = new KSModalDialogPanel();
                    
                    OrgSearchWidget orgSearchWidget = new OrgSearchWidget();
                    orgSearchWidget.addSelectionHandler(new SelectionHandler<OrgInfo>(){
                        public void onSelection(SelectionEvent<OrgInfo> event) {
                            OrgInfo o = event.getSelectedItem();
                            orgRelForm.setFieldValue("relOrgName", o.getLongName());
                            orgRelForm.setFieldValue("relOrgId", o.getId());
                            searchPopup.hide();
                        }
                    });
                    
                    VerticalPanel popupContent = new VerticalPanel();
                    popupContent.add(orgSearchWidget);
                    popupContent.add(new KSButton("Cancel", new ClickHandler(){
                        public void onClick(ClickEvent event) {
                            searchPopup.hide();
                        }                
                    }));
                    
                    searchPopup.setWidget(popupContent);
                }}));
            
            final HashMap<String, Object> map = new HashMap<String, Object>();
            
            add(panel, new CloseHandler<OrgMultiWidget>() {
                @Override
                public void onClose(CloseEvent<OrgMultiWidget> event) {
                    map.put("deleted", Boolean.TRUE);
                }});
            
            if(orgRelTypeList == null)
                DeferredCommand.addCommand(new IncrementalCommand() {
                    @Override
                    public boolean execute() {
                        if(orgRelTypeList != null) {
                            orgRelTypeDropDown.setListItems(orgRelTypeList);
                            return false;
                        }
                        return true;
                    }});
            else
                orgRelTypeDropDown.setListItems(orgRelTypeList);

            map.put("form",orgRelForm);
            forms.add(map);
            return orgRelForm;
        }

        @Override
        protected void save() {
            for (Map<String,Object> formMap : forms) {
                KSFormLayoutPanel form = (KSFormLayoutPanel) formMap.get("form");
                if (form.getFieldValue("relOrgId").length() == 0)
                    continue; //skipping this one
                OrgOrgRelationInfo orgRelationInfo = new OrgOrgRelationInfo();        
                
                orgRelationInfo.setOrgId(orgId);
                orgRelationInfo.setId((String) formMap.get("orgRelId"));
                
                MetaInfo orgRelMetaInfo = new MetaInfo();
                orgRelMetaInfo.setVersionInd((String) formMap.get("orgRelVersion"));
                orgRelationInfo.setMetaInfo(orgRelMetaInfo);
                
                orgRelationInfo.setType(form.getFieldValue("relType"));
                
                //TODO: This should lookup orgId based on related org name
                orgRelationInfo.setRelatedOrgId(form.getFieldValue("relOrgId"));
                
                orgRelationInfo.setEffectiveDate(((KSDatePicker)form.getFieldWidget("relEffDate")).getValue());
                orgRelationInfo.setExpirationDate(((KSDatePicker)form.getFieldWidget("relExpDate")).getValue());
                
                if (orgRelationInfo.getId() == null){
                    OrgRpcService.Util.getInstance().createOrgOrgRelation(orgRelationInfo, 
                            new AsyncCallback<OrgOrgRelationInfo>(){
                        public void onFailure(Throwable caught) {
                            Window.alert(caught.getMessage());
                        }
            
                        public void onSuccess(final OrgOrgRelationInfo result) {
                            fireEvent(new SaveEvent() { //TODO fix this terrible terrible hack
                                public String toString() {
                                    return "Org relation created with id: " + result.getId();
                                }
                            });
                        }
                    });
                }else if(formMap.get("deleted") != null){
                    OrgRpcService.Util.getInstance().removeOrgOrgRelation(orgRelationInfo.getId(), 
                            new AsyncCallback<StatusInfo>(){
                        public void onFailure(Throwable caught) {
                            Window.alert(caught.getMessage());
                        }
            
                        public void onSuccess(final StatusInfo result) {
                            fireEvent(new SaveEvent() { //TODO fix this terrible terrible hack
                                public String toString() {
                                    return "Org relation deleted: " + result.getMessage();
                                }
                            });
                        }
                    });
                }else{
                    OrgRpcService.Util.getInstance().updateOrgOrgRelation(orgRelationInfo, 
                            new AsyncCallback<OrgOrgRelationInfo>(){
                        public void onFailure(Throwable caught) {
                            Window.alert(caught.getMessage());
                        }
            
                        public void onSuccess(final OrgOrgRelationInfo result) {
                            fireEvent(new SaveEvent() { //TODO fix this terrible terrible hack
                                public String toString() {
                                    return "Org relation updated with id: " + result.getId();
                                }
                            });
                        }
                    });
                }
            }
        }

        @Override
        protected void create() {
            createBlankRelation();
        }
    }

    static class OrgPersonRelationWidget extends OrgMultiWidget {
        private ListItems orgPersonRelTypeList;
        
        List<Map<String,Object>> forms = new ArrayList<Map<String,Object>>();
        
        public OrgPersonRelationWidget() {
            this(null);
        }
        public OrgPersonRelationWidget(String orgId) {
            this(orgId,false);
        }
        public OrgPersonRelationWidget(String orgId, boolean open) {
            super(new KSDisclosureSection("Membership", null, open));
            this.orgId = orgId;
            if(orgId != null) { //basically can't do much without an orgId
                populateRelationshipTypes();
                populatePersonRelationInfos();
            }
        }
        
        public KSFormLayoutPanel createBlankPersonRelation() {
            HorizontalPanel panel = new HorizontalPanel();
            final KSFormLayoutPanel orgPersonRelForm = new KSFormLayoutPanel();

            final KSDropDown orgPersonRelTypeDropDown = new KSDropDown();

            addFormField(new KSTextBox(), "Person", "relPersonName", orgPersonRelForm);
            KSTextBox relPersonId = new KSTextBox();
            relPersonId.setEnabled(true);
            addFormField(relPersonId, "Person Id", "relPersonId", orgPersonRelForm);
            addFormField(orgPersonRelTypeDropDown, "Relationship", "relType", orgPersonRelForm);
            addFormField(new KSDatePicker(), "Effective date", "relEffDate", orgPersonRelForm);
            addFormField(new KSDatePicker(), "Expiration date", "relExpDate", orgPersonRelForm);

            panel.add(orgPersonRelForm);
            panel.add(new KSButton("Find Person", new ClickHandler() {

                @Override
                public void onClick(ClickEvent event) {
                    final KSModalDialogPanel searchPopup = new KSModalDialogPanel();
                    
                    PersonSearchWidget personSearchWidget = new PersonSearchWidget();
                    personSearchWidget.addSelectionHandler(new SelectionHandler<PersonInfo>(){
                        public void onSelection(SelectionEvent<PersonInfo> event) {
                            PersonInfo o = event.getSelectedItem();
                            orgPersonRelForm.setFieldValue("relPersonName", o.getPersonNameInfoList().get(0).getGivenName());
                            orgPersonRelForm.setFieldValue("relPersonId", o.getId());
                            searchPopup.hide();
                        }
                    });
                    
                    VerticalPanel popupContent = new VerticalPanel();
                    popupContent.add(personSearchWidget);
                    popupContent.add(new KSButton("Cancel", new ClickHandler(){
                        public void onClick(ClickEvent event) {
                            searchPopup.hide();
                        }                
                    }));
                    
                    searchPopup.setWidget(popupContent);
                    searchPopup.show();
                }})); //TODO implement

            final HashMap<String, Object> map = new HashMap<String, Object>();
            
            add(panel, new CloseHandler<OrgMultiWidget>() {
                @Override
                public void onClose(CloseEvent<OrgMultiWidget> event) {
                    map.put("deleted", Boolean.TRUE);
                }});
            
            if(orgPersonRelTypeList == null && orgId != null)
                DeferredCommand.addCommand(new IncrementalCommand() {
                    @Override
                    public boolean execute() {
                        if(orgPersonRelTypeList != null) {
                            orgPersonRelTypeDropDown.setListItems(orgPersonRelTypeList);
                            return false;
                        }
                        return true;
                    }});
            else if(orgPersonRelTypeList != null)
                orgPersonRelTypeDropDown.setListItems(orgPersonRelTypeList);

            map.put("form",orgPersonRelForm);
            forms.add(map);
            
            return orgPersonRelForm;
        }

        private void populateRelationshipTypes() {
            if(orgId != null){
                OrgRpcService.Util.getInstance().getPositionRestrictionsByOrg(orgId, new AsyncCallback<List<OrgPositionRestrictionInfo>>(){
                    public void onFailure(Throwable caught) {
                        Window.alert(caught.getMessage());
                    }
        
                    public void onSuccess(List<OrgPositionRestrictionInfo> orgPositionRestrictions) {
                        if(orgPositionRestrictions != null) {
                            final Map<String,String> restrictions = new LinkedHashMap<String, String>();
                            for (OrgPositionRestrictionInfo info : orgPositionRestrictions) {
                                restrictions.put(info.getOrgPersonRelationTypeKey(), info.getTitle());
                            }
                            orgPersonRelTypeList = new ListItems() {

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
                                    return restrictions.size();
                                }

                                @Override
                                public List<String> getItemIds() {
                                    return new ArrayList<String>(restrictions.keySet());
                                }

                                @Override
                                public String getItemText(String id) {
                                    return restrictions.get(id);
                                }
                                
                            };
                        }
                    }
                });             
            }
        }
        
        private void populatePersonRelationInfos() {
            OrgRpcService.Util.getInstance().getOrgPersonRelationsByOrg(orgId, 
                    new AsyncCallback<List<OrgPersonRelationInfo>>(){

                        public void onFailure(Throwable caught) {
                            Window.alert(caught.getMessage());
                        }

                        public void onSuccess(List<OrgPersonRelationInfo> orgPersonRelations) {
                            for(OrgPersonRelationInfo orgPersonRelationInfo : orgPersonRelations) {
                                final KSFormLayoutPanel orgPersonRelForm = createBlankPersonRelation();
                                Map<String,Object> map = forms.get(forms.size() - 1); //should always be the last one
                                map.put("orgPersonRelId",orgPersonRelationInfo.getId());
                                map.put("orgPersonRelVersion",orgPersonRelationInfo.getMetaInfo().getVersionInd());
                                orgPersonRelForm.setFieldValue("relPersonId",orgPersonRelationInfo.getPersonId());
                                final String orgPersonRelType = orgPersonRelationInfo.getType();
                                
                                PersonRpc.Util.getInstance().fetchPerson(orgPersonRelationInfo.getPersonId(), 
                                        new AsyncCallback<PersonInfo>(){

                                            public void onFailure(Throwable caught) {
                                            }

                                            public void onSuccess(PersonInfo personInfo) {
                                                orgPersonRelForm.setFieldValue("relPersonName", personInfo.getPersonNameInfoList().get(0).getGivenName());                       
                                            }            
                                });

                                //Disable editing of rel org id and name
                                ((FocusWidget)orgPersonRelForm.getFieldWidget("relPersonId")).setEnabled(false);
                                ((FocusWidget)orgPersonRelForm.getFieldWidget("relPersonName")).setEnabled(false);

                                ((KSDatePicker)orgPersonRelForm.getFieldWidget("relEffDate")).setValue(orgPersonRelationInfo.getEffectiveDate());
                                ((KSDatePicker)orgPersonRelForm.getFieldWidget("relExpDate")).setValue(orgPersonRelationInfo.getExpirationDate());
                                
                                final KSDropDown posTypeDropDown = (KSDropDown) orgPersonRelForm.getFieldWidget("relType");
                                
                                if(posTypeDropDown.getListItems() != null)
                                    posTypeDropDown.selectItem(orgPersonRelType);
                                else
                                    DeferredCommand.addCommand(new IncrementalCommand() {
                                        @Override
                                        public boolean execute() {
                                            if(posTypeDropDown.getListItems() != null) {
                                                posTypeDropDown.selectItem(orgPersonRelType);
                                                return false;
                                            }
                                            return true;
                                        }});
                            }
                        }
                });        
        }
        
        @Override
        protected void save() {
            for (Map<String,Object> formMap : forms) {
                KSFormLayoutPanel orgPersonRelForm = (KSFormLayoutPanel) formMap.get("form");
                if (orgPersonRelForm.getFieldValue("relPersonId").length() == 0)
                    continue; //skipping this one
                
                OrgPersonRelationInfo orgPersonRelationInfo = new OrgPersonRelationInfo();        
                
                orgPersonRelationInfo.setId((String) formMap.get("orgPersonRelId"));
                orgPersonRelationInfo.setOrgId(orgId);
                
                MetaInfo orgPersonRelMetaInfo = new MetaInfo();
                orgPersonRelMetaInfo.setVersionInd((String) formMap.get("orgPersonRelVersion"));
                orgPersonRelationInfo.setMetaInfo(orgPersonRelMetaInfo);
                
                orgPersonRelationInfo.setType(orgPersonRelForm.getFieldValue("relType"));
                
                //TODO: This should lookup orgId based on related org name
                orgPersonRelationInfo.setPersonId(orgPersonRelForm.getFieldValue("relPersonId"));
                
                orgPersonRelationInfo.setEffectiveDate(((KSDatePicker)orgPersonRelForm.getFieldWidget("relEffDate")).getValue());
                orgPersonRelationInfo.setExpirationDate(((KSDatePicker)orgPersonRelForm.getFieldWidget("relExpDate")).getValue());
                
                if (orgPersonRelationInfo.getId() == null){
                    OrgRpcService.Util.getInstance().createOrgPersonRelation(orgId,orgPersonRelationInfo.getPersonId(),orgPersonRelationInfo.getType(), orgPersonRelationInfo, 
                            new AsyncCallback<OrgPersonRelationInfo>(){
                        public void onFailure(Throwable caught) {
                            Window.alert(caught.getMessage());
                        }
            
                        public void onSuccess(final OrgPersonRelationInfo result) {
                            fireEvent(new SaveEvent() { //TODO fix this terrible terrible hack
                                public String toString() {
                                    return "Person relation created with id: " + result.getId();
                                }
                            });
                        }
                    });
                } else if(formMap.get("deleted") != null){
                    OrgRpcService.Util.getInstance().removeOrgPersonRelation(orgPersonRelationInfo.getId(), 
                            new AsyncCallback<StatusInfo>(){
                        public void onFailure(Throwable caught) {
                            Window.alert(caught.getMessage());
                        }
            
                        public void onSuccess(final StatusInfo result) {
                            fireEvent(new SaveEvent() { //TODO fix this terrible terrible hack
                                public String toString() {
                                    return "Person relation deleted: " + result.getMessage();
                                }
                            });
                        }
                    });
                } else {
                    OrgRpcService.Util.getInstance().updateOrgPersonRelation(orgPersonRelationInfo.getId(),orgPersonRelationInfo, new AsyncCallback<OrgPersonRelationInfo>(){
                        public void onFailure(Throwable caught) {
                            Window.alert(caught.getMessage());
                        }
            
                        public void onSuccess(final OrgPersonRelationInfo result) {
                            fireEvent(new SaveEvent() { //TODO fix this terrible terrible hack
                                public String toString() {
                                    return "Person relation updated for id: " + result.getId();
                                }
                            });
                        }
                    });
                }
            }
        }

        @Override
        protected void create() {
            createBlankPersonRelation();
        }
    }

    static class SingleListItem implements ListItems {
        
        private String item;

        public SingleListItem(String item) {
            this.item = item;
        }

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
            return 1;
        }

        @Override
        public List<String> getItemIds() {
            return Arrays.asList(item);
        }

        @Override
        public String getItemText(String id) {
            if (id.equals(item))
                return item;
            return null;
        }

    }

}
