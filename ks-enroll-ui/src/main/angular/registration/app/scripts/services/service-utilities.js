'use strict';

angular.module('regCartApp')
    .service('ServiceUtilities', ['$resource', 'APP_URL', function ServiceUtilities($resource, APP_URL) {

        this.getData = function (url) {
            return $resource(APP_URL + url, {}, {
                query: {method: 'GET', cache: false, isArray: false}
            });
        };

        this.deleteData = function (url) {
            return $resource(APP_URL + url, {}, {
                query: {method: 'DELETE', cache: false, isArray: false}
            });
        };

        this.postData = function (url) {
            return $resource(APP_URL + url, {}, {
                query: {headers: { 'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8'},
                    method: 'POST', cache: false, isArray: false,
                    transformRequest: function (obj) {
                        return transformRequest(obj);
                    }}
            });
        };

        this.putData = function (url) {
            return $resource(APP_URL + url, {}, {
                query: {headers: { 'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8'},
                    method: 'PUT', cache: false, isArray: false,
                    transformRequest: function (obj) {
                        return transformRequest(obj);
                    }}
            });
        };

        this.getArray = function (url) {
            return $resource(APP_URL + url, {}, {
                query: {method: 'GET', cache: false, isArray: true}
            });
        };

        function transformRequest(obj) {
            var str = [];
            for (var p in obj) {
                if (obj[p]) {
                    str.push(encodeURIComponent(p) + '=' + encodeURIComponent(obj[p]));
                }
            }
            return str.join('&');
        }
    }]
    );