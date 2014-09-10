'use strict';

angular.module('regCartApp')
    .service('ScheduleService', ['$q', '$timeout', 'URLS', 'STATUS', 'ServiceUtilities', 'GlobalVarsService', 'COURSE_TYPES', function ScheduleService($q, $timeout, URLS, STATUS, ServiceUtilities, GlobalVarsService, COURSE_TYPES) {

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

        this.getRegisteredCourses = function() {
            return registeredCourses;
        };

        this.getWaitlistedCourses = function() {
            return waitlistedCourses;
        };

        /*
         Helper method to concatenate all the courses on the schedule
         (both registered and waitlisted) into one array
         */
        this.getScheduledCourses = function() {
            return registeredCourses.concat(waitlistedCourses);
        };

        this.removeRegisteredCourse = function(course) {
            //this.getRegisteredCourses().splice(this.getRegisteredCourses().indexOf(course), 1);

            this.setRegisteredCredits(parseFloat(this.getRegisteredCredits()) - parseFloat(course.credits));
            this.setRegisteredCourseCount(parseInt(this.getRegisteredCourseCount()) - 1);
        };

        this.removeWaitlistedCourse = function(course) {
            //this.getWaitlistedCourses().splice(this.getWaitlistedCourses().indexOf(course), 1);

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

        this.setDroppedRegistered = function(value) {
            droppedRegistered = value;
        };

        this.getDroppedRegistered = function() {
            return droppedRegistered;
        };

        this.addDroppedRegistered = function(course) {
            droppedRegistered.push(course);
        };

        this.setDroppedWaitlisted = function(value) {
            droppedWaitlisted = value;
        };

        this.getDroppedWaitlisted = function() {
            return droppedWaitlisted;
        };

        this.addDroppedWaitlisted = function(course) {
            droppedWaitlisted.push(course);
        };

        this.isCourseRegistered = function(course) {
            return this.isCourseInList(course, this.getRegisteredCourses());
        };

        this.isCourseWaitlisted = function(course) {
            return this.isCourseInList(course, this.getWaitlistedCourses());
        };


        /**
         * This method takes the schedule list returned from the schedule service and updates the global counts.
         *
         * @param personSchedule
         */
        this.updateScheduleCounts = function (personSchedule) {
            var userId = personSchedule.userId;
            GlobalVarsService.setUserId(userId);


            if (scheduleMap[personSchedule.term.termId] === personSchedule) {
                // This really shouldn't be here, we should have a better way of handling this.
                console.log('Term not the same, clearing course lists');
                registeredCourses.splice(0, registeredCourses.length);
                waitlistedCourses.splice(0, waitlistedCourses.length);
            }


            var i;

            // Remove messages, if they exist
            for (i = 0; i < droppedRegistered.length; i++) {
                this.spliceCourse(COURSE_TYPES.registered, droppedRegistered[0]);
            }

            for (i = 0; i < droppedWaitlisted.length; i++) {
                this.spliceCourse(COURSE_TYPES.waitlisted, droppedWaitlisted[0]);
            }


            var registered = this.prepCourseList(personSchedule.registeredCourseOfferings),
                waitlisted = this.prepCourseList(personSchedule.waitlistCourseOfferings);


            // Update registered courses
            if (registeredCourses.length === 0) {
                // Add all of the courses
                for (i = 0; i < registered.courses.length; i++){
                    registeredCourses.push(registered.courses[i]);
                }
            } else {
                // Add only the new courses
                for (i = 0; i < (registered.courses.length - (registeredCourses.length - droppedRegistered.length)); i++) {
                    registeredCourses.unshift(registered.courses[i]);
                }
            }

            this.setRegisteredCredits(registered.totalCredits);
            this.setRegisteredCourseCount(registered.courses.length);


            // Update waitlisted courses
            if (waitlistedCourses.length === 0) {
                // Add all of the courses
                for (i = 0; i < waitlisted.courses.length; i++){
                    waitlistedCourses.push(waitlisted.courses[i]);
                }
            } else {
                // Add only the new courses
                for (i = 0; i < (waitlisted.courses.length - (waitlistedCourses.length - droppedWaitlisted.length)); i++) {
                    waitlistedCourses.unshift(waitlisted.courses[i]);
                }
            }

            this.setWaitlistedCredits(waitlisted.totalCredits);
            this.setWaitlistedCourseCount(waitlistedCourses.length);
        };

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

            this.sortCoursesByCreateTime(courses, 'latestFirst');

            return { courses: courses, totalCredits: totalCredits };
        };

        this.sortCoursesByCreateTime = function (unsortedList, order) {
            if (unsortedList.length > 1) {
                switch (order) {
                    case 'latestFirst':
                        unsortedList.sort(function(course1, course2) {
                            return course1.createTime < course2.createTime;
                        });
                        break;
                    case 'latestLast':
                        unsortedList.sort(function(course1, course2) {
                            return course1.createTime > course2.createTime;
                        });
                        break;
                }
            }
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