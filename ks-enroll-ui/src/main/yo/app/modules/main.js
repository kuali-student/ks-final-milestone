'use strict';

angular.module('kscrPocApp', [
  'ngResource',
  'ngSanitize',
  'ngTouch',
  'ui.router',
  'jmdobry.angular-cache'
])
  .config(function ($httpProvider, $stateProvider, $urlRouterProvider, $angularCacheFactoryProvider) {
    
    //
    // API
    //

    // Enable cross-domain resource sharing (CORS)
    // http://thibaultdenizet.com/tutorial/cors-with-angular-js-and-sinatra/
    $httpProvider.defaults.useXDomain = true;

    //
    // States
    //

    $stateProvider
      .state('app', {
        abstract: true,
        templateUrl: 'modules/app.html',
        controller: 'AppCtrl'
      })
      .state('app.search', {
        url: '/search',
        templateUrl: 'modules/app/search.html',
        data: {
          title: 'Search'
        }
      })
      .state('app.search.results', {
        abstract: true,
        url: '/:termCode/:query',
        template: '<div ui-view></div>',
        controller: 'AppSearchResultsCtrl'
      })
      .state('app.search.results.list', {
        url: '',
        templateUrl: 'modules/app/search/results/list.html',
        controller: 'AppSearchResultsListCtrl',
        data: {
          title: 'Searching'
        }
      })
      .state('app.search.results.empty', {
        url: '',
        templateUrl: 'modules/app/search/results/empty.html',
        data: {
          title: 'No results'
        }
      })
      .state('app.search.results.list.details', {
        url: '/:index',
        templateUrl: 'modules/app/search/results/list/details.html',
        controller: 'AppSearchResultsListDetailsCtrl',
        data: {
          title: 'Result'
        }
      })
      .state('app.cart', {
        url: '/cart',
        templateUrl: 'modules/app/cart.html',
        data: {
          title: 'Cart'
        }
      })
      .state('app.schedule', {
        url: '/schedule',
        templateUrl: 'modules/app/schedule.html',
        controller: 'AppScheduleCtrl',
        data: {
          title: 'Schedule'
        }
      });

    //
    // Routing
    //

    // For any unmatched url, send to a default route
    $urlRouterProvider.otherwise('/search');

    //
    // Cache
    //

    // Configuration
    // http://jmdobry.github.io/angular-cache/configuration.html
    $angularCacheFactoryProvider.setCacheDefaults({
      // Hold 1000 items.
      cache: 1000,
      // Items expire after 15 minutes.
      maxAge: 15 * 60 * 1000,
      // Cache clears after 60 minutes.
      cacheFlushInterval: 60 * 60 * 1000,
      // Items will be swiftly deleted.
      deleteOnExpire: 'aggressive',
      // Store cache.
      storageMode: 'localStorage',
      // Sync with localStorage on every operation.
      verifyIntegrity: true
    });

  })
  .run(function ($rootScope, $state, $angularCacheFactory, $http) {
    $rootScope.$state = $state;

    var pageTitle = 'Course Registration';
    $rootScope.pageTitle = pageTitle;

    $rootScope.$on('$stateChangeSuccess', function (event, toState) {
      updateStateTitle(toState);
    });

    $rootScope.$on('updateStateTitle', function (event, state) {
      updateStateTitle(state);
    });

    function updateStateTitle(state) {
      var titles = [pageTitle];
      if(angular.isDefined(state.data) && angular.isDefined(state.data.title)) {
        titles.unshift(state.data.title);
      }
      $rootScope.pageTitle = titles.join(' &mdash; ');
    }

    // Until the `ui-sref` directive allows dynamic state references,
    // we need to manually store and trigger dynamic states.
    // https://github.com/angular-ui/ui-router/issues/395

    // TODO
    // Perhaps a full history could be stored for each state,
    // instead of only one-level back.

    // List of all dynamic state references.
    $rootScope.srefs = {};

    // Register a dynamic state reference.
    function registerSref(stateName, toState, toParams) {
      $rootScope.srefs[stateName] = $state.href(toState, toParams);
    }

    // Registering the default dynamic states.
    registerSref('app.search', 'app.search');

    // Whenver the state changes, override the dynamic href
    // generated for any of the decendants of the base state.
    $rootScope.$on('$stateChangeSuccess', function (event, toState, toParams) {
      angular.forEach($rootScope.srefs, function(value, key) {
        if( $state.includes(key) ) {
          registerSref(key, toState, toParams);
        }
      });
    });

    //
    // Caching
    //

    // Override the default cache with an `angular-cache`,
    // with the default cache options.
    $http.defaults.cache = $angularCacheFactory('defaultCache');

  });
