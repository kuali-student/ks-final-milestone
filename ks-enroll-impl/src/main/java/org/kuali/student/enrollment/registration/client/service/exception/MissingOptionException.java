package org.kuali.student.enrollment.registration.client.service.exception;

import org.kuali.student.enrollment.registration.client.service.dto.CartItemResult;
import org.kuali.student.enrollment.registration.client.service.dto.CartResult;

/**
 * Created with IntelliJ IDEA.
 * User: Daniel
 * Date: 2/19/14
 * Time: 9:47 AM
 * To change this template use File | Settings | File Templates.
 */
public class MissingOptionException extends Exception{
    private CartItemResult cartItemOptions;

    public MissingOptionException(CartItemResult cartItemOptions) {
        this.cartItemOptions = cartItemOptions;
    }

    public CartItemResult getCartItemOptions() {
        return cartItemOptions;
    }
}
