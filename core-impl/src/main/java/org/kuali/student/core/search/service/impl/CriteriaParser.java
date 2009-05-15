package org.kuali.student.core.search.service.impl;

import java.util.Map;

import org.kuali.student.core.search.dto.CriteriaInfo;
import org.kuali.student.core.search.dto.CriteriaSet;
import org.kuali.student.core.search.dto.Criterion;
import org.kuali.student.core.search.dto.SearchIndexedType;
import org.kuali.student.core.search.dto.TypeAttribute;

public class CriteriaParser {

	
	public static void main(String[] args){
		//Try to get select orginfo where (type="x" or type="y") and (shortname like "%a%" or shorname ="sdfasdf")
		Criterion typeEqX = new Criterion();
		typeEqX.setOperator("EQUALS");
		TypeAttribute attr1 = new TypeAttribute();
		attr1.setAlias("OrgTypeKey");
		attr1.setName("type.id");
		attr1.setParentType("orgInfo");
		typeEqX.setAttribute(attr1);
		typeEqX.setValue("kuali.org.Program");
		
		Criterion typeEqY = new Criterion();
		typeEqY.setOperator("EQUALS");
		TypeAttribute attr2 = new TypeAttribute();
		attr2.setAlias("OrgTypeKey");
		attr2.setName("type.id");
		attr2.setParentType("orgInfo");
		typeEqY.setAttribute(attr2);
		typeEqY.setValue("kuali.org.Division");
		
		
		Criterion typeLikeName1 = new Criterion();
		typeLikeName1.setOperator("LIKE");
		TypeAttribute attr3 = new TypeAttribute();
		attr3.setAlias("OrgShortName1");
		attr3.setName("shortName");
		attr3.setParentType("orgInfo");
		typeLikeName1.setAttribute(attr3);
		typeLikeName1.setValue("%a%");
		
		Criterion typeLikeName2 = new Criterion();
		typeLikeName2.setOperator("LIKE");
		TypeAttribute attr4 = new TypeAttribute();
		attr4.setAlias("OrgShortName2");
		attr4.setName("shortName");
		attr4.setParentType("orgInfo");
		typeLikeName2.setAttribute(attr4);
		typeLikeName2.setValue("%b%");
		
		CriteriaSet typeMatch= new CriteriaSet();
		typeMatch.setOperator("OR");
		typeMatch.getCriterion().add(typeEqX);
		typeMatch.getCriterion().add(typeEqY);

		CriteriaSet nameMatch= new CriteriaSet();
		nameMatch.setOperator("OR");
		nameMatch.getCriterion().add(typeLikeName1);
		nameMatch.getCriterion().add(typeLikeName2);

		CriteriaSet csroot= new CriteriaSet();
		csroot.setOperator("AND");
		csroot.getCriteria().add(typeMatch);		
		csroot.getCriteria().add(nameMatch);
		
		CriteriaInfo ci = new CriteriaInfo();
		ci.setCriteria(csroot);
		
		SearchIndexedType sit = new SearchIndexedType();
		sit.setName("orgInfo");
		ci.getTypes().add(sit);
		
		CriteriaParser cp = new CriteriaParser();
		CriteriaParseResult pr = cp.parseCriteriaInfo(ci);
		System.out.println(pr.getQueryString());
		System.out.println(pr.getBindings());
	}

	
	private CriteriaParseResult parseCriteriaInfo(CriteriaInfo ci) {


		String q = "SELECT ";
		for(SearchIndexedType sit:ci.getTypes()){
			q+=sit.getName();
		}
		q+=" FROM ";
		int i = 0;
		for(SearchIndexedType sit:ci.getTypes()){
			if(i>0){
				q+=", ";
			}
			q+=sit.getName();
			i++;
		}	
		
		q+=" WHERE ";
		CriteriaParseResult pr = new CriteriaParseResult();
		q+=parse(ci.getCriteria(), pr.getBindings());
		q+=parse(ci.getCriterion(), pr.getBindings());//TODO if the criteriaset is not null add the and or before the criterion
		
		pr.setQueryString(q);
		return pr;
	}

	private String parse(CriteriaSet criteriaSet,
			Map<String,Object> bindings) {
		if(criteriaSet==null){
			return "";
		}
		String s = "(";
		int i=0;
		for(CriteriaSet c:criteriaSet.getCriteria()){
			if(i>0){
				s+=" "+criteriaSet.getOperator()+" ";
			}
			s+=parse(c,bindings);
			i++;
		}
		i=0;
		for(Criterion c:criteriaSet.getCriterion()){
			if(i>0){
				s+=" "+criteriaSet.getOperator()+" ";
			}
			s+=parse(c,bindings);
			i++;
		}
		return s+")";
	}

	private String parse(Criterion c, Map<String,Object> bindings) {
		if(c==null){
			return "";
		}
		String s = c.getAttribute().getParentType()+"."+c.getAttribute().getName();
		s+=" "+translateOperator(c.getOperator());
		s+=" :_"+bindings.size(); 
		bindings.put("_"+bindings.size(), c.getValue());//TODO multiple values ie Select w where w.x IN('a','b','c')
		return s;
	}


	private String translateOperator(String operator) {
		if("EQUALS".equals(operator)){
			return "=";
		}
		if("LIKE".equals(operator)){
			return "LIKE";
		}
		if("AND".equals(operator)){
			return "AND";
		}
		if("OR".equals(operator)){
			return "OR";
		}

		return "";
	}
	

}
