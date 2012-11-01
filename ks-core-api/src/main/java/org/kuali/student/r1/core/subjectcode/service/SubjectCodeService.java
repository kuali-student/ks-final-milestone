package org.kuali.student.r1.core.subjectcode.service;

import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

import org.kuali.student.r2.core.search.service.SearchService;

@WebService(name = "SubjectCodeService", targetNamespace = "http://student.kuali.org/wsdl/subjectCode")
@SOAPBinding(style = SOAPBinding.Style.DOCUMENT, use = SOAPBinding.Use.LITERAL, parameterStyle = SOAPBinding.ParameterStyle.WRAPPED)
public interface SubjectCodeService extends SearchService {

}
