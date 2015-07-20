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

public enum Defaults {
    HOST("http://example.edu"),
    CONNECTION_REQUEST_TIMEOUT("10000"),
    CONNECTION_TIMEOUT("10000"),
    SOCKET_TIMEOUT("10000"),
    JSON_INCLUDE("NON_EMPTY");

    private final String value;

    /**
     * Private constructor
     * @param value
     */
    private Defaults(final String value) {
        this.value = value;
    }

    /**
     * @return default string
     */
    public String getValue() {
        return value;
    }
}