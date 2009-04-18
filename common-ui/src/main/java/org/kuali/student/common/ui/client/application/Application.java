package org.kuali.student.common.ui.client.application;


/**
 * TODO read the window location parameters to determine if a particular view should be shown first
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
