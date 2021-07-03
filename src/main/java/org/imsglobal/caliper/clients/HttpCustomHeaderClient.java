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
import java.util.ArrayList;
import java.util.List;

public class HttpCustomHeaderClient extends AbstractClient {
    private static CloseableHttpClient httpClient;
    private static CloseableHttpResponse response = null;
    private List<HttpCustomHeader> customHeaders;

    private static final Logger log = LoggerFactory.getLogger(HttpClient.class);

    /**
     * Constructor. The args options provides the host details to the HttpCustomHeaderClient.
     * Scope is private to force use of the static factory method for instantiating an HttpCustomHeaderClient.
     */
    private HttpCustomHeaderClient(String id, HttpClientOptions options) {
        super(id, options);
        initialize();

        if (customHeaders == null) {
            customHeaders = new ArrayList<>();
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
            throw new IllegalStateException("HttpCustomHeaderClient is not initialized.");
        }
    }

    /**
     * Set a custom header. (Cannot set prohibited header declared in HttpCustomHeader class)
     * @param field: HTTP Header field
     * @param value: HTTP Header value for the name
     */
    public void setCustomHeader(String field, String value) {
        HttpCustomHeader header;
        int index = findCustomHeader(field.toLowerCase());

        if (index >= 0) {
            header = customHeaders.get(index);
            header.setValue(value);
        } else {
            header = new HttpCustomHeader(field, value);
            customHeaders.add(header);
        }
    }

    /**
     * Set custom header from list.
     * @param headers: List of custom headers
     */
    public void setCustomHeaders(List<HttpCustomHeader> headers) {
        customHeaders = headers;
    }

    /**
     * Getter function of custom header list.
     * @return custom header list
     */
    public List<HttpCustomHeader> getCustomHeaders() {
        return customHeaders;
    }

    /**
     * Find specific custom header from custom headers list
     * @param field: HTTP Header field
     * @return index of field from custom headers list
     */
    public int findCustomHeader(String field) {
        int index = -1;
        for (HttpCustomHeader header: customHeaders) {
            if (header.getField().equals(field.toLowerCase())) {
                index = customHeaders.indexOf(header);
                break;
            }
        }

        return index;
    }

    /**
     * Remove custom header from the list of custom headers.
     * @param field: HTTP Header field
     */
    public void removeCustomHeader(String field) {
        int index = findCustomHeader(field);

        if (index >= 0) {
            customHeaders.remove(index);
        }
    }

    /**
     * Remove all custom headers from the list of custom headers.
     */
    public void removeAllCustomHeaders() {
        customHeaders.clear();
    }

    /**
     * Get custom header and add to HTTP header
     * @param post: HttpPost object to send Envelope
     */
    private void applyCustomHeaders(HttpPost post) {
        for (HttpCustomHeader header : customHeaders) {
            post.setHeader(header.getField(), header.getValue());
        }
    }

    /**
     * Post envelope.
     * @param envelope Caliper envelope to be sent
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
            applyCustomHeaders(post);

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
     * Factory method for instantiating an HttpCustomHeaderClient.
     * @param id
     * @return HttpCustomHeaderClient
     */
    public static HttpCustomHeaderClient create(String id, HttpClientOptions options) {
        return new HttpCustomHeaderClient(id, options);
    }
}
