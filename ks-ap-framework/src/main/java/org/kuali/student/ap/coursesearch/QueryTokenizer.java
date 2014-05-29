package org.kuali.student.ap.coursesearch;

import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

/**
 *  Handles the parsing and extracting of query text into components and tokens
 */
public interface QueryTokenizer {

    /**
     * Rules for parsing strings into tokens
     */
    public enum Rule{
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
		public Rule rule;
		public String value;
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
    public List<Token> tokenize( String source );

    /**
     * Create a list of token's string values
     *
     * @param tokens - List of tokens to compile
     * @return List of string values
     */
    public List<String> toStringList(List<Token> tokens);

    /**
     * Extract a list of levels found in a string
     *
     * @param source - String to parse
     * @return A list of levels
     */
    public List<String> extractCourseLevels(String source);

    /**
     * Extract a list of codes found in a string
     *
     * @param source - String to parse
     * @return A list of codes
     */
    public List<String> extractCourseCodes(String source);

    /**
     * Extract a list of incomplete course codes (Division+Partial Code) found in a string
     *
     * @param source - String to parse
     * @param divisions - list of found divisions
     * @return A list of incomplete course codes
     */
    public List<String> extractIncompleteCourseCodes(String source, List<String> divisions);

    /**
     * Extract a list of complete course levels (Divison+code) found in a string
     *
     * @param source - String to parse
     * @param divisions - list of found divisions
     * @param codes - List of found codes
     * @return A list of complete course codes
     */
    public List<String> extractCompleteCourseCodes(String source, List<String> divisions, List<String> codes);

    /**
     * Extract a list of complete course levels (Divison+level) found in a string
     *
     * @param source - String to parse
     * @param divisions - list of found divisions
     * @param levels - List of found levels
     * @return A list of complete course levels
     */
    public List<String> extractCompleteCourseLevels(String source, List<String> divisions, List<String> levels);

    /**
     * Extract a list of divisions found in a string
     *
     * @param source - String to parse
     * @param divisionMap - Map of valid divisions
     * @return A list of valid divisions strings in the text
     */
    public List<String> extractDivisionsNoSpaces(String source, Map<String, String> divisionMap);

    public List<String> extractDivisionsSpaces(String source, Map<String, String> divisionMap);

    /**
     * Extract a list of divisions found in a string based on keywords related to that division.
     *
     * @param source - String to parse
     * @param subjectAreaMap - Map of keywords to related division
     * @return A list of valid divisions found
     */
    public List<String> extractDivisionsFromSubjectKeywords(String source, Map<String, String> subjectAreaMap);

    /**
     * Extracts the possible divisions in the query string
     *
     * @param divisionMap    - Map of possible divisions
     * @param query          - The query string
     * @param divisions      - List of extracted divisions
     * @param isSpaceAllowed - If spaces are allowed in the divisions
     * @return query string, minus matches found
     */
    public String extractDivisions(Map<String, String> divisionMap,
                            String query, List<String> divisions, boolean isSpaceAllowed);

    /**
     * Convert token to its correct text, remove quote if any
     *
     * @param token - The token to be cleaned up
     * @return Formatted token text
     */
    public String cleanToken(Token token);
}
