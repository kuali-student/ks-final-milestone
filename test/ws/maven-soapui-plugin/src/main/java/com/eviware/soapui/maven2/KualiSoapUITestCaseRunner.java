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
 * Allows a baseURL to be specified that service endpoints are relative to. Also allows a
 * serviceContext to be specified. The serviceContext defaults to "/services/" if nothing is
 * specified
 * 
 * @author Jeff Caddel
 * 
 * @since Apr 6, 2010 11:41:55 PM
 */
public class KualiSoapUITestCaseRunner extends SoapUITestCaseRunner {
	protected final Log log = LogFactory.getLog(this.getClass());

	URLUtil urlUtil = new URLUtil();
	String baseURL;
	String serviceContext;

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
				String newEndPoint = urlUtil.getUpdatedEndPoint(httpRequest.getEndpoint(), baseURL, serviceContext);
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
	}

	public URLUtil getUrlUtil() {
		return urlUtil;
	}

	public void setUrlUtil(URLUtil urlUtil) {
		this.urlUtil = urlUtil;
	}

	public String getBaseURL() {
		return baseURL;
	}

	public void setBaseURL(String baseURL) {
		this.baseURL = baseURL;
	}

	public String getServiceContext() {
		return serviceContext;
	}

	public void setServiceContext(String servicesContext) {
		this.serviceContext = servicesContext;
	}
}
