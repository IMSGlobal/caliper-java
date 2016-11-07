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

package org.imsglobal.caliper.entities.agent;

/**
 * An entity that can do things. Typical kinds of agents include people, organizations and software systems.
 */
public interface Agent {

    /**
     * Each entity (or node in the graph as defined by JSON-LD) requires an identifier.
     * The identifier should be expressed as a unique IRI in conformance with the
     * JSON-LD specification.
     * @return the identifier IRI
     */
    String getId();

    /**
     * Specifies the type of Agent or node in the graph as defined by JSON-LD.  The type should be
     * expressed as a unique IRI in conformance with the JSON-LD specification.
     * @return the type IRI
     */
    String getType();
}