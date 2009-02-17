package org.kuali.student.core.ui.client.application;

/**
 * Example usage:
 * 
 * MyAppService.getApplicationContext(new AsyncCallback<ApplicationContext>() {
				public void onFailure(Throwable caught) {
					// unable to load application context, handle it somehow
				}
				public void onSuccess(ApplicationContext result) {
					Application.setApplicationContext(result);
					RootPanel.get().add(Application.getApplicationComposite());
				}
			});
 */
public class Application {
	private static ApplicationContext applicationContext;
	private static ApplicationComposite applicationComposite;
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
	
}
