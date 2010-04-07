/*
 * soapUI, copyright (C) 2004-2009 eviware.com 
 *
 *  SoapUI is free software; you can redistribute it and/or modify it under the 
 *  terms of the GNU Lesser General Public License as published by the Free Software Foundation; 
 *  either version 2.1 of the License, or (at your option) any later version.
 *
 *  SoapUI is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without 
 *  even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. 
 *  See the GNU Lesser General Public License for more details at gnu.org.
 */

package com.eviware.soapui.maven2;

import com.eviware.soapui.SoapUI;

import org.apache.commons.io.IOUtils;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

/**
 * Runs soapUI functional tests
 * 
 * @goal test
 */

public class TestMojo extends AbstractMojo {

	protected File copyProjectFile() throws MojoExecutionException {
		if (projectFile == null) {
			throw new MojoExecutionException("soapui-project-file setting is required");
		}

		File file = new File(projectFile);
		if (file.exists()) {
			return file;
		}

		InputStream in = null;
		OutputStream out = null;
		try {
			ResourceLoader loader = new DefaultResourceLoader();
			Resource resource = loader.getResource(projectFile);
			in = resource.getInputStream();
			File temp = getTemporaryFile();
			out = new BufferedOutputStream(new FileOutputStream(temp));
			IOUtils.copy(in, out);
			return temp;
		} catch (Exception e) {
			throw new MojoExecutionException("Error copying project file", e);
		} finally {
			IOUtils.closeQuietly(in);
			IOUtils.closeQuietly(out);
		}
	}

	protected File getTemporaryFile() throws IOException {
		File temp = File.createTempFile("soapui-project", ".xml");
		temp.deleteOnExit();
		return temp;
	}

	public void execute() throws MojoExecutionException, MojoFailureException {
		if (skip || "true".equals(System.getProperty("maven.test.skip"))) {
			return;
		}

		getLog().info("Running SoapUI version: " + SoapUI.SOAPUI_VERSION);
		KualiSoapUITestCaseRunner runner = new KualiSoapUITestCaseRunner("soapUI " + SoapUI.SOAPUI_VERSION + " Maven2 TestCase Runner");
		File file = copyProjectFile();
		getLog().info("Loading SoapUI project from: " + file.getAbsolutePath());
		runner.setProjectFile(file.getAbsolutePath());
		runner.setContext(context);
		runner.setProtocol(protocol);
		runner.setPort(port);
		runner.setBaseURL(baseURL);
		runner.setServicesContext(servicesContext);

		if (endpoint != null) {
			runner.setEndpoint(endpoint);
		}

		if (testSuite != null) {
			runner.setTestSuite(testSuite);
		}

		if (testCase != null) {
			runner.setTestCase(testCase);
		}

		if (username != null)
			runner.setUsername(username);

		if (password != null)
			runner.setPassword(password);

		if (wssPasswordType != null)
			runner.setWssPasswordType(wssPasswordType);

		if (domain != null)
			runner.setDomain(domain);

		if (host != null)
			runner.setHost(host);

		if (outputFolder != null)
			runner.setOutputFolder(outputFolder);

		runner.setPrintReport(printReport);
		runner.setExportAll(exportAll);
		runner.setJUnitReport(junitReport);
		runner.setEnableUI(interactive);
		runner.setIgnoreError(testFailIgnore);
		runner.setSaveAfterRun(saveAfterRun);

		if (settingsFile != null)
			runner.setSettingsFile(settingsFile);

		if (projectPassword != null)
			runner.setProjectPassword(projectPassword);

		if (settingsPassword != null)
			runner.setSoapUISettingsPassword(settingsPassword);

		if (globalProperties != null)
			runner.setGlobalProperties(globalProperties);

		if (projectProperties != null)
			runner.setProjectProperties(projectProperties);

		if (soapuiProperties != null && soapuiProperties.size() > 0)
			for (Object key : soapuiProperties.keySet()) {
				getLog().info("Setting " + (String) key + " value " + soapuiProperties.getProperty((String) key));
				System.setProperty((String) key, soapuiProperties.getProperty((String) key));
			}
		try {
			runner.run();
		} catch (Exception e) {
			getLog().error(e.toString());
			throw new MojoFailureException(this, "SoapUI Test(s) failed", e.getMessage());
		}
	}

	/**
	 * The soapUI project file to test with
	 * 
	 * @parameter expression="${soapui.projectfile}"
	 *            default-value="${project.artifactId}-soapui-project.xml"
	 */

