'use strict';

angular.module('kscrPocApp')
  .factory('termsService', function ($resource, apiService) {
    return $resource(apiService.get('terms'), {}, {
      query: {
        method: 'GET',
        cache: true,
        isArray: true
      }
    });
  });