package org.kuali.student.lum.ui.selenium;

import java.util.List;
import java.util.regex.Pattern;
import java.util.concurrent.TimeUnit;
import org.junit.*;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;

public class CreateCourseTest {
    private WebDriver driver;
    private StringBuffer verificationErrors = new StringBuffer();

    //@Before
    public void setUp() throws Exception {
        driver = new FirefoxDriver();
        driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
    }

    //@Test
    public void testCreateCourseTest() throws Exception {
        driver.get("http://dev.ks.kuali.org/login.jsp");
        //driver.get("http://localhost:8081/ks-embedded-dev/login.jsp");
        //Login Page
        WebElement logonUserName = driver.findElement(By.id("j_username"));
        typeValue(logonUserName, "admin");
        WebElement logonPassword = driver.findElement(By.id("j_password"));
        typeValue(logonPassword, "admin");
        driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
        //Main Page
        By selectAnAreaPanel = By.id("gwt-debug-Application-Header-Select-an-area--panel-0");
        for (int second = 0;; second++) {
            if (second >= 60)
                fail("timeout");
            try {
                if (isElementPresent(selectAnAreaPanel))
                    break;
            } catch (Exception e) {}
            Thread.sleep(1000);
        }

        driver.findElement(selectAnAreaPanel).click();
        driver.findElement(By.id("gwt-debug-Curriculum-Management-label")).click();
        By createACourseLink = By.linkText("Create a Course");
        for (int second = 0;; second++) {
            if (second >= 60)
                fail("timeout");
            try {
                if (isElementPresent(createACourseLink))
                    break;
            } catch (Exception e) {}
            Thread.sleep(1000);
        }

        driver.findElement(createACourseLink).click();
        //Create a Course Popup
        By useCurriculumReviewCheckbox = By.id("gwt-debug-Use-curriculum-review-process-input");
        for (int second = 0;; second++) {
            if (second >= 60)
                fail("timeout");
            try {
                if (isElementPresent(useCurriculumReviewCheckbox))
                    break;
            } catch (Exception e) {}
            Thread.sleep(1000);
        }

        driver.findElement(useCurriculumReviewCheckbox).click();
        driver.findElement(By.id("gwt-debug-Start-Proposal-anchor")).click();
        //Start Proposal
        //Course Info Section
        By byProposalName = By.xpath("//input[@id='gwt-debug-proposal-name']");
        for (int second = 0;; second++) {
            if (second >= 60)
                fail("timeout");
            try {
                if (isElementPresent(byProposalName))
                    break;
            } catch (Exception e) {}
            Thread.sleep(1000);
        }

        WebElement proposalName = driver.findElement(byProposalName);
        typeValue(proposalName, "Selenium Tester");
        //proposalName.click();
        WebElement courseTitle = driver.findElement(By.xpath("//input[@id='gwt-debug-courseTitle']"));
        typeValue(courseTitle, "Selenium Tester");
        WebElement transcriptTitle = driver.findElement(By.xpath("//input[@id='gwt-debug-transcriptTitle']"));
        typeValue(transcriptTitle, "Selenium Transcript");
        WebElement subjectArea = driver.findElement(By.xpath("//input[@id='gwt-debug-subjectArea']"));
        typeValue(subjectArea, "CHEM", Keys.RIGHT);
        // ERROR: Caught exception [ERROR: Unsupported command [keyDown]]
        // ERROR: Caught exception [ERROR: Unsupported command [keyUp]]
        for (int second = 0;; second++) {
            if (second >= 60)
                fail("timeout");
            try {
                if (isElementPresent(By.xpath("//td[@role='menuitem']")))
                    break;
            } catch (Exception e) {}
            Thread.sleep(1000);
        }

        driver.findElement(By.xpath("//td[@role='menuitem']")).click();
        WebElement courseNumberSuffix = driver.findElement(By.xpath("//input[@id='gwt-debug-courseNumberSuffix']"));
        typeValue(courseNumberSuffix, "111");
        WebElement instructors = driver.findElement(By.xpath("//input[@id='gwt-debug-instructors']"));
        typeValue(instructors, "Admin 1", Keys.RIGHT);
        // ERROR: Caught exception [ERROR: Unsupported command [keyDown]]
        // ERROR: Caught exception [ERROR: Unsupported command [keyUp]]
        for (int second = 0;; second++) {
            if (second >= 60)
                fail("timeout");
            try {
                if (isElementPresent(By.xpath("//td[@role='menuitem']")))
                    break;
            } catch (Exception e) {}
            Thread.sleep(1000);
        }

        driver.findElement(By.xpath("//td[@role='menuitem']")).click();
        //Always add 1 second delay before 'Add to list' buttons
        Thread.sleep(1000);
        driver.findElement(By.id("gwt-debug-instructors-Add-to-list-anchor")).click();
        WebElement description = driver.findElement(By.xpath("//textarea[@id='gwt-debug-descr-plain']"));
        //description.click();
        typeValue(description, "Selenium Description");
        WebElement rationale = driver.findElement(By.xpath("//textarea[@id='gwt-debug-proposal-rationale']"));
        typeValue(rationale, "Selenium Rationale");
        driver.findElement(By.id("gwt-debug-Save-and-Continue-anchor")).click();

        //Governance Section
        for (int second = 0;; second++) {
            if (second >= 60)
                fail("timeout");
            try {
                if (isElementPresent(By.id("gwt-debug-All-input")))
                    break;
            } catch (Exception e) {}
            Thread.sleep(1000);
        }

        driver.findElement(By.id("gwt-debug-All-input")).click();
        WebElement unitContentOwner = driver.findElement(By.xpath("//select[@id='gwt-debug-unitsContentOwner']"));
        //unitContentOwner.click();
        // ERROR: Caught exception [ERROR: Unsupported command [select]]
        clickOptionInSelectList(unitContentOwner, "68");
        //driver.findElement(By.cssSelector("option[value=\"68\"]")).click();
        //Always sleep before 'Add to list' buttons
        Thread.sleep(1000);
        driver.findElement(By.id("gwt-debug-unitsContentOwner-Add-to-list-anchor")).click();
        driver.findElement(By.id("gwt-debug-Save-and-Continue-anchor")).click();

        //Course Logistics Section
        for (int second = 0;; second++) {
            if (second >= 60)
                fail("timeout");
            try {
                if (isElementPresent(By.id("gwt-debug-Any-input")))
                    break;
            } catch (Exception e) {}
            Thread.sleep(1000);
        }

        driver.findElement(By.id("gwt-debug-Any-input")).click();
        driver.findElement(By.id("gwt-debug-Completed-notation-input")).click();
        driver.findElement(By.id("gwt-debug-Standard-final-Exam-input")).click();
        WebElement outcome1Type = driver.findElement(By.xpath("//select[@id='gwt-debug-creditOptions-0-type']"));
        clickOptionInSelectList(outcome1Type, "kuali.resultComponentType.credit.degree.fixed");
        // ERROR: Caught exception [ERROR: Unsupported command [select]]
        WebElement outcome1CreditValue = driver.findElement(By
                .xpath("//input[@id='gwt-debug-creditOptions-0-fixedCreditValue']"));
        typeValue(outcome1CreditValue, "5");
        driver.findElement(By.id("gwt-debug-Save-and-Continue-anchor")).click();

        //Learning Objectives Section
        for (int second = 0;; second++) {
            if (second >= 60)
                fail("timeout");
            try {
                if (isElementPresent(By.xpath("//textarea[@id='gwt-debug-courseSpecificLOs-0-loInfo-desc-plain']")))
                    break;
            } catch (Exception e) {}
            Thread.sleep(1000);
        }
        WebElement learningObjective1 = driver.findElement(By
                .xpath("//textarea[@id='gwt-debug-courseSpecificLOs-0-loInfo-desc-plain']"));
        typeValue(learningObjective1, "Selenium Learning Objectives");

        driver.findElement(By.id("gwt-debug-courseSpecificLOs-0-loInfo-desc-plain-Browse-for-categories")).click();
        for (int second = 0;; second++) {
            if (second >= 60)
                fail("timeout");
            try {
                if (isElementPresent(By.id("gwt-debug-Communication-Skill-input")))
                    break;
            } catch (Exception e) {}
            Thread.sleep(1000);
        }

        driver.findElement(By.id("gwt-debug-Communication-Skill-input")).click();
        driver.findElement(By.id("gwt-debug-Add-anchor")).click();
        driver.findElement(By.id("gwt-debug-Save-and-Continue-anchor")).click();

        //Course Requisites Section
        for (int second = 0;; second++) {
            if (second >= 60)
                fail("timeout");
            try {
                if (isElementPresent(By.id("gwt-debug-Add-Student-Eligibility---Prerequisite-anchor")))
                    break;
            } catch (Exception e) {}
            Thread.sleep(1000);
        }

        driver.findElement(By.id("gwt-debug-Add-Student-Eligibility---Prerequisite-anchor")).click();
        for (int second = 0;; second++) {
            if (second >= 60)
                fail("timeout");
            try {
                if (isElementPresent(By.id("gwt-debug-Rule-Type")))
                    break;
            } catch (Exception e) {}
            Thread.sleep(1000);
        }

        WebElement ruleType = driver.findElement(By.id("gwt-debug-Rule-Type"));
        clickOptionInSelectList(ruleType, "19");
        //ruleType.click();
        // ERROR: Caught exception [ERROR: Unsupported command [select]]
        //driver.findElement(By.cssSelector("option[value=\"19\"]")).click();
        for (int second = 0;; second++) {
            if (second >= 60)
                fail("timeout");
            try {
                if (isElementPresent(By
                        .xpath("//input[@id='gwt-debug-kuali-reqComponent-field-type-value-positive-integer']")))
                    break;
            } catch (Exception e) {}
            Thread.sleep(1000);
        }
        WebElement minCreditValue = driver.findElement(By
                .xpath("//input[@id='gwt-debug-kuali-reqComponent-field-type-value-positive-integer']"));
        typeValue(minCreditValue, "10");
        driver.findElement(By.id("gwt-debug-Add-anchor")).click();
        //Give the 'Save' button a chance to become active
        Thread.sleep(1000);
        driver.findElement(By.id("gwt-debug-Save-anchor")).click();
        driver.findElement(By.id("gwt-debug-Save-and-Continue-anchor")).click();

        //Active Dates Section
        for (int second = 0;; second++) {
            if (second >= 60)
                fail("timeout");
            try {
                if (isElementPresent(By.xpath("//select[@id='gwt-debug-startTerm']")))
                    break;
            } catch (Exception e) {}
            Thread.sleep(1000);
        }
        WebElement startTerm = driver.findElement(By.xpath("//select[@id='gwt-debug-startTerm']"));
        clickOptionInSelectList(startTerm, "kuali.atp.SP2010-2011");
        //startTerm.click();
        // ERROR: Caught exception [ERROR: Unsupported command [select]]
        //driver.findElement(By.cssSelector("option[value=\"kuali.atp.SP2010-2011\"]")).click();
        driver.findElement(By.id("gwt-debug-Save-and-Continue-anchor")).click();

        //Financials Section
        for (int second = 0;; second++) {
            if (second >= 60)
                fail("timeout");
            try {
                if (isElementPresent(By.xpath("//textarea[@id='gwt-debug-feeJustification-plain']")))
                    break;
            } catch (Exception e) {}
            Thread.sleep(1000);
        }
        WebElement justificationOfFees = driver.findElement(By
                .xpath("//textarea[@id='gwt-debug-feeJustification-plain']"));
        typeValue(justificationOfFees, "Selenium Justification of Fees");
        driver.findElement(By.id("gwt-debug-revenues-Add-another-Organization-anchor")).click();
        driver.findElement(By.id("gwt-debug-revenues-0-affiliatedOrgs-0-orgId-Advanced-Search-anchor")).click();
        driver.findElement(By.id("gwt-debug-Search-anchor")).click();
        for (int second = 0;; second++) {
            if (second >= 60)
                fail("timeout");
            try {
                if (isElementPresent(By.id("gwt-debug-Academics-Academic-Affairs--College--input")))
                    break;
            } catch (Exception e) {}
            Thread.sleep(1000);
        }

        driver.findElement(By.id("gwt-debug-Academics-Academic-Affairs--College--input")).click();
        driver.findElement(By.id("gwt-debug-Search-anchor")).click();
        WebElement revenue1Percentage = driver.findElement(By
                .xpath("//input[@id='gwt-debug-revenues-0-affiliatedOrgs-0-percentage']"));
        typeValue(revenue1Percentage, "100");
        driver.findElement(By.id("gwt-debug-expenditure-affiliatedOrgs-Add-another-Organization-anchor")).click();
        driver.findElement(By.id("gwt-debug-expenditure-affiliatedOrgs-0-orgId-Advanced-Search-anchor")).click();
        driver.findElement(By.id("gwt-debug-Search-anchor")).click();
        driver.findElement(By.id("gwt-debug-Admissions-Undergraduate-Admissions-input")).click();
        driver.findElement(By.id("gwt-debug-Search-anchor")).click();
        WebElement expenditure1Percentage = driver.findElement(By
                .xpath("//input[@id='gwt-debug-expenditure-affiliatedOrgs-0-percentage']"));
        typeValue(expenditure1Percentage, "100");
        driver.findElement(By.id("gwt-debug-Save-and-Continue-anchor")).click();

        //Authors & Collaborators Section
        for (int second = 0;; second++) {
            if (second >= 60)
                fail("timeout");
            try {
                if (isElementPresent(By.id("gwt-debug-Name-Advanced-Search-anchor")))
                    break;
            } catch (Exception e) {}
            Thread.sleep(1000);
        }

        driver.findElement(By.id("gwt-debug-Name-Advanced-Search-anchor")).click();
        driver.findElement(By.id("gwt-debug-Search-anchor")).click();
        for (int second = 0;; second++) {
            if (second >= 60)
                fail("timeout");
            try {
                if (isElementPresent(By.id("gwt-debug-Admin-1--Test-1101-testadmin1-Admin-1--Test--testadmin1--input")))
                    break;
            } catch (Exception e) {}
            Thread.sleep(1000);
        }

        driver.findElement(By.id("gwt-debug-Admin-1--Test-1101-testadmin1-Admin-1--Test--testadmin1--input")).click();
        driver.findElement(By.id("gwt-debug-Search-anchor")).click();
        WebElement permission = driver.findElement(By.xpath("//select[@id='gwt-debug-Permission']"));
        //permission.click();
        clickOptionInSelectList(permission, "KS-SYS~Edit Document");
        // ERROR: Caught exception [ERROR: Unsupported command [select]]
        //driver.findElement(By.cssSelector("option[value=\"KS-SYS~Edit Document\"]")).click();
        WebElement actionRequest = driver.findElement(By.xpath("//select[@id='gwt-debug-Action-Request']"));
        //actionRequest.click();
        clickOptionInSelectList(actionRequest, "F");
        // ERROR: Caught exception [ERROR: Unsupported command [select]]
        //driver.findElement(By.cssSelector("option[value=\"F\"]")).click();
        driver.findElement(By.id("gwt-debug-Add-Collaborator-anchor")).click();
        driver.findElement(By.id("gwt-debug-Save-and-Continue-anchor")).click();

        //Supporting Documents Section
        for (int second = 0;; second++) {
            if (second >= 60)
                fail("timeout");
            try {
                if (isElementPresent(By.id("gwt-debug-Continue-anchor")))
                    break;
            } catch (Exception e) {}
            Thread.sleep(1000);
        }

        driver.findElement(By.id("gwt-debug-Continue-anchor")).click();

        //Finally the Review Proposal Section is displayed
    }

    //@After
    public void tearDown() throws Exception {
        driver.quit();
        String verificationErrorString = verificationErrors.toString();
        if (!"".equals(verificationErrorString)) {
            fail(verificationErrorString);
        }
    }

    private boolean isElementPresent(By by) {
        try {
            driver.findElement(by);
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    private void typeValue(final WebElement webElement, CharSequence... keysToSend) {
        webElement.clear();
        webElement.sendKeys(keysToSend);
    }

    private void clickOptionInSelectList(WebElement selectElement, String optionValue) {
        List<WebElement> selectOptions = selectElement.findElements(By.tagName("option"));
        for (WebElement option : selectOptions) {
            if (optionValue != null) {
                //select element by option value attribute
                if (optionValue.equals(option.getAttribute("value"))) {
                    option.click();
                    break;
                }
            }
        }
    }
}
