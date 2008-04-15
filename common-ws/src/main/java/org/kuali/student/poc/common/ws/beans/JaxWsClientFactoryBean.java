package org.kuali.student.poc.common.ws.beans;

import java.net.URL;

import javax.xml.namespace.QName;
import javax.xml.ws.BindingProvider;
import javax.xml.ws.Service;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.core.io.ClassPathResource;

public class JaxWsClientFactoryBean implements FactoryBean {
	private Class<?> serviceEndpointInterface = null;
	private String wsdlDocumentLocation="";
	private String serviceNamespaceURI="";
	private String serviceName="";
	private String serviceUrl="";
	private static final String CLASSPATH_PREFIX = "classpath:";
	@Override
	public Object getObject() throws Exception {
		URL url;
		if(wsdlDocumentLocation.startsWith(CLASSPATH_PREFIX)){
			ClassPathResource cpr = new ClassPathResource(wsdlDocumentLocation.substring(CLASSPATH_PREFIX.length()));
			url = cpr.getURL();
		}else{
			url = new URL(wsdlDocumentLocation);
		}
		QName serviceQName = new QName(this.serviceNamespaceURI,
				this.serviceName);
		Service service = Service.create(url, serviceQName);
		Object client = service.getPort(serviceEndpointInterface);
		//Override the service URL
		if(this.serviceUrl!=null&&!"".equals(this.serviceUrl)){
			((BindingProvider)client).getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, this.serviceUrl);
		}
		
		return client;
	}

	@Override
	public Class<?> getObjectType() {
		return serviceEndpointInterface;
	}

	/**
	 * @return the serviceEndpointInterface
	 */
	public Class<?> getServiceEndpointInterface() {
		return serviceEndpointInterface;
	}

	/**
	 * @param serviceEndpointInterface
	 *            the serviceEndpointInterface to set
	 */
	public void setServiceEndpointInterface(
			Class<?> serviceEndpointInterface) {
		this.serviceEndpointInterface = serviceEndpointInterface;
	}


	/**
	 * @return the serviceNamespaceURI
	 */
	public String getServiceNamespaceURI() {
		return serviceNamespaceURI;
	}

	/**
	 * @param serviceNamespaceURI
	 *            the serviceNamespaceURI to set
	 */
	public void setServiceNamespaceURI(String serviceNamespaceURI) {
		this.serviceNamespaceURI = serviceNamespaceURI;
	}

	/**
	 * @return the serviceName
	 */
	public String getServiceName() {
		return serviceName;
	}

	/**
	 * @param serviceName
	 *            the serviceName to set
	 */
	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	@Override
	public boolean isSingleton() {
		return false;
	}

	/**
	 * @return the wsdlDocumentLocation
	 */
	public String getWsdlDocumentLocation() {
		return wsdlDocumentLocation;
	}

	/**
	 * @param wsdlDocumentLocation the wsdlDocumentLocation to set
	 */
	public void setWsdlDocumentLocation(String wsdlDocumentLocation) {
		this.wsdlDocumentLocation = wsdlDocumentLocation;
	}

	/**
	 * @return the serviceUrl
	 */
	public String getServiceUrl() {
		return serviceUrl;
	}

	/**
	 * @param serviceUrl the serviceUrl to set
	 */
	public void setServiceUrl(String serviceUrl) {
		this.serviceUrl = serviceUrl;
	}

}
