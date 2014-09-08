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
    it('should get the transformed status message from the status', inject(function (STATUS) {
        expect(scope.getStatusMessageFromStatus(STATUS.success)).toContain('- Success');
        expect(scope.getStatusMessageFromStatus(STATUS.error)).toContain('- Failed');
        expect(scope.getStatusMessageFromStatus(STATUS.action)).toContain('- Failed');
        expect(scope.getStatusMessageFromStatus('')).toBe('');
    }));

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
    // Disabled to since card was moved to directive
    xit('should correctly check whether or not to show a badge for a cart item', inject(function(GRADING_OPTION) {
        var cartItem = { grading: '' };

        cartItem.grading = GRADING_OPTION.letter;
        expect(scope.showBadge(cartItem)).toBeFalsy();

        cartItem.grading = GRADING_OPTION.audit;
        expect(scope.showBadge(cartItem)).toBeTruthy();

        cartItem.grading = GRADING_OPTION.passFail;
        expect(scope.showBadge(cartItem)).toBeTruthy();
    }));
});
