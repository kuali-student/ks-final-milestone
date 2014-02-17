'use strict';

describe('Service: CartService', function () {

  // load the service's module
  beforeEach(module('regCartApp'));

  // instantiate service
  var CartService;
  beforeEach(inject(function (_CartService_) {
    CartService = _CartService_;
  }));

  it('should do something', function () {
    expect(!!CartService).toBe(true);
  });

});
