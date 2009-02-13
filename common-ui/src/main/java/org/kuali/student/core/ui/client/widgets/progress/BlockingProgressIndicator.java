package org.kuali.student.core.ui.client.widgets.progress;

import java.util.LinkedList;

import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * @author Bsmith
 *
 */
public class BlockingProgressIndicator{
	private static LinkedList<BlockingTask> tasks = new LinkedList<BlockingTask>();
	
	private static final VerticalPanel mainPanel = new VerticalPanel();
	private static final VerticalPanel listPanel = new VerticalPanel();
	private static final VerticalPanel headerPanel = new VerticalPanel();
	
	private static final ModalPopupPanel popupIndicator = new ModalPopupPanel();
	
	private static boolean initialized = false;
	
	public static void initialize(){

		headerPanel.setHorizontalAlignment(HorizontalPanel.ALIGN_CENTER);
		Label title = new Label("Processing...");
		title.addStyleName("KS-Label");
		headerPanel.addStyleName("KS-Blocking-Task-Header");
		headerPanel.add(title);
		
//		headerPanel.add(new Button("Add", new ClickHandler(){
//
//			public void onClick(ClickEvent event) {
//				addTask(new BlockingTask("New Task"));
//				
//			}}));
//		
//		headerPanel.add(new Button("Remove", new ClickHandler(){
//
//			public void onClick(ClickEvent event) {
//				removeTask(tasks.getFirst());
//				
//		}}));
		listPanel.addStyleName("KS-Blocking-Task-List");
		
		mainPanel.addStyleName("KS-Mouse-Normal");
		mainPanel.addStyleName("KS-Blocking-Task-Main");
		mainPanel.add(headerPanel);
		mainPanel.add(listPanel);
		
		popupIndicator.add(mainPanel);
		initialized = true;
	}
	
	public static void addTask(BlockingTask task) {
		tasks.add(task);
		updateIndicator();
	}
	
	public static void removeTask(BlockingTask task) {
		tasks.remove(task);
		if (tasks.isEmpty()) {
			hideIndicator();
		} else {
			updateIndicator();
		}
		
	}

	private static void updateIndicator() {
		
		showIndicator();
		listPanel.clear();
		for(BlockingTask task: tasks){
			HorizontalPanel taskPanel = new HorizontalPanel();
			taskPanel.add(new Label(task.getDescription()));
			taskPanel.add(new Image("images/twiddler3.gif"));
			taskPanel.addStyleName("KS-Blocking-Task-Item");
			listPanel.add(taskPanel);
		}
		
	}
	
	private static void showIndicator(){
		if(!initialized){
			initialize();
		}
		popupIndicator.show();
	}
	
	private static void hideIndicator() {
		popupIndicator.hide();
	}

}
