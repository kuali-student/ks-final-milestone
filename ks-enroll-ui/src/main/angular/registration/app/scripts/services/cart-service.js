'use strict';

// Cart REST Resource Factory
angular.module('regCartApp').factory('Cart', ['$resource', 'APP_URL', 'URLS', function($resource, APP_URL, URLS) {
    return $resource(APP_URL + URLS.courseRegistrationCart + '/cart', {}, {
        submit: {
            method: 'GET',
            url: APP_URL + URLS.courseRegistrationCart + '/cart/submit'
        }
    });
}]);

// Cart Item REST Resource Factory
angular.module('regCartApp').factory('CartItem', ['$resource', 'APP_URL', 'URLS', function($resource, APP_URL, URLS) {
    return $resource(APP_URL + URLS.courseRegistrationCart + '/cart/items', {}, {
        put: {
            method: 'PUT'
        }
    });
}]);


angular.module('regCartApp')
    .service('CartService', ['$q', '$resource', 'APP_URL', 'Cart', 'CartItem', 'RegUtil', 'GlobalVarsService', 'RegRequestStatusPoller',
        function CartService($q, $resource, APP_URL, Cart, CartItem, RegUtil, GlobalVarsService, RegRequestStatusPoller) {

            var cartCredits = 0;
            var cartCourseCount = 0;
            var cartCourses = [];

            // Cache of carts per term
            var carts = {};

            this.getCart = function(termId) {
                var deferred = $q.defer();

                if (angular.isDefined(carts[termId])) {
                    // Return the cached cart
                    deferred.resolve(carts[termId]);
                } else {
                    return this.getCartFromServer(termId).then(function(cart) {
                        // Cache the cart
                        carts[termId] = cart;
                        return cart;
                    });
                }

                return deferred.promise;
            };

            this.pollForCartUpdates = function(cart) {
                // This is currently the last place that knows or cares about the cartId.
                // The cartId is really just the regRequestId so we do need it to poll for status changes.
                return RegRequestStatusPoller.pollRegistrationRequestStatus(cart.cartId);
            };

            this.getCartCredits = function () {
                return cartCredits;
            };

            this.setCartCredits = function (value) {
                cartCredits = value;
            };

            this.getCartCourseCount = function () {
                return cartCourseCount;
            };

            this.setCartCourseCount = function (value) {
                cartCourseCount = value;
            };

            this.getCartCourses = function() {
                return cartCourses;
            };

            this.setCartCourses = function(courses) {
                cartCourses.splice(0, cartCourses.length);

                if (courses) {
                    angular.forEach(courses, function(course) {
                        cartCourses.push(course);
                    });

                    this.setCartCourseCount(courses.length);
                }
            };

            this.isCourseInCart = function(course) {
                return RegUtil.isCourseInList(course, this.getCartCourses());
            };

            this.isAoInCart = function(aoId) {
                var inCartIndicator = {flag: false, colorIndex: null};
                for (var i = 0; i < this.getCartCourses().length; i++) {
                    var course = this.getCartCourses()[i];
                    for (var j = 0; j < course.activityOfferings.length; j++) {
                        var activityOffering = course.activityOfferings[j];
                        if (aoId === activityOffering.activityOfferingId) {
                            inCartIndicator.flag = true;
                            inCartIndicator.colorIndex = GlobalVarsService.getCourseIndex(course);
                            return inCartIndicator;
                        }
                    }
                }
        
                return inCartIndicator;
            };

            // Server API Methods

            this.clearCart = function(termId) {
                return CartItem.delete({
                    termId: termId
                }).$promise;
            };

            this.getCartFromServer = function(termId) {
                return Cart.get({
                    termId: termId
                }).$promise;
            };

            this.submitCart = function(termId) {
                return Cart.submit({
                    termId: termId
                }).$promise
                    .then(function(registrationRequest) {
                        console.log('- Cart submitted - starting to poll for request status');
                        return RegRequestStatusPoller.pollRegistrationRequestStatus(registrationRequest.id);
                    });
            };

            this.addCourseToCart = function(termId, course) {
                if (course.courseCode) {
                    course.courseCode = course.courseCode.toUpperCase();
                }

                return CartItem.put({
                    termId: termId,
                    courseCode: course.courseCode || null,
                    regGroupCode: course.regGroupCode || null,
                    regGroupId: course.regGroupId || null,
                    gradingOptionId: course.gradingOptionId || null,
                    credits: course.credits || null
                }).$promise;
            };

            this.removeItemFromCart = function(termId, item) {
                return CartItem.remove({
                    termId: termId,
                    cartItemId: item.cartItemId
                }).$promise;
            };

            this.updateCartItem = function(termId, item) {
                return CartItem.save({
                    termId: termId,
                    cartItemId: item.cartItemId,
                    credits: item.credits,
                    gradingOptionId: item.gradingOptionId
                }).$promise;
            };

            this.invokeActionLink = function (actionLink) {
                return $resource(APP_URL + actionLink).get().$promise;
            };
        }]);