/**
 * Copyright 2012 The Kuali Foundation Licensed under the
 * Educational Community License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may
 * obtain a copy of the License at
 *
 * http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 *
 * Created by Charles on 11/14/12
 */
package org.kuali.student.enrollment.class2.courseoffering.refdata;

import org.apache.log4j.Logger;
import org.kuali.student.enrollment.courseoffering.dto.ActivityOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.CourseOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.FormatOfferingInfo;
import org.kuali.student.enrollment.courseoffering.service.CourseOfferingService;
import org.kuali.student.r1.lum.lrc.service.LrcService;
import org.kuali.student.r2.common.dto.AmountInfo;
import org.kuali.student.r2.common.dto.AttributeInfo;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.StatusInfo;
import org.kuali.student.r2.common.exceptions.AlreadyExistsException;
import org.kuali.student.r2.common.exceptions.CircularReferenceException;
import org.kuali.student.r2.common.exceptions.CircularRelationshipException;
import org.kuali.student.r2.common.exceptions.DataValidationErrorException;
import org.kuali.student.r2.common.exceptions.DependentObjectsExistException;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.exceptions.ReadOnlyException;
import org.kuali.student.r2.common.exceptions.UnsupportedActionException;
import org.kuali.student.r2.common.exceptions.VersionMismatchException;
import org.kuali.student.r2.lum.course.dto.ActivityInfo;
import org.kuali.student.r2.lum.course.dto.CourseInfo;
import org.kuali.student.r2.lum.course.dto.FormatInfo;
import org.kuali.student.r2.lum.course.infc.Course;
import org.kuali.student.r2.lum.course.service.CourseService;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeMap;
import java.util.regex.Pattern;

/**
 * 1. Remove any Course with no Course Offerings
 * 2. Remove any Activity with 0 contact hours (they don't exist)
 *
 * @author Kuali Student Team
 */
public class CluFixerImpl implements CluFixer {
    private CourseOfferingService coService;
    private CourseService courseService;
    private LrcService lrcService;
    private static final Logger LOGGER = Logger.getLogger(CluFixer.class);
    private String pathPrefix = "";

    private ContextInfo context = new ContextInfo();

    private static final String VALID_COURSE_CODES = "validCourseCodes.txt";
    private static final String COURSE_IDS = "courseIds.txt";
    private static final String BSCI105_COURSE_ID = "1c0fe33d-2233-4659-8a06-3adc8f6a17e1";

    public void setCoService(CourseOfferingService coService) {
        this.coService = coService;
    }

    public void setCourseService(CourseService courseService) {
        this.courseService = courseService;
    }

    public void setLrcService(LrcService lrcService) {
        this.lrcService = lrcService;
    }

    public void setPathPrefix(String prefix) {
        pathPrefix = prefix;
    }
    /**
     *
     * @param filePath Path to a file (e.g. "C:/Users/Charles/Desktop/Kuali/RefData/courseIds.txt")
     *                 which contains list of Course IDs, one per line;
     */
    public void cleanClus(String filePath) throws Exception {
        context.setAuthenticatedPrincipalId("REF_DATA_BATCH");
        context.setPrincipalId("REF_DATA_BATCH");

        setPathPrefix("C:/work/ws/ks/enrollment/aggregate-umd-enr-data/ks-enroll/ks-enroll-impl/src/main/java/org/kuali/student/enrollment/class2/courseoffering/refdata/datafiles/");
        _deleteUnusedCoursesDefault();
        _modifyFormatsForCourses();


    }

    private void _sortFile(String inputFileName) throws IOException {
        String truncatedFilename = inputFileName;
        if (inputFileName.endsWith(".txt")) {
            truncatedFilename = inputFileName.substring(0, inputFileName.length() - 4);
        }
        String newOutputFilename = truncatedFilename + ".sorted.txt";
        List<String> list = _loadDataFromFile(inputFileName);
        Collections.sort(list);
        _writeInfoToFile(list, newOutputFilename);
    }

