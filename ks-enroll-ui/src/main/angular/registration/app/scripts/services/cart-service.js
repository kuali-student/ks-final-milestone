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
                query: {headers: { 'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8'},
                    method: 'POST', cache: false, isArray: false,
                    transformRequest: function (obj) {
                        var str = [];
                        for (var p in obj) {
                            if (obj[p]) {
                                str.push(encodeURIComponent(p) + '=' + encodeURIComponent(obj[p]));
                            }
                        }
                        return str.join('&');
                    }}
            });
        };
        this.removeItemFromCart = function ($actionLink) {
            return $resource(APP_URL + $actionLink, {}, {
                query: {method: 'DELETE', cache: false, isArray: false}
            });
        };

        this.invokeActionLink = function ($actionLink) {
            return $resource(APP_URL + $actionLink, {}, {
                query: {method: 'GET', cache: false, isArray: false}
            });
        };
        this.updateCartItem = function () {
            return $resource(APP_URL + 'CourseRegistrationCartClientService/updateCartItem', {}, {
                query: {headers: { 'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8'},
                    method: 'PUT', cache: false, isArray: false, transformRequest: function (obj) {
                        var str = [];
                        for (var p in obj) {
                            if (obj[p]) {
                                str.push(encodeURIComponent(p) + '=' + encodeURIComponent(obj[p]));
                            }
                        }
                        return str.join('&');
                    }}
            });
        };
        this.submitCart = function () {
            return $resource(APP_URL + 'CourseRegistrationCartClientService/submitCart', {}, {
                query: {method: 'GET', cache: false, isArray: false}
            });
        };

        this.getRegistrationStatus = function () {
            return $resource(APP_URL + 'CourseRegistrationClientService/getRegistrationStatus', {}, {
                query: {method: 'GET', cache: false, isArray: false}
            });
        };

        this.undoDeleteCourse = function () {
            return $resource(APP_URL + 'CourseRegistrationCartClientService/undoDeleteCourse', {}, {
                query: {method: 'GET', cache: false, isArray: false}
            });
        };
    }]);
