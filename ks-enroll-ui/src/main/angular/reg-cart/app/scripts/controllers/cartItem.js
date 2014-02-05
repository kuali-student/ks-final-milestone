'use strict';


angular.module('regCartApp').controller('CartItemCtrl', ['$scope',
    function ($scope, $index) {
        //Default values
        $scope.newCredits = $scope.$parent.cartItem.credits;
        $scope.newGrading = $scope.$parent.cartItem.grading;
    }]);



