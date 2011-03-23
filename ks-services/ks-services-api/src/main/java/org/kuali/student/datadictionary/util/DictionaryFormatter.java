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
package org.kuali.student.datadictionary.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import org.kuali.rice.kns.datadictionary.AttributeDefinition;
import org.kuali.rice.kns.datadictionary.control.ControlDefinition;
import org.kuali.rice.kns.datadictionary.validation.constraint.BaseConstraint;
import org.kuali.rice.kns.datadictionary.validation.constraint.CaseConstraint;
import org.kuali.rice.kns.datadictionary.validation.constraint.CommonLookupParam;
import org.kuali.rice.kns.datadictionary.validation.constraint.LookupConstraint;
import org.kuali.rice.kns.datadictionary.validation.constraint.ValidCharactersConstraint;
import org.kuali.rice.kns.datadictionary.validation.constraint.WhenConstraint;
import org.kuali.student.datadictionary.DataDictionaryObjectStructure;

public class DictionaryFormatter {

    private DataDictionaryObjectStructure os;
    private String projectUrl;
    private String dictFileName;
    private String outputFileName;

    public DictionaryFormatter(DataDictionaryObjectStructure os, String projectUrl, String dictFileName, String outputFileName) {
        this.os = os;
        this.projectUrl = projectUrl;
        this.dictFileName = dictFileName;
        this.outputFileName = outputFileName;
    }

    public void formatForHtml() {
        File file = new File(this.outputFileName);
        OutputStream outputStream;
        try {
            outputStream = new FileOutputStream(file, false);
        } catch (FileNotFoundException ex) {
            throw new IllegalArgumentException(ex);
        }
        PrintStream out = new PrintStream(outputStream);
        writeHeader(out);
        writeBody(out);
        writeFooter(out);
        out.close();
    }

    private void writeHeader(PrintStream out) {
        out.println("<html>");
        out.println("<head>");
        writeTag(out, "title", dictFileName);
        out.println("</head>");
        out.println("<body bgcolor=\"#ffffff\" topmargin=0 marginheight=0>");
    }

    private void writeFooter(PrintStream out) {
        out.println("</body>");
        out.println("</html>");
    }

