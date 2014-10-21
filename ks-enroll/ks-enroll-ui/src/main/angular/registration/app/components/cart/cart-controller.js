'use strict';

/*
 * Controller for the cart functionality
 *
 * Event Handling
 * -- Emits: none
 * -- Broadcasts: none
 * -- Receives: "termIdChanged" -- received from the main-controller.js, updates cart when term Id is changed
 *              "addCourseToCart" -- received from helper method in course-details-controller.js, adds course to cart
 *              "registerForCourse" -- received from direct-register-controller.js, registers course from cart to schedule
 *              "directRegisterForCourse" -- received from helper method in course-details-controller.js, launches direct register modal wizard
 *              "deleteCartItem" -- received from the course card directive, removes course from cart
 *              "updateCourse" -- received from course-card-directive.js, updates course (edit credits, grading option, etc)
 */
angular.module('regCartApp')
    .controller('CartCtrl', ['$scope', '$modal', '$timeout', '$q', 'STATE', 'STATUS', 'GRADING_OPTION', 'ACTION_LINK',
        'COURSE_TYPES', 'GENERAL_ERROR_TYPE', 'VALIDATION_ERROR_TYPE', 'GlobalVarsService', 'MessageService',
        'TermsService', 'CartService', 'ScheduleService', 'RegUtil',
    function CartCtrl($scope, $modal, $timeout, $q, STATE, STATUS, GRADING_OPTION, ACTION_LINK, COURSE_TYPES,
              GENERAL_ERROR_TYPE, VALIDATION_ERROR_TYPE, GlobalVarsService, MessageService, TermsService, CartService,
              ScheduleService, RegUtil) {
        console.log('>> CartCtrl');

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
                CartService.setCartCredits($scope.creditTotal);
            }

            CartService.setCartCourses(items);
        });

        // this method loads the cart and kicks off polling if needed
        function loadCart(termId) {
            CartService.getCart(termId).then(function(cart) {
                $scope.cart = cart; // right now theCart is a mix of processing and cart items
                var cartItems = [],
                    hasProcessing = false;

                for (var i = 0; i < cart.items.length; i++) {
                    var item = cart.items[i];

                    // Standardize the fields between cart & scheduled courses. This should really be done on the REST side.
                    standardizeCourseData(item);

                    // if there are any processing items in the cart we need to start polling
                    if (GlobalVarsService.getCorrespondingStatusFromState(item.state) === STATUS.processing) {
                        item.status = STATUS.processing;

                        var newItem = angular.copy(item);
                        $scope.cartResults.items.push(newItem);

                        // set cart and all items in cart to processing
                        $scope.cartResults.state = STATE.lpr.processing;
                        $scope.cartResults.status = STATUS.processing;  // set the overall status to processing

                        hasProcessing = true;
                    } else {
                        cartItems.push(item);
                    }
                }

                $scope.cart.items = cartItems;

                if (hasProcessing) {
                    var poller = CartService.pollForCartUpdates(cart);
                    handlePoller(poller);
                }
            });
        }

        // Initialize the cart
        if (TermsService.getTermId()) {
            loadCart(TermsService.getTermId());
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
            addCourseToCart({
                courseCode: $scope.courseCode,
                regGroupCode: $scope.regCode
            });
        };

        // Listens for the "addCourseToCart" event and adds the course to the cart.
        $scope.$on('addCourseToCart', function (event, course) {
            event.promise = addCourseToCart(course);
        });

        // Listens for the "addCourseToCart" event and adds the course to the cart.
        $scope.$on('addCoursesToCart', function (event, courses) {
            event.promise = addCoursesToCart(courses);
        });

        // Listens for the "registerForCourse" event and registers the course.
        $scope.$on('registerForCourse', function(event, course) {
            event.promise = registerForCourse(course);
        });

        // Listens for the "directRegisterForCourse" event and launches the direct register modal wizard
        $scope.$on('directRegisterForCourse', function(event, course) {
            var modal = $modal.open({
                backdrop: 'static',
                controller: 'DirectRegisterCtrl',
                windowClass: 'kscr-DirectRegister-window',
                templateUrl: 'components/directregister/directRegister.html',
                size: 'sm',
                resolve: {
                    course: function () {
                        return course;
                    }
                }
            });

            event.promise = modal.result;
        });


        // Allows you to add a cartItem back into the cart.
        // Useful when a user wants to add a failed item back into their cart.
        $scope.addCartItemToCart = function (cartItem) {
            addCourseToCart(cartItem);
        };

        function addCourseToCart(course) {
            // Clear out the last cart items and user message if they exist (from Clear Cart)
            if ($scope.lastCartItems) {
                $scope.lastCartItems = null;
            }

            $scope.courseAdded = false; // reset cursor focus
            $scope.addToCartStatus = STATUS.processing;

            return CartService.addCourseToCart(TermsService.getTermId(), course)
                .then(handleAddCourseToCartResponse, handleAddCourseToCartErrorResponse)
                .then(function(newCourse) {
                    $scope.courseCode = '';
                    $scope.regCode = '';

                    // This part is responsible for glow effect: when the new item is added we want to highlight it and then fade the highlight away after 2 secs
                    newCourse.addingNewCartItem = true; // the highlighting fades in
                    $timeout(function(){
                        newCourse.addingNewCartItem = false;
                    }, 2000); // the highlighting stays for 2 secs and fades out

                    $scope.addToCartStatus = STATUS.success;
                    $scope.removeUserMessage();

                    return newCourse;
                }, function(reason) {
                    // For now, we only care about 1 reason
                    if (angular.isArray(reason)) {
                        reason = reason.pop();
                    }

                    if (angular.isObject(reason)) {
                        // Transform the UserMessage into what the UI is expecting
                        $scope.userMessage = createUserMessageFromServerMessage(reason, course);

                        if (angular.isDefined(reason.consoleMessage) && reason.consoleMessage) {
                            console.log('Error adding course to cart', reason.consoleMessage);
                        }

                        $scope.addToCartStatus = STATUS.error;
                    }

                    return reason;
                })
                .finally(function() { // This gets called at the very end regardless of what the outcome was (success || failure)
                    $scope.courseAdded = true; // refocus cursor back to course code

                    if ($scope.addToCartStatus === STATUS.processing) {
                        $scope.addToCartStatus = null;
                    }
                });
        }

        function addCoursesToCart(courses) {
            // Clear out the last cart items and user message if they exist (from Clear Cart)
            if ($scope.lastCartItems) {
                $scope.lastCartItems = null;
            }

            $scope.addToCartStatus = STATUS.processing;


            return CartService.addCoursesToCart(TermsService.getTermId(), courses)
                .then(function(responses) {
                    // This is a recursive function that will iterate through the responses and handle them 1 at a time
                    // This prevents multiple 'Show Additional Options' dialogs from being shown concurrently
                    function handleResponses() {
                        var response = responses.pop(),
                            promise;

                        if (response.state === STATE.addToCart.success) {
                            promise = handleAddCourseToCartResponse(response);
                        } else {
                            promise = handleAddCourseToCartErrorResponse(response);
                        }

                        return promise.finally(function() {
                            if (responses.length > 0) {
                                // Recall the function if there are still responses to process
                                return handleResponses();
                            }
                        });
                    }

                    return handleResponses();
                })
                .finally(function() {
                    $scope.addToCartStatus = STATUS.success;
                    $scope.removeUserMessage();
                });
        }

        /*
        Listens for the "deleteCartItem" event and calls the cart service to
        remove the given cart item from the cart.
         */
        $scope.$on('deleteCartItem', function (event, item) {
            CartService.removeItemFromCart(TermsService.getTermId(), item)
                .then(function(response) {
                    console.log('Cart item removed');

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
        $scope.$on('updateCourse', function(event, type, course, newCourse) {
            if (type === COURSE_TYPES.cart) {
                console.log('Updating cart item');

                event.promise = CartService.updateCartItem(TermsService.getTermId(), newCourse)
                    .then(function () {
                        $scope.creditTotal = creditTotal() - Number(course.credits) + Number(newCourse.credits);
                    });
            }
        });


        $scope.lastCartItems = null;
        $scope.clearCart = function() {
            $scope.userMessage = {
                txt: 'Clearing Cart',
                type: STATUS.processing
            };

            CartService.clearCart(TermsService.getTermId())
                .then(function() {
                    // Persist the last cart items
                    $scope.lastCartItems = $scope.cart.items.splice(0);

                    // Set a custom message with an actionLink that we'll catch in the invokeActionLink method
                    $scope.userMessage = {
                        txt: 'Cleared Cart',
                        type: STATUS.success,
                        actionLink: ACTION_LINK.undoClearCart,
                        linkText: 'Undo'
                    };
                }, function(error) {
                    $scope.userMessage = {
                        txt: error.data.genericMessage,
                        type: STATUS.error
                    };
                });
        };

        $scope.undoClearCart = function() {
            if (angular.isArray($scope.lastCartItems)) {
                addCoursesToCart($scope.lastCartItems);
            }
        };

        $scope.invokeActionLink = function (actionLink) {
            if (actionLink === ACTION_LINK.undoClearCart) {
                $scope.undoClearCart();
            } else {
                // call the backend service here to persist something
                CartService.invokeActionLink(actionLink).then(function (response) {
                    standardizeCourseData(response);
                    $scope.cart.items.unshift(response);
                    $scope.removeUserMessage();
                });
            }
        };

        $scope.addCartItemToWaitlist = function (cartItem) {
            console.log('Adding cart item to waitlist... ');

            cartItem.state = STATE.lpr.item.processing;
            cartItem.status = STATUS.processing;

            var poller = ScheduleService.registerForRegistrationGroup(cartItem, true);
            handlePoller(poller);
        };

        $scope.removeAlertMessage = function (cartItem) {
            cartItem.alertMessage = null;
        };

        $scope.removeUserMessage = function () {
            if ($scope.userMessage) {
                $scope.userMessage = null;
            }

            $scope.lastCartItems = null;
        };

        $scope.removeCartResultItem = function(cartResultItem) {
            $scope.cartResults.items.splice(cartResultItem, 1);
            calculateCartResultCounts();
        };

        $scope.register = function () {
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


            var poller = CartService.submitCart(TermsService.getTermId());
            handlePoller(poller);
        };

        function createUserMessageFromServerMessage(reason, course) {
            var userMessage = {
                txt: reason.genericMessage || null,
                detail: reason.detailedMessage || null,
                messageKey: reason.messageKey || null,
                type: reason.type,
                course: course
            };

            return angular.extend(userMessage, reason.data);
        }

        function handleAddCourseToCartResponse(response) {
            var cartItem = response.cartItem;

            standardizeCourseData(cartItem);
            $scope.cart.items.unshift(cartItem); // Add the new cart item at the end of the stack

            var deferred = $q.defer();
            deferred.resolve(cartItem);
            return deferred.promise;
        }

        function handleAddCourseToCartErrorResponse(response, status) {
            if (angular.isDefined(response.headers)) {
                status = response.status;
                response = response.data;
            }

            // Show options if the response is 400 or if any of the messages are of the creditOrGradingOptionsMissing type
            var showOptions = (status && status === 400);
            if (!showOptions && angular.isDefined(response.messages) &&
                angular.isArray(response.messages) && response.messages.length > 0) {

                for (var i = 0; i < response.messages.length; i++) {
                    if (VALIDATION_ERROR_TYPE.creditOrGradingOptionsMissing === response.messages[i].messageKey) {
                        showOptions = true;
                        break;
                    }
                }
            }


            if (showOptions) {
                // Additional options are required
                return showAdditionalOptionsModal(response.cartItem)
                    .then(function(course) {
                        // Once the additional options have been chosen, resubmit the course to be added
                        return CartService.addCourseToCart(TermsService.getTermId(), course)
                            .then(handleAddCourseToCartResponse);
                    });
            }

            var deferred = $q.defer();
            deferred.reject(response.messages);
            return deferred.promise;
        }

        function handlePoller(promise) {
            promise.then(function(response) { // Success
                console.log('- Stop polling - Success');
                updateCartWithResults(response);
            }, function(response) { // Error
                console.log('- Stop polling - Failed: ', response);
                updateCartWithResults(response);
            }, function(response) { // Notify
                console.log('- Continue polling');
                $scope.cart.state = response.state;
            });
        }

        // Update the cart results with the response from the cartPoller
        function updateCartWithResults(response) {
            if (angular.isDefined(response.state)) {
                $scope.cart.state = response.state;
            }

            $scope.cart.status = '';  // set the overall status to nothing... which is the default i guess
            $scope.cartResults.state = STATE.lpr.item.succeeded;

            if (angular.isDefined(response.responseItemResults)) {
                // Map the data from the responseItems to the cartResultItems
                var newItems = [];
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
                                case STATUS.success:
                                    if (angular.isArray(responseItem.messages) && responseItem.messages.length > 0) {
                                        // This successful item has a message attached to it,
                                        // pop it off an add it as a new info item right after the success result item
                                        var infoItem = angular.copy(cartResultItem);
                                        infoItem.status = STATUS.info;
                                        newItems.push({ oldItem: cartResultItem, newItem: infoItem });
                                    }
                                    break;

                                case STATUS.waitlist: // add to waitlist successful
                                    cartResultItem.waitlistMessage = GlobalVarsService.getCorrespondingMessageFromStatus(cartResultItem.status);
                                    break;
                                case STATUS.action: // waitlist action available
                                    // remove all messages that are not related to the waitlist
                                    for (var i = cartResultItem.statusMessages.length - 1; i>=0; i--) {
                                        var messageKey = cartResultItem.statusMessages[i].messageKey;
                                        if (angular.isString(messageKey)) {
                                            if (messageKey !== VALIDATION_ERROR_TYPE.waitlistAvailable) {
                                                cartResultItem.statusMessages.splice(i, 1);
                                            }
                                        }
                                    }
                                    break;
                            }
                        }
                    });
                });

                // Add in any new items outside of the other loop as to avoid concurrent modification issues
                for (var i = 0; i < newItems.length; i++) {
                    $scope.cartResults.items.splice($scope.cartResults.items.indexOf(newItems[i].oldItem) + 1, 0, newItems[i].newItem);
                }
            }

            // Calculate the result counts per status (clearing out initially to trigger the view to reset the values)
            calculateCartResultCounts(true);

            // After all the processing is complete, get the final Schedule counts.
            reloadSchedule();
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
                $scope.cartResults.infoCount = 0;
            }

            // Store as local variables so the $scope vars don't fire change events on increments
            var success = 0,
                waitlist = 0,
                waitlisted = 0,
                error = 0,
                info = 0;

            angular.forEach($scope.cartResults.items, function (item) {
                switch (item.status) {
                    case STATUS.success:
                        success++;
                        break;
                    case STATUS.waitlist: // Waitlist action successful
                        // if waitlist success is the only message, show a success icon, otherwise show an info icon
                        if (angular.isArray(item.statusMessages) && item.statusMessages.length > 1) {
                            info++;
                            item.status = STATUS.info;
                        } else {
                            success++; // Also increment the successes
                            waitlisted++;
                        }
                        break;
                    case STATUS.action: // Waitlist action available
                        waitlist++;
                        break;
                    case STATUS.error:
                        error++;
                        break;
                    case STATUS.info:
                        info++;
                        break;
                }
            });

            // Set the counts into the scope
            $scope.cartResults.successCount = success;
            $scope.cartResults.waitlistCount = waitlist;
            $scope.cartResults.waitlistedCount = waitlisted;
            $scope.cartResults.errorCount = error;
            $scope.cartResults.infoCount = info;
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

        // Direct register for a course
        function registerForCourse(course) {
            if (!course.credits || !course.gradingOptionId) {
                // Show the additional options modal if either the credit or grading options have not been set
                return showAdditionalOptionsModal(course).then(function(newCourse) {
                    course.gradingOptionId = newCourse.gradingOptionId;
                    course.credits = newCourse.credits;

                    return registerForCourse(course);
                });
            }


            return ScheduleService.registerForRegistrationGroup(course)
                .then(function(response) {
                    // Check to see if the request failed, if so don't reload the schedule
                    var state = null;
                    angular.forEach(response.responseItemResults, function(responseItem) {
                        if (state === null) {
                            state = responseItem.state;
                        }
                    });

                    if (state !== STATE.lpr.item.failed) {
                        // After all the processing is complete, get the final Schedule counts.
                        reloadSchedule();
                    }

                    return response;
                });
        }

        // Reload the schedule & update the schedule counts
        function reloadSchedule() {
            return ScheduleService.getSchedule(TermsService.getTermId(), true)
                .then(function (result) {
                    console.log('Called rest service to get schedule data - in cart-controller.js');
                    ScheduleService.setSelectedSchedule(result);
                });
        }

        // Show the Additional Options Modal Dialog allowing the user to select the specific credit & grading option they would like.
        function showAdditionalOptionsModal(cartItem) {
            var modal = $modal.open({
                backdrop: 'static',
                templateUrl: 'components/cart/additionalOptions.html',
                size: 'sm',
                resolve: {
                    item: function () {
                        return cartItem;
                    }
                },
                controller: ['$scope', 'item', function ($scope, item) {
                    console.log('Controller for modal... Item: ', item);

                    $scope.newCartItem = item;
                    $scope.newCartItem.editing = true;

                    $scope.dismissAdditionalOptions = function () {
                        console.log('Dismissing credits and grading');
                        $scope.$dismiss('cancel');
                    };

                    var submitted = false;
                    $scope.saveAdditionalOptions = function (course) {
                        if (!submitted) { // Only let the form be submitted once.
                            submitted = true;
                            course.editing = false;

                            $scope.newCartItem.credits = $scope.newCartItem.newCredits;
                            $scope.newCartItem.gradingOptionId = $scope.newCartItem.newGrading;

                            $scope.$close($scope.newCartItem);
                        }
                    };
                }]
            });

            return modal.result;
        }

        function standardizeCourseData(course) {
            return RegUtil.standardizeCartCourse(course);
        }

    }]);