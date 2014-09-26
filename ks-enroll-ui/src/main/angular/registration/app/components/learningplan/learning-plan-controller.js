'use strict';

/**
 * Learning Plan Controller
 *
 * This controller is intended to be used as the controller for the learning-plan directive.
 *
 * Events:
 * - broadcast:
 *      - addCourseToCart - broadcast when the user clicks the Keep in Cart button in the event of a failure
 * - emits: none
 * - catches: none
 */
angular.module('regCartApp')
    .controller('LearningPlanCtrl', ['$scope', '$rootScope', '$state', 'SEARCH_CRITERIA', 'LEARNING_PLAN_CATEGORIES', 'TermsService', 'CartService', 'ScheduleService', 'LearningPlanService',
        function ($scope, $rootScope, $state, SEARCH_CRITERIA, LEARNING_PLAN_CATEGORIES, TermsService, CartService, ScheduleService, LearningPlanService) {
            console.log('>> LearningPlanCtrl');

            $scope.categories = LEARNING_PLAN_CATEGORIES;

            $scope.plan = null;
            $scope.message = null;
            $scope.selectedCategory = null;


            // Load the Learning Plan for the currently selected term
            function loadLearningPlan() {
                $scope.plan = null;
                $scope.message = null;

                var termId = TermsService.getTermId();
                if (termId) {
                    LearningPlanService.getLearningPlan(termId)
                        .then(function(plan) {
                            $scope.plan = plan;
                            console.log(plan);
                        }, function(response) {
                            console.log('-- ' + response);
                            if (angular.isDefined(response.messageKey)) {
                                $scope.message = response;
                            }
                        });
                }
            }

            // Listen for the termIdChanged event that is fired when a term has been changed & processed
            $scope.$on('termIdChanged', function() {
                loadLearningPlan();
            });

            loadLearningPlan();


            $scope.addAllToCart = function() {
                var actionableItems = $scope.getActionableItems();
                if (actionableItems.length > 0) {
                    for (var i = 0; i < actionableItems.length; i++) {
                        $rootScope.$broadcast('addCourseToCart', actionableItems[i]);
                    }
                }
            };

            // Count of planned items in a given category
            $scope.countInCategory = function(category) {
                var count = 0;
                for (var i = 0; i < $scope.plan.length; i++) {
                    if ($scope.plan[i].category === category) {
                        count++;
                    }
                }

                return count;
            };

            $scope.getActionableItems = function() {
                var items = [];

                if (angular.isArray($scope.plan)) {
                    for (var i = 0; i < $scope.plan.length; i++) {
                        var item = $scope.plan[i];
                        if (item.category === $scope.selectedCategory && item.regGroupId && !$scope.isItemInCart(item) && !$scope.isItemInSchedule(item)) {
                            items.push(item);
                        }
                    }
                }

                return items;
            };

            $scope.isItemInCart = function(course) {
                return CartService.isCourseInCart(course);
            };

            $scope.isItemInSchedule = function(course) {
                return ScheduleService.isCourseRegistered(course) || ScheduleService.isCourseWaitlisted(course);
            };


            $scope.viewDetails = function(course) {
                if (course.courseId) {
                    // Redirects the view to the course details screen
                    $state.go('root.search.details', {
                        searchCriteria: SEARCH_CRITERIA.fromSchedule,
                        id: course.courseId,
                        code: course.courseCode,
                        regGroupId: course.regGroupId
                    });
                } else {
                    $state.go('root.search.results', {
                        searchCriteria: SEARCH_CRITERIA.fromSchedule,
                        cluId: course.cluId
                    });
                }
            };

        }]);