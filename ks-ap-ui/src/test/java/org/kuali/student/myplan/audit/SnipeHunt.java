package org.kuali.student.myplan.audit;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.kuali.student.myplan.utils.CourseLinkBuilder;
import org.kuali.student.r2.common.dto.ContextInfo;

public class SnipeHunt {
    public static void main( String[] args )
        throws Exception
    {
    	ContextInfo context = new ContextInfo();
        InputStream ins = ClassLoader.getSystemResourceAsStream("SnipeHunt.csv");
        BufferedReader buffy = new BufferedReader(new InputStreamReader( ins));

        // skip header row
        String lame = buffy.readLine();

        while ((lame = buffy.readLine()) != null) {

            String[] weak = lame.split( "\\t" );
            try
            {
                char lasera = weak[9].charAt( 0 );
                String darout = weak[11];
                switch ( lasera )
                {
                    // Linkify these rows
                    case 'b':
                    case 'c':
                    case 'n':
                    case 'A':
                    case 'B':
                        String victim = CourseLinkBuilder.makeLinks(darout, CourseLinkBuilder.LINK_TEMPLATE.TEST, context);
                        System.out.println(victim);
                        break;

                    // Do not linkify these rows
                    case '1':
                    case '2':
                    case '3':
                    case '4':
                    case '5':
                    case '7':
                    case '8':
                    case 'a':
                    case 'g':
                    case 'j':
                    case 'C':
                    case 'D':
                    case 'F':
                        System.out.println(darout);
                        break;

                    // Don't know what to do with these rows
                    case '0':
                    case '9':
                    case 'e':
                    case 'h':
                    case 'i':
                    case 'k':
                    case 'l':
                    case 'm':
                    case 'U':
                    default:
                        System.out.println(darout);
                        break;
                }
            }
            catch( Exception e )
            {
                // ignore
            }
        }

    }


}


