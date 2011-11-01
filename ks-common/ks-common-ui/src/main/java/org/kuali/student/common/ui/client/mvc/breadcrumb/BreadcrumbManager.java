package org.kuali.student.common.ui.client.mvc.breadcrumb;

import java.util.ArrayList;
import java.util.List;

import org.kuali.student.common.ui.client.mvc.Controller;
import org.kuali.student.common.ui.client.mvc.history.HistoryManager;
import org.kuali.student.common.ui.client.widgets.field.layout.element.SpanPanel;

import com.google.gwt.user.client.ui.ComplexPanel;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.InlineLabel;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.Widget;

/**
 * Manages breadcrumbs for the application
 * 
 * @author Kuali Student Team
 *
 */
public class BreadcrumbManager extends Composite{
	
	public static List<Hyperlink> links = new ArrayList<Hyperlink>();
	
	private static List<String> names = new ArrayList<String>();
	
	private static Controller root;
	private static ComplexPanel panel = new SpanPanel();
	private static boolean panelEmpty = true;
	
	private static Panel parentPanel;
	
	private static class BreadcrumbData{
		private String name;
		private String path;
		public BreadcrumbData(String name, String path) {
			super();
			this.name = name;
			this.path = path;
		}
	}
	
	/**
	 * Binds the controller as the top level controller to call collectBreadcrumbNames on.
	 * 
	 * @param controller
	 */
	public static void bind(Controller controller){
		root = controller;
	}
	
	/**
	 * Updates the breadcrumb panel with the current breadcrumb by walking the controller hierarchy by calling
	 * collectBreadcrumbNames on the root controller bound to the BreadcrumbManager.
	 * 
	 * @param historyStack
	 */
	public static void updateLinks(String historyStack){
		links.clear();
		panel.clear();
		panelEmpty = true;
		names.clear();
		root.collectBreadcrumbNames(names);
		
		String[] arr = HistoryManager.splitHistoryStack(historyStack);
		List<BreadcrumbData> breadcrumbs = new ArrayList<BreadcrumbData>();

		if(arr.length == names.size()){
			String path = "";
			//account for applicationController - skip first item from both
			for(int i = 1; i < names.size(); i++){
				path = path + "/" + arr[i];
				String name = names.get(i);
				//Views with empty names do not appear on the breadcrumbs
				if(name != null && !name.isEmpty()){
					breadcrumbs.add(new BreadcrumbData(name, path));
				}
			}
		}
		//Special link, a controller is adding a breadcrumb outside the scope of the current controller
		//in format name@path
		else if(names.size() > arr.length){
			String path = "";
			int j = 1;
			//account for applicationController - skip first item from both
			for(int i = 1; i < names.size(); i++){
				String name = names.get(i);
				if(name.contains("@")){
					String[] split = name.split("@");
					name = split[0];
					if(name != null && !name.isEmpty()){
						//In the special case the path is the second part of the split
						breadcrumbs.add(new BreadcrumbData(name, split[1]));
					}
				}
				else{
					if(j == arr.length){
						break;
					}
					path = path + "/" + arr[j];
					j++;
					if(name != null && !name.isEmpty()){
						breadcrumbs.add(new BreadcrumbData(name, path));
					}
				}
			}
		}
		
		if(parentPanel != null){
			if(breadcrumbs.size() == 1){
				panel.getParent().setVisible(false);
			}
			else{
				panel.getParent().setVisible(true);

			}
		}
		
		for(int i = 0; i < breadcrumbs.size(); i++){
			if(i < breadcrumbs.size() - 1){
				createLink(breadcrumbs.get(i).name, breadcrumbs.get(i).path);
			}
			else{
				createLabel(breadcrumbs.get(i).name);
				//WindowTitleUtils.setSubtitle(breadcrumbs.get(i).name);
			}
		}
	}
	
	private static void createLabel(String name){
		addToPanel(new InlineLabel(name));
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
			panel.add(new InlineLabel(" \u00BB "));
			panel.add(w);
		}
	}
	
	/** 
	 * @return the breadcrumb panel which contains the breadcrumb links dynamically updated by the 
	 * BreadcrumbManager
	 */
	public static ComplexPanel getBreadcrumbPanel(){
		return panel;
	}
	
	public static void setParentPanel(Panel panel){
		parentPanel = panel;
		parentPanel.setVisible(false);
	}

}
