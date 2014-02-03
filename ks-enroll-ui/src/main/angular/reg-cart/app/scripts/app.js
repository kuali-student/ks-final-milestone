'use strict';

angular.module('regCartApp', [
  'ngCookies',
  'ngResource',
  'ngSanitize',
  'ui.router'

])
  .config(function($stateProvider, $urlRouterProvider) {
        $urlRouterProvider.otherwise('/myCart');

        $stateProvider
            .state('root',{
                templateUrl:'partials/main.html'
            })
            .state('root.cart',{
                url: '/myCart',
                templateUrl:'partials/cart.html',
                controller:'CartCtrl'
            })
            .state('root.schedule',{
                url: '/mySchedule',
                templateUrl:'partials/schedule.html'
            })
            .state('root.additionalOptions',{
                url: '/options',
                templateUrl:'partials/additionalOptions.html'
            });
    });