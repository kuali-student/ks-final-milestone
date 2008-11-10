package edu.umd.ks.poc.lum.web.lutype.client.view;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DecoratorPanel;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;

public class TextBoxConfig extends Composite {

    DecoratorPanel base = new DecoratorPanel();
    FlexTable fTable = new FlexTable();
    TextBox minLen = null;
    TextBox maxLen = null;
    boolean loaded = false;

    public TextBoxConfig() {
        super.initWidget(base);
    }

    @Override
    protected void onLoad() {
        super.onLoad();

        if (!loaded) {
            loaded = true;

            minLen = new TextBox();
            maxLen = new TextBox();

            fTable.setWidget(0, 0, new Label("Min Length"));
            fTable.setWidget(0, 1, minLen);
            fTable.setWidget(1, 0, new Label("Max Length"));
            fTable.setWidget(1, 1, maxLen);

            base.add(fTable);
        }
    }

}
