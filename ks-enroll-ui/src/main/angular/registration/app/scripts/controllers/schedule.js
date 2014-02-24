'use strict';

var cartServiceModule = angular.module('regCartApp');

cartServiceModule.controller('ScheduleCtrl', ['$scope', 'ScheduleService',
    function ($scope, ScheduleService) {
        ScheduleService.populateSchedule('admin', $scope.termId);
        $scope.schedules = ScheduleService.getStudentSchedule();
        $scope.registeredCredits = ScheduleService.getRegisteredCredits();
        $scope.registeredCourseCount = ScheduleService.getRegisteredCourseCount();

    }]);