package edu.umd.ks.poc.lum.web.lum.client.view;


import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * @author Garey
 *
 */
public class HeaderPanel extends Composite {

    final VerticalPanel root = new VerticalPanel();
    final HorizontalPanel topRow = new HorizontalPanel();
    final HorizontalPanel topLeft = new HorizontalPanel();
    final HorizontalPanel bottomRow = new HorizontalPanel();
    final HorizontalPanel linkPanel = new HorizontalPanel();

    final Image logo = new Image("images/logo.gif");



    boolean loaded = false;

    public HeaderPanel() {
        super.initWidget(root);
    }

    protected void onLoad() {
        if (!loaded) {
            loaded = true;

            doLayout();
            initStyles();

        }
    }
    private void initStyles() {
        root.setWidth("100%");

        root.addStyleName("KS-Header");
        topRow.addStyleName("KS-Header-TopRow");
        bottomRow.addStyleName("KS-Header-BarText");

        topLeft.addStyleName("KS-Header-TopLeft");

        

        linkPanel.addStyleName("KS-Header-LinkPanel");

    }

    private void doLayout() {
        root.add(topRow);
        root.add(bottomRow);

        bottomRow.setHorizontalAlignment(VerticalPanel.ALIGN_RIGHT);
        bottomRow.setWidth("100%");
        bottomRow.add(new CurrentUser());
        bottomRow.add(new SwitchUserPanel());

        topRow.add(topLeft);

        topLeft.add(logo);

    }
}