    private void writeBody(PrintStream out) {
        out.println("(!) This page was automatically generated on " + new Date());
//  builder.append ("======= start dump of object structure definition ========");
        out.println("<h1>" + os.getEntryClass().getSimpleName() + "</h1>");
        out.print("This is a formatted view of ");
        writeLink(out, projectUrl + "/" + this.dictFileName, this.dictFileName);
        List<String> discrepancies = new Dictionary2BeanComparer(os.getFullClassName(), os).compare();
        if (discrepancies.size() > 0) {
            out.println("<br>");
            out.println("<b>" + discrepancies.size() + " discrepancie(s) found" + "</b>");
            out.println("<ol>");
            for (String discrepancy : discrepancies) {
                out.println("<li>" + discrepancy);
            }
            out.println("</ol>");
        }
        out.println("<table border=1>");
        out.println("<tr bgcolor=lightblue>");
        out.println("<th>");
        out.println("Field");
        out.println("</th>");
        out.println("<th>");
        out.println("Required?");
        out.println("</th>");
        out.println("<th>");
        out.println("DataType");
        out.println("</th>");
        out.println("<th>");
        out.println("Length");
        out.println("</th>");
        out.println("<th>");
        out.println("Dynamic or Hidden");
        out.println("</th>");
        out.println("<th>");
        out.println("Default");
        out.println("</th>");
        out.println("<th>");
        out.println("Repeats?");
        out.println("</th>");
        out.println("<th>");
        out.println("Valid Characters");
        out.println("</th>");
        out.println("<th>");
        out.println("Lookup");
        out.println("</th>");
        out.println("<th>");
        out.println("Cross Field");
        out.println("</th>");
        out.println("<th>");
        out.println("Default Widget");
        out.println("</th>");
        out.println("</tr>");
//        for (AttributeDefinition ad : getSortedFields()) {
        for (AttributeDefinition ad : os.getAttributes()) {
            out.println("<tr>");
            out.println("<td>");
            out.println(nbsp(ad.getName()));
            out.println("</td>");
            out.println("<td>");
            out.println(nbsp(calcRequired(ad)));
            out.println("</td>");
            out.println("<td>");
            out.println(nbsp(calcDataType(ad)));
            out.println("</td>");
            out.println("<td>");
            out.println(nbsp(calcLength(ad)));
            out.println("</td>");
            out.println("<td>");
            out.println(nbsp(calcDynamic(ad)));
            out.println("</td>");
            out.println("<td>");
            out.println(nbsp(calcDefaultValue(ad)));
            out.println("</td>");
            out.println("<td>");
            out.println(nbsp(calcRepeating(ad)));
            out.println("</td>");
            out.println("<td>");
            out.println(nbsp(calcValidCharsMinMax(ad)));
            out.println("</td>");
            out.println("<td>");
            out.println(nbsp(calcLookup(ad)));
            out.println("</td>");
            out.println("<td>");
            out.println(nbsp(calcCrossField(ad)));
            out.println("</td>");
            out.println("<td>");
            out.println(nbsp(calcWidget(ad)));
            out.println("</td>");
            out.println("</tr>");
        }
        out.println("</table>");
        return;
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
        if (ad.isRequired() != null) {
            if (ad.isRequired()) {
                return "required";
            }
        }
        if (ad.getMaximumNumberOfElements() != null) {
            if (ad.getMaximumNumberOfElements().intValue() == 0) {
                return "Not allowed";
            }
        }

        if (ad.getMinimumNumberOfElements() != null) {
            if (ad.getMinimumNumberOfElements().intValue() >= 1) {
                return "required";
            }
        }
        return " ";
//  return "optional";
    }

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
        String validChars = escapeXML(cons.getValue());
        String descr = labelKey + "<br>" + validChars;
        return descr;
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
        bldr.append("<br>");
        bldr.append("\n");
        bldr.append("Implemented using search: ");
        String searchPage = calcWikiSearchPage(lc.getSearchTypeId());
        bldr.append("[" + lc.getSearchTypeId() + "|" + searchPage + "#"
                + lc.getSearchTypeId() + "]");
        List<CommonLookupParam> configuredParameters = filterConfiguredParams(
                lc.getParams());
        if (configuredParameters.size() > 0) {
            bldr.append("<br>");
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
        return validChars + "<br>\n" + minMax;
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
        if (ad.getMaximumNumberOfElements().intValue() == DictionaryConstants.UNBOUNDED) {
            if (ad.getMinimumNumberOfElements() != null && ad.getMinimumNumberOfElements() > 1) {
                return "repeating: minimum " + ad.getMinimumNumberOfElements() + " times";
            }
            return "repeating: unlimited";
        }
        if (ad.getMaximumNumberOfElements().intValue() == 0) {
            return "NOT USED";
        }
        if (ad.getMaximumNumberOfElements().intValue() == 1) {
            return " ";
//   return "single";
        }

        if (ad.getMinimumNumberOfElements() != null) {
            if (ad.getMinimumNumberOfElements().intValue() > 1) {
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
                    return ("must be " + ad.getMaxLength());
                }
                return ad.getMinLength() + " to " + ad.getMaxLength();
            }
            return "up to " + ad.getMaxLength();
        }
        if (ad.getMinLength() != null) {
            return "at least " + ad.getMinLength();
        }
        return " ";
    }

    private String calcWidget(AttributeDefinition ad) {
        ControlDefinition control = ad.getControl ();
        if (control == null) {
            return " ";
        }
        return control.getClass ().getSimpleName();
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

    private String nbsp(String str) {
        if (str == null) {
            return "&nbsp;";
        }
        if (str.trim().isEmpty()) {
            return "&nbsp;";
        }
        return str;
    }

    public void writeTag(PrintStream out, String tag, String value) {
        writeTag(out, tag, null, value);
    }

    public void writeTag(PrintStream out, String tag, String modifiers, String value) {
        if (value == null) {
            return;
        }
        if (value.equals("")) {
            return;
        }
        out.print("<" + tag);
        if (modifiers != null && !modifiers.isEmpty()) {
            out.print(" " + modifiers);
        }
        out.print(">");
        out.print(escapeXML(value));
        out.print("</" + tag + ">");
        out.println("");
    }

    public String escapeXML(String s) {
        StringBuffer sb = new StringBuffer();
        int n = s.length();
        for (int i = 0; i < n; i++) {
            // http://www.hdfgroup.org/HDF5/XML/xml_escape_chars.htm
            char c = s.charAt(i);
            switch (c) {
                case '"':
                    sb.append("&quot;");
                    break;
                case '\'':
                    sb.append("&apos;");
                    break;
                case '&':
                    sb.append("&amp;");
                    break;
                case '<':
                    sb.append("&lt;");
                    break;
                case '>':
                    sb.append("&gt;");
                    break;
                //case ' ': sb.append("&nbsp;");break;\
                default:
                    sb.append(c);
                    break;
            }
        }
        return sb.toString();
    }

    private void writeLink(PrintStream out, String url, String value) {
        out.print("<a href=\"" + url + "\">" + value + "</a>");
    }
}

