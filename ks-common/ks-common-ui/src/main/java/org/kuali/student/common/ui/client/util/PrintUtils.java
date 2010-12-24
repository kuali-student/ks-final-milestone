package org.kuali.student.common.ui.client.util;

import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.NodeList;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.UIObject;

public class PrintUtils {
    private static int num = 0;
    /**
     * Shows the uiObject content in a printable form in a new window
     * @param uiObject
     */
    public static void print(UIObject uiObject){
    	String headTag = "";
    	String styleTags = "";
    	NodeList<com.google.gwt.dom.client.Element> head = Document.get().getElementsByTagName("head");
    	if(head.getItem(0) != null){
    		com.google.gwt.dom.client.Element e = head.getItem(0);
    		NodeList<com.google.gwt.dom.client.Element> styles = e.getElementsByTagName("style");
    		for(int i = 0; i < styles.getLength(); i++){
    			styleTags = styleTags + styles.getItem(i).getString();
    			
    		}
    	}
    	headTag = "<HEAD><TITLE>Print - " + Window.getTitle() + "</TITLE>" + styleTags + "</HEAD>";
    	openPrintWindow(uiObject.getElement().getString(), headTag, num);
    	num++;
    }
    
    private static native void openPrintWindow(String html, String headTag, int num)/*-{
    	var win = $wnd.open("", num, "width=900,height=600");
    	var doc = win.document;
    	doc.open("text/html", "replace");
    	doc.write("<HTML>");
    	doc.write(headTag);
    	doc.write("<BODY style='overflow: auto;'>");
    	doc.write("<a class='ks-button-primary' style='cursor: pointer;' onClick='print();'>Print</a>");
    	doc.write("<DIV class='printPage'>");
    	doc.write(html);
    	doc.write("</DIV></BODY></HTML>");
    	doc.close();
    	win.print();
    }-*/;
}
