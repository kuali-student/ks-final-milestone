'use strict';

angular.module('regCartApp')

    .directive('courseOptions', ['GRADING_OPTION', function (GRADING_OPTION) {
        return {
            restrict: 'E',
            transclude: true,
            scope: {
                // = : 2-way data binding, changes made locally propagate out
                // @ : no binding, value comes through as a string
                // & : value gets evaluated in the original scope, ideal for callbacks

                course: '=', // Handle on the course object, 2-way binding due to '='
                maxOptions: '@max', // Max # of items to show at a time
                prefix: '@', // Element ID prefix (e.g. waitlist_)
                showAll: '@', // Show all items, preventing the More toggle from showing [true, false]
                moreButtonSelectBehavior: '@moreBehavior', // Behavior of more/expand button when it is selected [expand, dialog]
                cancelText: '@', // Cancel button text
                submitText: '@', // Submit button text
                cancelFn: '&onCancel', // Function to call when canceling the form, provides course as parameter, usage: on-submit="processSubmit(course)"
                submitFn: '&onSubmit' // Function to call when submitting the form, provides course as parameter, usage: on-submit="processCancel(course)"
            },
            templateUrl: 'partials/courseOptions.html',
            controller: ['$scope', '$modal', function ($scope, $modal) {
                var course = $scope.course,
                    maxOptions = $scope.maxOptions || 4,
                    showAll = $scope.showAll ? true : false,
                    moreButtonSelectBehavior = $scope.moreButtonSelectBehavior || 'expand';

                $scope.showAllCreditOptions = showAll;
                $scope.showAllGradingOptions = showAll;


                // Transpose the grading options object into a more consumable format for the view
                $scope.gradingOptions = [];
                if (course && course.gradingOptions) {
                    angular.forEach(course.gradingOptions, function (v, k) {
                        this.push({ key: k, label: v});
                    }, $scope.gradingOptions);
                }

                // Sort the credit options as numbers rather than strings
                course.creditOptions.sort(function(o1, o2) {
                    var f1 = parseFloat(o1),
                        f2 = parseFloat(o2);

                    return (f1 === f2 ? 0 : (f1 > f2 ? 1 : -1));
                });


                // Set the newGrading value
                if (course.gradingOptionId) {
                    // There is already a selected value, use that
                    course.newGrading = course.gradingOptionId;
                } else {
                    if (angular.isDefined(course.gradingOptions[GRADING_OPTION.letter])) {
                        // Default to Letter if it is an option
                        course.newGrading = GRADING_OPTION.letter;
                    } else {
                        // Default to the first item in the list
                        course.newGrading = Object.keys($scope.course.gradingOptions)[0];
                    }
                }

                // Set the newCredits value
                if (course.credits) {
                    course.newCredits = course.credits;
                } else {
                    // Default to the first credit option
                    course.newCredits = course.creditOptions[0];
                }


                $scope.creditOptionsFilter = function (option) {
                    if (!course || $scope.showAllCreditOptions) {
                        return true;
                    }

                    return shouldShow(course.creditOptions, course.credits, option);
                };

                $scope.gradingOptionsFilter = function (option) {
                    if (!course || $scope.showAllGradingOptions) {
                        return true;
                    }

                    return shouldShow(Object.keys(course.gradingOptions), course.grading, option.key);
                };

                $scope.showMoreCreditOptions = function () {
                    if (moreButtonSelectBehavior === 'expand') {
                        $scope.showAllCreditOptions = true;
                    } else {
                        showOptionsDialog();
                    }
                };

                $scope.showMoreGradingOptions = function () {
                    if (moreButtonSelectBehavior === 'expand') {
                        $scope.showAllGradingOptions = true;
                    } else {
                        showOptionsDialog();
                    }
                };

                $scope.shouldShowMoreCreditOptionsToggle = function () {
                    return !$scope.showAllCreditOptions && course.creditOptions.length > maxOptions;
                };

                $scope.shouldShowMoreGradingOptionsToggle = function () {
                    return !$scope.showAllGradingOptions && Object.keys(course.gradingOptions).length > maxOptions;
                };

                $scope.cancel = function () {
                    console.log('Canceling options changes');

                    // Reset the edit state on the course & form
                    course.newCredits = course.credits;
                    course.newGrading = course.grading || course.gradingOptionId; // grading in cart, gradingOptionId in schedule

                    course.status = ''; // From cart
                    course.editing = false; // From schedule

                    if ($scope.cancelFn) {
                        $scope.cancelFn({course: course});
                    }

                    // Reset the state of the options form
                    reset();
                };

                $scope.submit = function () {
                    console.log('Submitting options form');

                    if ($scope.submitFn) {
                        $scope.submitFn({course: course});
                    }

                    // Reset the state of the options form
                    reset();
                };

                $scope.showGradingHelp = function () {
                    $modal.open({
                        templateUrl: 'partials/gradingOptionsHelp.html'
                    });
                };

                function shouldShow(options, selectedItem, currentItem) {
                    if (options.length <= maxOptions) {
                        return true;
                    }

                    var selectedItemIndex = options.indexOf(selectedItem),
                        currentItemIndex = options.indexOf(currentItem),
                        padding = 2,
                        min = Math.max(0, Math.min(selectedItemIndex - padding, options.length - maxOptions)),
                        max = Math.min(min + maxOptions, options.length) - 1;

                    return min <= currentItemIndex && currentItemIndex <= max;
                }

                function showOptionsDialog() {
                    // Create a sanitized copy of the scope for the modal dialog
                    var modalScope = $scope.$new();
                    modalScope.course = angular.copy(course);
                    modalScope.cancel = function () {
                    };
                    modalScope.submit = function () {
                    };

                    course.editing = false;

                    var dialog = $modal.open({
                        backdrop: 'static',
                        template: '<div class="kscr-AdditionalOptions">' +
                            '<course-options course="course" show-all="true" max="' + maxOptions + '" prefix="modal_' + ($scope.prefix ? $scope.prefix : '') + '" on-submit="modalSubmit()" on-cancel="modalCancel()"></course-options>' +
                            '</div>',
                        scope: modalScope,
                        controller: ['$scope', function ($modalScope) {
                            // Show all options within the modal dialog
                            $modalScope.showAllCreditOptions = true;
                            $modalScope.showAllGradingOptions = true;

                            $modalScope.modalCancel = function () {
                                $modalScope.$dismiss('cancel');
                            };

                            $modalScope.modalSubmit = function () {
                                $modalScope.$close($modalScope.course);
                            };
                        }]
                    });

                    dialog.result.then(function (modalCourse) {
                        course.newGrading = modalCourse.newGrading;
                        course.newCredits = modalCourse.newCredits;
                        $scope.submit();
                    }, function () {
                        $scope.cancel();
                    });
                }

                function reset() {
                    // Reset the option visibility based on the showAll parameter.
                    $scope.showAllCreditOptions = showAll;
                    $scope.showAllGradingOptions = showAll;
                }
            }]
        };
    }])
;
