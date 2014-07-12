package org.singularitylab.connector.payu.event

import groovy.transform.ToString
import org.singularitylab.connector.payu.Payment
import org.springframework.context.ApplicationEvent

/**
 *
 *
 * @author Jakub Dzon
 *
 */
@ToString
class PaymentEvent extends ApplicationEvent {

    Long paymentId
    Payment.PaymentStatus newPaymentStatus

    PaymentEvent(Object source, Long paymentId, Payment.PaymentStatus newPaymentStatus) {
        super(source)
        this.paymentId = paymentId
        this.newPaymentStatus = newPaymentStatus
    }
}
