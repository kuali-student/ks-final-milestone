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

import org.kuali.student.ap.coursesearch.QueryTokenizer;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *  Handles the parsing and extracting of query text into components and tokens
 */
public class QueryTokenizerImpl implements QueryTokenizer {

    public static Pattern LEVEL = Pattern.compile( "[0-9][Xx][Xx]" );
    public static Pattern NUMBER = Pattern.compile( "[0-9]+" );


    @Override
	public List<Token> tokenize( String source ){
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

    @Override
    public List<String> toStringList(List<Token> tokens){
        ArrayList<String> list = new ArrayList<String>();
        for( Token token : tokens )
        {
            list.add( token.value );
        }
        return list;
    }

    @Override
    public List<String> extractCourseLevels(String source){
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

    @Override
    public List<String> extractCourseCodes(String source){
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

    @Override
    public List<String> extractIncompleteCourseCodes(String source, List<String> divisions){
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

    @Override
    public List<String> extractCompleteCourseCodes(String source, List<String> divisions, List<String> codes){
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

    @Override
    public List<String> extractCompleteCourseLevels(String source, List<String> divisions, List<String> levels){
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

    @Override
    public List<String> extractDivisionsNoSpaces(String source, Map<String, String> divisionMap){
        List<String> divisions = new ArrayList<String>();
        List<Token> tokens = tokenize(source);

        for(Token token : tokens){
            if(divisionMap.containsKey(token.value)){
                divisions.add(divisionMap.get(token.value));
            }
        }

        return divisions;
    }

    @Override
    public List<String> extractDivisionsSpaces(String source, Map<String, String> divisionMap){
        List<String> divisions = new ArrayList<String>();

        boolean match = true;
        while (match) {
            match = false;
            List<QueryTokenizer.Token> tokens = tokenize(source);
            List<String> list = toStringList(tokens);
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

    @Override
    public List<String> extractDivisionsFromSubjectKeywords(String source, Map<String, String> subjectAreaMap){
        List<String> exactMatchDivisions = new ArrayList<String>();
        List<String> startMatchDivisions = new ArrayList<String>();
        List<String> partialMatchDivisions = new ArrayList<String>();
        List<Token> tokens = tokenize(source);

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

    @Override
    public String extractDivisions(Map<String, String> divisionMap,
                                   String query, List<String> divisions, boolean isSpaceAllowed) {
        if (!isSpaceAllowed) {
            query = query.trim().replaceAll(
                    "[\\s\\\\/:?\\\"<>|`~!@#$%^*()_+-={}\\]\\[;',.]", " ");
            divisions.addAll(extractDivisionsNoSpaces(query, divisionMap));
        } else {
            query = query.replaceAll(
                    "[\\\\/:?\\\"<>|`~!@#$%^*()_+-={}\\]\\[;',.]", " ");
            divisions.addAll(extractDivisionsSpaces(query, divisionMap));
        }


        return query;
    }

    @Override
    public String cleanToken(Token token) {
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