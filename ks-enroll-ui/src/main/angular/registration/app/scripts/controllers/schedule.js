'use strict';

var cartServiceModule = angular.module('regCartApp');

cartServiceModule.controller('ScheduleCtrl', ['$scope', '$modal', '$timeout', 'STATUS', 'GRADING_OPTION', 'ScheduleService', 'GlobalVarsService',
    function ($scope, $modal, $timeout, STATUS, GRADING_OPTION, ScheduleService, GlobalVarsService) {

        $scope.getSchedules = GlobalVarsService.getSchedule;
        $scope.registeredCredits = GlobalVarsService.getRegisteredCredits;
        $scope.registeredCourseCount = GlobalVarsService.getRegisteredCourseCount;
        $scope.waitlistedCredits = GlobalVarsService.getWaitlistedCredits;
        $scope.waitlistedCourseCount = GlobalVarsService.getWaitlistedCourseCount;
        $scope.numberOfDroppedWailistedCourses = 0;
        $scope.userId = GlobalVarsService.getUserId;

        $scope.$watch('termId', function (newValue) {
            console.log('term id has changed: '+newValue);
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

                schedulePoller(dropResponseResult.id, course);
            }, function (error) {
                $scope.userMessage = {txt: error.data, type: STATUS.error};
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

                schedulePoller(dropResponseResult.id, course);
            }, function (error) {
                course.statusMessage = {txt: error.data, type: STATUS.error};
            });
        };

        // This method is used to update the status of a course by polling the server
        var schedulePoller = function (registrationRequestId, course) {
            console.log('start polling for course to be dropped from schedule');
            course.statusMessage = {txt: '<strong>' + course.courseCode + ' (' + course.regGroupCode + ')</strong> drop processing', type: STATUS.processing};

            $timeout(function () {
                ScheduleService.getRegistrationStatus().query({regReqId: registrationRequestId}, function (regResponseResult) {
                    var status = GlobalVarsService.getCorrespondingStatusFromState(regResponseResult.state);
                    var message;
                    switch (status) {
                        case STATUS.new:
                        case STATUS.processing:
                            console.log('continue polling');
                            schedulePoller(registrationRequestId, course);
                            break;
                        case STATUS.success:
                            console.log('stop polling - success');
                            course.dropped = true; // used to display course details vs success to drop message
                            course.dropProcessing = false;

                            // After all the processing is complete, update the new Schedule counts.
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

                            course.statusMessage = {txt: message, type: STATUS.success};
                            break;
                        case STATUS.error:
                            console.log('stop polling - error');
                            course.dropProcessing = false;

                            // Use the message returned from the server
                            message = regResponseResult.responseItemResults[0].messages[0];
                            course.statusMessage = {txt: message, type: STATUS.error};
                            break;
                    }
                });
            }, 1000);  // right now we're going to wait 1 second per poll
        };

        $scope.editScheduleItem = function (course) {
            course.newCredits = course.credits;
            course.newGrading = course.gradingOptionId;
            course.editing = true;
        };

        $scope.updateScheduleItem = function (course) {
            console.log('Updating registered course:');
            console.log(course.newCredits);
            console.log(course.newGrading);
            ScheduleService.updateScheduleItem().query({
                courseCode: course.courseCode,
                regGroupCode: course.regGroupCode,
                masterLprId: course.masterLprId,
                termId: $scope.termId,
                credits: course.newCredits,
                gradingOptionId: course.newGrading
            }, function (scheduleItemResult) {
                GlobalVarsService.setRegisteredCredits(parseFloat(GlobalVarsService.getRegisteredCredits()) - parseFloat(course.credits) + parseFloat(scheduleItemResult.credits));
                updateCard(course, scheduleItemResult);
            }, function (error) {
                course.statusMessage = {txt: error.data, type: STATUS.error};
            });
        };

        $scope.updateWaitlistItem = function (course) {
            console.log('Updating waitlisted course:');
            console.log(course.newCredits);
            console.log(course.newGrading);
            ScheduleService.updateWaitlistItem().query({
                courseCode: course.courseCode,
                regGroupCode: course.regGroupCode,
                masterLprId: course.masterLprId,
                termId: $scope.termId,
                credits: course.newCredits,
                gradingOptionId: course.newGrading
            }, function (scheduleItemResult) {
                GlobalVarsService.setWaitlistedCredits(parseFloat(GlobalVarsService.getWaitlistedCredits()) - parseFloat(course.credits) + parseFloat(scheduleItemResult.credits));
                updateCard(course, scheduleItemResult);
            }, function (error) {
                course.statusMessage = {txt: error.data, type: STATUS.error};
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
            return course.gradingOptionId !== GRADING_OPTION.letter || course.editGradingOptionLetter;
        };

        function updateCard(course, scheduleItemResult) {
            console.log(scheduleItemResult);
            var oldCredits=course.credits;
            var oldGrading=course.gradingOptionId;
            course.credits = scheduleItemResult.credits;
            course.gradingOptionId = scheduleItemResult.gradingOptionId;
            course.editing = false;
            course.isopen = !course.isopen; // collapse the card
//                course.statusMessage = {txt: 'Changes saved successfully', type: 'success'};
            console.log('Started to animate...');
            if (course.newGrading !== oldGrading) {
                course.editGradingOption = true;
                if (course.gradingOptionId === GRADING_OPTION.letter) {
                    course.editGradingOptionLetter = true;
                }
                $timeout(function(){
                    course.editGradingOption = false;
                    course.editGradingOptionDone = true;
                }, 2000);
                $timeout(function(){
                    course.editGradingOptionDone = false;
                    if (course.gradingOptionId === GRADING_OPTION.letter) {
                        course.editGradingOptionLetter = false;
                    }
                }, 4000);
            }
            if (course.newCredits !== oldCredits) {
                course.editCredits = true;
                $timeout(function(){
                    course.editCredits = false;
                    course.editCreditsDone = true;
                }, 2000);
                $timeout(function(){
                    course.editCreditsDone = false;
                }, 4000);
            }
        }

    }]);