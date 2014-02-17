'use strict';

describe('Controller: AppScheduleCtrl', function () {

  // load the controller's module
  beforeEach(module('kscrPocApp'));

  var AppScheduleCtrl,
    scope;

  // Initialize the controller and a mock scope
  beforeEach(inject(function ($controller, $rootScope) {
    scope = $rootScope.$new();
    AppScheduleCtrl = $controller('AppScheduleCtrl', {
      $scope: scope
    });
  }));

  it('should attach a list of awesomeThings to the scope', function () {
    expect(scope.awesomeThings.length).toBe(3);
  });
});
