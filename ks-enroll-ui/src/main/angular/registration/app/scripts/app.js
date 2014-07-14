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

        // Define some of the more commonly used components
        var myCart = {
                templateUrl: 'partials/cart.html',
                controller: 'CartCtrl'
            },
            mySchedule = {
                templateUrl: 'partials/schedule.html',
                controller: 'ScheduleCtrl'
            },
            searchForm = {
                templateUrl: 'partials/searchForm.html',
                controller: 'SearchFormCtrl'
            };


        $stateProvider
            // Base 'root' state, contains a ui-view that should be referred to with '' view.
            .state('root', {
                abstract: true, // State is never explicity activated
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
                    searchform: searchForm
                }
            })
            .state('root.cart', {
                url: '/myCart',
                views: {
                    '': mySchedule,
                    mycart: myCart,
                    schedule: mySchedule,
                    searchform: searchForm
                }
            })
            .state('root.search', {
                url: '/search/:searchCriteria',
                views: {
                    '': {
                        templateUrl: 'partials/search.html',
                        controller: 'SearchCtrl'
                    },
                    mycart: myCart,
                    schedule: mySchedule,
                    searchform: searchForm
                }
            });

        //Add the  login interceptor to all service calls
        $httpProvider.interceptors.push('loginInterceptor');
    })

;