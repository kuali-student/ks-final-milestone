'use strict';

angular.module('regCartApp')
    .service('CourseCalendarDataParser', ['GRID_CONSTANTS', 'DAY_CONSTANTS', 'RegUtil', 'GlobalVarsService',
    function CourseCalendarDataParser(GRID_CONSTANTS, DAY_CONSTANTS, RegUtil, GlobalVarsService) {
        var cart,
            registered,
            waitlisted;

        var calendar,
            conflictMap,
            dirty = false;


        this.getCalendar = function() {
            if (!calendar || dirty) {
                // Build the calendar if it hasn't been built or if it is dirty
                calendar = buildCalendar();
            }

            return calendar;
        };

        this.setCart = function(courses) {
            var old = cart;
            cart = angular.copy(courses); // Use copies so we don't check against the same array instance

            if (shouldRebuild(cart, old)) {
                dirty = true;
            }

            return dirty;
        };

        this.setRegistered = function(courses) {
            var old = registered;
            registered = angular.copy(courses); // Use copies so we don't check against the same array instance

            if (shouldRebuild(registered, old)) {
                dirty = true;
            }

            return dirty;
        };

        this.setWaitlisted = function(courses) {
            var old = waitlisted;
            waitlisted = angular.copy(courses); // Use copies so we don't check against the same array instance

            if (shouldRebuild(waitlisted, old)) {
                dirty = true;
            }

            return dirty;
        };

        /*
         Checks if data in a given list has changed
         */
        function shouldRebuild(newList, oldList) {
            if (!angular.isArray(oldList) || !angular.isArray(newList) || newList.length !== oldList.length) {
                // Always rebuild if the old or new lists aren't arrays
                return true;
            }

            for (var i = 0; i < oldList.length; i++) {
                var oldCourse = oldList[i],
                    newCourse = RegUtil.getCourseFromList(oldCourse, newList);

                if (newCourse === null || (newCourse.dropped && !oldCourse.dropped)) {
                    // Rebuild if the newCourse doesn't exist or if it has been dropped
                    return true;
                }
            }

            // No changes have been detected, don't rebuild
            return false;
        }

        /*
         Returns formatted data for display in the course calendar
         */
        function buildCalendar() {
            dirty = false;

            conflictMap = {};

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
            var calendar = convertMapToCalendar(dayMap);

            // Update the schedule with conflicts so they can be flagged
            // with an icon in the card. Cart courses are not flagged.
            GlobalVarsService.setConflictMap(conflictMap);
            GlobalVarsService.updateConflicts(registered, 'REG');
            GlobalVarsService.updateConflicts(waitlisted, 'WAIT');

            return calendar;
        }

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

            var startTime = RegUtil.convertTimeStringToTime(scheduleComponent.startTime);
            var endTime = RegUtil.convertTimeStringToTime(scheduleComponent.endTime);

            /*
             Course started before midnight, and ended after midnight, so
             we need to extend the end time so that they can display on the
             same day.
             */
            if (endTime < startTime) {
                endTime += 1440; // 24 hours
            }

            var course = {
                index: getCourseIndex(courseDetails),
                courseCode: courseDetails.courseCode,
                startTime: startTime,
                endTime: endTime,
                type: type,
                key: courseDetails.courseCode + '.' + day + '.' + startTime + '.' + endTime, // Hash key for this course
                details: fullCourse
            };

            dayMap[day].push(course);

            return dayMap;
        }

        /*
         Take the full day map and convert to the full calendar format,
         looking for conflicts along the way.

         The default start and end times determine the base grid. If
         courses go outside of that initial range the grid will be
         expanded to fit.
         */
        function convertMapToCalendar(dayMap) {
            var typeArray = ['REG', 'WAIT', 'CART'],
                days = [],
                startTime = GRID_CONSTANTS.defaultStart,
                endTime = GRID_CONSTANTS.defaultEnd,
                buffer = GRID_CONSTANTS.defaultBuffer; // the amount of buffer (in minutes) to give to either side of the calendar.

            angular.forEach(DAY_CONSTANTS.dayArray, function(dayString) {
                var courses = dayMap[dayString];
                if (angular.isArray(courses)) {
                    // Iterate through all of the courses and bucket them by their type.
                    var courseMap = {};
                    angular.forEach(courses, function(course) {
                        var type = course.type;
                        if (!angular.isArray(courseMap[type])) {
                            courseMap[type] = [];
                        }
                        courseMap[type].push(course);

                        // Determine the earliest start time (- buffer)
                        if (startTime === null || course.startTime - buffer < startTime) {
                            startTime = course.startTime - buffer;
                        }

                        // Determine the latest end time (+ buffer)
                        if (endTime === null || course.endTime + buffer > endTime) {
                            endTime = course.endTime + buffer;
                        }
                    });

                    // In the order defined in the typeArray, iterate through the courses again and add them to the day.
                    var dayCourses = [];
                    angular.forEach(typeArray, function(type) {
                        angular.forEach(courseMap[type], function(course) {
                            dayCourses.push(course);
                        });
                    });

                    // Update the courses with the conflict data
                    updateCoursesWithConflictData(dayCourses);

                    // Sort the day's courses by their start time
                    sortCourses(dayCourses);

                    // Add the day to the days array
                    days.push({
                        day: dayString,
                        courses: dayCourses
                    });
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
         See if the course conflicts with any courses already slotted
         in this day. If it does, add a conflict for both courses.
         */
        function updateCoursesWithConflictData(courses) {
            // Initialize the lanes with an initial lane, and initialize the conflict map
            var lanes = [[]],
                laneMap = {};

            // Slot the course in the appropriate lane
            angular.forEach(courses, function(course) {
                course.conflicts = []; // The schedule data for the courses that this course conflicts with
                course.lane = 0; // The lane this course is in relative to its conflicts (each new conflicting course gets a higher value).

                var slotted = false;
                angular.forEach(lanes, function(lane) {
                    var conflicting = false;
                    angular.forEach(lane, function(slottedCourse) {
                        if (RegUtil.coursesConflict(course, slottedCourse)) {

                            // add both courses to the conflict map
                            addConflictToMap(course, slottedCourse);
                            addConflictToMap(slottedCourse, course);

                            conflicting = true;

                            // Make sure both the course & the conflicting course know about the courses they conflict with.
                            course.conflicts.push(slottedCourse.key);
                            slottedCourse.conflicts.push(course.key);
                        }
                    });

                    // Check to see if this course conflicts with any course in this lane.
                    if (!conflicting && !slotted) {
                        // Slot this course in this lane
                        course.lane = lanes.indexOf(lane);
                        laneMap[course.key] = course.lane;

                        // Add the course to the lane & sort the lane by startTime
                        lane.push(course);
                        sortCourses(lane);

                        slotted = true;
                    }
                });

                if (!slotted) {
                    // The course conflicted with every lane, we need to add a new lane for it.
                    var position = lanes.push([course]);

                    // Slot this course in this lane
                    course.lane = position - 1;
                    laneMap[course.key] = course.lane;
                }
            });

            // Update the course to know how many lanes are around it so that it can be laid out correctly in the UI.
            angular.forEach(courses, function(course) {
                var maxConflictLane = 0;
                angular.forEach(course.conflicts, function(key) {
                    if (laneMap[key] > maxConflictLane) {
                        maxConflictLane = laneMap[key];
                    }
                });

                course.conflictCount = Math.max(maxConflictLane, course.lane);

                // Create the truncated version of the course code based on # of conflicts
                switch (course.conflictCount) {
                    case 0:
                        course.truncated = course.courseCode;
                        break;
                    case 1:
                        course.truncated = course.courseCode.substring(0, 1) + '...';
                        break;
                    default:
                        course.truncated = course.courseCode.substring(0, 1);
                }
            });

            return courses;
        }

        /*
         Sort a list of courses by their start time
         */
        function sortCourses(courses) {
            courses.sort(function(a, b) {
                return (a.startTime === b.startTime ? 0 : (a.startTime < b.startTime ? -1 : 1)); // Sort by startTime
            });

            return courses;
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

        /*
         Add the indicated course to the conflict map (indexed by regGroupId)
         */
        function addConflictToMap(course, conflict) {
            var regGroupId = course.details.regGroupId;
            var conflictOffering = {courseCode: conflict.details.courseCode,
                                    regGroupCode: conflict.details.regGroupCode,
                                    type: conflict.type
                                    };
            if (!angular.isArray(conflictMap[regGroupId])) {
                conflictMap[regGroupId] = [];
            }
            var matchFound = false;
            for (var i = 0; i < conflictMap[regGroupId].length; i++) {
                if (angular.equals(conflictMap[regGroupId][i], conflictOffering)) {
                    matchFound = true;
                    break;
                }
            }
            if (!matchFound) {
                conflictMap[regGroupId].push(conflictOffering);
            }
        }
    }]);




angular.module('regCartApp')

    /*
    The calendar directive creates a graphical view of schedule data (registered, waitlist, and/or cart).
    */
    .directive('courseCalendar', ['$timeout', '$window', function($timeout, $window) {
        return {
            restrict: 'E',
            scope: {
                size: '@'
            },
            templateUrl: 'partials/courseCalendar.html',
            controller: ['$scope', 'CourseCalendarDataParser', 'CartService', 'ScheduleService',
            function($scope, CourseCalendarDataParser, CartService, ScheduleService) {

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

                $scope.clearSelectedCourse = function() {
                    $scope.selectedCourse = null;
                };


                // Init the calendar size to be 'small' || 'large'
                var size = $scope.size;
                if (size !== 'small') {
                    size = 'large';
                }
                $scope.calendarSize = size;
                $scope.orientation = ''; // Used by course-items to scale & position themselves correctly


                $scope.hideRegistered = false;
                $scope.hideWaitlisted = false;
                $scope.hideCart = false;

                // Watch the hideX variables and clear the selected course when then change.
                var clearSelectedCourse = function() {
                    if ($scope.selectedCourse !== null) {
                        $scope.selectedCourse = null;
                    }
                };
                $scope.$watch('hideRegistered', clearSelectedCourse);
                $scope.$watch('hideWaitlisted', clearSelectedCourse);
                $scope.$watch('hideCart', clearSelectedCourse);


                $scope.visibleTimeRange = [];

                // Create the list of times (date objects) to be shown in the view
                $scope.times = [];
                for (var i = 0; i < 24; i++) {
                    $scope.times.push(new Date(0, 0, 0, i, 0, 0, 0));
                }


                $scope.days = [];

                var redrawing = false;
                function redraw() {
                    if (redrawing) {
                        return;
                    }

                    redrawing = true;
                    $timeout(function() {
                        redrawing = false;

                        var calendar = CourseCalendarDataParser.getCalendar();

                        // Update the visible time range if it has changed
                        var visibleMin = Math.floor(calendar.timeRange[0] / 60) * 60,
                            visibleMax = Math.ceil(calendar.timeRange[1] / 60) * 60;

                        if ($scope.visibleTimeRange.length === 0) {
                            $scope.visibleTimeRange = [ visibleMin, visibleMax ];
                        } else {
                            if (!$scope.visibleTimeRange[0] || $scope.visibleTimeRange[0] !== visibleMin) {
                                $scope.visibleTimeRange[0] = visibleMin;
                            }

                            if (!$scope.visibleTimeRange[1] || $scope.visibleTimeRange[1] !== visibleMax) {
                                $scope.visibleTimeRange[1] = visibleMax;
                            }
                        }

                        // Opportunity for further optimization:
                        // It would be better to selectively add/remove rather than reset whole hog
                        $scope.days = calendar.days;

                    }, 50); // Delay a bit to pool up any redraws happening in quick succession
                }


                $scope.registered = ScheduleService.getRegisteredCourses();
                $scope.waitlisted = ScheduleService.getWaitlistedCourses();
                $scope.cart = CartService.getCartCourses();

                $scope.$watch('registered', function(list) {
                    var changed = CourseCalendarDataParser.setRegistered(list);
                    if (changed) {
                        redraw();
                    }
                }, true);
                $scope.$watch('waitlisted', function(list) {
                    var changed = CourseCalendarDataParser.setWaitlisted(list);
                    if (changed) {
                        redraw();
                    }
                }, true);
                $scope.$watchCollection('cart', function(list) {
                    var changed = CourseCalendarDataParser.setCart(list);
                    if (changed) {
                        redraw();
                    }
                });

                redraw();

            }],
            link: function(scope, element) {

                /**
                 * Helper method to calculate the current orientation of the calendar
                 *
                 * @return string - horizontal || vertical
                 */
                function getOrientation() {
                    // This could be refactored to not use the helper elements or jQuery
                    return (element.find('.kscr-CourseCalendar-orientation--horizontal:visible').length > 0 ? 'horizontal' : 'vertical');
                }

                // Store off the starting orientation
                $timeout(function() {
                    scope.orientation = getOrientation();
                });

                if (scope.calendarSize === 'large') {
                    angular.element($window).on('resize', function() {
                        $timeout(function() {
                            var o = getOrientation();
                            if (o !== scope.orientation) {
                                // console.log('Orientation changed: ' + scope.orientation + ' to ' + o);

                                scope.orientation = o;
                                scope.$broadcast('orientationChanged', scope.orientation);
                            }
                        });
                    });
                }
            }
        };
    }]);


/**
 * Course Calendar - Course Lane Directive
 */
angular.module('regCartApp')
    .directive('courseCalendarLane', ['$timeout', function($timeout) {
        return {
            restrict: 'CAE',
            link: function(scope, element, attr) {

                scope.scrollAfter = attr.scrollAfter || null;


                /**
                 * Update the lane's height to be accurate to the # of visible elements in it
                 */
                scope.updateLaneHeight = function() {
                    var items = element.children();
                    if (items.length > 0) {
                        var lanes = 0;
                        angular.forEach(scope.day.courses, function(course) {
                            if (lanes < (course.lane + 1)) {
                                lanes = (course.lane + 1);
                            }
                        });

                        var itemHeight = element.children()[0].offsetHeight,
                            targetHeight = (itemHeight * lanes);

                        if (itemHeight > 0 && targetHeight > 0) {
                            element.height(targetHeight);
                        }
                    }
                };


                $timeout(scope.updateLaneHeight);

                // Update the lane height when the orientation changes
                scope.$on('orientationChanged', function() {
                    $timeout(scope.updateLaneHeight);
                });

                // Update the lane height when the 'updateLaneHeight' event is received
                scope.$on('updateLaneHeight', function() {
                    $timeout(scope.updateLaneHeight);
                });
            }
        };
    }]);


/**
 * Course Calendar - Course Item Directive
 */
angular.module('regCartApp')
    .directive('courseCalendarItem', ['$timeout', function($timeout) {
        return {
            restrict: 'CAE',
            link: function(scope, element) {

                /**
                 * Layout the element within its parent based on the course duration and position
                 */
                function layout() {
                    var timeRange = scope.visibleTimeRange,
                        totalRange = timeRange[1] - timeRange[0],
                        duration = scope.course.endTime - scope.course.startTime;

                    // Calculate the proportion of the total size & the position as a percentage so
                    // it doesn't have to be updated when the window is resized.
                    var proportion = duration * 100 / totalRange, // Proportion of total width (%)
                        position = (scope.course.startTime - timeRange[0]) * 100 / totalRange; // Position within the parent element (%);

                    // Persist the calculated position and proportion on the course
                    scope.course.layout = {
                        position: position,
                        proportion: proportion
                    };

                    // Update the element to be position correctly relative to the parent container
                    element.css({
                        left: position + '%',
                        top: position + '%',
                        height: proportion + '%',
                        width: proportion + '%'
                    });

                    // Offset the course for conflicts
                    offsetForConflicts();

                    // Update the lane height now that we have a new course element
                    scope.$emit('updateLaneHeight');
                }

                /**
                 * Offset the course based on the conflict position & window layout
                 */
                function offsetForConflicts() {
                    if (angular.isUndefined(scope.course.layout)) {
                        layout();
                        return;
                    }

                    var css = {
                        left: scope.course.layout.position + '%',
                        top: scope.course.layout.position + '%',
                        height: scope.course.layout.proportion + '%',
                        width: scope.course.layout.proportion + '%'
                    };

                    if (scope.course.conflictCount > 0) {
                        if (scope.orientation === 'horizontal') {
                            // Landscape / large-format layout - courses laid out horizontally
                            css.top = (scope.course.lane * element[0].offsetHeight) + 'px'; // Offset this block in px from the top according to its conflict position
                        } else {
                            // Portrait / small-format / mobile layout - courses laid out vertically
                            var width = 100; // Width of the element based on the # of lanes around it

                            if (scope.course.conflictCount > 0) {
                                width = (100 / (scope.course.conflictCount + 1)); // Lane width
                            }

                            css.left = (width * scope.course.lane) + '%'; // Offset this block a % from the left according to its conflict position
                            css.width = width + '%'; // Portrait layout conflicts get a proportion of the total width.
                        }
                    }

                    element.css(css);
                }


                $timeout(layout);

                // When the window is resized, update the element's offset for conflicts
                scope.$on('orientationChanged', function() {
                    $timeout(offsetForConflicts);
                });

                // Fire a 'course-clicked' event when the course is clicked. This is caught and handled by the CourseCalendar directive.
                element.bind('click', function() {
                    scope.$emit('course-clicked', scope.course);
                });

            }
        };
    }]);

