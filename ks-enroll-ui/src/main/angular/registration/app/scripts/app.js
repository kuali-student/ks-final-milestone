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
            .state('root.cart.options', {
                url:'/options',
                onEnter:function ($stateParams, $state, $modal) {
                    console.log($state);
                    //This is a test of ui-rputer and modals... I think we might just go the route of a regular modal in cart controller.
                    $modal.open({
                        backdrop : 'static',
                        templateUrl:'partials/additionalOptions.html',
                        resolve:{

                        },
                        controller:['$scope', function ($scope) {
                            $scope.dismiss = function () {
                                console.log('dismiss');
                                $scope.$close(true);
                                return $state.transitionTo('root.cart');
                            };

                            $scope.save = function () {
                                console.log('save');
                                $scope.$close(true);
                                return $state.transitionTo('root.cart');
                            };
                        }]
                    }).result.then(function (result) {
                            console.log('before result transition');
                            if (result) {
                                console.log('transition');
                                return $state.transitionTo('root.cart');
                            }
                        });
                }
            })
            .state('root.schedule', {
                url:'/mySchedule',
                templateUrl:'partials/schedule.html'
            })
            .state('root.additionalOptions', {
                url:'/options',
                templateUrl:'partials/additionalOptions.html'
            });
    });