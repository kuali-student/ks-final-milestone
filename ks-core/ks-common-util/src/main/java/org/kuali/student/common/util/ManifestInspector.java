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

import java.io.IOException;
import java.io.InputStream;
import java.util.jar.Attributes;
import java.util.jar.Manifest;

import javax.servlet.ServletContext;

import static org.apache.commons.io.IOUtils.*;
import static org.apache.commons.lang.StringUtils.*;

/**
 * Obtains version information about the application from the
 * META-INF/MANIFEST.MF file contained inside a .war file
 */
public class ManifestInspector {

	/**
	 * Location of the MANIFEST.MF file
	 */
	public static final String MANIFEST_LOCATION = "/META-INF/MANIFEST.MF";
	public static final String BUNDLE_NAME = "Bundle-Name";
	public static final String BUNDLE_VERSION = "Bundle-Version";
	public static final String BUNDLE_TIMESTAMP = "Bundle-Timestamp";
	public static final String HUDSON_BUILD_NUMBER = "Hudson-Build-Number";
	public static final String NO_BUILD_INFORMATION_AVAILABLE = "No build information available";

	/**
	 * Return a Manifest object
	 */
	protected Manifest getManifest(ServletContext servletContext) throws IOException {
		InputStream in = null;
		try {
			in = servletContext.getResourceAsStream(MANIFEST_LOCATION);
			if (in == null) {
				return null;
			} else {
				return new Manifest(in);
			}
		} catch (IOException e) {
			throw e;
		} finally {
			closeQuietly(in);
		}
	}

	/**
	 * Examine the manifest provided for build information. Returns null if
	 * manifest is null
	 */
	protected BuildInformation getBuildInformation(Manifest manifest) {
		// No Manifest is available
		if (manifest == null) {
			return null;
		}

		// Extract the attributes
		Attributes attributes = manifest.getMainAttributes();

		// Manifest attributes containing the build information
		String name = attributes.getValue(BUNDLE_NAME);
		String version = attributes.getValue(BUNDLE_VERSION);
		String buildNumber = attributes.getValue(HUDSON_BUILD_NUMBER);
		String timestamp = attributes.getValue(BUNDLE_TIMESTAMP);

		// Create and populate a BuildInformation object
		BuildInformation bi = new BuildInformation();
		bi.setName(name);
		bi.setVersion(version);
		bi.setBuildNumber(buildNumber);
		bi.setTimestamp(timestamp);
		return bi;
	}

	/**
	 * Obtain version information from MANIFEST.MF
	 */
	public String getBuildInformationString(ServletContext context) throws IOException {
		// Get a handle to a Manifest object
		Manifest manifest = getManifest(context);

		// Store build information attributes from the manifest into a POJO
		BuildInformation buildInformation = getBuildInformation(manifest);

		// Convert the POJO to a string
		return toString(buildInformation);
	}

	/**
	 * Return true if BuildInformation is null or does not contain any
	 * meaningful information.
	 */
	protected boolean isNullOrEmpty(BuildInformation bi) {
		if (bi == null) {
			return true;
		}
		if (!isEmpty(bi.getName())) {
			return false;
		}
		if (!isEmpty(bi.getVersion())) {
			return false;
		}
		if (!isEmpty(bi.getBuildNumber())) {
			return false;
		}
		if (!isEmpty(bi.getTimestamp())) {
			return false;
		}
		return true;
	}

	/**
	 * Convert the build information POJO to a display String
	 */
	public String toString(BuildInformation bi) {
		/**
		 * For developers pointed at a local build, MANIFEST.MF may not be
		 * present
		 * 
		 * Since we overlay the Rice war file, the MANIFEST.MF from Rice may be
		 * present but doesn't contain build information
		 */
		if (isNullOrEmpty(bi)) {
			return NO_BUILD_INFORMATION_AVAILABLE;
		}

		/**
		 * Build number is only present if Hudson has done the build
		 */
		if (!isEmpty(bi.getBuildNumber())) {
			bi.setBuildNumber("#" + bi.getBuildNumber());
		}

		/**
		 * Build a string out of the build information
		 */
		StringBuffer sb = new StringBuffer();
		if (!isEmpty(bi.getName())) {
			sb.append(bi.getName());
			sb.append(" :: ");
		}
		if (!isEmpty(bi.getVersion())) {
			sb.append(bi.getVersion());
			sb.append(" :: ");
		}
		if (!isEmpty(bi.getBuildNumber())) {
			sb.append(bi.getBuildNumber());
			sb.append(" :: ");
		}
		if (!isEmpty(bi.getTimestamp())) {
			sb.append(bi.getTimestamp());
		}
		return sb.toString();
	}
}
