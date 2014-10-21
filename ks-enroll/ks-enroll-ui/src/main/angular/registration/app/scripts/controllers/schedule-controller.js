'use strict';

/*
 * Controller for the schedule functionality
 *
 * Event Handling
 * -- Emits: "courseDropped" --
 * -- Broadcasts: none
 * -- Receives: "courseStatusMessageRemoved" -- received from course-card-directive.js, removes status message
 *              "dropCourse" -- received from course-card-directive.js, shows message that course has been dropped
 *              "updateCourse" -- received from course-card-directive.js, updates course (edit credits, grading option, etc)
 */
angular.module('regCartApp')
    .controller('ScheduleCtrl', ['$scope', '$modal', '$timeout', '$q', 'STATUS', 'GRADING_OPTION', 'COURSE_TYPES', 'GlobalVarsService', 'RegUtil', 'TermsService', 'ScheduleService', 'CartService',
    function ScheduleCtrl($scope, $modal, $timeout, $q, STATUS, GRADING_OPTION, COURSE_TYPES, GlobalVarsService, RegUtil, TermsService, ScheduleService, CartService) {
        console.log('>> ScheduleCtrl');

        $scope.schedule = ScheduleService.getSchedule();
        $scope.registeredCredits = ScheduleService.getRegisteredCredits;
        $scope.registeredCourseCount = ScheduleService.getRegisteredCourseCount;
        $scope.waitlistedCredits = ScheduleService.getWaitlistedCredits;
        $scope.waitlistedCourseCount = ScheduleService.getWaitlistedCourseCount;
        $scope.userId = GlobalVarsService.getUserId;
        $scope.courseTypes = COURSE_TYPES;

        var numberOfDroppedWailistedCourses = 0,
            numberOfDroppedCourses = 0;


        /*
         Helper method to determine if the waitlist should be shown

         Any of these conditions will return a true:
         -- Student is waitlisted for more than 0 credits
         -- A waitlist message is on the screen
         */
        $scope.showWaitlist = function() {
            //return ($scope.waitlistedCredits() > 0 || numberOfDroppedWailistedCourses > 0);
            return (ScheduleService.getWaitlistedCourses().length > 0);
        };

        /*
        Helper method to determine if the schedule should be shown

        Any of these conditions will return a true:
        -- The waitlist section is being shown
        -- Student is registered for more than 0 credits
        -- A registration message is on the screen
         */
        $scope.showSchedule = function() {
            return ($scope.showWaitlist() || $scope.registeredCredits() > 0 || numberOfDroppedCourses > 0);
        };

        /*
         Helper method to determine if the calendar grid should be shown

         Student needs to have at least one non-TBA course: either in the
         cart, registered, or waitlisted.
         */
        $scope.showGrid = function() {
            return RegUtil.coursesHaveScheduledTimes(CartService.getCartCourses()) ||
                RegUtil.coursesHaveScheduledTimes(ScheduleService.getScheduledCourses());
        };

        /*
         Listens for the "courseStatusMessageRemoved" event and removes the card for the given course.
         */
        $scope.$on('courseStatusMessageRemoved', function(event, type, course, statusMessage) {
            if (angular.isObject(statusMessage) && angular.isDefined(statusMessage.action) && statusMessage.action === 'drop' && statusMessage.type === STATUS.success) {
                // Splice course card when message is dismissed
                ScheduleService.spliceCourse(type, course);
                switch (type) {
                    case COURSE_TYPES.waitlisted:
                        numberOfDroppedWailistedCourses--;
                        break;
                    case COURSE_TYPES.registered:
                        numberOfDroppedCourses--;
                }
            }
        });

        /*
         Listens for the "dropCourse" event and calls the appropriate RESTful service depending on the course type.
         */
        $scope.$on('dropCourse', function(event, type, course) {
            switch (type) {
                case COURSE_TYPES.waitlisted:
                    console.log('Drop waitlisted course');
                    event.promise = ScheduleService.dropFromWaitlist(course).then(function() {
                        // need a count on how many success drop message for WL are opened. So if there are no WL courses
                        // when we "X" the last success drop message the "Waitlisted" section name would disappear
                        numberOfDroppedWailistedCourses++;
                    });
                    break;
                case COURSE_TYPES.registered:
                    console.log('Drop registered course');
                    event.promise = ScheduleService.dropRegistrationGroup(course).then(function() {
                        // need a count on how many success drop message are opened. So if there are no courses when we "X" the last
                        // success drop message the section can be replaced by the splash screen (unless there are waitlisted courses)
                        numberOfDroppedCourses++;
                    });
            }
        });

        /*
         Listens for the "updateCourse" event and calls the appropriate RESTful service depending on the course type.
         */
        $scope.$on('updateCourse', function(event, type, course, newCourse) {
            switch (type) {
                case COURSE_TYPES.waitlisted:
                    console.log('Update waitlisted course');
                    event.promise = ScheduleService.updateWaitlistItem(TermsService.getTermId(), course, newCourse);
                    break;
                case COURSE_TYPES.registered:
                    console.log('Update registered course');
                    event.promise = ScheduleService.updateScheduleItem(TermsService.getTermId(), course, newCourse);
            }
        });

    }]);