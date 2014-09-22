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
            CartService.getCart(termId).then(function (theCart) {
                $scope.cart = theCart; // right now theCart is a mix of processing and cart items
                var cartItems = [],
                    submittedCartId = null; // we must assume that the items are all from one cart

                // if there are any processing items in the cart we need to start polling
                angular.forEach(theCart.items, function(item) {
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


        // Allows you to add a cartResultItem back into the cart. useful when a user wants to add a failed item back
        // into their cart.
        $scope.addCartItemToCart = function (cartItem) {
            addCourseToCart(cartItem);
        };

        function addCourseToCart(course, deferred) {
            deferred = deferred || $q.defer();

            $scope.courseAdded = false; // reset cursor focus
            $scope.addToCartStatus = STATUS.processing;

            if (course.courseCode) {
                course.courseCode = course.courseCode.toUpperCase();
            }

            CartService.addCourseToCart().query({
                cartId: $scope.cart.cartId,
                termId: TermsService.getTermId(),
                courseCode: course.courseCode || null,
                regGroupCode: course.regGroupCode || null,
                regGroupId: course.regGroupId || null,
                gradingOptionId: course.gradingOptionId || null,
                credits: course.credits || null
            }, function (response) {
                console.log('Searched for course: ' + $scope.courseCode + ' ' + $scope.regCode + ', Term: ' + TermsService.getTermId());
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
                $scope.addToCartStatus = STATUS.success;

                deferred.resolve(response);
            }, function (error) {
                var errorText;
                if (error.status === 404) {
                    //Reg group was not found
                    if (error.data.messageKey) {
                        switch (error.data.messageKey) {
                            case GENERAL_ERROR_TYPE.courseNotFound:
                                errorText = error.data.courseCode + ' does not exist for ' + TermsService.getSelectedTerm().termName;
                                break;
                            default:
                                errorText = MessageService.getMessage(error.data.messageKey);
                        }
                    } else {
                        errorText = error.data;
                    }
                    $scope.userMessage = {txt: errorText, messageKey: GENERAL_ERROR_TYPE.noRegGroup, type: STATUS.error, course: course.courseCode};
                    $scope.courseAdded = true;  // refocus cursor back to course code
                    $scope.addToCartStatus = STATUS.error;

                    deferred.reject($scope.userMessage);
                } else if (error.status === 400) {
                    //Additional options are required
                    showAdditionalOptionsModal(error.data).then(function(course) {
                        addCourseToCart(course, deferred);
                    }, function(result) {
                        $scope.courseAdded = true; // refocus cursor back to course code
                        $scope.addToCartStatus = null;

                        deferred.reject(result);
                    });
                } else {
                    console.log('Error with adding course', error.data.consoleMessage);
                    //Reg group is not in offered state
                    errorText = error.data.genericMessage + ' for ' + TermsService.getSelectedTerm().termName;
                    $scope.userMessage = {txt: errorText, messageKey: GENERAL_ERROR_TYPE.noRegGroup, type: error.data.type, detail: error.data.detailedMessage, course: course.courseCode + ' (' + course.regGroupCode + ')'};
                    $scope.courseAdded = true; // refocus cursor back to course code
                    $scope.addToCartStatus = STATUS.error;

                    deferred.reject($scope.userMessage);
                }
            });

            return deferred.promise;
        }

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
                    $scope.creditTotal = creditTotal() - Number(course.credits) + Number(newCourse.credits);
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

                    updateCartWithResults(response);
                }, function(response) { // Error
                    console.log('- Stop polling - Error: ', response);

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
        function registerForCourse(course, deferred) {
            deferred = deferred || $q.defer();


            if (!course.credits || !course.gradingOptionId) {
                // Show the additional options modal if either the credit or grading options have not been set
                showAdditionalOptionsModal(course).then(function(newCourse) {
                    course.gradingOptionId = newCourse.gradingOptionId;
                    course.credits = newCourse.credits;

                    registerForCourse(course, deferred);
                }, function(result) {
                    deferred.reject(result);
                });

                return deferred.promise;
            }


            ScheduleService.registerForRegistrationGroup().query({
                courseCode: course.courseCode || null,
                regGroupId: course.regGroupId || null,
                gradingOption: course.gradingOptionId || null,
                credits: course.credits || null,
                allowWaitlist: course.allowWaitlist || false,
                allowRepeatedCourses: course.allowRepeatedCourses || false
            }, function (regRequest) {
                ScheduleService.pollRegistrationRequestStatus(regRequest.id)
                    .then(function(response) {
                        var state = null;
                        angular.forEach(response.responseItemResults, function(responseItem) {
                            if (state === null) {
                                state = responseItem.state;
                            }
                        });

                        if (state === STATE.lpr.item.failed) {
                            deferred.resolve(response);
                        } else {
                            // After all the processing is complete, get the final Schedule counts.
                            reloadSchedule();

                            deferred.resolve(response);
                        }
                    }, function(error) {
                        deferred.reject(error);
                    });
            }, function(error) {
                deferred.reject(error);
            });

            return deferred.promise;
        }

        // Reload the schedule & update the schedule counts
        function reloadSchedule() {
            ScheduleService.getSchedule(TermsService.getTermId(), true).then(function (result) {
                console.log('Called rest service to get schedule data - in cart-controller.js');
                ScheduleService.setSelectedSchedule(result);
            });
        }

        // Show the Additional Options Modal Dialog allowing the user to select the specific credit & grading option they would like.
        function showAdditionalOptionsModal(cartItem) {
            var modal = $modal.open({
                backdrop: 'static',
                templateUrl: 'partials/additionalOptions.html',
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