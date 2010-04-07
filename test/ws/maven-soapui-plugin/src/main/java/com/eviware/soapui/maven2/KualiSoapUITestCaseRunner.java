package com.eviware.soapui.maven2;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.eviware.soapui.impl.support.AbstractHttpRequest;
import com.eviware.soapui.impl.support.http.HttpRequestTestStep;
import com.eviware.soapui.impl.wsdl.WsdlRequest;
import com.eviware.soapui.impl.wsdl.teststeps.WsdlTestRequest;
import com.eviware.soapui.support.StringUtils;
import com.eviware.soapui.tools.SoapUITestCaseRunner;

/**
 * Allows context, protocol, and port to be specified in addition to host
 * 
 * @author Jeff Caddel
 * 
 * @since Apr 6, 2010 11:41:55 PM
 */
public class KualiSoapUITestCaseRunner extends SoapUITestCaseRunner {
	protected final Log log = LogFactory.getLog(this.getClass());

	URLUtil urlUtil = new URLUtil();
	String context;
	String protocol;
	Integer port;
	String baseURL;
	String servicesContext;

	public KualiSoapUITestCaseRunner() {
		super();
	}

	public KualiSoapUITestCaseRunner(String title) {
		super(title);
	}

	protected void prepareRequestStep(HttpRequestTestStep requestStep) {
		AbstractHttpRequest<?> httpRequest = requestStep.getHttpRequest();
		configureEndpoint(httpRequest);

		if (StringUtils.hasContent(getUsername())) {
			httpRequest.setUsername(getUsername());
		}

		if (StringUtils.hasContent(getPassword())) {
			httpRequest.setPassword(getPassword());
		}

		if (StringUtils.hasContent(getDomain())) {
			httpRequest.setDomain(getDomain());
		}

		if (httpRequest instanceof WsdlRequest) {
			if (getWssPasswordType() != null && getWssPasswordType().length() > 0) {
				((WsdlRequest) httpRequest).setWssPasswordType(getWssPasswordType().equals("Digest") ? WsdlTestRequest.PW_TYPE_DIGEST : WsdlTestRequest.PW_TYPE_TEXT);
			}
		}
	}

	protected void configureEndpoint(AbstractHttpRequest<?> httpRequest) {
		if (StringUtils.hasContent(getEndpoint())) {
			httpRequest.setEndpoint(getEndpoint());
			return;
		}
		if (StringUtils.hasContent(getBaseURL())) {
			try {
				log.info("Old endpoint: " + httpRequest.getEndpoint());
				String newEndPoint = urlUtil.getUpdatedEndPoint(httpRequest.getEndpoint(), baseURL, servicesContext);
				log.info("New endpoint: " + newEndPoint);
				httpRequest.setEndpoint(newEndPoint);
			} catch (Exception e) {
				log.error("Failed to set host on endpoint", e);
			}
			return;
		}
		if (StringUtils.hasContent(getHost())) {
			try {
				log.info("Replacing host: '" + httpRequest.getEndpoint() + "' with '" + getHost() + "'");
				String ep = urlUtil.replaceHost(httpRequest.getEndpoint(), getHost());
				httpRequest.setEndpoint(ep);
			} catch (Exception e) {
				log.error("Failed to set host on endpoint", e);
			}
		}
		if (StringUtils.hasContent(getContext())) {
			try {
				log.info("Replacing context: '" + httpRequest.getEndpoint() + "' with '" + getContext() + "'");
				String ep = urlUtil.replaceContext(httpRequest.getEndpoint(), getContext());
				httpRequest.setEndpoint(ep);
			} catch (Exception e) {
				log.error("Failed to set context on endpoint", e);
			}
		}
		log.info("Protocol: " + getProtocol());
		if (StringUtils.hasContent(getProtocol())) {
			try {
				log.info("Replacing protocol: '" + httpRequest.getEndpoint() + "' with '" + getProtocol() + "'");
				String ep = urlUtil.replaceProtocol(httpRequest.getEndpoint(), getProtocol());
				httpRequest.setEndpoint(ep);
			} catch (Exception e) {
				log.error("Failed to set protocol on endpoint", e);
			}
		}
		if (port != null) {
			try {
				log.info("Replacing port: '" + httpRequest.getEndpoint() + "' with '" + getPort() + "'");
				String ep = urlUtil.replacePort(httpRequest.getEndpoint(), getPort());
				httpRequest.setEndpoint(ep);
			} catch (Exception e) {
				log.error("Failed to set port on endpoint", e);
			}
		}
	}

	public URLUtil getUrlUtil() {
		return urlUtil;
	}

	public void setUrlUtil(URLUtil urlUtil) {
		this.urlUtil = urlUtil;
	}

	public String getContext() {
		return context;
	}

	public void setContext(String context) {
		this.context = context;
	}

	public String getProtocol() {
		return protocol;
	}

	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}

	public Integer getPort() {
		return port;
	}

	public void setPort(Integer port) {
		this.port = port;
	}

	public String getBaseURL() {
		return baseURL;
	}

	public void setBaseURL(String baseURL) {
		this.baseURL = baseURL;
	}

	public String getServicesContext() {
		return servicesContext;
	}

	public void setServicesContext(String servicesContext) {
		this.servicesContext = servicesContext;
	}
}
