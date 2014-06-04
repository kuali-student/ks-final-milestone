/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kuali.student.common.test.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import org.junit.Assert;

import static org.junit.Assert.assertEquals;

/**
 * Helps create a dynamic 
 * @author nwright
 */
public class ListOfStringTester {

	/**
	 * Verify that the expected elements exist in the actual list.  
	 * order is not important
	 * @param expectedList
	 * @param actualList
	 * @param failForExtraElements true if we should fail if there are other elements in the actual list.
	 */
	public void checkExistsAnyOrder (List<String>expectedList, List<String>actualList, boolean failForExtraElements) {
		
		if (failForExtraElements) 
			Assert.assertFalse(expectedList.size() != actualList.size());
		
		List<String>unmatchedList = new ArrayList<String>(expectedList);
		
		for (String string : actualList) {

			boolean removed = unmatchedList.remove(string);
		
			if (failForExtraElements)
				Assert.assertTrue(removed);
		}
		
		Assert.assertTrue(unmatchedList.size() == 0);
	
	}
	
    public void check(List<String> expectedList, List<String> actualList) {
        if (expectedList.size () != actualList.size ()) {
            this.dump(expectedList, actualList);
        }
        assertEquals(expectedList.size(), actualList.size());
        List<String> expectedSorted = new ArrayList(expectedList);
        Collections.sort(expectedSorted);
        List<String> actualSorted = new ArrayList(actualList);
        Collections.sort(actualSorted);
        for (int i = 0; i < expectedSorted.size(); i++) {
            String expected = expectedSorted.get(i);
            String actual = actualSorted.get(i);
            assertEquals(i + "", expected, actual);
        }
    }

    public void dump (List<String> expectedList, List<String> actualList) {
         System.out.println ("Original List");
        this.dump(expectedList);
        System.out.println ("Updated List");
        this.dump(actualList);
    }
    
    public void dump(List<String> list) {
        for (int i = 0; i < list.size(); i++) {
            String expected = list.get(i);
            System.out.println(i + ".) " + expected);
        }
    }

    /*
     * Generates a List<String> object, where each element is the String representation
     * of a randomly generated int.
     * @param length the number of elements to return in the List<String> object.
     * @param upperBound the maximum value to be generated
     */
    public static List<String> generateRandomListOfStringIds (int length, int upperBound) {
        Random generator = new Random();
        List<String> arr = new ArrayList<String> ();
        for (int i=0; i<length; i++) {
            int randomNum = generator.nextInt(upperBound);
            arr.add("" + randomNum);
        }
        return arr;
    }

}
