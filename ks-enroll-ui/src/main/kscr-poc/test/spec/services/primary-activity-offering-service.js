'use strict';

describe('Service: primaryActivityOfferingService', function () {

  // load the service's module
  beforeEach(module('kscrPocApp'));

  // instantiate service
  var primaryActivityOfferingService;
  beforeEach(inject(function (_primaryActivityOfferingService_) {
    primaryActivityOfferingService = _primaryActivityOfferingService_;
  }));

  it('should do something', function () {
    expect(!!primaryActivityOfferingService).toBe(true);
  });

});
