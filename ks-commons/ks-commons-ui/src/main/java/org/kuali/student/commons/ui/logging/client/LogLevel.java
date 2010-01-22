package org.kuali.student.commons.ui.logging.client;


public enum LogLevel {
		DEBUG(0),
		INFO(1),
		WARN(2),
		ERROR(3),
		FATAL(4);
		
		private int level;
		private LogLevel(int level) {
			this.level = level;
		}
		public int getLevel() {
			return level;
		}
}
