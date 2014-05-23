'use strict';

var cartServiceModule = angular.module('regCartApp');

cartServiceModule.controller('ScheduleCtrl', ['$scope', '$modal', 'ScheduleService', 'GlobalVarsService', '$timeout',
    function ($scope, $modal, ScheduleService, GlobalVarsService, $timeout) {

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
            }, function (dropResponseResult) {
                course.dropping = false; // used to display confirmation popup
                course.dropProcessing = true; // used to display spinner while poll is running

                schedulePoller(dropResponseResult.registrationRequestId, course);
            }, function (error) {
                $scope.userMessage = {txt: error.data, type: 'error'};
            });
        };

        $scope.dropFromWaitlist = function (index, course) {
            console.log('Open drop confirmation for waitlist course');
            ScheduleService.dropFromWaitlist().query({
                masterLprId: course.masterLprId
            }, function (dropResponseResult) {
                // need a count on how many success drop message for WL are opened. So if there are no WL courses when we "X" the last success drop message the "Waitlisted" section name would disappear
                $scope.numberOfDroppedWailistedCourses = $scope.numberOfDroppedWailistedCourses + 1;
                $scope.showWaitlistMessages = true;

                course.dropping = false; // used to display confirmation popup
                course.dropProcessing = true; // used to display spinner

                schedulePoller(dropResponseResult.registrationRequestId, course);
            }, function (error) {
                course.statusMessage = {txt: error.data, type: 'error'};
            });
        };

        // This method is used to update the status of a course by polling the server
        var schedulePoller = function (registrationRequestId, course) {
            console.log('start polling for course to be dropped from schedule');
            course.statusMessage = {txt: '<strong>' + course.courseCode + ' (' + course.regGroupCode + ')</strong> drop processing', type: 'processing'};

            $timeout(function () {
                ScheduleService.getRegistrationStatus().query({regReqId: registrationRequestId}, function (regResponseResult) {
                    var status = GlobalVarsService.getCorrespondingStatusFromState(regResponseResult.state);
                    switch (status) {
                        case 'new':
                        case 'processing':
                            console.log('continue polling');
                            schedulePoller(registrationRequestId, course);
                            break;
                        case 'success':
                            console.log('stop polling - success');
                            course.dropped = true; // used to display course details vs success to drop message
                            course.dropProcessing = false;

                            // After all the processing is complete, update the new Schedule counts.
                            var message;
                            if (course.waitlisted) {
                                // can't use splice (which would remove the success message, so updating counts manually
                                GlobalVarsService.setWaitlistedCredits(parseFloat(GlobalVarsService.getWaitlistedCredits()) - parseFloat(course.credits));
                                GlobalVarsService.setWaitlistedCourseCount(parseInt(GlobalVarsService.getWaitlistedCourseCount()) - 1);

                                message = 'Removed from waitlist for <strong>' + course.courseCode + ' (' + course.regGroupCode + ')</strong> successfully';
                            } else {
                                // can't use splice (which would remove the success message, so updating counts manually
                                GlobalVarsService.setRegisteredCredits(parseFloat(GlobalVarsService.getRegisteredCredits()) - parseFloat(course.credits));
                                GlobalVarsService.setRegisteredCourseCount(parseInt(GlobalVarsService.getRegisteredCourseCount()) - 1);

                                message = '<strong>' + course.courseCode + ' (' + course.regGroupCode + ')</strong> dropped successfully';
                            }

                            course.statusMessage = {txt: message, type: 'success'};
                            break;
                        case 'error':
                            console.log('stop polling - error');
                            course.dropProcessing = false;

                            // Use the message returned from the server
                            var message = regResponseResult.responseItemResults[0].message;
                            course.statusMessage = {txt: message, type: 'error'};
                            break;
                    }
                });
            }, 1000);  // right now we're going to wait 1 second per poll
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