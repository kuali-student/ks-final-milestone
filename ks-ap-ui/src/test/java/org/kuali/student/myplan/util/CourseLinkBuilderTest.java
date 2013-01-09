package org.kuali.student.myplan.util;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.kuali.student.myplan.utils.CourseLinkBuilder;
import org.kuali.student.r2.common.dto.ContextInfo;

@RunWith(value = Parameterized.class)
public class CourseLinkBuilderTest {

    private String rawText, cookedText;

    public CourseLinkBuilderTest(String rawText, String cookedText) {
        this.rawText = rawText;
        this.cookedText = cookedText;
    }

    /*
        This Component (Course Link Builder) is incomplete
     */

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        Object[][] data = new Object[][] {
            //{"Complete one GENOME 361, 371 or BIOL/FISH 340", "\n\t\t\t\t\t\t\tComplete one GENOME 361, 371 or BIOL/FISH 340 \n\t\t\t\t\t\t" },
            {"CIV 201","[unknown::Unknown::CIV 201]"},
            /*{ "20 credits, including ECON 200 and ECON 201:",
                "20 credits, including [unknown::Unknown::ECON 200] and [unknown::Unknown::ECON 201]:" },
            { "19 - 20 credits, including MATH 112 or 124.",
                    "19 - 20 credits, including [unknown::Unknown::MATH 112] or [unknown::Unknown::124]."},
            { "You must complete MUHST 211, 212, & 210:",
                    "You must complete [unknown::Unknown::MUHST 211], [unknown::Unknown::212], & [unknown::Unknown::210]:"},
            {"-  3) Statistics: Either CS&SS 220, STAT 311 or Q SCI 381",
                "-  3) Statistics: Either [unknown::Unknown::CS&SS 220], [unknown::Unknown::STAT 311] or [unknown::Unknown::Q SCI 381]"},
            {" +  4) One from BIOL 220, PHYS 115 & 118, 122,",
                    " +  4) One from [unknown::Unknown::BIOL 220], [unknown::Unknown::PHYS 115] & [unknown::Unknown::118], [unknown::Unknown::122],"},
            {" 18 credits in MUSAP 320 and/or 420",
                    " 18 credits in [unknown::Unknown::MUSAP 320] and/or [unknown::Unknown::420]"},
            {"From: C LIT 240, ENGL  110, 111, 121, 131, 182, 197, 198, 199, 281, 9",
                    "From: [unknown::Unknown::C LIT 240], [unknown::Unknown::ENGL  110], [unknown::Unknown::111], [unknown::Unknown::121], [unknown::Unknown::131], [unknown::Unknown::182], [unknown::Unknown::197], [unknown::Unknown::198], [unknown::Unknown::199], [unknown::Unknown::281], 9"},
            { "1) You must complete ECON 200:",
                    "1) You must complete [unknown::Unknown::ECON 200]:" },
            { "Civil Engineering Requirement: ECON 200, 201",
                    "Civil Engineering Requirement: [unknown::Unknown::ECON 200], [unknown::Unknown::201]"}  ,
            {"-  1) Part A Writing: One course from B CMU 301, 302, 410, ENGL 281, 381.",
                    "-  1) Part A Writing: One course from [unknown::Unknown::B CMU 301], [unknown::Unknown::302], [unknown::Unknown::410], [unknown::Unknown::ENGL 281], [unknown::Unknown::381]."},
            {" +  4) One from BIOL 220, PHYS 115 & 118, 122,",
                    " +  4) One from [unknown::Unknown::BIOL 220], [unknown::Unknown::PHYS 115] & [unknown::Unknown::118], [unknown::Unknown::122],"},
            {"MATH 307 And 320 or AMATH 351 plus MATH 308 or AMATH 352",
                    "[unknown::Unknown::MATH 307] And [unknown::Unknown::320] or [unknown::Unknown::AMATH 351] plus [unknown::Unknown::MATH 308] or [unknown::Unknown::AMATH 352]"},
            {"From: MATH  124 OR 134, 125 OR 135, 126 OR 136",
                "From: [unknown::Unknown::MATH  124] OR [unknown::Unknown::134], [unknown::Unknown::125] OR [unknown::Unknown::135], [unknown::Unknown::126] OR [unknown::Unknown::136]"},
            {"  -  Required Courses: E E 271, 331, 332, 361, 433 plus 473 or 482",
                "  -  Required Courses: [unknown::Unknown::E E 271], [unknown::Unknown::331], [unknown::Unknown::332], [unknown::Unknown::361], [unknown::Unknown::433] plus [unknown::Unknown::473] or [unknown::Unknown::482]"},
            {"Suggested Electives: E E 341, 486",
                "Suggested Electives: [unknown::Unknown::E E 341], [unknown::Unknown::486]"},
            {"-  1) Calculus with Analytical Geometry: MATH 124-126",
                "-  1) Calculus with Analytical Geometry: MATH 124-126"},
            {"MATH 307 or AMATH 351 plus MATH 308 or AMATH 352",
                "[unknown::Unknown::MATH 307] or [unknown::Unknown::AMATH 351] plus [unknown::Unknown::MATH 308] or [unknown::Unknown::AMATH 352]"},
            {"-  3) Advanced Multivariable Calculus: MATH 324",
                "-  3) Advanced Multivariable Calculus: [unknown::Unknown::MATH 324]"},
            {"-  1) Part A Writing: One course from B CMU 301, 302, 410, ENGL 281, 381.",
                "-  1) Part A Writing: One course from [unknown::Unknown::B CMU 301], [unknown::Unknown::302], [unknown::Unknown::410], [unknown::Unknown::ENGL 281], [unknown::Unknown::381]."},
            {"-  1) Introductory Accounting I (ACCTG 215)   <5>",
                "-  1) Introductory Accounting I ([unknown::Unknown::ACCTG 215])   <5>"},
            {"(excluding GEN ST 350), which may include up to 14 credits",
                "(excluding [unknown::Unknown::GEN ST 350]), which may include up to 14 credits"},
            {"1) Construction Engineering: CEE 404, 421, 425 or, with adviser approval, 498",
                "1) Construction Engineering: [unknown::Unknown::CEE 404], [unknown::Unknown::421], [unknown::Unknown::425] or, with adviser approval, [unknown::Unknown::498]"},
            {"You must complete MUSIC 113 and 119 (taken         concurrently) and 120, or a placement test:",
                "You must complete [unknown::Unknown::MUSIC 113] and [unknown::Unknown::119] (taken         concurrently) and [unknown::Unknown::120], or a placement test:"},
            {"-  2) MATH 125 and 126 along with either MATH 308 (preferred), 205, 307, or AMATH 352.",
                "-  2) [unknown::Unknown::MATH 125] and [unknown::Unknown::126] along with either [unknown::Unknown::MATH 308] (preferred), [unknown::Unknown::205], [unknown::Unknown::307], or [unknown::Unknown::AMATH 352]."},
            {"-  4) 12 credits in ESS courses numbered 401-488.",
                "-  4) 12 credits in ESS courses numbered 401-488."},
            {"The course from ESS 311-314 which you did not",
                "The course from ESS 311-314 which you did not"},
            {"2) A A 210 with a 2.0 or better",
                "2) [unknown::Unknown::A A 210] with a 2.0 or better"},
            {"-  1) Earth Systems Literacy: One course from ATM S 211, ESS 201, ESS/OCEAN 230, GEOG 205 or OCEAN 200",
                "-  1) Earth Systems Literacy: One course from [unknown::Unknown::ATM S 211], [unknown::Unknown::ESS 201], ESS/OCEAN 230, [unknown::Unknown::GEOG 205] or [unknown::Unknown::OCEAN 200]"},
            {"-  1) Biocultural Anthropology: BIO A 201",
                "-  1) Biocultural Anthropology: [unknown::Unknown::BIO A 201]"},
            {"-  3) Statistics: Either CS&SS/SOC/STAT 221, STAT 220, STAT 311 or Q SCI 381",
                "-  3) Statistics: Either CS&SS/SOC/STAT 221, [unknown::Unknown::STAT 220], [unknown::Unknown::STAT 311] or [unknown::Unknown::Q SCI 381]"},
            {"From: C LIT 240, ENGL  110, 111, 121, 131, 182, 197, 198,  199, 281, 9",
                "From: [unknown::Unknown::C LIT 240], [unknown::Unknown::ENGL  110], [unknown::Unknown::111], [unknown::Unknown::121], [unknown::Unknown::131], [unknown::Unknown::182], [unknown::Unknown::197], [unknown::Unknown::198],  [unknown::Unknown::199], [unknown::Unknown::281], 9"},
            {"From: BIOL  100, 118, 161, PSYCH 202, 357, GENOME261, 351",
                "From: [unknown::Unknown::BIOL  100], [unknown::Unknown::118], [unknown::Unknown::161], [unknown::Unknown::PSYCH 202], [unknown::Unknown::357], [unknown::Unknown::GENOME261], [unknown::Unknown::351]"},
            {"From: FRENCH204 to 206, 208 to 226, 228 to 233, 235, 236, 238 to 296, 298, 300, 306, 307(SP07  OR AFTER), 308 to 336, 338 to 399, 400 to 499",
                "From: [unknown::Unknown::FRENCH204] to [unknown::Unknown::206], [unknown::Unknown::208] to [unknown::Unknown::226], [unknown::Unknown::228] to [unknown::Unknown::233], [unknown::Unknown::235], [unknown::Unknown::236], [unknown::Unknown::238] to [unknown::Unknown::296], [unknown::Unknown::298], [unknown::Unknown::300], [unknown::Unknown::306], [unknown::Unknown::307](SP07  OR AFTER), [unknown::Unknown::308] to [unknown::Unknown::336], [unknown::Unknown::338] to [unknown::Unknown::399], [unknown::Unknown::400] to [unknown::Unknown::499]"},
            {"Civil Engineering Requirement: ECON 200, 201 or IND E 250",
                "Civil Engineering Requirement: [unknown::Unknown::ECON 200], [unknown::Unknown::201] or [unknown::Unknown::IND E 250]"},
            {"ECON 200 and 201 can also apply toward the I&S requirement, below.   -   Needs:  1 course",
                "[unknown::Unknown::ECON 200] and [unknown::Unknown::201] can also apply toward the I&S requirement, below.   -   Needs:  1 course"},
            {"SP09 SPAN  202  5.0 3.6   INTERMEDIATE",
                "SP09 [unknown::Unknown::SPAN  202]  5.0 3.6   INTERMEDIATE"},
            { "AU11 BIOL    180  5.0   2.7    INTRO BIOLOGY",
                "AU11 [unknown::Unknown::BIOL    180]  5.0   2.7    INTRO BIOLOGY"},
            {"Biology Admission Requirement: You must complete one of the following options: A) BIOL 180 or 201 with a 2.5 or better; B) One of following sequences with a minimum 2.00 GPA: BIOL 180, 200, 220 -or- 201, 202, 203",
            //Biology Admission Requirement: You must complete one of the following options: A) BIOL 180 or 201 with a 2.5 or better; B) One of following sequences with a minimum 2.00 GPA: BIOL 180, 200, 220 -or- 201, 202, 203
                "Biology Admission Requirement: You must complete one of the following options: A) [unknown::Unknown::BIOL 180] or [unknown::Unknown::201] with a 2.5 or better; B) One of following sequences with a minimum 2.00 GPA: [unknown::Unknown::BIOL 180], [unknown::Unknown::200], [unknown::Unknown::220] -or- [unknown::Unknown::201], [unknown::Unknown::202], [unknown::Unknown::203]"},
            //{"blah blah CEE 473 INTERMEDIATE",
           //     "SP09 [unknown::Unknown::SPAN  202]  5.0 3.6   INTERMEDIATE"},
            {"       MATH 134, 135, 136 and 324 or 334","       [unknown::Unknown::MATH 134], [unknown::Unknown::135], [unknown::Unknown::136] and [unknown::Unknown::324] or [unknown::Unknown::334]"},
        */
        };
        return Arrays.asList(data);
    }

    @Test
    public void testLinkBuilder() {
          assertEquals(cookedText, CourseLinkBuilder.makeLinks(rawText, CourseLinkBuilder.LINK_TEMPLATE.TEST, new ContextInfo()));
    }
}
