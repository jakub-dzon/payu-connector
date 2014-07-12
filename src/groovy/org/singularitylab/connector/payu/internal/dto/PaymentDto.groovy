package org.singularitylab.connector.payu.internal.dto

import groovy.transform.ToString
import org.singularitylab.connector.payu.Buyer
import org.singularitylab.connector.payu.Payment
import org.singularitylab.connector.payu.Product

/**
 *
 *
 * @author Jakub Dzon
 *
 */
@ToString
class PaymentDto {
    String notifyUrl
    String customerIp
    String description
    String currencyCode
    BigDecimal totalAmount
    Products products
    String merchantPosId
    String extOrderId
    String completeUrl
    BuyerDto buyer

    PaymentDto(Payment payment) {
        this.description = payment.description
        this.currencyCode = payment.currencyCode
        this.extOrderId = payment.orderId
        this.products = new Products(products: payment.products.collect { new ProductDto(it) })
        this.totalAmount = payment.products.sum { it.unitPrice * it.quantity }
        if (payment.buyer) {
            this.buyer = new BuyerDto(payment.buyer)
        }
    }

    PaymentDto() {
    }

    static class ProductDto {
        String name
        BigDecimal unitPrice
        int quantity
        String code

        ProductDto(Product product) {
            this.name = product.name
            this.unitPrice = product.unitPrice
            this.quantity = product.quantity
            this.code = product.id
        }
    }

    static class Products {
        List<ProductDto> products
    }

    static class BuyerDto {
        String phone
        String email
        String firstName
        String lastName
        String extCustomerId

        BuyerDto(Buyer buyer) {
            this.phone = buyer.phone
            this.email = buyer.email
            this.firstName = buyer.firstName
            this.lastName = buyer.lastName
            this.extCustomerId = buyer.id
        }
    }
}


