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

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import org.imsglobal.caliper.entities.Entity;
import org.imsglobal.caliper.entities.EntityType;
import org.imsglobal.caliper.entities.agent.Person;
import org.imsglobal.caliper.entities.w3c.Organization;
import org.imsglobal.caliper.validators.EntityValidator;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

/**
 * A Caliper Membership is used to define the relationship between objects that can have memberIds and objects
 * that can be memberIds. Objects recognized as having memberIds are CourseOffering, CourseSection
 * and Group, all of which implement the Joinable marker interface. Only a Person object can be a memberId.
 */

@SupportedStatuses({
    Status.ACTIVE,
    Status.DELETED,
    Status.INACTIVE
})
public class Membership extends Entity implements org.imsglobal.caliper.entities.w3c.Membership {

    @JsonProperty("@type")
    private final EntityType type;

    @JsonProperty("member")
    private final Person member;

    @JsonProperty("organization")
    private final Organization organization;

    @JsonProperty("roles")
    private final ImmutableList<org.imsglobal.caliper.entities.w3c.Role> roles;

    @JsonProperty("status")
    private final org.imsglobal.caliper.entities.w3c.Status status;

    /**
     * @param builder apply builder object properties to the Membership object.
     */
    protected Membership(Builder<?> builder) {
        super(builder);

        EntityValidator.checkType(builder.type, EntityType.MEMBERSHIP);
        EntityValidator.checkMembershipStatus(builder.status);

        this.type = builder.type;
        this.member = builder.member;
        this.organization = builder.organization;
        this.roles = ImmutableList.copyOf(builder.roles);
        this.status = builder.status;
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
     * @return the person involved in the membership relationship.
     */
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "@id")
    @JsonIdentityReference(alwaysAsId = true)
    @Nonnull
    public Person getMember() {
        return member;
    }

    /**
     * @return the membership in which the person is a memberId.
     */
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "@id")
    @JsonIdentityReference(alwaysAsId = true)
    @Nonnull
    public Organization getOrganization() {
        return organization;
    }

    /**
     * @return the roles that the agent plays in a memberIdship relationship with an memberIdship.
     */
    @Nullable
    public ImmutableList<org.imsglobal.caliper.entities.w3c.Role> getRoles() {
        return roles;
    }

    /**
     * @return the current status of a membership which applies to all roles.
     */
    @Nonnull
    public org.imsglobal.caliper.entities.w3c.Status getStatus() {
        return status;
    }

    /**
     * Builder class provides a fluid interface for setting object properties.
     * @param <T> builder
     */
    public static abstract class Builder<T extends Builder<T>> extends Entity.Builder<T> {
        private EntityType type;
        private Person member;
        private Organization organization;
        private List<org.imsglobal.caliper.entities.w3c.Role> roles = Lists.newArrayList();
        private org.imsglobal.caliper.entities.w3c.Status status;

        /**
         * Default Constructor
         */
        public Builder() {
            type(EntityType.MEMBERSHIP);
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
         * @param member
         * @return builder.
         */
        public T member(Person member) {
            this.member = member;
            return self();
        }

        /**
         * @param organization
         * @return builder.
         */
        public T organization(Organization organization) {
            this.organization = organization;
            return self();
        }

        /**
         * @param roles
         * @return builder.
         */
        public T roles(List<org.imsglobal.caliper.entities.w3c.Role> roles) {
            this.roles = roles;
            return self();
        }

        /**
         * @param role
         * @return builder.
         */
        public T role(org.imsglobal.caliper.entities.w3c.Role role) {
            this.roles.add(role);
            return self();
        }

        /**
         * @param status
         * @return builder.
         */
        public T status(org.imsglobal.caliper.entities.w3c.Status status) {
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