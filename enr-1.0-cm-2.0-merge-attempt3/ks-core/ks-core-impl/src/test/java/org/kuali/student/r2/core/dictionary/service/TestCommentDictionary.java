package org.kuali.student.r2.core.dictionary.service;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import org.junit.Test;
import org.kuali.student.common.test.util.ContextInfoTestUtility;
import org.kuali.student.r1.common.dictionary.dto.ObjectStructureDefinition;
import org.kuali.student.r1.common.dictionary.service.impl.DictionaryTesterHelper;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.ValidationResultInfo;
import org.kuali.student.r2.common.validator.DefaultValidatorImpl;
import org.kuali.student.r1.common.validator.ServerDateParser;
import org.kuali.student.r2.core.comment.dto.CommentInfo;
import org.kuali.student.r2.core.comment.dto.TagInfo;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import static org.junit.Assert.*;

public class TestCommentDictionary
{

    @Test
    public void testLoadAtpDictionary ()
    {
        System.out.println ("testing comment dictionary");
        Set<String> startingClasses = new LinkedHashSet ();
        startingClasses.add (CommentInfo.class.getName ());
        startingClasses.add (TagInfo.class.getName ());
        String contextFile = "ks-comment-dictionary-context";
        String outFile = "target/" + contextFile + ".txt";
        DictionaryTesterHelper helper = new DictionaryTesterHelper (outFile,
                startingClasses,
                contextFile
                + ".xml", false);
        List<String> errors = helper.doTest ();
        if (errors.size () > 0)
        {
            fail ("failed dictionary validation:\n" + formatAsString (errors));
        }
    }

    private String formatAsString (List<String> errors)
    {
        int i = 0;
        StringBuilder builder = new StringBuilder ();
        for (String error : errors)
        {
            i ++;
            builder.append (i + ". " + error + "\n");
        }
        return builder.toString ();
    }

    @Test
    public void testCommentInfoValidation () throws OperationFailedException
    {
        ContextInfo contextInfo = ContextInfoTestUtility.getEnglishContextInfo();
        ApplicationContext ac = new ClassPathXmlApplicationContext (
                "classpath:ks-comment-dictionary-context.xml");
        System.out.println ("h2. Validation Test");
        DefaultValidatorImpl val = new DefaultValidatorImpl ();
        val.setDateParser (new ServerDateParser ());
        val.setSearchDispatcher (new MockSearchDispatcher ());
        CommentInfo info = new CommentInfo ();
        ObjectStructureDefinition os = (ObjectStructureDefinition) ac.getBean (
                info.getClass ().getName ());
        List<ValidationResultInfo> validationResults = val.validateObject (info, os, contextInfo);
        System.out.println ("h3. With just a blank");
        for (ValidationResultInfo vr : validationResults)
        {
            System.out.println (vr.getElement () + " " + vr.getMessage ());
        }
        assertEquals (4, validationResults.size ());
    }
}