    private void _filterCourseIdsToValidCourseIds(String fileName) throws IOException {
        LOGGER.info(" START ===============================================");
        List<String> results = new ArrayList<String>();
        List<String> origCourseIds = _loadDataFromFile(fileName); // "courseIds.txt"
        Set<String> validCourseCodes = new HashSet<String>(_loadDataFromFile("validCourseCodes.txt"));
        Set<String> exemptCourseCodes = new HashSet<String>(_loadDataFromFile("exemptBadCourseCodes.txt"));
        PrintStream skipOut = _createPrintStream("skippedCourseIds.txt");
        int count = 0;
        for (String courseId: origCourseIds) {
            try {
                if (count > 0 && count % 25 == 0) {
                    System.err.println("count = " + count);
                    skipOut.flush();
                }
                count++;
                CourseInfo info = courseService.getCourse(courseId, context);
                results.add(info.getCode() + "/" + info.getId());
            } catch (DoesNotExistException e) {
                LOGGER.warn("DoesNotExist: Skipping " + courseId);
                skipOut.println(courseId);
            } catch (Exception e) {
                LOGGER.warn("OtherException (" + e.getClass().toString() + "): Skipping " + courseId);
                skipOut.println("(" + e.getClass().toString() + ") " + courseId);
            }
        }
        skipOut.close();
        PrintStream pOut = _createPrintStream("validCourseCodeAndCourseIds.txt");
        for (String line: results) {
            pOut.println(line);
        }
        pOut.close();
        LOGGER.info(" END   ===============================================");
    }

    private void _modifyFormatsForCourses() throws
            InvalidParameterException, MissingParameterException, DoesNotExistException,
            PermissionDeniedException, OperationFailedException, DataValidationErrorException,
            DependentObjectsExistException, UnsupportedActionException, AlreadyExistsException,
            CircularRelationshipException, CircularReferenceException, ReadOnlyException, VersionMismatchException {
        List<Map<String, List<String>>> listOfMaps = _getCluLuiActivityTypes("cluLuiActivityTypes.txt");
        Map<String, List<String>> shortNameToCluLuiTypes = listOfMaps.get(0);
        Map<String, List<String>> cluTypeToShortNameLuiTypeList = listOfMaps.get(1);
        Map<String, List<String>> courseCodeToFormatList = _loadCourseCodeToFormatList("courseCodeToFormats.txt");
        // Now to get a list of course IDs
        List<String> courseIdsPlus = _loadDataFromFile("validCourseCodeAndCourseIds.sorted.txt");
        List<String> courseIds = new ArrayList<String>();
        Map<String, String> courseIdToCourseCode = new HashMap<String, String>();
        for (String line: courseIdsPlus) {
            String[] parts = line.split("/");
            courseIds.add(parts[1]); // Extract out the IDs
            courseIdToCourseCode.put(parts[1], parts[0]);
        }

//        String courseId = "CLUID-BSCI288-199908000000";
//        String courseId = "86c94194-062d-4a49-a9d2-ef24de44d860";
//        String courseId = "d2ac6c8d-53e4-4eab-9095-d128f9d433a1";
//        String courseId = "b981491a-97e9-4d53-8a7c-189ebedc0028";
        for (String courseId: courseIds) {
            System.err.println("--------------- Processing " + courseIdToCourseCode.get(courseId) + " (" + courseId +")");
            _modifyFormatsForCourse(courseId, courseCodeToFormatList, shortNameToCluLuiTypes, cluTypeToShortNameLuiTypeList);
        }
        System.err.println("=========== Finished modifying formats");
    }

    private void _filterLog(String inputFileName) throws IOException {
       List<String> result = _loadDataFromFile(inputFileName, new BasicFilter() {
           @Override
           public boolean permits(String input) {
               return input.indexOf("Caused by: org.kuali.student.r2.common.exceptions.DoesNotExistException:") >= 0;
           }
       });
        _writeInfoToFile(result, inputFileName + ".filtered.txt");
    }

    private void _deleteUnusedCoursesDefault() {
        _deleteUnusedCourses("badCourseInfo.txt", "exemptBadCourseCodes.txt");
    }

