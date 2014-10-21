'use strict';

angular.module('kscrPocApp')
  .factory('config', function ($injector, configApp) {

    // Server configurations are optional.
    // Angular doesn't have optional dependencies,
    // so this method will suffice.
    var configServer = $injector.has('configServer') ? $injector.get('configServer') : null;

    // Override local configs with server configs,
    // duplicating all properties to a new object,
    // so the sources are retained.
    var config = angular.extend({}, configApp, configServer);

    return config;
  });
