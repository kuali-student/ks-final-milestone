'use strict';

angular.module('kscrPocApp')
  .factory('pagingService', function () {
    
    var pagingInstances = {};

    function cleanArray(arr) {
      var newArray = [];
      for( var i = 0, l = arr.length; i < l; i++ ) {
        if( arr[i] ) {
          newArray.push( arr[i] );
        }
      }
      return newArray;
    }

    function Paging(id) {
      this.id = id;
      this._indexedItems = [];
      this.items = [];
      this.itemCount = 0;
      pagingInstances[id] = this;
    }

    Paging.prototype = {
      addItem: function(item, index) {
        index = angular.isNumber(index) ? index : this.items.length;
        this._indexedItems[ index ] = item;
        // In case of a custom index, this ensures that `items` doesn't contain
        // any `undefind`s, so it's cleaner to present.
        this.items = cleanArray(this._indexedItems);
        // Unfortunately, Angular doesn't like getters, so we have to
        // maintain this count manually.
        this.itemCount = this.items.length;
      },
      getItem: function(index, indexModifier) {
        // Transform into numbers, since the index is probably a string,
        // pulled from the URL.
        index = angular.isString(index) ? parseInt(index) : index;
        // By default, don't alter the index, but add the modifier if provided.
        index += angular.isDefined(indexModifier) ? indexModifier : 0;
        // Find and return the item.
        return this._indexedItems[index] || null;
      },
      item: function(index) {
        return this.getItem(index, 0);
      },
      next: function(index) {
        return this.getItem(index, 1);
      },
      previous: function(index) {
        return this.getItem(index, -1);
      },
      // Starts fresh
      clean: function() {
        this._indexedItems = [];
        this.items = [];
        this.itemCount = 0;
        return this;
      }
    };

    function getOrRegister(id) {
      // Return a registered instance
      if( angular.isDefined(pagingInstances[id]) ) {
        return pagingInstances[id];
      }
      // Create a new instance
      return new Paging(id);
    }

    return {
      get: function (id) {
        return getOrRegister(id);
      },
      clean: function(id) {
        var instance = getOrRegister(id);
        instance.clean();
        return instance;
      }
    };
  });
