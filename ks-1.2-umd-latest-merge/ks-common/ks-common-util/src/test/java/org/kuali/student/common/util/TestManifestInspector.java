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

import java.util.jar.Attributes;
import java.util.jar.Manifest;

import junit.framework.Assert;

import org.apache.cxf.common.util.StringUtils;
import org.junit.Test;

public class TestManifestInspector {
	String name = "Kuali Student Embedded";
	String version = "1.1.0-M8-SNAPSHOT";
	String timestamp = "2010-08-10 21:35 EDT";
	String buildNumber = "89";

	@Test
	public void testNullManifest() throws Exception {
		ManifestInspector mi = new ManifestInspector();
		BuildInformation bi = mi.getBuildInformation(null);
		Assert.assertNull(bi);
	}

	@Test
	public void testEmptyManifest() throws Exception {
		ManifestInspector mi = new ManifestInspector();
		Manifest manifest = new Manifest();
		BuildInformation bi = mi.getBuildInformation(manifest);
		Assert.assertTrue(mi.isNullOrEmpty(bi));
		String s = mi.toString(bi);
		Assert.assertEquals(s, ManifestInspector.NO_BUILD_INFORMATION_AVAILABLE);
	}

	@Test
	public void testNoBuildNumberManifest() throws Exception {
		ManifestInspector mi = new ManifestInspector();
		Manifest manifest = new Manifest();
		Attributes attributes = manifest.getMainAttributes();
		attributes.putValue(ManifestInspector.BUNDLE_NAME, name);
		attributes.putValue(ManifestInspector.BUNDLE_VERSION, version);
		attributes.putValue(ManifestInspector.BUNDLE_TIMESTAMP, timestamp);
		BuildInformation bi = mi.getBuildInformation(manifest);
		Assert.assertFalse(mi.isNullOrEmpty(bi));
		Assert.assertTrue(StringUtils.isEmpty(bi.getBuildNumber()));
		String testString = name + " :: " + version + " :: " + timestamp;
		String s = mi.toString(bi);
		Assert.assertEquals(s, testString);
	}

	@Test
	public void testFullBuildInfo() throws Exception {
		ManifestInspector mi = new ManifestInspector();
		Manifest manifest = new Manifest();
		Attributes attributes = manifest.getMainAttributes();
		attributes.putValue(ManifestInspector.BUNDLE_NAME, name);
		attributes.putValue(ManifestInspector.BUNDLE_VERSION, version);
        attributes.putValue(ManifestInspector.BUNDLE_BUILD_NUMBER, buildNumber);
		attributes.putValue(ManifestInspector.BUNDLE_TIMESTAMP, timestamp);
		BuildInformation bi = mi.getBuildInformation(manifest);
		Assert.assertFalse(mi.isNullOrEmpty(bi));
		String testString = name + " :: " + version + " :: #" + buildNumber + " :: " + timestamp;
		String s = mi.toString(bi);
		Assert.assertEquals(s, testString);
	}

}
