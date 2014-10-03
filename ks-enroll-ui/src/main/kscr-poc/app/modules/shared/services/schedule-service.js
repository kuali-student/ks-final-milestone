'use strict';

angular.module('kscrPocApp')
  .factory('scheduleService', function ($resource, apiService, $angularCacheFactory, namecaseFilter) {

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

          angular.forEach(data, function(schedule) {
            angular.forEach(schedule.courseOfferings, function(co) {
              angular.forEach(co.activityOfferings, function(ao) {

                // BUG
                // `activityOfferingTypeShortName` is incorrectly sending the wrong data.
                // Interpret the name from `activityOfferingType` until fixed.

                // Extract the last part of the type.
                var activityTypeName = ao.activityOfferingType.split('.').pop();
                // Titlecase the first letter.
                // Repurposing Namecase Filter for the sake of a quick solution.
                activityTypeName = namecaseFilter(activityTypeName);
                // Reassign the name.
                ao.activityOfferingTypeShortName = activityTypeName;

                // BUG
                // Process the Activity Offerings, retaining only the first schedule.
                // This is no longer needed once the service prevents duplicates.
                // https://jira.kuali.org/browse/KSENROLL-11632
                var sc = ao.scheduleComponents;
                if( angular.isArray(sc) && angular.isDefined(sc[0]) ) {
                  ao.schedule = sc[0];
                }

                angular.forEach(ao.instructors, function(instructor) {
                  instructor.firstName = namecaseFilter(instructor.firstName);
                  instructor.lastName = namecaseFilter(instructor.lastName);
                });
              });
            });
          });

          return data;
        }
      }
    });
  });
