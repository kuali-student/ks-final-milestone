'use strict';

var cartServiceModule = angular.module('regCartApp');

cartServiceModule.controller('ScheduleCtrl', ['$scope', 'ScheduleService',
    function ($scope, ScheduleService) {
        ScheduleService.populateSchedule('admin', $scope.termId);
        $scope.schedules = ScheduleService.getStudentSchedule();
        $scope.registeredCredits = ScheduleService.getRegisteredCredits;
        $scope.registeredCourseCount = ScheduleService.getRegisteredCourseCount;

        $scope.dropRegistrationGroup = function (index, courseCode, credits, masterLprId) {
            ScheduleService.dropRegistrationGroup().query({
                masterLprId:masterLprId
            }, function () {
                $scope.schedules[0].courseOfferings.splice(index, 1);
                ScheduleService.setRegisteredCredits(parseFloat(ScheduleService.getRegisteredCredits()) - parseFloat(credits));
                $scope.userMessage = {txt:courseCode + ' dropped Successfully', type:'success'};
            });
        };
    }]);