/*
 * Copyright 2011 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 1.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl1.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.student.datadictinoary;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.kuali.rice.kns.datadictionary.AttributeDefinition;
import org.kuali.rice.kns.datadictionary.validation.constraint.BaseConstraint;
import org.kuali.rice.kns.datadictionary.validation.constraint.CaseConstraint;
import org.kuali.rice.kns.datadictionary.validation.constraint.CommonLookupParam;
import org.kuali.rice.kns.datadictionary.validation.constraint.LookupConstraint;
import org.kuali.rice.kns.datadictionary.validation.constraint.ValidCharactersConstraint;
import org.kuali.rice.kns.datadictionary.validation.constraint.WhenConstraint;


public class DictionaryFormatter {

    private StringBuilder builder = new StringBuilder(5000);
    private DataDictionaryObjectStructure os;
    private String rowSeperator = "\n";
    private String colSeperator = "|";
    private String name;
    private String className;
    private boolean processSubstructures = false;
    private int level;
    private Map<String, DataDictionaryObjectStructure> subStructuresToProcess =
            new LinkedHashMap();
    private Set<DataDictionaryObjectStructure> subStructuresAlreadyProcessed;

    public DictionaryFormatter(String name,
            String className,
            DataDictionaryObjectStructure os,
            Set<DataDictionaryObjectStructure> subStructuresAlreadyProcessed,
            int level,
            boolean processSubstructures) {
        this.name = name;
        this.className = className;
        this.os = os;
        this.subStructuresAlreadyProcessed = subStructuresAlreadyProcessed;
        this.level = level;
        this.processSubstructures = processSubstructures;
    }
    public static final String UNBOUNDED = "unbounded";

    public String getRowSeperator() {
        return rowSeperator;
    }

    public void setRowSeperator(String rowSeperator) {
        this.rowSeperator = rowSeperator;
    }

    public String getColSeparator() {
        return colSeperator;
    }

    public void setColSeparator(String separator) {
        this.colSeperator = separator;
    }

    private String pad(String str, int size) {
        StringBuilder padStr = new StringBuilder(size);
        padStr.append(str);
        while (padStr.length() < size) {
            padStr.append(' ');
        }
        return padStr.toString();
    }

    public String formatForWiki() {
        builder.append(rowSeperator);
//  builder.append ("======= start dump of object structure definition ========");
        builder.append(rowSeperator);
        builder.append("h" + level + ". " + calcNotSoSimpleName(name));
        builder.append("{anchor:" + name + "}");
        builder.append(rowSeperator);
        if (className != null) {
            builder.append("The corresponding java class for this dictionary object is "
                    + os.getFullClassName());
        }
        builder.append(rowSeperator);
        builder.append(colSeperator);
        builder.append(colSeperator);
        builder.append("Field");
        builder.append(colSeperator);
        builder.append(colSeperator);
        builder.append("Required?");
        builder.append(colSeperator);
        builder.append(colSeperator);
        builder.append("DataType");
        builder.append(colSeperator);
        builder.append(colSeperator);
        builder.append("Length");
        builder.append(colSeperator);
        builder.append(colSeperator);
        builder.append("Dynamic or Hidden");
        builder.append(colSeperator);
        builder.append(colSeperator);
        builder.append("Default");
        builder.append(colSeperator);
        builder.append(colSeperator);
        builder.append("Repeats?");
        builder.append(colSeperator);
        builder.append(colSeperator);
        builder.append("Valid Characters");
        builder.append(colSeperator);
        builder.append(colSeperator);
        builder.append("Lookup");
        builder.append(colSeperator);
        builder.append(colSeperator);
        builder.append("Cross Field");
        builder.append(colSeperator);
        builder.append(colSeperator);
        builder.append(rowSeperator);
        for (AttributeDefinition ad : getSortedFields()) {
            builder.append(colSeperator);
            builder.append(pad(ad.getName(), 30));
            builder.append(colSeperator);
            builder.append(pad(calcRequired(ad), 10));
            builder.append(colSeperator);
            builder.append(pad(calcDataType(ad), 25));
            builder.append(colSeperator);
            builder.append(pad(calcLength(ad), 15));
            builder.append(colSeperator);
            builder.append(pad(calcDynamic(ad), 7));
            builder.append(colSeperator);
            builder.append(pad(calcDefaultValue(ad), 15));
            builder.append(colSeperator);
            builder.append(calcRepeating(ad));
            builder.append(colSeperator);
            builder.append(calcValidCharsMinMax(ad));
            builder.append(colSeperator);
            builder.append(calcLookup(ad));
            builder.append(colSeperator);
            builder.append(calcCrossField(ad));
            builder.append(colSeperator);
            builder.append(rowSeperator);
        }
        List<String> discrepancies = null;
        if (className == null) {
            discrepancies = new ArrayList(1);
            discrepancies.add(
                    "There is no corresponding java class for this dictionary object structure");
        } else {
            discrepancies = new Dictionary2BeanComparer(className, os).compare();
        }
        if (discrepancies.size() > 0) {
            builder.append("h" + (level + 1) + ". " + discrepancies.size()
                    + " discrepancie(s) found in "
                    + calcSimpleName(name));
            builder.append(rowSeperator);
            builder.append(formatAsString(discrepancies));
            builder.append(rowSeperator);
        }

//  builder.append ("======= end dump of object structure definition ========");
        builder.append(rowSeperator);
        Set<DataDictionaryObjectStructure> subStructuresAlreadyProcessedBeforeProcessingSubStructures =
                new HashSet();
        subStructuresAlreadyProcessedBeforeProcessingSubStructures.addAll(
                subStructuresAlreadyProcessed);
        for (String subName : this.subStructuresToProcess.keySet()) {
            DataDictionaryObjectStructure subOs = this.subStructuresToProcess.get(subName);
            if (!subStructuresAlreadyProcessedBeforeProcessingSubStructures.contains(
                    subOs)) {
                this.subStructuresAlreadyProcessed.add(subOs);
//    System.out.println ("formatting substructure " + subName);
                Class<?> subClazz = getClass(subOs.getFullClassName());
                DictionaryFormatter formatter =
                        new DictionaryFormatter(subName, subOs.getFullClassName(),
                        subOs,
                        subStructuresAlreadyProcessed,
                        level + 1,
                        this.processSubstructures);
                builder.append(formatter.formatForWiki());
                builder.append(rowSeperator);
            }
        }

        return builder.toString();
    }

    private List<AttributeDefinition> getSortedFields() {
        List<AttributeDefinition> fields = os.getAttributes();
        Collections.sort(fields, new AttributeDefinitionNameComparator());
        return fields;
    }

    private static class AttributeDefinitionNameComparator implements Comparator<AttributeDefinition> {

        @Override
        public int compare(AttributeDefinition o1, AttributeDefinition o2) {
            return o1.getName().toLowerCase().compareTo(o2.getName().toLowerCase());
        }
    }

    private Class getClass(String className) {
        try {
            return Class.forName(className);
        } catch (ClassNotFoundException ex) {
            return null;
//   throw new IllegalArgumentException ("Could not find class for " + className);
        }
    }

    private String formatAsString(List<String> discrepancies) {
        int i = 0;
        StringBuilder builder = new StringBuilder();
        for (String discrep : discrepancies) {
            i++;
            builder.append(i + ". " + discrep + "\n");
        }
        return builder.toString();
    }

    private String calcDataType(AttributeDefinition ad) {
//        if (ad.getDataType().equals(DataType.COMPLEX)) {
//            if (ad.getDataObjectStructure() == null) {
//                throw new IllegalArgumentException(
//                        ad.getName() + " is complex but does not have a sub-structure defined");
//            }
//            Class subClazz = this.getClass(ad.getDataObjectStructure().getName());
//            String subStrucName = calcComplexSubStructureName(ad);
//            // process if explicity asking for substructures OR the field is a freestanding field
//            // so it won't be processed by just processing all of the DTO's and their sub-objects
//            if (this.processSubstructures || subClazz == null) {
//                if (!this.subStructuresAlreadyProcessed.contains(
//                        ad.getDataObjectStructure())) {
////     System.out.println ("Adding " + subStrucName + " to set to be processed");
//                    this.subStructuresToProcess.put(subStrucName, ad.getDataObjectStructure());
//                }
//            }
//            return "[" + calcNotSoSimpleName(subStrucName) + "|#" + subStrucName + "]";
//        }
        return ad.getDataType().toString();
    }

    private String calcDefaultValue(AttributeDefinition ad) {
//        if (ad.getDefaultValue() != null) {
//            return ad.getDefaultValue().toString();
//        }
        return " ";
    }

    private String calcDynamic(AttributeDefinition ad) {
//        if (ad.isDynamic()) {
//            return "dynamic";
//        }
        return " ";
    }

    private String calcComplexSubStructureName(AttributeDefinition ad) {
//        if (this.processSubstructures) {
//            return name + "." + ad.getName() + "." + calcSimpleName(
//                    ad.getDataObjectStructure().getName());
//        }
//        return calcSimpleName(ad.getDataObjectStructure().getName());
        return " ";
    }

    private String calcSimpleName(String name) {
        if (name.lastIndexOf(".") != -1) {
            name = name.substring(name.lastIndexOf(".") + 1);
        }
        return name;
    }

    private String calcNotSoSimpleName(String name) {
        if (name.lastIndexOf(".") == -1) {
            return name;
        }
        String simpleName = calcSimpleName(name);
        String fieldName = calcSimpleName(name.substring(0, name.length()
                - simpleName.length()
                - 1));
        return fieldName + "." + simpleName;
    }

    private String calcRequired(AttributeDefinition ad) {
        if (ad.getMaximumNumberOfElements() != null) {
            if (!ad.getMaximumNumberOfElements().equals(UNBOUNDED)) {
                if (ad.getMaximumNumberOfElements() == 0) {
                    return "Not allowed";
                }
            }
        }

        if (ad.getMinimumNumberOfElements() != null) {
            if (ad.getMinimumNumberOfElements() >= 1) {
                return "required";
            }
        }
        if (ad.isRequired() != null) {
            if (ad.isRequired()) {
                return "required";
            }
        }

        return " ";
//  return "optional";
    }
    private static final String LINK_TO_DEFINITIONS =
            "KULSTG:Formatted View of Base Dictionary#Valid Character Definitions";

    private String calcValidChars(AttributeDefinition ad) {
        if (ad.getValidCharactersConstraint() == null) {
            return " ";
        }
        return calcValidChars(ad.getValidCharactersConstraint());
    }

    private String calcValidChars(ValidCharactersConstraint cons) {
        String labelKey = cons.getLabelKey();
        if (labelKey == null) {
            labelKey = "validation.validChars";
        }
        String validChars = escapeWiki(cons.getValue());
        String descr = "[" + labelKey + "|" + LINK_TO_DEFINITIONS + "]" + "\\\\\n"
                + validChars;
        return descr;
    }

    private String escapeWiki(String str) {
        StringBuilder bldr = new StringBuilder(str.length());
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            switch (c) {
                case '{':
                case '}':
                case '[':
                case ']':
                case '|':
                    bldr.append('\\');
            }
            bldr.append(c);
        }
        return bldr.toString();
    }

    private String calcLookup(AttributeDefinition ad) {
        if (ad.getLookupDefinition() == null) {
            return " ";
        }
        return calcLookup(ad.getLookupDefinition());
    }

    private String calcLookup(LookupConstraint lc) {
        StringBuilder bldr = new StringBuilder();
        bldr.append(lc.getId());
//  this is the search description not the lookup description
//  builder.append (" - ");
//  builder.append (lc.getDesc ());
        String and = "";
        bldr.append("\\\\");
        bldr.append("\n");
        bldr.append("Implemented using search: ");
        String searchPage = calcWikiSearchPage(lc.getSearchTypeId());
        bldr.append("[" + lc.getSearchTypeId() + "|" + searchPage + "#"
                + lc.getSearchTypeId() + "]");
        List<CommonLookupParam> configuredParameters = filterConfiguredParams(
                lc.getParams());
        if (configuredParameters.size() > 0) {
            bldr.append("\\\\");
            bldr.append("\n");
            bldr.append(" where ");
            and = "";
            for (CommonLookupParam param : configuredParameters) {
                bldr.append(and);
                and = " and ";
                bldr.append(param.getName());
                bldr.append("=");
                if (param.getDefaultValueString() != null) {
                    bldr.append(param.getDefaultValueString());
                    continue;
                }
                if (param.getDefaultValueList() != null) {
                    String comma = "";
                    for (String defValue : param.getDefaultValueList()) {
                        bldr.append(comma);
                        comma = ", ";
                        bldr.append(defValue);
                    }
                }
            }
        }
        return bldr.toString();
    }

    private String calcValidCharsMinMax(AttributeDefinition ad) {
        String validChars = calcValidChars(ad);
        String minMax = calcMinMax(ad);
        String and = " and ";
        if (validChars.trim().equals("")) {
            return minMax;
        }
        if (minMax.trim().equals("")) {
            return validChars;
        }
        return validChars + "\\\\\n" + minMax;
    }

    private String calcMinMax(AttributeDefinition ad) {
        if (ad.getExclusiveMin() == null) {
            if (ad.getInclusiveMax() == null) {
                return " ";
            }
            return "Must be <= " + ad.getInclusiveMax();
        }
        if (ad.getInclusiveMax() == null) {
            return "Must be > " + ad.getExclusiveMin();
        }
        return "Must be > " + ad.getExclusiveMin() + " and < "
                + ad.getInclusiveMax();
    }
    private static final String PAGE_PREFIX = "Formatted View of ";
    private static final String PAGE_SUFFIX = " Searches";

    private String calcWikiSearchPage(String searchType) {
        return PAGE_PREFIX + calcWikigPageAbbrev(searchType) + PAGE_SUFFIX;
    }

    private String calcWikigPageAbbrev(String searchType) {
        if (searchType == null) {
            return null;
        }
        if (searchType.equals("enumeration.management.search")) {
            return "EM";
        }
        if (searchType.startsWith("lu.")) {
            return "LU";
        }
        if (searchType.startsWith("cluset.")) {
            return "LU";
        }
        if (searchType.startsWith("lo.")) {
            return "LO";
        }
        if (searchType.startsWith("lrc.")) {
            return "LRC";
        }
        if (searchType.startsWith("comment.")) {
            return "Comment";
        }
        if (searchType.startsWith("org.")) {
            return "Organization";
        }
        if (searchType.startsWith("atp.")) {
            return "ATP";
        }
        throw new IllegalArgumentException("Unknown type of search: " + searchType);
    }

    private List<CommonLookupParam> filterConfiguredParams(
            List<CommonLookupParam> params) {
        List list = new ArrayList();
        if (params == null) {
            return list;
        }
        if (params.size() == 0) {
            return list;
        }
        for (CommonLookupParam param : params) {
            if (param.getDefaultValueString() != null) {
                list.add(param);
                continue;
            }
            if (param.getDefaultValueList() != null) {
                list.add(param);
            }
        }
        return list;
    }

    private String calcRepeating(AttributeDefinition ad) {
        if (ad.getMaximumNumberOfElements() == null) {
            return "???";
        }
        if (ad.getMaximumNumberOfElements().equals(UNBOUNDED)) {
            if (ad.getMinimumNumberOfElements() != null && ad.getMinimumNumberOfElements() > 1) {
                return "repeating: minimum " + ad.getMinimumNumberOfElements() + " times";
            }
            return "repeating: unlimited";
        }
        if (ad.getMaximumNumberOfElements() == 0) {
            return "NOT USED";
        }
        if (ad.getMaximumNumberOfElements() == 1) {
            return " ";
//   return "single";
        }

        if (ad.getMinimumNumberOfElements() != null) {
            if (ad.getMinimumNumberOfElements() > 1) {
                return "repeating: " + ad.getMinimumNumberOfElements() + " to " + ad.getMaximumNumberOfElements()
                        + " times";
            }
        }
        return "repeating: maximum " + ad.getMaximumNumberOfElements() + " times";
    }

    private String calcLength(AttributeDefinition ad) {
        if (ad.getMaxLength() != null) {
            if (ad.getMinLength() != null && ad.getMinLength() != 0) {
                if (ad.getMaxLength() == ad.getMinLength()) {
                    return ("(must be " + ad.getMaxLength() + ")");
                }
                return "(" + ad.getMinLength() + " to " + ad.getMaxLength() + ")";
            }
            return "(up to " + ad.getMaxLength() + ")";
        }
        if (ad.getMinLength() != null) {
            return "(over " + ad.getMinLength() + ")";
        }
        return " ";
    }

    private String calcCrossField(AttributeDefinition ad) {
        StringBuilder b = new StringBuilder();
        String semicolon = "";
        String cfr = calcCrossFieldRequire(ad);
        if (cfr != null) {
            b.append(semicolon);
            semicolon = "; ";
            b.append(cfr);
        }
        String cfw = calcCrossFieldWhen(ad);
        if (cfw != null) {
            b.append(semicolon);
            semicolon = "; ";
            b.append(cfw);
        }
        if (b.length() == 0) {
            return " ";
        }
        return b.toString();
    }

    private String calcCrossFieldRequire(AttributeDefinition ad) {
//        if (ad.getRequireConstraint() == null) {
//            return null;
//        }
//        if (ad.getRequireConstraint().size() == 0) {
//            return null;
//        }
        StringBuilder b = new StringBuilder();
//        String comma = "";
//        b.append("if not empty then ");
//        for (RequiredConstraint rc : ad.getRequireConstraint()) {
//            b.append(comma);
//            comma = ", ";
//            b.append(rc.getFieldPath());
//        }
//        if (ad.getRequireConstraint().size() == 1) {
//            b.append(" is");
//        } else {
//            b.append(" are");
//        }
//        b.append(" also required");
        return b.toString();
    }

    private String calcCrossFieldWhen(AttributeDefinition ad) {
        if (ad.getCaseConstraint() == null) {
            return null;
        }
        StringBuilder b = new StringBuilder();
        CaseConstraint cc = ad.getCaseConstraint();
        for (WhenConstraint wc : cc.getWhenConstraint()) {
            b.append("\\\\");
            b.append("\n");
            b.append("when ");
            b.append(cc.getFieldPath());
            b.append(" ");
            if (!cc.isCaseSensitive()) {
                b.append("ignoring case ");
            }
            b.append(cc.getOperator());
            b.append(" ");

            b.append("\\\\");
            b.append("\n");
            String comma = "";
            for (Object value : wc.getValues()) {
                b.append(comma);
                comma = " or ";
                b.append(asString(value));
            }
            b.append("\\\\");
            b.append("\n");
            b.append("then override constraint:"
                    + calcOverride(ad, (BaseConstraint) wc.getConstraint()));
        }
        return b.toString();
    }

    private String calcOverride(AttributeDefinition ad, BaseConstraint cons) {
        StringBuilder b = new StringBuilder();
//        b.append(calcOverride("serviceSide", ad.(),
//                cons.getApplyClientSide()));
//        b.append(calcOverride("exclusiveMin", ad.getExclusiveMin(),
//                cons.getExclusiveMin()));
//        b.append(calcOverride("inclusiveMax", ad.getInclusiveMax(),
//                cons.getInclusiveMax()));
//        String minOccursMessage = calcOverride("minOccurs", ad.getMinimumNumberOfElements(),
//                cons.getMinimumNumberOfElements());
//        if (!minOccursMessage.trim().equals("")) {
//            if (cons.getMinimumNumberOfElements() != null && cons.getMinimumNumberOfElements() == 1) {
//                minOccursMessage = " REQUIRED";
//            }
//        }
//        b.append(minOccursMessage);
//        b.append(calcOverride("validchars", ad.getValidCharactersConstraint(),
//                cons.getValidCharactersConstraint()));
//        b.append(calcOverride("lookup", ad.getLookupDefinition(),
//                cons.getLookupDefinition()));
        //TODO: other more complex constraints
        return b.toString();
    }

    private String calcOverride(String attribute, LookupConstraint val1,
            LookupConstraint val2) {
        if (val1 == val2) {
            return "";
        }
        if (val1 == null && val2 != null) {
            return " add lookup " + this.calcLookup(val2);
        }
        if (val1 != null && val2 == null) {
            return " remove lookup constraint";
        }
        return " change lookup to " + calcLookup(val2);
    }

    private String calcOverride(String attribute, ValidCharactersConstraint val1,
            ValidCharactersConstraint val2) {
        if (val1 == val2) {
            return "";
        }
        if (val1 == null && val2 != null) {
            return " add validchars " + calcValidChars(val2);
        }
        if (val1 != null && val2 == null) {
            return " remove validchars constraint";
        }
        return " change validchars to " + calcValidChars(val2);
    }

    private String calcOverride(String attribute, boolean val1, boolean val2) {
        if (val1 == val2) {
            return "";
        }
        return " " + attribute + "=" + val2;
    }

    private String calcOverride(String attribute, String val1, String val2) {
        if (val1 == null && val2 == null) {
            return "";
        }
        if (val1 == val2) {
            return "";
        }
        if (val1 == null) {
            return " " + attribute + "=" + val2;
        }
        if (val1.equals(val2)) {
            return "";
        }
        return " " + attribute + "=" + val2;
    }

    private String calcOverride(String attribute, Object val1, Object val2) {
        if (val1 == null && val2 == null) {
            return "";
        }
        if (val1 == val2) {
            return "";
        }
        if (val1 == null) {
            return " " + attribute + "=" + val2;
        }
        if (val1.equals(val2)) {
            return "";
        }
        return " " + attribute + "=" + asString(val2);
    }

    private String asString(Object value) {
        if (value == null) {
            return "null";
        }
        if (value instanceof String) {
            return (String) value;
        }
        return value.toString();
    }
}

