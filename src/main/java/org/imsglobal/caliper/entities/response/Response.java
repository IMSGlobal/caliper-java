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

package org.imsglobal.caliper.entities.response;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import org.imsglobal.caliper.entities.DigitalResource;
import org.imsglobal.caliper.entities.Entity;
import org.imsglobal.caliper.entities.EntityType;
import org.imsglobal.caliper.entities.Generatable;
import org.imsglobal.caliper.entities.Type;
import org.imsglobal.caliper.entities.assignable.Attempt;
import org.imsglobal.caliper.entities.foaf.Agent;
import org.imsglobal.caliper.validators.EntityValidator;
import org.joda.time.DateTime;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public abstract class Response extends Entity implements Generatable {

    @JsonProperty("@type")
    private final Type type;

    @JsonProperty("assignable")
    private final DigitalResource assignable;

    @JsonProperty("actor")
    private final Agent actor;

    @JsonProperty("attempt")
    private Attempt attempt;

    @JsonProperty("startedAtTime")
    private DateTime startedAtTime;

    @JsonProperty("endedAtTime")
    private DateTime endedAtTime;

    @JsonProperty("duration")
    private String duration;

    /**
     * @param builder apply builder object properties to the Attempt object.
     */
    protected Response(Builder<?> builder) {
        super(builder);

        EntityValidator.checkType(builder.type, EntityType.RESPONSE);
        EntityValidator.checkId("assignable Id", builder.assignable.getId());
        EntityValidator.checkId("actor Id", builder.actor.getId());
        EntityValidator.checkAttempt(builder.attempt);
        EntityValidator.checkStartTime(builder.startedAtTime, builder.endedAtTime);
        EntityValidator.checkDuration(builder.duration);

        this.type = builder.type;
        this.assignable = builder.assignable;
        this.actor = builder.actor;
        this.attempt = builder.attempt;
        this.startedAtTime = builder.startedAtTime;
        this.endedAtTime = builder.endedAtTime;
        this.duration = builder.duration;
    }

    /**
     * @return the type
     */
    @Override
    @Nonnull
    public Type getType() {
        return type;
    }

    /**
     * Serialization of Assignable associated with this Response is limited to
     * the identifying URI only.
     * @return the assignable
     */
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "@id")
    @JsonIdentityReference(alwaysAsId = true)
    @Nonnull
    public DigitalResource getAssignable() {
        return assignable;
    }

    /**
     * Serialization of Agent associated with this Response is limited to
     * the identifying URI only.
     * @return the actor
     */
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "@id")
    @JsonIdentityReference(alwaysAsId = true)
    @Nonnull
    public Agent getActor() {
        return actor;
    }

    /**
     * @return attempt associated with the response;
     */
    @Nonnull
    public Attempt getAttempt() {
        return attempt;
    }

    /**
     * @return started at time
     */
    @Nullable
    public DateTime getStartedAtTime() {
        return startedAtTime;
    }

    /**
     * @return ended at time
     */
    @Nullable
    public DateTime getEndedAtTime() {
        return endedAtTime;
    }

    /**
     * An xsd:duration (http://books.xmlschemata.org/relaxng/ch19-77073.html)
     * The format is expected to be PnYnMnDTnHnMnS.  Valid values include PT1004199059S, PT130S,
     * PT2M10S, P1DT2S, -P1Y, or P1Y2M3DT5H20M30.123S.  The following values are invalid:
     * 1Y (leading P is missing), P1S (T separator is missing), P-1Y (all parts must be positive),
     * P1M2Y (parts order is significant and Y must precede M) or P1Y-1M (all parts must be positive).
     *
     * @return duration
     */
    @Nullable
    public String getDuration() {
        return duration;
    }

    /**
     * Builder class provides a fluid interface for setting object properties.
     * @param <T> builder
     */
    public static abstract class Builder<T extends Builder<T>> extends Entity.Builder<T>  {
        private EntityType type;
        private DigitalResource assignable;
        private Agent actor;
        private Attempt attempt;
        private DateTime startedAtTime;
        private DateTime endedAtTime;
        private String duration;

        /**
         * Initialize type with default value.
         */
        public Builder() {
            type(EntityType.RESPONSE);
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
         * @param assignable
         * @return builder.
         */
        public T assignable(DigitalResource assignable) {
            this.assignable = assignable;
            return self();
        }

        /**
         * @param actor
         * @return builder.
         */
        public T actor(Agent actor) {
            this.actor = actor;
            return self();
        }

        /**
         * @param attempt
         * @return builder.
         */
        public T attempt(Attempt attempt) {
            this.attempt = attempt;
            return self();
        }

        /**
         * @param startedAtTime
         * @return builder.
         */
        public T startedAtTime(DateTime startedAtTime) {
            this.startedAtTime = startedAtTime;
            return self();
        }

        /**
         * @param endedAtTime
         * @return builder.
         */
        public T endedAtTime(DateTime endedAtTime) {
            this.endedAtTime = endedAtTime;
            return self();
        }

        /**
         * @param duration
         * @return builder.
         */
        public T duration(String duration) {
            this.duration = duration;
            return self();
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
}