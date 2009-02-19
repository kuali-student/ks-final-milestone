package org.kuali.student.ui.kitchensink.client;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.Response;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public abstract class KitchenSinkExample extends Composite {
    final VerticalPanel panel = new VerticalPanel();
    final Label title = new Label(getTitle());
    final Label description = new Label(getDescription());
    final Label exampleLabel = new Label("Example:");
    final Label resourcesLabel = new Label("Resources:");

    boolean loaded = false;

    final List<KitchenSinkResource> resources = new ArrayList<KitchenSinkResource>();

    static {
        initHighlighter();
    }

    public KitchenSinkExample() {
        super.initWidget(panel);
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
        panel.add(title);
        panel.add(description);
        panel.add(exampleLabel);

        title.setStyleName("ExampleTitle");
        description.setStyleName("ExampleDescription");
        exampleLabel.setStyleName("ExampleBlockHeader");
        resourcesLabel.setStyleName("ExampleBlockHeader");

        panel.add(getExampleWidget());
        if (resources != null && resources.size() > 0) {
            panel.add(resourcesLabel);
            for (KitchenSinkResource r : resources) {
                Label resourceTitle = new Label(r.getTitle());
                Label resourceDescription = new Label(r.getDescription());
                resourceTitle.setStyleName("ExampleResourceTitle");
                resourceDescription.setStyleName("ExampleResourceDescription");
                
                panel.add(resourceTitle);
                panel.add(resourceDescription);
                panel.add(createCodeBlock(r.getType(), r.getContent()));
            }
        }
        doHighlight();
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

}
