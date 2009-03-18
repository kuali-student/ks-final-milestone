package org.kuali.student.ui.kitchensink.client;

import static org.kuali.student.ui.kitchensink.client.KitchenSinkStyleConstants.STYLE_DESCRIPTION;
import static org.kuali.student.ui.kitchensink.client.KitchenSinkStyleConstants.STYLE_EXAMPLE_PANEL;
import static org.kuali.student.ui.kitchensink.client.KitchenSinkStyleConstants.STYLE_RESOURCE_DESCRIPTION;
import static org.kuali.student.ui.kitchensink.client.KitchenSinkStyleConstants.STYLE_RESOURCE_TITLE;
import static org.kuali.student.ui.kitchensink.client.KitchenSinkStyleConstants.STYLE_TAB_PANEL;
import static org.kuali.student.ui.kitchensink.client.KitchenSinkStyleConstants.STYLE_TITLE;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.kuali.student.common.ui.client.widgets.KSTabPanel;

import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.Response;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Frame;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public abstract class KitchenSinkExample extends Composite {
    private final VerticalPanel main = new VerticalPanel();
    private final KSTabPanel tabPanel = new KSTabPanel();

    private final Label title = new Label(getTitle());
    private final Label description = new Label(getDescription());
    private final Label exampleLabel = new Label("Example");
    private final SimplePanel liveCSSTab = new SimplePanel();

    private Frame liveCSSFrame = null;

    boolean loaded = false;

    final List<KitchenSinkResource> resources = new ArrayList<KitchenSinkResource>();

    public List<KitchenSinkResource> getResources() {
        return resources;
    }

    static {
        initHighlighter();
    }

    public KitchenSinkExample() {
        super.initWidget(main);
    }

    protected void onLoad() {

        if (!loaded) {
            loaded = true;
            if (resourceLoadingComplete()) {
                populate();
            } else {
                loadResources();
            }
        }
//        tabPanel.selectTab(0);
    }

    private boolean resourceLoadingComplete() {
        boolean result = true;
        for (KitchenSinkResource r : resources) {
            if (r.isLoaded() == false) {
                result = false;
                break;
            }
        }
        return result;
    }

    private void loadResources() {
        for (final KitchenSinkResource r : resources) {
            RequestBuilder builder = new RequestBuilder(RequestBuilder.GET, r.getPath());
            builder.setCallback(new RequestCallback() {
                public void onError(Request request, Throwable exception) {
                    r.setContent(exception.toString());
                    checkComplete();
                }

                public void onResponseReceived(Request request, Response response) {
                    r.setContent(response.getText());
                    checkComplete();
                }

                private void checkComplete() {
                    if (resourceLoadingComplete()) {
                        populate();
                    }
                }

            });
            try {
                builder.send();
            } catch (RequestException e) {
                r.setContent(e.toString());
                if (resourceLoadingComplete()) {
                    populate();
                }
            }
        }
    }

    private void populate() {


        main.add(title);
        main.add(description);      
        main.add(tabPanel);

        title.setStyleName(STYLE_TITLE);
        description.setStyleName(STYLE_DESCRIPTION);
        tabPanel.setStyleName(STYLE_TAB_PANEL);
        main.setStyleName(STYLE_EXAMPLE_PANEL);

        tabPanel.addTab(getExampleWidget(), exampleLabel);

        Map<String, List<KitchenSinkResource>> resourceMap = new TreeMap<String, List<KitchenSinkResource>>();

        // group the resources by type
        if (resources != null && resources.size() > 0) {
            for (KitchenSinkResource r : resources) {
                String type = translateResourceType(r.getType());
                List<KitchenSinkResource> list = resourceMap.get(type);
                if (list == null) {
                    list = new ArrayList<KitchenSinkResource>();
                    resourceMap.put(type, list);
                }
                list.add(r);
            }
        }

        // add each group to its own tab
        for (String type : resourceMap.keySet()) {
            VerticalPanel resourcePanel = new VerticalPanel();
            for (KitchenSinkResource r : resourceMap.get(type)) {
                Label resourceTitle = new Label(r.getTitle());
                Label resourceDescription = new Label(r.getDescription());
                resourceTitle.setStyleName(STYLE_RESOURCE_TITLE);
                resourceDescription.setStyleName(STYLE_RESOURCE_DESCRIPTION);

                resourcePanel.add(resourceTitle);
                resourcePanel.add(resourceDescription);
                resourcePanel.add(createCodeBlock(r.getType(), r.getContent()));
            }
            tabPanel.addTab(resourcePanel, type);
        }


        doHighlight();

        tabPanel.addTab(liveCSSTab, "Edit CSS Live");
        tabPanel.addSelectionHandler(new SelectionHandler<Integer>() {
            public void onSelection(SelectionEvent<Integer> event) {
                if (event.getSelectedItem() == tabPanel.getWidgetIndex(liveCSSTab) && liveCSSFrame == null) {
                    liveCSSFrame = new Frame("LiveCSS.html?exampleClass=" + KitchenSinkExample.this.getClass().getName());
                    liveCSSFrame.getElement().setAttribute("FRAMEBORDER", "0");
                    liveCSSTab.setWidget(liveCSSFrame);
                    liveCSSFrame.addStyleName("KSink-LiveCSS-Frame");
                }
            }
        });

        tabPanel.selectTab(0);
    }

    public static final native void initHighlighter() /*-{
           $wnd.dp.SyntaxHighlighter.ClipboardSwf = 'flash/clipboard.swf';
       }-*/;

    public final native void doHighlight() /*-{
           $wnd.dp.SyntaxHighlighter.HighlightAll('code');
       }-*/;

    public void addResource(String type, String title, String path, String description) {
        addResource(type, title, path, description, null);
    }

    public void addResource(String type, String title, String path, String description, String content) {
        KitchenSinkResource r = new KitchenSinkResource();
        r.setType(type);
        r.setTitle(title);
        r.setPath(path);
        r.setDescription(description);
        r.setContent(content);
        resources.add(r);
    }

    public abstract String getTitle();

    public abstract String getDescription();

    public abstract Widget getExampleWidget();

    private HTML createCodeBlock(String language, String code) {
        HTML result = null;
        if (code != null) {
            result = new HTML("<textarea name='code' rows='" + getRows(code) + "' cols='120' class='" + language + "'>" + code + "</textarea>");
        }
        return result;
    }

    private int getRows(String code) {
        int result = 2;
        int pos = 0;
        while (pos != -1) {
            pos = code.indexOf('\n', pos + 1);
            result++;
        }
        return result;
    }

    private String translateResourceType(String resourceType) {
        resourceType = resourceType.toLowerCase();
        if (resourceType.equals("css")) {
            return "Styles";
        }
        else if (resourceType.equals("java")) {
            return "Code";
        }
        else { 
            return "Other";
        }
    }

}
