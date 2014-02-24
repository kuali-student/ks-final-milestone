'use strict';

angular.module('regCartApp')
    .controller('MainCtrl',
    function ($scope, TermsService, ScheduleService, APP_URL) {
        console.log('In Main Controller');

        $scope.appUrl = APP_URL.replace('/services/', '/');

        $scope.terms = TermsService.getTermsFromServer().query(({active:true}, function (result) {
            // default to the current term
            /* Commenting this out for now. In real world use below
             angular.forEach($scope.terms, function (term) {
             if (term.currentTerm) {
             $scope.termId = term.termId;
             }
             });
             */
        }));

        $scope.termId = 'kuali.atp.2012Spring';
        TermsService.setTermId($scope.termId);

        $scope.schedules = ScheduleService.getScheduleFromServer().query({ userId:'admin', termId:TermsService.getTermId() }, function (result) {
            console.log('called rest service to get schedule data');
            var creditCount = 0;
            var courses = 0;
            angular.forEach(result, function (schedule) {
                angular.forEach(schedule.courseOfferings, function (course) {
                    creditCount += parseFloat(course.credits);
                    courses++;
                });
            });

            ScheduleService.setRegisteredCourseCount(courses);
            ScheduleService.setRegisteredCredits(creditCount);
            ScheduleService.setStudentSchedule(result);

        });
        $scope.registeredCredits = ScheduleService.getRegisteredCredits;
        $scope.registeredCourseCount = ScheduleService.getRegisteredCourseCount;

    });
