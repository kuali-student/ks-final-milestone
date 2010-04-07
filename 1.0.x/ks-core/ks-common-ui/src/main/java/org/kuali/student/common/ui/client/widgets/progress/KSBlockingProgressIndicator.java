/*
 * Copyright 2009 The Kuali Foundation Licensed under the
 * Educational Community License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may
 * obtain a copy of the License at
 * 
 * http://www.osedu.org/licenses/ECL-2.0
 * 
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */
package org.kuali.student.common.ui.client.widgets.progress;

import java.util.LinkedList;

import org.kuali.student.common.ui.client.application.Application;
import org.kuali.student.common.ui.client.widgets.KSImage;
import org.kuali.student.common.ui.client.widgets.KSMessages;
import org.kuali.student.common.ui.client.widgets.KSModalDialogPanel;
import org.kuali.student.common.ui.client.widgets.KSStyles;

import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * This class handles a static list of BlockingTasks, when a blocking task is added to the list, the blocking
 * progress indicator is shown to the user and the user can perform no action until all blocking tasks are finished/removed.
 * 
 * @author Bsmith
 *
 */
public class KSBlockingProgressIndicator{
    
    private static LinkedList<BlockingTask> tasks = new LinkedList<BlockingTask>();
	
	private static final VerticalPanel mainPanel = new VerticalPanel();
	private static final VerticalPanel listPanel = new VerticalPanel();
	
	private static KSModalDialogPanel popupIndicator = new KSModalDialogPanel();
	
	private static boolean initialized = false;
	
	/**
	 * Initializes the blocking progress indicator.  This must be called before
	 * blocking task are added.
	 * 
	 */
	public static void initialize(){
		
		
		mainPanel.add(listPanel);
		
		popupIndicator.setHeader(Application.getApplicationContext().getMessage(KSMessages.PROCESSING));
		
		popupIndicator.setWidget(mainPanel);
		setupDefaultStyle();
		initialized = true;
	}
	
	/**
	 * Adds a blocking task to the queue.  When all tasks are removed, the indicator
	 * is no longer shown.
	 * 
	 * @param task the task description to be added
	 */
	public static void addTask(BlockingTask task) {
		tasks.add(task);
		updateIndicator();    
	}
	
	/**
	 * Removes the blocking task from the queue
	 * 
	 * @param task the task to be removed from the blocking task queue
	 */
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
		KSImage kSImage = new KSImage("images/twiddler3.gif");
		for(BlockingTask task: tasks){
			HorizontalPanel taskPanel = new HorizontalPanel();
			taskPanel.add(new Label(task.getDescription()));
			taskPanel.add(kSImage);
			taskPanel.addStyleName(KSStyles.KS_BLOCKING_TASK_ITEM);
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
	
	private static  void setupDefaultStyle(){
		popupIndicator.addStyleName(KSStyles.KS_BLOCKING_TASK_WINDOW);
		listPanel.addStyleName(KSStyles.KS_BLOCKING_TASK_LIST);
		mainPanel.addStyleName(KSStyles.KS_BLOCKING_TASK_MAIN);
		mainPanel.addStyleName(KSStyles.KS_MOUSE_NORMAL);
	}

}
