'use strict';

angular.module('kscrPocApp')
  .factory('regGroupService', function ($http, apiService, $q, orderByFilter, $angularCacheFactory) {

    // Indicate if all items in one array is found in another.
    function arrayHasValues(arr, items) {
      // Allows objects to be used, instead of just arrays.
      items = angular.isArray(items) ? items : [items];
      for( var i = 0, l = items.length; i < l; i++ ) {
        if( arr.indexOf(items[i]) === -1 ) {
          return false;
        }
      }
      return true;
    }

    function configHttp(params) {
      return {
        params: params,
        cache: true
      };
    }

    function filterRegGroupsByActivityOfferingIds(params, activityOfferingIds) {
      // Configure the parameters.
      var httpConfig = configHttp(params);
      // Call the service.
      var regGroupsResource = $http.get(apiService.get('reggroups'), httpConfig);

      // Process and return filtered Reg Groups.
      return regGroupsResource.then(function(results) {
        var filteredRegGroups = [];
        angular.forEach(results.data, function(regGroup) {
          // Keep a Reg Group if it contains all the Activity Offering Ids indicated.
          if( arrayHasValues(regGroup.activityOfferingIds, activityOfferingIds) ) {
            filteredRegGroups.push(regGroup);
          }
        });
        return filteredRegGroups;
      });
    }

    // Return a RegGroupId only if it's an absolute match.
    function getMatchingRegGroupId(params, activityOfferingIds) {
      return filterRegGroupsByActivityOfferingIds(params, activityOfferingIds).then(function(results) {
        if( results.length === 1 ) {
          return results[0].regGroupId;
        }
        return null;
      });
    }

    // Find all secondary Activity Offerings and group them by Activity Type.
    function getGroupedSecondaryActivityOfferings(params, defaultAOIds) {
      // Configure the parameters.
      var httpConfig = configHttp(params);
      // Transform into an array.
      defaultAOIds = angular.isArray(defaultAOIds) ? defaultAOIds : [ defaultAOIds ];

      // Call all services.
      // We must use $http, because $resource doesn't return promises.
      var regGroupsResource = filterRegGroupsByActivityOfferingIds(params, defaultAOIds);
      var activityOfferingsResource = $http.get(apiService.get('activityofferings'), httpConfig);
      var activityTypesResource = $http.get(apiService.get('activitytypes'), httpConfig);
      // Group the resources.
      var resources = [regGroupsResource, activityOfferingsResource, activityTypesResource];
      
      // Wait for the promises to be resolved.
      return $q.all(resources).then(function(results) {
        // Pull out the results.
        var regGroupsData = results[0];
        var activityOfferingsData = results[1].data;
        var activityTypesData = results[2].data;

        //
        // Index
        //

        // Index all Activity Offerings in these Reg Groups.
        var associatedAOIdsIndex = {};
        angular.forEach(regGroupsData, function(regGroup) {
          angular.forEach(regGroup.activityOfferingIds, function(aoId) {
            associatedAOIdsIndex[aoId] = true;
          });
        });

        //
        // Filter
        //

        // Keep only relevant Activity Offerings.
        var _activityOfferingsData = [];
        angular.forEach(activityOfferingsData, function(ao) {
          // Retain Activity Offerings if they are associated
          // with the appropriate Reg Groups,
          // and they aren't explicitly meant to be ignored.
          var aoId = ao.activityOfferingId;
          if( associatedAOIdsIndex[aoId] && !arrayHasValues(defaultAOIds, aoId) ) {
            _activityOfferingsData.push(ao);
          }
        });
        // Overwrite the source array.
        activityOfferingsData = _activityOfferingsData;

        //
        // Sort
        //

        // Sort Activity Types by priority.
        activityTypesData = orderByFilter(activityTypesData, 'priority');

        // Prepare sort predicates for Activity Offerings.
        var aoSortPredicates = 'sun,mon,tue,wed,thu,fri,sat,startTime'.split(',');
        // Prefix all predicates, since the schedule data is a nested object.
        angular.forEach(aoSortPredicates, function(predicate, key) {
          aoSortPredicates[key] = 'schedule.' + predicate;
        });
        // Sort Activity Offerings by day, then time.
        activityOfferingsData = orderByFilter(activityOfferingsData, aoSortPredicates, true);

        //
        // Index
        //

        // Keep indexes of Activity Offerings for quick reference.
        var aoIndexById = {};
        var aoIndexByType = {};

        // Process indexes.
        angular.forEach(activityOfferingsData, function(ao) {

          // Index Activity Offerings by id.
          aoIndexById[ ao.activityOfferingId ] = ao;
          
          // Initiate an array of a certain Type, if it doesn't yet exist.
          if( angular.isUndefined(aoIndexByType[ ao.activityOfferingTypeName ]) ) {
            aoIndexByType[ ao.activityOfferingTypeName ] = [];
          }
          // Add the Activity Offerings to its respective Type array.
          aoIndexByType[ ao.activityOfferingTypeName ].push(ao);
        });

        //
        // Prepare return data
        //

        var data = [];

        // Group all sorted Activity Offerings by sorted Activity Offering Types.
        angular.forEach(activityTypesData, function(activityType) {
          var typeName = activityType.name;
          var activityOfferings = aoIndexByType[typeName];

          // Ignore any Activity Offering Types
          // in which there were no AOs found.
          if( !angular.isArray(activityOfferings) ) {
            return;
          }

          // Add the Type.
          data.push({
            name: typeName,
            description: activityType.description,
            activityOfferings: activityOfferings
          });
        });

        return data;
      });
    }

    function register(regGroupId, userId) {
      // Configure the parameters.
      var params = {
        regGroupId: regGroupId,
        userId: userId
      };
      var httpConfig = configHttp(params);
      // Call the service.
      return $http.get(apiService.get('registerreggroup'), httpConfig).success(function() {
        // Clear the cache for the schedule service,
        // so it'll automatically call the API again to get an updated schedule.
        var scheduleServiceCache = $angularCacheFactory.get('scheduleService');
        if( angular.isDefined(scheduleServiceCache) ) {
          scheduleServiceCache.removeAll();
        }
      });
    }

    return {
      // @param params Object
      // @param activityOfferingIds String/Array
      get: function(params, activityOfferingIds) {
        return getGroupedSecondaryActivityOfferings(params, activityOfferingIds);
      },
      getMatchingRegGroupId: function(params, activityOfferingIds) {
        return getMatchingRegGroupId(params, activityOfferingIds);
      },
      register: function(regGroupId, userId) {
        return register(regGroupId, userId);
      }
    };
  });
