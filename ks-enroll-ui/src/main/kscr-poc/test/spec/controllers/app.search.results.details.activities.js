'use strict';

describe('Controller: AppSearchResultsDetailsActivitiesCtrl', function () {

  // load the controller's module
  beforeEach(module('kscrPocApp'));

  var AppSearchResultsDetailsActivitiesCtrl,
    scope;

  // Initialize the controller and a mock scope
  beforeEach(inject(function ($controller, $rootScope) {
    scope = $rootScope.$new();
    AppSearchResultsDetailsActivitiesCtrl = $controller('AppSearchResultsDetailsActivitiesCtrl', {
      $scope: scope
    });
  }));

  it('should attach a list of awesomeThings to the scope', function () {
    expect(scope.awesomeThings.length).toBe(3);
  });
});
