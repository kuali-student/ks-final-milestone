'use strict';

angular.module('regCartApp', [
        'configuration',
        'ngAnimate',
        'ngCookies',
        'ngResource',
        'ngSanitize',
        'ui.router',
        'ui.bootstrap'
    ])
    .config(function ($stateProvider, $urlRouterProvider, $httpProvider) {
        $urlRouterProvider.otherwise('/myCart');

        // Define some of the more commonly used components
        var myCart = {
                templateUrl: 'partials/cart.html',
                controller: 'CartCtrl'
            },
            mySchedule = {
                templateUrl: 'partials/schedule.html',
                controller: 'ScheduleCtrl'
            },
            myLearningPlan = {
                templateUrl: 'components/learningplan/learningPlan.html',
                controller: 'LearningPlanCtrl'
            };


        $stateProvider
            // Base 'root' state, contains a ui-view that should be referred to with '' view.
            .state('root', {
                abstract: true, // State is never explicity activated
                url: '?term',
                views: {
                    root: {
                        templateUrl: 'partials/main.html',
                        controller: 'MainCtrl'
                    }
                }
            })

            .state('root.schedule', {
                url: '/mySchedule',
                views: {
                    '': mySchedule,
                    mycart: myCart,
                    schedule: mySchedule,
                    learningplan: myLearningPlan
                }
            })
            .state('root.cart', {
                url: '/myCart',
                views: {
                    '': mySchedule,
                    mycart: myCart,
                    schedule: mySchedule,
                    learningplan: myLearningPlan
                }
            })

            .state('root.search', {
                url: '/search?page&displayLimit&predicate&reverse&filters', // Optional query parameters that maintain display state
                views: {
                    '': {
                        templateUrl: 'partials/search.html'
                    },
                    mycart: myCart,
                    schedule: mySchedule,
                    learningplan: myLearningPlan
                }
            })
            .state('root.search.results', {
                url: '/?searchCriteria&cluId&origin', // URL gets appended to parent's
                views: {
                    '': {
                        templateUrl: 'partials/searchResults.html',
                        controller: 'SearchCtrl'
                    }
                }
            })
            .state('root.search.details', {
                url: '/{id}?searchCriteria&cluId&code&regGroupId&origin', // URL gets appended to parent's
                templateUrl: 'partials/searchDetails.html',
                controller: 'CourseDetailsCtrl'
            })
        ;

        //Add the  login interceptor to all service calls
        $httpProvider.interceptors.push('loginInterceptor');
    })

;


/**
 * This is the override section for the FEATURE_TOGGLES
 * It allows you to selectively override the config based on query parameters.
 *
 * Available Overrides:
 * - LearningPlan - ?learningPlan=true
 *
 */
angular.module('regCartApp')
    .run(['$location', 'FEATURE_TOGGLES', function($location, FEATURE_TOGGLES) {
        var queryParameters = $location.search();
        if (angular.isDefined(queryParameters.learningPlan)) {
            FEATURE_TOGGLES.learningPlan = queryParameters.learningPlan ? true : false;
        }
    }]);