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

package org.imsglobal.caliper.context;

import javax.annotation.Nonnull;

import org.imsglobal.caliper.entities.CaliperCoercible;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class JsonldStringContext implements JsonldContext, CaliperCoercible {

    @JsonProperty("@context")
    private String id;

    @JsonIgnore
    private final boolean coercedToId = true;

    /**
     * Constructor.  Rendered private to force use of factory methods.
     */
    private JsonldStringContext(String id) {
        this.id = id;
    }

    /**
     * Accessor
     * @return id
     */
    public String getId() {
        return id;
    }

    /**
     * @return coerceToId flag
     */
    @Nonnull
    public boolean isCoercedToId() {
        return coercedToId;
    }

    /**
     * Factory method
     * @param id
     * @return JsonldStringContext
     */
    public static JsonldStringContext create(String id) {
        return new JsonldStringContext(id);
    }
}