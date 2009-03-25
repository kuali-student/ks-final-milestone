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
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.VerticalPanel;

public class LiveCSSComposite extends Composite {
    private final VerticalPanel panel = new VerticalPanel();
    private final VerticalPanel inputPanel = new VerticalPanel();
    
    private final Label cssLabel = new Label("CSS");
    private final TextArea css = new TextArea();
    
    private final HorizontalPanel buttonPanel = new HorizontalPanel();
    
    private final Button update = new Button("Apply Styles", new ClickHandler() {
        public void onClick(ClickEvent arg0) {
            updateStyles();
        }
    });
    private final Button reset = new Button("Reset", new ClickHandler() {
        public void onClick(ClickEvent arg0) {
            css.setText("");
            loadCSS();
            
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
        
        css.addStyleName("KSink-LiveCSS-TextBox");
        update.addStyleName("KSink-LiveCSS-Button");
        reset.addStyleName("KSink-LiveCSS-Button");
        exampleLabel.addStyleName("KSink-LiveCSS-Titles");
        cssLabel.addStyleName("KSink-LiveCSS-Titles");
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
                        updateStyles();
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
        var head = $wnd.document.getElementsByTagName("head")[0]; 
        
        // try deleting linked styles?
        var styles = $wnd.document.getElementsByTagName("link");
        for (var i=0; i<styles.length; i++) {
            var href = styles[i].getAttribute("href");
            
            if(href.match("CommonUI.css")){
               styles[i].parentNode.removeChild(styles[i]);
            }
            
        }
        
        //remove old style
        var oldStyle = $wnd.document.getElementById("liveStyle");
        if(oldStyle != null){
            head.removeChild(oldStyle);
        }
        
        // create new style
        var style = $wnd.document.createElement("style");
        style.setAttribute("type", "text/css");
        style.setAttribute("id", "liveStyle"); 
             
        if(style.styleSheet){ 
                style.styleSheet.cssText = cssStr; 
        } else { 
                var cssText = $wnd.document.createTextNode(cssStr); 
                style.appendChild(cssText); 
        } 
        head.appendChild(style); 
     
    }-*/;   
}
