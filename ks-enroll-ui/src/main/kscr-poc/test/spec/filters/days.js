'use strict';

describe('Filter: days', function () {

  // load the filter's module
  beforeEach(module('kscrPocApp'));

  // initialize a new instance of the filter before each test
  var days;
  beforeEach(inject(function ($filter) {
    days = $filter('days');
  }));

  it('should return the input prefixed with "days filter:"', function () {
    var text = 'angularjs';
    expect(days(text)).toBe('days filter: ' + text);
  });

});
