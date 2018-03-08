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
import java.util.HashMap;

public class HttpCustomHeaderClient extends AbstractClient {
    private static CloseableHttpClient httpClient;
    private static CloseableHttpResponse response = null;
    private static HashMap<String, String> customHeaders = null;

    private static final Logger log = LoggerFactory.getLogger(HttpClient.class);

    /**
     * Constructor. The args options provides the host details to the HttpCustomHeaderClient.
     * Scope is private to force use of the static factory method for instantiating an HttpCustomHeaderClient.
     */
    private HttpCustomHeaderClient(String id, HttpClientOptions options) {
        super(id, options);
        initialize();

        if (customHeaders == null) {
            customHeaders = new HashMap<>(0);
        }
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
     * Set custom header. (Except 'Content-Type' and 'Authorization')
     * @param field: HTTP Header field
     * @param value: HTTP Header value for the name
     */
    public void setCustomHeader(String field, String value) {

        /* Because HTTP header fields are case-insensitive. (From RFC 7230 Section 3.2 "Header Fields") */
        String lowerCaseField = field.toLowerCase();

        if (lowerCaseField.equals("content-type") || lowerCaseField.equals("authorization")) {
            return;
        }

        customHeaders.put(field, value);
    }

    /**
     * Get custom header and add to HTTP header
     * @param post: HttpPost object to send Envelope
     */
    private void getCustomHeader(HttpPost post) {
        for (String name : customHeaders.keySet()) {
            post.setHeader(name, customHeaders.get(name));
        }
    }

    /**
     * Post envelope.
     * @param envelope
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
            post.setHeader("Authorization", this.getOptions().getApiKey());
            post.setHeader("Content-Type", this.getOptions().getContentType());
            post.setEntity(new StringEntity(json, ContentType.APPLICATION_JSON));

            // Get custom header and set header to HttpPost object
            getCustomHeader(post);

            // Execute POST
            response = httpClient.execute(post);

            // HTTP Response code
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode < 200 || statusCode > 202) {
                response.close();

                // Update statistics
                updateStatistics(Boolean.TRUE);

                throw new RuntimeException("WARN: HTTP POST failed; status code=" + statusCode);
            } else {
                if (log.isDebugEnabled()) {
                    log.debug(response.getStatusLine().toString());
                    log.debug(EntityUtils.toString(response.getEntity()));
                }
                response.close();

                // Update statistics
                updateStatistics(Boolean.FALSE);

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
     * Factory method for instantiating an HttpCustomHeaderClient.
     * @param id
     * @return HttpCustomHeaderClient
     */
    public static HttpCustomHeaderClient create(String id, HttpClientOptions options) {
        return new HttpCustomHeaderClient(id, options);
    }
}
