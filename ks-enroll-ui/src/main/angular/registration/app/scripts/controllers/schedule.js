'use strict';

var cartServiceModule = angular.module('regCartApp');

cartServiceModule.controller('ScheduleCtrl', ['$scope', '$modal', 'ScheduleService', 'GlobalVarsService',
    function ($scope, $modal, ScheduleService, GlobalVarsService) {
        ScheduleService.getScheduleFromServer().query({termId: $scope.termId }, function (result) {
            console.log('called rest service to get schedule data - in schedule.js');
            $scope.schedules = result;
            GlobalVarsService.updateScheduleCounts($scope.schedules);
            $scope.registeredCredits = GlobalVarsService.getRegisteredCredits;
            $scope.registeredCourseCount = GlobalVarsService.getRegisteredCourseCount;

        });

        $scope.openDropConfirmation = function (index, course) {
            console.log('Open drop confirmation');
            course.dropping = true;
            $scope.index = index;
            $scope.course = course;
        };

        $scope.cancelDropConfirmation = function (course) {
            course.dropping = false;
        };

        $scope.dropRegistrationGroup = function (index, course) {
            console.log('Open drop confirmation');
            ScheduleService.dropRegistrationGroup().query({
                masterLprId: course.masterLprId
            }, function () {
                course.dropping = false;
                $scope.schedules[0].courseOfferings.splice(index, 1);
                GlobalVarsService.updateScheduleCounts($scope.schedules);
                //ScheduleService.setRegisteredCredits(parseFloat(ScheduleService.getRegisteredCredits()) - parseFloat(course.credits));
                $scope.userMessage = {txt:course.courseCode + ' dropped Successfully', type:'success'};
            });
        };

        $scope.editScheduleItem = function (course) {
            $scope.newCredits = course.credits;
            $scope.newGrading = course.gradingOptionId;
            course.editing = true;
        };

        $scope.cancelEditScheduleItem = function (course) {
            course.editing = false;
        };

        $scope.updateScheduleItem = function (course, newCredits, newGrading) {
            console.log('Updating:');
            console.log(newCredits);
            console.log(newGrading);
            ScheduleService.updateScheduleItem().query({
                courseCode: course.courseCode,
                cartItemId: course.regGroupCode,
                masterLprId: course.masterLprId,
                credits: newCredits,
                gradingOptionId: newGrading
            }, function (scheduleItemResult) {
                console.log(scheduleItemResult);
                course.credits = scheduleItemResult.credits;
                course.gradingOptionId = scheduleItemResult.gradingOptionId;
                GlobalVarsService.updateScheduleCounts($scope.schedules);
                course.editing = false;
                $scope.userMessage = {txt: 'Updated Successfully', type: 'success'};
            });
        };

        $scope.showBadge = function (course) {
            return course.gradingOptions[course.gradingOptionId] != 'Letter';
        };

    }]);