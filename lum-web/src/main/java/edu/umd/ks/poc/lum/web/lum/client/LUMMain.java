package edu.umd.ks.poc.lum.web.lum.client;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.kuali.student.commons.ui.mvc.client.Controller;
import org.kuali.student.commons.ui.mvc.client.EventDispatcher;
import org.kuali.student.commons.ui.mvc.client.MVCEvent;
import org.kuali.student.commons.ui.mvc.client.MVCEventListener;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.DeferredCommand;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.SerializableException;
import com.google.gwt.user.client.ui.DecoratorPanel;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.Frame;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.client.ui.DockPanel.DockLayoutConstant;

import edu.umd.ks.poc.lum.lu.dto.CluCreateInfo;
import edu.umd.ks.poc.lum.lu.dto.CluInfo;
import edu.umd.ks.poc.lum.lu.dto.CluUpdateInfo;
import edu.umd.ks.poc.lum.lu.dto.LuAttributeTypeInfo;
import edu.umd.ks.poc.lum.lu.dto.LuTypeInfo;
import edu.umd.ks.poc.lum.lu.dto.Status;
import edu.umd.ks.poc.lum.scat.dto.ScatTableInfo;
import edu.umd.ks.poc.lum.web.clu.client.CluButtonWrapper;
import edu.umd.ks.poc.lum.web.clu.client.CluPanel;
import edu.umd.ks.poc.lum.web.clu.client.CluWorkflowComposite;
import edu.umd.ks.poc.lum.web.clu.client.FindClusPanel;
import edu.umd.ks.poc.lum.web.clu.client.CluPanel.CreateCluEvent;
import edu.umd.ks.poc.lum.web.clu.client.FindClusPanel.CluDetailsEvent;
import edu.umd.ks.poc.lum.web.core.client.Authorization;
import edu.umd.ks.poc.lum.web.core.client.GWTUtils;
import edu.umd.ks.poc.lum.web.core.client.GlobalEventDispatcher;
import edu.umd.ks.poc.lum.web.kew.client.service.WorkflowUtilityRpcService;
import edu.umd.ks.poc.lum.web.kew.client.view.ActionListPanel;
import edu.umd.ks.poc.lum.web.lu.client.service.LuRpcService;
import edu.umd.ks.poc.lum.web.lum.client.view.HeaderPanel;
import edu.umd.ks.poc.lum.web.lum.client.view.LeftMenu;
import edu.umd.ks.poc.lum.web.lutype.client.view.AddAttributePanel;
import edu.umd.ks.poc.lum.web.lutype.client.view.FindLuTypePanel;
import edu.umd.ks.poc.lum.web.lutype.client.view.LuTypeCreatePanel;
import edu.umd.ks.poc.lum.web.lutype.client.view.AddAttributePanel.AttributeCreateEvent;
import edu.umd.ks.poc.lum.web.lutype.client.view.AddAttributePanel.AttributeRemoveEvent;
import edu.umd.ks.poc.lum.web.lutype.client.view.FindLuTypePanel.LuTypeSelectEvent;
import edu.umd.ks.poc.lum.web.scat.client.CreateScat;
import edu.umd.ks.poc.lum.web.scat.client.ScatDetails;
import edu.umd.ks.poc.lum.web.scat.client.SearchScatTables;
import edu.umd.ks.poc.lum.web.scat.client.SearchScatTables.ScatDetailsEvent;

public class LUMMain extends Controller {

    DockPanel dBase = new DockPanel();
    boolean loaded = false;
    SimplePanel center = new SimplePanel();

    String luTypeKey;

    LuTypeCreatePanel luTypeCreatePanel = new LuTypeCreatePanel();
    AddAttributePanel addAttributePanel = new AddAttributePanel("Name", "Desc");
    FindLuTypePanel findLuTypePanel = new FindLuTypePanel();
    CluButtonWrapper createClu = new CluButtonWrapper(null, null);
    FindClusPanel findClu = new FindClusPanel();

