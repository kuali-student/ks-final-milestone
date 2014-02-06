'use strict';


angular.module('regCartApp').controller('CartItemCtrl', ['$scope',
    function ($scope) {
        //Default values
        $scope.newCredits = $scope.$parent.cartItem.credits;
        $scope.newGrading = $scope.$parent.cartItem.grading;

        $scope.cancel = function(){
            $scope.newCredits = $scope.$parent.cartItem.credits;
            $scope.newGrading = $scope.$parent.cartItem.grading;
            $scope.$parent.editing = false;
        };

        $scope.submit = function(){

            $scope.$parent.editing = false;

            $scope.$parent.cartItem.credits = $scope.newCredits;
            $scope.$parent.cartItem.grading = $scope.newGrading;

        };

    }]);



