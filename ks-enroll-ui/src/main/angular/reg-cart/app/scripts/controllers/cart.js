'use strict';

angular.module('regCartApp')
    .controller('CartCtrl', ['$scope', 'CartService',
    function ($scope, CartService) {
        $scope.cart = CartService.getCart().query({termCode:'Fall2012'});
    }]);
