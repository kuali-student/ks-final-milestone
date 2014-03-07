'use strict';

angular.module('regCartApp')
    .service('CartService', ['$resource', 'APP_URL', function CartService($resource, APP_URL) {

        this.getCart = function () {
            return $resource(APP_URL + 'CourseRegistrationCartClientService/searchForCart', {}, {
                query: {method: 'GET', cache: false, isArray: false}
            });
        };
        this.getGradingOptions = function () {
            return $resource(APP_URL + 'CourseRegistrationCartClientService/getStudentRegistrationOptions', {}, {
                query: {method: 'GET', cache: false, isArray: false}
            });
        };
        this.addCourseToCart = function () {
            return $resource(APP_URL + 'CourseRegistrationCartClientService/addCourseToCart', {}, {
                query: {method: 'GET', cache: false, isArray: false}
            });
        };
        this.removeItemFromCart = function ($actionLink) {
            return $resource(APP_URL + $actionLink, {}, {
                query: {method: 'GET', cache: false, isArray: false}
            });
        };

        this.invokeActionLink = function ($actionLink) {
            return $resource(APP_URL + $actionLink, {}, {
                query: {method: 'GET', cache: false, isArray: false}
            });
        };
        this.updateCartItem = function () {
            return $resource(APP_URL + 'CourseRegistrationCartClientService/updateCartItem', {}, {
                query: {method: 'GET', cache: false, isArray: false}
            });
        };
        this.submitCart = function () {
            return $resource(APP_URL + 'CourseRegistrationCartClientService/submitCart', {}, {
                query: {method: 'GET', cache: false, isArray: false}
            });
        };
    }]);
