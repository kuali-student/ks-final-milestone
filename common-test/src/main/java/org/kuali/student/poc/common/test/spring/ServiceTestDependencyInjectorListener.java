package org.kuali.student.poc.common.test.spring;

import java.lang.reflect.Field;

import javax.jws.WebService;

import org.kuali.student.poc.common.cxf.security.SecureJaxWsProxyFactoryBean;
import org.kuali.student.poc.common.ws.beans.JaxWsClientFactoryBean;
import org.springframework.test.context.TestContext;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

public class ServiceTestDependencyInjectorListener extends DependencyInjectionTestExecutionListener {

    @Override
    protected void injectDependencies(TestContext testContext) throws Exception {

        super.injectDependencies(testContext);
        for (Field f : testContext.getTestClass().getDeclaredFields()) {
            if (f.isAnnotationPresent(Client.class)) {
                Client a = f.getAnnotation(Client.class);
                Class<?> serviceImplClass = Class.forName(a.value());
                String serviceName = "";
                String serviceWsdlDocumentLocation = "http://localhost:" + a.port() + "/Service/Service?wsdl";
                String serviceNamespaceURI = "";
                if (serviceImplClass.isAnnotationPresent(WebService.class)) {
                    WebService wsAnnotation = serviceImplClass.getAnnotation(WebService.class);
                    serviceName = wsAnnotation.serviceName();
                    serviceNamespaceURI = wsAnnotation.targetNamespace();
                }

                if (!a.secure()) {
                    JaxWsClientFactoryBean clientFactory = new JaxWsClientFactoryBean();
                    clientFactory.setServiceEndpointInterface(f.getType());
                    clientFactory.setServiceName(serviceName);
                    clientFactory.setServiceNamespaceURI(serviceNamespaceURI);
                    clientFactory.setWsdlDocumentLocation(serviceWsdlDocumentLocation);
                    f.set(testContext.getTestInstance(), clientFactory.getObject());
                } else if (a.secure()) {
                    //TODO: Create secure client based on jaxws-rt or cxf
                    
                    SecureJaxWsProxyFactoryBean proxyFactory = new SecureJaxWsProxyFactoryBean();

                    proxyFactory.setServiceClass(serviceImplClass.getInterfaces()[0]);
                    proxyFactory.setAddress("http://localhost.umd.edu:" + a.port() + "/Service/ServiceSecure");

                    f.set(testContext.getTestInstance(), proxyFactory.create());
                }
            }
        }

    }
}
