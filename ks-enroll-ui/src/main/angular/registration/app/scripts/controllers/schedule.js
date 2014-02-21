'use strict';

var cartServiceModule = angular.module('regCartApp');

cartServiceModule.controller('ScheduleCtrl', ['$scope', 'ScheduleService',
    function ($scope, ScheduleService) {
        $scope.schedules = ScheduleService.getCart().query({ userId:'admin', termId:$scope.termId }, function (result) {
            console.log('got schedule data back');
            console.log(result);
            var creditCount = 0;
            angular.forEach(result, function (schedule) {
                angular.forEach(schedule.courseOfferings, function (course) {
                    console.log(course.credits);
                    creditCount += parseFloat(course.credits);
                });
            });

            $scope.registeredCredits = creditCount;
            console.log('Registered Credits');
            console.log($scope.registeredCredits);
        });

           /*
        $scope.$watch('schedules', function (newSchedules) {
            console.log('schedule has changed');
            $scope.registeredCredits = 0;
            if (newSchedules) {       // TODO: KSENROLL-11755: the first time the page is loaded, this is null. not sure why
                angular.forEach(newSchedules, function (schedule) {
                    angular.forEach(schedule.courseOfferings, function (course) {
                        $scope.registeredCredits += course.credits;
                    });
                });
            }
        });
        */

    }]);