    private void _deleteUnusedCourses(String badCourseInfoFilename, String exemptBadCourseCodesFilename) {
        // badCourseInfo.txt FORMAT: <course code>/<clu id>/<version ind id>
        // exemptBadCourseCodes.txt FORMAT: <course code>
        Map<String, String> courseIdToCourseCode = _computeBadCourseInfoMap(badCourseInfoFilename);
        Set<String> badCourseCodesSet = _computeExemptBadCourseCodes(exemptBadCourseCodesFilename);
        for (Map.Entry<String, String> entry: courseIdToCourseCode.entrySet()) {
            if (badCourseCodesSet.contains(entry.getValue())) {
                LOGGER.info("Skipping " + entry.getValue() + " (" + entry.getKey() + ")");
                continue; // Skip to next one
            }
            // Otherwise, we're here
            StatusInfo statusInfo = null;
            try {
                statusInfo = courseService.deleteCourse(entry.getKey(), context);
                LOGGER.info("Deleted " + entry.getValue() + " (" + entry.getKey() + ")");
            } catch (Exception e) {
                LOGGER.warn("Failed to delete " + entry.getValue() + " (" + entry.getKey() + ")");
                continue;
            }
        }
    }

    private Map<String,String> _computeBadCourseInfoMap(String badCourseInfoFilename) {
        Map<String,String> courseIdToCourseCode = new HashMap<String, String>();
        List<String> lines = _loadDataFromFile(badCourseInfoFilename);
        for (String line: lines) {
            // Each line is a course code, course ID, and version independent ID separated by /
            List<String> parts = Arrays.asList(line.split("/"));
            courseIdToCourseCode.put(parts.get(1), parts.get(0));
        }
        return courseIdToCourseCode;
    }

    private Set<String> _computeExemptBadCourseCodes(String exemptBadCourseCodesFilename) {
        List<String> badCourseCodes = _loadDataFromFile(exemptBadCourseCodesFilename);
        Set<String> badCourseCodesSet = new HashSet<String>(badCourseCodes);
        return badCourseCodesSet;
    }

    private List<String> _inferFormat(Map<String, List<String>> cluTypeToShortNameLuiTypeList, FormatInfo firstFormat) {
        List<String> formatList;
        List<ActivityInfo> activityInfos = firstFormat.getActivities();
        String format = null;
        for (ActivityInfo activity: activityInfos) {
            if (activity.getContactHours() != null && activity.getContactHours().getUnitQuantity() != null
                    && Integer.parseInt(activity.getContactHours().getUnitQuantity()) > 0) {
                // Check that there are contact hours (often it's null when there are none).
                String activityType = activity.getTypeKey();
                String shortName = cluTypeToShortNameLuiTypeList.get(activityType).get(0);
                if (format == null) {
                    format = shortName;
                } else {
                    format += "/" + shortName;
                }
            }
        }
        formatList = new ArrayList<String>();
        formatList.add(format);  // Place in the one format based on contact hours
        return formatList;
    }


    private String _fixBadStartEndTerm(String startTerm, String endTerm) {
        if (startTerm == null || endTerm == null) {
            return endTerm;
        }
        String prefix = "kuali.atp.";
        int startTermVal = _extractModifiedTerm(startTerm);
        int endTermVal = _extractModifiedTerm(endTerm);
        if (endTermVal < startTermVal) {
            int year = endTermVal / 100;
            String result =  prefix + (year + 1) + endTerm.substring(prefix.length() + 4);
            return result;
        }

        return endTerm;  //To change body of created methods use File | Settings | File Templates.
    }

    // Assumes file stores info in
    // <short name>/CLU type/LUI type
    // e.g. LEC/kuali.lu.type.activity.LectureORSeminar/kuali.lui.type.activity.offering.LectureORSeminar
    // Short names are LEC, DIS, LAB, EXP
    private List<Map<String, List<String>>> _getCluLuiActivityTypes(String fileName) {
        // cluLuiActivityTypes.txt
        List<Map<String, List<String>>> listOfMaps = new ArrayList<Map<String, List<String>>>();
        Map<String, List<String>> shortNameToCluLuiTypes = new HashMap<String, List<String>>();
        Map<String, List<String>> cluTypeToShortNameLuiTypeList = new HashMap<String, List<String>>();
        List<String> lines = _loadDataFromFile(fileName);
        for (String line: lines) {
            List<String> types = new ArrayList<String>(Arrays.asList(line.split("/")));
            shortNameToCluLuiTypes.put(types.get(0), types.subList(1, types.size()));
            List<String> shortNameLuiType = new ArrayList<String>();
            shortNameLuiType.add(types.get(0)); // add the short name like LEC
            shortNameLuiType.add(types.get(2)); // add the lui type
            cluTypeToShortNameLuiTypeList.put(types.get(1), shortNameLuiType);
        }
        listOfMaps.add(shortNameToCluLuiTypes);
        listOfMaps.add(cluTypeToShortNameLuiTypeList);
        return listOfMaps;
    }

