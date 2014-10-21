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

public class BlockingTask {
	private static int maxId = 0;
	private final int id = maxId++;
	private String description = null;
	
	public BlockingTask(String description) {
		this.description = description;
	}
	
	public int getId() {
		return id;
	}
	public String getDescription() {
		return description;
	}

	public boolean equals(Object obj) {
		return obj != null && obj instanceof BlockingTask && ((BlockingTask) obj).getId() == this.id;
	}
	
	public int hashCode(){
		int hash = 1;
		Integer idObject = Integer.valueOf(id);
		hash = hash * 31 + idObject.hashCode();
		return hash;
	}
	
	
}
