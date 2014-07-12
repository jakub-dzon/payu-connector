// configuration for plugin testing - will not be included in the plugin zip

log4j = {
    error 'org.codehaus.groovy.grails.web.servlet',  //  controllers
            'org.codehaus.groovy.grails.web.pages', //  GSP
            'org.codehaus.groovy.grails.web.sitemesh', //  layouts
            'org.codehaus.groovy.grails.web.mapping.filter', // URL mapping
            'org.codehaus.groovy.grails.web.mapping', // URL mapping
            'org.codehaus.groovy.grails.commons', // core / classloading
            'org.codehaus.groovy.grails.plugins', // plugins
            'org.codehaus.groovy.grails.orm.hibernate', // hibernate integration
            'org.springframework',
            'org.hibernate',
            'net.sf.ehcache.hibernate'

    warn 'org.mortbay.log'

    debug 'grails.app.controllers.org.singularitylab.connector.payu',
            'grails.app.services.org.singularitylab.connector.payu'
}

grails.serverURL = "http://87.207.75.188:8080/payu-connector"

payu {
    api {
        url = "https://secure.payu.com/api/v2/orders"
        key = "13a980d4f851f3d9a1cfc792fb1f5e50"
        posId = "145227"
    }
    redirects {
        completed {
            controller = "completed"
            action = "completeAction"
        }
    }
    mode = "test"
}
