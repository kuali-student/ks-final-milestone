'use strict';

var cartServiceModule = angular.module('regCartApp');

cartServiceModule.controller('ScheduleCtrl', ['$scope', '$modal', 'ScheduleService', 'GlobalVarsService',
    function ($scope, $modal, ScheduleService, GlobalVarsService) {

        $scope.getSchedules = GlobalVarsService.getSchedule;
        $scope.registeredCredits = GlobalVarsService.getRegisteredCredits;
        $scope.registeredCourseCount = GlobalVarsService.getRegisteredCourseCount;
        $scope.waitlistedCredits = GlobalVarsService.getWaitlistedCredits;
        $scope.waitlistedCourseCount = GlobalVarsService.getWaitlistedCourseCount;
        $scope.numberOfDroppedWailistedCourses = 0;

        $scope.$watch('termId', function (newValue) {
            console.log('term id has changed');
            if ($scope.userMessage && $scope.userMessage.txt) {
                $scope.removeUserMessage();
            }
            if ($scope.waitlistUserMessage && $scope.waitlistUserMessage.txt) {
                $scope.removeWaitlistUserMessage();
            }
            ScheduleService.getScheduleFromServer().query({termId: newValue }, function (result) {
                console.log('called rest service to get schedule data - in schedule.js');
                GlobalVarsService.updateScheduleCounts(result);
            });
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
            console.log('Open drop confirmation for registered course');
            ScheduleService.dropRegistrationGroup().query({
                masterLprId: course.masterLprId
            }, function () {
                // "dropping" is used to display confirmation popup, "dropped" to display course details vs success to drop message
                course.dropping = false;
                course.dropped = true;
                // can't use splice (which would remove the success message, so updating counts manually
                GlobalVarsService.setRegisteredCredits(parseFloat(GlobalVarsService.getRegisteredCredits()) - parseFloat(course.credits));
                GlobalVarsService.setRegisteredCourseCount(parseInt(GlobalVarsService.getRegisteredCourseCount()) - 1);
                course.statusMessage = {txt: '<strong>' + course.courseCode + ' (' + course.regGroupCode + ')</strong> dropped successfully', type: 'success'};
            }, function (error) {
                $scope.userMessage = {txt: error.data, type: 'error'};
            });
        };

        $scope.dropFromWaitlist = function (index, course) {
            console.log('Open drop confirmation for waitlist course');
            ScheduleService.dropFromWaitlist().query({
                masterLprId: course.masterLprId
            }, function () {
                // need a count on how many success drop message for WL are opened. So if there are no WL courses when we "X" the last success drop message the "Waitlisted" section name would disappear
                $scope.numberOfDroppedWailistedCourses = $scope.numberOfDroppedWailistedCourses + 1;
                $scope.showWaitlistMessages = true;
                // "dropping" is used to display confirmation popup, "dropped" to display course details vs success to drop message
                course.dropping = false;
                course.dropped = true;
                // can't use splice (which would remove the success message, so updating counts manually
                GlobalVarsService.setWaitlistedCredits(parseFloat(GlobalVarsService.getWaitlistedCredits()) - parseFloat(course.credits));
                GlobalVarsService.setWaitlistedCourseCount(parseInt(GlobalVarsService.getWaitlistedCourseCount()) - 1);
                course.statusMessage = {txt: 'Removed from waitlist for <strong>' + course.courseCode + ' (' + course.regGroupCode + ')</strong> successfully', type: 'success'};
            }, function (error) {
                course.statusMessage = {txt: error.data, type: 'error'};
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
            console.log('Updating registered course:');
            console.log(newCredits);
            console.log(newGrading);
            ScheduleService.updateScheduleItem().query({
                courseCode: course.courseCode,
                regGroupCode: course.regGroupCode,
                masterLprId: course.masterLprId,
                credits: newCredits,
                gradingOptionId: newGrading
            }, function (scheduleItemResult) {
                console.log(scheduleItemResult);
                GlobalVarsService.setRegisteredCredits(parseFloat(GlobalVarsService.getRegisteredCredits()) - parseFloat(course.credits) + parseFloat(scheduleItemResult.credits));
                course.credits = scheduleItemResult.credits;
                course.gradingOptionId = scheduleItemResult.gradingOptionId;
                course.editing = false;
                course.statusMessage = {txt: 'Changes saved successfully', type: 'success'};
            }, function (error) {
                course.statusMessage = {txt: error.data, type: 'error'};
            });
        };

        $scope.updateWaitlistItem = function (course, newCredits, newGrading) {
            console.log('Updating waitlisted course:');
            console.log(newCredits);
            console.log(newGrading);
            ScheduleService.updateWaitlistItem().query({
                courseCode: course.courseCode,
                regGroupCode: course.regGroupCode,
                masterLprId: course.masterLprId,
                credits: newCredits,
                gradingOptionId: newGrading
            }, function (scheduleItemResult) {
                console.log(scheduleItemResult);
                GlobalVarsService.setRegisteredCredits(parseFloat(GlobalVarsService.getRegisteredCredits()) - parseFloat(course.credits) + parseFloat(scheduleItemResult.credits));
                course.credits = scheduleItemResult.credits;
                course.gradingOptionId = scheduleItemResult.gradingOptionId;
                course.editing = false;
                course.statusMessage = {txt: 'Changes saved successfully', type: 'success'};
            }, function (error) {
                course.statusMessage = {txt: error.data, type: 'error'};
            });
        };

        $scope.removeStatusMessage = function (course) {
            course.statusMessage = null;
        };

        $scope.removeUserMessage = function () {
            $scope.userMessage.txt = null;
            $scope.userMessage.linkText = null;
        };

        $scope.removeWaitlistStatusMessage = function (course) {
            course.statusMessage = null;
            $scope.numberOfDroppedWailistedCourses = $scope.numberOfDroppedWailistedCourses - 1;
            if ($scope.numberOfDroppedWailistedCourses === 0) {
                $scope.showWaitlistMessages = false;
            }
        };

        $scope.showBadge = function (course) {
            return course.gradingOptions[course.gradingOptionId] !== 'Letter';
        };

    }]);