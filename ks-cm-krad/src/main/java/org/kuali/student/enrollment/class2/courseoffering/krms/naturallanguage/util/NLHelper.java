package org.kuali.student.enrollment.class2.courseoffering.krms.naturallanguage.util;

import org.kuali.student.r2.lum.clu.dto.CluInfo;


public class NLHelper {

	private NLHelper() {
    }

    public static String getProperGrammar(Number number, String singularText, String pluralText) {
		return (number.intValue() == 1 ? singularText : pluralText);
	}
	
    public static String getProperGrammar(Number number, String singularText) {
        if(singularText.charAt(singularText.length()-1) == 's') {
            return  singularText;
        }
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
