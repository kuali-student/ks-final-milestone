package org.kuali.student.enrollment.registration.client.service.dto;


import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CartResult", propOrder = {
        "cartId", "termId", "items"})
public class CartResult {

    private String cartId;
    private String termId;
    private List<CartItemResult> items;

    public String getCartId() {
        return cartId;
    }

    public void setCartId(String cartId) {
        this.cartId = cartId;
    }

    public List<CartItemResult> getItems() {
        if (items == null) {
            items = new ArrayList<CartItemResult>();
        }
        return items;
    }

    public void setItems(List<CartItemResult> items) {
        this.items = items;
    }

    public String getTermId() {
        return termId;
    }

    public void setTermId(String termId) {
        this.termId = termId;
    }
}
