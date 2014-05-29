package org.kuali.student.ap.coursesearch.controller;

import org.junit.Before;
import org.junit.Test;
import org.kuali.student.ap.coursesearch.QueryTokenizer;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class QueryTokenizerImplTest {
    String sample = " A abc123 edc&i \"quoted text !@#\" 0 00 000 0000 3xx 4XX ";
    QueryTokenizer qt;

    @Before
    public void setUp() {
        qt = new QueryTokenizerImpl();
    }

    @Test
    public void testTokenize() throws Exception {
        List<QueryTokenizer.Token> list = qt.tokenize(sample);
        assertTrue( list.size() == 10 );
        assertEquals( list.get( 2 ).rule, QueryTokenizer.Rule.WORD );
        assertEquals( list.get( 2 ).value, "edc&i" );
        assertEquals( list.get( 3 ).rule, QueryTokenizer.Rule.QUOTED );
    }

    @Test
    public void testIgnoreSpaces() throws Exception {
        List<QueryTokenizer.Token> list = qt.tokenize("   A  B   C     D  ");
        assertTrue( list.size() == 4 );
        assertEquals( list.get( 3 ).rule, QueryTokenizer.Rule.WORD );
    }

    @Test
    public void testExtractCourseLevels() throws Exception {
        List<String> levels = qt.extractCourseLevels(sample);
        assertEquals( levels.get( 0 ), "3xx" );
        assertEquals( levels.get( 1 ), "4XX" );
    }

    @Test
    public void testExtractCourseCodes() throws Exception {
        List<String> levels = qt.extractCourseCodes(sample);
        assertEquals( levels.get( 0 ), "123" );
        assertEquals( levels.get( 1 ), "000" );
    }
}
