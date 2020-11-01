/**
 * This file is part of IMS Caliper Analytics™ and is licensed to
 * IMS Global Learning Consortium, Inc. (http://www.imsglobal.org)
 * under one or more contributor license agreements.  See the NOTICE
 * file distributed with this work for additional information.
 *
 * IMS Caliper is free software: you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation, version 3 of the License.
 *
 * IMS Caliper is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE.  See the GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License along
 * with this program. If not, see <http://www.gnu.org/licenses/>.
 */

package org.imsglobal.caliper.clients;

import org.apache.http.HttpHeaders;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.imsglobal.caliper.Envelope;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * Provisions the Sensor with an HttpClient that binds to one or more Requestors.
 */
public class HttpClient extends AbstractClient {
    private static CloseableHttpClient httpClient;
    private static CloseableHttpResponse response = null;

    private static final Logger log = LoggerFactory.getLogger(HttpClient.class);

    /**
     * Constructor.  The args options provides the host details to the HttpClient.  Scope is private
     * to force use of the static factory method for instantiating an HttpClient.
     * @param id
     */
    private HttpClient(String id, HttpClientOptions options) {
        super(id, options);
        initialize();
    }

    /**
     * Init method
     */
    public static synchronized void initialize() {
        if (httpClient == null) {
            httpClient = HttpClients.createDefault();
        }
    }

    /**
     * Check initialized instance.
     */
    private static void checkInitialized() {
        if (httpClient == null) {
            throw new IllegalStateException("HttpClient is not initialized.");
        }
    }

    /**
     * Post envelope.
     * @param envelope
     * @return status
     */
    @Override
    public void send(Envelope envelope) {
        boolean status = Boolean.FALSE;

        try {
            if (log.isDebugEnabled()) {
                log.debug("Entering send()...");
            }

            // Check if HttpClient is initialized.
            checkInitialized();

            // Serialize the envelope
            String json = this.serializeEnvelope(envelope);

            // Prep the post
 
            HttpPost post = new HttpPost(super.getOptions().getHost());
            post.setHeader(HttpHeaders.AUTHORIZATION, this.getOptions().getApiKey());
            post.setHeader(HttpHeaders.CONTENT_TYPE, this.getOptions().getContentType());
            post.setHeader(HttpHeaders.ACCEPT, HttpHeaders.ACCEPT);
            
            
            post.setEntity(new StringEntity(json));

            // Execute POST
            response = httpClient.execute(post);

            // HTTP Response code
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode < 200 || statusCode > 202) {
                response.close();

                // Update statistics
                updateStatistics(Boolean.FALSE);

                throw new RuntimeException("WARN: HTTP POST failed; status code=" + statusCode);
            } else {
                if (log.isDebugEnabled()) {
                    log.debug(response.getStatusLine().toString());
                    log.debug(EntityUtils.toString(response.getEntity()));
                }
                response.close();

                // Update statistics
                updateStatistics(Boolean.TRUE);

                if (log.isDebugEnabled()) {
                    log.debug("Exiting send()...");
                }
            }
        } catch (ClientProtocolException cpe) {
            cpe.printStackTrace();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    /**
     * Factory method for instantiating an HttpClient.
     * @param id
     * @return HttpClient
     */
    public static HttpClient create(String id, HttpClientOptions options) {
        return new HttpClient(id, options);
    }
}