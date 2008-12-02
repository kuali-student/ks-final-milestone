package org.kuali.student.rules.internal.common.utils;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.xml.datatype.XMLGregorianCalendar;

import org.apache.xerces.jaxp.datatype.DatatypeFactoryImpl;
import org.kuali.student.rules.internal.common.entity.RuleElementType;
import org.kuali.student.rules.rulemanagement.dto.BusinessRuleInfoDTO;
import org.kuali.student.rules.rulemanagement.dto.RuleElementDTO;
import org.kuali.student.rules.rulemanagement.dto.RulePropositionDTO;

public class BusinessRuleUtil {

    static final String COMPOSITION_IS_VALID_MESSAGE = "Composition is valid";
    public static final char PROPOSITION_PREFIX = 'P';
    //public static final String CALENDAR_DATE_FORMAT = "yyyy.MM.dd-HH.mm.ss.SSS";
    public static final String ISO_TIMESTAMP_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSSZ";
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat(ISO_TIMESTAMP_FORMAT);

    /*
     * Validates rule composed of propositions e.g. P1, e.g. (P1), e.g. P1 OR P2 AND P3, e.g. (P1 AND P2 OR P3) etc.
     * 
     * @param text - rule text
     * @param identifiers - currently defined identifiers
     * @return validation message
     * @throws TBD
     * 
     */
    public static String validateRuleComposition(String text, Set<Integer> identifiers) { // TODO with Antler?

        String message;

        // we need at least one proposition
        if (text.trim().isEmpty()) {
            return "Enter at least one Proposition";
        }

        // validate rule composition
        try {
            message = validateCompositionFormat(text, false);
        } catch (IllegalRuleFormatException e) {
            return e.getMessage();
        }

        // check that all propositions are defined
        int propID;
        String[] tokens = text.split("[ ()]");
        ArrayList<Integer> listedPropositionIds = new ArrayList<Integer>();
        for (String token : tokens) {
            token = token.trim();
            // System.out.println("Validate Token read:" + token);
            if (!token.isEmpty() && ((token.charAt(0) == PROPOSITION_PREFIX) || (token.charAt(0) == PROPOSITION_PREFIX))) {

                try {
                    propID = getPropositionID(token);
                } catch (IllegalRuleFormatException e) {
                    // TODO
                    return e.getMessage();
                }
                listedPropositionIds.add(propID);

                if (!identifiers.contains(propID)) {
                    return "Unknown Proposition: " + token;
                }
            }
        }

        // check that all defined propositions were used; if not, issue a warning
        StringBuffer missingProps = new StringBuffer();
        for (Integer id : identifiers) {
            if (!listedPropositionIds.contains(id)) {
                missingProps.append(PROPOSITION_PREFIX + "" + id + " ");
            }
        }

        if (missingProps.length() > 0) {
            return "Warning: Unused Propositions - " + missingProps;
        }

        // check that all elements are valid i.e. OR, AND etc.
        // TODO

        return (message.isEmpty() ? COMPOSITION_IS_VALID_MESSAGE : message);
    }

