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
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import org.imsglobal.caliper.entities.AbstractEntity;
import org.imsglobal.caliper.entities.EntityType;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

/**
 * A Caliper Membership is used to define the relationship between objects that can have members and objects
 * that can be members. Objects recognized as having members are Organization, CourseOffering, CourseSection
 * and Group, all of which implement the Joinable marker interface. Only a Membership object can be a memberId.
 */
@SupportedStatuses({Status.ACTIVE, Status.INACTIVE})
public class Membership extends AbstractEntity {

    @JsonProperty("member")
    private final CaliperAgent member;

    @JsonProperty("organization")
    private final CaliperOrganization organization;

    @JsonProperty("roles")
    private final ImmutableList<Role> roles;

    @JsonProperty("status")
    private final Status status;

    /**
     * @param builder apply builder object properties to the object.
     */
    private Membership(Builder<?> builder) {
        super(builder);

        this.member = builder.member;
        this.organization = builder.organization;
        this.roles = ImmutableList.copyOf(builder.roles);
        this.status = builder.status;
    }

    /**
     * @return the person involved in the membership relationship.
     */
    @Nonnull
    public CaliperAgent getMember() {
        return member;
    }

    /**
     * @return the membership in which the person is a memberId.
     */
    @Nonnull
    public CaliperOrganization getOrganization() {
        return organization;
    }

    /**
     * @return the roles that the agent plays in a memberIdship relationship with an memberIdship.
     */
    @Nullable
    public ImmutableList<Role> getRoles() {
        return roles;
    }

    /**
     * @return the current status of a membership which applies to all roles.
     */
    @Nonnull
    public Status getStatus() {
        return status;
    }

    /**
     * Builder class provides a fluid interface for setting object properties.
     * @param <T> builder.
     */
    public static abstract class Builder<T extends Builder<T>> extends AbstractEntity.Builder<T> {
        private CaliperAgent member;
        private CaliperOrganization organization;
        private List<Role> roles = Lists.newArrayList();
        private Status status;

        /**
         * Constructor
         */
        public Builder() {
            super.type(EntityType.MEMBERSHIP);
        }

        /**
         * @param member
         * @return builder.
         */
        public T member(CaliperAgent member) {
            this.member = member;
            return self();
        }

        /**
         * @param organization
         * @return builder.
         */
        public T organization(CaliperOrganization organization) {
            this.organization = organization;
            return self();
        }

        /**
         * @param roles
         * @return builder.
         */
        public T roles(List<Role> roles) {
            if(roles != null) {
                this.roles.addAll(roles);
            }
            return self();
        }

        /**
         * @param role
         * @return builder.
         */
        public T role(Role role) {
            this.roles.add(role);
            return self();
        }

        /**
         * @param status
         * @return builder.
         */
        public T status(Status status) {
            this.status = status;
            return self();
        }

        /**
         * Client invokes build method in order to create an immutable object.
         * @return a new instance of the Membership.
         */
        public Membership build() {
            return new Membership(this);
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