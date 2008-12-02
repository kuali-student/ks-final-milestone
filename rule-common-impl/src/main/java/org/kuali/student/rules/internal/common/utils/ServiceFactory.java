package org.kuali.student.rules.internal.common.utils;

import java.net.URL;

import javax.xml.namespace.QName;
import javax.xml.ws.BindingProvider;
import javax.xml.ws.Service;

public class ServiceFactory {
	private String wsdlURL;
	private String namespace;
	private String serviceName;
	private String serviceInterface;
	private String endpointAddress;

	public void setWsdlURL(String wsdlURL) {
		this.wsdlURL = wsdlURL;
	}

	public void setNamespace(String namespace) {
		this.namespace = namespace;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	public void setServiceInterface(String serviceInterface) {
		this.serviceInterface = serviceInterface;
	}

	public void setEndpointAddress(String endpointAddress) {
		this.endpointAddress = endpointAddress;
	}

	public Service getService() throws Exception {
		return ServiceFactory.getService(this.wsdlURL, this.namespace,
				this.serviceName);
	}

	public static Service getService(String wsdlStr, String namespace,
			String serviceName) throws Exception {
		URL url = null;

		url = getLocalUrl(wsdlStr);
		if (url == null)
			url = new URL(wsdlStr);

		QName sName = new QName(namespace, serviceName);
		Service s = Service.create(url, sName);

		return s;
	}

	public Object getPort() throws Exception {
		return ServiceFactory.getPort(this.wsdlURL, this.namespace,
				this.serviceName, this.serviceInterface, this.endpointAddress);
	}

	public static Object getPort(String wsdlStr, String namespace,
			String serviceName, String serviceInterface, String endpointAddress)
			throws Exception {

		Object oRet = null;

		Service s = ServiceFactory.getService(wsdlStr, namespace, serviceName);

		oRet = s.getPort(Class.forName(serviceInterface));

		if (endpointAddress != null && !"".equals(endpointAddress)) {
			((BindingProvider) oRet).getRequestContext().put(
					BindingProvider.ENDPOINT_ADDRESS_PROPERTY, endpointAddress);
		}

		return oRet;
	}

	private static URL getLocalUrl(String file) throws Exception {

		return ServiceFactory.class.getClassLoader().getResource(file);

	}

}
