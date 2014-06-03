'use strict';

angular.module('regCartApp')
    .service('ServiceUtilities', ['$resource', 'APP_URL', function ($resource, APP_URL) {

        this.getData = function(url) {
            console.log('get data function');
            return $resource(APP_URL + url, {}, {
                query: {method: 'GET', cache: false, isArray: false}
            });
        };

        this.deleteData = function(url) {
            console.log('delete data function');
            return $resource(APP_URL + url, {}, {
                query: {method: 'DELETE', cache: false, isArray: false}
            });
        };

        this.postData = function(url) {
            console.log('post data function');
            return $resource(APP_URL + url, {}, {
                query: {headers: { 'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8'},
                    method: 'POST', cache: false, isArray: false,
                    transformRequest: function (obj) {
                        return transformRequest(obj);
                    }}
            });
        };

        this.putData = function(url) {
            console.log('put data function');
            return $resource(APP_URL + url, {}, {
                query: {headers: { 'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8'},
                    method: 'PUT', cache: false, isArray: false,
                    transformRequest: function (obj) {
                        return transformRequest(obj);
                    }}
            });
        };

        this.getArray = function(url) {
            console.log('get data function');
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