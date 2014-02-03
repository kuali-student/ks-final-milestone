'use strict';

angular.module('regCartApp')
    .service('CartService', function CartService($resource) {
        this.getCart = function () {
            return $resource('json/static-cart.json', {}, {
                query:{method:'GET', cache:false, isArray:true}
            });
        };

    });
