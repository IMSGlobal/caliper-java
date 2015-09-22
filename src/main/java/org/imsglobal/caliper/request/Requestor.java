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

package org.imsglobal.caliper.request;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.imsglobal.caliper.Sensor;
import org.imsglobal.caliper.payload.Envelope;
import org.joda.time.DateTime;

import java.io.UnsupportedEncodingException;
import java.util.List;

public abstract class Requestor<T> {

    /**
     * Constructor
     */
    public Requestor() {

    }

    /**
     * Send Caliper data to a target event store.
     * @param sensor
     * @param data
     * @return true/false boolean on success/failure
     */
    public abstract boolean send(Sensor sensor, T data);

    /**
     * Send a collection of Caliper data to a target event store.
     * @param sensor
     * @param data
     * @return true/false boolean on success/failure
     */
    public abstract boolean send(Sensor sensor, List<T> data);

    /**
     * Generate an HTTP StringEntity from the provided JSON string.  Set the ContentType to 'application/json'.
     * @param json
     * @return String Entity
     * @throws JsonProcessingException
     * @throws UnsupportedEncodingException
     */

    /**
     * Create Caliper envelope
     * @param sensor
     * @param data
     * @return
     */
    public Envelope createEnvelope(Sensor sensor, DateTime sendTime,  List<T> data) {
        return new Envelope<>(sensor, sendTime, data);
    }

    /**
     * Generate an HTTP StringEntity from the provided JSON string.  Set the ContentType to 'application/json'.
     * @param value
     * @param contentType
     * @return
     * @throws UnsupportedEncodingException
     */
    public StringEntity generatePayload(String value, ContentType contentType) throws UnsupportedEncodingException {
        StringEntity payload = new StringEntity(value);
        payload.setContentType(contentType.toString());

        return payload;
    }
}