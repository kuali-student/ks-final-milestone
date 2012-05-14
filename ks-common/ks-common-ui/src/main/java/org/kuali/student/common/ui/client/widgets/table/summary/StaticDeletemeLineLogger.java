package org.kuali.student.common.ui.client.widgets.table.summary;

import com.google.gwt.user.client.Window;

public class StaticDeletemeLineLogger {
	private static String lastLog;
	public static void log(String line){
		lastLog = line;
	}
	public static void AlertLastLine(){
		Window.alert("Last Line was:"+lastLog);
	}
}
