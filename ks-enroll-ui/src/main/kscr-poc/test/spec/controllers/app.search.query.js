'use strict';

describe('Controller: AppSearchQueryCtrl', function () {

  // load the controller's module
  beforeEach(module('kscrPocApp'));

  var AppSearchQueryCtrl,
    scope;

  // Initialize the controller and a mock scope
  beforeEach(inject(function ($controller, $rootScope) {
    scope = $rootScope.$new();
    AppSearchQueryCtrl = $controller('AppSearchQueryCtrl', {
      $scope: scope
    });
  }));

  it('should attach a list of awesomeThings to the scope', function () {
    expect(scope.awesomeThings.length).toBe(3);
  });
});
