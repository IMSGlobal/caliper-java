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
import org.imsglobal.caliper.events.Event;
import org.imsglobal.caliper.stats.Statistics;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;

public class Sensor<T> {

    private Map<T, Client> clients = new HashMap<>();

    /**
     * Register a client that will get invoked whenever an event is sent through this sensor
     * @param key the identifier of this client
     * @param client the client object
     */
    public void registerClient(T key, Client client){
        this.clients.put(key, client);
    }

    /**
     * Unregister a client. unregistered clients will be removed from the client map & no longer be referenceable
     * @param key
     * @return
     */
    public Client unRegisterClient(T key){
        return this.clients.remove(key);
    }

    /**
     * Send an event to all configured clients
     * @param event the event object to be sent
     */
    public void send(Event event){
        for(Client client: clients.values()){
            client.send(event);
        }
    }

    /**
     * Returns
     * @return a map where the keys are the indentifying objects and the values are the corresponding statistics
     * for that key's Client.
     */
    public Map<T, Statistics> getStatistics(){
        return Maps.transformValues(clients, new Function<Client, Statistics>() {
            @Nullable
            @Override
            public Statistics apply(@Nullable Client client) {
                return client.getStatistics();
            }
        });
    }

}