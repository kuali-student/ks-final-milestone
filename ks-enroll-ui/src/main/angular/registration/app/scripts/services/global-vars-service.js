'use strict';

angular.module('regCartApp')
    .service('GlobalVarsService', ['STATE', 'STATUS', function GlobalVarsService(STATE, STATUS) {

        var cartCredits = 0;
        var cartCourseCount = 0;
        var registeredCredits;
        var registeredCourseCount = 0;
        var waitlistedCredits = 0;
        var waitlistedCourseCount = 0;
        var schedule;
        var userId;
        var courseIndexes = {};
        var courseIndexPointer = 1;

        var cartCourses = [];
        var registeredCourses = [];
        var waitlistedCourses = [];


        this.getCartCredits = function () {
            return cartCredits;
        };

        this.setCartCredits = function (value) {
            cartCredits = value;
        };

        this.getCartCourseCount = function () {
            return cartCourseCount;
        };

        this.setCartCourseCount = function (value) {
            cartCourseCount = value;
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

        this.getCartCourses = function() {
            return cartCourses;
        };

        this.setCartCourses = function(courses) {
            cartCourses.splice(0, cartCourses.length);

            if (courses) {
                angular.forEach(courses, function(course) {
                    cartCourses.push(course);
                });

                this.setCartCourseCount(courses.length);
            }
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
            this.getRegisteredCourses().splice(this.getRegisteredCourses().indexOf(course), 1);

            this.setRegisteredCredits(parseFloat(this.getRegisteredCredits()) - parseFloat(course.credits));
            this.setRegisteredCourseCount(parseInt(this.getRegisteredCourseCount()) - 1);
        };

        this.removeWaitlistedCourse = function(course) {
            this.getWaitlistedCourses().splice(this.getWaitlistedCourses().indexOf(course), 1);

            this.setWaitlistedCredits(parseFloat(this.getWaitlistedCredits()) - parseFloat(course.credits));
            this.setWaitlistedCourseCount(this.getWaitlistedCourses().length);
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

        this.isCourseInCart = function(course) {
            return isCourseInList(course, this.getCartCourses());
        };

        this.isCourseRegistered = function(course) {
            return isCourseInList(course, this.getRegisteredCourses());
        };

        this.isCourseWaitlisted = function(course) {
            return isCourseInList(course, this.getWaitlistedCourses());
        };


        this.getSchedule = function () {
            return schedule;
        };

        this.setSchedule = function (value) {
            schedule = value;
        };

        this.getUserId = function () {
            return userId;
        };

        this.setUserId = function (value) {
            userId = value;
        };

        // In this method we pass in a state and it returns a status
        this.getCorrespondingStatusFromState = function (state) {
            var retStatus = STATUS.new;
            if (STATE.processing.indexOf(state) >= 0) {
                retStatus = STATUS.processing;
            } else if (STATE.success.indexOf(state) >= 0) {
                retStatus = STATUS.success;
            } else if (STATE.error.indexOf(state) >= 0) {
                retStatus = STATUS.error;
            } else if (STATE.waitlist.indexOf(state) >= 0) {
                retStatus = STATUS.waitlist;
            } else if (STATE.action.indexOf(state) >= 0) {
                retStatus = STATUS.action;
            }

            return retStatus;
        };

        /**
         * This method takes the schedule list returned from the schedule service and updates the global counts.
         *
         * @param personSchedule
         */
        this.updateScheduleCounts = function (personSchedule) {

            var scheduleList = personSchedule.studentScheduleTermResults;
            var userId = personSchedule.userId;

            //Calculate credit count, course count and grading option count
            var creditCount = 0;
            var waitlistCreditCount = 0;

            registeredCourses.splice(0, registeredCourses.length);
            waitlistedCourses.splice(0, waitlistedCourses.length);

            this.setSchedule(scheduleList);
            angular.forEach(scheduleList, function (schedule) {
                angular.forEach(schedule.registeredCourseOfferings, function (course) {
                    creditCount += parseFloat(course.credits);
                    registeredCourses.push(course);

                    var gradingOptionCount = 0;
                    //grading options are an object (map) so there's no easy way to get the object size without this code
                    angular.forEach(course.gradingOptions, function () {
                        gradingOptionCount++;
                    });
                    course.gradingOptionCount = gradingOptionCount;
                });
                angular.forEach(schedule.waitlistCourseOfferings, function (course) {
                    waitlistCreditCount += parseFloat(course.credits);
                    waitlistedCourses.push(course);

                    var gradingOptionCount = 0;
                    //grading options are an object (map) so there's no easy way to get the object size without this code
                    angular.forEach(course.gradingOptions, function () {
                        gradingOptionCount++;
                    });
                    course.gradingOptionCount = gradingOptionCount;
                });
            });

            this.setRegisteredCourseCount(registeredCourses.length);
            this.setRegisteredCredits(creditCount);
            this.setWaitlistedCredits(waitlistCreditCount);
            this.setWaitlistedCourseCount(waitlistedCourses.length);
            this.setUserId(userId);
        };

        // In this method we pass in a status and it returns a message to display
        this.getCorrespondingMessageFromStatus = function (status) {
            var statusMessage = '';
            if (status === STATUS.waitlist) {
                statusMessage = 'If a seat becomes available you will be registered automatically';
            }

            return statusMessage;
        };

        /*
        Return the course index based on the course details position in the array.

        If no course index is found, add the course details to the array and
        return the new index.
         */
        this.getCourseIndex = function (courseDetails) {
            var courseDetailsString = courseDetails.courseCode + (courseDetails.regGroup || courseDetails.regGroupCode); // regGroupCode in schedule
            var index = courseIndexes[courseDetailsString];

            if (isNaN(index)) {
                index = courseIndexPointer++;
                courseIndexes[courseDetailsString] = index;
            }

            return index;
        };


        /**
         * Method for determining if a course is present in a list of courses
         * @param course
         * @param list
         * @returns {boolean}
         */
        function isCourseInList (course, list) {
            if (angular.isString(course)) {
                course = {
                    regGroupId: course
                };
            }

            var inList = false;
            angular.forEach(list, function(listCourse) {
                if (!inList && (listCourse.regGroupId === course.regGroupId)) { // Courses match on regGroupId
                    inList = true;
                }
            });

            return inList;
        }

    }]);
