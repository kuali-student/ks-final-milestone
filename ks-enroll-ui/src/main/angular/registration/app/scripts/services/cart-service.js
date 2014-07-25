'use strict';

angular.module('regCartApp')
    .service('CartService', ['$q', 'ServiceUtilities', 'URLS', function CartService($q, ServiceUtilities, URLS) {

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