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

package org.imsglobal.caliper.entities.lis;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.imsglobal.caliper.entities.Entity;
import org.imsglobal.caliper.entities.EntityType;
import org.imsglobal.caliper.validators.EntityValidator;

import javax.annotation.Nonnull;

/**
 * A Caliper LIS Group represents a Course substructure that a Person is able to join as a member.
 */
public class Group extends Entity implements org.imsglobal.caliper.entities.w3c.Organization {

    @JsonProperty("@type")
    private final EntityType type;

    @JsonProperty("subOrganizationOf")
    private final Course subOrganizationOf;

    /**
     * @param builder apply builder object properties to the Group object.
     */
    protected Group(Builder<?> builder) {
        super(builder);

        EntityValidator.checkType(builder.type, EntityType.GROUP);

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
     * @return the parent organization
     */
    @Nonnull
    public Course getSubOrganizationOf() {
        return subOrganizationOf;
    }

    /**
     * Builder class provides a fluid interface for setting object properties.
     * @param <T> builder
     */
    public static abstract class Builder<T extends Builder<T>> extends Entity.Builder<T>  {
        private EntityType type;
        private Course subOrganizationOf;

        /**
         * Initialize type with default value.  Required if the group type is not specified by the user.
         */
        public Builder() {
            type(EntityType.GROUP);
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
        public T subOrganizationOf(Course subOrganizationOf) {
            this.subOrganizationOf = subOrganizationOf;
            return self();
        }

        /**
         * Client invokes build method in order to create an immutable object.
         * @return a new instance of Group.
         */
        public Group build() {
            return new Group(this);
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