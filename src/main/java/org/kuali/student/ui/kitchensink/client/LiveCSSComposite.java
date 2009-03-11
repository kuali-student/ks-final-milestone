package org.kuali.student.ui.kitchensink.client;

import org.kuali.student.common.ui.client.widgets.KSButton;
import org.kuali.student.common.ui.client.widgets.KSLabel;
import org.kuali.student.common.ui.client.widgets.KSTextArea;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.Response;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;

public class LiveCSSComposite extends Composite {
    private final VerticalPanel panel = new VerticalPanel();
    private final VerticalPanel inputPanel = new VerticalPanel();
    
    private final KSLabel cssLabel = new KSLabel("CSS");
    private final KSTextArea css = new KSTextArea();
    
    private final HorizontalPanel buttonPanel = new HorizontalPanel();
    
    private final KSButton update = new KSButton("Refresh Styles", new ClickHandler() {
        public void onClick(ClickEvent arg0) {
            updateStyles();
        }
    });
    private final KSButton reset = new KSButton("Reset", new ClickHandler() {
        public void onClick(ClickEvent arg0) {
            css.setText("");
            loadCSS();
            updateStyles();
        }
    });
    
    private final VerticalPanel examplePanel = new VerticalPanel();
    private final KSLabel exampleLabel = new KSLabel("Example:");
    private final SimplePanel content = new SimplePanel();
    
    private KitchenSinkExample example = null;
    
    public LiveCSSComposite() {
        super.initWidget(panel);
        panel.add(inputPanel);
        inputPanel.add(cssLabel);
        inputPanel.add(css);
        inputPanel.add(buttonPanel);
        buttonPanel.add(update);
        buttonPanel.add(reset);
        panel.add(examplePanel);
        examplePanel.add(exampleLabel);
        examplePanel.add(content);
        
        inputPanel.addStyleName("KSink-LiveCSS-InputPanel");
        examplePanel.addStyleName("KSink-LiveCSS-ContentPanel");
        
    }
    
    public void setExample(KitchenSinkExample example) {
        this.example = example;
        this.content.setWidget(example.getExampleWidget());
        loadCSS();
        updateStyles();
    }
    private void loadCSS() {
        for (KitchenSinkResource ksr : example.getResources()) {
            if (ksr.getType().trim().equalsIgnoreCase("css")) {
                final String path = ksr.getPath();
                RequestBuilder request = new RequestBuilder(RequestBuilder.GET, path);
                request.setCallback(new RequestCallback() {
                    public void onError(Request arg0, Throwable arg1) {
                        // do nothing
                    }

                    public void onResponseReceived(Request request, Response response) {
                        css.setText(css.getText() + "\n" + response.getText());
                    }
                });
                try {
                    request.send();
                } catch (RequestException re) {
                    // do nothing
                }
            }
        }
        
    }
    public KitchenSinkExample getExample() {
        return example;
    }
    
    private void updateStyles() {
        updateDOMStyles(css.getText());
    }
    private final native void updateDOMStyles(String cssStr) /*-{ 
        var style = $wnd.document.getElementById("liveStyle");
        if (style == null) { 
            style = $wnd.document.createElement("style");
            style.setAttribute("type", "text/css");
            style.setAttribute("id", "liveStyle"); 
        } 
        if(style.styleSheet){ 
                style.styleSheet.cssText = cssStr; 
        } else { 
                var cssText = $wnd.document.createTextNode(cssStr); 
                style.appendChild(cssText); 
        } 
        var head = $wnd.document.getElementsByTagName("head")[0]; 
        if(head){ 
                head.appendChild(style); 
        } 
    }-*/; 
    
//    private void updateStyles() {
//        Document doc = Document.get();
//        
//        BodyElement body = doc.getBody(); 
//        HeadElement head = 
//            HeadElement.as(Element.as( 
//            body.getPreviousSibling())); 
//        
//        Element e = doc.getElementById("liveStyle");
//        if (e != null) {
//            head.removeChild(e);
//        }
//        
//        
//        StyleElement style = doc.createStyleElement();
//        style.setAttribute("type", "text/css");
//        style.setId("liveStyle");
//        head.appendChild(style);
//        style.appendChild(doc.createTextNode(css.getText()));
//        
//    }    
    
    
}
