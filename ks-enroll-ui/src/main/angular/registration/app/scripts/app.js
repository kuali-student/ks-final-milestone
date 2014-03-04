'use strict';

angular.module('regCartApp', [
    'configuration',
    'ngCookies',
    'ngResource',
    'ngSanitize',
    'ui.router',
    'ui.bootstrap'
])
    .config(function ($stateProvider, $urlRouterProvider) {
        $urlRouterProvider.otherwise('/myCart');

        $stateProvider
            .state('root', {
                templateUrl:'partials/main.html',
                controller:'MainCtrl'
            })
            .state('root.cart', {
                url:'/myCart',
                templateUrl:'partials/cart.html',
                controller:'CartCtrl'
            })
            .state('root.schedule', {
                url:'/mySchedule',
                templateUrl:'partials/schedule.html',
                controller:'ScheduleCtrl'
            })
            .state('root.additionalOptions', {
                url:'/options',
                templateUrl:'partials/additionalOptions.html'
            })
            .state('root.dropCourse', {
                url:'/options',
                templateUrl:'partials/dropCourse.html'
            })
            .state('root.editCourseOptions', {
                url:'/options',
                templateUrl:'partials/editCourseOptions.html'
            });
    });