package org.singularitylab.connector.payu

import groovy.transform.ToString

@ToString
class Buyer implements Serializable {

    String phone
    String email
    String firstName
    String lastName

    static constraints = {
        phone blank: true, nullable: true
        email blank: false
        firstName blank: false
        lastName blank: false
    }

    static belongsTo = Payment
}
