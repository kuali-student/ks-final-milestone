package org.apache.torque.mojo;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

public class Tail {
	public static void main(String[] args) {
		try {
			int display = 3 * 1024;
			String filename = "C:/temp/old.xml";
			File file = new File(filename);
			long length = file.length();
			InputStream in = new FileInputStream(file);
			in.skip(length - display);
			OutputStream out = new FileOutputStream("C:/temp/tail.txt");
			byte[] buffer = new byte[1024];
			int readLength = 0;
			while ((readLength = in.read(buffer, 0, buffer.length)) != -1) {
				out.write(buffer, 0, readLength);
			}
			in.close();
			out.close();
		} catch (Throwable t) {
			t.printStackTrace();
		}
	}

}
