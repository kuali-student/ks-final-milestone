package org.kuali.student.common.ui.client.application;


/**
 * Example usage:
 * 
 */
public class Application {
	private static ApplicationContext applicationContext;
	private static ApplicationComposite applicationComposite;
	private static EventDispatcher eventDispatcher = new EventDispatcher();
	
	public static ApplicationContext getApplicationContext() {
		return applicationContext;
	}
	public static void setApplicationContext(ApplicationContext applicationContext) {
		Application.applicationContext = applicationContext;
	}
	public static ApplicationComposite getApplicationComposite() {
		if (applicationComposite == null) {
			applicationComposite = new ApplicationComposite();
		}
		return applicationComposite;
	}
    public static EventDispatcher getEventDispatcher() {
        return eventDispatcher;
    }
	
}
