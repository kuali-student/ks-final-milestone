'use strict';

describe('Filter: credits', function () {

  // load the filter's module
  beforeEach(module('kscrPocApp'));

  // initialize a new instance of the filter before each test
  var credits;
  beforeEach(inject(function ($filter) {
    credits = $filter('credits');
  }));

  it('should return the input prefixed with "credits filter:"', function () {
    var text = 'angularjs';
    expect(credits(text)).toBe('credits filter: ' + text);
  });

});
