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

import javax.annotation.Nonnull;

import org.imsglobal.caliper.Envelope;
import org.imsglobal.caliper.databind.JxnCoercibleSimpleModule;
import org.imsglobal.caliper.statistics.Statistics;
import org.imsglobal.caliper.validators.SensorValidator;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.fasterxml.jackson.databind.util.StdDateFormat;
import com.fasterxml.jackson.datatype.joda.JodaModule;

/**
 * This class provides a skeletal implementation of the Sensor Client interface
 * in order to minimize the effort required to implement the interface.
 */
public abstract class AbstractClient implements CaliperClient {
    private String id;
    private HttpClientOptions options;
    private Statistics statistics;

    /**
     * Constructor
     * @param id
     */
    protected AbstractClient(String id, HttpClientOptions options) {
        SensorValidator.chkId(id, this.getClass().getSimpleName());
        //SensorValidator.chkOptions(this.getOptions());

        this.id = id;
        this.options = options;
        this.statistics = new Statistics();
    }

    /**
     * Get identifier.
     * @return id
     */
    @Nonnull
    public String getId() {
        return id;
    }

    /**
     * Retrieve options
     * @return options
     */
    public HttpClientOptions getOptions() {
        return options;
    }

    /**
     * Get statistics.
     * @return statistics
     */
    @Nonnull
    public Statistics getStatistics() {
        return this.statistics;
    }

    /**
     * Serialize Caliper envelope.
     * @param envelope
     * @return String
     * @throws JsonProcessingException
     */
    protected String serializeEnvelope(Envelope envelope) throws JsonProcessingException {
        // Create mapper and serialize the envelope
        SimpleFilterProvider provider = new SimpleFilterProvider()
            .setFailOnUnknownId(true);

        ObjectMapper mapper = new ObjectMapper()
            .setDateFormat(new StdDateFormat())
            .setSerializationInclusion(JsonInclude.Include.NON_EMPTY)
            .setFilterProvider(provider)
            .registerModules(new JodaModule(), new JxnCoercibleSimpleModule());

        return mapper.writeValueAsString(envelope);
    }

    /**
     * Send Envelope to a target endpoint
     * @param envelope
     */
    public abstract void send(Envelope envelope);

    /**
     * Update statistics
     * @param status
     */
    protected void updateStatistics(boolean status) {
        this.statistics.updateMeasures(1);
        if (status) {
            statistics.updateSuccessful(1);
        } else {
            statistics.updateFailed(1);
        }
    }
}