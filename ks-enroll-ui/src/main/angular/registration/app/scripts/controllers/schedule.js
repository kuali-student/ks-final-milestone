'use strict';

angular.module('regCartApp')
    .controller('ScheduleCtrl', ['$scope', '$modal', '$timeout', 'STATUS', 'GRADING_OPTION', 'COURSE_TYPES', 'ScheduleService', 'GlobalVarsService',
    function ($scope, $modal, $timeout, STATUS, GRADING_OPTION, COURSE_TYPES, ScheduleService, GlobalVarsService) {

        $scope.getSchedules = GlobalVarsService.getSchedule;
        $scope.registeredCredits = GlobalVarsService.getRegisteredCredits;
        $scope.registeredCourseCount = GlobalVarsService.getRegisteredCourseCount;
        $scope.waitlistedCredits = GlobalVarsService.getWaitlistedCredits;
        $scope.waitlistedCourseCount = GlobalVarsService.getWaitlistedCourseCount;
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
            return ($scope.waitlistedCredits() > 0 || numberOfDroppedWailistedCourses > 0);
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
            var showGrid = false;

            //check the cart
            angular.forEach(GlobalVarsService.getCartCourses(), function(course) {
                angular.forEach(course.schedule, function(schedule) {
                    if (!showGrid) {
                        var locationTime = schedule.activityOfferingLocationTime;
                        for (var i = 0; i < locationTime.length; i++) {
                            if (!locationTime[i].isTBA) {
                                showGrid = true;
                                break;
                            }
                        }
                    }
                });
            });

            // if no non-TBA courses in the cart, check the schedule
            if (!showGrid) {
                angular.forEach(GlobalVarsService.getScheduledCourses(), function(course) {
                    angular.forEach(course.activityOfferings, function(ao) {
                        if (!showGrid) {
                            var scheduleComponents = ao.scheduleComponents;
                            for (var i = 0; i < scheduleComponents.length; i++) {
                                if (!scheduleComponents[i].isTBA) {
                                    showGrid = true;
                                    break;
                                }
                            }
                        }
                    });
                });
            }

            return showGrid;
        };

        /*
         Listens for the "courseStatusMessageRemoved" event and removes the card for the given course.
         */
        $scope.$on('courseStatusMessageRemoved',function (event, type) {
            switch (type) {
                case COURSE_TYPES.waitlisted:
                    numberOfDroppedWailistedCourses--;
                    break;
                case COURSE_TYPES.registered:
                    numberOfDroppedCourses--;
            }
        });

        /*
         Listens for the "dropCourse" event and calls the appropriate RESTful service depending on the course type.
         */
        $scope.$on('dropCourse', function(event, type, course, successCallback, errorCallback) {
            switch (type) {
                case COURSE_TYPES.waitlisted:
                    console.log('Drop waitlisted course');
                    executeDrop(type, course, successCallback, errorCallback,
                        ScheduleService.dropFromWaitlist().query, {
                            masterLprId: course.masterLprId
                        });
                    break;
                case COURSE_TYPES.registered:
                    console.log('Drop registered course');
                    executeDrop(type, course, successCallback, errorCallback,
                        ScheduleService.dropRegistrationGroup().query, {
                            masterLprId: course.masterLprId
                        });
            }
        });

        /*
         Listens for the "updateCourse" event and calls the appropriate RESTful service depending on the course type.
         */
        $scope.$on('updateCourse', function(event, type, course, newCourse, successCallback, errorCallback) {
            switch (type) {
                case COURSE_TYPES.waitlisted:
                    console.log('Update waitlisted course');
                    executeUpdate(type, course, newCourse, successCallback, errorCallback,
                        ScheduleService.updateWaitlistItem().query, {
                            courseCode: course.courseCode,
                            regGroupId: course.regGroupId,
                            masterLprId: course.masterLprId,
                            termId: $scope.termId,
                            credits: newCourse.credits,
                            gradingOptionId: newCourse.gradingOptionId
                        });
                    break;
                case COURSE_TYPES.registered:
                    console.log('Update registered course');
                    executeUpdate(type, course, newCourse, successCallback, errorCallback,
                        ScheduleService.updateScheduleItem().query, {
                            courseCode: course.courseCode,
                            regGroupId: course.regGroupId,
                            masterLprId: course.masterLprId,
                            termId: $scope.termId,
                            credits: newCourse.credits,
                            gradingOptionId: newCourse.gradingOptionId
                        });
            }
        });


        /**
         * Execute a drop request against the RESTful service and manage it accordingly.
         * Drops are a registration request that needs to be polled until they return a terminal state.
         *
         * @param type of course
         * @param course being updated
         * @param successCallback to be called when the drop has completely finished
         * @param errorCallback to be called if the drop fails at any point
         * @param request to be executed
         * @param params to be provided to the request
         */
        function executeDrop(type, course, successCallback, errorCallback, request, params) {
            request(params, function(regRequest) {
                console.log('- Drop course registration request submitted - starting to poll for request status');

                ScheduleService.pollRegistrationRequestStatus(regRequest.id)
                    .then(function() { // Success
                        console.log('-- Stop polling - Success');
                        switch (type) {
                            case COURSE_TYPES.waitlisted:
                                // After all the processing is complete, update the new Schedule counts.
                                GlobalVarsService.removeWaitlistedCourse(course);

                                // need a count on how many success drop message for WL are opened. So if there are no WL courses
                                // when we "X" the last success drop message the "Waitlisted" section name would disappear
                                numberOfDroppedWailistedCourses++;
                                break;
                            case COURSE_TYPES.registered:
                                // After all the processing is complete, update the new Schedule counts.
                                GlobalVarsService.removeRegisteredCourse(course);

                                // need a count on how many success drop message are opened. So if there are no courses when we "X" the last
                                // success drop message the section can be replaced by the splash screen (unless there are waitlisted courses)
                                numberOfDroppedCourses++;
                        }

                        // Call the success callback function
                        if (angular.isFunction(successCallback)) {
                            successCallback();
                        }

                        $scope.$emit('courseDropped', course, type);

                    }, function(response) { // Error
                        console.log('-- Stop polling - Error', response);

                        // Call the error callback function with the error message
                        if (angular.isFunction(errorCallback)) {
                            errorCallback(response.responseItemResults[0].messages[0]);
                        }
                    }, function() { // Notify
                        console.log('-- Continue polling');
                    });

            }, function(error) {
                console.log('- Drop course registration request failed', error);

                // Call the error callback function with the error message
                if (angular.isFunction(errorCallback)) {
                    errorCallback(error.data);
                }
            });
        }

        /**
         * Execute an update request against the RESTful service and manage it accordingly.
         * Updates are a registration request that needs to be polled until they return a terminal state.
         *
         * @param type of course
         * @param course is the actual course prior to any updates (original data)
         * @param newCourse is a clone with the updated data applied
         * @param successCallback to be called when the drop has completely finished
         * @param errorCallback to be called if the drop fails at any point
         * @param request to be executed
         * @param params to be provided to the request
         */
        function executeUpdate(type, course, newCourse, successCallback, errorCallback, request, params) {
            request(params, function (regRequest) {
                console.log('- Update course registration request submitted - starting to poll for request status');

                ScheduleService.pollRegistrationRequestStatus(regRequest.id)
                    .then(function() { // Success
                        console.log('-- Stop polling - Success');
                        switch (type) {
                            case COURSE_TYPES.waitlisted:
                                // Update the course counts
                                GlobalVarsService.updateWaitlistedCourse(course, newCourse);
                                break;
                            case COURSE_TYPES.registered:
                                // Update the course counts
                                GlobalVarsService.updateRegisteredCourse(course, newCourse);
                        }

                        // Call the success callback function
                        if (angular.isFunction(successCallback)) {
                            successCallback();
                        }
                    }, function(response) { // Error
                        console.log('-- Stop polling - Error', response);

                        // Call the error callback function with the error message
                        if (angular.isFunction(errorCallback)) {
                            errorCallback(response.responseItemResults[0].messages[0]);
                        }
                    }, function() { // Notify
                        console.log('-- Continue polling');
                    });

            }, function (error) {
                console.log('- Update course registration request failed', error);

                // Call the error callback function with the error message
                if (angular.isFunction(errorCallback)) {
                    errorCallback(error.data);
                }
            });
        }

    }]);