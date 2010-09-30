/**
 * Copyright 2010 The Kuali Foundation Licensed under the
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

import org.kuali.student.common.ui.client.widgets.KSImage;
import org.kuali.student.common.ui.client.widgets.KSLightBox;

import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.DeferredCommand;
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

	private static KSLightBox popupIndicator;

	private static boolean initialized = false;
    private static int width = 400;
    private static int height = 100;
	/**
	 * Initializes the blocking progress indicator.  This must be called before
	 * blocking task are added.
	 *
	 */
	public static void initialize(){


		mainPanel.add(listPanel);

		//popupIndicator = new KSLightBox(false);
		popupIndicator = new KSLightBox(false);
		//popupIndicator.setShowCloseLink(false);
		
		popupIndicator.setWidget(mainPanel);
		popupIndicator.setSize(400, 50);
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
    public static void setSize(final int w, final int h) {
     width = w;
     height = h;
    }
	private static void updateIndicator() {

		listPanel.clear();
		int i=1;
		for(BlockingTask task: tasks){
			HorizontalPanel taskPanel = new HorizontalPanel();
		    KSImage kSImage = new KSImage("images/common/twiddler3.gif");

			taskPanel.add(kSImage);
			taskPanel.add(new Label(task.getDescription()));
			//taskPanel.addStyleName("KS-Blocking-Task-Item");
			height =  25*(i++);
			//listPanel.setSize(300, )
			listPanel.add(taskPanel);
		}
        showIndicator();		
	}
	private static void showIndicator(){
		if(!initialized){
			initialize();
		}
		popupIndicator.setSize(width, height);
		popupIndicator.show();
	}

	private static void hideIndicator() {
		popupIndicator.hide();
	}

	private static  void setupDefaultStyle(){
		listPanel.addStyleName("KS-Blocking-Task-List");
		mainPanel.addStyleName("KS-Blocking-Task-Main");
		mainPanel.addStyleName("KS-Mouse-Normal");
	}
}
