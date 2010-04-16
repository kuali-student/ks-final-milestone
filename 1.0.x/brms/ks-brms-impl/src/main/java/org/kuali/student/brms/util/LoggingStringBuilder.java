package org.kuali.student.brms.util;

public class LoggingStringBuilder {
	private int counter = 0;
	private StringBuilder stringBuilder = new StringBuilder();
	private String format = "%1$6d: %2$s";
	
	public LoggingStringBuilder() {
	}
	
	public LoggingStringBuilder(String format) {
		this.format = format;
	}

	public StringBuilder append(String s) {
		String f = String.format(format, ++counter, s);
		this.stringBuilder.append(f);
		this.stringBuilder.append("\n");
		return stringBuilder;
	}
	
	public void trimToSize() {
		this.stringBuilder.trimToSize();
	}

	public StringBuilder getStringBuilder() {
		return this.stringBuilder;
	}
	
	public String toString() {
		return this.stringBuilder.toString();
	}
}
