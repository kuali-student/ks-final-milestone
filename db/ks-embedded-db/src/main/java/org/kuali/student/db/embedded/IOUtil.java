package org.kuali.student.db.embedded;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.net.URL;
import java.net.URLDecoder;

/**
 * 
 */
public class IOUtil {

	protected IOUtil() {
		super();
	}

	protected static IOUtil instance = new IOUtil();

	public static IOUtil getInstance() {
		return instance;
	}

	/**
	 * Check to see if this file exists. Throw an exception if it does not.
	 */
	protected File checkExistence(String filename) {
		File f = new File(filename);
		if (!f.exists()) {
			throw new RuntimeException(new FileNotFoundException(filename
					+ " does not exist"));
		}
		return f;
	}

	/**
	 * Obtain the contents of the file as a byte[]
	 */
	public byte[] getBytes(String filename) {
		InputStream in = null;
		ByteArrayOutputStream out = null;
		try {
			// Get the io streams
			in = new BufferedInputStream(new FileInputStream(filename));
			out = new ByteArrayOutputStream();

			// copy input to output
			copy(in, out);

			// return the contents of the file as a byte array
			return out.toByteArray();
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			nullSafeClose(in, out);
		}
	}

	/**
	 * Copy length bytes from the InputStream to the OutputStream
	 */
	public void copy(InputStream in, OutputStream out, long length)
			throws IOException {
		// Setup a 4K buffer
		int bufferSize = 4096;

		// Max # of bytes per read
		int maxBytesPerRead = bufferSize;

		// Allocate a buffer
		byte[] buffer = new byte[bufferSize];

		long bytesToRead = length;

		// Try to read the max # of bytes
		int bytesForThisRead = maxBytesPerRead;

		// Check to make sure we are allowed to read that many
		if (bytesForThisRead > bytesToRead) {
			// Reduce the # of bytes we will read
			bytesForThisRead = (int) bytesToRead;
		}

		// Make the first read
		int bytesRead = in.read(buffer, 0, bytesForThisRead);

		// Iterate until we have read everything we need to
		while (bytesToRead > 0 && bytesRead > 0) {

			// Deduct what we've read from our total
			bytesToRead -= bytesRead;

			// Append what we read to the string
			out.write(buffer, 0, bytesRead);

			// Calculate how much we will read next time
			bytesForThisRead = maxBytesPerRead;

			// Make sure we don't read too much
			if (bytesForThisRead > bytesToRead) {
				// Adjust for the final read
				bytesForThisRead = (int) bytesToRead;
			}
			// Read in the characters
			bytesRead = in.read(buffer, 0, bytesForThisRead);
		}

	}

	/**
	 * Copy contents from the input stream to the output stream
	 */
	public void copy(InputStream in, OutputStream out) throws IOException {
		// Allocate a buffer
		byte[] buffer = new byte[4096];

		// Copy the file
		int bytesRead = in.read(buffer, 0, buffer.length);
		while (bytesRead != -1) {
			out.write(buffer, 0, bytesRead);
			bytesRead = in.read(buffer, 0, buffer.length);
		}
	}

	/**
	 * Copy filename1 to filename2
	 */
	public boolean copy(String filename1, String filename2) {
		InputStream in = null;
		OutputStream out = null;
		try {
			// Get the io streams
			in = new BufferedInputStream(new FileInputStream(filename1));
			out = new BufferedOutputStream(new FileOutputStream(filename2));

			// copy input to output
			copy(in, out);
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			nullSafeClose(in, out);
		}
		return true;
	}

	/**
	 * Read in the full contents of a file
	 */
	public String read(String filename) {
		return readTail(filename, Integer.MAX_VALUE);
	}

