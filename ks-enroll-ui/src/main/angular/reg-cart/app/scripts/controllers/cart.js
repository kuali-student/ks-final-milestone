'use strict';


var cartServiceModule = angular.module('regCartApp');

cartServiceModule.controller('CartCtrl', ['$scope', 'CartService',
    function ($scope, CartService) {
        $scope.cart = CartService.getCart().query({termCode:'Fall2012'});

        $scope.add = function() {

            var code = $scope.courseCode;
            var regGroup = $scope.regCode;

            $scope.error = 'Cannot find the course "' + code + '" in term ' + $scope.termId;
            $scope.courseCode.setValidity("coursecheck", false);
        };

        $scope.delete = function(index) {
            //var course = $scope.cart.items[index];

            // call the backend service here to persist something
            // API.delete(course){ id: couse.id }, function (success) {
            //      $scope.cart.splice(index, 1);
            // });

            $scope.cart.items.splice(index, 1);


        };

    }]);



