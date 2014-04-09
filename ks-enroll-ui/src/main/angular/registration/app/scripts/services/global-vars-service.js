'use strict';

angular.module('regCartApp')
    .service('GlobalVarsService', function GlobalVarsService() {

        var registeredCredits = 0;
        var registeredCourseCount = 0;
        var schedule;

        var processingStates = ['kuali.lpr.trans.item.state.processing','kuali.lpr.trans.state.processing'];
        var successStates = ['kuali.lpr.trans.state.succeeded', 'kuali.lpr.trans.item.state.succeeded'];
        var errorStates = ['kuali.lpr.trans.state.failed', 'kuali.lpr.trans.item.state.failed'];
        var waitlistStates = ['kuali.lpr.trans.item.state.waitlist'];
        //var actionStates = ["", ""];

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

        this.getSchedule = function () {
            return schedule;
        };

        this.setSchedule = function (value) {
            schedule = value;
        };

        // In this method we pass in a state and it returns a status
        this.getCorrespondingStatusFromState = function(state){
            var retStatus = 'new';
            if(processingStates.indexOf(state) >= 0){
                retStatus = 'processing';
            } else if(successStates.indexOf(state) >= 0){
                retStatus = 'success';
            } else if(errorStates.indexOf(state) >= 0){
                retStatus = 'error';
            } else if(waitlistStates.indexOf(state) >= 0){
                retStatus = 'waitlist';
            }

            return retStatus;
        };

        /**
         * This method takes the schedule list returned from the schedule service and updates the global counts.
         *
         * @param scheduleList
         */
        this.updateScheduleCounts = function (scheduleList) {

            //Calculate credit count, course count and grading option count
            var creditCount = 0;
            var courses = 0;

            this.setSchedule(scheduleList);
            angular.forEach(scheduleList, function (schedule) {
                angular.forEach(schedule.registeredCourseOfferings, function (course) {
                    creditCount += parseFloat(course.credits);
                    courses++;
                    var gradingOptionCount = 0;
                    //grading options are an object (map) so there's no easy way to get the object size without this code
                    angular.forEach(course.gradingOptions, function(){
                        gradingOptionCount++;
                    });
                    course.gradingOptionCount = gradingOptionCount;
                });
            });

            this.setRegisteredCourseCount(courses);
            this.setRegisteredCredits(creditCount);

        };

    });
