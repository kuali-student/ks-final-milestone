package org.kuali.student.common.ui.client.configurable.mvc.layouts;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.DeferredCommand;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import org.kuali.student.common.ui.client.configurable.mvc.LayoutController;
import org.kuali.student.common.ui.client.mvc.Callback;
import org.kuali.student.common.ui.client.mvc.View;
import org.kuali.student.common.ui.client.widgets.KSButton;
import org.kuali.student.common.ui.client.widgets.headers.KSDocumentHeader;
import org.kuali.student.common.ui.client.widgets.menus.KSMenuItemData;
import org.kuali.student.common.ui.client.widgets.menus.impl.KSBlockMenuImpl;
import org.kuali.student.common.ui.client.widgets.panels.collapsable.VerticalCollapsableDrawer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MenuSectionController extends LayoutController implements ContentNavLayoutController {

    private KSBlockMenuImpl menu = new KSBlockMenuImpl();
    private List<KSMenuItemData> topLevelMenuItems = new ArrayList<KSMenuItemData>();
    protected Map<String, List<View>> menuViewMap = new HashMap<String, List<View>>();
    private Map<Enum<?>, List<KSButton>> viewButtonsMap = new HashMap<Enum<?>, List<KSButton>>();
    protected Map<Enum<?>, KSMenuItemData> viewMenuItemMap = new HashMap<Enum<?>, KSMenuItemData>();
    private List<View> menuOrder = new ArrayList<View>();
    private FlowPanel layout = new FlowPanel();
    private KSDocumentHeader header = new KSDocumentHeader();
    private FlowPanel rightPanel = new FlowPanel();
    private FlowPanel contentPanel = new FlowPanel();
    private FlowPanel buttonPanel = new FlowPanel();
    private VerticalPanel leftPanel = new VerticalPanel();
    private SimplePanel sideBar = new SimplePanel();
    private boolean refreshMenuOnAdd = true;
    private VerticalCollapsableDrawer collapsablePanel = new VerticalCollapsableDrawer();

    private Callback<Boolean> showViewCallback = new Callback<Boolean>() {

        @Override
        public void exec(Boolean result) {
            if (result == false) {
                KSMenuItemData item = viewMenuItemMap.get(MenuSectionController.this.getCurrentView().getViewEnum());
                if (item != null) {
                    item.setSelected(true, false);
                }
            }
        }
    };

    public MenuSectionController(String controllerId) {
        super(controllerId);
        List<View> list = new ArrayList<View>();
        menuViewMap.put("", list);
        menu.setStyleName("ks-menu-layout-menu");
        rightPanel.setStyleName("ks-menu-layout-rightColumn");
        collapsablePanel.addStyleName("ks-menu-layout-leftColumn");
        layout.addStyleName("ks-menu-layout");
        menu.setTopLevelItems(topLevelMenuItems);
        collapsablePanel.setContent(leftPanel);
        leftPanel.add(menu);
        leftPanel.add(sideBar);
        rightPanel.add(header);
        rightPanel.add(contentPanel);
        rightPanel.add(buttonPanel);
        layout.add(collapsablePanel);
        layout.add(rightPanel);
        this.initWidget(layout);
    }

    public void removeMenuNavigation() {
        collapsablePanel.removeFromParent();
    }

    public void setContentTitle(String title) {
        header.setTitle(title);
    }

    public void addContentWidget(Widget w) {
        header.addWidget(w);
    }

    public void setSideBarWidget(Widget w) {
        sideBar.setWidget(w);
    }

    public void setContentInfo(String info) {
        header.getInfoLabel().setHTML(info);
        header.getInfoLabel().removeStyleName("content-warning");
        header.getInfoLabel().addStyleName("content-info");
    }

    public void setContentWarning(String info) {
        header.getInfoLabel().setHTML(info);
        header.getInfoLabel().removeStyleName("content-info");
        header.getInfoLabel().addStyleName("content-warning");

    }

    public void addCommonButton(String parentMenu, KSButton button) {
        if (parentMenu != null) {
            List<View> views = menuViewMap.get(parentMenu);
            if (views != null) {
                for (int i = 0; i < views.size(); i++) {
                    addButtonForView(views.get(i).getViewEnum(), button);
                }
            }
        }
    }

    public void addCommonButton(String parentMenu, KSButton button, List<Enum<?>> excludedViews) {
        if (parentMenu != null) {
            List<View> views = menuViewMap.get(parentMenu);
            if (views != null) {
                for (int i = 0; i < views.size(); i++) {
                    if (!excludedViews.contains(views.get(i).getViewEnum())) {
                        addButtonForView(views.get(i).getViewEnum(), button);
                    }
                }
            }
        }
    }

    public void showNextViewOnMenu() {
        int i = menuOrder.indexOf(this.getCurrentView());
        if (i != -1 && i + 1 < menuOrder.size()) {
            this.showView(menuOrder.get(i + 1).getViewEnum());
        }
    }

    public void addButtonForView(Enum<?> viewType, KSButton button) {
        List<KSButton> buttons = viewButtonsMap.get(viewType);
        if (buttons == null) {
            buttons = new ArrayList<KSButton>();
            button.addStyleName("ks-button-spacing");
            buttons.add(button);
            viewButtonsMap.put(viewType, buttons);
        } else {
            buttons.add(button);
        }
    }

    @Override
    protected void hideView(View view) {
        contentPanel.clear();
        buttonPanel.clear();
    }

    @Override
    protected void renderView(View view) {
        contentPanel.add(view.asWidget());
        List<KSButton> buttons = viewButtonsMap.get(view.getViewEnum());
        if (buttons != null) {
            for (KSButton button : buttons) {
                buttonPanel.add(button);
            }
        }
        KSMenuItemData item = viewMenuItemMap.get(view.getViewEnum());
        if (item != null) {
            item.setSelected(true, false);
        }
    }

    /* Adds 'name' of the menu above menu items. This menu name has no view */

    public void addMenu(String title) {
        if (title != null && !title.equals("")) {
            KSMenuItemData item = new KSMenuItemData(title);
            topLevelMenuItems.add(item);
            List<View> list = new ArrayList<View>();
            menuViewMap.put(title, list);
            if (refreshMenuOnAdd) {
                menu.refresh();
            }
        }
    }

    /**
     * Adds a view whose view the menu at first.  addMenuItem() should not be called before this method if it is
     * intended for this to be the first view the user sees.  revealMenuItems()
     * must be called to show any items that were added after this call.
     * IMPORTANT: the order in which you call this method, addMenuItem and addSpecialMenuItem affects the order in which they appear
     * on the menu, but also the order in which they are shown when the showNextViewOnMenu is called.  Care must be
     * taken when calling these methods to insure a consistent/logical UI.
     */
    public void addStartMenuItem(String parentMenu, final View view) {
        addMenuItem(parentMenu, view);
        this.setDefaultView(view.getViewEnum());
        refreshMenuOnAdd = false;
        menu.refresh();
    }

    public void revealMenuItems() {
        menu.refresh();
        refreshMenuOnAdd = true;
    }

    /**
     * Adds a view whose view name will appear as a link on the menu, the view will be shown when this item is clicked.
     * IMPORTANT: the order in which you call addMenuItem and addSpecialMenuItem affects the order in which they appear
     * on the menu, but also the order in which they are shown when the showNextViewOnMenu is called.  Care must be
     * taken when calling these methods to insure a consistent/logical UI.
     */
    @Override
    public void addMenuItem(String parentMenu, final View view) {
        super.addView(view);
        KSMenuItemData parentItem = null;
        for (int i = 0; i < topLevelMenuItems.size(); i++) {
            if (topLevelMenuItems.get(i).getLabel().equals(parentMenu)) {
                parentItem = topLevelMenuItems.get(i);
                break;
            }
        }

        KSMenuItemData item = new KSMenuItemData(view.getName());
        viewMenuItemMap.put(view.getViewEnum(), item);
        item.setClickHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                DeferredCommand.addCommand(new Command() {
                    @Override
                    public void execute() {
                        MenuSectionController.this.showView(view.getViewEnum(), showViewCallback);
                    }
                });
            }

        });

        if (parentItem != null) {
            menuOrder.add(view);
            parentItem.addSubItem(item);
            menuViewMap.get(parentMenu).add(view);
        } else {
            menuOrder.add(view);
            topLevelMenuItems.add(item);
            menuViewMap.get("").add(view);
        }

        if (refreshMenuOnAdd) {
            menu.refresh();
        }
    }


    /**
     * This adds a special item that is a top level menu item to the menu, this will appear directly below other
     * menus that are currently added to the menu.  This menu item has special styling to make it more apparent on
     * the menu visually.
     * IMPORTANT: the order in which you call addMenuItem and addSpecialMenuItem affects the order in which they appear
     * on the menu, but also the order in which they are shown when the showNextViewOnMenu is called.  Care must be
     * taken when calling these methods to insure a consistent/logical UI.
     */
    @Override
    public void addSpecialMenuItem(final View view, String description) {
        //TODO add description to the menu item
        super.addView(view);
        menuViewMap.get("").add(view);
        menuOrder.add(view);
        KSMenuItemData item = new KSMenuItemData(view.getName());
        item.addSpecialStyle("ks-menu-layout-special-menu-item-panel");
        viewMenuItemMap.put(view.getViewEnum(), item);
        item.setClickHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                DeferredCommand.addCommand(new Command() {
                    @Override
                    public void execute() {
                        MenuSectionController.this.showView(view.getViewEnum(), showViewCallback);
                    }
                });
            }

        });
        topLevelMenuItems.add(item);
        if (refreshMenuOnAdd) {
            menu.refresh();
        }
    }

    /**
     * This version of updateModel only traverses views that can be accessed through its menu.
     * Views that are part of this controller, but not part of the menu, can update their views through
     * updateModelFromCurrentView and updateModelFromView methods.
     */
    @Override
    public void updateModel() {
        for (View v : menuOrder) {
            v.updateModel();
        }

    }


}