    @Transactional
    private void _modifyFormatsForCourse(String courseId,
                                        Map<String, List<String>> courseCodeToFormatList,
                                        Map<String, List<String>> shortNameToCluLuiTypes,
                                        Map<String, List<String>> cluTypeToShortNameLuiTypeList)
            throws InvalidParameterException, MissingParameterException, DoesNotExistException,
            PermissionDeniedException, OperationFailedException, DataValidationErrorException, DependentObjectsExistException, UnsupportedActionException, AlreadyExistsException, CircularRelationshipException, CircularReferenceException, ReadOnlyException, VersionMismatchException {
        // Use courseId = BSCI105_COURSE_ID
        CourseInfo course = courseService.getCourse(courseId, context);

        // Check to see if this course has been processed.  The default (pre-change) is a single Format with
        // four ActivityInfo objects.  After the modifications,
        FormatInfo firstFormat = course.getFormats().get(0);
        if (firstFormat.getActivities().size() < 4) { // By default, old UMD data has exactly 4 formats
            // This course object has already been modified
            System.err.println("Skipping: " + course.getCode() + " (" + course.getId() + ")");
            return;
        }
        // If the format isn't in courseCodeToFormatList (which is from a spreadsheet by UMD), then
        // figure out the format based on contact hours of Activities
        String courseCode = course.getCode();
        List<String> formatList = courseCodeToFormatList.get(courseCode); // This is a list of short names
        if (formatList == null) { // Can't find this from the spreadsheet data, so infer the format
            formatList = _inferFormat(cluTypeToShortNameLuiTypeList, firstFormat);
        }
        // Now redo the formats
        List<FormatInfo> newFormatList = new ArrayList<FormatInfo>();
        for (String format: formatList) {
            List<String> shortNames = Arrays.asList(format.split("/"));
            List<String> activityTypes = new ArrayList<String>();
            for (String shortName: shortNames) {
                // Get the CLU type which is at index 0 (LUI type is at index 1)
                // Short name is currently LEC, LAB, DIS, or EXP
                activityTypes.add(shortNameToCluLuiTypes.get(shortName).get(0));
            }
            FormatInfo info = new FormatInfo(firstFormat);
            List<ActivityInfo> newActivityTypes = new ArrayList<ActivityInfo>();
            for (ActivityInfo actInfo: firstFormat.getActivities()) {
                if (!activityTypes.contains(actInfo.getTypeKey())) {
                    // Skip if this type is not part of the format
                    continue;
                }
                ActivityInfo actCopy = new ActivityInfo(actInfo);
                actCopy.setId(null); // Make a copy
                AmountInfo amtInfo = actCopy.getContactHours();
                if (amtInfo.getUnitQuantity() == null ||
                        "0".equals(amtInfo.getUnitQuantity().trim())) {
                    amtInfo.setUnitQuantity("3"); // Have non-zero contact hours.  Pick 3 as a reasonable default
                }
                newActivityTypes.add(actCopy);
            }
            info.setActivities(newActivityTypes);
            info.setId(null); // Need new IDs for these types
            newFormatList.add(info);
        }
        course.setFormats(newFormatList);

        // Fix bad start/end terms
        String oldStartTerm = course.getStartTerm();
        String oldEndTerm = course.getEndTerm();
        String newEndTerm = _fixBadStartEndTerm(oldStartTerm, oldEndTerm);
        course.setEndTerm(newEndTerm);

        for (AttributeInfo attr: course.getAttributes()) {
            // This is compensate for some bad data.  This sets the lastTermOffered to the endTerm
            if (attr.getKey().equals("lastTermOffered")) {
                attr.setValue(course.getEndTerm());
            }
        }
        // Fix campus locations if necessary
        if (course.getCampusLocations().isEmpty()) {
            course.getCampusLocations().add("NO");
        }
        // Fix finalExamStatus if need be
        boolean foundFinalExamStatus = false;
        for (AttributeInfo attr: course.getAttributes()) {
            if (attr.getKey().equals("finalExamStatus")) {
                foundFinalExamStatus = true;
            }
        }
        if (!foundFinalExamStatus) {
            AttributeInfo newAttr = new AttributeInfo();
            newAttr.setKey("finalExamStatus");
            newAttr.setValue("STD");
            course.getAttributes().add(newAttr);
        }

        CourseInfo updated = courseService.updateCourse(course.getId(), course, context);
        System.err.println("   Updated: " + course.getCode() + " (" + course.getId() + ")");
    }

