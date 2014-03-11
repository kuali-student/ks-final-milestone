'use strict';

angular.module('regCartApp')
    .service('ScheduleService', ['$resource', 'APP_URL', function ScheduleService($resource, APP_URL) {

        var registeredCredits = 0;
        var registeredCourseCount = 0;
        var studentSchedule;

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

        this.getStudentSchedule = function () {
            return studentSchedule;
        };

        this.setStudentSchedule = function (value) {
            studentSchedule = value;
        };

        this.getScheduleFromServer = function () {
            return $resource(APP_URL + 'CourseRegistrationClientService/personschedule', {}, {
                query: {method: 'GET', cache: false, isArray: true}
            });
        };
        this.updateSchedule = function () {
            return $resource(APP_URL + 'CourseRegistrationClientService/updateScheduleItem', {}, {
                query: {method: 'GET', cache: false, isArray: true}
            });
        };

        this.updateScheduleItem = function () {
            return $resource(APP_URL + 'CourseRegistrationClientService/updateScheduleItem', {}, {
                query: {method: 'GET', cache: false, isArray: false}
            });
        };

        this.dropRegistrationGroup = function () {
            return $resource(APP_URL + 'CourseRegistrationClientService/dropRegistrationGroup', {}, {
                query: {method: 'GET', cache: false, isArray: false}
            });
        };

        this.populateSchedule = function (termIdInput) {
            this.setStudentSchedule(this.getScheduleFromServer().query({termId: termIdInput }, function (result) {
                console.log('called rest service to get schedule data');
                //Calculate credit count, course count and grading option count
                var creditCount = 0;
                var courses = 0;
                angular.forEach(result, function (schedule) {
                    angular.forEach(schedule.courseOfferings, function (course) {
                        creditCount += parseFloat(course.credits);
                        courses++;
                        var gradingOptionCount = 0;
                        //grading options are an object (map) so there's no easy way to get the object size without this code
                        angular.forEach(course.gradingOptions, function(){
                            gradingOptionCount++;
                        })
                        course.gradingOptionCount = gradingOptionCount;
                    });
                });

                registeredCredits = creditCount;
                registeredCourseCount = courses;
            }));
        };


    }]);