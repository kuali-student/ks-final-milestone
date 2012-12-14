package org.kuali.student.myplan.plan.util;

import org.apache.log4j.Logger;
import org.kuali.rice.core.api.util.ConcreteKeyValue;
import org.kuali.rice.core.api.util.KeyValue;
import org.kuali.rice.krad.keyvalues.KeyValuesBase;

import java.util.ArrayList;
import java.util.List;

/**
 * Assembles a list of published terms.
 */
public class TermsListBuilder extends KeyValuesBase {

    private final Logger logger = Logger.getLogger(TermsListBuilder.class);

    private static int futureTermsCount = 6;

    private static String atpTerm1 = "1";
    private static String atpTerm2 = "2";
    private static String atpTerm3 = "3";
    private static String atpTerm4 = "4";

    /**
     * Build and returns the list of available terms.
     *
     * @return A List of available terms as KeyValue items.
     */
    @Override
    public List<KeyValue> getKeyValues() {

        List<KeyValue> keyValues = new ArrayList<KeyValue>();
        String minTerm = AtpHelper.getCurrentAtpId();
        String[] minTerms = AtpHelper.atpIdToTermAndYear(minTerm);
        int maximumYear = Integer.parseInt(minTerms[1]) + futureTermsCount;
        int maximumQuarter = Integer.parseInt(minTerms[0]);
        if (maximumQuarter == 4) {
            maximumQuarter = 3;
        } else {
            maximumQuarter = maximumQuarter++;
        }
        String maxTerm = AtpHelper.getAtpFromNumTermAndYear(String.valueOf(maximumQuarter), String.valueOf(maximumYear));
        String[] maxTerms = AtpHelper.atpIdToTermAndYear(maxTerm);
        int minYear = Integer.parseInt(minTerms[1]);
        String term1 = "";
        String term2 = "";
        String term3 = "";
        String term4 = "";
        for (int i = 0; !term4.equalsIgnoreCase(maxTerm); i++) {
            term1 = AtpHelper.getAtpFromNumTermAndYear(atpTerm4, String.valueOf(minYear));
            keyValues.add(new ConcreteKeyValue(term1, AtpHelper.atpIdToTermName(term1)));
            minYear++;
            term2 = AtpHelper.getAtpFromNumTermAndYear(atpTerm1, String.valueOf(minYear));
            keyValues.add(new ConcreteKeyValue(term2, AtpHelper.atpIdToTermName(term2)));
            term3 = AtpHelper.getAtpFromNumTermAndYear(atpTerm2, String.valueOf(minYear));
            keyValues.add(new ConcreteKeyValue(term3, AtpHelper.atpIdToTermName(term3)));
            term4 = AtpHelper.getAtpFromNumTermAndYear(atpTerm3, String.valueOf(minYear));
            keyValues.add(new ConcreteKeyValue(term4, AtpHelper.atpIdToTermName(term4)));
        }
        return keyValues;
    }

}

