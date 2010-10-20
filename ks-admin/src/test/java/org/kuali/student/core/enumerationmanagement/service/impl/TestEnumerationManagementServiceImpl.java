package org.kuali.student.core.enumerationmanagement.service.impl;

import java.text.SimpleDateFormat;
import java.util.List;

import javax.xml.namespace.QName;

import org.junit.Test;
import org.kuali.rice.core.resourceloader.GlobalResourceLoader;
import org.kuali.student.core.enumerationmanagement.dto.EnumeratedValueInfo;
import org.kuali.student.core.enumerationmanagement.dto.EnumerationInfo;
import org.kuali.student.core.enumerationmanagement.service.EnumerationManagementService;
import org.kuali.student.core.exceptions.MissingParameterException;
import org.kuali.student.core.search.dto.SearchParam;
import org.kuali.student.core.search.dto.SearchRequest;
import org.kuali.student.core.search.dto.SearchResult;
import org.kuali.student.core.test.BaseCase;

public class TestEnumerationManagementServiceImpl extends BaseCase{
	private static final SimpleDateFormat DF = new SimpleDateFormat("yyyyMMdd");
	@Test
	public void testSearchParsing() throws MissingParameterException{
				
	    QName qname = new QName("http://student.kuali.org/wsdl/EnumerationManagementService", "EnumerationManagementService");
	    EnumerationManagementService service = (EnumerationManagementService) GlobalResourceLoader.getService(qname);
		
		
		SearchRequest searchRequest = new SearchRequest();
		searchRequest.setSearchKey("enumeration.management.search");
		
		SearchParam searchParam;
		
		searchParam = new SearchParam();
		searchParam.setKey("enumeration.queryParam.enumerationType");
		searchParam.setValue("kuali.lu.subjectArea");
		searchRequest.getParams().add(searchParam);
		
		searchParam = new SearchParam();
		searchParam.setKey("enumeration.queryParam.enumerationOptionalCode");
		searchParam.setValue("SOWK");
		searchRequest.getParams().add(searchParam);
		
		SearchResult result = service.search(searchRequest);
		
		assertNotNull(result);
		assertEquals(1,result.getRows().size());
		
		
		searchRequest.getParams().remove(1);
		
		searchRequest.setNeededTotalResults(true);
		searchRequest.setMaxResults(4);
		searchRequest.setStartAt(3);
		searchRequest.setSortColumn("enumeration.resultColumn.sortKey");
		
		result = service.search(searchRequest);
		assertEquals(4,result.getRows().size());
		assertEquals("3",result.getRows().get(0).getCells().get(0).getValue());
		assertEquals("4",result.getRows().get(1).getCells().get(0).getValue());
		assertEquals("5",result.getRows().get(2).getCells().get(0).getValue());
		assertEquals("6",result.getRows().get(3).getCells().get(0).getValue());
		
		assertEquals(34, result.getTotalResults().intValue());
	}
	
	@Test 
	public void testGets() throws Exception{
	    QName qname = new QName("http://student.kuali.org/wsdl/EnumerationManagementService", "EnumerationManagementService");
	    EnumerationManagementService service = (EnumerationManagementService) GlobalResourceLoader.getService(qname);
	    
	    List<EnumeratedValueInfo> values = service.getEnumeratedValues("kuali.lu.subjectArea", null, null, null);
	    assertEquals(34,values.size());
	    
	    EnumerationInfo enumeration = service.getEnumeration("kuali.lu.subjectArea");
	    assertEquals("Subject Area Enumeration",enumeration.getName());
	    
	    List<EnumerationInfo> enumerations = service.getEnumerations();
	    assertEquals(11,enumerations.size());
	    
	}
	
	@Test
	public void testEnumerationManagementServiceCrud() throws Exception{
	    QName qname = new QName("http://student.kuali.org/wsdl/EnumerationManagementService", "EnumerationManagementService");
	    EnumerationManagementService service = (EnumerationManagementService) GlobalResourceLoader.getService(qname);
		
	    EnumeratedValueInfo dto = new EnumeratedValueInfo();
	    
	    dto.setAbbrevValue("abbrev");
	    dto.setCode("CODE");
	    dto.setEffectiveDate(DF.parse("20100101"));
	    dto.setExpirationDate(DF.parse("20100202"));
	    dto.setEnumerationKey("kuali.lu.subjectArea");
	    dto.setSortKey("99");
	    dto.setValue("value");
	    EnumeratedValueInfo added = service.addEnumeratedValue("kuali.lu.subjectArea", dto);
	    
	    assertEquals(dto.getAbbrevValue(),added.getAbbrevValue());
	    assertEquals(dto.getCode(),added.getCode());
	    assertEquals(dto.getEnumerationKey(),added.getEnumerationKey());
	    assertEquals(dto.getSortKey(),added.getSortKey());
	    assertEquals(dto.getValue(),added.getValue());
	    assertEquals(dto.getEffectiveDate(),added.getEffectiveDate());
	    assertEquals(dto.getExpirationDate(),added.getExpirationDate());
	    
	    added.setAbbrevValue("NEWabbrev");
	    added.setCode("NEWCODE");
	    added.setEffectiveDate(DF.parse("20100303"));
	    added.setExpirationDate(DF.parse("20100404"));
	    added.setEnumerationKey("kuali.atptype.duration");
	    added.setSortKey("100");
	    added.setValue("NEWvalue");
	    
	    EnumeratedValueInfo updated = service.updateEnumeratedValue("kuali.lu.subjectArea", "BIOL", added);
		
	    assertEquals(added.getAbbrevValue(),updated.getAbbrevValue());
	    assertEquals(added.getCode(),updated.getCode());
	    assertEquals(added.getEnumerationKey(),updated.getEnumerationKey());
	    assertEquals(added.getSortKey(),updated.getSortKey());
	    assertEquals(added.getValue(),updated.getValue());
	    assertEquals(added.getEffectiveDate(),updated.getEffectiveDate());
	    assertEquals(added.getExpirationDate(),updated.getExpirationDate());
	}
}
