package org.kuali.student.r1.core.dictionary.service;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import org.junit.Test;
import org.kuali.student.r1.common.dictionary.dto.ObjectStructureDefinition;
import org.kuali.student.r1.common.dictionary.service.impl.DictionaryDiscrepencyTesterHelper;
import org.kuali.student.r1.common.dictionary.service.impl.DictionaryTesterHelper;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.core.search.dto.SearchParamInfo;
import org.kuali.student.r2.common.search.dto.SearchRequestInfo;
import org.kuali.student.r2.core.search.dto.SearchResultCellInfo;
import org.kuali.student.r2.core.search.dto.SearchResultInfo;
import org.kuali.student.r2.core.search.dto.SearchResultRowInfo;
import org.kuali.student.r2.core.search.dto.SortDirection;
import org.kuali.student.r2.lum.clu.dto.AcademicSubjectOrgInfo;
import org.kuali.student.r2.lum.clu.dto.AccreditationInfo;
import org.kuali.student.r2.lum.clu.dto.AdminOrgInfo;
import org.kuali.student.r2.lum.clu.dto.AffiliatedOrgInfo;
import org.kuali.student.r2.lum.clu.dto.CluAccountingInfo;
import org.kuali.student.r2.lum.clu.dto.CluCluRelationInfo;
import org.kuali.student.r2.lum.clu.dto.CluCreditInfo;
import org.kuali.student.r2.lum.clu.dto.CluFeeInfo;
import org.kuali.student.r2.lum.clu.dto.CluFeeRecordInfo;
import org.kuali.student.r2.lum.clu.dto.CluIdentifierInfo;
import org.kuali.student.r2.lum.clu.dto.CluInfo;
import org.kuali.student.r2.lum.clu.dto.CluInstructorInfo;
import org.kuali.student.r2.lum.clu.dto.CluLoRelationInfo;
import org.kuali.student.r2.lum.clu.dto.CluPublicationInfo;
import org.kuali.student.r2.lum.clu.dto.CluResultInfo;
import org.kuali.student.r2.lum.clu.dto.CluSetInfo;
import org.kuali.student.r2.lum.clu.dto.CluSetTreeViewInfo;
import org.kuali.student.r2.lum.clu.dto.ExpenditureInfo;
import org.kuali.student.r2.lum.clu.dto.FeeInfo;
import org.kuali.student.r2.lum.clu.dto.FieldInfo;
import org.kuali.student.r2.lum.clu.dto.LuCodeInfo;
import org.kuali.student.r2.lum.clu.dto.LuDocRelationInfo;
import org.kuali.student.r2.lum.clu.dto.MembershipQueryInfo;
import org.kuali.student.r2.lum.clu.dto.ResultOptionInfo;
import org.kuali.student.r2.lum.clu.dto.RevenueInfo;
import org.kuali.student.r1.common.validation.dto.ValidationResultInfo;
import org.kuali.student.r1.common.validator.DefaultValidatorImpl;
import org.kuali.student.r1.common.validator.ServerDateParser;
import org.kuali.student.r1.core.statement.dto.ReqCompFieldInfo;
import org.kuali.student.r1.core.statement.dto.ReqComponentInfo;
import org.kuali.student.r1.core.statement.dto.StatementInfo;
import org.kuali.student.r1.core.statement.dto.StatementTreeViewInfo;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import static org.junit.Assert.*;

public class TestSearchDTOEqualXMLBeanDefinition {

	@Test
	public void testLoadStatementInfoDictionary() {
  System.out.println ("testing statement dictionary");
		Set<String> startingClasses = new LinkedHashSet();
		
		//startingClasses.add(SearchParamInfo.class.getName ());//NullPointerException
		//startingClasses.add(SearchRequestInfo.class.getName ());//NullPointerException
		//startingClasses.add(SearchResultCellInfo.class.getName ());//NullPointerException
		//startingClasses.add(SearchResultInfo.class.getName ());//NullPointerException
		//startingClasses.add(SearchResultRowInfo.class.getName ());//NullPointerException
		//startingClasses.add(SortDirection.class.getName ());//NullPointerException
		String contextFile = "ks-statement-dictionary-context";//Wrong xml, can't find any with above mentioned classes
		String outFile = "target/" + contextFile + ".txt";
		DictionaryDiscrepencyTesterHelper helper = new DictionaryDiscrepencyTesterHelper(outFile,
				startingClasses, contextFile + ".xml", false);
		helper.setPrintDescrepenciesOnly(true);
	 List<String> errors = helper.doTest ();
  if (errors.size () > 0)
  {
   fail ("failed dictionary validation:\n" + formatAsString (errors));
  }

 }

 private String formatAsString (List<String> errors)
 {
  int i = 0;
  StringBuilder builder = new StringBuilder ();
  for (String error : errors)
  {
   i ++;
   builder.append (i + ". " + error + "\n");
  }
  return builder.toString ();
 }

}
