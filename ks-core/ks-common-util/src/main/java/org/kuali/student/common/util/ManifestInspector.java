package org.kuali.student.common.util;

import java.io.InputStream;
import java.util.jar.Attributes;
import java.util.jar.Manifest;

import javax.servlet.ServletContext;

import org.apache.commons.io.IOUtils;
import org.apache.cxf.common.util.StringUtils;

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

/**
 * Obtains version information about the application from the META-INF/MANIFEST.MF file contained inside a .war file
 */
public class ManifestInspector {
	/**
	 * Location of the MANIFEST.MF file
	 */
	private static final String MANIFEST = "/META-INF/MANIFEST.MF";

	/**
	 * Manifest attributes that contain build information
	 */
	private static final String[] BUILD_ATTRIBUTES = { "Kuali-Student-Type", "Kuali-Student-Version", "Build-Timestamp" };

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
	 * Convert attributes we are interested in to a String
	 */
	public String toString(Attributes attributes, String[] attributeNames) {
		StringBuffer sb = new StringBuffer();
		int count = 0;
		for (int i = 0; i < attributeNames.length; i++) {
			if (count != 0) {
				sb.append(" :: ");
			}
			String value = attributes.getValue(attributeNames[i]);
			if (!StringUtils.isEmpty(value)) {
				count++;
				sb.append(value);
			}
		}
		return sb.toString();
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

		// Convert the ones we are interested in to a string
		String buildInfo = toString(attributes, BUILD_ATTRIBUTES);
		if (StringUtils.isEmpty(buildInfo)) {
			return "No build information available";
		} else {
			return buildInfo;
		}
	}
}
