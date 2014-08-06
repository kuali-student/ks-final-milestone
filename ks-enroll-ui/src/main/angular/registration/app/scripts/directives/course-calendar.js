'use strict';

angular.module('regCartApp')
    .service('CourseCalendarDataParser', ['DAY_CONSTANTS', 'GlobalVarsService', function(DAY_CONSTANTS, GlobalVarsService) {

        /*
         Returns formatted data for display in the course calendar
         */
        this.buildCalendar = function(registered, waitlisted, cart) {
            // First, we split the data up by days
            var dayMap = initDayMap();

            if (registered) {
                // iterate over registered courses
                dayMap = iterateCourseList(dayMap, registered, 'REG');
            }

            if (waitlisted) {
                // iterate over waitlisted courses
                dayMap = iterateCourseList(dayMap, waitlisted, 'WAIT');
            }

            if (cart) {
                // iterate over cart
                dayMap = iterateCourseList(dayMap, cart, 'CART');
            }

            // Finally, convert all of the day data to the calendar format
            return convertMapToCalendar(dayMap);
        };

        /*
         Monday through Friday are always shown, so they should be initialized
         with an empty array
         */
        function initDayMap() {
            var dayMap = {};

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
                // do not show dropped courses on the calendar
                if (!course.dropped) {
                    var courseDetails = {
                        courseCode: course.courseCode,
                        regGroup: course.regGroupCode
                    };

                    // Standardize the activity offering data structure between the cart & the other types
                    if (type === 'CART' && angular.isUndefined(course.activityOfferings)) {
                        course.activityOfferings = [];
                        angular.forEach(course.schedule, function(ao) {
                            if (angular.isUndefined(ao.scheduleComponents)) {
                                ao.scheduleComponents = [];
                                angular.forEach(ao.activityOfferingLocationTime, function (locationTime) {
                                    ao.scheduleComponents.push(locationTime.time);
                                });
                            }
                            if (angular.isUndefined(ao.activityOfferingTypeName)) {
                                ao.activityOfferingTypeName = ao.activityOfferingType;
                            }
                            course.activityOfferings.push(ao);
                        });
                    }

                    // iterate over activity offerings
                    angular.forEach(course.activityOfferings, function(ao) {
                        angular.forEach(ao.scheduleComponents, function (scheduleComponent) {
                            dayMap = updateDayMap(dayMap, courseDetails, scheduleComponent, type, course);
                        });
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
        function updateDayMap(dayMap, courseDetails, scheduleComponent, type, course) {
            if (!scheduleComponent.isTBA) {
                angular.forEach(DAY_CONSTANTS.dayArray, function(day) {
                    if (scheduleComponent.days.indexOf(day) > -1) {
                        dayMap = addToDayMap(dayMap, courseDetails, scheduleComponent, day, type, course);
                    }
                });
            }
            return dayMap;
        }

        /*
         Add the AO to the day map
         */
        function addToDayMap(dayMap, courseDetails, scheduleComponent, day, type, fullCourse) {
            if (!scheduleComponent.startTime || !scheduleComponent.endTime) {
                // Can't do anything without a start or end time
                return dayMap;
            }

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
            var endTime = convertTimeStringToTime(scheduleComponent.endTime);

            /*
             Course started before midnight, and ended after midnight, so
             we need to extend the end time so that they can display on the
             same day.
             */
            if (endTime < startTime) {
                endTime += 1440;
            }

            var course = {
                index: getCourseIndex(courseDetails),
                courseCode: courseDetails.courseCode,
                startTime: startTime,
                endTime: endTime,
                type: type,
                details: fullCourse
            };

            dayMap[day].push(course);

            return dayMap;
        }

        /*
         Take the full day map and convert to the full calendar format,
         looking for conflicts along the way.
         */
        function convertMapToCalendar(dayMap) {
            var typeArray = ['REG', 'WAIT', 'CART'];
            var days = [];
            var startTime = null; // 7 am
            var endTime = null; // 7 pm
            var buffer = 60; // the amount of buffer (in minutes) to give to either side of the calendar.
            angular.forEach(DAY_CONSTANTS.dayArray, function(dayString) {
                var courses = dayMap[dayString];
                if (angular.isArray(courses)) {
                    var rows = [];
                    var courseMap = {};
                    angular.forEach(courses, function(course) {
                        var type = course.type;
                        if (!angular.isArray(courseMap[type])) {
                            courseMap[type] = [];
                        }
                        courseMap[type].push(course);
                        if (startTime === null || course.startTime - buffer < startTime) {
                            startTime = course.startTime - buffer;
                        }
                        if (endTime === null || course.endTime + buffer > endTime) {
                            endTime = course.endTime + buffer;
                        }
                    });
                    angular.forEach(typeArray, function (type) {
                        var courses = courseMap[type];
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

            return {
                timeRange: [startTime, endTime],
                days: days
            };
        }

        /*
         Go through each row and add the course to the first row with no conflicts.
         */
        function addCourseToRow(rows, course) {
            var courseSlotted = false;
            var sorter = function(a, b) {
                return (a.startTime === b.startTime ? 0 : (a.startTime < b.startTime ? -1 : 1));
            };

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
                    courses.sort(sorter);

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
         consistent (at least until they refresh, at which time the
         indexes will be reset).
         */
        function getCourseIndex(courseDetails) {
            return GlobalVarsService.getCourseIndex(courseDetails);
        }
    }]);




angular.module('regCartApp')

    /*
    The calendar directive creates a graphical view of schedule data (registered, waitlist, and/or cart).
    */
    .directive('courseCalendar', function() {
        return {
            restrict: 'E',
            scope: {
                size: '@'
            },
            templateUrl: 'partials/courseCalendar.html',
            controller: ['$scope', 'CourseCalendarDataParser', 'GlobalVarsService', function($scope, CourseCalendarDataParser, GlobalVarsService) {

                /*
                 Filter used by view to show courses based on the toggles in the legend
                 */
                $scope.courseFilter = function(course) {
                    var select = true;
                    switch (course.type) {
                        case 'REG':
                            select = !$scope.hideRegistered;
                            break;
                        case 'WAIT':
                            select = !$scope.hideWaitlisted;
                            break;
                        case 'CART':
                            select = !$scope.hideCart;
                            break;
                    }

                    return select;
                };

                /*
                 Filter used by the view to restrict the visible time range to the data being shown
                 */
                $scope.timeFilter = function(time) {
                    var h = time.getHours();

                    return ($scope.visibleTimeRange[0] / 60) <= h && h <= ($scope.visibleTimeRange[1] / 60);
                };


                /*
                 Handle the course-clicked event that is emitted from the child course-calendar-item directive
                 */
                $scope.selectedCourse = null;
                $scope.$on('course-clicked', function(event, course) {
                    if ($scope.selectedCourse && $scope.selectedCourse.index === course.index) {
                        $scope.selectedCourse = null;
                    } else {
                        $scope.selectedCourse = course;
                    }
                    $scope.$apply();
                });


                var size = $scope.size;
                if (size !== 'small') {
                    size = 'large';
                }
                $scope.calendarSize = size;

                $scope.hideRegistered = false;
                $scope.hideWaitlisted = false;
                $scope.hideCart = false;

                $scope.visibleTimeRange = [];


                // Create the list of times (date objects) to be shown in the view
                $scope.times = [];
                for (var i = 0; i < 24; i++) {
                    $scope.times.push(new Date(0, 0, 0, i, 0, 0, 0));
                }

                $scope.registered = GlobalVarsService.getRegisteredCourses();
                $scope.waitlisted = GlobalVarsService.getWaitlistedCourses();
                $scope.cart = GlobalVarsService.getCartCourses();


                var init = function(list) {
                    if (list) {
                        var calendar = CourseCalendarDataParser.buildCalendar($scope.registered, $scope.waitlisted, $scope.cart);
                        $scope.visibleTimeRange = [
                            Math.floor(calendar.timeRange[0] / 60) * 60,
                            Math.ceil(calendar.timeRange[1] / 60) * 60
                        ];

                        $scope.days = calendar.days;
                    }
                };

                init(true);
                $scope.$watchCollection('registered', init);
                $scope.$watchCollection('waitlisted', init);
                $scope.$watchCollection('cart', init);
            }]
        };
    })
;


/**
 * Course Calendar - Course Item Directive
 */
angular.module('regCartApp')
    .directive('courseCalendarItem', ['$timeout', '$window', function($timeout, $window) {
        return {
            restrict: 'CAE',
            scope: false,
            link: function(scope, element) {

                function layout() {
                    var timeRange = scope.visibleTimeRange,
                        totalRange = timeRange[1] - timeRange[0],
                        duration = scope.course.endTime - scope.course.startTime;

                    // Calculate the width & position from the left as a percentage so
                    // it doesn't have to be updated when the window is resized.
                    var proportion = duration * 100 / totalRange,
                        offset = (scope.course.startTime - timeRange[0]) * 100 / totalRange;

                    // Update the element to be position correctly relative to the parent container
                    element.css({
                        left: offset + '%',
                        top: offset + '%',
                        height: proportion + '%',
                        width: proportion + '%'
                    });

                    resize();
                }

                function resize() {
                    // Make sure the parent still knows about the height of its children
                    var height = element[0].offsetHeight,
                        parent = element.parent();

                    if (parent.height() < height) {
                        parent.height(height);
                    }
                }

                angular.element($window).on('resize', function() {
                    $timeout(resize);
                });

                $timeout(layout);


                element.bind('click', function() {
                    scope.$emit('course-clicked', scope.course);
                });

            }
        };
    }]);