	private String projectFile;

	/**
	 * The TestSuite to run project file to test with
	 * 
	 * @parameter expression="${soapui.testsuite}"
	 */

	private String testSuite;

	/**
	 * The TestCase to run project file to test with
	 * 
	 * @parameter expression="${soapui.testcase}"
	 */

	private String testCase;

	/**
	 * The username to use for authentication challenges
	 * 
	 * @parameter expression="${soapui.username}"
	 */

	private String username;

	/**
	 * The password to use for authentication challenges
	 * 
	 * @parameter expression="${soapui.password}"
	 */

	private String password;

	/**
	 * The WSS password-type to use for any authentications. Setting this will result in the
	 * addition of WS-Security UsernamePassword tokens to any outgoing request containing the
	 * specified username and password. Set to either 'Text' or 'Digest'
	 * 
	 * @parameter expression="${soapui.wssPasswordType}"
	 */

	private String wssPasswordType;

	/**
	 * The domain to use for authentication challenges
	 * 
	 * @parameter expression="${soapui.domain}"
	 */

	private String domain;

	/**
	 * The host to use for requests
	 * 
	 * @parameter expression="${soapui.host}"
	 */

	private String host;

	/**
	 * The port to use for requests (eg 80, 443)
	 * 
	 * @parameter default-value="80"
	 */

	private Integer port;

	/**
	 * The protocol to use for requests (eg http, https)
	 * 
	 * @parameter default-value="http"
	 */

	private String protocol;

	/**
	 * The context to use for requests (eg ks-embedded, ks-stg, ks-dev etc)
	 * 
	 * @parameter default-value=""
	 */

	private String context;

	/**
	 * The base url to use for all requests (eg https://test.kuali.org/ks-stg
	 * 
	 * @parameter default-value=""
	 */

	private String baseURL;

	/**
	 * The base url to use for all requests (eg https://test.kuali.org/ks-stg
	 * 
	 * @parameter default-value="services"
	 */

	private String servicesContext;

	/**
	 * Overrides the endpoint to use for requests
	 * 
	 * @parameter expression="${soapui.endpoint}"
	 */

	private String endpoint;

	/**
	 * Sets the output folder for reports
	 * 
	 * @parameter expression="${soapui.outputFolder}"
	 */

	private String outputFolder;

	/**
	 * Turns on printing of reports
	 * 
	 * @parameter expression="${soapui.printReport}"
	 */

	private boolean printReport;

	/**
	 * Enabled interactive groovy scripts
	 * 
	 * @parameter expression="${soapui.interactive}"
	 */

	private boolean interactive;

	/**
	 * Turns on exporting of all results
	 * 
	 * @parameter expression="${soapui.exportAll}"
	 */

	private boolean exportAll;

	/**
	 * Turns on creation of reports in junit style
	 * 
	 * @parameter expression="${soapui.junitReport}"
	 */

	private boolean junitReport;

	/**
	 * Specifies soapUI settings file to use
	 * 
	 * @parameter expression="${soapui.settingsFile}"
	 */

	private String settingsFile;

	/**
	 * Tells Test Runner to skip tests.
	 * 
	 * @parameter expression="${soapui.skip}"
	 */

	private boolean skip;

	/**
	 * Specifies password for encrypted soapUI project file
	 * 
	 * @parameter expression="${soapui.project.password}"
	 */
	private String projectPassword;

	/**
	 * Specifies password for encrypted soapui-settings file
	 * 
	 * @parameter expression="${soapui.settingsFile.password}"
	 */
	private String settingsPassword;

	/**
	 * If set ignore failed tests
	 * 
	 * @parameter expression="${soapui.testFailIgnore}"
	 */

	private boolean testFailIgnore;

	/**
	 * Specified global property values soapui.saveAfterRun
	 * 
	 * @parameter expression="${soapui.globalProperties}"
	 */

	private String[] globalProperties;

	/**
	 * Specified project property values
	 * 
	 * @parameter expression="${soapui.projectProperties}"
	 */

	private String[] projectProperties;

	/**
	 * Saves project file after running tests
	 * 
	 * @parameter expression="${}"
	 */

	private boolean saveAfterRun;

	/**
	 * SoapUI Properties.
	 * 
	 * @parameter expression="${soapuiProperties}"
	 */
	private Properties soapuiProperties;
}
