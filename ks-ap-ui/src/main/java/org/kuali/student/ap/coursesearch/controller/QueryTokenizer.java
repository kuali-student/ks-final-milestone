/*
 * Copyright 2014 The Kuali Foundation Licensed under the
 * Educational Community License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may
 * obtain a copy of the License at
 *
 * http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */
package org.kuali.student.ap.coursesearch.controller;

import java.util.*;
import java.util.regex.*;

/**
 *  Handles the parsing and extracting of query text into components and tokens
 */
public class QueryTokenizer
{

    public static Pattern LEVEL = Pattern.compile( "[0-9][Xx][Xx]" );
    public static Pattern NUMBER = Pattern.compile( "[0-9]+" );

    /**
     * Rules for parsing strings into tokens
     */
	enum Rule{
        // Order significant. See matcher's foreach loop below.
		WORD( "[A-Za-z0-9&-_]+" ),
		QUOTED( "\"[^\"]*\"" );
		
		public Pattern pattern;
		
		Rule( String regex )
		{
			pattern = Pattern.compile( regex );
		}
	}

    /**
     * A container for a specific string token
     */
	public static class Token{
		Rule rule;
		String value;
		public Token( Rule rule, String value )
		{
			this.rule = rule;
			this.value = value;
		}
	}

    /**
     * Parse a string into the list of individual tokens that comprise it
     *
     * @param source - The string to parse
     * @return A list of tokens that made up the string
     */
	public static List<Token> tokenize( String source ){
		ArrayList<Token> tokens = new ArrayList<Token>();
		int pos = 0;
		final int len = source.length();
		
		Matcher m = Pattern.compile( "dummy" ).matcher( source );
		m.useTransparentBounds( true );
		m.useAnchoringBounds( false );

        loop:
		while( pos < len )
		{
			m.region( pos, len );
			for( Rule rule : Rule.values() )
			{
				if( m.usePattern( rule.pattern ).lookingAt() )
				{
					String value = source.substring( m.start(), m.end() );
					Token token = new Token( rule, value );
					tokens.add( token );	
					pos = m.end();
					continue loop;
				}
			}
			
			pos++;
		}
		
		return tokens;
	}

    /**
     * Create a list of token's string values
     *
     * @param tokens - List of tokens to compile
     * @return List of string values
     */
    public static List<String> toStringList( List<Token> tokens ){
        ArrayList<String> list = new ArrayList<String>();
        for( Token token : tokens )
        {
            list.add( token.value );
        }
        return list;
    }

    /**
     * Extract a list of levels found in a string
     *
     * @param source - String to parse
     * @return A list of levels
     */
    public static List<String> extractCourseLevels(String source){
        ArrayList<String> tokens = new ArrayList<String>();
        int pos = 0;
        final int len = source.length();

        Matcher m = Pattern.compile( "dummy" ).matcher( source );
        m.useTransparentBounds( true );
        m.useAnchoringBounds( false );

        while( pos < len )
        {
            m.region( pos, len );

            if( m.usePattern( LEVEL ).lookingAt() )
            {
                String value = source.substring( m.start(), m.end() );
                tokens.add( value );
                pos = m.end();
            }

            pos++;
        }

        return tokens;
    }

    /**
     * Extract a list of codes found in a string
     *
     * @param source - String to parse
     * @return A list of codes
     */
    public static List<String> extractCourseCodes(String source){
        ArrayList<String> tokens = new ArrayList<String>();
        int pos = 0;
        final int len = source.length();

        Matcher m = Pattern.compile( "dummy" ).matcher( source );
        m.useTransparentBounds( true );

        while( pos < len )
        {
            m.region( pos, len );

            if( m.usePattern( NUMBER ).lookingAt() )
            {
                if( m.end() - m.start() == 3 )
                {
                    String value = source.substring( m.start(), m.end() );
                    tokens.add( value );
                }
                pos = m.end();
            }

            pos++;
        }

        return tokens;
    }

    /**
     * Extract a list of incomplete course codes (Division+Partial Code) found in a string
     *
     * @param source - String to parse
     * @param divisions - list of found divisions
     * @return A list of incomplete course codes
     */
    public static List<String> extractIncompleteCourseCodes(String source, List<String> divisions){
        ArrayList<String> tokens = new ArrayList<String>();
        int pos = 0;
        final int len = source.length();

        Matcher m = Pattern.compile( "dummy" ).matcher( source );
        m.useTransparentBounds( true );

        while( pos < len )
        {
            m.region( pos, len );
            for(String division : divisions){
                Pattern courseCode = Pattern.compile( division+"[0-9]+" );
                if( m.usePattern( courseCode ).lookingAt() )
                {
                    String value = source.substring( m.start(), m.end() );
                    tokens.add( value );
                    pos = m.end();
                }
            }

            pos++;
        }

        return tokens;
    }

    /**
     * Extract a list of complete course levels (Divison+code) found in a string
     *
     * @param source - String to parse
     * @param divisions - list of found divisions
     * @param codes - List of found codes
     * @return A list of complete course codes
     */
    public static List<String> extractCompleteCourseCodes(String source, List<String> divisions, List<String> codes){
        List<String> completedCodes = new ArrayList<String>();
        List<Token> tokens = tokenize(source);

        for(Token token : tokens){
            for(String division : divisions){
                for(String code : codes){
                    if(token.value.contains(division) && token.value.contains(code)){
                        String newCompletedCode = division+","+code;
                        if(!completedCodes.contains(newCompletedCode)){
                            completedCodes.add(newCompletedCode);
                        }
                    }
                }
            }
        }

        return completedCodes;
    }