    private int _extractModifiedTerm(String term) {
        String prefix = "kuali.atp.";
        String yearSubstr = term.substring(prefix.length(), prefix.length() + 4);
        String season = term.substring(prefix.length() + 4);
        String suffix;
        if (season.equals("Spring")) {
            suffix = "02";
        } else if (season.equals("Winter")) {
            suffix = "01";
        } else if (season.equals("Summer1")) {
            suffix = "05";
        } else if (season.equals("Summer2")) {
            suffix = "07";
        } else if (season.equals("Fall")) {
            suffix = "08";
        } else {
            throw new RuntimeException("Illegal season name");
        }
        String result = yearSubstr + suffix;
        Integer val = new Integer(result);
        return val;
    }

    // Each line of the file should look like
    // (BSCI105) [LEC/LAB|LEC]
    // Course code is in parentheses followed by formats in brackets.  Multiple formats (as in this case)
    // are separated by the vertical bar, which is on the same key as the backslash on standard Western keyboards
    private Map<String, List<String>> _loadCourseCodeToFormatList(String fileName) {
        // courseCodesToFormats.txt
        Map<String, List<String>> courseCodesToFormatList = new HashMap<String, List<String>>();
        List<String> lines = _loadDataFromFile(fileName);
        for (String line: lines) {
            line = line.trim();
            if (line.isEmpty()) {
                continue;
            }
            int leftParenIndex = line.indexOf('(');
            int rightParenIndex = line.indexOf(')');
            String courseCode = line.substring(leftParenIndex + 1, rightParenIndex);
            int leftBracketIndex = line.indexOf('[');
            int rightBracketIndex = line.indexOf(']');
            String formatStr = line.substring(leftBracketIndex + 1, rightBracketIndex);
            // Now split
            List<String> formats = new ArrayList<String>(Arrays.asList(formatStr.split(Pattern.quote("|"))));
            courseCodesToFormatList.put(courseCode, formats);
        }
        return courseCodesToFormatList;
    }

    private void _getFormatOfferingInfoForOneCourse() throws InvalidParameterException, MissingParameterException, DoesNotExistException, PermissionDeniedException, OperationFailedException, IOException {
        Course course = courseService.getCourse(BSCI105_COURSE_ID, context);
        List<CourseOfferingInfo> coList =
                coService.getCourseOfferingsByCourse(BSCI105_COURSE_ID, context);
        PrintStream pOut = _createPrintStream("formatData.txt");
        for (CourseOfferingInfo info: coList) {
            pOut.println(info.getCourseOfferingCode() + " ------------------------");
            List<FormatOfferingInfo> foInfos =
                    coService.getFormatOfferingsByCourseOffering(info.getId(), context);

            for (FormatOfferingInfo fo: foInfos) {
                pOut.println("Format offering : " + fo.getId());
                pOut.println("Format id: " + fo.getFormatId());
                List<String> aoTypeKeys = fo.getActivityOfferingTypeKeys();
                pOut.print("AO Type keys: ");
                for (String key: aoTypeKeys) {
                    pOut.print(key + " ");
                }
                pOut.println();
                List<ActivityOfferingInfo> aoInfos =
                        coService.getActivityOfferingsByFormatOffering(fo.getId(), context);
                int count = 0;
                for (ActivityOfferingInfo ao: aoInfos) {
                    // Print ao
                    pOut.println(count + "/" + ao.getId() + "/" + ao.getTypeKey());
                }
            }
        }
    }
    private PrintStream _createPrintStream(String fileName) throws IOException {
        File file = new File(pathPrefix, fileName);
        if (file.exists()) {
            file.delete();
        }
        file.createNewFile();
        FileOutputStream fos = new FileOutputStream(file);
        PrintStream pOut = new PrintStream(fos);
        return pOut;
    }

