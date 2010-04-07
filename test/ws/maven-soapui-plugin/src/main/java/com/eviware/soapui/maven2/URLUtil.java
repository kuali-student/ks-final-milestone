package com.eviware.soapui.maven2;

import java.net.MalformedURLException;
import java.net.URL;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * The SoapUI maven plugin did not deal with URL's very well. It got confused by
 * http://localhost/ks-embedded for example. This logic properly parses URL's in a Kuali Student
 * friendly way
 * 
 * @author Jeff Caddel
 * 
 * @since Apr 6, 2010 9:58:43 PM
 */
public class URLUtil {
	protected final Log log = LogFactory.getLog(this.getClass());

	/**
	 * Return an updated endpoint based on the baseURL and servicesContext
	 */
	public String getUpdatedEndPoint(String endPoint, String baseURL, String servicesContext) {
		// Always make sure baseURL ends with a trailing slash
		if (!baseURL.endsWith("/")) {
			baseURL += "/";
		}

		// Construct some URL objects
		URL oldURL = getURL(endPoint);
		URL newURL = getURL(baseURL);

		// Extract the path portion of the existing endpoint
		String path = oldURL.getPath();

		// Figure out where the "/services/" portion of the path is
		int pos = path.indexOf("/" + servicesContext + "/") + 1;

		// Save the portion pointing to the service, eg "services/LuService"
		String serviceSuffix = path.substring(pos);

		// Append this to the baseURL
		String newFullURL = newURL.toString() + serviceSuffix;

		// Return the new URL
		return newFullURL;
	}

	/**
	 * Replace the port with our new port
	 */
	public String replacePort(String endPoint, Integer port) {
		// Get a URL object
		URL url = getURL(endPoint);
		// Construct a new URL with the new path
		return getURL(url.getProtocol(), url.getHost(), port, url.getPath()).toString();
	}

	/**
	 * Replace the protocol with our new protocol
	 */
	public String replaceProtocol(String endPoint, String protocol) {
		// Get a URL object
		URL url = getURL(endPoint);
		// Construct a new URL with the new path
		return getURL(protocol, url.getHost(), getPort(url), url.getPath()).toString();
	}

	/**
	 * Replace the context with our new context
	 */
	public String replaceContext(String endPoint, String context) {
		// Get a URL object
		URL url = getURL(endPoint);
		// Convert to the new context
		String path = getNewPath(url, context);
		// Construct a new URL with the new path
		return getURL(url.getProtocol(), url.getHost(), getPort(url), path).toString();
	}

	/**
	 * Replace the host with our new host
	 */
	public String replaceHost(String endPoint, String host) {
		// Get a URL object
		URL url = getURL(endPoint);
		// Replace the host with the new host
		return getURL(url.getProtocol(), host, getPort(url), url.getPath()).toString();
	}

	/**
	 * Return -1 if the port is set to the default, otherwise return the port
	 */
	protected int getPort(URL url) {
		int port = url.getPort();
		int defaultPort = url.getDefaultPort();
		return (port == defaultPort) ? -1 : port;
	}

	/**
	 * Replace the existing context with the new context, unless the existing context is
	 * "services". In that case, insert the newContext without replacing "services"
	 * 
	 * @param url
	 * @param newContext
	 */
	protected String getNewPath(URL url, String newContext) {
		String path = url.getPath();
		String oldContext = getContext(url);
		if (StringUtils.isEmpty(oldContext)) {
			return "/" + newContext + path;
		}
		if ("services".equalsIgnoreCase(oldContext)) {
			return "/" + newContext + path;
		}
		int pos = oldContext.length() + 1;
		return "/" + newContext + path.substring(pos);
	}

	/**
	 * Return the context. For our purposes "context" means the first portion of the url after
	 * protocol and hostname
	 * 
	 * http://localhost/ks-embedded/services/LUService - context is "ks-embedded"<br>
	 * http://localhost/services/LUService - context is "services"<br>
	 * http://localhost/ - context is "/"<br>
	 * http://localhost - context is ""<br>
	 * 
	 * @param url
	 */
	protected String getContext(URL url) {
		String path = url.getPath();
		return StringUtils.substringBetween(path, "/", "/");
	}

	/**
	 * Return a URL from the parameters
	 * 
	 * @param protocol
	 * @param hostname
	 * @param port
	 * @param file
	 */
	protected URL getURL(String protocol, String hostname, int port, String file) {
		try {
			return new URL(protocol, hostname, port, file);
		} catch (MalformedURLException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Return a URL from the string passed in
	 * 
	 * @param spec
	 */
	protected URL getURL(String spec) {
		try {
			return new URL(spec);
		} catch (MalformedURLException e) {
			throw new RuntimeException(e);
		}
	}
}
