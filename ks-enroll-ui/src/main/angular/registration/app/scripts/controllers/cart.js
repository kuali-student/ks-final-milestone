'use strict';

angular.module('regCartApp')
    .controller('CartCtrl',
    function ($scope, $modal, CartService) {
        console.log('CartController!');
        console.log($scope.termId);
        $scope.oneAtATime = false;
        //Add a watch so that when termId changes, the cart is reloaded with the new termId
        $scope.$watch('termId', function (newValue) {
            console.log('term id has changed');
            if (newValue) {       // TODO: KSENROLL-11755: the first time the page is loaded, this is null. not sure why
                CartService.getCart().query({termId:newValue, userId:'admin'}, function (theCart) {
                    $scope.cart = theCart;
                });
            }
        });

        $scope.add = function () {
            addCourseToCart($scope.cart.cartId, $scope.courseCode, $scope.termId, $scope.regCode, null, null, null, null);
        };
        $scope.$on('addCourseToCart', function (event, cartId, courseCode, termId, regGroupCode, regGroupId, gradingOptionId, credits, userId) {
            console.log('receiveing event addCourseToCart', event);
            addCourseToCart(cartId, courseCode, termId, regGroupCode, regGroupId, gradingOptionId, credits, userId);
        });

        function addCourseToCart(cartId, courseCode, termId, regGroupCode, regGroupId, gradingOptionId, credits, userId) {
            CartService.addCourseToCart().query({
                cartId:cartId,
                courseCode:courseCode,
                termId:termId,
                regGroupCode:regGroupCode,
                regGroupId:regGroupId,
                gradingOptionId:gradingOptionId,
                credits:credits,
                userId:(userId ? userId : 'admin')
            }, function (response) {
                console.log('response: ' + JSON.stringify(response));
                console.log('Searched for course: ' + $scope.courseCode + ' Term: ' + $scope.termId);
                console.log('Added item:');
                $scope.userMessage = {txt:'Course Added Successfully', type:'success'};
                $scope.cart.items.unshift(response);

            }, function (error) {
                console.log('CartId:', cartId);
                if (error.status === 404) {
                    //Reg group was not found
                    $scope.userMessage = {txt:error.data, type:'error'};
                } else if (error.status === 400) {
                    console.log('CartId:', cartId);
                    //Additional options are required
                    $modal.open({
                        backdrop:'static',
                        templateUrl:'partials/additionalOptions.html',
                        resolve:{
                            item:function () {
                                return error.data;
                            },
                            cartId:function () {
                                return cartId;
                            }
                        },
                        controller:['$rootScope', '$scope', 'item', 'cartId', function ($rootScope, $scope, item, cartId) {
                            console.log('in controller');
                            console.log($scope);
                            console.log(item);
                            $scope.newCartItem = item;

                            $scope.dismiss = function () {
                                console.log('dismiss');
                                $scope.$close(true);
                            };

                            $scope.save = function () {
                                console.log('save', cartId);
                                $rootScope.$broadcast('addCourseToCart', cartId, $scope.newCartItem.courseCode, $scope.newCartItem.termId, $scope.newCartItem.regGroupCode, $scope.newCartItem.regGroupId, $scope.newCartItem.grading, $scope.newCartItem.credits, null);
                                $scope.$close(true);

                            };
                        }]
                    });
                } else {
                    console.log('Error with adding course', error);
                    $scope.userMessage = {txt:'There was an error processing your request', type:'error'};
                }

            });
        }

        $scope.cancelNew = function () {
            $scope.newCcartItem = null;
            $scope.showNew = false;
        };

        $scope.delete = function (index) {
            var item = $scope.cart.items[index];
            var actionLinks = item.actionLinks;
            var deleteUri;
            angular.forEach(actionLinks, function (actionLink) {
                if (actionLink.action === 'removeItemFromCart') {
                    deleteUri = actionLink.uri;
                }
            });

            // call the backend service here to persist something
            CartService.removeItemFromCart(deleteUri).query({},
                function (response) {
                    $scope.cart.items.splice(index, 1);

                    var actionUri;
                    angular.forEach(response.actionLinks, function (actionLink) {
                        if (actionLink.action === 'addCourseToCart') {
                            actionUri = actionLink.uri;
                        }
                    });

                    $scope.userMessage = {'txt':item.courseCode + '(' + item.regGroupCode + ') ' + 'has been successfully removed from your cart.   ',
                        'actionLink':actionUri,
                        'linkText':'Undo',
                        'type':'success'};
                    $scope.userActionSuccessful = true;
                });
        };

        $scope.invokeActionLink = function (actionLink) {
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
                $scope.userMessage = {txt:'Updated Successfully', type:'success'};
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
                    $scope.userMessage = {txt:'Cart was submitted.', type:'success'};
                });
            });
        };
    });



