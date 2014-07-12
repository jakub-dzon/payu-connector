class PayuConnectorGrailsPlugin {
    def version = "1.0.0"
    def grailsVersion = "2.2 > *"
    def pluginExcludes = [
            "grails-app/views/error.gsp"
    ]
    def title = "Payu Connector Plugin"
    def author = "Jakub Dzon"
    def authorEmail = "jakub@dzon.pl"
    def description = '''Grails plugin providing easy way of integrating with PayU on-line payment gateway (http://www.payu.pl)'''
    def documentation = "http://grails.org/plugin/payu-connector"
    def license = "APACHE"
    def developers = [[name: "Jakub Dzon", email: "jakub@dzon.pl"]]
    def issueManagement = [system: "github", url: "https://github.com/jakub-dzon/payu-connector/issues"]
    def scm = [url: "https://github.com/jakub-dzon/payu-connector"]

}
