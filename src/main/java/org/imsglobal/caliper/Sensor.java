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

package org.imsglobal.caliper;

import com.google.common.base.Function;
import com.google.common.collect.Maps;
import org.imsglobal.caliper.clients.CaliperClient;
import org.imsglobal.caliper.statistics.Statistics;
import org.joda.time.DateTime;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Concrete implementation of the Caliper Sensor interface.  Caliper Events and Entity describes
 * are sent via an Envelope.  Serialization and transmission of the Envelope is delegated to
 * one or more registered Clients which in turn delegate serialization and transmission to
 * an associated Requestor.  The delegation chain is thus Sensor to Client to Requestor.
 */
public class Sensor {
    private String id;
    private Map<String, CaliperClient> clients = new HashMap<>();

    /**
     * Constructor. Scope is private to force use of the static factory method for instantiating a Sensor.
     */
    private Sensor(String id) {
        this.id = id;
    }

    /**
     * Get identifier.
     * @return id
     */
    public String getId() {
        return id;
    }

    /**
     * Register a Sensor client.
     * @param client the client object
     */
    public void registerClient(CaliperClient client) {
        clients.put(client.getId(), client);
    }

    /**
     * Unregister a Sensor client.
     * @param key
     * @return
     */
    public void unregisterClient(String key) {
        clients.remove(key);
    }

    /**
     * Retrieve a client.
     * @param key
     * @return
     */
    public CaliperClient getClient(String key) {
        return clients.get(key);
    }

    /**
     * Retrieve Map of clients.
     * @return clients
     */
    public Map<String, CaliperClient> getClients() {
        return clients;
    }

    /**
     * Create the Envelope. The sendTime of the envelope will be set using DateTime.now().
     * @param id
     * @param dataVersion
     * @param data
     * @return envelope
     */
    public Envelope create(String id, String dataVersion, List<CaliperSendable> data) {
        return new Envelope(id, DateTime.now(), dataVersion, data);
    }

    /**
     * Create the Envelope.
     * @param id
     * @param sendTime
     * @param dataVersion
     * @param data
     * @return envelope
     */
    public Envelope create(String id, DateTime sendTime, String dataVersion, List<CaliperSendable> data) {
        return new Envelope(id, sendTime, dataVersion, data);
    }
    
    /**
     * Delegate serialization and transmission of the Envelope to a particular registered Client.
     * @param envelope
     */
    public void send(CaliperClient client, Envelope envelope) {
        if (clients.containsKey(client.getId())) {
            client.send(envelope);
        } else {
            throw new IllegalArgumentException(client.getId() + " is not a registered Client.");
        }
    }

    /**
     * Delegate serialization and transmission of the Envelope to all registered Clients.
     * @param envelope
     */
    public void send(Envelope envelope) {
        if (clients.size() > 0) {
            for(CaliperClient client: clients.values()){
                client.send(envelope);
            }
        } else {
            throw new IllegalStateException("No Clients have been registered.");
        }
    }

    /**
     * Returns a map where the keys are the identifying objects and the values are the corresponding statistics
     * for that key's Client.
     * @return a map
     */
    public Map<String, Statistics> getStatistics() {
        return Maps.transformValues(clients, new Function<CaliperClient, Statistics>() {
            @Nullable
            @Override
            public Statistics apply(@Nullable CaliperClient client) {
                return client.getStatistics();
            }
        });
    }

    /**
     * Factory method for creating Sensors.
     * @return Sensors
     */
    public static Sensor create(String id) {
        return new Sensor(id);
    }
}