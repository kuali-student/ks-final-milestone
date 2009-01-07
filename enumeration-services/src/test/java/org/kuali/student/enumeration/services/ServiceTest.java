package org.kuali.student.enumeration.services;

import javax.xml.namespace.QName;

import org.apache.cxf.frontend.ClientProxyFactoryBean;
import org.junit.Test;
import org.kuali.student.enumeration.dto.EnumerationMeta;
import org.kuali.student.enumeration.dto.EnumerationMetaList;
import org.kuali.student.enumeration.service.EnumerationService;

public class ServiceTest {
    @Test
    public void testContext() {
        EnumerationService enumerationService;

        ClientProxyFactoryBean factory = new ClientProxyFactoryBean();
        factory.setServiceClass(EnumerationService.class);
        factory.setWsdlLocation("classpath:wsdl/EnumerationService.wsdl");
        factory.setServiceName(new QName("http://student.kuali.org/wsdl/EnumerationService", "EnumerationService"));
        factory.setAddress("http://localhost:8787/enumeration-services/EnumerationService");
        enumerationService = (EnumerationService) factory.create();

        EnumerationMeta meta = new EnumerationMeta();
        meta.setDesc("desc");
        meta.setKey("key");
        meta.setName("name");
        
        enumerationService.addEnumerationMeta(meta);
        
        EnumerationMetaList list = enumerationService.findEnumerationMetas();
        System.out.println(list.getEnumerationMeta().size());
    }
}
