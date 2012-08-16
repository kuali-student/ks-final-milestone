package org.kuali.student.enrollment.acal;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class AcalReferenceDataParser {
    private List<Map<String, String>> dataSet = new ArrayList<Map<String, String>>();
    private Map<String, Atp> allAcals = new LinkedHashMap<String, Atp>();
    private Map<String, Atp> allHolidayCalendars = new LinkedHashMap<String, Atp>();

    private static final String FALL_ATP_TYPE = "Fall";
    private static final String WINTER_ATP_TYPE = "Winter";
    private static final String SPRING_ATP_TYPE = "Spring";
    private static final String SUMMER_ATP_TYPE = "Summer";
    private static final String SESSION1_ATP_TYPE = "Session 1";
    private static final String SESSION2_ATP_TYPE = "Session 2";
    private static final String FALL_ATP_TYPE_KEY = "kuali.atp.type.Fall";
    private static final String WINTER_ATP_TYPE_KEY = "kuali.atp.type.Winter";
    private static final String SPRING_ATP_TYPE_KEY = "kuali.atp.type.Spring";
    private static final String SUMMER_ATP_TYPE_KEY = "kuali.atp.type.Summer";
    private static final String SESSION1_ATP_TYPE_KEY = "kuali.atp.type.Session1";
    private static final String SESSION2_ATP_TYPE_KEY = "kuali.atp.type.Session2";

    private static final String RECORD_KEY = "table_key";

    private static final String RECORD_FIRST_DAY = "first_day";
    private static final String RECORD_CENSUS_DAY = "tenth_day";
    private static final String RECORD_LAST_DAY_ADD = "last_day_add";
    private static final String RECORD_LAST_DAY_DROP_NOT = "last_drop_not";
    private static final String RECORD_DROP_UG = "last_drop_ug";
    private static final String RECORD_DROP_GRAD = "last_drop_grad";
    private static final String RECORD_LAST_DAY_CLASSES = "last_day_classes";
    private static final String RECORD_LAST_DAY_EXAMS = "last_day_exams";
    private static final String RECORD_GRADE_SUBMIT_DEADLINE_DATE = "grade_submit_ddln";
    private static final String RECORD_GRADE_SUBMIT_DEADLINE_TIME = "grade_submit_ddtm";

    private static final String RECORD_SUB_1_FIRST_DAY = "first_day";
    private static final String RECORD_SUB_1_CENSUS_DAY = "tenth_day";
    private static final String RECORD_SUB_1_LAST_DAY_ADD = "last_day_add_a";
    private static final String RECORD_SUB_1_LAST_DAY_DROP_NOT = "last_drop_not_a";
    private static final String RECORD_SUB_1_DROP_UG = "last_a_drop_ug";
    private static final String RECORD_SUB_1_DROP_GRAD = "last_a_drop_grad";
    private static final String RECORD_SUB_1_LAST_DAY_CLASSES = "last_day_class_a";
    private static final String RECORD_SUB_1_GRADE_SUBMIT_DEADLINE_DATE = "grade_submit_ddln";
    private static final String RECORD_SUB_1_GRADE_SUBMIT_DEADLINE_TIME = "grade_submit_ddtm";

    private static final String RECORD_SUB_2_FIRST_DAY = "first_day_b";
    private static final String RECORD_SUB_2_LAST_DAY_ADD = "last_day_add_b";
    private static final String RECORD_SUB_2_LAST_DAY_DROP_NOT = "last_drop_not_b";
    private static final String RECORD_SUB_2_DROP_UG = "last_b_drop_ug";
    private static final String RECORD_SUB_2_DROP_GRAD = "last_b_drop_grad";
    private static final String RECORD_SUB_2_LAST_DAY_CLASSES = "last_day_classes";
    private static final String RECORD_SUB_2_LAST_DAY_EXAMS = "last_day_exams";
    private static final String RECORD_SUB_2_GRADE_SUBMIT_DEADLINE_DATE = "grade_submit_ddln";
    private static final String RECORD_SUB_2_GRADE_SUBMIT_DEADLINE_TIME = "grade_submit_ddtm";


    private static final String KEYDATE_INSTRUCTIONAL_PERIOD_KEY = "kuali.atp.milestone.InstructionalPeriod";
    private static final String KEYDATE_FINAL_EXAM_PERIOD_KEY = "kuali.atp.milestone.FinalExamPeriod";
    private static final String KEYDATE_CENSUS_KEY = "kuali.atp.milestone.FinancialAidCensus";
    private static final String KEYDATE_DROP_DEADLINE_WITHOUT_RECORD_KEY = "kuali.atp.milestone.DropDeadlineWithoutRecord";
    private static final String KEYDATE_DROP_DEADLINE_KEY = "kuali.atp.milestone.DropDate";
    private static final String KEYDATE_GRADES_DUE_KEY = "kuali.atp.milestone.GradesDue";
    private static final String KEYDATE_COURSE_SELECTION_PERIOD_END_KEY = "kuali.atp.milestone.CourseSelectionPeriodEnd";

    private static final Map<String, String> keydateNames = new LinkedHashMap<String, String>();
    {
        keydateNames.put(KEYDATE_INSTRUCTIONAL_PERIOD_KEY, "Instructional Period");
        keydateNames.put(KEYDATE_COURSE_SELECTION_PERIOD_END_KEY, "Course Selection Period End");
        keydateNames.put(KEYDATE_CENSUS_KEY, "Census");
        keydateNames.put(KEYDATE_DROP_DEADLINE_WITHOUT_RECORD_KEY, "Drop Deadline Without Record");
        keydateNames.put(KEYDATE_DROP_DEADLINE_KEY, "Drop Deadline");
        keydateNames.put(KEYDATE_FINAL_EXAM_PERIOD_KEY, "Final Exam Period");
        keydateNames.put(KEYDATE_GRADES_DUE_KEY, "Grades Due");
    }

    private static final Set<String> relativeToInstructionPeriod = new HashSet<String>();
    {
        relativeToInstructionPeriod.add(KEYDATE_CENSUS_KEY);
        relativeToInstructionPeriod.add(KEYDATE_COURSE_SELECTION_PERIOD_END_KEY);
        relativeToInstructionPeriod.add(KEYDATE_FINAL_EXAM_PERIOD_KEY);
    }

    private static final String HOLIDAY_NEWYEARSDAY_KEY = "kuali.atp.milestone.NewYearsDay";
    private static final String HOLIDAY_MLKDAY_KEY = "kuali.atp.milestone.MLKDay";
    private static final String HOLIDAY_PRESIDENTSDAY_KEY = "kuali.atp.milestone.PresidentsDay";
    private static final String HOLIDAY_MEMORIALDAY_KEY = "kuali.atp.milestone.MemorialDay";
    private static final String HOLIDAY_INDEPENDENCEDAY_KEY = "kuali.atp.milestone.IndependenceDay";
    private static final String HOLIDAY_LABORDAY_KEY = "kuali.atp.milestone.LaborDay";
    private static final String HOLIDAY_VETERANSDAY_KEY = "kuali.atp.milestone.VeteransDay";
    private static final String HOLIDAY_THANKSGIVINGBREAK_KEY = "kuali.atp.milestone.ThanksgivingBreak";
    private static final String HOLIDAY_CHRISTMAS_KEY = "kuali.atp.milestone.Christmas";
    private static final String HOLIDAY_OTHERNONINSTRUCTIONALHOLIDAY_KEY = "kuali.atp.milestone.OtherNonInstructionalHoliday";
    private static final String HOLIDAY_OTHERNONINSTRUCTIONALDAY_KEY = "kuali.atp.milestone.OtherNonInstructionalDay";

    private static final String HOLIDAY_NEWYEARSDAYOBSERVED_KEY = "kuali.atp.milestone.NewYearsDayObserved";
    private static final String HOLIDAY_INDEPENDENCEDAYOBSERVED_KEY = "kuali.atp.milestone.IndependenceDayObserved";
    private static final String HOLIDAY_VETERANSDAYOBSERVED_KEY = "kuali.atp.milestone.VeteransDayObserved";
    private static final String HOLIDAY_CHRISTMASOBSERVED_KEY = "kuali.atp.milestone.ChristmasObserved";


    private static final Map<String, String> holidayNames = new LinkedHashMap<String, String>();
    {
        holidayNames.put(HOLIDAY_NEWYEARSDAY_KEY, "New Years Day");
        holidayNames.put(HOLIDAY_MLKDAY_KEY, "Martin Luther King, Jr. Day");
        holidayNames.put(HOLIDAY_PRESIDENTSDAY_KEY, "Presidents Day");
        holidayNames.put(HOLIDAY_MEMORIALDAY_KEY, "Memorial Day");
        holidayNames.put(HOLIDAY_INDEPENDENCEDAY_KEY, "Independence Day");
        holidayNames.put(HOLIDAY_LABORDAY_KEY, "Labor Day");
        holidayNames.put(HOLIDAY_VETERANSDAY_KEY, "Veterans Day");
        holidayNames.put(HOLIDAY_THANKSGIVINGBREAK_KEY, "Thanksgiving Break");
        holidayNames.put(HOLIDAY_CHRISTMAS_KEY, "Christmas Day");
        holidayNames.put(HOLIDAY_NEWYEARSDAYOBSERVED_KEY, "New Years Day Observed");
        holidayNames.put(HOLIDAY_INDEPENDENCEDAYOBSERVED_KEY, "Independence Day Observed");
        holidayNames.put(HOLIDAY_VETERANSDAYOBSERVED_KEY, "Veterans Day Observed");
        holidayNames.put(HOLIDAY_CHRISTMASOBSERVED_KEY, "Christmas Day Observed");
    }


    private static final Map<String, String> observedHolidays = new LinkedHashMap<String, String>();
    {
        observedHolidays.put(HOLIDAY_NEWYEARSDAY_KEY, HOLIDAY_NEWYEARSDAYOBSERVED_KEY);
        observedHolidays.put(HOLIDAY_MLKDAY_KEY, null);
        observedHolidays.put(HOLIDAY_PRESIDENTSDAY_KEY, null);
        observedHolidays.put(HOLIDAY_MEMORIALDAY_KEY, null);
        observedHolidays.put(HOLIDAY_INDEPENDENCEDAY_KEY, HOLIDAY_INDEPENDENCEDAYOBSERVED_KEY);
        observedHolidays.put(HOLIDAY_LABORDAY_KEY, null);
        observedHolidays.put(HOLIDAY_VETERANSDAY_KEY, HOLIDAY_VETERANSDAYOBSERVED_KEY);
        observedHolidays.put(HOLIDAY_THANKSGIVINGBREAK_KEY, null);
        observedHolidays.put(HOLIDAY_CHRISTMAS_KEY, HOLIDAY_CHRISTMASOBSERVED_KEY);
    }


    public static void main(String[] args) {
        AcalReferenceDataParser parser = new AcalReferenceDataParser();
        File inputFile = new File("/kuali/temp/Reference Insitution Data_ Terms - Data.tsv");
        File outputFile = new File("/kuali/temp/output.sql");

        parser.loadDataSet(inputFile);

        int[] records = new int[] {35};
        for (int record : records) {
//            System.out.println("Record " + record + ": " + parser.dataSet.get(record));
        }

        parser.parse();
        SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy");
        for (Atp acal : parser.allAcals.values()) {
            int startYear = Integer.parseInt(yearFormat.format(acal.getStartDate()));
            int endYear = Integer.parseInt(yearFormat.format(acal.getEndDate()));
            for (int year = startYear; year <= endYear; year++) {
                Atp holiCal = parser.getHolidayCalendar(Integer.toString(year));
                acal.getHolidayCalendars().add(holiCal);
            }
        }

        parser.alterEndDates(new ArrayList<Atp>(parser.allAcals.values()));

//        System.out.println(parser.getReport(Arrays.asList(new String[]{"1978","2011"})));
//        System.out.println(parser.getReport(Arrays.asList(new String[]{"1979"})));

        SqlGenerator sqlGenerator = new SqlGenerator();
        StringBuilder sql = new StringBuilder();
        for (Atp acal : parser.allAcals.values()) {
            sqlGenerator.getSqlForAcademicCalendar(sql, acal);
        }
        parser.writeToFile(outputFile, sql.toString());


    }

    private void parse() {
        int i = 0;
        for (Map<String, String> record : dataSet) {
            i++;
//            System.out.println("parsing record [" + i + "]");
            // create term
            Atp term = createTerm(record);

            // add term to acal
            Atp acal = getAcalForTermId(record.get(RECORD_KEY));
            List<Atp> terms = acal.getTerms();
            terms.add(term);
        }
        for (Atp acal : allAcals.values()) {
            List<Atp> terms = acal.getTerms();
            Atp firstTerm = terms.get(0);
            Atp lastTerm = terms.get(terms.size() - 1);
            acal.setStartDate(firstTerm.getStartDate());
            acal.setEndDate(lastTerm.getEndDate());
        }
    }

    private Atp createTerm(Map<String, String> record) {
        String recordKey = record.get(RECORD_KEY);
        int year = getYear(recordKey);
        int quarter = getQuarter(recordKey);
        String termName = getAtpTypeName(quarter);

        Atp term = new Atp();

        term.setId((year + "" + termName).replaceAll(" ", "").toUpperCase());
        term.setName(termName + " " + year);
        term.setDescriptionPlain(termName + " " + year);
        term.setDescriptionFormatted("<p>" + term.getDescriptionPlain() + "</p>");
        term.setStartDate(parseDate(record.get(RECORD_FIRST_DAY)));
        Date termEnd = parseDate(record.get(RECORD_LAST_DAY_EXAMS));
        term.setEndDate(termEnd);
        term.setType(getAtpTypeKey(quarter));
        term.setAtpCode(termName.substring(0,3).toUpperCase() + year);

        for (String keydateType : keydateNames.keySet()) {
            term.addMilestone(getKeydateForTerm(keydateType, record));
        }

        // create subterm 1
        if (3 == quarter) {
            String subtermName = SESSION1_ATP_TYPE;
            Atp subterm = new Atp();

            subterm.setId((year + "" + subtermName).replaceAll(" ", "").toUpperCase());
            subterm.setName(subtermName + " " + year);
            subterm.setDescriptionPlain(subtermName + " " + year);
            subterm.setDescriptionFormatted("<p>" + subterm.getDescriptionPlain() + "</p>");
            subterm.setStartDate(parseDate(record.get(RECORD_SUB_1_FIRST_DAY)));
            Date subTermEnd = parseDate(record.get(RECORD_SUB_1_LAST_DAY_CLASSES));
            subterm.setEndDate(subTermEnd);
            subterm.setType(SESSION1_ATP_TYPE_KEY);
            subterm.setAtpCode(termName.substring(0, 3).toUpperCase() + "A" + year);

            for (String keydateType : keydateNames.keySet()) {
                subterm.addMilestone(getKeydateForSubterm1(keydateType, record));
            }

            term.getTerms().add(subterm);
        }



        // create subterm 2
        if (3 == quarter) {
            String subtermName = SESSION2_ATP_TYPE;
            Atp subterm = new Atp();

            subterm.setId((year + "" + subtermName).replaceAll(" ", "").toUpperCase());
            subterm.setName(subtermName + " " + year);
            subterm.setDescriptionPlain(subtermName + " " + year);
            subterm.setDescriptionFormatted("<p>" + subterm.getDescriptionPlain() + "</p>");
            subterm.setStartDate(parseDate(record.get(RECORD_SUB_2_FIRST_DAY)));
            Date subTermEnd = parseDate(record.get(RECORD_SUB_2_LAST_DAY_EXAMS));
            subterm.setEndDate(subTermEnd);
            subterm.setType(SESSION2_ATP_TYPE_KEY);
            subterm.setAtpCode(termName.substring(0, 3).toUpperCase() + "A" + year);

            for (String keydateType : keydateNames.keySet()) {
                subterm.addMilestone(getKeydateForSubterm2(keydateType, record));
            }

            term.getTerms().add(subterm);
        }

        addMilestoneRelations(term);
        return term;
    }

    private Milestone getKeydateForTerm(String keydateType, Map<String, String> record) {
        String recordKey = record.get(RECORD_KEY);
        int termYear = getYear(recordKey);
        String termName = getAtpTypeName(getQuarter(recordKey));
        String keydateName = keydateNames.get(keydateType);

        String id = (termYear + termName + keydateName).replaceAll(" ", "").toUpperCase();
        String name = termName + " " + termYear + " " + keydateName;
        String descriptionPlain = keydateName + " for " + termName + " " + termYear;
        String descriptionFormatted = "<p>" + descriptionPlain + "<p>";

        Date start = null;
        Date end = null;
        boolean allDay;
        boolean range;

        if (KEYDATE_COURSE_SELECTION_PERIOD_END_KEY.equals(keydateType)) {
            start = parseDate(record.get(RECORD_LAST_DAY_ADD));
            if (start == null) {
//                throw new RuntimeException(recordKey);
                return null; // TODO Some records don't have selection period end. Should this be calculated?
            }
            allDay = true;
            range = false;

        } else if (KEYDATE_INSTRUCTIONAL_PERIOD_KEY.equals(keydateType)) {
            start = parseDate(record.get(RECORD_FIRST_DAY));
            if (start == null) {
                throw new RuntimeException(recordKey);
            }
            end = parseDate(record.get(RECORD_LAST_DAY_CLASSES));
            if (end == null) {
                throw new RuntimeException(recordKey);
            }
            allDay = true;
            range = true;

        } else if (KEYDATE_FINAL_EXAM_PERIOD_KEY.equals(keydateType)) {
            if (3 == getQuarter(record.get(RECORD_KEY))) {
                return null;
            }
            start = parseDate(record.get(RECORD_LAST_DAY_CLASSES));
            if (start == null) {
                throw new RuntimeException(recordKey);
            }
            start = addToDate(start, Calendar.DATE, 1);
            end = parseDate(record.get(RECORD_LAST_DAY_EXAMS));
            if (end == null) {
                throw new RuntimeException(recordKey);
            }
            allDay    = true;
            range = true;

        } else if (KEYDATE_CENSUS_KEY.equals(keydateType)) {
            start = parseDate(record.get(RECORD_CENSUS_DAY));
            if (start == null) {
                throw new RuntimeException(recordKey);
            }
            allDay = true;
            range = false;

        } else if (KEYDATE_DROP_DEADLINE_WITHOUT_RECORD_KEY.equals(keydateType)) {
            start = parseDate(record.get(RECORD_LAST_DAY_DROP_NOT));
            if (start == null) {
                throw new RuntimeException(recordKey);
            }
            allDay = true;
            range = false;

        } else if (KEYDATE_DROP_DEADLINE_KEY.equals(keydateType)) {
            start = parseDate(record.get(RECORD_DROP_UG));
            if (start == null) {
                throw new RuntimeException(recordKey);
            }
            allDay = true;
            range = false;

        } else if (KEYDATE_GRADES_DUE_KEY.equals(keydateType)) {
            start = parseDate(record.get(RECORD_GRADE_SUBMIT_DEADLINE_DATE), record.get(RECORD_GRADE_SUBMIT_DEADLINE_TIME));
            if (start == null) {
                return null;
            }
            allDay = false;
            range = false;

        } else {
            throw new RuntimeException(recordKey + " Keydate type not known.");
        }

        Milestone milestone = new Milestone(id, name, keydateType, descriptionPlain, descriptionFormatted, start, end, allDay, range);
        return milestone;
    }

    private Milestone getKeydateForSubterm1(String keydateType, Map<String, String> record) {
        String recordKey = record.get(RECORD_KEY);
        int termYear = getYear(recordKey);
        String termName = SESSION1_ATP_TYPE;
        String keydateName = keydateNames.get(keydateType);

        String id = (termYear + termName + keydateName).replaceAll(" ", "").toUpperCase();
        String name = termName + " " + termYear + " " + keydateName;
        String descriptionPlain = keydateName + " for " + termName + " " + termYear;
        String descriptionFormatted = "<p>" + descriptionPlain + "<p>";

        Date start = null;
        Date end = null;
        boolean allDay;
        boolean range;

        if (KEYDATE_COURSE_SELECTION_PERIOD_END_KEY.equals(keydateType)) {
            start = parseDate(record.get(RECORD_SUB_1_LAST_DAY_ADD));
            if (start == null) {
//                throw new RuntimeException(recordKey);
                return null;  // TODO Some records don't have selection period end
            }
            allDay = true;
            range = false;

        } else if (KEYDATE_INSTRUCTIONAL_PERIOD_KEY.equals(keydateType)) {
            start = parseDate(record.get(RECORD_SUB_1_FIRST_DAY));
            if (start == null) {
                throw new RuntimeException(recordKey);
            }
            end = parseDate(record.get(RECORD_SUB_1_LAST_DAY_CLASSES));
            if (end == null) {
                end = parseDate(record.get(RECORD_SUB_2_FIRST_DAY));
                end = addToDate(end, Calendar.DATE, -1);
                if (end == null) {
                    throw new RuntimeException(record.get(RECORD_KEY));
                }
            }
            allDay = true;
            range = true;

        } else if (KEYDATE_FINAL_EXAM_PERIOD_KEY.equals(keydateType)) {
            return null;

        } else if (KEYDATE_CENSUS_KEY.equals(keydateType)) {
            start = parseDate(record.get(RECORD_SUB_1_CENSUS_DAY));
            if (start == null) {
                throw new RuntimeException(recordKey);
            }
            allDay = true;
            range = false;

        } else if (KEYDATE_DROP_DEADLINE_WITHOUT_RECORD_KEY.equals(keydateType)) {
            start = parseDate(record.get(RECORD_SUB_1_LAST_DAY_DROP_NOT));
            if (start == null) {
                throw new RuntimeException(recordKey);
            }
            allDay = true;
            range = false;

        } else if (KEYDATE_DROP_DEADLINE_KEY.equals(keydateType)) {
            start = parseDate(record.get(RECORD_SUB_1_DROP_UG));
            if (start == null) {
                throw new RuntimeException(recordKey);
            }
            allDay = true;
            range = false;

        } else if (KEYDATE_GRADES_DUE_KEY.equals(keydateType)) {
            start = parseDate(record.get(RECORD_SUB_1_GRADE_SUBMIT_DEADLINE_DATE), record.get(RECORD_SUB_1_GRADE_SUBMIT_DEADLINE_TIME));
            if (start == null) {
                return null;
            }
            allDay = false;
            range = false;

        } else {
            throw new RuntimeException(recordKey + " Keydate type not known.");
        }

        Milestone milestone = new Milestone(id, name, keydateType, descriptionPlain, descriptionFormatted, start, end, allDay, range);
        return milestone;
    }

    private Milestone getKeydateForSubterm2(String keydateType, Map<String, String> record) {
        String recordKey = record.get(RECORD_KEY);
        int termYear = getYear(recordKey);
        String termName = SESSION2_ATP_TYPE;
        String keydateName = keydateNames.get(keydateType);

        String id = (termYear + termName + keydateName).replaceAll(" ", "").toUpperCase();
        String name = termName + " " + termYear + " " + keydateName;
        String descriptionPlain = keydateName + " for " + termName + " " + termYear;
        String descriptionFormatted = "<p>" + descriptionPlain + "<p>";

        Date start = null;
        Date end = null;
        boolean allDay;
        boolean range;

        if (KEYDATE_COURSE_SELECTION_PERIOD_END_KEY.equals(keydateType)) {
            start = parseDate(record.get(RECORD_SUB_2_LAST_DAY_ADD));
            if (start == null) {
//                throw new RuntimeException(recordKey);
                return null; // TODO Some records don't have selection period end
            }
            allDay = true;
            range = false;

        } else if (KEYDATE_INSTRUCTIONAL_PERIOD_KEY.equals(keydateType)) {
            start = parseDate(record.get(RECORD_SUB_2_FIRST_DAY));
            if (start == null) {
                throw new RuntimeException(recordKey);
            }
            end = parseDate(record.get(RECORD_SUB_2_LAST_DAY_CLASSES));
            if (end == null) {
                throw new RuntimeException(recordKey);
            }
            allDay = true;
            range = true;

        } else if (KEYDATE_FINAL_EXAM_PERIOD_KEY.equals(keydateType)) {
            return null;

        } else if (KEYDATE_CENSUS_KEY.equals(keydateType)) {
            return null;

        } else if (KEYDATE_DROP_DEADLINE_WITHOUT_RECORD_KEY.equals(keydateType)) {
            start = parseDate(record.get(RECORD_SUB_2_LAST_DAY_DROP_NOT));
            if (start == null) {
                throw new RuntimeException(recordKey);
            }
            allDay = true;
            range = false;

        } else if (KEYDATE_DROP_DEADLINE_KEY.equals(keydateType)) {
            start = parseDate(record.get(RECORD_SUB_2_DROP_UG));
            if (start == null) {
                throw new RuntimeException(recordKey);
            }
            allDay = true;
            range = false;

        } else if (KEYDATE_GRADES_DUE_KEY.equals(keydateType)) {
            start = parseDate(record.get(RECORD_SUB_2_GRADE_SUBMIT_DEADLINE_DATE), record.get(RECORD_SUB_2_GRADE_SUBMIT_DEADLINE_TIME));
            if (start == null) {
                return null;
            }
            allDay = false;
            range = false;

        } else {
            throw new RuntimeException("Keydate type not known.");
        }

        Milestone milestone = new Milestone(id, name, keydateType, descriptionPlain, descriptionFormatted, start, end, allDay, range);
        return milestone;
    }

    private Date addToDate(Date date, int field, int amount) {
        if (date == null) {
            return null;
        }
        Calendar cal = new GregorianCalendar();
        cal.setTime(date);
        cal.add(field, amount);
        return cal.getTime();
    }
    private Date parseDate(String dateString) {
        Date date = null;
        if (dateString != null && dateString.length() > 0) {
            String[] dateSegments = dateString.split("/");
            int day = Integer.parseInt(dateSegments[1]);
            int month = Integer.parseInt(dateSegments[0]) - 1;
            int year = Integer.parseInt(dateSegments[2]);
            if (year > 99) {
                throw new RuntimeException("Unexpected year format: " + dateString);
            }
            if (year > 20) {
                year = 1900 + year;
            } else {
                year = 2000 + year;
            }
            Calendar cal = new GregorianCalendar(year, month, day);
            date = cal.getTime();
        }
        return date;
    }

    private Date parseDate(String dateString, String timeString) {
        Date date = null;
        if (dateString != null && dateString.length() > 0 && timeString != null && timeString.length() > 0) {
            while (timeString.length() < 6) {
                timeString = "0" + timeString;
            }

            int hour = Integer.parseInt(timeString.substring(0,2));
            int minute = Integer.parseInt(timeString.substring(2,4));
            int second = Integer.parseInt(timeString.substring(4,6));

            Calendar cal = new GregorianCalendar();
            cal.setTime(parseDate(dateString));
            cal.set(Calendar.HOUR_OF_DAY, hour);
            cal.set(Calendar.MINUTE, minute);
            cal.set(Calendar.SECOND, second);
            date = cal.getTime();
        }
        return date;
    }

    int getQuarter(String recordKey) {
        if (recordKey.length() != 5) {
            throw new IllegalArgumentException("recordKey: " + recordKey);
        }
        int quater = Integer.parseInt(recordKey.substring(4, 5));
        return quater;
    }
    int getYear(String recordKey) {
        if (recordKey.length() != 5) {
            throw new IllegalArgumentException("recordKey: " + recordKey);
        }
        int year = Integer.parseInt(recordKey.substring(0, 4));
        return year;
    }
    String getAtpTypeName(int quarter) {
        String name = null;
        switch (quarter) {
            case 1: name = WINTER_ATP_TYPE; break;
            case 2: name = SPRING_ATP_TYPE; break;
            case 3: name = SUMMER_ATP_TYPE; break;
            case 4: name = FALL_ATP_TYPE; break;
        }
        return name;
    }
    String getAtpTypeKey(int quarter) {
        String name = null;
        switch (quarter) {
            case 1: name = WINTER_ATP_TYPE_KEY; break;
            case 2: name = SPRING_ATP_TYPE_KEY; break;
            case 3: name = SUMMER_ATP_TYPE_KEY; break;
            case 4: name = FALL_ATP_TYPE_KEY; break;
        }
        return name;
    }

    private Atp getAcalForTermId(String termId) {
        if (termId.length() != 5) {
            throw new IllegalArgumentException("termId: " + termId);
        }
        int year = getYear(termId);
        int quater = getQuarter(termId);
        if (quater < 3) {
            year--;
        }
        Atp acal = allAcals.get(String.valueOf(year));
        if (acal == null) {
            acal = new Atp();
            allAcals.put(String.valueOf(year), acal);
            acal.setId(year + "" + (year + 1) + "ACADEMICCALENDAR");
            acal.setType("kuali.atp.type.AcademicCalendar");
            acal.setAdminOrgId("102"); // Registrar code
            acal.setAtpCode("ACAL"+year);
            acal.setName(year + "/" + (year + 1) + " Academic Calendar");
            acal.setDescriptionPlain("Academic Calendar for " + year + "/" + (year + 1));
            acal.setDescriptionFormatted("<p>" + acal.getDescriptionPlain() + "</p>");
        }
        return acal;
    }

    private void loadDataSet(File file) {
        DataInputStream in = null;
        try {
            FileInputStream fstream = new FileInputStream(file);
            in = new DataInputStream(fstream);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));

            String[] headers = null;
            String line;
            int row = 0;
            while ((line = br.readLine()) != null) {
                String[] columnValues = line.split("\t");
                if (0 == row) {
                    headers = columnValues;
                } else {
                    Map<String, String> map = new LinkedHashMap<String, String>();
                    for (int i = 0; i < headers.length; i++) {
                        map.put(headers[i], columnValues[i]);
                    }
                    dataSet.add(map);
                }
                row++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void writeToFile(File file, String content) {
        try{
            FileWriter fstream = new FileWriter(file);
            BufferedWriter out = new BufferedWriter(fstream);
            out.write(content);
            out.close();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public String getReport(List<String> years) {
        StringBuilder builder = new StringBuilder();
        String tab = "\t";
        String line = "\n";


        for (String year : years) {
            Atp acal = allAcals.get(year);
            addReportLineForAtp(builder, acal);
            for (Atp holiCal : acal.getHolidayCalendars()) {
                addReportLineForAtp(builder, holiCal);
                for (Milestone holiday : holiCal.getMilestones()) {
                    addReportLineForMilestone(builder, holiday);
                }
            }
            for (Atp term : acal.getTerms()) {
                addReportLineForAtp(builder, term);
                for (Milestone milestone : term.getMilestones()) {
                    addReportLineForMilestone(builder, milestone);
                }
                for (Atp subTerm : term.getTerms()) {
                    addReportLineForAtp(builder, subTerm);
                    for (Milestone milestone : subTerm.getMilestones()) {
                        addReportLineForMilestone(builder, milestone);
                    }

                }
            }
        }
        return builder.toString();
    }


    private StringBuilder addReportLineForAtp(StringBuilder builder, Atp atp) {
        String tab = "\t";
        String line = "\n";

        if (builder == null) {
            builder = new StringBuilder();
        }

        builder.append(atp.getName()).append(tab);

        Date startDate = atp.getStartDate();
        builder.append(getDate(startDate)).append(tab);
        builder.append(tab);

        Date endDate = atp.getEndDate();
        builder.append(getDate(endDate)).append(tab);
        builder.append(tab);

        builder.append(atp.getType());

        builder.append(line);
        return builder;
    }

    private StringBuilder addReportLineForMilestone(StringBuilder builder, Milestone milestone) {
        String tab = "\t";
        String line = "\n";

        if (builder == null) {
            builder = new StringBuilder();
        }

        builder.append(milestone.getName()).append(tab);

        Date startDate = milestone.getStartDate();
        builder.append(getDate(startDate)).append(tab);
        if (!milestone.isAllDay()) {
            builder.append(getTime(startDate));
        }
        builder.append(tab);

        Date endDate = milestone.getEndDate();
        if (milestone.isDateRange()) {
            if (endDate == null) {
                throw new RuntimeException("End date not supplied for range. " + milestone.getId());
            }
            builder.append(getDate(endDate)).append(tab);
            if (!milestone.isAllDay()) {
                builder.append(getTime(endDate));
            }
        } else {
            builder.append(tab);
            if (endDate != null) {
                throw new RuntimeException("End date not expected for non range. " + milestone.getId());
            }
        }
        builder.append(tab);
        builder.append(milestone.getType());

        builder.append(line);
        return builder;
    }

    private String getDate(Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");
        if (date == null) {
            return "";
        }
        return dateFormat.format(date);
    }
    private String getTime(Date date) {
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
        return timeFormat.format(date);
    }

    public Atp getHolidayCalendar(String yearString) {
        Atp holiCal = allHolidayCalendars.get(yearString);
        int year = Integer.parseInt(yearString);
        if (null == holiCal) {
            holiCal = new Atp();
            holiCal.setId(year + "HOLIDAYCALENDAR");
            holiCal.setName(year + " Holiday Calendar");
            holiCal.setStartDate(new GregorianCalendar(year, Calendar.JANUARY, 1).getTime());
            holiCal.setEndDate(new GregorianCalendar(year, Calendar.DECEMBER, 31).getTime());
            holiCal.setDescriptionPlain("Holiday Calendar for " + year);
            holiCal.setDescriptionFormatted("<p>" + holiCal.getDescriptionPlain() + "</p>");
            holiCal.setType("kuali.atp.type.HolidayCalendar");
            holiCal.setAtpCode("HCAL" + year);
            holiCal.setAdminOrgId("102"); // Registrar code

            for (String holidayTypeKey : observedHolidays.keySet()) {
                Milestone holiday = getHoliday(holidayTypeKey, year);
                holiCal.addMilestone(holiday);
                Milestone observedholiday = getHoliday(observedHolidays.get(holidayTypeKey), year);
                if (null != observedholiday) {
                    holiCal.addMilestone(observedholiday);
                }
            }
            allHolidayCalendars.put(yearString, holiCal);
        }
        return holiCal;
    }

    public Milestone getHoliday(String holidayType, int year) {
        if (null == holidayType) {
            return null;
        }

        String[] typeSplit = holidayType.split("\\.");

        String id = (year + typeSplit[typeSplit.length-1]).toUpperCase();
        String name = holidayNames.get(holidayType);
        String descriptionPlain = name + " for " + year;
        String descriptionFormatted = "<p>" + descriptionPlain + "<p>";

        Date start = null;
        Date end = null;
        boolean allDay = true;
        boolean range = false;

        if (HOLIDAY_NEWYEARSDAY_KEY.equals(holidayType)) {
            Calendar cal = new GregorianCalendar(year, Calendar.JANUARY, 1);
            start = cal.getTime();
        } else if (HOLIDAY_NEWYEARSDAYOBSERVED_KEY.equals(holidayType)) {
            Milestone milestone = getHoliday(HOLIDAY_NEWYEARSDAY_KEY, year);
            int offset = getWeekendOffset(milestone.getStartDate());
            if (0 == offset) {
                return null;
            } else {
                start = addToDate(milestone.getStartDate(), Calendar.DATE, offset);
                end = addToDate(milestone.getEndDate(), Calendar.DATE, offset);
            }
        } else if (HOLIDAY_MLKDAY_KEY.equals(holidayType)) {
            start = getNthDayOfWeekInMonth(3, Calendar.MONDAY, Calendar.JANUARY, year);
        } else if (HOLIDAY_PRESIDENTSDAY_KEY.equals(holidayType)) {
            start = getNthDayOfWeekInMonth(3, Calendar.MONDAY, Calendar.FEBRUARY, year);
        } else if (HOLIDAY_MEMORIALDAY_KEY.equals(holidayType)) {
            start = getNthDayOfWeekInMonth(1, Calendar.MONDAY, Calendar.JUNE, year);
            start = addToDate(start, Calendar.DATE, -7);
        } else if (HOLIDAY_INDEPENDENCEDAY_KEY.equals(holidayType)) {
            start = new GregorianCalendar(year, Calendar.JULY, 4).getTime();
        } else if (HOLIDAY_INDEPENDENCEDAYOBSERVED_KEY.equals(holidayType)) {
            Milestone milestone = getHoliday(HOLIDAY_INDEPENDENCEDAY_KEY, year);
            int offset = getWeekendOffset(milestone.getStartDate());
            if (0 == offset) {
                return null;
            } else {
                start = addToDate(milestone.getStartDate(), Calendar.DATE, offset);
                end = addToDate(milestone.getEndDate(), Calendar.DATE, offset);
            }
        } else if (HOLIDAY_LABORDAY_KEY.equals(holidayType)) {
            start = getNthDayOfWeekInMonth(1, Calendar.MONDAY, Calendar.SEPTEMBER, year);
        } else if (HOLIDAY_VETERANSDAY_KEY.equals(holidayType)) {
            start = new GregorianCalendar(year, Calendar.NOVEMBER, 11).getTime();
        } else if (HOLIDAY_VETERANSDAYOBSERVED_KEY.equals(holidayType)) {
            Milestone milestone = getHoliday(HOLIDAY_VETERANSDAY_KEY, year);
            int offset = getWeekendOffset(milestone.getStartDate());
            if (0 == offset) {
                return null;
            } else {
                start = addToDate(milestone.getStartDate(), Calendar.DATE, offset);
                end = addToDate(milestone.getEndDate(), Calendar.DATE, offset);
            }
        } else if (HOLIDAY_THANKSGIVINGBREAK_KEY.equals(holidayType)) {
            start = getNthDayOfWeekInMonth(4, Calendar.THURSDAY, Calendar.NOVEMBER, year);
            end = addToDate(start, Calendar.DATE, 1);
            range = true;
        } else if (HOLIDAY_CHRISTMAS_KEY.equals(holidayType)) {
            start = new GregorianCalendar(year, Calendar.DECEMBER, 25).getTime();
        } else if (HOLIDAY_CHRISTMASOBSERVED_KEY.equals(holidayType)) {
            Milestone milestone = getHoliday(HOLIDAY_CHRISTMAS_KEY, year);
            int offset = getWeekendOffset(milestone.getStartDate());
            if (0 == offset) {
                return null;
            } else {
                start = addToDate(milestone.getStartDate(), Calendar.DATE, offset);
                end = addToDate(milestone.getEndDate(), Calendar.DATE, offset);
            }
        } else {
            throw new RuntimeException("Holiday type not known. " + holidayType);
        }

        Milestone milestone = new Milestone(id, name, holidayType, descriptionPlain, descriptionFormatted, start, end, allDay, range);
        return milestone;
    }

    private void addMilestoneRelations(Atp term) {
        List<Milestone> keyDates = term.getMilestones();
        Milestone instructionperiod = null;
        for (Milestone keyDate : keyDates) {
            if (KEYDATE_INSTRUCTIONAL_PERIOD_KEY.equals(keyDate.getType())) {
                instructionperiod = keyDate;
                break;
            }
        }
        if (null == instructionperiod) {
            throw new RuntimeException("Could not find instruction period for term: " + term.getId());
        }
        for (Milestone keyDate : keyDates) {
            if (relativeToInstructionPeriod.contains(keyDate.getType())) {
                keyDate.setRelative(true);
                keyDate.setRelativeMilestoneId(instructionperiod.getId());
            } else {
                keyDate.setRelative(false);
            }
        }

        for (Atp atp : term.getTerms()) {
            addMilestoneRelations(atp);
        }
    }

    private void alterEndDates(List<Atp> acals) {
        for (int i = acals.size()-1; i >= 0 ; i--) {
            Atp acal = acals.get(i);
            if (i > 0) {
                Atp prevAcal = acals.get(i-1);
                Date start = acal.getStartDate();
                Date prevEndDate = addToDate(start, Calendar.DATE, -1);
                prevAcal.setEndDate(prevEndDate);
                List<Atp> prevTerms = prevAcal.getTerms();
                if (!prevTerms.isEmpty()) {
                    Atp lastPrevTerm = prevTerms.get(prevTerms.size()-1);
                    lastPrevTerm.setEndDate(prevEndDate);
                    List<Atp> prevSubTerms = lastPrevTerm.getTerms();
                    if (!prevSubTerms.isEmpty()) {
                        Atp lastPrevTermSubTerm = prevSubTerms.get(prevSubTerms.size()-1);
                        lastPrevTermSubTerm.setEndDate(prevEndDate);
                    }
                }
            }

            List<Atp> terms = acal.getTerms();
            for (int j = terms.size()-1; j >= 0; j--) {
                Atp term = terms.get(j);
                if (j > 0) {
                    Atp prevTerm = terms.get(j-1);
                    Date prevEndDate = addToDate(term.getStartDate(), Calendar.DATE, -1);
                    prevTerm.setEndDate(prevEndDate);
                    List<Atp> prevSubTerms = prevTerm.getTerms();
                    if (!prevSubTerms.isEmpty()) {
                        Atp lastPrevSubTerm = prevSubTerms.get(prevSubTerms.size()-1);
                        lastPrevSubTerm.setEndDate(prevEndDate);
                    }
                }
            }

            for (int j = terms.size()-1; j >= 0; j--) {
                Atp term = terms.get(j);
                if (j > 0) {
                    Atp prevTerm = terms.get(j-1);
                    Date start = term.getStartDate();
                    prevTerm.setEndDate(addToDate(start, Calendar.DATE, -1));
                }
                List<Atp> subTerms = term.getTerms();
                for (int k = subTerms.size()-1; k >= 0; k--) {
                    Atp subTerm = subTerms.get(k);
                    if (k > 0) {
                        Atp prevSubTerm = subTerms.get(k-1);
                        Date start = subTerm.getStartDate();
                        prevSubTerm.setEndDate(addToDate(start, Calendar.DATE, -1));
                    }
                }
            }
        }
    }

    private int getWeekendOffset(Date date) {
        Calendar cal = new GregorianCalendar();
        cal.setTime(date);
        int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
        if (Calendar.SATURDAY == dayOfWeek) {
            return -1;
        } else if (Calendar.SUNDAY == dayOfWeek) {
            return 1;
        } else {
            return 0;
        }
    }

    private Date getNthDayOfWeekInMonth(int n, int dayOfWeek, int month, int year) {
        Calendar cal = new GregorianCalendar(year, month, 1);
        if (dayOfWeek != cal.get(Calendar.DAY_OF_WEEK)) {
            int daysUntil = (dayOfWeek - cal.get(Calendar.DAY_OF_WEEK) + 7) % 7;
            cal.add(Calendar.DATE, daysUntil);
        }
        cal.add(Calendar.DATE, (n-1) * 7);
        return cal.getTime();
    }

}
