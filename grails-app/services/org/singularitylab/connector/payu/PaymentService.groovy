package org.singularitylab.connector.payu

import grails.plugins.rest.client.RestBuilder
import grails.util.Holders
import org.singularitylab.connector.payu.internal.dto.PaymentDto
import org.singularitylab.connector.payu.internal.dto.PaymentResponse

/**
 * Responsibel for sending payment requests to PayU REST API.
 * Application using the service has to define following configuration properties:
 * <ul>
 *     <li>{@code payu.api.posId} - POS ID</li>
 *     <li>{@code payu.api.key} - PayU API '2nd key'</li>
 *     <li>{@code payu.api.url} - PayU service URL</li>
 *     <li>{@code payu.redirects.completed.controller} - Name of a controller that the user is redirected to after the payment</li>
 *     <li>{@code payu.redirects.completed.action} - Name of an action within {@code payu.redirects.completed.controller} that the user is redirected to after the payment</li>
 * </ul>
 *
 * @author Jakub Dzon
 */
class PaymentService {

    def posId = Holders.config.payu.api.posId

    def key = Holders.config.payu.api.key

    def url = Holders.config.payu.api.url

    def completeController = Holders.config.payu.redirects.completed.controller

    def completeAction = Holders.config.payu.redirects.completed.action

    def authKey = "${posId}:${key}".bytes.encodeBase64().toString()

    def restBuilder = new RestBuilder()

    /**
     * Send REST payment request to PayU service.
     *
     * @param payment {@link Payment} object with data that will be sent to the PayU service.
     * @param clientIp IP address of the user
     * @return URL the user has to be redirected to proceed with the payment
     * @throws PayuException if PayU responds with HTTP status != 200
     */
    def pay(Payment payment, String clientIp) {
        def paymentRequest = new PaymentDto(payment)
        paymentRequest.merchantPosId = posId
        paymentRequest.notifyUrl = "${Holders.config.grails.serverURL}/payment/processNotification"
        paymentRequest.customerIp = clientIp
        paymentRequest.completeUrl = "${Holders.config.grails.serverURL}/${completeController}/${completeAction}"

        requestPayment(paymentRequest)
    }

    private def requestPayment(PaymentDto paymentRequest) {
        def result = restBuilder.post(url) {
            header 'Authorization', 'Basic ' + authKey
            json paymentRequest
        }

        log.info(result.text)

        if (200 == result.status) {
            def response = new PaymentResponse(result.json)
            response.redirectUri
        } else {
            throw new PayuException("Error response received. Status: ${result.status}. Text: ${result.text} ")
        }
    }

}
