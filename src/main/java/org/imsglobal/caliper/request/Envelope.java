package org.imsglobal.caliper.request;
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

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.imsglobal.caliper.Sensor;
import org.joda.time.DateTime;

import javax.annotation.Nonnull;

@JsonPropertyOrder({
    "@context",
    "@id",
    "@type",
    "sensor",
    "sendTime",
    "data" })
public abstract class Envelope<T> {

    @JsonProperty("@context")
    private EnvelopeContext context;

    @JsonProperty("@id")
    private String id;

    @JsonProperty("@type")
    private EnvelopeType type;

    @JsonProperty("sensor")
    private String sensorId;

    @JsonProperty("sendTime")
    private DateTime sendTime;

    @JsonProperty("data")
    private T data;

    /**
     * Constructor
     */
    public Envelope() {
        this.context = EnvelopeContext.CONTEXT;
        this.type = EnvelopeType.ENVELOPE;
    }

    /**
     * Constructor
     * @param id
     * @param sensor
     * @param sendTime
     */
    public Envelope(String id, Sensor sensor, DateTime sendTime) {
        this.id = id;
        this.context = EnvelopeContext.CONTEXT;
        this.type = EnvelopeType.ENVELOPE;
        this.sensorId = sensor.getId();
        this.sendTime = sendTime;
    }

    /**
     * Get the context.
     * @return the context
     */
    @Nonnull
    public EnvelopeContext getContext() {
        return context;
    }

    /**
     * Get the id.
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * Set the identifier
     * @param id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Get the type.
     * @return the type
     */
    @Nonnull
    public EnvelopeType getType() {
        return type;
    }

    /**
     * Get the sensor
     * @return sensor identifier
     */
    @Nonnull
    public String getSensorId() {
        return sensorId;
    }

    /**
     * Set the sensor identifier.
     * @param sensor
     */
    public void setSensorId(@Nonnull Sensor sensor) {
        this.sensorId = sensor.getId();
    }

    /**
     * Get the sent time.
     * @return the sent time.
     */
    public DateTime getSendTime() {
        return sendTime;
    }

    /**
     * Set the time.
     * @param sendTime
     */
    public void setSendTime(DateTime sendTime) {
        this.sendTime = sendTime;
    }

    /**
     * Get the data.
     * @return the data
     */
    @Nonnull
    public T getData() {
        return data;
    }

    /**
     * Set the data.
     * @param data
     */
    public void setData(@Nonnull T data) {
        this.data = data;
    }
}