	/**
	 * Convert length bytes from the file starting at offset to a String
	 */
	public String read(String filename, int offset, int length) {
		// Nothing to do
		if (length <= 0) {
			return "";
		}

		// Get a reference to the file
		File file = new File(filename);

		if (!file.exists()) {
			throw new RuntimeException(new FileNotFoundException(filename));
		}

		// Total file length
		int fileLength = (int) file.length();

		// They tried to tell us to skip past the end of the file
		if (offset >= fileLength) {
			return "";
		}

		// Can't start before the beginning
		if (offset < 0) {
			offset = 0;
		}

		// Make sure length does not extend us past the end of the file
		int maxLength = fileLength - offset;
		if (length > maxLength) {
			length = maxLength;
		}

		Reader in = null;
		try {
			// Open the file
			in = new BufferedReader(new FileReader(filename));

			// Skip the desired number of bytes
			if (offset > 0) {
				in.skip(offset);
			}

			// Allocate a buffer
			char[] characters = new char[4096];

			// Total # of characters to read in
			int charsToRead = length;

			// Maximum # of characters per read
			int maxCharsPerRead = characters.length;

			// Try to read the max # of chars
			int charsForThisRead = maxCharsPerRead;

			// Check to make sure we are allowed to read that many
			if (charsForThisRead > charsToRead) {
				// Reduce the # of chars we will read
				charsForThisRead = charsToRead;
			}

			// Create a spot to store what we are reading in
			StringBuffer sb = new StringBuffer();

			// Make the first read
			int charsRead = in.read(characters, 0, charsForThisRead);

			// Iterate until we have read everything we need to
			while (charsToRead > 0) {

				// Deduct what we've read from our total
				charsToRead -= charsRead;

				// Append what we read to the string
				sb.append(characters, 0, charsRead);

				// Calculate how much we will read next time
				charsForThisRead = maxCharsPerRead;

				// Make sure we don't read too much
				if (charsForThisRead > charsToRead) {
					// Adjust for the final read
					charsForThisRead = charsToRead;
				}
				// Read in the characters
				charsRead = in.read(characters, 0, charsForThisRead);
			}

			// Convert the buffer to a String
			return sb.toString();
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			nullSafeClose(in);
		}
	}

	/**
	 * Read length bytes off the tail end of a file
	 */
	public String readTail(String filename, int length) {
		// Get a reference to the file
		File file = new File(filename);

		// Total file length
		int fileLength = (int) file.length();

		// Calculate offset
		int offset = 0;
		if (length < fileLength) {
			/**
			 * To get length # of characters off the end of the file, we need to
			 * skip this many
			 */
			offset = fileLength - length;
		}

		// Calculate length
		int maxLength = fileLength - offset;
		if (length > maxLength) {
			length = maxLength;
		}
		return read(filename, offset, length);
	}

	/**
	 * Convert a resource name to a file name
	 */
	public String getFilename(String resource) {
		URL url = Thread.currentThread().getContextClassLoader().getResource(
				resource);
		if (url == null) {
			return null;
		}
		try {
			/**
			 * The string returned by getFile() contains %20 instead of spaces
			 * if there are spaces in the file name:<br>
			 * For example: <code>
			 * C:/Documents%20and%20Settings/jcaddel/workspace/phx/common/common-util/target/test-classes/4096.txt
			 * </code>
			 * 
			 * Passing that string into file.exists() returns false
			 */
			return URLDecoder.decode(url.getFile(), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}
	}

	public String readResource(String resource) {
		URL url = Thread.currentThread().getContextClassLoader().getResource(
				resource);
		InputStream in = null;
		try {
			in = url.openStream();
			byte[] bytes = new byte[1024];
			int length = 0;
			StringBuffer sb = new StringBuffer();
			for (;;) {
				length = in.read(bytes);
				if (length == -1) {
					break;
				} else {
					sb.append(new String(bytes, 0, length));
				}
			}
			return sb.toString();
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			nullSafeClose(in);
		}

	}

	/**
	 * Write contents from an input stream to an output stream
	 */
	public void write(InputStream in, OutputStream out) throws IOException {
		byte[] buffer = new byte[1024];
		int bytesRead = in.read(buffer);
		while (bytesRead != -1) {
			out.write(buffer, 0, bytesRead);
			bytesRead = in.read(buffer);
		}
	}

	public void write(String content, String filename) {
		Writer out = null;
		try {
			out = new BufferedWriter(new FileWriter(filename));
			out.write(content);
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			nullSafeClose(out);
		}
	}

	public void nullSafeClose(InputStream in, OutputStream out) {
		nullSafeClose(in);
		nullSafeClose(out);
	}

	public void nullSafeClose(InputStream in) {
		if (in == null) {
			return;
		}
		try {
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void nullSafeClose(OutputStream out) {
		if (out == null) {
			return;
		}
		try {
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void nullSafeClose(Writer out) {
		if (out == null) {
			return;
		}
		try {
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Check for null first, then attempt to close the reader. Re-throw an
	 * IOException (should it occur) as a RuntimeException
	 * 
	 * @param in
	 */
	public void nullSafeClose(Reader in) {
		if (in == null) {
			return;
		}
		try {
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Obtain a reader that points to the resource passed in by looking for it
	 * on the classpath
	 * 
	 * @param resource
	 * @return
	 */
	public Reader getReader(String resource) {
		ClassLoader loader = this.getClass().getClassLoader();
		InputStream in = loader.getResourceAsStream(resource);
		return new BufferedReader(new InputStreamReader(in));
	}

}
