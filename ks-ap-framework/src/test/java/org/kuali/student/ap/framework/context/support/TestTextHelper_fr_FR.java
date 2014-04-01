package org.kuali.student.ap.framework.context.support;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.kuali.student.r2.common.dto.LocaleInfo;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.assertEquals;

/**
 * Created with IntelliJ IDEA.
 * User: chmaurer
 * Date: 1/13/14
 * Time: 3:00 PM
 * To change this template use File | Settings | File Templates.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:textHelper-test-context.xml" })
@Transactional
@TransactionConfiguration(transactionManager = "JtaTxManager", defaultRollback = true)
public class TestTextHelper_fr_FR extends TestTextHelperBase {

    @Override
    public String getPrefix() {
        return "fr_FR_";
    }

    @Override
    public LocaleInfo getLocaleInfo() {
        LocaleInfo locale = new LocaleInfo();
        locale.setLocaleLanguage("fr");
        locale.setLocaleRegion("FR");
        locale.setLocaleVariant("");
        return locale;
    }

    // The methods below are being overridden since the MessageService only supports language and not region/variant

    private String getFrPrefix() {
        return "fr_";
    }

    @Test
    public void testSimpleTextLookup() throws Exception {
        String value = th.getText("simpleText");
        assertEquals(getFrPrefix() + "Simple Text", value);
    }

    @Test
    public void testLookupWithSubstitution() throws Exception {
        String value = th.getFormattedMessage("substitutionText", "blue");
        assertEquals(getFrPrefix() + "My favorite color is blue.", value);
    }

    @Test
    public void testLookupWithMultipleSubstitution() throws Exception {
        String value = th.getFormattedMessage("multipleSubstitutionText", "Apples", "Bananas");
        assertEquals(getFrPrefix() + "I like to eat Apples and Bananas.", value);
    }
}
