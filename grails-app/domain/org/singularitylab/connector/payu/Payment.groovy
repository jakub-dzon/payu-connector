package org.singularitylab.connector.payu

import groovy.transform.ToString

/**
 *
 *
 * @author Jakub Dzon
 *
 */
@ToString
class Payment implements Serializable {
    String payuOrderId
    String currencyCode
    String orderId
    String description
    String fee
    List<Product> products
    PaymentStatus status
    Buyer buyer

    Date dateCreated
    Date lastUpdated

    static hasMany = [products: Product]

    static constraints = {
        orderId blank: false
        payuOrderId blank: true, nullable: true
        currencyCode blank: false
        description blank: true
        status blank: true
        buyer nullable: true
        fee nullable: true
    }

    static enum PaymentStatus {
        COMPLETED([]),
        REJECTED([COMPLETED]),
        CANCELED([REJECTED]),
        PENDING([CANCELED, COMPLETED]),
        NEW([PENDING, CANCELED, COMPLETED])

        Set<PaymentStatus> allowedTransitions = new HashSet<>()

        PaymentStatus(Collection<PaymentStatus> allowedTransitions) {
            this.allowedTransitions.addAll(allowedTransitions)
        }

        def isValidStateTransition(PaymentStatus newStatus) {
            boolean contains = allowedTransitions.contains(newStatus)
            contains
        }
    }

}
