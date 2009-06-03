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
	
	
}
