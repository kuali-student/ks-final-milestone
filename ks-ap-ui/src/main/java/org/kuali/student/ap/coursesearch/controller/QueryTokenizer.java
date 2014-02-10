package org.kuali.student.ap.coursesearch.controller;

import java.util.*;
import java.util.regex.*;

public class QueryTokenizer
{

    public static Pattern LEVEL = Pattern.compile( "[0-9][Xx][Xx]" );
    public static Pattern NUMBER = Pattern.compile( "[0-9]+" );

	enum Rule
	{
        // Order significant. See matcher's foreach loop below.
		WORD( "[A-Za-z0-9&]+" ),
		QUOTED( "\"[^\"]*\"" );
		
		public Pattern pattern;
		
		Rule( String regex )
		{
			pattern = Pattern.compile( regex );
		}
	}
	
	public static class Token
	{
		Rule rule;
		String value;
		public Token( Rule rule, String value )
		{
			this.rule = rule;
			this.value = value;
		}
	}
	
	public static List<Token> tokenize( String source )
	{
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

    public static List<String> toStringList( List<Token> tokens )
    {
        ArrayList<String> list = new ArrayList<String>();
        for( Token token : tokens )
        {
            list.add( token.value );
        }
        return list;
    }

    public static List<String> extractCourseLevels(String source)
    {
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

    public static List<String> extractCourseCodes(String source)
    {
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
    public static List<String> extractIncompleteCourseCodes(String source, List<String> divisions)
    {
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
                    source = source.replace(pair, "");
                    match = true;
                }
            }
        }
        return divisions;
    }


}