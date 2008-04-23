package org.kuali.student.poc.common.test.spring;

import java.lang.reflect.Field;

import javax.jws.WebService;
import javax.xml.namespace.QName;

import org.kuali.student.poc.common.ws.beans.JaxWsClientFactory;
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
                String serviceNamespaceURI = "";
                String serviceWsdlLocation = "http://localhost:" + a.port() + "/Service/Service" + (a.secure()?"Secure":"") + "?wsdl";
                String serviceAddress = "http://localhost:" + a.port() + "/Service/Service" + (a.secure()?"Secure":"");
               
                if (serviceImplClass.isAnnotationPresent(WebService.class)) {
                    WebService wsAnnotation = serviceImplClass.getAnnotation(WebService.class);
                    serviceName = wsAnnotation.serviceName();
                    serviceNamespaceURI = wsAnnotation.targetNamespace();
                }

                Class<?> clientImplClass = Class.forName("org.kuali.student.poc.common.ws.beans.JaxWsClientFactoryBean");
              
                if (a.secure()) {                    
                    try {
                        clientImplClass = Class.forName("org.kuali.student.poc.common.cxf.security.SecureJaxWsProxyFactoryBean");
                    } catch (ClassNotFoundException cnfe) {}
                }

                JaxWsClientFactory clientFactory = (JaxWsClientFactory) clientImplClass.newInstance();

                clientFactory.setServiceEndpointInterface(f.getType());
                clientFactory.setServiceName(new QName(serviceNamespaceURI, serviceName));
                clientFactory.setWsdlLocation(serviceWsdlLocation);
                clientFactory.setAddress(serviceAddress);

                f.set(testContext.getTestInstance(), clientFactory.getObject());
            }
        }

    }
}
