'use strict';

angular.module('regCartApp')

    /*
    The calendar directive creates a graphical view of schedule data (registered, waitlist, and/or cart).
    */
    .directive('courseCalendar', ['DAY_CONSTANTS', 'GlobalVarsService', function(DAY_CONSTANTS, GlobalVarsService) {
        return {
            restrict: 'E',
            templateUrl: 'partials/courseCalendar.html',
            link:function(scope,element,attrs) {

                /*
                Returns formatted data for display in the course calendar
                 */
                scope.courseCalendar = function(schedules, cart) {

                    // First, we split the data up by days
                    var dayMap=initDayMap();

                    if (angular.isArray(schedules)) {
                        angular.forEach(schedules, function(schedule) {
                            // iterate over registered courses
                            dayMap = iterateCourseList(dayMap, schedule.registeredCourseOfferings, "REG");
                            // iterate over waitlisted courses
                            dayMap = iterateCourseList(dayMap, schedule.waitlistCourseOfferings, "WAIT");
                        })
                    }

                    if (cart) {
                        // iterate over cart
                        dayMap = iterateCourseList(dayMap, cart.items, "CART");
                    }

                    // Finally, convert all of the day data to the calendar format
                    return convertMapToCalendar(dayMap);
                };

                /*
                Monday through Friday are always shown, so they should be initialized
                with an empty array
                 */
                function initDayMap() {
                    var dayMap={};

                    dayMap[DAY_CONSTANTS.monday] = [];
                    dayMap[DAY_CONSTANTS.tuesday] = [];
                    dayMap[DAY_CONSTANTS.wednesday] = [];
                    dayMap[DAY_CONSTANTS.thursday] = [];
                    dayMap[DAY_CONSTANTS.friday] = [];

                    return dayMap;
                }

                /*
                Iterate over each course in the list and group the AOs by day
                 */
                function iterateCourseList(dayMap, courseList, type) {
                    angular.forEach(courseList, function(course) {
                        var courseDetails = {courseCode: course.courseCode,
                                             regGroup: course.regGroupCode
                        };

                        // iterate over activity offerings
                        switch (type) {
                            case 'CART': // courses in the cart;
                                angular.forEach(course.schedule, function(ao) {
                                    angular.forEach(ao.activityOfferingLocationTime, function (locationTime) {
                                        var scheduleComponent = locationTime.time;
                                        dayMap = updateDayMap(dayMap, courseDetails, scheduleComponent, type);
                                    });
                                });
                                break;
                            default: // registered and waitlisted courses
                                angular.forEach(course.activityOfferings, function(ao) {
                                    angular.forEach(ao.scheduleComponents, function (scheduleComponent) {
                                        dayMap = updateDayMap(dayMap, courseDetails, scheduleComponent, type);
                                    })
                                });
                        }
                    });

                    return dayMap;
                }

                /*
                Add the course to the map for for appropriate day(s). TBA courses will not be
                shown on the grid.

                We are using the days String to determine this as it is more reliable than
                the booleans.
                 */
                function updateDayMap(dayMap, courseDetails, scheduleComponent, type) {
                    angular.forEach(DAY_CONSTANTS.dayArray, function(day) {
                        if (scheduleComponent.days.indexOf(day) > -1) {
                            dayMap = addToDayMap(dayMap, courseDetails, scheduleComponent, day, type);
                        }
                    });
                    return dayMap;
                }

                /*
                Add the AO to the day map
                 */
                function addToDayMap(dayMap, courseDetails, scheduleComponent, day, type) {
                    if (!angular.isArray(dayMap[day])) {
                        dayMap[day] = [];

                        /*
                        If the student has any classes on saturday or sunday,
                        add both days to the calendar
                         */
                        if (day === DAY_CONSTANTS.saturday && (!angular.isArray(dayMap[DAY_CONSTANTS.sunday]))) {
                            dayMap[DAY_CONSTANTS.sunday] = [];
                        }
                        if (day === DAY_CONSTANTS.sunday && (!angular.isArray(dayMap[DAY_CONSTANTS.saturday]))) {
                            dayMap[DAY_CONSTANTS.saturday] = [];
                        }
                    }

                    var startTime = convertTimeStringToTime(scheduleComponent.startTime);
                    var endTime =  convertTimeStringToTime(scheduleComponent.endTime);

                    /*
                     Course started before midnight, and ended after midnight, so
                     we need to extend the end time so that they can display on the
                     same day.
                     */
                    if (endTime < startTime) {
                        endTime += 1440;
                    }

                    var course={index: getCourseIndex(courseDetails),
                                courseCode: courseDetails.courseCode,
                                startTime: startTime,
                                endTime: endTime,
                                type: type
                    };

                    dayMap[day].push(course);

                    return dayMap;
                }

                /*
                Take the full day map and convert to the full calendar format,
                looking for conflicts along the way.
                 */
                function convertMapToCalendar(dayMap) {
                    var typeArray=['REG', 'WAIT', 'CART'];
                    var days = [];
                    var startTime = 7 * 60; // 7 am
                    var endTime = 19 * 60; // 7 pm
                    var buffer = 60; // the amount of buffer (in minutes) to give to either side of the calendar.
                    angular.forEach(DAY_CONSTANTS.dayArray, function(dayString) {
                        var courses=dayMap[dayString];
                        if (angular.isArray(courses)) {
                            var rows=[];
                            var courseMap={};
                            angular.forEach(courses, function(course) {
                                var type = course.type;
                                if (!angular.isArray(courseMap[type])) {
                                    courseMap[type] = [];
                                }
                                courseMap[type].push(course);
                                if (course.startTime - buffer < startTime) {
                                    startTime = course.startTime - buffer;
                                }
                                if (course.endTime + buffer > endTime) {
                                    endTime = course.endTime + buffer;
                                }
                            });
                            angular.forEach(typeArray, function (type) {
                                var courses=courseMap[type];
                                angular.forEach(courses, function(course) {
                                    rows = addCourseToRow(rows, course);
                                });
                            });
                            var day = {day:dayString, rows: rows};
                            days.push(day);
                        }
                    });
                  if (startTime < 0) {
                      startTime = 0; // midnight
                  }
                  return {timeRange: [startTime, endTime],
                          days: days};
                }

                /*
                Go through each row and add the course to the first row with no conflicts.
                 */
                function addCourseToRow(rows, course) {
                    var courseSlotted = false;
                    for (var i = 0; i < rows.length; i++) {
                        var row = rows[i];
                        var courses = row.courses;
                        if (!angular.isArray(courses)) {
                            courses = [];
                        }
                        var conflictFound = checkRowForConflict(courses, course);
                        if (!conflictFound) {
                            courses.push(course);

                            // Sort the course by their start time
                            courses.sort(function(a, b) {
                                return (a.startTime === b.startTime ? 0 : (a.startTime < b.startTime ? -1 : 1));
                            });

                            courseSlotted = true;
                            break;
                        }
                    }
                    // If all existing rows have a conflict, create a new one for the course
                    if (!courseSlotted) {
                        var newCourses = [];
                        newCourses.push(course);
                        rows.push({courses: newCourses});
                    }
                    return rows;
                }

                /*
                See if the course conflicts with any courses already slotted
                in this row. If it does, add a conflict for both courses.
                 */
                function checkRowForConflict(slottedCourses, course) {
                    var conflictFound = false;
                    var start = course.startTime;
                    var end = course.endTime;
                    for (var j = 0; j < slottedCourses.length; j++) {
                        var slottedCourse = slottedCourses[j];
                        var slottedStart = slottedCourse.startTime;
                        var slottedEnd = slottedCourse.endTime;
                        if (start <= slottedEnd && end >= slottedStart) {
                            if (!angular.isArray(course.conflicts)) {
                                course.conflicts = [];
                            }
                            if (!angular.isArray(slottedCourse.conflicts)) {
                                slottedCourse.conflicts = [];
                            }
                            course.conflicts.push(slottedCourse.index);
                            slottedCourse.conflicts.push(course.index);
                            conflictFound = true;
                            break;
                        }
                    }
                    return conflictFound;
                }

                /*
                Converts the start time to an integer representing the minutes since
                start of day.
                 */
                function convertTimeStringToTime(str) {
                    var colonIndex = str.indexOf(':'),
                        h = str.substring(0, colonIndex),
                        m = str.substring(colonIndex + 1, str.indexOf(' ')),
                        isPm = str.toLowerCase().indexOf('pm') !== -1;

                    var time = parseInt(h) * 60;
                    time += parseInt(m);

                    if (isPm && h !== '12') {
                        time += (60 * 12);
                    }

                    return time;
                }

                /*
                Use the course code + the reg group code to retrieve an index
                for color-coding and conflict matching.

                All indexes will be stored in the GlobalVarsService, so that
                if a student drops or re-adds a course the colors will remain
                consistant (at least until they refresh, at which time the
                indexes will be reset).
                 */
                function getCourseIndex(courseDetails) {
                    return GlobalVarsService.getCourseIndex(courseDetails);
                }

            }
        }
    }])

;
