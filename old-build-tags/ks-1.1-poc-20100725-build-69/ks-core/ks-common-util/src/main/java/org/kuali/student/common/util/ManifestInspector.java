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

package org.kuali.student.common.util;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.jar.Attributes;
import java.util.jar.Manifest;

import javax.servlet.ServletContext;

import org.apache.commons.io.IOUtils;
import org.apache.cxf.common.util.StringUtils;

/**
 * Obtains version information about the application from the META-INF/MANIFEST.MF file contained inside a .war file
 */
public class ManifestInspector {
	/**
	 * Location of the MANIFEST.MF file
	 */
	private static final String MANIFEST = "/META-INF/MANIFEST.MF";

	/**
	 * Return a Manifest object
	 */
	public Manifest getManifest(ServletContext servletContext) {
		InputStream in = null;
		try {
			in = servletContext.getResourceAsStream(MANIFEST);
			if (in == null) {
				return null;
			} else {
				return new Manifest(in);
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			IOUtils.closeQuietly(in);
		}
	}

	/**
	 * Obtain version information from MANIFEST.MF
	 */
	public String getBuildInformation(ServletContext context) {
		// Get a handle to a Manifest object
		Manifest manifest = getManifest(context);

		// No Manifest is available
		if (manifest == null) {
			return "No build information available";
		}

		// Extract the attributes
		Attributes attributes = manifest.getMainAttributes();

		/**
		 * Manifest attributes containing the build information we want to display
		 */
		String name = attributes.getValue("Bundle-Name");
		String version = attributes.getValue("Bundle-Version");
		String buildNumber = attributes.getValue("Hudson-Build-Number");
		String timestamp = attributes.getValue("Bundle-Timestamp");

		/**
		 * MANIFEST.MF does not get created until the .war is bundled up. For developers pointed at a Tomcat instance
		 * using the exploded directory structure, there will not be a MANIFEST.MF present
		 */
		if (isEmpty(name) && isEmpty(version) && isEmpty(buildNumber) && isEmpty(timestamp)) {
			return "No build information available";
		}

		/**
		 * Build number will only be present if Hudson has done the build
		 */
		if (buildNumber == null) {
			buildNumber = "N/A";
		} else {
			buildNumber = "#" + buildNumber;
		}

		// Build a list
		List<String> displayAttributes = new ArrayList<String>();
		displayAttributes.add(name);
		displayAttributes.add(version);
		displayAttributes.add(buildNumber);
		displayAttributes.add(timestamp);

		// Return the display string
		return getBuildInfoString(displayAttributes);
	}

	/**
	 * Remove any empties from the list
	 */
	protected void removeEmptyStrings(List<String> strings) {
		Iterator<String> itr = strings.iterator();
		while (itr.hasNext()) {
			String value = itr.next();
			if (isEmpty(value)) {
				itr.remove();
			}
		}
	}

	/**
	 * Convert attributes into a String
	 */
	protected String getBuildInfoString(List<String> strings) {
		removeEmptyStrings(strings);
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < strings.size(); i++) {
			if (i != 0) {
				sb.append(" :: ");
			}
			sb.append(strings.get(i));
		}
		return sb.toString();
	}

	/**
	 * Pass through call to StringUtils
	 */
	protected boolean isEmpty(String s) {
		return StringUtils.isEmpty(s);
	}
}
