'use strict';

angular.module('kscrPocApp')
    .factory('addToCartService', function ($resource, apiService) {
        return $resource(apiService.get('addCourseToCart'), {}, {
            query: {
                method: 'GET',
                cache: true,
                isArray: false
            }
        });
    });