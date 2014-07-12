package org.singularitylab.connector.payu

import org.singularitylab.connector.payu.event.PaymentEvent
import org.springframework.context.ApplicationListener

/**
 *
 *
 * @author Jakub Dzon
 *
 */
class PaymentListeninigService implements ApplicationListener<PaymentEvent> {
    @Override
    void onApplicationEvent(PaymentEvent event) {
        println event
    }
}
