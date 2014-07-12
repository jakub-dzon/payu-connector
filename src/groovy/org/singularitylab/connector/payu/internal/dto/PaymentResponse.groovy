package org.singularitylab.connector.payu.internal.dto

import groovy.transform.ToString

/**
 *
 *
 * @author Jakub Dzon
 *
 */
@ToString
class PaymentResponse {
    String resId
    String orderId
    String extOrderId
    String redirectUri
    String currencyCode
    Status status
    String version
    def payMethods
    def properties

    @ToString
    static class Status {
        String statusCode
        String code
        String codeLiteral
        String statusDesc
        String severity
        String location
    }

}