    private List<String> _findInvalidCoursesAndIds(String courseIdFilename, String validCourseCodesFilename) throws IOException {
        List<String> validCourseCodes = _loadDataFromFile(validCourseCodesFilename);
        List<String> allCourseIds = _loadDataFromFile(courseIdFilename);
        int count = 0;
        List<String> badCourses = new ArrayList<String>();
        for (String courseId: allCourseIds) {

            try {
                count++;
                if (count % 50 == 1) {
                    System.err.println("count = " + count);
                }
                Course course = courseService.getCourse(courseId, context);
                if (course.getCode() == null) {
                    System.err.println("Null course code: " + courseId);
                    continue;
                }
                if (!validCourseCodes.contains(course.getCode())) {
                    // This must be an invalid course
                    String badCourse = course.getCode() + "/" + course.getId() + "/" + course.getVersion().getVersionIndId();
                    badCourses.add(badCourse);
                }
            } catch (Exception e) {
                System.err.println("Error with: " + courseId + " " + e.getMessage());
            }
        }
        System.err.println("count = " + count);
        return badCourses;
    }

    private List<String> _loadValidCourses(String filePath) {
        List<String> courseData = _loadDataFromFile(filePath);
        for (int i = 0; i < courseData.size(); i++) {
            courseData.set(i, courseData.get(i).substring(0, 7));
        }
        return courseData;
    }

    private void _checkDuplicateCourses(String filePath) {
        List<String> courseData = _loadDataFromFile(filePath);
        String prev = null;
        for (String data: courseData) {
            String prefix = data.substring(0, 7);
            if (prev != null) {
                if (prefix.equals(prev)) {
                    System.err.println("Same: " + prefix);
                }
            }
            prev = prefix;
        }
        System.err.println("Done");
    }
    private void _sortValidCourses() throws IOException {
        List<String> courseData = _loadDataFromFile("validCourseInfo.txt");
        if (courseData == null) {
            return;
        }
        Collections.sort(courseData);
        // Print info
        _writeInfoToFile(courseData, "validCourseInfo.sorted.txt");
    }

    private List<String> _loadDataFromFile(String fileName) {
        return _loadDataFromFile(fileName, null);
    }

