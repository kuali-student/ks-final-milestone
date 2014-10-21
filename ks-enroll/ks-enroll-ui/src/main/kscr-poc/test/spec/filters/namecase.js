'use strict';

describe('Filter: namecase', function () {

  // load the filter's module
  beforeEach(module('kscrPocApp'));

  // initialize a new instance of the filter before each test
  var namecase;
  beforeEach(inject(function ($filter) {
    namecase = $filter('namecase');
  }));

  it('should return the input prefixed with "namecase filter:"', function () {
    var text = 'angularjs';
    expect(namecase(text)).toBe('namecase filter: ' + text);
  });

});
