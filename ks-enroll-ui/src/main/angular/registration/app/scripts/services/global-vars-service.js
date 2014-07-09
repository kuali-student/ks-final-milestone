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
            var courses = 0;
            var waitlistCreditCount = 0;
            var waitlistCourses = 0;

            this.setSchedule(scheduleList);
            angular.forEach(scheduleList, function (schedule) {
                angular.forEach(schedule.registeredCourseOfferings, function (course) {
                    creditCount += parseFloat(course.credits);
                    courses++;
                    var gradingOptionCount = 0;
                    //grading options are an object (map) so there's no easy way to get the object size without this code
                    angular.forEach(course.gradingOptions, function () {
                        gradingOptionCount++;
                    });
                    course.gradingOptionCount = gradingOptionCount;
                });
                angular.forEach(schedule.waitlistCourseOfferings, function (course) {
                    waitlistCreditCount += parseFloat(course.credits);
                    waitlistCourses++;
                    var gradingOptionCount = 0;
                    //grading options are an object (map) so there's no easy way to get the object size without this code
                    angular.forEach(course.gradingOptions, function () {
                        gradingOptionCount++;
                    });
                    course.gradingOptionCount = gradingOptionCount;
                });
            });

            this.setRegisteredCourseCount(courses);
            this.setRegisteredCredits(creditCount);
            this.setWaitlistedCredits(waitlistCreditCount);
            this.setWaitlistedCourseCount(waitlistCourses);
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

    }]);
