'use strict';

describe('Controller: AppSearchResultsDetailsCtrl', function () {

  // load the controller's module
  beforeEach(module('kscrPocApp'));

  var AppSearchResultsDetailsCtrl,
    scope;

  // Initialize the controller and a mock scope
  beforeEach(inject(function ($controller, $rootScope) {
    scope = $rootScope.$new();
    AppSearchResultsDetailsCtrl = $controller('AppSearchResultsDetailsCtrl', {
      $scope: scope
    });
  }));

  it('should attach a list of awesomeThings to the scope', function () {
    expect(scope.awesomeThings.length).toBe(3);
  });
});