    CreateScat createScatPanel = new CreateScat();
    SearchScatTables searchScatTablesPanel = new SearchScatTables();

    ActionListPanel actionListPanel = null;
    Frame iFrame = null;

    LumConstants constants = (LumConstants) GWT.create(LumConstants.class);

    public LUMMain() {
        super.initWidget(dBase);
    }

    public EventDispatcher getEventDispatcher() {
        return GlobalEventDispatcher.getInstance();
    }

    protected void onLoad() {
        super.onLoad();

        WorkflowUtilityRpcService.Util.getInstance().getUser(new AsyncCallback<String>() {
            public void onFailure(Throwable caught) {
                Window.alert(caught.getMessage());
            }

            public void onSuccess(String result) {
                Authorization.setUser(result);
                onModuleLoad();
            }

        });

    }

    /**
     * This overridden method ...
     *
     * @see com.google.gwt.user.client.ui.Widget#onLoad()
     */

    protected void onModuleLoad() {

        if (!loaded) {
            loaded = true;

            DecoratorPanel base = new DecoratorPanel();
            base.setWidth("100%");

            dBase.setWidth("100%");
            dBase.setHeight("100%");
            dBase.setHorizontalAlignment(dBase.ALIGN_LEFT);

            ///dBase.getNorth().setWidget(new HeaderPanel());
            //dBase.getWest().setWidget(new LeftMenu());
            base.setWidget(center);
            //dBase.getCenter().setWidget(base);

            HeaderPanel hp = new HeaderPanel();
            LeftMenu lm = new LeftMenu();
            lm.setWidth("100%");
            center.setWidth("100%");
            center.setHeight("100%");

            dBase.add(hp,dBase.NORTH);
            dBase.add(lm,dBase.WEST);
            dBase.add(center, dBase.CENTER);

            dBase.setCellWidth(lm, "15%");
            dBase.setCellWidth(center, "85%");
            dBase.setCellHeight(center, "100%");


            this.addListeners();
            // default

        }

        String params = GWTUtils.getParamString();

        if (params != null && !"".equals(params)) {

            Map<String, String> paramMap = GWTUtils.parseParamString(params);

            if (paramMap.get("backdoorId") != null) {
                // GlobalEventDispatcher.getInstance().fireEvent(CurrentUser.AuthChangeEvent.class);
            }
            if (paramMap.get("docId") != null) {
                final String docId = paramMap.get("docId");
                WorkflowUtilityRpcService.Util.getInstance().getCluIdForDocument(docId, new AsyncCallback<String>() {
                    public void onFailure(Throwable caught) {
                        Window.alert(caught.getMessage());

                    }

                    public void onSuccess(String cluId) {
                        CluWorkflowComposite cwc = new CluWorkflowComposite();
                        cwc.setCluId(cluId);
                        cwc.setRouteDocId(docId);
                        GlobalEventDispatcher.getInstance().fireEvent(CluDetailsEvent.class, cwc);
                    }

                });

            }

        }

    }

    /**
     * @return the center
     */
    public Widget getCenter() {
        return center.getWidget();
    }

    /**
     * @param center
     *            the center to set
     */
    public void setCenter(Widget center) {
        this.center.setWidget(center);
    }

    protected void initModel() {

    }

