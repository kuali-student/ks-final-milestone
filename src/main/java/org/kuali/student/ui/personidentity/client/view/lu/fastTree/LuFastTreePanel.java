package org.kuali.student.ui.personidentity.client.view.lu.fastTree;

import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import org.kuali.student.commons.ui.mvc.client.Controller;
import org.kuali.student.commons.ui.mvc.client.EventTypeHierarchy;
import org.kuali.student.commons.ui.mvc.client.EventTypeRegistry;
import org.kuali.student.commons.ui.mvc.client.MVC;
import org.kuali.student.commons.ui.mvc.client.MVCEvent;
import org.kuali.student.commons.ui.mvc.client.MVCEventListener;
import org.kuali.student.ui.personidentity.client.controller.LearningUnitController;
import org.kuali.student.ui.personidentity.client.model.lu.GwtLuTypeInfo;
import org.kuali.student.ui.personidentity.client.model.lu.GwtLuiInfo;

import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.DeferredCommand;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.widgetideas.client.FastTree;
import com.google.gwt.widgetideas.client.FastTreeItem;

public class LuFastTreePanel extends Composite {

    Controller c = null;

    public static class LuFastTreePanelEvent extends MVCEvent {
        static {
            EventTypeRegistry.register(LuFastTreePanelEvent.class, new LuFastTreePanelEvent().getHierarchy());
        }

        public EventTypeHierarchy getHierarchy() {
            return super.getHierarchy().add(LuFastTreePanelEvent.class);
        }
    }

    public static class LuFastTreePanelEventPopulateChildren extends LuFastTreePanelEvent {
        static {
            EventTypeRegistry.register(LuFastTreePanelEventPopulateChildren.class, new LuFastTreePanelEventPopulateChildren().getHierarchy());
        }

        public EventTypeHierarchy getHierarchy() {
            return super.getHierarchy().add(LuFastTreePanelEventPopulateChildren.class);
        }
    }

    static {
        new LuFastTreePanelEvent();
        new LuFastTreePanelEventPopulateChildren();
    }

    public class LuiRootFastTreeItem extends FastTreeItem {

        boolean ensureChildren = true;
        
        public LuiRootFastTreeItem() {
            super();
            this.setText("Lu Types");
        }

        
        public void setEnsureChildren(boolean ensureChildren) {
            this.ensureChildren = ensureChildren;
        }

        protected void ensureChildren() {
            if(this.ensureChildren)
                this.populateChildren();
        }

        public void populateChildren() {
            LearningUnitController.findLuTypes(new AsyncCallback() {
                public void onFailure(Throwable caught) {
                    Window.alert(caught.getMessage());
                }

                public void onSuccess(Object result) {
                    List<GwtLuTypeInfo> lTypes = (List<GwtLuTypeInfo>) result;
                    if (lTypes != null) {
                        populateTree(lTypes);
                    }

                    c.getEventDispatcher().fireEvent(LuFastTreePanelEventPopulateChildren.class, lTypes);
                }
            });
        }

        public void populateTree(List<GwtLuTypeInfo> lTypes) {
            if (lTypes != null) {
                removeItems();
                for (GwtLuTypeInfo luTypeInfo : lTypes) {
                    LuTypeFastItem luItem = new LuTypeFastItem(luTypeInfo);
                    addItem(luItem);
                }
            }

        }

    };

    VerticalPanel panel = new VerticalPanel();
    FastTree luTree = new FastTree();

    boolean loaded = false;

    public LuFastTreePanel() {
        super();

        LuiRootFastTreeItem ftm = new LuiRootFastTreeItem();
        ftm.addItem("");
        ftm.becomeInteriorNode();
        luTree.addItem(ftm);

        panel.add(luTree);

        initWidget(panel);
    }

    protected void onLoad() {
        super.onLoad();
        if (!loaded) {
            loaded = true;
            c = MVC.findParentController(this);
        }
    }