    /*
     * Recursive parsing of a rule composed of propositions e.g. P1, e.g. (P1), e.g. P1 OR P2 AND P3, e.g. (P1 AND P2 OR P3) etc.
     * 
     * @param text - rule text
     * @param findClosingBracket - whether the recusion occured where we entered in bracket parsing mode
     * @return rule text left to be parsed
     * @throws IllegalRuleFormatException     
     */
    private static String validateCompositionFormat(String text, boolean findClosingBracket) throws IllegalRuleFormatException {
        // TODO our own exception here?

        String nextToken;
        boolean logicalOperatorFound = false;

        // first we expect P1 or (P1
        while ((nextToken = getNextTokenFromComposition(text)) != null) {

            text = text.trim();

            // first handle any opening bracket
            if (nextToken.equals("(")) {
                text = text.substring(1).trim();
                // we expect Proposition P1 or '(P1'
                text = validateCompositionFormat(text, true).trim();
                if (text.isEmpty()) {
                    throw new IllegalRuleFormatException("Expected ')' but found nothing.");
                }
                if (text.charAt(0) != ')') {
                    throw new IllegalRuleFormatException("Expected ')' but found '" + text.charAt(0) + "' in '" + firstFiveChars(text) + "...'");
                }
                text = text.substring(1); // remove the closing bracket
                logicalOperatorFound = false;

            } else { // now we expect Px

                if (text.startsWith("AND")) {
                    throw new IllegalRuleFormatException("Expected '" + PROPOSITION_PREFIX + "x' but found 'AND' in '" + firstFiveChars(text) + "...'");
                } else if (text.startsWith("OR")) {
                    throw new IllegalRuleFormatException("Expected '" + PROPOSITION_PREFIX + "x' but found 'OR' in '" + firstFiveChars(text) + "...'");
                } else if (text.startsWith(")")) {
                    throw new IllegalRuleFormatException("Expected '" + PROPOSITION_PREFIX + "x' or '(" + PROPOSITION_PREFIX + "x' but found ')' in '" + firstFiveChars(text) + "...'");
                }

                // (after brackets) handle Proposition or combination of Propositions
                int propID = getPropositionID(nextToken); // ensure we can get valid Proposition ID
                text = text.substring(new Integer(propID).toString().length() + 1).trim();
            }

            text = text.trim();

            // next we expect one of the following: end of the text, OR, AND, )
            if (text.isEmpty()) {
                return "";
            } else if (text.charAt(0) == ')') {
                if (findClosingBracket) {
                    return text; // process closing bracket in the caller
                }
                throw new IllegalRuleFormatException("Found ')' without opening bracket.");
            } else if (!text.startsWith("AND") && !text.startsWith("OR")) {
                throw new IllegalRuleFormatException("Expected 'AND' or 'OR' but found '" + firstFiveChars(text) + "...'");
            }

            // we found another logical operator but we don't want P1 AND P2 OR P3 but rather (P1 AND P2) OR P3
            if (logicalOperatorFound) {
                throw new IllegalRuleFormatException("Found ambiguous proposition composition with multiple logical operators not grouped by brackets. Please add brackets: '" + firstFiveChars(text) + "...'");
            }

            logicalOperatorFound = true;
            text = text.substring((text.startsWith("AND") ? 3 : 2)).trim();

            // now we expect again P1 or (P1 or P1) so loop
        }

        throw new IllegalRuleFormatException("Expected '" + PROPOSITION_PREFIX + "x' or '(" + PROPOSITION_PREFIX + "x' but found nothing.");
    }

    /*
     * Retrieves next token from rule composition e.g. Px, (, ), OR, AND
     * 
     * @param composition - business rule composition e.g. (P1 AND P2) OR P3
     * @param definedPropositions - list of proposition details with sequence numbers e.g. P1
     * @return list of business rule types or NULL if no token found
     * @throws OperationFailedException
     * 
     */
    private static String getNextTokenFromComposition(String partialComposition) throws IllegalRuleFormatException {
        String nextToken = "?";
        // System.out.println("get next token:" + partialComposition);
        partialComposition = partialComposition.trim().toUpperCase();
        if (partialComposition.isEmpty()) {
            return null;
        }

        // check for brackets, OR, AND
        if ((partialComposition.charAt(0) == '(') || (partialComposition.charAt(0) == ')')) {
            nextToken = partialComposition.substring(0, 1);
        } else if (partialComposition.startsWith("OR")) {
            nextToken = partialComposition.substring(0, 2);
        } else if (partialComposition.startsWith("AND")) {
            nextToken = partialComposition.substring(0, 3);
        } else if (partialComposition.charAt(0) == PROPOSITION_PREFIX) {
            nextToken = PROPOSITION_PREFIX + Integer.toString(getPropositionID(partialComposition));
        } else {
            String[] tokens = partialComposition.split(" ");
            nextToken = tokens[0];

        }

        // System.out.println("Next token:'" + nextToken + "'");
        return nextToken;
    }

    /*
     * Retrieve up to first five characters of a String
     * 
     * @param text to retrieve chars from
     * @return up to the first five characters 
     * 
     */
    private static String firstFiveChars(String text) {
        if (text.isEmpty())
            return "";
        int maxLen = (text.length() > 5 ? 5 : text.length());
        return text.substring(0, maxLen);
    }

    /*
     * Get Proposition ID from a text that starts with proposition in format 'Px' or 'px' e.g. P1
     * 
     * @param text - rule text
     * @param identifiers - currently defined identifiers
     * @return ID 
     * @throws IllegalRuleFormatException if format of the proposition ID not valid
     * 
     */
    private static int getPropositionID(String text) throws IllegalRuleFormatException { // TODO our own exception here?

        if (text == null) {
            throw new IllegalRuleFormatException("Proposition ID is null.");
        }

        text = text.trim().toUpperCase();
        if (text.isEmpty() || (text.charAt(0) != PROPOSITION_PREFIX)) {
            throw new IllegalRuleFormatException("Invalid Proposition format. Expected '" + PROPOSITION_PREFIX + "' but found: '" + text + "'");
        }

        String[] tokens = text.split("[^0-9]");
        if ((tokens.length == 1)) {
            throw new IllegalRuleFormatException("Proposition is missing ID number: '" + text + "'");
        }

        Integer propID;
        try {
            propID = new Integer(tokens[1]);
        } catch (NumberFormatException e) {
            throw new IllegalRuleFormatException("Invalid Proposition ID: '" + tokens[1] + "'");
        }

        return propID;
    }

