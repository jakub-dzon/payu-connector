package org.singularitylab.connector.payu

import groovy.transform.ToString

/**
 *
 *
 * @author Jakub Dzon
 *
 */
@ToString
class Product implements Serializable {
    String name
    BigDecimal unitPrice
    int quantity

    static belongsTo = [payment: Payment]

    static constraints = {
        name blank: false
        quantity min: 1
    }

    static marshalling = {
        shouldOutputIdentifier false
        shouldOutputVersion false
        shouldOutputClass false
    }
}
