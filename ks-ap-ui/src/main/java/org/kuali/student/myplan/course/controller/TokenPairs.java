package org.kuali.student.myplan.course.controller;

import java.util.*;

public class TokenPairs
{
    public static List<String> toPairs(List<String> list)
    {
        ArrayList<String> pairs = new ArrayList<String>();
        int a = 0;
        int b = 0;
        int size = list.size();
        while( a < size )
        {
            String result = null;
            if( a == b )
            {
                result = list.get( a );
                b++;
            }
            else if( a != b )
            {
                result = list.get( a ) + " " + list.get( b );
                a++;
            }

            if( b == size )
            {
                a = b;
            }

            pairs.add( result );
        }

        return pairs;
    }

    public static class LongestFirst implements Comparator<String>
    {
       // First sort by length, then by String's own comparison (ie alpha)
       public int compare( String left, String right )
       {
          int diff = right.length() - left.length();
          if( diff == 0 )
          {
              diff = left.compareTo( right );
          }
          return diff;
       }
    }

    public static void sortedLongestFirst( List<String> list )
    {
        Collections.sort(list, new LongestFirst());
    }
}