    /**
     * Generates a function string from a business rule DTO
     * 
     * @param rule
     *            business rule whose functional string representation is required
     */
    public static String createFunctionalString(BusinessRuleInfoDTO rule) {

        Collection<RuleElementDTO> ruleElements = rule.getRuleElementList();

        int counter = 1;
        StringBuilder functionString = new StringBuilder();

        if(ruleElements == null) {
		    return functionString.toString().trim();
		}
        
        // step through rule elements and create a function string
        for (RuleElementDTO ruleElement : ruleElements) {
            
            RuleElementType type = null;
            
            if(RuleElementType.LPAREN.getName().equals( ruleElement.getOperation() )) {
                type = RuleElementType.LPAREN;
            } else if(RuleElementType.RPAREN.getName().equals( ruleElement.getOperation() )) {
                type = RuleElementType.RPAREN;
            } else {
                type = RuleElementType.valueOf(ruleElement.getOperation()); 
            }
            
            switch (type) {
                case AND:
                    functionString.append(" " + RuleElementType.AND.getName() + " ");
                    break;
                case LPAREN:
                    functionString.append(RuleElementType.LPAREN.getName());
                    break;
                case NOT:
                    functionString.append(RuleElementType.NOT.getName());
                    break;
                case OR:
                    functionString.append(" " + RuleElementType.OR.getName() + " ");
                    break;
                case PROPOSITION:
                    functionString.append(PROPOSITION_PREFIX + String.valueOf(counter));
                    counter++;
                    break;
                case RPAREN:
                    functionString.append(RuleElementType.RPAREN.getName());
                    break;
                default:
                    functionString.append("(unknown)");
            }
        }
        return functionString.toString().trim();
    }

    /**
     * This method returns the functional string with AND as * and OR as +
     * 
     * @param rule
     * @return
     */
    public static String createAdjustedRuleFunctionString(BusinessRuleInfoDTO rule) {
        String functionString = createFunctionalString(rule);
        functionString = functionString.replace("AND", "*");
        functionString = functionString.replace("OR", "+");
        return functionString;
    }

    /**
     * This method generates a map of proposition name with proposition for a given business rule
     * 
     * @param rule
     *            business rule used to generate map
     * @return returns map of name and proposition
     */
    public static Map<String, RulePropositionDTO> getRulePropositions(BusinessRuleInfoDTO rule) {

        Map<String, RulePropositionDTO> propositionMap = new HashMap<String, RulePropositionDTO>();
        Collection<RuleElementDTO> ruleElements = rule.getRuleElementList();

        if (ruleElements == null) {
        	return null;
        }
        
        int counter = 1;
        for (RuleElementDTO ruleElement : ruleElements) {
            if (RuleElementType.PROPOSITION.toString().equals(ruleElement.getOperation())) {
                propositionMap.put(PROPOSITION_PREFIX + String.valueOf(counter), ruleElement.getRuleProposition());
                counter++;
            }
        }
        return propositionMap;
    }

    public static Object convertToDataType(final Object value) {
    	Class<?> clazz = value.getClass();
    	return convertToDataType(clazz, value);
    }

    public static Object convertToDataType(final Class<?> clazz, final String value) {
    	return convertToDataType(clazz.getName(), value);
    }
    
