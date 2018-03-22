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

public class HttpCustomHeader {
    private final String field;
    private String value;
    private static final String[] PROHIBITED_HEADERS = new String[] { "content-type", "authorization" };

    public HttpCustomHeader(String field, String value) {

        for (String header: PROHIBITED_HEADERS) {
            if (header.equals(field.toLowerCase())) {
                throw new IllegalArgumentException("Setting " + field + " header not allowed.");
            }
        }

        // Always save header fields in lower case because fields are case-insensitive.
        // (From RFC 7230 Section 3.2 "Header Fields")
        this.field = field.toLowerCase();
        setValue(value);
    }

    public String getField() {
        return this.field;
    }

    public String getValue() {
        return this.value;
    }

    public void setValue(String value) {

        if (value == null || value.length() == 0) {
            throw new IllegalArgumentException("Empty header value not allowed.");
        }

        this.value = value;
    }

    @Override
    public String toString() {
        return "HttpCustomHeader{" + "field='" + field + '\'' +
                ", value='" + value + '\'' +
                '}';
    }
}
