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

/**
 * 
 */
package org.kuali.student.lum.lu.ui.course.client.widgets.a.util;

import java.util.LinkedList;

/**
 * @author wilj
 *
 */
public class WorkQueue {
	public static abstract class WorkItem {
		private boolean canceled = false;

		public abstract void exec(final Callback<Boolean> onCompleteCallback);

		public boolean isCanceled() {
			return this.canceled;
		}

		protected void setCanceled(final boolean canceled) {
			this.canceled = canceled;
		}
	}

	private boolean running = false;
	private final LinkedList<WorkItem> queue = new LinkedList<WorkItem>();

	private final Callback<Boolean> onCompleteCallback = new Callback<Boolean>() {
		@Override
		public void exec(final Boolean value) {
			processNext();
		}
	};

	public void clear() {
		for (final WorkItem item : queue) {
			item.setCanceled(true);
		}
		queue.clear();
	}

	public boolean isRunning() {
		return this.running;
	}

	private void processNext() {
		WorkItem item = null;
		while (!queue.isEmpty() && (item = queue.removeFirst()) != null) {
			if (item.isCanceled()) {
				item = null;
			} else {
				break;
			}
		}
		if (item == null) {
			running = false;
		} else {
			item.exec(onCompleteCallback);
		}
	}

	public void submit(final WorkItem item) {
		queue.add(item);
		if (!running) {
			running = true;
			processNext();
		}
	}
}
