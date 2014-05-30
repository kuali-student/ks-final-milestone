'use strict';

angular.module('regCartApp')
    .controller('CartCtrl',
    function ($scope, $modal, CartService, ScheduleService, GlobalVarsService, $timeout) {
        $scope.oneAtATime = false;
        $scope.isCollapsed = true;
        var hasCartBeenLoaded = false;
        $scope.cartResults = {items: []};

        //Add a watch so that when termId changes, the cart is reloaded with the new termId
        $scope.$watch('termId', function (newValue) {
            console.log('term id has changed');
            $scope.cartResults.items.splice(0, $scope.cartResults.items.length);
            if ($scope.userMessage && $scope.userMessage.txt) {
                $scope.removeUserMessage();
            }

            if (newValue) {
                hasCartBeenLoaded = true;
                loadCart(newValue);
            }
        });

        // Watch the cart items to ensure the global vars are up to date
        $scope.$watchCollection('cart.items', function(items) {
            if (items) {
                GlobalVarsService.setCartCourseCount(items.length);
                GlobalVarsService.setCartCredits(creditTotal());
            }
        });


        // this method loads the cart and kicks off polling if needed
        function loadCart(termId) {
            CartService.getCart().query({termId: termId}, function (theCart) {
                $scope.cart = theCart; // right now theCart is a mix of processing and cart items
                var cartItems = [];

                var startPolling = false; // prime to false
                var submittedCartId; // we must assume that the items are all from one cart

                // if there are any processing items in the cart we need to start polling
                for (var i = 0; i < $scope.cart.items.length; i++) {

                    var item = $scope.cart.items[i];
                    if (GlobalVarsService.getCorrespondingStatusFromState(item.state) === 'processing') {
                        item.status = 'processing';

                        //var newItem = $.extend(true, {}, item);
                        var newItem = angular.copy(item);
                        $scope.cartResults.items.push(newItem);
                        // set cart and all items in cart to processing
                        $scope.cartResults.state = 'kuali.lpr.trans.state.processing';
                        $scope.cartResults.status = 'processing';  // set the overall status to processing
                        startPolling = true;
                        submittedCartId = item.cartId;
                    } else {
                        cartItems.push(item);
                    }
                }

                $scope.cart.items = cartItems;
                if (startPolling) {
                    cartPoller(submittedCartId);  // each items has a reference back to the cartId
                }
            });
        }

        $scope.getStatusMessageFromStatus = function (status) {
            var retStatus = '';
            if (status === 'success') {
                retStatus = ' - Success!';
            }
            if (status === 'error' || status === 'action') {
                retStatus = ' - Failed!';
            }

            return retStatus;
        };

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
                $scope.userMessage = {txt: 'Course Added to Cart', type: 'success'};
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
                            $scope.newCartItem.credits = $scope.newCartItem.newCredits = $scope.newCartItem.creditOptions[0];
                            $scope.newCartItem.grading = $scope.newCartItem.newGrading = 'kuali.resultComponent.grade.letter';
                            $scope.newCartItem.editing = true;
                            $scope.dismissAdditionalOptions = function () {
                                console.log('Dismissing credits and grading');
                                $scope.$close(true);
                            };

                            $scope.saveAdditionalOptions = function (course) {
                                course.editing=false;
                                console.log('Save credits and grading for cartId:', cartId);
                                $rootScope.$broadcast('addCourseToCart', cartId, $scope.newCartItem.courseCode, $scope.newCartItem.termId, $scope.newCartItem.regGroupCode, $scope.newCartItem.regGroupId, $scope.newCartItem.newGrading, $scope.newCartItem.newCredits);
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
            $scope.newCartItem = null;
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
                        if (actionLink.action === 'undoDeleteCourse') {
                            actionUri = actionLink.uri;
                        }
                    });

                    $scope.userMessage = {'txt': 'Removed <b>' + item.courseCode + '(' + item.regGroupCode + ')</b>',
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
                    $scope.userMessage = {txt: ''};
                });
        };

        $scope.editCartItem = function (cartItem) {
            cartItem.newCredits = cartItem.credits;
            cartItem.newGrading = cartItem.grading;
            cartItem.status = 'editing';
            cartItem.editing = true;
        };

        $scope.updateCartItem = function (cartItem) {
            console.log('Updating cart item. Grading: ' + cartItem.newGrading + ', credits: ' + cartItem.newCredits);
            CartService.updateCartItem().query({
                cartId: $scope.cart.cartId,
                cartItemId: cartItem.cartItemId,
                credits: cartItem.newCredits,
                gradingOptionId: cartItem.newGrading
            }, function (newCartItem) {
                console.log('old: ' + cartItem.credits + ' To: ' + newCartItem.credits);
                console.log('old: ' + cartItem.grading + ' To: ' + newCartItem.grading);
                cartItem.credits = newCartItem.credits;
                cartItem.grading = newCartItem.grading;
                cartItem.status = '';
                cartItem.editing = false;
                cartItem.actionLinks = newCartItem.actionLinks;
                $scope.creditTotal = creditTotal();
                cartItem.alertMessage = {txt: 'Changes saved successfully', type: 'success'};
            });
        };

        $scope.addCartItemToWaitlist = function (cartItem) {
            console.log('Adding cart item to waitlist... ');
            ScheduleService.registerForRegistrationGroup().query({
                courseCode: cartItem.courseCode,
                regGroupId: cartItem.regGroupId,
                gradingOption: cartItem.grading,
                credits: cartItem.credits,
                allowWaitlist: true
            }, function (registrationResponseInfo) {
                cartItem.state = 'kuali.lpr.trans.item.state.processing';
                cartItem.status = 'processing';
                cartItem.cartItemId = registrationResponseInfo.registrationResponseItems[0].registrationRequestItemId;

                //cartItem.waitlistedStatus = true;
                //cartItem.statusMessage = GlobalVarsService.getCorrespondingMessageFromStatus('waitlisted');

                $timeout(function () {
                }, 250);    // delay for 250 milliseconds
                console.log('Just waited 250, now start the polling');
                cartPoller(registrationResponseInfo.registrationRequestId);
            });
        };

        $scope.removeAlertMessage = function (cartItem) {
            cartItem.alertMessage = null;
        };

        $scope.removeUserMessage = function () {
            $scope.userMessage.txt = null;
            $scope.userMessage.linkText = null;
        };

        $scope.register = function () {
            CartService.submitCart().query({
                cartId: $scope.cart.cartId
            }, function (registrationResponseInfo) {
                $scope.userMessage = {txt: ''};
                console.log('Submitted cart. RegReqId[' + registrationResponseInfo.registrationRequestId + ']');

                //$scope.cartResults = $.extend(true, {}, $scope.cart);
                $scope.cartResults = angular.copy($scope.cart);
                $scope.cart.items.splice(0, $scope.cart.items.length);

                // set cart and all items in cart to processing
                $scope.showConfirmation = false;
                $scope.cartResults.state = 'kuali.lpr.trans.state.processing';
                $scope.cartResults.status = 'processing';  // set the overall status to processing
                $scope.creditTotal = 0; // your cart will always update to zero upon submit
                angular.forEach($scope.cartResults.items, function (item) {
                    item.state = 'kuali.lpr.trans.item.state.processing';
                    item.status = 'processing';
                });
                $timeout(function () {
                }, 250);    // delay for 250 milliseconds
                console.log('Just waited 250, now start the polling');
                cartPoller(registrationResponseInfo.registrationRequestId);
            });
        };

        // This method is used to update the states/status of each cart item by polling the server
        var cartPoller = function (registrationRequestId) {
            $scope.pollingCart = false; // prime to false
            $timeout(function () {
                CartService.getRegistrationStatus().query({regReqId: registrationRequestId}, function (regResponseResult) {
                    $scope.cart.state = regResponseResult.state;
                    angular.forEach(regResponseResult.responseItemResults, function (responseItem) {
                        angular.forEach($scope.cartResults.items, function (item) {
                            if (item.cartItemId === responseItem.registrationRequestItemId) {
                                item.state = responseItem.state;
                                item.type = responseItem.type;
                                // we need to update the status, which is used to controll css
                                item.status = GlobalVarsService.getCorrespondingStatusFromState(responseItem.state);
                                item.statusMessage = responseItem.message;
                            }
                            // If anything is still processing, continue polling
                            if (responseItem.state === 'kuali.lpr.trans.item.state.processing') {
                                $scope.pollingCart = true;
                            }
                        });
                    });
                    if ($scope.pollingCart) {
                        console.log('Continue polling');
                        cartPoller(registrationRequestId);
                    } else {
                        console.log('Stop polling');
                        $scope.cart.status = '';  // set the overall status to nothing... which is the default i guess
                        $scope.cartResults.state = 'kuali.lpr.trans.state.succeeded';
                        $scope.cartResults.successCount = 0;
                        $scope.cartResults.waitlistCount = 0;
                        $scope.cartResults.errorCount = 0;
                        angular.forEach($scope.cartResults.items, function (item) {
                            switch (item.status) {
                                case 'success':
                                    $scope.cartResults.successCount++;
                                    break;
                                case 'waitlist':
                                    $scope.cartResults.waitlistCount++;
                                    item.statusMessage = GlobalVarsService.getCorrespondingMessageFromStatus(item.status);
                                    break;
                                case 'error':
                                    $scope.cartResults.errorCount++;
                                    break;
                                case 'action':
                                    // Waitlist action available, indicate as a Failure
                                    $scope.cartResults.errorCount++;
                                    break;
                            }
                        });
                        // After all the processing is complete, get the final Schedule counts.
                        ScheduleService.getScheduleFromServer().query({termId: $scope.termId }, function (result) {
                            console.log('called rest service to get schedule data - in main.js');
                            GlobalVarsService.updateScheduleCounts(result);
                            $scope.registeredCredits = GlobalVarsService.getRegisteredCredits;   // notice that i didn't put the (). in the ui call: {{registeredCredits()}}
                            $scope.registeredCourseCount = GlobalVarsService.getRegisteredCourseCount; // notice that i didn't put the (). in the ui call: {{registeredCourseCount()}}
                        });
                    }
                });
            }, 1000);  // right now we're going to wait 1 second per poll
        };

        $scope.removeCartResultItem = function (cartResultItem) {
            $scope.cartResults.items.splice(cartResultItem, 1);
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
            return cartItem.gradingOptions[cartItem.grading] !== 'Letter';
        };

        $scope.editing = function (cartItem) {
            return cartItem.status === 'editing';
        };

    });



