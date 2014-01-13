package org.kuali.student.ap.test.resourceBundles;

import org.junit.runner.RunWith;
import org.kuali.student.r2.common.dto.LocaleInfo;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Test class for default locales
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:testKsapFrameworkInit-context.xml" })
public class TestResourceBundles extends ResourceBundlesTestBase {

    @Override
    public String getPrefix() {
        return "";
    }

    @Override
    public LocaleInfo getLocaleInfo() {
        return null;
    }
}
