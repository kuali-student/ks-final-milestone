package org.kuali.student.common.ui.client.mvc.breadcrumb;

import java.util.ArrayList;
import java.util.List;

import org.kuali.student.common.ui.client.mvc.Controller;
import org.kuali.student.common.ui.client.mvc.history.HistoryManager;
import org.kuali.student.common.ui.client.widgets.field.layout.element.SpanPanel;

import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.ComplexPanel;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.InlineLabel;
import com.google.gwt.user.client.ui.Widget;

public class BreadcrumbManager extends Composite{
	
	public static List<Hyperlink> links = new ArrayList<Hyperlink>();
	
	private static List<String> names = new ArrayList<String>();
	
	private static Controller root;
	private static ComplexPanel panel = new SpanPanel();
	private static boolean panelEmpty = true;
	
	public static void bind(Controller controller){
		root = controller;
	}
	
	public static void updateLinks(String historyStack){
		links.clear();
		panel.clear();
		panelEmpty = true;
		names.clear();
		root.collectBreadcrumbNames(names);
		
		String[] arr = HistoryManager.splitHistoryStack(historyStack);
		if(arr.length == names.size()){
			//account for applicationController - skip first item from both
			String path = "";
			for(int i = 1; i < names.size(); i++){
				path = path + "/" + arr[i];
				String name = names.get(i);
				if(name != null && !name.isEmpty()){
					createLink(name, path);
				}
			}
		}
	}
	
	private static void createLink(String name, final String viewPath){
		Hyperlink link = new Hyperlink(name, viewPath);
		links.add(link);
		addToPanel(link);
	}
	
	private static void addToPanel(Widget w){
		if(panelEmpty){
			panel.add(w);
			panelEmpty = false;
		}
		else{
			panel.add(new InlineLabel(" > "));
			panel.add(w);
		}
	}
	
	public static ComplexPanel getBreadcrumbPanel(){
		return panel;
	}
}