    public static Object convertToDataType(final String className, final String value) {
    	Class<?> clazz = null;
    	try {
			clazz = Class.forName(className);
		} catch (ClassNotFoundException e) {
			throw new RuntimeException(e);
		}
		
    	if (value == null) {
    		return null;
    	}
    	else if (clazz.isPrimitive()) {
        	throw new RuntimeException("Rule proposition comparison data type conversion error. Primitives cannot be converted: " + clazz);
    	}
    	else if (clazz.equals(String.class)) {
    		return value;
    	}
    	else if (clazz.equals(Integer.class)) {
    		return new Integer(value);
    	}
    	else if (clazz.equals(Double.class)) {
    		return new Double(value);
    	}
    	else if (clazz.equals(Long.class)) {
    		return new Long(value);
    	}
    	else if (clazz.equals(Float.class)) {
    		return new Float(value);
    	}
    	else if (clazz.equals(Short.class)) {
    		return new Short(value);
    	}
    	else if (clazz.equals(Byte.class)) {
    		return new Byte(value);
    	}
    	else if (clazz.equals(BigDecimal.class)) {
    		return new BigDecimal(value);
    	}
    	else if (clazz.equals(BigInteger.class)) {
    		return new BigInteger(value);
    	}
    	else if (clazz.equals(Boolean.class)) {
    		return new Boolean(value);
    	}
    	else if (clazz.equals(Date.class)) {
    		return convertDate(value);
    	}
    	else if (clazz.equals(Calendar.class)) {
    		Date date = convertDate(value);
    		Calendar cal = Calendar.getInstance();
    		cal.setTime(date);
    		return cal;
    	}
    	else if (clazz.equals(GregorianCalendar.class)) {
    		Date date = convertDate(value);
    		GregorianCalendar cal = new GregorianCalendar();
    		cal.setTime(date);
    		return cal;
    	}
    	else if (clazz.equals(XMLGregorianCalendar.class)) {
        	String xsdDate = value;
    		DatatypeFactoryImpl factory = new DatatypeFactoryImpl();
        	XMLGregorianCalendar time = factory.newXMLGregorianCalendar(xsdDate);
        	return time;
    	}
    	throw new RuntimeException("Data type conversion error. Data type not found: " + clazz);
    }

    /**
     * <p>Converts the <code>expectedValue</code> to <code>dataType</code>.</p>
     * <p>e.g. dateType="java.lang.Integer" expectedValue="123" returns new Integer(123)</p>
     * 
     * @param dataType Data type to convert <code>expectedValue</code> to
     * @param value The value to be converted
     * @return New value in proper data type 
     */
    //public static <T> T convertToDataType(final Class<T> clazz, final Object value) {
    public static Object convertToDataType(final Class<?> clazz, final Object value) {
    	if (value == null) {
    		return null;
    	}
    	else if (clazz.isPrimitive()) {
        	throw new RuntimeException("Rule proposition comparison data type conversion error. Primitives cannot be converted: " + clazz);
    	}
    	else if (clazz.equals(String.class)) {
    		return clazz.cast(value);
    	}
    	else if (clazz.equals(Integer.class)) {
    		return clazz.cast(value);
    	}
    	else if (clazz.equals(Double.class)) {
    		return clazz.cast(value);
    	}
    	else if (clazz.equals(Long.class)) {
    		return clazz.cast(value);
    	}
    	else if (clazz.equals(Float.class)) {
    		return clazz.cast(value);
    	}
    	else if (clazz.equals(Short.class)) {
    		return clazz.cast(value);
    	}
    	else if (clazz.equals(BigDecimal.class)) {
    		return clazz.cast(value);
    	}
    	else if (clazz.equals(BigInteger.class)) {
    		return clazz.cast(value);
    	}
    	else if (clazz.equals(Boolean.class)) {
    		return clazz.cast(value);
    	}
    	else if (clazz.equals(Date.class)) {
    		return clazz.cast(value);
    	}
    	else if (clazz.equals(Calendar.class)) {
    		return clazz.cast(value);
    	}
    	else if (clazz.equals(GregorianCalendar.class)) {
    		return clazz.cast(value);
    	}
    	else if (value instanceof XMLGregorianCalendar) {
    		XMLGregorianCalendar xmlCal = (XMLGregorianCalendar) value;
    		return xmlCal.toGregorianCalendar();
    	}
    	throw new RuntimeException("Data type conversion error. Data type not found: " + clazz);
    }
    
    /**
     * Converts an <code>ISO_TIMESTAMP_FORMAT</code> string date into a 
     * <code>java.util.Date</code>.
     * 
     * @param value Converted date
     * @return
     */
    public static Date convertDate(String value) {
    	try {
			return dateFormat.parse(value);
		} catch (ParseException e) {
			throw new RuntimeException("Data type conversion error", e);
		}
    }
    
    /**
     * Formats a date such as java.util.Date, java.util.Calendar, etc.
     * according to the <code>ISO_TIMESTAMP_FORMAT</code>.
     * 
     * @param dateObject A date object  
     * @return
     */
    public static String formatDate(Date date) {
    	return dateFormat.format(date).toString();
    }
}
