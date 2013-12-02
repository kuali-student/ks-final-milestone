package org.kuali.student.ap.coursesearch.controller;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;

public class TokenPairsTest {
    @Test
    public void testGronk() throws Exception {
        ArrayList<String> list = new ArrayList<String>();
        list.add( "A" );
        list.add( "BB" );
        list.add( "C" );
        List<String> pairs = TokenPairs.toPairs(list);
        assertTrue( pairs.size() == 5 );
        assertEquals( pairs.get( 0 ), "A" );
        assertEquals( pairs.get( 1 ), "A BB" );
        assertEquals( pairs.get( 2 ), "BB" );
        assertEquals( pairs.get( 3 ), "BB C" );
        assertEquals( pairs.get( 4 ), "C" );
    }

    @Test
    public void testSortedLongestFirst() throws Exception {
        ArrayList<String> list = new ArrayList<String>();
        list.add( "A" );
        list.add( "BB" );
        list.add( "CCC" );
        list.add( "AA" );

        TokenPairs.sortedLongestFirst( list );
        assertEquals( list.get( 0 ), "CCC" );
        assertEquals( list.get( 1 ), "AA" );
        assertEquals( list.get( 2 ), "BB" );
        assertEquals( list.get( 3 ), "A" );
    }

    @Test
    public void testLongestFirstComparator()
    {
        TokenPairs.LongestFirst comparator = new TokenPairs.LongestFirst();
        assertEquals( 0, comparator.compare( "A", "A" ) );
        assertEquals( 1, comparator.compare( "A", "AA" ) );
        assertEquals( -1, comparator.compare( "AA", "A" ) );
        assertEquals( 1, comparator.compare( "B", "A" ) );
        assertEquals( -1, comparator.compare( "A", "B" ) );
    }
}
