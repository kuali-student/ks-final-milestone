'use strict';

describe('Controller: CartCtrl', function () {

    // load the controller's module
    beforeEach(module('regCartApp'));

    var CartCtrl,
        scope,
        $httpBackend;



    // Initialize the controller and a mock scope
    beforeEach(inject(function ($controller, $rootScope, _$httpBackend_) {
        scope = $rootScope.$new();
        CartCtrl = $controller('CartCtrl', {
            $scope: scope
        });

        $httpBackend = _$httpBackend_;
    }));


    // Test init
    it('should attach an empty list of cartResults to the scope', function () {
        expect(scope.cartResults).toEqual({items: []});
    });

    // Test getStatusMessageFromStatus()
    it('should get the transformed status message from the status', function () {
        expect(scope.getStatusMessageFromStatus('success')).toBe(' - Success!');
        expect(scope.getStatusMessageFromStatus('error')).toBe(' - Failed!');
        expect(scope.getStatusMessageFromStatus('action')).toBe(' - Failed!');
        expect(scope.getStatusMessageFromStatus('')).toBe('');
    });

    // Test cancelNewCartItem()
    it('should cancel the new cart item', function() {
        scope.newCartItem = {};
        scope.showNew = true;

        scope.cancelNewCartItem();

        expect(scope.newCartItem).toBeNull();
        expect(scope.showNew).toBeFalsy();
    });

    // Test removeAlertMessage(cartItem);
    it('should remove the alert message from the cart item', function() {
        var cartItem = { alertMessage: 'test message' };

        scope.removeAlertMessage(cartItem);

        expect(cartItem.alertMessage).toBeNull();
    });

    // Test removeUserMessage()
    it('should remove the error message from the user', function() {
        scope.userMessage = {
            txt: 'text message',
            linkText: 'test link test'
        };

        scope.removeUserMessage();

        expect(scope.userMessage.txt).toBeNull();
        expect(scope.userMessage.linkText).toBeNull();
    });

    // Test showBadge(cartItem)
    it('should correctly check whether or not to show a badge for a cart item', function() {
        var cartItem = {
            grading: 'Letter',
            gradingOptions: []
        };

        cartItem.gradingOptions[cartItem.grading] = 'Letter';
        expect(scope.showBadge(cartItem)).toBeFalsy();

        cartItem.gradingOptions[cartItem.grading] = 'Audit';
        expect(scope.showBadge(cartItem)).toBeTruthy();
    });
});
