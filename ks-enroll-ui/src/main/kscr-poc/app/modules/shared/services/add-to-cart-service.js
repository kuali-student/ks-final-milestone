'use strict';

angular.module('kscrPocApp')
    .factory('addToCartService', function ($resource, apiService) {
        return $resource(apiService.get('addCourseToCart'), {}, {
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
    });