    private List<String> _loadDataFromFile(String fileName, BasicFilter filter) {
        Scanner scanner = null;
        String filePath = pathPrefix + fileName;
        try {
            scanner = new Scanner(new File(pathPrefix, fileName));

        } catch (FileNotFoundException e) {
            LOGGER.warn("No file found at: " + filePath);
            return null;
        }
        List<String> courseData = new ArrayList<String>();
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine().trim();
            if (!line.isEmpty() && !line.startsWith("#")) { // Lines with # refer to comments in data
                if (filter == null || filter.permits(line)) {
                    courseData.add(line);
                }
            }
        }
        return courseData;
    }

    private void _writeInfoToFile(List<String> listOfStrings, String fileName) throws IOException {
        PrintStream pOut = _createPrintStream(fileName);
        for (String line: listOfStrings) {
            pOut.println(line);
        }
        pOut.close();
    }

    private void _writeValidCourses(String courseIdsFilePath) throws IOException {
        ContextInfo context = new ContextInfo();
        List<String> validCourseInfo =  new ArrayList<String>();
        List<String> courseIds = _loadDataFromFile(courseIdsFilePath);
        if (courseIds == null) {
            return;
        }
        int count = 0;
        for (String courseId: courseIds) {
            try {
                List<CourseOfferingInfo> coInfos =
                        coService.getCourseOfferingsByCourse(courseId, context);
                if (!coInfos.isEmpty()) {
                    count++;
                } else {
                    // Skip courses with no COs.
                    continue;
                }
                // Map the id to course code
                CourseInfo course = courseService.getCourse(courseId, context);
                String courseCode = "None";
                if (course.getCode() != null) {
                    courseCode = course.getCode();
                }
                validCourseInfo.add(course.getCode() + "/" + course.getId() + "/" + course.getVersion().getVersionIndId());
            } catch (Exception ex) {
                LOGGER.info("Exception thrown on: " + courseId);
            }
            if (count % 50 == 1) {
                LOGGER.warn("count = " + count);
            }
        }
        // Print info
        _writeInfoToFile(validCourseInfo, "validCourseInfo.txt");
    }

    private Map<String, List<String>> _determineFormats(SpreadsheetData spreadsheet) throws IOException {
        Map<String, List<String>> courseCodeToFormats = new TreeMap<String, List<String>>();
        Set<String> uniqueFormats = new HashSet<String>();
        for (SpreadsheetRowData rowData: spreadsheet) {
            String courseCode = rowData.getValue("CM Course");
            List<String> formats = courseCodeToFormats.get(courseCode);
            if (formats == null) {
                // If nothing is in the map, create and map it
                formats = new ArrayList<String>();
                courseCodeToFormats.put(courseCode, formats);
            }
            String formatName = rowData.getValue("shortName");
            if (!formats.contains(formatName)) {
                formats.add(formatName);
            }
            uniqueFormats.add(formatName);
        }
        // Print it!
        _printFormats(courseCodeToFormats, uniqueFormats, "courseCodeToFormats.txt");
        return courseCodeToFormats;
    }

    private void _printFormats(Map<String, List<String>> courseCodeToFormats, Set<String> uniqueFormats, String fileName) throws IOException {
        // Called by _determineFormats which is the entry point
        PrintStream pOut = _createPrintStream(fileName);
        for (String courseCode: courseCodeToFormats.keySet()) {
            pOut.print("(" + courseCode + ") ");
            List<String> formats = courseCodeToFormats.get(courseCode);
            boolean firstTime = true;
            for (String format: formats) {
                if (firstTime) {
                    firstTime = false;
                    pOut.print("[");
                } else {
                    pOut.print("|"); // separator is |
                }
                pOut.print(format);
            }
            pOut.println("]");
        }
        boolean firstTime = true;
        for (String format: uniqueFormats) {
            if (firstTime) {
                firstTime = false;
                pOut.print("[");
            } else {
                pOut.print(", ");
            }
            pOut.print("(" + format + ")");
        }
        pOut.println("]");
        pOut.close();
    }

    private void _writeCourseIdsAndCodes(String courseIdsFilePath) throws IOException {
        ContextInfo context = new ContextInfo();
        Map<String, String> courseCodeToCourseId = new TreeMap<String, String>();
        List<String> courseIds = _loadDataFromFile(courseIdsFilePath);
        if (courseIds == null) {
            return;
        }
        int count = 0;
        for (String courseId: courseIds) {
            try {
                List<CourseOfferingInfo> coInfos =
                        coService.getCourseOfferingsByCourse(courseId, context);
                if (!coInfos.isEmpty()) {
                    count++;
                }
                // Map the id to course code
                CourseInfo course = courseService.getCourse(courseId, context);
                String courseCode = "None";
                if (course.getCode() != null) {
                    courseCode = course.getCode();
                }
                if (courseCodeToCourseId.containsKey(courseCode)) {
                    courseCode = _findNewCourseCode(courseCode, courseCodeToCourseId);
                }
                courseCodeToCourseId.put(courseCode, courseId);
            } catch (Exception ex) {
                LOGGER.info("Exception thrown on: " + courseId);
            }
            if (count % 100 == 1) {
                LOGGER.warn("count = " + count);
            }
        }
        // Print info
        PrintStream pOut = _createPrintStream("courseIdsAndCodes.txt");
        for (String coCode: courseCodeToCourseId.keySet()) {
            pOut.println(coCode + "/" + courseCodeToCourseId.get(coCode));
        }
        pOut.close();
        LOGGER.info("Total courses: " + courseIds.size());
        LOGGER.info("Courses with COs: " + count);
    }

    private String _findNewCourseCode(String codePrefix, Map<String, String> courseCodeToCourseId) {
        for (int i = 0; i < 1000; i++) {
            String testCode = codePrefix + i;
            if (!courseCodeToCourseId.containsKey(testCode)) {
                return testCode;
            }
        }
        return null;
    }

    private List<String> _loadCourseIdsDefault() {
        return _loadDataFromFile("courseIds.txt");
    }

    public ContextInfo getContext() {
        return context;
    }


}
