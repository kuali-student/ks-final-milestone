'use strict';

angular.module('regCartApp')
    .controller('CartCtrl', ['$scope', '$modal', '$timeout', 'STATE', 'STATUS', 'GRADING_OPTION', 'ACTION_LINK', 'CartService', 'ScheduleService', 'GlobalVarsService',
    function ($scope, $modal, $timeout, STATE, STATUS, GRADING_OPTION, ACTION_LINK, CartService, ScheduleService, GlobalVarsService) {
        $scope.statuses = STATUS;
        $scope.oneAtATime = false;
        $scope.isCollapsed = true;
        var hasCartBeenLoaded = false;
        $scope.cartResults = {items: []};

        // Add a listener to the termIdChanged event so that when termId changes, the cart is reloaded with the new termId
        $scope.$on('termIdChanged', function (event, newValue) {
            console.log('term id has changed - cart: ' + newValue);
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
            $scope.creditTotal = creditTotal();
            if (items) {
                GlobalVarsService.setCartCourseCount(items.length);
                GlobalVarsService.setCartCredits($scope.creditTotal);
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
                    if (GlobalVarsService.getCorrespondingStatusFromState(item.state) === STATUS.processing) {
                        item.status = STATUS.processing;

                        //var newItem = $.extend(true, {}, item);
                        var newItem = angular.copy(item);
                        $scope.cartResults.items.push(newItem);
                        // set cart and all items in cart to processing
                        $scope.cartResults.state = STATE.lpr.processing;
                        $scope.cartResults.status = STATUS.processing;  // set the overall status to processing
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
            if (status === STATUS.success) {
                retStatus = ' - Success!';
            } else if (status === STATUS.error || status === STATUS.action) {
                retStatus = ' - Failed';
            }

            return retStatus;
        };

        $scope.addRegGroupToCart = function () {
            $scope.courseCode = $scope.courseCode.toUpperCase();
            addCourseToCart($scope.cart.cartId, $scope.courseCode, $scope.termId, $scope.regCode, null, null, null);
        };

        // Allows you to add a cartResultItem back into the cart. useful when a user wants to add a failed item back
        // into their cart.
        $scope.addCartItemToCart = function (cartItem) {
            addCourseToCart($scope.cart.cartId, null, $scope.termId, null, cartItem.regGroupId, cartItem.grading, cartItem.credits);
        };

        $scope.$on('addCourseToCart', function (event, cartId, courseCode, termId, regGroupCode, regGroupId, gradingOptionId, credits) {
            console.log('Received event addCourseToCart ', event);
            addCourseToCart(cartId, courseCode, termId, regGroupCode, regGroupId, gradingOptionId, credits);
        });

        function addCourseToCart(cartId, courseCode, termId, regGroupCode, regGroupId, gradingOptionId, credits) {
            $scope.courseAdded = false; // reset cursor focus
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
//                $scope.userMessage = {txt: 'Course Added to Cart', type: 'success'};
                $scope.courseCode = '';
                $scope.regCode = '';
                $scope.cart.items.unshift(response);
                // This part is responsible for glow effect: when the new item is added we want to highlight it and then fade the highlight away after 2 secs
                console.log('Started to glow...');
                // the highlighting fades in
                response.addingNewCartItem = true;
                // the highlighting stays for 2 secs and fades out
                $timeout(function(){
                    response.addingNewCartItem = false;
                }, 2000);
                $scope.courseAdded = true; // refocus cursor back to course code
            }, function (error) {
                console.log('CartId:', cartId);
                if (error.status === 404) {
                    //Reg group was not found
                    $scope.userMessage = {txt: error.data, type: STATUS.error, course: courseCode};
                    $scope.courseAdded = true;  // refocus cursor back to course code
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
                            $scope.newCartItem.grading = $scope.newCartItem.newGrading = GRADING_OPTION.letter;
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
                    $scope.courseAdded = true; // refocus cursor back to course code
                } else {
                    console.log('Error with adding course', error.data.consoleMessage);
                    //Reg group is not in offered state
                    $scope.userMessage = {txt: error.data.genericMessage, type: error.data.type, detail: error.data.detailedMessage, course: courseCode + ' (' + regGroupCode + ')'};
                    $scope.courseAdded = true; // refocus cursor back to course code
                }
            });
        }

        $scope.cancelNewCartItem = function () {
            $scope.newCartItem = null;
            $scope.showNew = false;
        };

        /*
        Listens for the "deleteCartItem" event and calls the cart service to
        remove the given cart item from the cart.
         */
        $scope.$on('deleteCartItem', function (event, index) {
            var item = $scope.cart.items[index];
            var actionLinks = item.actionLinks;
            var deleteUri = null;
            angular.forEach(actionLinks, function (actionLink) {
                if (actionLink.action === ACTION_LINK.removeItemFromCart) {
                    deleteUri = actionLink.uri;
                }
            });

            // call the backend service here to persist something
            CartService.removeItemFromCart(deleteUri).query({},
                function (response) {
                    $scope.cart.items.splice(index, 1);

                    var actionUri = null;
                    angular.forEach(response.actionLinks, function (actionLink) {
                        if (actionLink.action === ACTION_LINK.undoDeleteCourse) {
                            actionUri = actionLink.uri;
                        }
                    });

                    $scope.userMessage = {'txt': 'Removed <b>' + item.courseCode + '(' + item.regGroupCode + ')</b>',
                        'actionLink': actionUri,
                        'linkText': 'Undo',
                        'type': STATUS.success};
                    $scope.userActionSuccessful = true;
                });
        });

        $scope.invokeActionLink = function (actionLink) {
            $scope.userActionSuccessful = false;
            // call the backend service here to persist something
            CartService.invokeActionLink(actionLink).query({},
                function (response) {
                    $scope.cart.items.unshift(response);
                    $scope.userMessage = {txt: ''};
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
                cartItem.state = STATE.lpr.item.processing;
                cartItem.status = STATUS.processing;
                cartItem.cartItemId = registrationResponseInfo.registrationRequestItems[0].id;

                //cartItem.waitlistedStatus = true;
                //cartItem.statusMessage = GlobalVarsService.getCorrespondingMessageFromStatus('waitlisted');

                $timeout(function () {
                }, 250);    // delay for 250 milliseconds
                console.log('Just waited 250, now start the polling');
                cartPoller(registrationResponseInfo.id);
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
                console.log('Submitted cart. RegReqId[' + registrationResponseInfo.id + ']');

                //$scope.cartResults = $.extend(true, {}, $scope.cart);
                $scope.cartResults = angular.copy($scope.cart);
                $scope.cart.items.splice(0, $scope.cart.items.length);

                // set cart and all items in cart to processing
                $scope.showConfirmation = false;
                $scope.cartResults.state = STATE.lpr.processing;
                $scope.cartResults.status = STATUS.processing;  // set the overall status to processing
                $scope.creditTotal = 0; // your cart will always update to zero upon submit
                angular.forEach($scope.cartResults.items, function (item) {
                    item.state = STATE.lpr.item.processing;
                    item.status = STATUS.processing;
                });
                $timeout(function () {
                }, 250);    // delay for 250 milliseconds
                console.log('Just waited 250, now start the polling');
                cartPoller(registrationResponseInfo.id);
            });
        };

        // This method is used to update the STATE/status of each cart item by polling the server
        var cartPoller = function (registrationRequestId) {
            $scope.pollingCart = false; // prime to false
            $timeout(function () {
                ScheduleService.getRegistrationStatus().query({regReqId: registrationRequestId}, function (regResponseResult) {
                    $scope.cart.state = regResponseResult.state;
                    angular.forEach(regResponseResult.responseItemResults, function (responseItem) {
                        angular.forEach($scope.cartResults.items, function (item) {
                            if (item.cartItemId === responseItem.registrationRequestItemId) {
                                item.state = responseItem.state;
                                item.type = responseItem.type;
                                // we need to update the status, which is used to controll css
                                item.status = GlobalVarsService.getCorrespondingStatusFromState(responseItem.state);
                                item.statusMessages = responseItem.messages;
                            }
                            // If anything is still processing, continue polling
                            if (item.status === STATUS.processing) {
                                $scope.pollingCart = true;
                            }
                        });
                    });
                    if ($scope.pollingCart) {
                        console.log('Continue polling');
                        /*
                        Observation: this can cause an infinite loop if the server is not responding. This process
                        should probably timeout after a certain amount of time and/or number of tries.
                         */
                        cartPoller(registrationRequestId);
                    } else {
                        console.log('Stop polling');
                        $scope.cart.status = '';  // set the overall status to nothing... which is the default i guess
                        $scope.cartResults.state = STATE.lpr.item.succeeded;

                        // Calculate the result counts per status (clearing out initially to trigger the view to reset the values)
                        $scope.cartResults.successCount = 0;
                        $scope.cartResults.waitlistCount = 0;
                        $scope.cartResults.errorCount = 0;
                        calculateCartResultCounts();

                        angular.forEach($scope.cartResults.items, function (item) {
                            switch (item.status) {
                                case STATUS.waitlist:
                                case STATUS.action: //waitlist action available
                                    item.waitlistMessage = GlobalVarsService.getCorrespondingMessageFromStatus(item.status);
                                    break;
                            }
                        });

                        // After all the processing is complete, get the final Schedule counts.
                        ScheduleService.getScheduleFromServer().query({termId: $scope.termId }, function (result) {
                            console.log('called rest service to get schedule data - in cart.js');
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
            calculateCartResultCounts();
        };

        /**
         * Calculate the counts of the cart results
         */
        function calculateCartResultCounts() {
            // Store as local variables so the $scope vars don't fire change events on increments
            var success = 0,
                waitlist = 0,
                error = 0;

            angular.forEach($scope.cartResults.items, function (item) {
                switch (item.status) {
                    case STATUS.success:
                        success++;
                        break;
                    case STATUS.waitlist:
                    case STATUS.action: //waitlist action available
                        waitlist++;
                        break;
                    case STATUS.error:
                        error++;
                        break;
                }
            });

            // Set the counts into the scope
            $scope.cartResults.successCount = success;
            $scope.cartResults.waitlistCount = waitlist;
            $scope.cartResults.errorCount = error;
        }

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

    }]);