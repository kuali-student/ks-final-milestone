'use strict';

describe('Service: regGroupService', function () {

  // load the service's module
  beforeEach(module('kscrPocApp'));

  // instantiate service
  var regGroupService;
  beforeEach(inject(function (_regGroupService_) {
    regGroupService = _regGroupService_;
  }));

  it('should do something', function () {
    expect(!!regGroupService).toBe(true);
  });

});