    public void showItem(GwtLuiInfo item) {

        String itemText = item.getCluDisplay().getCluShortName() + "-" + item.getLuiCode();
        FastTreeItem itm = this.getItem(itemText);

        if (itm != null) {
            luTree.setSelectedItem(itm, true);
            luTree.ensureSelectedItemVisible();
        }
    }

 

    public void showItem(final GwtLuiInfo item, final GwtLuTypeInfo tInfo, final FastTreeItem tItem) {
        FastTreeItem itm = null;
        if (tItem == null) { // start at the root
            itm = this.getItem("Lu Types");
            this.showItem(item, tInfo, itm);
        } else {
            if (tItem instanceof LuiRootFastTreeItem) {
                itm = this.getItem(tInfo.getDescription());
                if (itm != null) {
                    this.showItem(item, tInfo, itm);
                } else {
                    // this is a problem bc of the async stuff.
                    ((LuiRootFastTreeItem) tItem).populateChildren();
                    ((LuiRootFastTreeItem) tItem).setEnsureChildren(false);
                    
                    c.getEventDispatcher().addListener(LuFastTreePanelEventPopulateChildren.class, new MVCEventListener() {
                        public void onEvent(Class<? extends MVCEvent> event, Object data) {
                            final MVCEventListener m = this;
                            DeferredCommand.addCommand(new Command() {
                                public void execute() {
                                    c.getEventDispatcher().removeListener(LuFastTreePanelEventPopulateChildren.class, m);
                                }
                            });

                            showItem(item, tInfo, tItem);
                        }

                    });

                }
            } else if (tItem instanceof LuTypeFastItem) {
                itm = this.getItem(item.getCluDisplay().getCluShortName());
                if (itm != null) {
                    this.showItem(item, tInfo, itm);
                } else {
                    // this is a problem bc of the async stuff.
                    ((LuTypeFastItem) tItem).populateChildren();
                    ((LuTypeFastItem) tItem).setEnsureChildren(false);
                    //((LuTypeFastItem) tItem).ensureChildren();
                    c.getEventDispatcher().addListener(LuTypeFastItem.LuTypeFastItemPopulated.class, new MVCEventListener() {
                        public void onEvent(Class<? extends MVCEvent> event, Object data) {
                            final MVCEventListener m = this;
                            DeferredCommand.addCommand(new Command() {
                                public void execute() {
                                    c.getEventDispatcher().removeListener(LuTypeFastItem.LuTypeFastItemPopulated.class, m);
                                }
                            });
                            showItem(item, tInfo, tItem);
                        }
                    });
                }
            } else if (tItem instanceof CluDisplayFastItem) {
                itm = this.getItem(item.getCluDisplay().getCluShortName() + "-" + item.getLuiCode());
                if (itm != null) {
                    this.showItem(item, tInfo, itm);
                } else {
                    // this is a problem bc of the async stuff.
                    ((CluDisplayFastItem) tItem).populateChildren();
                    ((CluDisplayFastItem) tItem).setEnsureChildren(false);
                    //((CluDisplayFastItem) tItem).ensureChildren();
                    c.getEventDispatcher().addListener(CluDisplayFastItem.CluDisplayFastItemPopulated.class, new MVCEventListener() {
                        public void onEvent(Class<? extends MVCEvent> event, Object data) {
                            final MVCEventListener m = this;
                            DeferredCommand.addCommand(new Command() {
                                public void execute() {
                                    c.getEventDispatcher().removeListener(CluDisplayFastItem.CluDisplayFastItemPopulated.class, m);
                                }
                            });

                            showItem(item, tInfo, tItem);
                        }
                    });
                }
            } else if (tItem instanceof LuiFastTreeItem) {
                luTree.setSelectedItem(tItem, true);
                luTree.ensureSelectedItemVisible();
            }
        }

    }

    public FastTreeItem getItem(String itemText) {
        FastTreeItem fRet = null;
        if (luTree != null) {

            Iterator<FastTreeItem> itr = luTree.treeItemIterator();
            while (itr.hasNext()) {
                FastTreeItem itm = itr.next();
                if (itm.getText().equalsIgnoreCase(itemText)) {
                    fRet = itm;
                }
            }
        }
        return fRet;
    }

}
