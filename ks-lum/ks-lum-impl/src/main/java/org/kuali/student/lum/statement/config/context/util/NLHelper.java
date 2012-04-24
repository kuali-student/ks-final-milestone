package org.kuali.student.lum.statement.config.context.util;

import org.kuali.student.lum.lu.dto.CluInfo;

public class NLHelper {

	public static String getProperGrammar(Number number, String singularText, String pluralText) {
		return (number.intValue() == 1 ? singularText : pluralText);
	}
	
    public static String getProperGrammar(Number number, String singularText) {
        return (number.intValue() == 1 ? singularText : singularText + "s");
    }	

	public static String getProperGrammar(String number, String singularText, String pluralText) {
		return getProperGrammar(Integer.valueOf(number), singularText, pluralText);
	}
	
	public static String getProperGrammar(String number, String singularText) {
		return getProperGrammar(Integer.valueOf(number), singularText);
	}
	
	public static String getCluOrCluSetAsShortNames(CluInfo clu, NLCluSet cluSet) {
		return getCluOrCluSetAsShortNames(clu, cluSet, ",");
	}
	
	public static String getCluOrCluSetAsShortNames(CluInfo clu, NLCluSet cluSet, String separator) {
        if(clu != null) {
            return clu.getOfficialIdentifier().getShortName();
        }
        return cluSet.getCluSetAsShortName(separator);
    }

	public static String getCluOrCluSetAsLongNames(CluInfo clu, NLCluSet cluSet) {
		return getCluOrCluSetAsLongNames(clu, cluSet, ",");
	}
	
	public static String getCluOrCluSetAsLongNames(CluInfo clu, NLCluSet cluSet, String separator) {
        if(clu != null) {
            return clu.getOfficialIdentifier().getLongName();
        }
        return cluSet.getCluSetAsLongName(separator);
    }
}
