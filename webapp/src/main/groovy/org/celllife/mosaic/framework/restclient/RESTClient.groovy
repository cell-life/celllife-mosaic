package org.celllife.mosaic.framework.restclient

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service

/**
 * Client that invokes a REST service and returns a map of the data result
 */
@Service
class RESTClient {
	
	@Value('${internal.username}')
	def String username;
	
	@Value('${internal.password}')
	def String password;

    def get(String uri) {

		def client = new groovyx.net.http.RESTClient(uri)
        client.auth.basic(username, password)

        return client.get([:]).data
    }

    def get(String uri, Map<String, Object> query) {

        def client = new groovyx.net.http.RESTClient(uri)
        client.auth.basic(username, password)

        return client.get(query:query).data
    }
}