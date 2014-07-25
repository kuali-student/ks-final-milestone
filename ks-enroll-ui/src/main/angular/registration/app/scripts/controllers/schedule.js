'use strict';

angular.module('regCartApp')
    .controller('ScheduleCtrl', ['$scope', '$modal', '$timeout', 'STATUS', 'GRADING_OPTION', 'ScheduleService', 'GlobalVarsService',
    function ($scope, $modal, $timeout, STATUS, GRADING_OPTION, ScheduleService, GlobalVarsService) {

        $scope.getSchedules = GlobalVarsService.getSchedule;
        $scope.registeredCredits = GlobalVarsService.getRegisteredCredits;
        $scope.registeredCourseCount = GlobalVarsService.getRegisteredCourseCount;
        $scope.waitlistedCredits = GlobalVarsService.getWaitlistedCredits;
        $scope.waitlistedCourseCount = GlobalVarsService.getWaitlistedCourseCount;
        $scope.numberOfDroppedWailistedCourses = 0;
        $scope.numberOfDroppedCourses = 0;
        $scope.userId = GlobalVarsService.getUserId;

        /*
        Listens for the "removeWaitlistStatusMessage" event and removes the card for the given course.
         */
        $scope.$on('removeWaitlistStatusMessage',function (event, course) {
            course.statusMessage = null;
            $scope.numberOfDroppedWailistedCourses = $scope.numberOfDroppedWailistedCourses - 1;
            if ($scope.numberOfDroppedWailistedCourses === 0) {
                $scope.showWaitlistMessages = false;
            }
        });

        /*
         Listens for the "removeRegisteredStatusMessage" event and removes the card for the given course.
         */
        $scope.$on('removeRegisteredStatusMessage',function (event, course) {
            course.statusMessage = null;
            $scope.numberOfDroppedCourses = $scope.numberOfDroppedCourses - 1;
            if ($scope.numberOfDroppedCourses === 0) {
                $scope.showRegisteredMessages = false;
            }
        });

        /*
         Listens for the "dropRegistered" event and calls the schedule service to drop the given
         course.
         */
        $scope.$on('dropRegistered', function (event, index, course) {
            console.log('Open drop confirmation for registered course');
            ScheduleService.dropRegistrationGroup().query({
                masterLprId: course.masterLprId
            }, function (dropResponseResult) {
                // need a count on how many success drop message are opened. So if there are no courses when we "X" the last
                // success drop message the section can be replaced by the splash screen (unless there are waitlisted courses)
                $scope.numberOfDroppedCourses = $scope.numberOfDroppedCourses + 1;
                $scope.showRegisteredMessages = true;
                course.dropping = false; // used to display confirmation popup
                course.dropProcessing = true; // used to display spinner while poll is running

                schedulePoller(dropResponseResult.id, course);
            }, function (error) {
                $scope.userMessage = {txt: error.data, type: STATUS.error};
            });
        });

        /*
         Listens for the "dropWaitlist" event and calls the schedule service to remove the given
         course from the user's waitlist.
         */
        $scope.$on('dropWaitlist', function (event, index, course) {
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
        });

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

                            $scope.$broadcast('courseDropped', course);

                            // After all the processing is complete, update the new Schedule counts.
                            if (course.waitlisted) {
                                GlobalVarsService.removeWaitlistedCourse(course);
                                message = 'Removed from waitlist for <strong>' + course.courseCode + ' (' + course.regGroupCode + ')</strong> successfully';
                            } else {
                                GlobalVarsService.removeRegisteredCourse(course);
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

    }]);