    /**
     * Extract a list of complete course levels (Divison+level) found in a string
     *
     * @param source - String to parse
     * @param divisions - list of found divisions
     * @param levels - List of found levels
     * @return A list of complete course levels
     */
    public static List<String> extractCompleteCourseLevels(String source, List<String> divisions, List<String> levels){
        List<String> completedLevels = new ArrayList<String>();
        List<Token> tokens = tokenize(source);

        for(Token token : tokens){
            for(String division : divisions){
                for(String level : levels){
                    if(token.value.contains(division) && token.value.contains(level)){
                        String newCompletedLevel = division+","+level;
                        if(!completedLevels.contains(newCompletedLevel)){
                            completedLevels.add(newCompletedLevel);
                        }
                    }
                }
            }
        }

        return completedLevels;
    }

    /**
     * Extract a list of divisions found in a string
     *
     * @param source - String to parse
     * @param divisionMap - Map of valid divisions
     * @return A list of valid divisions strings in the text
     */
    public static List<String> extractDivisionsNoSpaces(String source, Map<String, String> divisionMap){
        List<String> divisions = new ArrayList<String>();
        List<Token> tokens = tokenize(source);

        for(Token token : tokens){
            if(divisionMap.containsKey(token.value)){
                divisions.add(divisionMap.get(token.value));
            }
        }

        return divisions;
    }

    /**
     * Extract a list of divisions found in a string while allowing the divisions to be create from 2 tokens
     * separated by a space
     *
     * @param source - String to parse
     * @param divisionMap - Map of valid divisions
     * @return A list of valid division strings in the text
     */
    public static List<String> extractDivisionsSpaces(String source, Map<String, String> divisionMap){
        List<String> divisions = new ArrayList<String>();

        boolean match = true;
        while (match) {
            match = false;
            List<QueryTokenizer.Token> tokens = QueryTokenizer.tokenize(source);
            List<String> list = QueryTokenizer.toStringList(tokens);
            List<String> pairs = TokenPairs.toPairs(list);
            TokenPairs.sortedLongestFirst(pairs);

            Iterator<String> i = pairs.iterator();
            while (match == false && i.hasNext()) {
                String pair = i.next();

                String key = pair.replace(" ", "");
                if (divisionMap.containsKey(key)) {
                    String division = divisionMap.get(key);
                    divisions.add(division);
                    source = source.replaceFirst(pair, "");
                    match = true;
                }
            }
        }
        return divisions;
    }

    /**
     * Extract a list of divisions found in a string based on keywords related to that division.
     *
     * @param source - String to parse
     * @param subjectAreaMap - Map of keywords to related division
     * @return A list of valid divisions found
     */
    public static List<String> extractDivisionsFromSubjectKeywords(String source, Map<String,String> subjectAreaMap){
        List<String> exactMatchDivisions = new ArrayList<String>();
        List<String> startMatchDivisions = new ArrayList<String>();
        List<String> partialMatchDivisions = new ArrayList<String>();
        List<Token> tokens = QueryTokenizer.tokenize(source);

        for (QueryTokenizer.Token token : tokens) {

            // Convert token to its correct text
            String queryText = cleanToken(token);

            // Skip if query is less than 3 characters
            if (queryText != null && queryText.length() < 3) continue;

            //  Look to see if the query text is present in any subject area descriptions
            String divisionKey = queryText.trim().toUpperCase();
            for (Map.Entry<String, String> entry : subjectAreaMap.entrySet()) {
                if (entry.getValue().trim().toUpperCase().equals(divisionKey)) {
                    exactMatchDivisions.add(entry.getKey());
                } else if (entry.getValue().toUpperCase().startsWith(divisionKey)) {
                    startMatchDivisions.add(entry.getKey());
                } else if (entry.getValue().toUpperCase().contains(divisionKey)) {
                    partialMatchDivisions.add(entry.getKey());
                }
            }
        }

        // Build final list in specific order
        List<String> divisions = new ArrayList<String>();
        divisions.addAll(exactMatchDivisions);
        divisions.addAll(startMatchDivisions);
        divisions.addAll(partialMatchDivisions);

        return divisions;
    }

    /**
     * Extracts the possible divisions in the query string
     *
     * @param divisionMap    - Map of possible divisions
     * @param query          - The query string
     * @param divisions      - List of extracted divisions
     * @param isSpaceAllowed - If spaces are allowed in the divisions
     * @return query string, minus matches found
     */
    public static String extractDivisions(Map<String, String> divisionMap,
                                   String query, List<String> divisions, boolean isSpaceAllowed) {
        if (!isSpaceAllowed) {
            query = query.trim().replaceAll(
                    "[\\s\\\\/:?\\\"<>|`~!@#$%^*()_+-={}\\]\\[;',.]", " ");
            divisions.addAll(QueryTokenizer.extractDivisionsNoSpaces(query, divisionMap));
        } else {
            query = query.replaceAll(
                    "[\\\\/:?\\\"<>|`~!@#$%^*()_+-={}\\]\\[;',.]", " ");
            divisions.addAll(QueryTokenizer.extractDivisionsSpaces(query, divisionMap));
        }


        return query;
    }

    /**
     * Convert token to its correct text, remove quote if any
     *
     * @param token - The token to be cleaned up
     * @return Formatted token text
     */
    public static String cleanToken(Token token) {
        String queryText = null;
        switch (token.rule) {
            case WORD:
                queryText = token.value;
                break;
            case QUOTED:
                queryText = token.value;
                queryText = queryText.substring(1, queryText.length() - 1);
                break;
            default:
                break;
        }
        return queryText;
    }

}