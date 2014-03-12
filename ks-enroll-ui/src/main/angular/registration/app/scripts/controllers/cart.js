'use strict';

angular.module('regCartApp')
    .controller('CartCtrl',
    function ($scope, $modal, CartService, ScheduleService, GlobalVarsService) {
        $scope.oneAtATime = false;
        //Add a watch so that when termId changes, the cart is reloaded with the new termId
        $scope.$watch('termId', function (newValue) {
            console.log('term id has changed');
            if (newValue) {
                CartService.getCart().query({termId: newValue}, function (theCart) {
                    $scope.cart = theCart;
                });
            }
        });

        $scope.addRegGroupToCart = function () {
            $scope.courseCode = $scope.courseCode.toUpperCase();
            addCourseToCart($scope.cart.cartId, $scope.courseCode, $scope.termId, $scope.regCode, null, null, null, null);
        };

        $scope.$on('addCourseToCart', function (event, cartId, courseCode, termId, regGroupCode, regGroupId, gradingOptionId, credits) {
            console.log('Received event addCourseToCart ', event);
            addCourseToCart(cartId, courseCode, termId, regGroupCode, regGroupId, gradingOptionId, credits);
        });

        function addCourseToCart(cartId, courseCode, termId, regGroupCode, regGroupId, gradingOptionId, credits) {
            CartService.addCourseToCart().query({
                cartId: cartId,
                courseCode: courseCode,
                termId: termId,
                regGroupCode: regGroupCode,
                regGroupId: regGroupId,
                gradingOptionId: gradingOptionId,
                credits: credits
            }, function (response) {
                console.log('Searched for course: ' + $scope.courseCode + ', Term: ' + $scope.termId);
                $scope.userMessage = {txt: 'Course Added Successfully', type: 'success'};
                $scope.courseCode = '';
                $scope.regCode = '';
                $scope.cart.items.unshift(response);
            }, function (error) {
                console.log('CartId:', cartId);
                if (error.status === 404) {
                    //Reg group was not found
                    $scope.userMessage = {txt: error.data, type: 'error'};
                } else if (error.status === 400) {
                    console.log('CartId: ', cartId);
                    //Additional options are required
                    $modal.open({
                        backdrop: 'static',
                        templateUrl: 'partials/additionalOptions.html',
                        resolve: {
                            item: function () {
                                return error.data;
                            },
                            cartId: function () {
                                return cartId;
                            }
                        },
                        controller: ['$rootScope', '$scope', 'item', 'cartId', function ($rootScope, $scope, item, cartId) {
                            console.log('Controller for modal... Item: ', item);
                            $scope.newCartItem = item;
                            $scope.newCartItem.credits = $scope.newCartItem.creditOptions[0];
                            $scope.newCartItem.grading = 'kuali.resultComponent.grade.letter';
                            $scope.dismissAdditionalOptions = function () {
                                console.log('Dismissing credits and grading');
                                $scope.$close(true);
                            };

                            $scope.saveAdditionalOptions = function () {
                                console.log('Save credits and grading for cartId:', cartId);
                                $rootScope.$broadcast('addCourseToCart', cartId, $scope.newCartItem.courseCode, $scope.newCartItem.termId, $scope.newCartItem.regGroupCode, $scope.newCartItem.regGroupId, $scope.newCartItem.grading, $scope.newCartItem.credits);
                                $scope.$close(true);

                            };
                        }]
                    });
                } else {
                    console.log('Error with adding course', error.data.consoleMessage);
                    $scope.userMessage = {txt: error.data.genericMessage, type: error.data.type, detail: error.data.detailedMessage};
                }

            });
        }

        $scope.cancelNewCartItem = function () {
            $scope.newCcartItem = null;
            $scope.showNew = false;
        };

        $scope.deleteCartItem = function (index) {
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

                    $scope.userMessage = {'txt': item.courseCode + '(' + item.regGroupCode + ') ' + 'has been successfully removed from your cart.',
                        'actionLink': actionUri,
                        'linkText': 'Undo',
                        'type': 'success'};
                    $scope.userActionSuccessful = true;
                });
        };

        $scope.invokeActionLink = function (actionLink) {
            $scope.userActionSuccessful = false;
            // call the backend service here to persist something
            CartService.invokeActionLink(actionLink).query({},
                function (response) {
                    $scope.cart.items.unshift(response);
                    $scope.userMessage.txt = '';
                });
        };

        $scope.editCartItem = function (cartItem) {
            $scope.newCredits = cartItem.credits;
            $scope.newGrading = cartItem.grading;
            cartItem.editing = true;
        };

        $scope.cancelEditItem = function (cartItem) {
            cartItem.editing = false;
        };

        $scope.updateCartItem = function (cartItem, newCredits, newGrading) {
            console.log('Updating cart item. Grading: ' + newGrading + ', credits: ' + newCredits);
            CartService.updateCartItem().query({
                cartId: $scope.cart.cartId,
                cartItemId: cartItem.cartItemId,
                credits: newCredits,
                grading: newGrading
            }, function (newCartItem) {
                console.log($scope);
                console.log(JSON.stringify(newCartItem));
                cartItem.credits = newCartItem.credits;
                console.log('old: ' + cartItem.grading + ' To: ' + newCartItem.grading);
                cartItem.grading = newCartItem.grading;
                console.log('old: ' + cartItem.grading + ' To: ' + newCartItem.grading);
                cartItem.editing = false;
                cartItem.actionLinks = newCartItem.actionLinks;
                $scope.userMessage = {txt: 'Updated Successfully', type: 'success'};
            });

        };

        $scope.register = function () {

            // We are updating the counts here, but in the future we will be polling for the updates.
            var currentCartCreditCount = creditTotal();
            var currentCartCourseCount = $scope.cart.items.length;
            var currentScheduleCreditCount = GlobalVarsService.getRegisteredCredits();
            var currentScheduleCourseCount = GlobalVarsService.getRegisteredCourseCount();

            CartService.submitCart().query({
                cartId: $scope.cart.cartId
            }, function () {
                console.log('Submiting cart.');
                CartService.getCart().query({termId: $scope.termId}, function (theCart) {
                    $scope.cart = theCart;
                    $scope.userMessage = {txt: 'Cart was submitted.', type: 'success'};
                    GlobalVarsService.setRegisteredCourseCount(currentCartCourseCount + currentScheduleCourseCount);
                    GlobalVarsService.setRegisteredCredits(currentCartCreditCount + currentScheduleCreditCount);
                });
            });
        };

        function creditTotal() {
            if (!$scope.cart) {
                return 0;
            }
            var totalNumber = 0;
            for (var i = 0; i < $scope.cart.items.length; i++) {
                totalNumber = totalNumber + Number($scope.cart.items[i].credits);
            }

            return totalNumber;
        }

        $scope.$watchCollection('cart.items', function () {
            $scope.creditTotal = creditTotal();
        });

        $scope.showBadge = function (cartItem) {
            //console.log("Cart Item Grading: " + JSON.stringify(cartItem));
            return cartItem.gradingOptions[cartItem.grading] != 'Letter';
        };

    });



