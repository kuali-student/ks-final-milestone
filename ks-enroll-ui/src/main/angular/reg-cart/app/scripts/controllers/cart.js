'use strict';


var cartServiceModule = angular.module('regCartApp');

cartServiceModule.controller('CartCtrl', ['$scope', 'CartService',
    function ($scope, CartService) {
        $scope.cart = CartService.getCart().query({termCode:'Fall2012'});

        $scope.add = function() {
            $scope.cart.items.push($scope.newCartItem);

            $scope.newCartItem = null;
            $scope.showNew = false;

        };

        $scope.search = function() {

            var code = $scope.courseCode;
            var regGroup = $scope.regCode;

            $scope.newCartItem = CartService.getGradingOptions().query();

            if($scope.newCartItem == null) {
                $scope.error = 'Cannot find the course "' + code + '" in term ' + $scope.termId;
                $scope.courseCode.setValidity("coursecheck", false);
                $scope.showNew = false;
            } else {
                $scope.showNew = true;
            }
        };

        $scope.cancelNew = function(){
            $scope.newCcartItem = null;
            $scope.showNew = false;
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



