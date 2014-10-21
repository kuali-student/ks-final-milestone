package org.kuali.student.common.ui.client.configurable.mvc.layouts;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.kuali.student.common.ui.client.configurable.mvc.LayoutController;
import org.kuali.student.common.ui.client.mvc.Callback;
import org.kuali.student.common.ui.client.mvc.View;
import org.kuali.student.common.ui.client.util.ExportElement;
import org.kuali.student.common.ui.client.widgets.KSButton;
import org.kuali.student.common.ui.client.widgets.headers.KSDocumentHeader;
import org.kuali.student.common.ui.client.widgets.menus.KSMenuItemData;
import org.kuali.student.common.ui.client.widgets.menus.impl.KSBlockMenuImpl;
import org.kuali.student.common.ui.client.widgets.panels.collapsable.VerticalCollapsableDrawer;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.DeferredCommand;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * A layout controller which generates a menu for views that added to it through addMenuItem calls.
 * The user can click on items in the menu to show the view.
 * 
 * @author Kuali Student Team
 *
 */
public class MenuSectionController extends LayoutController implements ContentNavLayoutController {

    protected KSBlockMenuImpl menu = new KSBlockMenuImpl();
    protected List<KSMenuItemData> topLevelMenuItems = new ArrayList<KSMenuItemData>();
    protected Map<String, List<View>> menuViewMap = new HashMap<String, List<View>>();
    private Map<Enum<?>, List<KSButton>> viewButtonsMap = new HashMap<Enum<?>, List<KSButton>>();
    private Map<Enum<?>, List<KSButton>> topViewButtonsMap = new HashMap<Enum<?>, List<KSButton>>();
    protected Map<Enum<?>, KSMenuItemData> viewMenuItemMap = new HashMap<Enum<?>, KSMenuItemData>();
    private List<View> menuOrder = new ArrayList<View>();
    private HorizontalPanel layout = new HorizontalPanel();
    private KSDocumentHeader header = new KSDocumentHeader();
    protected FlowPanel rightPanel = new FlowPanel();
    private FlowPanel contentPanel = new FlowPanel();
    private FlowPanel infoPanel = new FlowPanel();
    private FlowPanel bottomButtonPanel = new FlowPanel();
    private FlowPanel topButtonPanel = new FlowPanel();
    protected VerticalPanel leftPanel = new VerticalPanel();
    private SimplePanel sideBar = new SimplePanel();
    private boolean refreshMenuOnAdd = true;
    private VerticalCollapsableDrawer collapsablePanel = GWT.create(VerticalCollapsableDrawer.class);

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

    public MenuSectionController() {
        super();
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
        rightPanel.add(infoPanel);
        rightPanel.add(topButtonPanel);
        rightPanel.add(contentPanel);
        rightPanel.add(bottomButtonPanel);
        layout.add(collapsablePanel);
        layout.add(rightPanel);
        header.setVisible(false);
        this.showPrint(true);
        this.initWidget(layout);
    }

    public void removeMenuNavigation() {
        collapsablePanel.removeFromParent();
    }

    public void setContentTitle(String title) {
        header.setTitle(title);
        header.setVisible(true);
    }

    public void addContentWidget(Widget w) {
        header.addWidget(w);
        header.setVisible(true);
    }
    
    public void addInfoWidget(Widget w) {
        infoPanel.add(w);
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

    public void showPrint(boolean show) {
        header.showPrint(show);
    }
    
    public void showExport(boolean show) {
        header.showExport(show);
    }

    /**
     * @see org.kuali.student.common.ui.client.configurable.mvc.layouts.ContentNavLayoutController#addCommonButton(java.lang.String, org.kuali.student.common.ui.client.widgets.KSButton)
     */
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

    /**
     * @see org.kuali.student.common.ui.client.configurable.mvc.layouts.ContentNavLayoutController#addCommonButton(java.lang.String, org.kuali.student.common.ui.client.widgets.KSButton, java.util.List)
     */
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

    public void addTopButtonForView(Enum<?> viewType, KSButton button) {
        List<KSButton> buttons = topViewButtonsMap.get(viewType);
        if (buttons == null) {
            buttons = new ArrayList<KSButton>();
            button.addStyleName("ks-button-spacing");
            buttons.add(button);
            topViewButtonsMap.put(viewType, buttons);
        } else {
            buttons.add(button);
        }
    }

    @Override
    protected void hideView(View view) {
        contentPanel.clear();
        bottomButtonPanel.clear();
    }

    @Override
    protected void renderView(View view) {
        contentPanel.add(view.asWidget());

        //Render bottom buttons
        List<KSButton> buttons = viewButtonsMap.get(view.getViewEnum());
        if (buttons != null) {
            for (KSButton button : buttons) {
                bottomButtonPanel.add(button);
            }
        }
        
        //Render top buttons
        buttons = topViewButtonsMap.get(view.getViewEnum());
        if (buttons != null) {
            for (KSButton button : buttons) {
                topButtonPanel.add(button);
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

    @Override
    public List<ExportElement> getExportElementsFromView() {
        return super.getExportElementsFromView();
        
    }
    
    public void addStyleName(String style){
    	layout.addStyleName(style);
    }
}
