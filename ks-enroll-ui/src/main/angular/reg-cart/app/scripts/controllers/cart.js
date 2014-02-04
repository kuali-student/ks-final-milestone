'use strict';


var cartServiceModule = angular.module('regCartApp');

cartServiceModule.controller('CartCtrl', ['$scope', 'CartService',
    function ($scope, CartService) {
        $scope.cart = CartService.getCart().query({termCode:'Fall2012'});

        $scope.delete = function(index) {
            var course = $scope.cart[index];

            // call the backend service here to persist something
            // API.delete(course){ id: couse.id }, function (success) {
            //      $scope.cart.splice(index, 1);
            // });

            $scope.cart.splice(index, 1);
        };

    }]);



