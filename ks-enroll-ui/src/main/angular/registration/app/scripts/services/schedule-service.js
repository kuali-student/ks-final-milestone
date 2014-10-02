'use strict';

angular.module('regCartApp')
    .service('ScheduleService', ['$q', '$timeout', 'URLS', 'STATUS', 'ServiceUtilities', 'GlobalVarsService', 'RegUtil',
        'COURSE_TYPES', 'DAY_CONSTANTS',
        function ScheduleService($q, $timeout, URLS, STATUS, ServiceUtilities, GlobalVarsService, RegUtil, COURSE_TYPES,
            DAY_CONSTANTS) {

            var selectedSchedule; // Currently selected schedule

            var registeredCredits = 0;
            var registeredCourseCount = 0;
            var registeredCourses = [];
            var droppedRegistered = [];

            var waitlistedCredits = 0;
            var waitlistedCourseCount = 0;
            var waitlistedCourses = [];
            var droppedWaitlisted = [];

            // Cache of schedules per term
            var scheduleMap = {};


            /**
             * Get the user's schedule for a given term. Pulls from cache if it exists, loads it and caches it if it does not.
             *
             * @param termId to load schedule for
             * @param forceLoad
             * @returns {promise}
             */
            this.getSchedule = function(termId, forceLoad) {
                var deferred = $q.defer();

                if (!forceLoad) {
                    // Make sure forceLoad is set
                    forceLoad = false;
                }

                if (angular.isDefined(scheduleMap[termId]) && !forceLoad) {
                    // Return the cached cart
                    deferred.resolve(scheduleMap[termId]);
                } else {
                    this.getScheduleFromServer().query({termId: termId}, function(schedule) {
                        // Cache the schedule
                        scheduleMap[termId] = schedule;
                        deferred.resolve(schedule);
                    }, function(error) {
                        // Report out the error
                        deferred.reject(error);
                    });
                }

                return deferred.promise;
            };

            /**
             * Set the selected schedule & update the tallies (# courses, total credits)
             *
             * @param schedule
             */
            this.setSelectedSchedule = function(schedule) {
                // Set the userId to the one coming from the schedule object
                GlobalVarsService.setUserId(schedule.userId);

                if (selectedSchedule !== schedule) {
                    // Clear out the handles we have on each of the course lists
                    registeredCourses.splice(0, registeredCourses.length);
                    waitlistedCourses.splice(0, waitlistedCourses.length);

                    droppedRegistered.splice(0, droppedRegistered.length);
                    droppedWaitlisted.splice(0, droppedWaitlisted.length);

                    selectedSchedule = schedule;
                }

                this.updateScheduleCounts(schedule);
            };


            this.getRegisteredCourses = function() {
                return registeredCourses;
            };

            this.getWaitlistedCourses = function() {
                return waitlistedCourses;
            };

            this.getRegisteredCredits = function () {
                return registeredCredits;
            };

            this.setRegisteredCredits = function (value) {
                registeredCredits = value;
            };

            this.getRegisteredCourseCount = function () {
                return registeredCourseCount;
            };

            this.setRegisteredCourseCount = function (value) {
                registeredCourseCount = value;
            };

            this.getWaitlistedCredits = function () {
                return waitlistedCredits;
            };

            this.setWaitlistedCredits = function (value) {
                waitlistedCredits = value;
            };

            this.getWaitlistedCourseCount = function () {
                return waitlistedCourseCount;
            };

            this.setWaitlistedCourseCount = function (value) {
                waitlistedCourseCount = value;
            };

            this.getDroppedRegistered = function() {
                return droppedRegistered;
            };

            this.addDroppedRegistered = function(course) {
                droppedRegistered.push(course);
            };

            this.getDroppedWaitlisted = function() {
                return droppedWaitlisted;
            };

            this.addDroppedWaitlisted = function(course) {
                droppedWaitlisted.push(course);
            };


            /**
             * Helper method to concatenate all the courses on the schedule
             * (both registered and waitlisted) into one array
             *
             * @return {array} of registered + waitlisted courses
             */
            this.getScheduledCourses = function() {
                return registeredCourses.concat(waitlistedCourses);
            };

            this.removeRegisteredCourse = function(course) {
                this.setRegisteredCredits(parseFloat(this.getRegisteredCredits()) - parseFloat(course.credits));
                this.setRegisteredCourseCount(parseInt(this.getRegisteredCourseCount()) - 1);
            };

            this.removeWaitlistedCourse = function(course) {
                this.setWaitlistedCredits(parseFloat(this.getWaitlistedCredits()) - parseFloat(course.credits));
                this.setWaitlistedCourseCount(this.getWaitlistedCourses().length);
            };

            this.spliceCourse = function(type, course) {
                switch (type) {
                    case COURSE_TYPES.waitlisted:
                        this.getWaitlistedCourses().splice(this.getWaitlistedCourses().indexOf(course), 1);
                        this.getDroppedWaitlisted().splice(this.getDroppedWaitlisted().indexOf(course), 1);
                        break;
                    case COURSE_TYPES.registered:
                        this.getRegisteredCourses().splice(this.getRegisteredCourses().indexOf(course), 1);
                        this.getDroppedRegistered().splice(this.getDroppedRegistered().indexOf(course), 1);
                }
            };

            this.updateRegisteredCourse = function(oldCourse, newCourse) {
                var credits = parseFloat(this.getRegisteredCredits()) - parseFloat(oldCourse.credits) + parseFloat(newCourse.credits);
                this.setRegisteredCredits(credits);
            };

            this.updateWaitlistedCourse = function(oldCourse, newCourse) {
                var credits = parseFloat(this.getWaitlistedCredits()) - parseFloat(oldCourse.credits) + parseFloat(newCourse.credits);
                this.setWaitlistedCredits(credits);
            };


            this.isCourseRegistered = function(course) {
                return RegUtil.isCourseInList(course, this.getRegisteredCourses());
            };

            this.isCourseWaitlisted = function(course) {
                return RegUtil.isCourseInList(course, this.getWaitlistedCourses());
            };

            this.isAoInSchedule = function(aoId) {
                var inScheduleIndicator = {flag: false, colorIndex: null};
                for (var i = 0; i < this.getRegisteredCourses().length; i++) {
                    var course = this.getRegisteredCourses()[i];
                    if (this.getDroppedRegistered().length === 0 || this.getDroppedRegistered().indexOf(course) === -1) {
                        for (var j = 0; j < course.activityOfferings.length; j++) {
                            var activityOffering = course.activityOfferings[j];
                            if (aoId === activityOffering.activityOfferingId) {
                                inScheduleIndicator.flag = true;
                                inScheduleIndicator.colorIndex = GlobalVarsService.getCourseIndex(course);
                                return inScheduleIndicator;
                            }
                        }
                    } else {
                        inScheduleIndicator.flag = false;
                        inScheduleIndicator.colorIndex = GlobalVarsService.getCourseIndex(course);
                        return inScheduleIndicator;
                    }
                }

            };

            // This method takes the schedule list returned from the schedule service and updates the global counts.
            this.updateScheduleCounts = function(personSchedule) {
                var i;

                // Remove messages, if they exist
                for (i = 0; i < droppedRegistered.length; i++) {
                    this.spliceCourse(COURSE_TYPES.registered, droppedRegistered[i]);
                }

                for (i = 0; i < droppedWaitlisted.length; i++) {
                    this.spliceCourse(COURSE_TYPES.waitlisted, droppedWaitlisted[i]);
                }


                var registered = this.prepCourseList(personSchedule.registeredCourseOfferings),
                    waitlisted = this.prepCourseList(personSchedule.waitlistCourseOfferings);


                // Update registered courses
                for (i = 0; i < registered.courses.length; i++) {
                    if (!this.isCourseRegistered(registered.courses[i])) {
                        registeredCourses.push(registered.courses[i]);
                    }
                }

                RegUtil.sortCoursesByCreateTime(registeredCourses, 'latestFirst');
                this.setRegisteredCredits(registered.totalCredits);
                this.setRegisteredCourseCount(registered.courses.length);


                // Update waitlisted courses
                for (i = 0; i < waitlisted.courses.length; i++){
                    if (!this.isCourseWaitlisted(waitlisted.courses[i])) {
                        waitlistedCourses.push(waitlisted.courses[i]);
                    }
                }

                RegUtil.sortCoursesByCreateTime(waitlistedCourses, 'latestFirst');
                this.setWaitlistedCredits(waitlisted.totalCredits);
                this.setWaitlistedCourseCount(waitlistedCourses.length);
            };

            // Helper method for prepping a list of courses into a better format and tally the total # of credits in the list
            this.prepCourseList = function(courses) {
                if (!angular.isArray(courses)) {
                    courses = [];
                }

                var totalCredits = 0;
                for (var i = 0; i < courses.length; i++) {
                    var course = courses[i];

                    // Used for checking if Edit button is needed
                    course.gradingOptionCount = Object.keys(course.gradingOptions).length;
                    totalCredits += parseFloat(course.credits);
                }

                return { courses: courses, totalCredits: totalCredits };
            };


            // Schedule Poller
            this.pollRegistrationRequestStatus = function(registrationRequestId, interval, deferred) {
                // Make sure the interval is defined
                if (!angular.isNumber(interval)) {
                    interval = 1000;
                }

                // Make sure the promise is set up.
                if (!deferred) {
                    deferred = $q.defer();
                }

                var me = this; // Get a handle on the service so we can refer to it within the $timeout.
                $timeout(function() {
                    // Query for the registration status
                    me.getRegistrationStatus().query({regReqId: registrationRequestId}, function (result) {
                        var status = GlobalVarsService.getCorrespondingStatusFromState(result.state);
                        switch (status) {

                            case STATUS.new:
                            case STATUS.processing:
                                // The request is still new or processing, first make sure at least 1 of the items are still processing.
                                var processing = false;
                                angular.forEach(result.responseItemResults, function(item) {
                                    if (processing) {
                                        return;
                                    }

                                    var itemStatus = GlobalVarsService.getCorrespondingStatusFromState(item.state);
                                    switch (itemStatus) {
                                        case STATUS.new:
                                        case STATUS.processing:
                                            processing = true; // At least 1 item is still processing.
                                            break;
                                    }
                                });

                                deferred.notify(result); // Notify out as well to give any itemized processors full coverage of all items.

                                if (processing) {
                                    // The request is still new or processing, reschedule the poller
                                    me.pollRegistrationRequestStatus(registrationRequestId, interval, deferred);
                                } else {
                                    // A state mismatch exists on the request, all items have finished
                                    // successfully even though the request is still marked as new or processing.
                                    deferred.resolve(result);
                                }
                                break;

                            case STATUS.success:
                                // The request has finished successfully
                                deferred.notify(result); // Notify out as well to give any itemized processors full coverage of all items.
                                deferred.resolve(result);
                                break;

                            case STATUS.error:
                                // The request has finished with an error state
                                deferred.reject(result);
                                break;

                        }
                    }, function(error) {
                        // Return out the error
                        deferred.reject(error);
                    });
                }, interval);

                return deferred.promise;
            };

            // check the schedule for any time conflicts with the provided ao
            this.hasTimeConflict = function (ao) {
                if (angular.isArray(ao.scheduleComponents)) {
                    for (var i=0; i<ao.scheduleComponents.length; i++) {
                        var timeSlot = angular.copy(ao.scheduleComponents[i]);
                        timeSlot.activityOfferingId = ao.activityOfferingId;
                        if (hasConflict(timeSlot, registeredCourses) ||
                            hasConflict(timeSlot, waitlistedCourses)) {
                            return true;
                        }
                    }
                }
                return false;
            };

            function hasConflict(timeSlot, courses) {
                // compare each scheduled course with the provided ao
                if (angular.isArray(courses)) {
                    for (var i=0; i<courses.length; i++) {
                        var conflict = checkCourseForConflict(timeSlot, courses[i]);
                        if (conflict) return true;
                    }
                }
                return false; // went through ever course, no conflict was found
            }

            function checkCourseForConflict(timeSlot, course) {
                if (!course.dropped) {
                    // the course is not dropped, check each activity for the course
                    for (var k=0; k<course.activityOfferings.length; k++) {
                        var activityOffering = course.activityOfferings[k];
                        var conflict = checkActivityOfferingForConflict(timeSlot, course.activityOfferings[k]);
                        if (conflict) return true;
                    }
                }
                return false;
            }

            function checkActivityOfferingForConflict(timeSlot, activityOffering) {
                // if the ao is the same, don't show it as a conflict
                if (activityOffering.activityOfferingId !== timeSlot.activityOfferingId) {
                    for (var l=0; l<activityOffering.scheduleComponents.length; l++) {
                        // check the date/time info for this activity
                        var conflict = checkScheduleComponentForConflict(timeSlot,
                            angular.copy(activityOffering.scheduleComponents[l]));
                        if (conflict) return true;
                    }
                }
                return false;
            }

            function checkScheduleComponentForConflict(timeSlot, courseTime) {
                for (var j=0; j<DAY_CONSTANTS.dayArray.length; j++) {
                    var day = DAY_CONSTANTS.dayArray[j];
                    if (timeSlot.days.indexOf(day) > -1 && courseTime.days.indexOf(day) > -1) {
                        var course1 = {};
                        var course2 = {};
                        // convert the time strings to numbers
                        course1.startTime = RegUtil.convertTimeStringToTime(timeSlot.startTime);
                        course1.endTime = RegUtil.convertTimeStringToTime(timeSlot.endTime);
                        course2.startTime = RegUtil.convertTimeStringToTime(courseTime.startTime);
                        course2.endTime = RegUtil.convertTimeStringToTime(courseTime.endTime);
                        // adjust for courses that end past midnight (rare)
                        if (course1.endTime < course1.startTime) {
                            course1.endTime += 1440; // 24 hours
                        }
                        if (course2.endTime < course2.startTime) {
                            course2.endTime += 1440; // 24 hours
                        }
                        var conflict = RegUtil.coursesConflict(course1, course2);
                        if (conflict) {
                            return true; // continue looking if conflict not found
                        }
                    }
                }
                return false;
            }

            // Server API Methods

            this.getScheduleFromServer = function () {
                return ServiceUtilities.getData(URLS.courseRegistration + '/studentSchedule');
            };

            this.dropRegistrationGroup = function () {
                return ServiceUtilities.deleteData(URLS.courseRegistration + '/registrationRequest');
            };

            this.registerForRegistrationGroup = function () {
                return ServiceUtilities.postData(URLS.courseRegistration + '/registrationRequest');
            };

            this.updateScheduleItem = function () {
                return ServiceUtilities.putData(URLS.courseRegistration + '/registrationRequest');
            };

            this.dropFromWaitlist = function () {
                return ServiceUtilities.deleteData(URLS.courseRegistration + '/waitlistRegistrationRequest');
            };

            this.updateWaitlistItem = function () {
                return ServiceUtilities.putData(URLS.courseRegistration + '/waitlistRegistrationRequest');
            };

            this.getRegistrationStatus = function () {
                return ServiceUtilities.getData(URLS.courseRegistration + '/registrationStatus');
            };

        }]);