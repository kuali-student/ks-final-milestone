package com.eviware.soapui.maven2;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.eviware.soapui.impl.support.AbstractHttpRequest;
import com.eviware.soapui.impl.support.http.HttpRequestTestStep;
import com.eviware.soapui.impl.wsdl.WsdlRequest;
import com.eviware.soapui.impl.wsdl.teststeps.WsdlTestRequest;
import com.eviware.soapui.support.StringUtils;
import com.eviware.soapui.tools.SoapUITestCaseRunner;

public class KualiSoapUITestCaseRunner extends SoapUITestCaseRunner {
	protected final Log log = LogFactory.getLog(this.getClass());

	URLUtil urlUtil = new URLUtil();
	String context;

	public KualiSoapUITestCaseRunner() {
		super();
	}

	public KualiSoapUITestCaseRunner(String title) {
		super(title);
	}

	protected void prepareRequestStep(HttpRequestTestStep requestStep) {
		AbstractHttpRequest<?> httpRequest = requestStep.getHttpRequest();
		if (StringUtils.hasContent(getEndpoint())) {
			httpRequest.setEndpoint(getEndpoint());
		} else {
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
		}

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
}
