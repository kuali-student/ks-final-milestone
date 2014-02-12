'use strict';


var cartServiceModule = angular.module('regCartApp');

cartServiceModule.controller('CartCtrl', ['$scope', '$state', '$modal', 'CartService',
    function ($scope, $state, $modal, CartService) {

        //Add a watch so that when termId changes, the cart is reloaded with the new termId
        $scope.$watch('termId', function (newValue) {
            if (newValue) {       // TODO: KSENROLL-11755: the first time the page is loaded, this is null. not sure why
                CartService.getCart().query({termId:newValue, userId:'admin'}, function (theCart) {
                    $scope.cart = theCart;
                });
            }
        });

        //$scope.add = function() {
        //    $scope.cart.items.unshift($scope.newCartItem);
        //
        //    $scope.newCartItem = null;
        //    $scope.showNew = false;
        //
        //};

        $scope.add = function () {

            CartService.addCourseToCart().query({cartId:$scope.cart.cartId,
                courseCode:$scope.courseCode,
                termId:$scope.termId,
                regGroupCode:$scope.regCode,
                gradingMethod:'',
                credits:''
            }, function (response) {
                console.log('response: ' + JSON.stringify(response));
                console.log('Searched for course: ' + $scope.courseCode + ' Term: ' + $scope.termId);
                console.log('Added item:');

                $scope.cart.items.unshift(response);
//                if (!addedItem.cartItemId) {
//
////                    console.log('No itemId so finding options');
////                    return $state.transitionTo('root.cart.options');
//                    $modal.open({
//                        backdrop:'static',
//                        templateUrl:'partials/additionalOptions.html',
//                        resolve:{item:function () {
//                            return addedItem;
//                        }},
//                        controller:['$scope', 'item', function ($scope, item) {
//                            console.log('in controller');
//                            console.log($scope);
//                            console.log(item);
//                            $scope.addedItem = item;
//                            $scope.dismiss = function () {
//                                console.log('dismiss');
//                                $scope.$close(true);
//                            };
//
//                            $scope.save = function () {
//                                console.log('save');
//                                $scope.$close(true);
//                            };
//                        }]
//                    }).result.then(function (result) {
//                            console.log('before result transition');
//                            if (result) {
//                                console.log('transition');
//                            }
//                        });
//                } else {
//
            })
        };


        /*
         $scope.search = function() {

         var code = $scope.courseCode;
         //var regGroup = $scope.regCode;

         $scope.newCartItem = CartService.getGradingOptions().query();

         if($scope.newCartItem === null) {
         $scope.error = 'Cannot find the course "' + code + '" in term ' + $scope.termId;
         $scope.courseCode.setValidity('coursecheck', false);
         $scope.showNew = false;
         } else {
         $scope.showNew = true;
         }
         };
         */

        $scope.cancelNew = function () {
            $scope.newCcartItem = null;
            $scope.showNew = false;
        };

        $scope.delete = function (index) {
            var item = $scope.cart.items[index];
            var actionLinks = item.actionLinks;
            var deleteUri;
            angular.forEach(actionLinks, function (actionLink) {
                if (actionLink.action == 'removeItemFromCart') {
                    deleteUri = actionLink.uri;
                }
            });

            // call the backend service here to persist something
            CartService.removeItemFromCart(deleteUri).query({},
                function (response) {
                    $scope.cart.items.splice(index, 1);

                    var actionUri;
                    angular.forEach(response.actionLinks, function(actionLink){
                        if(actionLink.action == 'addCourseToCart'){
                            actionUri = actionLink.uri;
                        }
                    });

                    $scope.userMessage = {'txt' : item.courseCode + '(' + item.regGroupCode + ') ' + 'has been successfully removed from your cart.   ',
                                            'actionLink' : actionUri,
                                            'linkText' : 'Undo'};
                    $scope.userActionSuccessful = true;
            });
        };

        $scope.invokeActionLink = function(actionLink) {
            $scope.userActionSuccessful = false;
            // call the backend service here to persist something
            CartService.invokeActionLink(actionLink).query({},
                function (response) {
                    $scope.cart.items.unshift(response);
                });
        };

        $scope.editItem = function (cartItem) {
            $scope.newCredits = cartItem.credits;
            $scope.newGrading = cartItem.grading;
            cartItem.editing = true;
        };

        $scope.cancelEditItem = function (cartItem) {
            cartItem.editing = false;
        };

        $scope.updateCartItem = function (cartItem, newCredits, newGrading) {
            console.log('Updating:');
            console.log(newGrading);
            CartService.updateCartItem().query({
                cartId:$scope.cart.cartId,
                cartItemId:cartItem.cartItemId,
                credits:newCredits,
                grading:newGrading,
                userId:'admin'
            }, function (newCartItem) {
                console.log($scope);
                cartItem.credits = newCartItem.credits;
                cartItem.grading = newCartItem.grading;
                cartItem.editing = false;
            });

        };

        $scope.register = function () {
            CartService.submitCart().query({
                cartId:$scope.cart.cartId,
                userId:'admin'
            }, function () {
                console.log('Submiting cart.');
                CartService.getCart().query({termId:$scope.termId, userId:'admin'}, function (theCart) {
                    $scope.cart = theCart;
                });
            });
        };
    }]);



