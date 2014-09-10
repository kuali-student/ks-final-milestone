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
            console.log('registeredCredits = ' + credits);
            this.setRegisteredCredits(credits);
        };

        this.updateWaitlistedCourse = function(oldCourse, newCourse) {
            var credits = parseFloat(this.getWaitlistedCredits()) - parseFloat(oldCourse.credits) + parseFloat(newCourse.credits);
            console.log('waitlistedCredits = ' + credits);
            this.setWaitlistedCredits(credits);
        };

        this.isCourseRegistered = function(course) {
            return ServiceUtilities.isCourseInList(course, this.getRegisteredCourses());
        };

        this.isCourseWaitlisted = function(course) {
            return ServiceUtilities.isCourseInList(course, this.getWaitlistedCourses());
        };

        /**
         * This method takes the schedule list returned from the schedule service and updates the global counts.
         *
         * @param personSchedule
         */
        this.updateScheduleCounts = function (personSchedule) {
            var userId = personSchedule.userId;
            GlobalVarsService.setUserId(userId);

            // Update registered courses
            if (registeredCourses.length == 0) {
                if (personSchedule.registeredCourseOfferings != null) {
                    this.sortCoursesByCreateTime(personSchedule.registeredCourseOfferings, "latestFirst");
                    for (var i=0; i<personSchedule.registeredCourseOfferings.length; i++){
                        var course = personSchedule.registeredCourseOfferings[i];
                        this.setRegisteredCredits(this.getRegisteredCredits() + parseFloat(course.credits));
                        course.gradingOptionCount = this.countGradingOptions(course);
                        registeredCourses.push(course);
                    }
                }
            } else {
                // Remove messages, if they exist
                while(droppedRegistered.length > 0){
                    this.spliceCourse(COURSE_TYPES.registered, droppedRegistered[0]);
                }
                // Add only the new registered courses
                var difference = personSchedule.registeredCourseOfferings.length - (registeredCourses.length - droppedRegistered.length);
                this.sortCoursesByCreateTime(personSchedule.registeredCourseOfferings, "latestFirst");

                for (var j=0; j<difference; j++) {
                    var course = personSchedule.registeredCourseOfferings[j];
                    this.setRegisteredCredits(this.getRegisteredCredits() + parseFloat(course.credits));
                    course.gradingOptionCount = this.countGradingOptions(course);
                    registeredCourses.unshift(personSchedule.registeredCourseOfferings[j]);
                }
            }
            this.setRegisteredCourseCount(registeredCourses.length);


            // Update waitlisted courses
            if (waitlistedCourses.length == 0) {
                if (personSchedule.waitlistCourseOfferings != null) {
                    this.sortCoursesByCreateTime(personSchedule.waitlistCourseOfferings, "latestFirst");
                    for (var i=0; i<personSchedule.waitlistCourseOfferings.length; i++){
                        var course = personSchedule.waitlistCourseOfferings[i];
                        this.setWaitlistedCredits(this.getWaitlistedCredits() + parseFloat(course.credits));
                        course.gradingOptionCount = this.countGradingOptions(course);
                        waitlistedCourses.push(course);
                    }
                }
            } else {
                // Remove messages, if they exist
                while(droppedWaitlisted.length > 0){
                    this.spliceCourse(COURSE_TYPES.waitlisted, droppedWaitlisted[0]);
                }
                // Add only the new waitlisted courses
                var difference = personSchedule.waitlistCourseOfferings.length - (waitlistedCourses.length - droppedWaitlisted.length);
                if (personSchedule.waitlistCourseOfferings != null) { this.sortCoursesByCreateTime(personSchedule.waitlistCourseOfferings, "latestFirst"); }

                for (var j=0; j<difference; j++) {
                    var course = personSchedule.waitlistCourseOfferings[j];
                    this.setWaitlistedCredits(this.getWaitlistedCredits() + parseFloat(course.credits));
                    course.gradingOptionCount = this.countGradingOptions(course);
                    waitlistedCourses.unshift(personSchedule.waitlistCourseOfferings[j]);
                }
            }
            this.setWaitlistedCourseCount(waitlistedCourses.length);
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

        this.setDroppedRegistered = function (value) {
            droppedRegistered = value;
        }

        this.getDroppedRegistered = function () {
            return droppedRegistered;
        };

        this.addDroppedRegistered = function (course) {
            droppedRegistered.push(course);
        };

        this.setDroppedWaitlisted = function (value) {
            droppedWaitlisted = value;
        }

        this.getDroppedWaitlisted = function () {
            return droppedWaitlisted;
        };

        this.addDroppedWaitlisted = function (course) {
            droppedWaitlisted.push(course);
        };

        this.sortCoursesByCreateTime = function (unsortedList, order) {
            if (unsortedList.length > 1) {
                switch(order) {
                    case "latestFirst":
                        unsortedList.sort(function(course1, course2) {
                            return course1.createTime < course2.createTime;
                        });
                        break;
                    case "latestLast":
                        unsortedList.sort(function(course1, course2) {
                            return course1.createTime > course2.createTime;
                        });
                        break;
                }

            }
        };

        this.countGradingOptions = function (course) {
            // Grading options are an object (map) so there's no easy way to get the object size without this code
            // Used for checking if Edit button is needed
            var gradingOptionCount = 0;
            for (var gradingOption in course.gradingOptions) {
                gradingOptionCount++;
            }

            return gradingOptionCount;
        }
    }]);