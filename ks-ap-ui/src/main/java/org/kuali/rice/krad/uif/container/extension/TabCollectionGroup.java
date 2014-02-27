package org.kuali.rice.krad.uif.container.extension;

import org.kuali.rice.krad.uif.container.CollectionGroupBase;
import org.kuali.rice.krad.uif.widget.Tabs;

/**
 * CollectionGroup that holds a Tab widget to enable rendering of each item in the collection as
 * a tab of its own.
 * <p/>
 * <p>
 * All tab widget configuration should be done using the templateOptionsJSString on the tabWidget
 * instead of the CollectionGroup.
 * </p>
 *
 * @author Kuali Rice Team (rice.collab@kuali.org)
 *         Date: 2/13/13
 */
public class TabCollectionGroup extends CollectionGroupBase {
    private static final long serialVersionUID = 3L;

    private Tabs tabsWidget;

    public TabCollectionGroup() {
        super();
    }

    public Tabs getTabsWidget() {
        return tabsWidget;
    }

    public void setTabsWidget(Tabs tabsWidget) {
        this.tabsWidget = tabsWidget;
    }
}
