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

import com.fasterxml.jackson.annotation.JsonProperty;
import org.imsglobal.caliper.entities.Entity;
import org.imsglobal.caliper.entities.EntityType;
import org.imsglobal.caliper.validators.EntityValidator;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * A collection of people organized together into a community or other social, commercial or political structure.
 * The group has some common purpose or reason for existence which goes beyond the set of people belonging to it
 * and can act as an Agent. Organizations are often decomposable into hierarchical structures.
 */
public class Organization extends Entity implements org.imsglobal.caliper.entities.foaf.Agent,
                                                    org.imsglobal.caliper.entities.w3c.Organization {

    @JsonProperty("@type")
    private final EntityType type;

    @JsonProperty("subOrganizationOf")
    private final org.imsglobal.caliper.entities.w3c.Organization subOrganizationOf;

    /**
     * @param builder apply builder object properties to the Organization object.
     */
    protected Organization(Builder<?> builder) {
        super(builder);

        EntityValidator.checkType(builder.type, EntityType.ORGANIZATION);

        this.type = builder.type;
        this.subOrganizationOf = builder.subOrganizationOf;
    }

    /**
     * @return the type
     */
    @Override
    @Nonnull
    public EntityType getType() {
        return type;
    }

    /**
     * @return parent organization
     */
    @Nullable
    public org.imsglobal.caliper.entities.w3c.Organization getSubOrganizationOf() {
        return subOrganizationOf;
    }

    /**
     * Builder class provides a fluid interface for setting object properties.
     * @param <T> builder
     */
    public static abstract class Builder<T extends Builder<T>> extends Entity.Builder<T>  {
        private EntityType type;
        private org.imsglobal.caliper.entities.w3c.Organization subOrganizationOf;

        /**
         * Initialize type with default value.
         */
        public Builder() {
            type(EntityType.ORGANIZATION);
        }

        /**
         * @param type
         * @return builder.
         */
        private T type(EntityType type) {
            this.type = type;
            return self();
        }

        /**
         * @param subOrganizationOf
         * @return builder.
         */
        public T subOrganizationOf(org.imsglobal.caliper.entities.w3c.Organization subOrganizationOf) {
            this.subOrganizationOf = subOrganizationOf;
            return self();
        }

        /**
         * Client invokes build method in order to create an immutable object.
         * @return a new instance of the Organization.
         */
        public Organization build() {
            return new Organization(this);
        }
    }

    /**
     *
     */
    private static class Builder2 extends Builder<Builder2> {
        @Override
        protected Builder2 self() {
            return this;
        }
    }

    /**
     * Static factory method.
     * @return a new instance of the builder.
     */
    public static Builder<?> builder() {
        return new Builder2();
    }
}