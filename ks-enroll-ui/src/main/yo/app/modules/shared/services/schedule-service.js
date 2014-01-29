'use strict';

angular.module('kscrPocApp')
  .factory('scheduleService', function ($resource, apiService, $angularCacheFactory) {

    // Use a custom cache, so manipulations can be more targeted.
    var cache = $angularCacheFactory('scheduleService');

    return $resource(apiService.get('personschedule'), {}, {
      query: {
        method: 'GET',
        cache: false,
        isArray: true,
        transformResponse: function(data) {
          // Convert the raw data string to native objects.
          data = angular.fromJson(data);

          // Process the Activity Offerings, retaining only the first schedule.
          // This is no longer needed once the service prevents duplicates.
          // https://jira.kuali.org/browse/KSENROLL-11632
          angular.forEach(data, function(scheduleItem) {
            angular.forEach(scheduleItem.activityOfferings, function(ao) {
              var sc = ao.scheduleComponents;
              if( angular.isArray(sc) && angular.isDefined(sc[0]) ) {
                ao.schedule = sc[0];
              }
            });
          });

          return data;
        }
      }
    });
  });
