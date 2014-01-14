package org.kuali.student.ap.test.resourceBundles;

import org.junit.runner.RunWith;
import org.kuali.student.r2.common.dto.LocaleInfo;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Test class for fr locales
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:testKsapFrameworkInit-context.xml" })
public class TestResourceBundles_fr extends ResourceBundlesTestBase {

    @Override
    public String getPrefix() {
        return "fr_";
    }

    @Override
    public LocaleInfo getLocaleInfo() {
        LocaleInfo locale = new LocaleInfo();
        locale.setLocaleLanguage("fr");
        locale.setLocaleRegion("");
        locale.setLocaleVariant("");
        return locale;
    }
}
