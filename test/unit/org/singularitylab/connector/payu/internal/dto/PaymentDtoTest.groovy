package org.singularitylab.connector.payu.internal.dto

import org.junit.Test
import org.singularitylab.connector.payu.Payment
import org.singularitylab.connector.payu.Product;

import static org.junit.Assert.*;

public class PaymentDtoTest {


    public static final String ANY_ORDER_ID = "order-id"
    public static final String ANY_CURRENCY = "PLN"
    public static final String ANY_DESCRIPTION = "Some description"

    @Test
    public void shouldCreatePaymentRequest() {
        //Given

        def product1 = new Product(name: "p1", unitPrice: 100, quantity: 1)
        def product2 = new Product(name: "p2", unitPrice: 66, quantity: 3)

        def products = [product1, product2]
        def payment = new Payment(orderId: ANY_ORDER_ID, currencyCode: ANY_CURRENCY, description: ANY_DESCRIPTION, products: products)

        //When
        def paymentRequest = new PaymentDto(payment)

        //Then
        assertEquals 298, paymentRequest.totalAmount
        assertEquals ANY_ORDER_ID, paymentRequest.extOrderId
        assertEquals ANY_CURRENCY, paymentRequest.currencyCode
        assertEquals ANY_DESCRIPTION, paymentRequest.description
        assertTrue paymentRequest.products.products.contains(product1)
        assertTrue paymentRequest.products.products.contains(product2)
    }

}