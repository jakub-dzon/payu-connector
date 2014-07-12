package org.singularitylab.connector.payu

import grails.util.Holders
import org.singularitylab.connector.payu.event.PaymentEvent

class PaymentController {

    static scaffold = true

    def paymentService

    def execute = {
        def remoteAddr = request.remoteAddr

        def id = params.id

        log.info("Retrieving payment for ID=${id}")

        def payment = Payment.get(id)

        log.info("Executing payment ${payment}")

        def redirectUrl = paymentService.pay(payment, remoteAddr)

        redirect(url: redirectUrl)
    }

    def processNotification = {
        def json = request.JSON

        log.info("Received notification: ${json}")

        def internalOrderId = json.order.extOrderId
        def newStatus = Payment.PaymentStatus.valueOf(json.order.status)

        if (log.isDebugEnabled()) {
            log.debug("New status: ${newStatus}")
        }

        def payment = Payment.findByOrderId(internalOrderId)
        if (payment) {
            if (log.isDebugEnabled()) {
                log.debug("Payment: ${payment}")
            }

            if (payment.status.isValidStateTransition(newStatus)) {
                payment.status = newStatus
                payment.fee = json.order.fee
                payment.save()
                publishEvent(new PaymentEvent(this, payment.id, newStatus))
            }
        } else {
            log.warn("Payment for ${internalOrderId} has not been found")
        }

    }

    def test = {
        def mode = Holders.config.payu.mode
        if (mode && mode == "test") {
            def orderId = UUID.randomUUID().toString()
            def payment = new Payment(currencyCode: "PLN", description: "X description", orderId: orderId, status: Payment.PaymentStatus.NEW)

            payment.addToProducts(new Product(name: "p1", unitPrice: 1000, quantity: 1))
            payment.addToProducts(new Product(name: "p2", unitPrice: 555, quantity: 1))
            payment.buyer = new Buyer(firstName: "Foo", lastName: "Bar", email: "jakub@geecon.org", phone: "+48 123 45 67 89")

            payment.save(failOnError: true)

            if (log.isDebugEnabled()) {
                log.debug("Payment created: ${payment.id}")
            }
            redirect(action: execute, id: payment.id)
        }
    }
}
