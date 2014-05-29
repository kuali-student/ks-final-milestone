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
        $urlRouterProvider.otherwise('/responsive/cart');

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
            .state('root.additionalOptions', {
                url: '/options',
                templateUrl: 'partials/additionalOptions.html'
            });

        //Add the  login interceptor to all service calls
        $httpProvider.interceptors.push('loginInterceptor');
    })

;