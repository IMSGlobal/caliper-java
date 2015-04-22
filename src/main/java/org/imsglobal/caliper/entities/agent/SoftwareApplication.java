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
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import org.imsglobal.caliper.entities.Entity;
import org.imsglobal.caliper.entities.EntityType;
import org.imsglobal.caliper.entities.w3c.Membership;
import org.imsglobal.caliper.validators.EntityValidator;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

@JsonPropertyOrder({
        "@id",
        "@type",
        "name",
        "description",
        "hasMembership",
        "extensions",
        "dateCreated",
        "dateModified" })
public class SoftwareApplication extends Entity implements org.imsglobal.caliper.entities.foaf.Agent,
                                                           org.imsglobal.caliper.entities.schemadotorg.SoftwareApplication {

    @JsonProperty("@type")
    private final EntityType type;

    @JsonProperty("hasMembership")
    private ImmutableList<Membership> memberships;

    /**
     * @param builder apply builder object properties to the SoftwareApplication object.
     */
    protected SoftwareApplication(Builder<?> builder) {
        super(builder);

        EntityValidator.checkType(builder.type, EntityType.SOFTWARE_APPLICATION);

        this.type = builder.type;
        this.memberships = ImmutableList.copyOf(builder.memberships);
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
     * @return memberships
     */
    @Nullable
    public ImmutableList<Membership> hasMembership() {
        return memberships;
    }

    /**
     * Builder class provides a fluid interface for setting object properties.
     * @param <T> builder
     */
    public static abstract class Builder<T extends Builder<T>> extends Entity.Builder<T>  {
        private EntityType type;
        private List<Membership> memberships = Lists.newArrayList();

        /**
         * Initialize type with default value.
         */
        public Builder() {
            type(EntityType.SOFTWARE_APPLICATION);
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
         * @param memberships
         * @return builder.
         */
        private T memberships(List<Membership> memberships) {
            this.memberships = memberships;
            return self();
        }

        /**
         * @param membership
         * @return builder.
         */
        private T membership(Membership membership) {
            this.memberships.add(membership);
            return self();
        }

        /**
         * Client invokes build method in order to create an immutable object.
         * @return a new instance of the SoftwareApplication.
         */
        public SoftwareApplication build() {
            return new SoftwareApplication(this);
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