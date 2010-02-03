package org.kuali.student.db.embedded;

public class TorqueHelper {
	public static void main(String[] args) {
		new TorqueHelper().run();
	}

	public void run() {
		try {
			String filename = "C:/workspace/1.0.0-m3/db/ks-embedded-db/src/main/torque/schema/schema.xml";
			String contents = IOUtil.getInstance().read(filename);
			System.out.println(contents);
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}

}
