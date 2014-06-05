'use strict';

angular.module('regCartApp')
    .service('CartService', ['ServiceUtilities', 'URLS', function CartService(ServiceUtilities, URLS) {

        this.getCart = function () {
            return ServiceUtilities.getData(URLS.courseRegistrationCart+'/searchForCart');
        };

        this.getGradingOptions = function () {
            return ServiceUtilities.getData(URLS.courseRegistrationCart+'/getStudentRegistrationOptions');
        };

        this.addCourseToCart = function () {
            return ServiceUtilities.postData(URLS.courseRegistrationCart+'/addCourseToCart');
        };

        this.removeItemFromCart = function ($actionLink) {
            return ServiceUtilities.deleteData($actionLink);
        };

        this.invokeActionLink = function ($actionLink) {
            return ServiceUtilities.getData($actionLink);
        };

        this.updateCartItem = function () {
            return ServiceUtilities.putData(URLS.courseRegistrationCart+'/updateCartItem');
        };

        this.submitCart = function () {
            return ServiceUtilities.getData(URLS.courseRegistrationCart+'/submitCart');
        };

        this.undoDeleteCourse = function () {
            return ServiceUtilities.getData(URLS.courseRegistrationCart+'/undoDeleteCourse');
        };
    }]);