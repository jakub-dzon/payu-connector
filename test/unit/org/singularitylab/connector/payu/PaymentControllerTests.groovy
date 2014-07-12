package org.singularitylab.connector.payu


import grails.test.mixin.*
import grails.test.mixin.domain.DomainClassUnitTestMixin
import org.junit.Before
import org.singularitylab.connector.payu.PaymentController
import org.singularitylab.connector.payu.event.PaymentEvent
import org.springframework.context.ApplicationEvent

@TestFor(PaymentController)
@Mock(Payment)
class PaymentControllerTests {

    public static final String REDIRECT_URL = 'http://some.url.com/test'
    private paymentStub

    @Before
    public void setUp() throws Exception {
        paymentStub = new Payment(orderId: "order-id", currencyCode: "PLN", status: Payment.PaymentStatus.NEW, description: "Descript.").save()
    }

    void testExecute() {
        //Given
        params.id = this.paymentStub.id
        def paymentService = mockFor(PaymentService)
        paymentService.demand.pay(1) { Payment payment, String ip -> REDIRECT_URL }
        controller.paymentService = paymentService.createMock()

        //When
        controller.execute()

        //Then
        assert response.redirectUrl == REDIRECT_URL
    }

    void testNotificationForStateChange() {
        //Given
        request.json = '{order:{extOrderId:"order-id", status:"PENDING"}}'
        def receivedEvent = null
        controller.metaClass.publishEvent = { ApplicationEvent event ->
            receivedEvent = event
        }

        //When
        controller.processNotification()

        //Then
        assert Payment.get(paymentStub.id).status == Payment.PaymentStatus.PENDING
        assertNotNull receivedEvent
        assert receivedEvent instanceof PaymentEvent
        assert receivedEvent.paymentId == paymentStub.id
        assert receivedEvent.newPaymentStatus == Payment.PaymentStatus.PENDING
    }

    void testNotificationForInvalidStateChange() {
        //Given
        request.json = '{order:{extOrderId:"order-id", status:"REJECTED"}}'
        def receivedEvent = null
        controller.metaClass.publishEvent = { ApplicationEvent event ->
            receivedEvent = event
        }

        //When
        controller.processNotification()

        //Then
        assert Payment.get(paymentStub.id).status == Payment.PaymentStatus.NEW
        assertNull receivedEvent
    }


}
