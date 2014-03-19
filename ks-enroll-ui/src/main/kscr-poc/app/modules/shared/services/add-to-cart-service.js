'use strict';

angular.module('kscrPocApp')
    .factory('addToCartService', function ($resource, apiService) {
        return $resource(apiService.get('addCourseToCart'), {}, {
            query: {
                method: 'POST',
                cache: true,
                isArray: false
            }
        });
    });