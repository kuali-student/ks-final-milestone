'use strict';

/**
 * Direct Register Controller
 *
 * This controller is intended to be used as the controller for a $modal dialog.
 * It takes a course and walks the user through a wizard style process
 * so they can directly register without going through the cart.
 *
 * Usage:
        var modal = $modal.open({
            backdrop: 'static',
            controller: 'DirectRegisterCtrl',
            windowClass: 'kscr-DirectRegister-window',
            templateUrl: 'components/directregister/directRegister.html',
            size: 'sm',
            resolve: {
                course: function () {
                    return course;
                }
            }
        });
 *
 * Events:
 * - broadcast:
 *      - registerForCourse - broadcast when the full course object exists (credit & grading option) & it is confirmed
 *      - addCourseToCart - broadcast when the user clicks the "Put in Cart" button in the event of a failure
 * - emits: none
 * - catches: none
 */
angular.module('regCartApp')
    .controller('DirectRegisterCtrl', ['$scope', '$rootScope', '$modalInstance', 'GRADING_OPTION', 'STATUS', 'GlobalVarsService', 'course',
        function ($scope, $rootScope, $modalInstance, GRADING_OPTION, STATUS, GlobalVarsService, course) {
            console.log('>> DirectRegisterCtrl');

            // Course object we are working with. Should be made available through a resolve: { course: courseItem }
            $scope.course = course;
            $scope.results = [];
            $scope.statuses = STATUS;

            $scope.directRegister = true;


            var confirmed = false,
                processing = false;


            // Auto-select a grading option if there is only 1 option
            if (!$scope.course.gradingOptionId && Object.keys($scope.course.gradingOptions).length === 1) {
                $scope.course.gradingOptionId = Object.keys($scope.course.gradingOptions)[0];
            }

            // Auto-select a credit option if there is only 1 option
            if (!$scope.course.credits && $scope.course.creditOptions.length === 1) {
                $scope.course.credits = $scope.course.creditOptions[0];
            }


            // Step 1. Save Course Options
            $scope.saveOptions = function(newCourse) {
                // Apply the new values to the course
                if (newCourse.newGrading) {
                    $scope.course.gradingOptionId = newCourse.newGrading;
                }

                if (newCourse.newCredits) {
                    $scope.course.credits = newCourse.newCredits;
                }
            };


            // Step 2: Confirm the Registration
            $scope.confirm = function() {
                confirmed = true;

                // Clear out the results array
                $scope.results.splice(0, $scope.results.length);

                processing = true;
                var event = $rootScope.$broadcast('registerForCourse', $scope.course);
                event.promise.then(updateWithResult, updateWithResult);
            };


            // Step 4: Results (Mocked methods from CartCtrl)
            $scope.addCartItemToCart = function() {
                // Emit the 'addCourseToCart' event to add the course to the cart
                $rootScope.$broadcast('addCourseToCart', $scope.course);
            };

            $scope.addCartItemToWaitlist = function() {
                // Clear out the results
                $scope.results = [];

                // Push the course back through the confirmation process with allowWaitlist = true
                $scope.course.allowWaitlist = true;
                $scope.course.sourceAction = 'waitlist'; //Change the mode from register to waitlist
                $scope.confirm();
            };

            $scope.removeCartResultItem = function() {
                $modalInstance.close($scope.course);
            };




            // Cancel / Close the Modal
            $scope.cancel = function() {
                $modalInstance.dismiss('cancel');
            };


            // Should the options be shown? true if either gradingOptionId or credits are not selected
            $scope.showOptions = function() {
                return getStep() === 'options';
            };

            // Should the confirmation step be shown? true if the user has yet to confirm
            $scope.showConfirm = function() {
                return getStep() === 'confirm';
            };

            // Should the processing step be shown? true if the user has confirmed but the submission has yet to return
            $scope.showProcessing = function() {
                return getStep() === 'processing';
            };

            // Should the results be shown? true if we have a terminal status on their submission
            $scope.showResults = function() {
                return getStep() === 'results';
            };


            // Returns the grading option for the course
            $scope.gradingOption = function () {
                return $scope.course.gradingOptions[$scope.course.gradingOptionId];
            };

            // Shows the grading badge (unless it is letter -- the default)
            $scope.showBadge = function () {
                return $scope.course.gradingOptionId !== GRADING_OPTION.letter;
            };


            // Helper method for determining which step they are on. [ options, confirm, processing, results ]
            function getStep() {
                var step = '';
                if (!$scope.course.gradingOptionId || !$scope.course.credits) {
                    step = 'options';
                } else if (!confirmed) {
                    step = 'confirm';
                } else if (processing) {
                    step = 'processing';
                } else if (!processing) {
                    step = 'results';
                }

                return step;
            }


            // Method for mapping the response of the registration request back to the results.
            // If no items exist, the dailog will close with an error state.
            function updateWithResult(response) {
                if (angular.isDefined(response.responseItemResults)) {
                    var resultItem = angular.copy($scope.course),
                        responseItem = response.responseItemResults[0];

                    resultItem.state = responseItem.state;
                    resultItem.type = responseItem.type;
                    resultItem.status = GlobalVarsService.getCorrespondingStatusFromState(responseItem.state);
                    resultItem.statusMessages = responseItem.messages;

                    $scope.results.push(resultItem);

                    // Apply the waitlist message if the result was of that type
                    switch (resultItem.status) {
                        case STATUS.success:
                            if (angular.isArray(responseItem.messages) && responseItem.messages.length > 0) {
                                // This successful item has a message attached to it,
                                // pop it off an add it as a new info item right after the success result item
                                var infoItem = angular.copy(resultItem);
                                infoItem.status = STATUS.info;
                                $scope.results.push(infoItem);

                                // Get rid of the info messages on the success item.
                                responseItem.messages.splice(0, responseItem.messages.length);
                            }
                            break;

                        case STATUS.waitlist:
                        case STATUS.action: // waitlist action available
                            resultItem.waitlistMessage = GlobalVarsService.getCorrespondingMessageFromStatus(resultItem.status);
                            break;
                    }
                }

                processing = false;

                if ($scope.results.length === 0) {
                    // No items were found. Close the dialog out and pass along the response object.
                    $modalInstance.dismiss(response);
                }
            }

        }]);