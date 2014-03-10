'use strict';

var cartServiceModule = angular.module('regCartApp');

cartServiceModule.controller('ScheduleCtrl', ['$scope', '$modal', 'ScheduleService',
    function ($scope, $modal, ScheduleService) {
        ScheduleService.populateSchedule($scope.termId);
        $scope.schedules = ScheduleService.getStudentSchedule();
        $scope.registeredCredits = ScheduleService.getRegisteredCredits;
        $scope.registeredCourseCount = ScheduleService.getRegisteredCourseCount;

/*        $scope.openDropConfirmation = function (index, course) {
            console.log('Open drop confirmation');
            $modal.open({
                backdrop: 'static',
                templateUrl: 'partials/dropCourse.html',
                resolve: {
                    index: function () {
                        return index;
                    },
                    course: function () {
                        return course;
                    }
                },
                controller: ['$rootScope', '$scope', 'index', 'course', function ($rootScope, $scope, index, course) {
                    console.log('in controller');
                    console.log($scope);
                    console.log(index);
                    console.log(course);
                    $scope.index = index;
                    $scope.course = course;
                    $scope.dismiss = function () {
                        console.log('dismiss');
                        $scope.$close(true);
                    };
                    $scope.dropRegistrationGroup = function () {
                        console.log('Drop registration group');
                        dropRegistrationGroup($scope.index, $scope.course.courseCode, $scope.course.regGroupCode, $scope.course.credits, $scope.course.masterLprId);
                        $scope.$close(true);
                    };
                }]
            });
        };

        function dropRegistrationGroup(index, courseCode, regGroupCode, credits, masterLprId) {
            console.log('Calling service to drop registration group');
            ScheduleService.dropRegistrationGroup().query({
                userId: 'admin',
                masterLprId: masterLprId
            }, function () {
                $scope.schedules[0].courseOfferings.splice(index, 1);
                ScheduleService.setRegisteredCredits(parseFloat(ScheduleService.getRegisteredCredits()) - parseFloat(credits));
                $scope.userMessage = {txt: 'Dropped ' + courseCode + ' (' + regGroupCode + ') successfully', type: 'success'};
            });
        }           */

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
                userId: 'admin',
                masterLprId: course.masterLprId
            }, function () {
                course.dropping = false;
                $scope.schedules[0].courseOfferings.splice(index, 1);
                ScheduleService.setRegisteredCredits(parseFloat(ScheduleService.getRegisteredCredits()) - parseFloat(course.credits));
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
                userId: 'admin',
                courseCode: course.courseCode,
                cartItemId: course.regGroupCode,
                masterLprId: course.masterLprId,
                credits: newCredits,
                gradingOptionId: newGrading
            }, function (scheduleItemResult) {
                console.log(scheduleItemResult);
                course.credits = scheduleItemResult.credits;
                course.gradingOptionId = scheduleItemResult.gradingOptionId;
                course.editing = false;
                $scope.userMessage = {txt: 'Updated Successfully', type: 'success'};
            });
        };

    }]);