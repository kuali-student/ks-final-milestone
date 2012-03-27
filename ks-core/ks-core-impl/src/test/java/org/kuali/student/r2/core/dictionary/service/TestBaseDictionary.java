package org.kuali.student.r2.core.dictionary.service;

import java.util.Map;
import org.junit.Test;
import org.kuali.student.common.test.util.ContextInfoTestUtility;
import org.kuali.student.r1.common.dictionary.dto.ValidCharsConstraint;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.validator.DefaultValidatorImpl;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import static org.junit.Assert.*;

public class TestBaseDictionary
{

    @Test
    public void testValidCharsConstraints ()
    {
        ContextInfo contextInfo = ContextInfoTestUtility.getEnglishContextInfo();
        System.out.println ("testing base dictionary");
        String contextFile = "ks-base-dictionary-context.xml";
        ApplicationContext ac = new ClassPathXmlApplicationContext ("classpath:"
                + contextFile);
        Map<String, ValidCharsConstraint> vcs = (Map<String, ValidCharsConstraint>) ac.getBeansOfType (
                ValidCharsConstraint.class);
        for (String id : vcs.keySet ())
        {
            ValidCharsConstraint vc = vcs.get (id);
            System.out.println ("valid chars constraint: " + id + " "
                    + vc.getLabelKey () + " " + vc.getValue ());
        }

        String id = null;
        ValidCharsConstraint vc = null;
        DefaultValidatorImpl v = new DefaultValidatorImpl ();

        id = "alphanumericHyphenPeriod";
        vc = vcs.get (id);
        assertNotNull (vc);
        assertNull (v.processValidCharConstraint ("test", vc, null, "1", contextInfo));
        assertNull (v.processValidCharConstraint ("test", vc, null, "A", contextInfo));
        assertNull (v.processValidCharConstraint ("test", vc, null, ".", contextInfo));
        assertNull (v.processValidCharConstraint ("test", vc, null, "-", contextInfo));
        assertNotNull (v.processValidCharConstraint ("test", vc, null, " ", contextInfo));
        assertNotNull (v.processValidCharConstraint ("test", vc, null, ",", contextInfo));
        assertNotNull (v.processValidCharConstraint ("test", vc, null, "\n", contextInfo));
        assertNotNull (v.processValidCharConstraint ("test", vc, null, "\r", contextInfo));
        assertNotNull (v.processValidCharConstraint ("test", vc, null, "\t", contextInfo));

        id = "digits";
        vc = vcs.get (id);
        assertNotNull (vc);
        assertNull (v.processValidCharConstraint ("test", vc, null, "1", contextInfo));
        assertNotNull (v.processValidCharConstraint ("test", vc, null, "A", contextInfo));
        assertNotNull (v.processValidCharConstraint ("test", vc, null, ".", contextInfo));
        assertNotNull (v.processValidCharConstraint ("test", vc, null, "-", contextInfo));
        assertNotNull (v.processValidCharConstraint ("test", vc, null, " ", contextInfo));
        assertNotNull (v.processValidCharConstraint ("test", vc, null, ",", contextInfo));
        assertNotNull (v.processValidCharConstraint ("test", vc, null, "\n", contextInfo));
        assertNotNull (v.processValidCharConstraint ("test", vc, null, "\r", contextInfo));
        assertNotNull (v.processValidCharConstraint ("test", vc, null, "\t", contextInfo));

        id = "numeric";
        vc = vcs.get (id);
        assertNotNull (vc);
        assertNull (v.processValidCharConstraint ("test", vc, null, "0", contextInfo));
        assertNull (v.processValidCharConstraint ("test", vc, null, "1", contextInfo));
        assertNull (v.processValidCharConstraint ("test", vc, null, "20", contextInfo));
        assertNull (v.processValidCharConstraint ("test", vc, null, "-20", contextInfo));
        assertNull (v.processValidCharConstraint ("test", vc, null, "-12345620", contextInfo));
        assertNull (v.processValidCharConstraint ("test", vc, null, "20.1", contextInfo));
        assertNull (v.processValidCharConstraint ("test", vc, null, "20.0", contextInfo));
        assertNull (v.processValidCharConstraint ("test", vc, null, "20.01", contextInfo));
        assertNull (v.processValidCharConstraint ("test", vc, null, "20.00", contextInfo));
        assertNull (v.processValidCharConstraint ("test", vc, null, "120.00", contextInfo));
        assertNull (v.processValidCharConstraint ("test", vc, null, "1120.00", contextInfo));
        assertNull (v.processValidCharConstraint ("test", vc, null, "11120.00", contextInfo));
        assertNull (v.processValidCharConstraint ("test", vc, null, "111120.00", contextInfo));
        assertNull (v.processValidCharConstraint ("test", vc, null, "1111120.00", contextInfo));
        assertNull (v.processValidCharConstraint ("test", vc, null, "11111120.00", contextInfo));
        assertNotNull (v.processValidCharConstraint ("test", vc, null, "020", contextInfo));
        assertNotNull (v.processValidCharConstraint ("test", vc, null, "-020", contextInfo));
        assertNotNull (v.processValidCharConstraint ("test", vc, null, "-020.1", contextInfo));
        assertNotNull (v.processValidCharConstraint ("test", vc, null, "20.010", contextInfo));
        assertNotNull (v.processValidCharConstraint ("test", vc, null, "20.011", contextInfo));
        assertNotNull (v.processValidCharConstraint ("test", vc, null, "A", contextInfo));
        assertNotNull (v.processValidCharConstraint ("test", vc, null, ".", contextInfo));
        assertNotNull (v.processValidCharConstraint ("test", vc, null, "-", contextInfo));
        assertNotNull (v.processValidCharConstraint ("test", vc, null, " ", contextInfo));
        assertNotNull (v.processValidCharConstraint ("test", vc, null, ",", contextInfo));
        assertNotNull (v.processValidCharConstraint ("test", vc, null, "\n", contextInfo));
        assertNotNull (v.processValidCharConstraint ("test", vc, null, "\r", contextInfo));
        assertNotNull (v.processValidCharConstraint ("test", vc, null, "\t", contextInfo));

        id = "numeric.range";
        vc = vcs.get (id);
        assertNotNull (vc);
        assertNull (v.processValidCharConstraint ("test", vc, null, "0", contextInfo));
        assertNull (v.processValidCharConstraint ("test", vc, null, "1", contextInfo));
        assertNull (v.processValidCharConstraint ("test", vc, null, "20", contextInfo));
        assertNull (v.processValidCharConstraint ("test", vc, null, "-20", contextInfo));
        assertNull (v.processValidCharConstraint ("test", vc, null, "-12345620", contextInfo));
        assertNull (v.processValidCharConstraint ("test", vc, null, "20.1", contextInfo));
        assertNull (v.processValidCharConstraint ("test", vc, null, "20.0", contextInfo));
        assertNull (v.processValidCharConstraint ("test", vc, null, "20.01", contextInfo));
        assertNull (v.processValidCharConstraint ("test", vc, null, "20.00", contextInfo));
        assertNull (v.processValidCharConstraint ("test", vc, null, "120.00", contextInfo));
        assertNull (v.processValidCharConstraint ("test", vc, null, "1120.00", contextInfo));
        assertNull (v.processValidCharConstraint ("test", vc, null, "11120.00", contextInfo));
        assertNull (v.processValidCharConstraint ("test", vc, null, "111120.00", contextInfo));
        assertNull (v.processValidCharConstraint ("test", vc, null, "1111120.00", contextInfo));
        assertNull (v.processValidCharConstraint ("test", vc, null, "11111120.00", contextInfo));
        assertNull (v.processValidCharConstraint ("test", vc, null, "1-2", contextInfo));
        assertNull (v.processValidCharConstraint ("test", vc, null, "1.1-2.2", contextInfo));
        assertNull (v.processValidCharConstraint ("test", vc, null, "1 - 2", contextInfo));
        assertNull (v.processValidCharConstraint ("test", vc, null, "1--2", contextInfo));
        assertNull (v.processValidCharConstraint ("test", vc, null, "1 - -2", contextInfo));
        assertNull (v.processValidCharConstraint ("test", vc, null, "-1 - -2", contextInfo));
        assertNotNull (v.processValidCharConstraint ("test", vc, null, "1 -2", contextInfo));
        assertNotNull (v.processValidCharConstraint ("test", vc, null, "1 - 2 - 3", contextInfo));
        assertNotNull (v.processValidCharConstraint ("test", vc, null, "1- 2", contextInfo));
        assertNotNull (v.processValidCharConstraint ("test", vc, null, "020", contextInfo));
        assertNotNull (v.processValidCharConstraint ("test", vc, null, "-020", contextInfo));
        assertNotNull (v.processValidCharConstraint ("test", vc, null, "-020.1", contextInfo));
        assertNotNull (v.processValidCharConstraint ("test", vc, null, "20.010", contextInfo));
        assertNotNull (v.processValidCharConstraint ("test", vc, null, "20.011", contextInfo));
        assertNotNull (v.processValidCharConstraint ("test", vc, null, "A", contextInfo));
        assertNotNull (v.processValidCharConstraint ("test", vc, null, ".", contextInfo));
        assertNotNull (v.processValidCharConstraint ("test", vc, null, "-", contextInfo));
        assertNotNull (v.processValidCharConstraint ("test", vc, null, " ", contextInfo));
        assertNotNull (v.processValidCharConstraint ("test", vc, null, ",", contextInfo));
        assertNotNull (v.processValidCharConstraint ("test", vc, null, "\n", contextInfo));
        assertNotNull (v.processValidCharConstraint ("test", vc, null, "\r", contextInfo));
        assertNotNull (v.processValidCharConstraint ("test", vc, null, "\t", contextInfo));

        id = "positive.numeric.range";
        vc = vcs.get (id);
        assertNotNull (vc);
        assertNull (v.processValidCharConstraint ("test", vc, null, "0", contextInfo));
        assertNull (v.processValidCharConstraint ("test", vc, null, "1", contextInfo));
        assertNull (v.processValidCharConstraint ("test", vc, null, "20", contextInfo));
        assertNotNull (v.processValidCharConstraint ("test", vc, null, "-20", contextInfo));
        assertNotNull (v.processValidCharConstraint ("test", vc, null, "-12345620", contextInfo));
        assertNull (v.processValidCharConstraint ("test", vc, null, "20.1", contextInfo));
        assertNull (v.processValidCharConstraint ("test", vc, null, "20.0", contextInfo));
        assertNull (v.processValidCharConstraint ("test", vc, null, "20.01", contextInfo));
        assertNull (v.processValidCharConstraint ("test", vc, null, "20.00", contextInfo));
        assertNull (v.processValidCharConstraint ("test", vc, null, "120.00", contextInfo));
        assertNull (v.processValidCharConstraint ("test", vc, null, "1120.00", contextInfo));
        assertNull (v.processValidCharConstraint ("test", vc, null, "11120.00", contextInfo));
        assertNull (v.processValidCharConstraint ("test", vc, null, "111120.00", contextInfo));
        assertNull (v.processValidCharConstraint ("test", vc, null, "1111120.00", contextInfo));
        assertNull (v.processValidCharConstraint ("test", vc, null, "11111120.00", contextInfo));
        assertNull (v.processValidCharConstraint ("test", vc, null, "1-2", contextInfo));
        assertNull (v.processValidCharConstraint ("test", vc, null, "1.1-2.2", contextInfo));
        assertNull (v.processValidCharConstraint ("test", vc, null, "1 - 2", contextInfo));
        assertNotNull (v.processValidCharConstraint ("test", vc, null, "1--2", contextInfo));
        assertNotNull (v.processValidCharConstraint ("test", vc, null, "1 - -2", contextInfo));
        assertNotNull (v.processValidCharConstraint ("test", vc, null, "-1 - -2", contextInfo));
        assertNotNull (v.processValidCharConstraint ("test", vc, null, "1 -2", contextInfo));
        assertNotNull (v.processValidCharConstraint ("test", vc, null, "1 - 2 - 3", contextInfo));
        assertNotNull (v.processValidCharConstraint ("test", vc, null, "1- 2", contextInfo));
        assertNotNull (v.processValidCharConstraint ("test", vc, null, "020", contextInfo));
        assertNotNull (v.processValidCharConstraint ("test", vc, null, "-020", contextInfo));
        assertNotNull (v.processValidCharConstraint ("test", vc, null, "-020.1", contextInfo));
        assertNotNull (v.processValidCharConstraint ("test", vc, null, "20.010", contextInfo));
        assertNotNull (v.processValidCharConstraint ("test", vc, null, "20.011", contextInfo));
        assertNotNull (v.processValidCharConstraint ("test", vc, null, "A", contextInfo));
        assertNotNull (v.processValidCharConstraint ("test", vc, null, ".", contextInfo));
        assertNotNull (v.processValidCharConstraint ("test", vc, null, "-", contextInfo));
        assertNotNull (v.processValidCharConstraint ("test", vc, null, " ", contextInfo));
        assertNotNull (v.processValidCharConstraint ("test", vc, null, ",", contextInfo));
        assertNotNull (v.processValidCharConstraint ("test", vc, null, "\n", contextInfo));
        assertNotNull (v.processValidCharConstraint ("test", vc, null, "\r", contextInfo));
        assertNotNull (v.processValidCharConstraint ("test", vc, null, "\t", contextInfo));

        id = "gpa4.0";
        vc = vcs.get (id);
        assertNotNull (vc);
        assertNull (v.processValidCharConstraint ("test", vc, null, "0.0", contextInfo));
        assertNull (v.processValidCharConstraint ("test", vc, null, "4.0", contextInfo));
        assertNull (v.processValidCharConstraint ("test", vc, null, "1.0", contextInfo));
        // TODO: fix reg ex so it allows 4.0 but excludes 4.1, 4.2 etc...
        //  assertNotNull (v.processValidCharConstraint ("test", vc, null, "4.1"));
        assertNotNull (v.processValidCharConstraint ("test", vc, null, "1", contextInfo));
        assertNull (v.processValidCharConstraint ("test", vc, null, "1.0", contextInfo));
        assertNotNull (v.processValidCharConstraint ("test", vc, null, "\n", contextInfo));
        assertNotNull (v.processValidCharConstraint ("test", vc, null, "\r", contextInfo));
        assertNotNull (v.processValidCharConstraint ("test", vc, null, "\t", contextInfo));

        id = "positive.integer";
        vc = vcs.get (id);
        assertNotNull (vc);
        assertNull (v.processValidCharConstraint ("test", vc, null, "1", contextInfo));
        assertNull (v.processValidCharConstraint ("test", vc, null, "22", contextInfo));
        assertNull (v.processValidCharConstraint ("test", vc, null, "33", contextInfo));
        assertNotNull (v.processValidCharConstraint ("test", vc, null, "0", contextInfo));
        assertNotNull (v.processValidCharConstraint ("test", vc, null, "1.0", contextInfo));
        assertNotNull (v.processValidCharConstraint ("test", vc, null, "-1", contextInfo));
        assertNotNull (v.processValidCharConstraint ("test", vc, null, "\n", contextInfo));
        assertNotNull (v.processValidCharConstraint ("test", vc, null, "\r", contextInfo));
        assertNotNull (v.processValidCharConstraint ("test", vc, null, "\t", contextInfo));

        id = "relationalOperator";
        vc = vcs.get (id);
        assertNotNull (vc);
        assertNull (v.processValidCharConstraint ("test", vc, null, "=", contextInfo));
        assertNull (v.processValidCharConstraint ("test", vc, null, ">", contextInfo));
        assertNull (v.processValidCharConstraint ("test", vc, null, "<", contextInfo));
        assertNull (v.processValidCharConstraint ("test", vc, null, ">=", contextInfo));
        assertNull (v.processValidCharConstraint ("test", vc, null, "<=", contextInfo));
        assertNull (v.processValidCharConstraint ("test", vc, null, "<>", contextInfo));
        assertNotNull (v.processValidCharConstraint ("test", vc, null, "!=", contextInfo));
        assertNotNull (v.processValidCharConstraint ("test", vc, null, "=>", contextInfo));
        assertNotNull (v.processValidCharConstraint ("test", vc, null, "=<", contextInfo));
        assertNotNull (v.processValidCharConstraint ("test", vc, null, "0", contextInfo));
        assertNotNull (v.processValidCharConstraint ("test", vc, null, "1.0", contextInfo));
        assertNotNull (v.processValidCharConstraint ("test", vc, null, "-1", contextInfo));
        assertNotNull (v.processValidCharConstraint ("test", vc, null, "\n", contextInfo));
        assertNotNull (v.processValidCharConstraint ("test", vc, null, "\r", contextInfo));
        assertNotNull (v.processValidCharConstraint ("test", vc, null, "\t", contextInfo));

        id = "alpha";
        vc = vcs.get (id);
        assertNotNull (vc);
        assertNull (v.processValidCharConstraint ("test", vc, null, "a", contextInfo));
        assertNull (v.processValidCharConstraint ("test", vc, null, "A", contextInfo));
        assertNull (v.processValidCharConstraint ("test", vc, null, "Z", contextInfo));
        assertNull (v.processValidCharConstraint ("test", vc, null, "zzzzz", contextInfo));
        assertNull (v.processValidCharConstraint ("test", vc, null, "abcdefghijklmnopqrstuvwxyz", contextInfo));
        assertNotNull (v.processValidCharConstraint ("test", vc, null, "1", contextInfo));
        assertNotNull (v.processValidCharConstraint ("test", vc, null, "0", contextInfo));
        assertNotNull (v.processValidCharConstraint ("test", vc, null, "\n", contextInfo));
        assertNotNull (v.processValidCharConstraint ("test", vc, null, "\r", contextInfo));
        assertNotNull (v.processValidCharConstraint ("test", vc, null, "\t", contextInfo));

        id = "upperCase";
        vc = vcs.get (id);
        assertNotNull (vc);
        assertNull (v.processValidCharConstraint ("test", vc, null, "AB", contextInfo));
        assertNull (v.processValidCharConstraint ("test", vc, null, "A", contextInfo));
        assertNull (v.processValidCharConstraint ("test", vc, null, "ABCDEFGHIJKLMNOPQRSTUVWXYZ", contextInfo));
        assertNull (v.processValidCharConstraint ("test", vc, null, "-", contextInfo));
        assertNull (v.processValidCharConstraint ("test", vc, null, ".", contextInfo));
        assertNull (v.processValidCharConstraint ("test", vc, null, "1", contextInfo));
        assertNull (v.processValidCharConstraint ("test", vc, null, "0", contextInfo));
        assertNotNull (v.processValidCharConstraint ("test", vc, null, "a", contextInfo));
        assertNotNull (v.processValidCharConstraint ("test", vc, null, "\n", contextInfo));
        assertNotNull (v.processValidCharConstraint ("test", vc, null, "\r", contextInfo));
        assertNotNull (v.processValidCharConstraint ("test", vc, null, "\t", contextInfo));

        id = "upperAlpha";
        vc = vcs.get (id);
        assertNotNull (vc);
        assertNull (v.processValidCharConstraint ("test", vc, null, "AB", contextInfo));
        assertNull (v.processValidCharConstraint ("test", vc, null, "A", contextInfo));
        assertNull (v.processValidCharConstraint ("test", vc, null,
                "ABCDEFGHIJKLMNOPQRSTUVWXYZ", contextInfo));
        assertNotNull (v.processValidCharConstraint ("test", vc, null, "a", contextInfo));
        assertNotNull (v.processValidCharConstraint ("test", vc, null, "1", contextInfo));
        assertNotNull (v.processValidCharConstraint ("test", vc, null, "0", contextInfo));
        assertNotNull (v.processValidCharConstraint ("test", vc, null, "\n", contextInfo));
        assertNotNull (v.processValidCharConstraint ("test", vc, null, "\r", contextInfo));
        assertNotNull (v.processValidCharConstraint ("test", vc, null, "\t", contextInfo));

        id = "lineText";
        vc = vcs.get (id);
        assertNotNull (vc);
        assertNull (v.processValidCharConstraint ("test", vc, null, "a", contextInfo));
        assertNull (v.processValidCharConstraint ("test", vc, null, "A", contextInfo));
        assertNull (v.processValidCharConstraint ("test", vc, null, "Z", contextInfo));
        assertNull (v.processValidCharConstraint ("test", vc, null, "Z", contextInfo));
        assertNull (v.processValidCharConstraint ("test", vc, null, "!", contextInfo));
        assertNull (v.processValidCharConstraint ("test", vc, null, "@", contextInfo));
        assertNull (v.processValidCharConstraint ("test", vc, null, "#", contextInfo));
        assertNull (v.processValidCharConstraint ("test", vc, null, "$", contextInfo));
        assertNull (v.processValidCharConstraint ("test", vc, null, "%", contextInfo));
        //TODO: maybe allow these special characters
        assertNotNull (v.processValidCharConstraint ("test", vc, null, "^", contextInfo));
        assertNull (v.processValidCharConstraint ("test", vc, null, "&", contextInfo));
        assertNotNull (v.processValidCharConstraint ("test", vc, null, "*", contextInfo));
        assertNull (v.processValidCharConstraint ("test", vc, null, "(", contextInfo));
        assertNull (v.processValidCharConstraint ("test", vc, null, ")", contextInfo));
        assertNull (v.processValidCharConstraint ("test", vc, null, "_", contextInfo));
        assertNull (v.processValidCharConstraint ("test", vc, null, "+", contextInfo));
        assertNull (v.processValidCharConstraint ("test", vc, null, "-", contextInfo));
        assertNull (v.processValidCharConstraint ("test", vc, null, "=", contextInfo));
        assertNotNull (v.processValidCharConstraint ("test", vc, null, "{", contextInfo));
        assertNotNull (v.processValidCharConstraint ("test", vc, null, "}", contextInfo));
        assertNull (v.processValidCharConstraint ("test", vc, null, "[", contextInfo));
        assertNull (v.processValidCharConstraint ("test", vc, null, "]", contextInfo));
        assertNotNull (v.processValidCharConstraint ("test", vc, null, "|", contextInfo));
        assertNull (v.processValidCharConstraint ("test", vc, null, "\\", contextInfo));
        assertNull (v.processValidCharConstraint ("test", vc, null, ",", contextInfo));
        assertNull (v.processValidCharConstraint ("test", vc, null, ".", contextInfo));
        assertNull (v.processValidCharConstraint ("test", vc, null, "/", contextInfo));
        assertNull (v.processValidCharConstraint ("test", vc, null, "<", contextInfo));
        assertNull (v.processValidCharConstraint ("test", vc, null, ">", contextInfo));
        assertNull (v.processValidCharConstraint ("test", vc, null, "?", contextInfo));
        assertNotNull (v.processValidCharConstraint ("test", vc, null, "~", contextInfo));
        assertNotNull (v.processValidCharConstraint ("test", vc, null, "`", contextInfo));

        assertNull (v.processValidCharConstraint ("test", vc, null, "zzzzz", contextInfo));
        assertNull (v.processValidCharConstraint ("test", vc, null,
                "abcdefghijklmnopqrstuvwxyz", contextInfo));
        assertNull (v.processValidCharConstraint ("test", vc, null,
                "ABCDEFGHIJKLMNOPQRSTUVWXYZ", contextInfo));
        assertNull (v.processValidCharConstraint ("test", vc, null, "1", contextInfo));
        assertNull (v.processValidCharConstraint ("test", vc, null, "0", contextInfo));
        assertNotNull (v.processValidCharConstraint ("test", vc, null, "\n", contextInfo));
        assertNotNull (v.processValidCharConstraint ("test", vc, null, "\r", contextInfo));
        assertNull (v.processValidCharConstraint ("test", vc, null, "\t", contextInfo));

        id = "multiLineText";
        vc = vcs.get (id);
        assertNotNull (vc);
        assertNull (v.processValidCharConstraint ("test", vc, null, "a", contextInfo));
        assertNull (v.processValidCharConstraint ("test", vc, null, "A", contextInfo));
        assertNull (v.processValidCharConstraint ("test", vc, null, "Z", contextInfo));
        assertNull (v.processValidCharConstraint ("test", vc, null, "Z", contextInfo));
        assertNull (v.processValidCharConstraint ("test", vc, null, "!", contextInfo));
        assertNull (v.processValidCharConstraint ("test", vc, null, "@", contextInfo));
        assertNull (v.processValidCharConstraint ("test", vc, null, "#", contextInfo));
        assertNull (v.processValidCharConstraint ("test", vc, null, "$", contextInfo));
        assertNull (v.processValidCharConstraint ("test", vc, null, "%", contextInfo));
        //TODO: maybe allow these special characters
        assertNotNull (v.processValidCharConstraint ("test", vc, null, "^", contextInfo));
        assertNull (v.processValidCharConstraint ("test", vc, null, "&", contextInfo));
        assertNotNull (v.processValidCharConstraint ("test", vc, null, "*", contextInfo));
        assertNull (v.processValidCharConstraint ("test", vc, null, "(", contextInfo));
        assertNull (v.processValidCharConstraint ("test", vc, null, ")", contextInfo));
        assertNull (v.processValidCharConstraint ("test", vc, null, "_", contextInfo));
        assertNull (v.processValidCharConstraint ("test", vc, null, "+", contextInfo));
        assertNull (v.processValidCharConstraint ("test", vc, null, "-", contextInfo));
        assertNull (v.processValidCharConstraint ("test", vc, null, "=", contextInfo));
        assertNotNull (v.processValidCharConstraint ("test", vc, null, "{", contextInfo));
        assertNotNull (v.processValidCharConstraint ("test", vc, null, "}", contextInfo));
        assertNull (v.processValidCharConstraint ("test", vc, null, "[", contextInfo));
        assertNull (v.processValidCharConstraint ("test", vc, null, "]", contextInfo));
        assertNotNull (v.processValidCharConstraint ("test", vc, null, "|", contextInfo));
        assertNull (v.processValidCharConstraint ("test", vc, null, "\\", contextInfo));
        assertNull (v.processValidCharConstraint ("test", vc, null, ",", contextInfo));
        assertNull (v.processValidCharConstraint ("test", vc, null, ".", contextInfo));
        assertNull (v.processValidCharConstraint ("test", vc, null, "/", contextInfo));
        assertNull (v.processValidCharConstraint ("test", vc, null, "<", contextInfo));
        assertNull (v.processValidCharConstraint ("test", vc, null, ">", contextInfo));
        assertNull (v.processValidCharConstraint ("test", vc, null, "?", contextInfo));
        assertNotNull (v.processValidCharConstraint ("test", vc, null, "~", contextInfo));
        assertNotNull (v.processValidCharConstraint ("test", vc, null, "`", contextInfo));

        assertNull (v.processValidCharConstraint ("test", vc, null, "zzzzz", contextInfo));
        assertNull (v.processValidCharConstraint ("test", vc, null,
                "abcdefghijklmnopqrstuvwxyz", contextInfo));
        assertNull (v.processValidCharConstraint ("test", vc, null,
                "ABCDEFGHIJKLMNOPQRSTUVWXYZ", contextInfo));
        assertNull (v.processValidCharConstraint ("test", vc, null, "1", contextInfo));
        assertNull (v.processValidCharConstraint ("test", vc, null, "0", contextInfo));
        assertNull (v.processValidCharConstraint ("test", vc, null, "\n", contextInfo));
        assertNull (v.processValidCharConstraint ("test", vc, null, "\r", contextInfo));
        assertNull (v.processValidCharConstraint ("test", vc, null, "\t", contextInfo));

        // If you get this error -- don't just change the number
        // also add a unit test for new valid chars definition that you must have added into the base dictionary!
        assertEquals (13, vcs.size ());
    }
}
