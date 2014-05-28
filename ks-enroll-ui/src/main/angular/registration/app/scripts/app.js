'use strict';

angular.module('regCartApp', [
        'configuration',
        'ngAnimate',
        'ngCookies',
        'ngResource',
        'ngSanitize',
        'ngTouch',
        'ui.router',
        'ui.bootstrap'
    ])
    .config(function ($stateProvider, $urlRouterProvider, $httpProvider) {
        $urlRouterProvider.otherwise('/myCart');

        $stateProvider
            .state('root.responsive', {
                templateUrl: 'partials/responsive/responsive.html'
            })
            .state('root.responsive.schedule', {
                url: '/responsive/schedule',
                views:{
                    mycart:{
                        templateUrl: 'partials/cart.html',
                        controller: 'CartCtrl'
                    },
                    schedule:{
                        templateUrl: 'partials/responsive/schedule.html',
                        controller: 'ScheduleCtrl'
                    }
                }
            })
            .state('root.responsive.cart', {
                url: '/responsive/cart',
                views:{
                    mycart:{
                        templateUrl: 'partials/cart.html',
                        controller: 'CartCtrl'
                    },
                    schedule:{
                        templateUrl: 'partials/responsive/schedule.html',
                        controller: 'ScheduleCtrl'
                    }
                }
            })
            .state('root', {
                templateUrl: 'partials/main.html',
                controller: 'MainCtrl'
            })
            .state('root.cart', {
                url: '/myCart',
                templateUrl: 'partials/cart.html',
                controller: 'CartCtrl'
            })
            .state('root.schedule', {
                url: '/mySchedule',
                templateUrl: 'partials/schedule.html',
                controller: 'ScheduleCtrl'
            })
            .state('root.additionalOptions', {
                url: '/options',
                templateUrl: 'partials/additionalOptions.html'
            });

        //Add the  login interceptor to all service calls
        $httpProvider.interceptors.push('loginInterceptor');
    })



    .directive('courseOptions', function() {
        return {
            restrict: 'E',
            transclude: true,
            scope: {
                course: '=', // Handle on the course object
                maxOptions: '=max', // Max # of items to show at a time
                prefix: '@', // Element ID prefix (e.g. waitlist_)
                showAll: '=', // Show all items, preventing the More toggle from showing [true, false]
                cancelFn: '&onCancel', // Function to call when canceling the form, provides course as parameter
                submitFn: '&onSubmit' // Function to call when submitting the form, provides course as parameter
            },
            templateUrl: 'partials/courseOptions.html',
            controller: ['$scope', '$modal', function($scope, $modal) {
                var course = $scope.course,
                    maxOptions = $scope.maxOptions || 4,
                    showAll = $scope.showAll ? true : false;

                $scope.showAllCreditOptions = showAll;
                $scope.showAllGradingOptions = showAll;


                // Transpose the grading options object into a more consumable format for the view
                $scope.gradingOptions = [];
                if (course && course.gradingOptions) {
                    angular.forEach(course.gradingOptions, function(v, k) {
                        this.push({ key: k, label: v});
                    }, $scope.gradingOptions);
                }


                $scope.creditOptionsFilter = function(option) {
                    if (!course || $scope.showAllCreditOptions) {
                        return true;
                    }

                    return shouldShow(course.creditOptions, course.credits, option);
                };

                $scope.gradingOptionsFilter = function(option) {
                    if (!course || $scope.showAllGradingOptions) {
                        return true;
                    }

                    return shouldShow(Object.keys(course.gradingOptions), course.grading, option.key);
                };

                function shouldShow(options, selectedItem, currentItem) {
                    if (options.length <= maxOptions) {
                        return true;
                    }

                    var selectedItemIndex = options.indexOf(selectedItem),
                        currentItemIndex = options.indexOf(currentItem),
                        min = (selectedItemIndex < (maxOptions) ? 0 : selectedItemIndex - 2),
                        max = (selectedItemIndex < (maxOptions - 1) ? (maxOptions - 1) : selectedItemIndex + 1);

                    if (selectedItemIndex < 3) { // In first 3 items, show 0-3
                        min = 0;
                        max = 3;
                    } else if (selectedItemIndex >= (options.length - 2)) { // In last 2, show last-3
                        max = options.length - 1;
                        min = max - 3;
                    } else {
                        min = selectedItemIndex - 2;
                        max = selectedItemIndex + 1;
                    }

                    return min <= currentItemIndex && currentItemIndex <= max;
                }

                $scope.showOptionsDialog = function() {
                    // Create a sanitized copy of the scope for the modal dialog
                    var modalScope = $scope.$new();
                    modalScope.course = angular.copy(course);
                    modalScope.cancel = function() {};
                    modalScope.submit = function() {};

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

                            $modalScope.modalCancel = function() {
                                $modalScope.$dismiss('cancel');
                            };

                            $modalScope.modalSubmit = function() {
                                $modalScope.$close($modalScope.course);
                            };
                        }]
                    });

                    dialog.result.then(function(modalCourse) {
                        course.newGrading = modalCourse.newGrading;
                        course.newCredits = modalCourse.newCredits;
                        $scope.submit();
                    }, function() {
                        $scope.cancel();
                    });
                };

                $scope.shouldShowMoreCreditOptionsToggle = function() {
                    return !$scope.showAllCreditOptions && course.creditOptions.length > maxOptions;
                };

                $scope.shouldShowMoreGradingOptionsToggle = function() {
                    return !$scope.showAllGradingOptions && Object.keys(course.gradingOptions).length > maxOptions;
                };

                $scope.cancel = function() {
                    console.log('Canceling options changes');

                    // Reset the edit state on the course & form
                    course.newCredits = course.credits;
                    course.newGrading = course.grading || course.gradingOptionId; // grading in cart, gradingOptionId in schedule

                    course.status = ''; // From cart
                    course.editing = false; // From schedule

                    if ($scope.cancelFn) {
                        $scope.cancelFn({course: course});
                    }
                };

                $scope.submit = function() {
                    console.log('Submitting options form');

                    if ($scope.submitFn) {
                        $scope.submitFn({course: course});
                    }
                };
            }]
        };
    })
;