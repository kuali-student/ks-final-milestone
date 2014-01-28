'use strict';

angular.module('kscrPocApp')
  .factory('apiService', function (config) {
    return {
      get: function(endpoint) {
        var url = config.apiBase;
        // Set the appropriate namespace.
        switch(endpoint) {
          case 'personschedule':
          case 'registerreggroup':
            url += config.apiCourseRegistrationBase;
            break;
          default:
            url += config.apiScheduleOfClassesBase;
            break;
        }
        // Set for a second level.
        switch(endpoint) {
          case 'primaryactivities':
            url += 'courseofferings/';
            break;
          default:
            break;
        }
        url += endpoint;
        return url;
      }
    };
  });