    protected void addListeners() {
        getEventDispatcher().addListener(LeftMenu.LuTypeMenuClick.class, new MVCEventListener() {
            public void onEvent(Class<? extends MVCEvent> event, Object data) {
                final String strMenu = (String) data;

                if (LeftMenu.CREATE_LU_TYPE.equals(strMenu)) {
                    DeferredCommand.addCommand(new Command() {
                        public void execute() {
                            luTypeCreatePanel = new LuTypeCreatePanel();
                            setCenter(luTypeCreatePanel);
                        }
                    });
                } else if (LeftMenu.ADD_ATTRIBUTES.equals(strMenu)) {
                    DeferredCommand.addCommand(new Command() {
                        public void execute() {
                            setCenter(addAttributePanel);
                        }
                    });

                } else if (LeftMenu.FIND_LU_TYPE.equals(strMenu)) {
                    DeferredCommand.addCommand(new Command() {
                        public void execute() {
                            findLuTypePanel = new FindLuTypePanel();
                            setCenter(findLuTypePanel);
                        }
                    });

                } else if (LeftMenu.CREATE_CLU.equals(strMenu)) {
                    DeferredCommand.addCommand(new Command() {
                        public void execute() {
                            createClu = new CluButtonWrapper(null, null);
                            setCenter(createClu);
                        }
                    });

                } else if (LeftMenu.FIND_CLU.equals(strMenu)) {
                    DeferredCommand.addCommand(new Command() {
                        public void execute() {
                            findClu = new FindClusPanel();
                            setCenter(findClu);
                        }
                    });

                } else if (LeftMenu.CREATE_SCAT.equals(strMenu)) {
                    DeferredCommand.addCommand(new Command() {
                        public void execute() {
                            createScatPanel = new CreateScat();
                            setCenter(createScatPanel);
                        }
                    });

                } else if (LeftMenu.SEARCH_SCAT.equals(strMenu)) {
                    DeferredCommand.addCommand(new Command() {
                        public void execute() {
                            searchScatTablesPanel = new SearchScatTables();
                            setCenter(searchScatTablesPanel);
                        }
                    });

                } else if (LeftMenu.ACTION_LIST.equals(strMenu)) {
                    DeferredCommand.addCommand(new Command() {
                        public void execute() {
                            actionListPanel = new ActionListPanel();
                            setCenter(actionListPanel);
                        }
                    });

                }

                DeferredCommand.addCommand(new Command() {

                    public void execute() {

                        String s = constants.iFrameLinks();

                        String[] nameVal = s.split(",");

                        for (String str : nameVal) {

                            String[] nv = str.split("\\|");

                            if (strMenu.equals(nv[0])) {
                                iFrame = new Frame(GWT.getHostPageBaseURL() + nv[1]);
                                iFrame.addStyleDependentName("WorkflowFrame");
                                iFrame.setWidth("100%");
                                iFrame.setHeight("100%");
                                setCenter(iFrame);
                            }

                        }

                    }
                });

            }

        });

        getEventDispatcher().addListener(ActionListPanel.ActionListCluApproveEvent.class, new MVCEventListener() {
            public void onEvent(Class<? extends MVCEvent> event, Object data) {
                GlobalEventDispatcher.getInstance().fireEvent(LeftMenu.LuTypeMenuClick.class, LeftMenu.ACTION_LIST);
            }
        });
        getEventDispatcher().addListener(ActionListPanel.ActionListCluDisapproveEvent.class, new MVCEventListener() {
            public void onEvent(Class<? extends MVCEvent> event, Object data) {
                actionListPanel = new ActionListPanel();
                setCenter(actionListPanel);
            }
        });

        getEventDispatcher().addListener(LuTypeCreatePanel.LuTypeCreateEvent.class, new MVCEventListener() {
            public void onEvent(Class<? extends MVCEvent> event, Object data) {
                final LuTypeInfo info = (LuTypeInfo) data;

                LuRpcService.Util.getInstance().createLuTypeInfo(info, new AsyncCallback<String>() {

                    public void onFailure(Throwable caught) {
                        Window.alert(caught.getMessage());
                    }

                    public void onSuccess(String result) {
                        luTypeKey = result;
                        try {
                            LuRpcService.Util.getInstance().fetchLuType(luTypeKey, new AsyncCallback<LuTypeInfo>() {

                                public void onFailure(Throwable caught) {
                                    Window.alert(caught.getMessage());
                                }

                                public void onSuccess(LuTypeInfo result) {
                                    addAttributePanel.setCurrLuType(result);
                                    addAttributePanel.refresh();
                                    setCenter(addAttributePanel);
                                }

                            });
                        } catch (SerializableException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }

                    }

                });

            }
        });

        getEventDispatcher().addListener(AttributeCreateEvent.class, new MVCEventListener() {

            public void onEvent(Class<? extends MVCEvent> event, Object data) {
                LuAttributeTypeInfo attrTypeInfo = (LuAttributeTypeInfo) data;
                LuRpcService.Util.getInstance().createLuAttributeTypeInfo(attrTypeInfo, new AsyncCallback<String>() {

                    public void onFailure(Throwable caught) {
                        Window.alert(caught.getMessage());
                    }

                    public void onSuccess(String result) {
                        final String attrTypeInfoId = result;
                        // Update the LuType
                        try {
                            LuRpcService.Util.getInstance().fetchLuType(luTypeKey, new AsyncCallback<LuTypeInfo>() {

                                public void onFailure(Throwable caught) {
                                    // TODO Auto-generated method stub
                                    Window.alert(caught.getMessage());
                                }

                                public void onSuccess(LuTypeInfo result) {
                                    result.getLuAttributeTypeIds().add(attrTypeInfoId);
                                    LuRpcService.Util.getInstance().updateLuTypeInfo(result, new AsyncCallback<String>() {

                                        public void onFailure(Throwable caught) {
                                            // TODO Auto-generated method stub
                                            Window.alert(caught.getMessage());
                                        }

                                        public void onSuccess(String result) {
                                            // refresh the attribute screen
                                            addAttributePanel.refresh();
                                        }

                                    });
                                }

                            });
                        } catch (SerializableException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }

                });

            }

        });

        getEventDispatcher().addListener(LuTypeSelectEvent.class, new MVCEventListener() {

            public void onEvent(Class<? extends MVCEvent> event, Object data) {
                luTypeKey = (String) data;
                try {
                    LuRpcService.Util.getInstance().fetchLuType(luTypeKey, new AsyncCallback<LuTypeInfo>() {

                        public void onFailure(Throwable caught) {
                            Window.alert(caught.getMessage());
                        }

                        public void onSuccess(LuTypeInfo result) {
                            addAttributePanel.setCurrLuType(result);
                            addAttributePanel.refresh();
                            setCenter(addAttributePanel);
                        }

                    });
                } catch (SerializableException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        });

        getEventDispatcher().addListener(AttributeRemoveEvent.class, new MVCEventListener() {

            public void onEvent(Class<? extends MVCEvent> event, Object data) {
                final String attrTypeId = (String) data;
                try {
                    LuRpcService.Util.getInstance().fetchLuType(luTypeKey, new AsyncCallback<LuTypeInfo>() {

                        public void onFailure(Throwable caught) {
                            Window.alert(caught.getMessage());
                        }

                        public void onSuccess(LuTypeInfo result) {
                            for (Iterator<String> i = result.getLuAttributeTypeIds().iterator(); i.hasNext();) {
                                String currAttTypeId = i.next();
                                if (currAttTypeId.equals(attrTypeId)) {
                                    i.remove();
                                }
                            }
                            // Save the LuType again
                            LuRpcService.Util.getInstance().updateLuTypeInfo(result, new AsyncCallback<String>() {

                                public void onFailure(Throwable caught) {
                                    Window.alert(caught.getMessage());
                                }

                                public void onSuccess(String result) {
                                    addAttributePanel.refresh();
                                }

                            });

                        }

                    });
                } catch (SerializableException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

            }
        });

        getEventDispatcher().addListener(CluPanel.LuTypesRequestEvent.class, new MVCEventListener() {

            public void onEvent(Class<? extends MVCEvent> event, Object data) {
                try {
                    LuRpcService.Util.getInstance().findLuTypes(new AsyncCallback<List<LuTypeInfo>>() {

                        public void onFailure(Throwable caught) {
                            Window.alert(caught.getMessage());
                        }

                        public void onSuccess(List<LuTypeInfo> result) {
                            getEventDispatcher().fireEvent(CluPanel.LuTypesResponseEvent.class, result);

                        }
                    });
                } catch (SerializableException e) {
                    e.printStackTrace();
                }
            }
        });

        getEventDispatcher().addListener(CreateCluEvent.class, new MVCEventListener() {

            public void onEvent(Class<? extends MVCEvent> event, Object data) {
                final CluInfo clu = (CluInfo) data;

                try {
                    if (clu.getCluId() == null) {
                        CluCreateInfo cluCreateInfo = new CluCreateInfo();
                        cluCreateInfo.setCluLongName(clu.getCluLongName());
                        cluCreateInfo.setCluShortName(clu.getCluShortName());
                        cluCreateInfo.setDescription(clu.getDescription());
                        cluCreateInfo.setEffectiveEndDate(clu.getEffectiveEndDate());
                        cluCreateInfo.setEffectiveStartDate(clu.getEffectiveStartDate());
                        cluCreateInfo.setStatus(clu.getStatus());
                        cluCreateInfo.setAdminDepartment(clu.getAdminDepartment());
                        cluCreateInfo.getAttributes().putAll(clu.getAttributes());
                        LuRpcService.Util.getInstance().createAndRouteClu(Authorization.getUser(), clu.getLuTypeId(), cluCreateInfo, clu.getCreateUserComment(), new AsyncCallback<String>() {

                            public void onFailure(Throwable caught) {
                                Window.alert(caught.getMessage());
                            }

                            public void onSuccess(String cluId) {
                                CluWorkflowComposite cwc = new CluWorkflowComposite();
                                cwc.setCluId(cluId);
                                GlobalEventDispatcher.getInstance().fireEvent(CluDetailsEvent.class, cwc);
                            }

                        });
                    } else {
                        CluUpdateInfo cluUpdateInfo = new CluUpdateInfo();
                        cluUpdateInfo.setCluLongName(clu.getCluLongName());
                        cluUpdateInfo.setCluShortName(clu.getCluShortName());
                        cluUpdateInfo.setDescription(clu.getDescription());
                        cluUpdateInfo.setEffectiveEndDate(clu.getEffectiveEndDate());
                        cluUpdateInfo.setEffectiveStartDate(clu.getEffectiveStartDate());
                        cluUpdateInfo.setStatus(clu.getStatus());
                        cluUpdateInfo.setAdminDepartment(clu.getAdminDepartment());
                        cluUpdateInfo.getAttributes().putAll(clu.getAttributes());
                        LuRpcService.Util.getInstance().updateClu(clu.getCluId(), cluUpdateInfo, new AsyncCallback<Status>() {

                            public void onFailure(Throwable caught) {
                                Window.alert(caught.getMessage());
                            }

                            public void onSuccess(Status status) {
                                CluWorkflowComposite cwc = new CluWorkflowComposite();
                                cwc.setCluId(clu.getCluId());
                                GlobalEventDispatcher.getInstance().fireEvent(CluDetailsEvent.class, cwc);
                            }

                        });
                    }
                } catch (SerializableException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }

        });
        getEventDispatcher().addListener(ScatDetailsEvent.class, new MVCEventListener() {

            public void onEvent(Class<? extends MVCEvent> event, Object data) {
                ScatDetails scatDetails = new ScatDetails((ScatTableInfo) data);
                setCenter(scatDetails);
            }
        });
        getEventDispatcher().addListener(CluDetailsEvent.class, new MVCEventListener() {

            public void onEvent(Class<? extends MVCEvent> event, Object data) {
                CluWorkflowComposite cwc = (CluWorkflowComposite) data;
                CluButtonWrapper cluDetails = new CluButtonWrapper(cwc.getCluId(), cwc.getRouteDocId());
                setCenter(cluDetails);
            }
        });

    }

}
