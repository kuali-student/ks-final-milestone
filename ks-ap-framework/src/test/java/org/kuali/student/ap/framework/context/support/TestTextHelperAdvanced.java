package org.kuali.student.ap.framework.context.support;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kuali.student.ap.framework.config.KsapFrameworkServiceLocator;
import org.kuali.student.ap.framework.context.TextHelper;
import org.kuali.student.r2.common.dto.LocaleInfo;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.assertEquals;

/**
 * Created with IntelliJ IDEA.
 * User: chmaurer
 * Date: 2/24/14
 * Time: 12:13 PM
 * To change this template use File | Settings | File Templates.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:textHelper-test-context.xml" })
@Transactional
@TransactionConfiguration(transactionManager = "JtaTxManager", defaultRollback = true)
public class TestTextHelperAdvanced {
    private static final Logger LOG = Logger.getLogger(TestTextHelperAdvanced.class);
    private static final String TEXT = "This will get overridden by a different local";
    private static final String TEXT2 = "This is also only in the properties file and not in the DB.";

    private TextHelper th = null;

    @Before
    public void setUp() throws Throwable {
        //DefaultKsapContext.before("student1", getLocaleInfo());
        th = KsapFrameworkServiceLocator.getTextHelper();
    }

    @After
    public void tearDown() throws Throwable {
        DefaultKsapContext.after();
    }

    @Test
    public void testStuff() throws Exception {
        TestLocale en = new BaseLocale("en", "", "", "");
        TestLocale fr = new BaseLocale("fr", "", "", "fr_");
        TestLocale frFR = new BaseLocale("fr", "FR", "", "fr_FR_");

        int loops = 3;

        for (int i=0; i<loops; i++) {
            LOG.debug("Run iter #" + i + "...");
            doTest(en);
            doTest(fr);
            doTest(frFR);
        }
    }

    private void doTest(TestLocale locale)   {
        DefaultKsapContext.before("student1", locale.getLocaleInfo());

        String value = th.getText("testRegionOverride");
        assertEquals(locale.getText(TEXT), value);

//        String value2 = th.getText("langLocalePropsOnlyNoDB");
//        assertEquals(locale.getText(TEXT2), value2);

        DefaultKsapContext.after();
    }

    private interface TestLocale {
        public LocaleInfo getLocaleInfo();
        public String getText(String text);
    }

    private class BaseLocale implements TestLocale {
        private String language;
        private String region;
        private String variant;
        private String prefix;

        public BaseLocale(String language, String region, String variant, String prefix) {
            this.language = language;
            this.region = region;
            this.variant = variant;
            this.prefix = prefix;
        }
        public String getText(String text) {
            return prefix + text;
        }

        public LocaleInfo getLocaleInfo() {
            LocaleInfo locale = new LocaleInfo();
            locale.setLocaleLanguage(language);
            locale.setLocaleRegion(region);
            locale.setLocaleVariant(variant);
            return locale;
        }
    }
}
