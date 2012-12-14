package edu.uw.kuali.student.myplan.tests.unit;

import edu.uw.kuali.student.myplan.util.CircularTermList;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;

public class CircularTermListTest {

    @Test
    public void circularTermListExerciseTermsByName() {
        CircularTermList ctl = new CircularTermList("autumn", 2000);
        assertEquals(ctl.getQuarterName(), "Autumn");
        assertEquals(ctl.getQuarterNumber(), "4");
        assertEquals(ctl.getYear(), 2000);

        ctl.incrementQuarter();
        assertEquals(ctl.getQuarterName(), "Winter");
        assertEquals(ctl.getQuarterNumber(), "1");
        assertEquals(ctl.getYear(), 2001);

        ctl.incrementQuarter();
        assertEquals(ctl.getQuarterName(), "Spring");
        assertEquals(ctl.getQuarterNumber(), "2");
        assertEquals(ctl.getYear(), 2001);

        ctl.incrementQuarter();
        assertEquals(ctl.getQuarterName(), "Summer");
        assertEquals(ctl.getQuarterNumber(), "3");
        assertEquals(ctl.getYear(), 2001);

        ctl.incrementQuarter();
        assertEquals(ctl.getQuarterName(), "Autumn");
        assertEquals(ctl.getQuarterNumber(), "4");
        assertEquals(ctl.getYear(), 2001);

        ctl.incrementQuarter();
        assertEquals(ctl.getQuarterName(), "Winter");
        assertEquals(ctl.getQuarterNumber(), "1");
        assertEquals(ctl.getYear(), 2002);
    }

    @Test
    public void circularTermListExerciseTermsByNumber() {
        CircularTermList ctl = new CircularTermList("4", 2011);
        assertEquals(ctl.getQuarterName(), "Autumn");
        assertEquals(ctl.getQuarterNumber(), "4");
        assertEquals(ctl.getYear(), 2011);

        ctl.incrementQuarter();
        assertEquals(ctl.getQuarterName(), "Winter");
        assertEquals(ctl.getQuarterNumber(), "1");
        assertEquals(ctl.getYear(), 2012);

        ctl.incrementQuarter();
        assertEquals(ctl.getQuarterName(), "Spring");
        assertEquals(ctl.getQuarterNumber(), "2");
        assertEquals(ctl.getYear(), 2012);

        ctl.incrementQuarter();
        assertEquals(ctl.getQuarterName(), "Summer");
        assertEquals(ctl.getQuarterNumber(), "3");
        assertEquals(ctl.getYear(), 2012);

        ctl.incrementQuarter();
        assertEquals(ctl.getQuarterName(), "Autumn");
        assertEquals(ctl.getQuarterNumber(), "4");
        assertEquals(ctl.getYear(), 2012);

        ctl.incrementQuarter();
        assertEquals(ctl.getQuarterName(), "Winter");
        assertEquals(ctl.getQuarterNumber(), "1");
        assertEquals(ctl.getYear(), 2013);
    }

    @Test (expected = IllegalArgumentException.class)
    public void circularTermListUnknownQuarter() {
        new CircularTermList("unknown", 2000);
    }
}
