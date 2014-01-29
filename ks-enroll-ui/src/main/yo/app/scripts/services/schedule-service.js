'use strict';

angular.module('kscrPocApp')
  .factory('scheduleService', function () {
    // Service logic
    // ...

    var meaningOfLife = 42;

    // Public API here
    return {
      name: "gillen",
      someMethod: function () {
        return meaningOfLife;
      }
    };
  });
