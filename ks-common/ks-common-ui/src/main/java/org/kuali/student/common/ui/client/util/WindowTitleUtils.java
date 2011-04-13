package org.kuali.student.common.ui.client.util;

import com.google.gwt.user.client.Window;

/**
 * Provides a variety of helper methods to manipulate page title based on location
 * 
 * @author Kuali Student Team
 *
 */
public class WindowTitleUtils {
	
	private static String contextTitle = "";
	private static String appTitle = "";
	private static String subTitle = "";
	
	public static void setApplicationTitle(String title){
		appTitle = title;
		contextTitle = "";
		subTitle = "";
		Window.setTitle(generateWindowTitle());
	}
	
	/**
	 * Call this when scope of the application changes to make this be the new
	 * preceding title for the window, followed by last breadcrumb name
	 */
	public static void setContextTitle(String title){
		contextTitle = title;
		subTitle = "";
		Window.setTitle(generateWindowTitle());
	}
	
	/**
	 * Call this to change the subtitle
	 */
	public static void setSubtitle(String title){
		subTitle = title;
		Window.setTitle(generateWindowTitle());
	}
	
	private static String generateWindowTitle(){
		String title = null;
		if(appTitle != null && !appTitle.equals("")){
			title = appTitle;
		}
		
		if(contextTitle != null && !contextTitle.equals("")){
			if(title != null){
				if(!contextTitle.equals(appTitle)){
					title = title + ": " + contextTitle;
				}
			}
			else{
				title = contextTitle;
			}
		}
		
		if(subTitle != null && !subTitle.equals("")){
			if(title != null){
				if(!subTitle.equals(contextTitle)){
					title = title + " - " + subTitle;
				}
			}
			else{
				title = subTitle;
			}
		}
		
		if(title == null){
			title = "KS";
		}
		
		return title;
	}
}
