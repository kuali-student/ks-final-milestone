package org.kuali.student.db.embedded;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

public class TorqueHelper {
	IOUtil io = IOUtil.getInstance();

	public static void main(String[] args) {
		new TorqueHelper().run();
	}

	public void run() {
		try {
			List<String> tables = getTables();
			String dir = "C:/workspace/1.0.0-m3/db/ks-embedded-db/src/main/torque/data";
			StringBuffer sb = new StringBuffer();
			sb.append("<?xml version=\"1.0\" encoding=\"ISO-8859-1\" standalone=\"no\" ?>\n");
			sb.append("<!DOCTYPE dataset SYSTEM \"file://torque-data.dtd\">\n");
			sb.append("<dataset name=\"all\">\n");
			int count = 0;
			for (String table : tables) {
				String filename = dir + "/" + table + ".xml";
				File file = new File(filename);
				if (file.exists()) {
					count++;
					System.out.println(table);
					String contents = io.read(filename);
					String data = StringUtils.substringBetween(contents, "<dataset>", "</dataset>");
					sb.append("\n<!-- Start of data for table '" + table + "' -->");
					if (!StringUtils.isEmpty(data)) {
						sb.append(data);
					}
					sb.append("<!-- End of data for table '" + table + "' -->\n");
				}
				if (count > 5) {
					break;
				}
			}
			sb.append("</dataset>\n");
			io.write(sb.toString(), "C:/workspace/1.0.0-m3/db/ks-embedded-db/target/data/torque/torque-ks-all-data.xml");
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}

	protected List<String> getTables() {
		String filename = "C:/workspace/1.0.0-m3/db/ks-embedded-db/src/main/torque/schema/schema.xml";
		String contents = io.read(filename);
		List<String> tables = split(contents, "<table ", "</table>");
		List<String> newList = new ArrayList<String>();
		for (String table : tables) {
			String s = StringUtils.substringBetween(table, "name=\"", "\">");
			newList.add(s);
		}
		return newList;
	}

	protected List<String> split(String contents, String start, String end) {
		List<String> list = new ArrayList<String>();
		while (contents.indexOf(start) != -1) {
			String s = StringUtils.substringBetween(contents, start, end);
			int pos = contents.indexOf(start) + start.length() + s.length() + end.length();
			contents = contents.substring(pos);
			list.add(s);
		}
		return list;
	}
}
