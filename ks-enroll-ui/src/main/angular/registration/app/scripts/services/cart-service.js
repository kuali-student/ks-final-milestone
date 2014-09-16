'use strict';

angular.module('regCartApp')
    .service('CartService', ['$q', 'URLS', 'RegUtil', 'ServiceUtilities', function CartService($q, URLS, RegUtil, ServiceUtilities) {

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
                this.getCartFromServer().query({termId: termId}, function(cart) {
                    // Cache the cart
                    carts[termId] = cart;
                    deferred.resolve(cart);
                }, function(error) {
                    // Report out the error
                    deferred.reject(error);
                });
            }

            return deferred.promise;
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

        // Server API Methods

        this.getCartFromServer = function () {
            return ServiceUtilities.getData(URLS.courseRegistrationCart + '/searchForCart');
        };

        this.addCourseToCart = function () {
            return ServiceUtilities.postData(URLS.courseRegistrationCart + '/addCourseToCart');
        };

        this.removeItemFromCart = function ($actionLink) {
            return ServiceUtilities.deleteData($actionLink);
        };

        this.invokeActionLink = function ($actionLink) {
            return ServiceUtilities.getData($actionLink);
        };

        this.updateCartItem = function () {
            return ServiceUtilities.putData(URLS.courseRegistrationCart + '/updateCartItem');
        };

        this.submitCart = function () {
            return ServiceUtilities.getData(URLS.courseRegistrationCart + '/submitCart');
        };

        this.undoDeleteCourse = function () {
            return ServiceUtilities.getData(URLS.courseRegistrationCart + '/undoDeleteCourse');
        };
    }]);