'use strict';

angular.module('regCartApp')
    .controller('CartCtrl', ['$scope', '$modal', '$timeout', 'STATE', 'STATUS', 'GRADING_OPTION', 'ACTION_LINK', 'COURSE_TYPES', 'CartService', 'ScheduleService', 'GlobalVarsService',
    function ($scope, $modal, $timeout, STATE, STATUS, GRADING_OPTION, ACTION_LINK, COURSE_TYPES, CartService, ScheduleService, GlobalVarsService) {
        $scope.states = STATE;
        $scope.statuses = STATUS;
        $scope.courseTypes = COURSE_TYPES;

        $scope.oneAtATime = false;
        $scope.isCollapsed = true;
        $scope.cartResults = {items: []};

        // Add a listener to the termIdChanged event so that when termId changes, the cart is reloaded with the new termId
        $scope.$on('termIdChanged', function (event, newValue) {
            console.log('term id has changed - cart: ' + newValue);
            $scope.cartResults.items.splice(0, $scope.cartResults.items.length);
            if ($scope.userMessage && $scope.userMessage.txt) {
                $scope.removeUserMessage();
            }

            loadCart(newValue);
        });

        // Watch the cart items to ensure the global vars are up to date
        $scope.$watchCollection('cart.items', function(items) {
            $scope.creditTotal = creditTotal();
            if (items) {
                GlobalVarsService.setCartCredits($scope.creditTotal);
            }

            GlobalVarsService.setCartCourses(items);
        });

        // this method loads the cart and kicks off polling if needed
        function loadCart(termId) {
            CartService.getCart(termId).then(function (theCart) {
                $scope.cart = theCart; // right now theCart is a mix of processing and cart items
                var cartItems = [],
                    submittedCartId = null; // we must assume that the items are all from one cart

                // if there are any processing items in the cart we need to start polling
                angular.forEach($scope.cart.items, function(item) {
                    // Standardize the fields between cart & scheduled courses. This should really be done on the REST side.
                    standardizeCourseData(item);


                    if (GlobalVarsService.getCorrespondingStatusFromState(item.state) === STATUS.processing) {
                        item.status = STATUS.processing;

                        var newItem = angular.copy(item);
                        $scope.cartResults.items.push(newItem);

                        // set cart and all items in cart to processing
                        $scope.cartResults.state = STATE.lpr.processing;
                        $scope.cartResults.status = STATUS.processing;  // set the overall status to processing

                        submittedCartId = item.cartId;
                    } else {
                        cartItems.push(item);
                    }
                });

                $scope.cart.items = cartItems;

                if (submittedCartId !== null) {
                    cartPoller(submittedCartId);  // each items has a reference back to the cartId
                }
            });
        }

        // Initialize the cart
        if ($scope.termId) {
            loadCart($scope.termId);
        }

        /*
         Returns the course index
         */
        $scope.courseIndex = function(course) {
            if (!angular.isDefined(course.index) || !course.index) {
                course.index = GlobalVarsService.getCourseIndex(course);
            }

            return course.index;
        };


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
            addCourseToCart($scope.cart.cartId, $scope.courseCode, $scope.termId, $scope.regCode, null, null, null);
        };

        $scope.$on('addRegGroupIdToCart', function (event, selectedRegGroupId) {
            addCourseToCart(null, null, null, null, selectedRegGroupId, null, null);
        });

        // Allows you to add a cartResultItem back into the cart. useful when a user wants to add a failed item back
        // into their cart.
        $scope.addCartItemToCart = function (cartItem) {
            addCourseToCart($scope.cart.cartId, null, $scope.termId, null, cartItem.regGroupId, cartItem.gradingOptionId, cartItem.credits);
        };

        $scope.$on('addCourseToCart', function (event, cartId, courseCode, termId, regGroupCode, regGroupId, gradingOptionId, credits) {
            console.log('Received event addCourseToCart ', event);
            addCourseToCart(cartId, courseCode, termId, regGroupCode, regGroupId, gradingOptionId, credits);
        });

        function addCourseToCart(cartId, courseCode, termId, regGroupCode, regGroupId, gradingOptionId, credits) {
            $scope.courseAdded = false; // reset cursor focus

            if (courseCode) {
                courseCode = courseCode.toUpperCase();
            }

            if (!cartId) {
                cartId = $scope.cart.cartId;
            }

            CartService.addCourseToCart().query({
                cartId: cartId,
                courseCode: courseCode,
                termId: termId,
                regGroupCode: regGroupCode,
                regGroupId: regGroupId,
                gradingOptionId: gradingOptionId,
                credits: credits
            }, function (response) {
                console.log('Searched for course: ' + $scope.courseCode + ' ' + $scope.regCode + ', Term: ' + $scope.termId);
                $scope.courseCode = '';
                $scope.regCode = '';

                standardizeCourseData(response);
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
                        controller: ['$scope', 'item', 'cartId', function ($scope, item, cartId) {
                            console.log('Controller for modal... Item: ', item);
                            $scope.newCartItem = item;

                            $scope.newCartItem.credits = $scope.newCartItem.newCredits = $scope.newCartItem.creditOptions[0];
                            $scope.newCartItem.gradingOptionId = $scope.newCartItem.newGrading = GRADING_OPTION.letter;
                            $scope.newCartItem.editing = true;

                            $scope.dismissAdditionalOptions = function () {
                                console.log('Dismissing credits and grading');
                                $scope.$close(true);
                            };

                            var submitted = false;
                            $scope.saveAdditionalOptions = function (course) {
                                if (!submitted) { // Only let the form be submitted once.
                                    submitted = true;
                                    course.editing = false;
                                    console.log('Save credits and grading for cartId:', cartId);
                                    addCourseToCart(cartId, $scope.newCartItem.courseCode, $scope.newCartItem.termId, $scope.newCartItem.regGroupCode, $scope.newCartItem.regGroupId, $scope.newCartItem.newGrading, $scope.newCartItem.newCredits);
                                    $scope.$close(true);
                                }
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
        $scope.$on('deleteCartItem', function (event, item) {
            var actionLinks = item.actionLinks,
                deleteUri = null;

            angular.forEach(actionLinks, function (actionLink) {
                if (actionLink.action === ACTION_LINK.removeItemFromCart) {
                    deleteUri = actionLink.uri;
                }
            });

            // call the backend service here to persist something
            CartService.removeItemFromCart(deleteUri).query({},
                function (response) {
                    console.log('Cart item removed', response);

                    $scope.cart.items.splice($scope.cart.items.indexOf(item), 1);

                    var actionUri = null;
                    angular.forEach(response.actionLinks, function (actionLink) {
                        if (actionLink.action === ACTION_LINK.undoDeleteCourse) {
                            actionUri = actionLink.uri;
                        }
                    });

                    $scope.userMessage = {
                        'txt': 'Removed <b>' + item.courseCode + '(' + item.regGroupCode + ')</b>',
                        'actionLink': actionUri,
                        'linkText': 'Undo',
                        'type': STATUS.success
                    };
                });
        });

        /*
         Listens for the "updateCourse" event and calls the cart update RESTful service.
         */
        $scope.$on('updateCourse', function(event, type, course, newCourse, successCallback, errorCallback) {
            if (type === COURSE_TYPES.cart) {
                console.log('Updating cart item');

                CartService.updateCartItem().query({
                    cartId: $scope.cart.cartId,
                    cartItemId: course.cartItemId,
                    credits: newCourse.credits,
                    gradingOptionId: newCourse.gradingOptionId
                }, function () {
                    console.log('- Cart item successfully updated');
                    // This perhaps should run through the poller...
                    if (angular.isFunction(successCallback)) {
                        successCallback();
                    }
                }, function(error) {
                    console.log('- Error updating cart item', error);
                    if (angular.isFunction(errorCallback)) {
                        errorCallback(error.genericMessage);
                    }
                });
            }
        });

        $scope.invokeActionLink = function (actionLink) {
            // call the backend service here to persist something
            CartService.invokeActionLink(actionLink).query({},
                function (response) {
                    standardizeCourseData(response);
                    $scope.cart.items.unshift(response);
                    $scope.removeUserMessage();
                });
        };

        $scope.addCartItemToWaitlist = function (cartItem) {
            console.log('Adding cart item to waitlist... ');
            ScheduleService.registerForRegistrationGroup().query({
                courseCode: cartItem.courseCode,
                regGroupId: cartItem.regGroupId,
                gradingOption: cartItem.gradingOptionId,
                credits: cartItem.credits,
                allowWaitlist: true
            }, function (registrationResponseInfo) {
                cartItem.state = STATE.lpr.item.processing;
                cartItem.status = STATUS.processing;
                cartItem.cartItemId = registrationResponseInfo.registrationRequestItems[0].id;

                $timeout(function () {
                    console.log('Just waited 250, now start the polling');
                    cartPoller(registrationResponseInfo.id);
                }, 250);
            });
        };

        $scope.removeAlertMessage = function (cartItem) {
            cartItem.alertMessage = null;
        };

        $scope.removeUserMessage = function () {
            if ($scope.userMessage) {
                $scope.userMessage.txt = null;
                $scope.userMessage.linkText = null;
            }
        };

        $scope.removeCartResultItem = function (cartResultItem) {
            $scope.cartResults.items.splice(cartResultItem, 1);
            calculateCartResultCounts();
        };

        $scope.register = function () {
            CartService.submitCart().query({
                cartId: $scope.cart.cartId
            }, function (registrationResponseInfo) {
                console.log('Submitted cart. RegReqId[' + registrationResponseInfo.id + ']');

                $scope.removeUserMessage();

                // Move all of the cart over to the cartResults
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
                    console.log('Just waited 250, now start the polling');
                    cartPoller(registrationResponseInfo.id);
                }, 250);
            });
        };

        // This method is used to update the STATE/status of each cart item by polling the server
        function cartPoller(registrationRequestId) {
            ScheduleService.pollRegistrationRequestStatus(registrationRequestId)
                .then(function(response) { // Success
                    console.log('- Stop polling - Success');

                    $scope.cart.state = response.state;
                    $scope.cart.status = '';  // set the overall status to nothing... which is the default i guess
                    $scope.cartResults.state = STATE.lpr.item.succeeded;

                    // Map the data from the responseItems to the cartResultItems
                    angular.forEach(response.responseItemResults, function(responseItem) {
                        angular.forEach($scope.cartResults.items, function (cartResultItem) {
                            if (cartResultItem.cartItemId === responseItem.registrationRequestItemId) {
                                cartResultItem.state = responseItem.state;
                                cartResultItem.type = responseItem.type;
                                // we need to update the status, which is used to control css
                                cartResultItem.status = GlobalVarsService.getCorrespondingStatusFromState(responseItem.state);
                                cartResultItem.statusMessages = responseItem.messages;

                                // Apply the waitlist message if the result was of that type
                                switch (cartResultItem.status) {
                                    case STATUS.waitlist:
                                    case STATUS.action: //waitlist action available
                                        cartResultItem.waitlistMessage = GlobalVarsService.getCorrespondingMessageFromStatus(cartResultItem.status);
                                        break;
                                }
                            }
                        });
                    });

                    // Calculate the result counts per status (clearing out initially to trigger the view to reset the values)
                    calculateCartResultCounts(true);

                    // After all the processing is complete, get the final Schedule counts.
                    ScheduleService.getSchedule($scope.termId, true).then(function (result) {
                        console.log('called rest service to get schedule data - in cart.js');
                        GlobalVarsService.updateScheduleCounts(result);
                        $scope.registeredCredits = GlobalVarsService.getRegisteredCredits;   // notice that i didn't put the (). in the ui call: {{registeredCredits()}}
                        $scope.registeredCourseCount = GlobalVarsService.getRegisteredCourseCount; // notice that i didn't put the (). in the ui call: {{registeredCourseCount()}}
                    });

                }, function(response) { // Error
                    console.log('- Stop polling - Error: ', response);
                }, function(response) { // Notify
                    console.log('- Continue polling');
                    $scope.cart.state = response.state;
                });
        }

        /**
         * Calculate the counts of the cart results
         */
        function calculateCartResultCounts(resetValues) {
            if (resetValues) {
                $scope.cartResults.successCount = 0;
                $scope.cartResults.waitlistCount = 0;
                $scope.cartResults.waitlistedCount = 0;
                $scope.cartResults.errorCount = 0;
            }

            // Store as local variables so the $scope vars don't fire change events on increments
            var success = 0,
                waitlist = 0,
                waitlisted = 0,
                error = 0;

            angular.forEach($scope.cartResults.items, function (item) {
                switch (item.status) {
                    case STATUS.success:
                        success++;
                        break;
                    case STATUS.waitlist: // Waitlist action successful
                        success++; // Also increment the successes
                        waitlisted++;
                        break;
                    case STATUS.action: // Waitlist action available
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
            $scope.cartResults.waitlistedCount = waitlisted;
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

        function standardizeCourseData(course) {
            // Standardize the fields between cart & scheduled courses. This should really be done on the REST side.
            course.longName = course.courseTitle;
            course.gradingOptionId = course.grading;
        }

    }]);