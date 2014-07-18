'use strict';

angular.module('kscrPocApp')
  .filter('pluralize', function () {
    return function (count, singular, plural) {
      if( count === 1 ) {
        return singular;
      }
      plural = angular.isString(plural) ? plural : singular + 's';
      return plural;
    };
  });
