package org.kuali.student.ap.coursesearch.controller;

import org.junit.Test;
import static org.junit.Assert.*;

import java.util.List;

public class QueryTokenizerTest {
    String sample = " A abc123 edc&i \"quoted text !@#\" 0 00 000 0000 3xx 4XX ";

    @Test
    public void testTokenize() throws Exception {
        List<QueryTokenizer.Token> list = QueryTokenizer.tokenize( sample );
        assertTrue( list.size() == 10 );
        assertEquals( list.get( 2 ).rule, QueryTokenizer.Rule.WORD );
        assertEquals( list.get( 2 ).value, "edc&i" );
        assertEquals( list.get( 3 ).rule, QueryTokenizer.Rule.QUOTED );
    }

    @Test
    public void testIgnoreSpaces() throws Exception {
        List<QueryTokenizer.Token> list = QueryTokenizer.tokenize( "   A  B   C     D  " );
        assertTrue( list.size() == 4 );
        assertEquals( list.get( 3 ).rule, QueryTokenizer.Rule.WORD );
    }

    @Test
    public void testExtractCourseLevels() throws Exception {
        List<String> levels = QueryTokenizer.extractCourseLevels(sample);
        assertEquals( levels.get( 0 ), "3xx" );
        assertEquals( levels.get( 1 ), "4XX" );
    }

    @Test
    public void testExtractCourseCodes() throws Exception {
        List<String> levels = QueryTokenizer.extractCourseCodes(sample);
        assertEquals( levels.get( 0 ), "123" );
        assertEquals( levels.get( 1 ), "000" );
    }
}
