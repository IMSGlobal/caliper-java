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

package org.imsglobal.caliper.entities.outcome;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.imsglobal.caliper.entities.AbstractEntity;
import org.imsglobal.caliper.entities.CaliperGeneratable;
import org.imsglobal.caliper.entities.EntityType;
import org.imsglobal.caliper.entities.TimePeriod;
import org.imsglobal.caliper.entities.agent.CaliperAgent;
import org.imsglobal.caliper.entities.resource.CaliperDigitalResource;
import org.imsglobal.caliper.validators.EntityValidator;
import org.joda.time.DateTime;

import javax.annotation.Nullable;

/**
 * Representation of an Attempt. Attempts are generated as part of or
 * are the object of an interaction represented by an AssignableEvent.
 */
public class Attempt extends AbstractEntity implements CaliperGeneratable {

    @JsonProperty("assignable")
    private final CaliperDigitalResource assignable;

    @JsonProperty("assignee")
    private final CaliperAgent assignee;

    @JsonProperty("isPartOf")
    private final Attempt isPartOf;

    @JsonProperty("count")
    private int count;

    @JsonIgnore
    private TimePeriod timePeriod = new TimePeriod();

    /**
     * @param builder apply builder object properties to the object.
     */
    private Attempt(Builder<?> builder) {
        super(builder);

        EntityValidator.checkStartTime(builder.timePeriod.getStartedAtTime(), builder.timePeriod.getEndedAtTime());
        EntityValidator.checkDuration(builder.timePeriod.getDuration());

        this.assignable = builder.assignable;
        this.assignee = builder.assignee;
        this.isPartOf = builder.isPartOf;
        this.count = builder.count;
        this.timePeriod.setStartedAtTime(builder.timePeriod.getStartedAtTime());
        this.timePeriod.setEndedAtTime(builder.timePeriod.getEndedAtTime());
        this.timePeriod.setDuration(builder.timePeriod.getDuration());
    }

    /**
     * @return the assignment
     */
    @Nullable
    public CaliperDigitalResource getAssignable() {
        return assignable;
    }

    /**
     * @return the actor
     */
    @Nullable
    public CaliperAgent getAssignee() {
        return assignee;
    }

    /**
     * @return the parent Attempt if one exists
     */
    @Nullable
    public Attempt getIsPartOf() {
        return isPartOf;
    }

    /**
     * @return the count
     */
    @Nullable
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    public int getCount() {
        return count;
    }

    /**
     * @return started at time
     */
    @Nullable
    public DateTime getStartedAtTime() {
        return timePeriod.getStartedAtTime();
    }

    /**
     * @return ended at time
     */
    @Nullable
    public DateTime getEndedAtTime() {
        return timePeriod.getEndedAtTime();
    }

    /**
     * @return duration
     */
    @Nullable
    public String getDuration() {
        return timePeriod.getDuration();
    }

    /**
     * Builder class provides a fluid interface for setting object properties.
     * @param <T> builder.
     */
    public static abstract class Builder<T extends Builder<T>> extends AbstractEntity.Builder<T> {
        private CaliperDigitalResource assignable;
        private CaliperAgent assignee;
        private Attempt isPartOf;
        private int count;
        private TimePeriod timePeriod = new TimePeriod();

        /**
         * Constructor
         */
        public Builder() {
            super.type(EntityType.ATTEMPT);
        }

        /**
         * @param assignable
         * @return builder.
         */
        public T assignable(CaliperDigitalResource assignable) {
            this.assignable = assignable;
            return self();
        }

        /**
         * @param assignee
         * @return builder.
         */
        public T assignee(CaliperAgent assignee) {
            this.assignee = assignee;
            return self();
        }

        /**
         * @param isPartOf
         * @return builder.
         */
        public T isPartOf(Attempt isPartOf) {
            this.isPartOf = isPartOf;
            return self();
        }

        /**
         * @param count
         * @return builder
         */
        public T count(int count) {
            this.count = count;
            return self();
        }

        /**
         * @param startedAtTime
         * @return
         */
        public T startedAtTime(DateTime startedAtTime) {
            this.timePeriod.setStartedAtTime(startedAtTime);

            return self();
        }

        /**
         * @param endedAtTime
         * @return builder
         */
        public T endedAtTime(DateTime endedAtTime) {
            this.timePeriod.setEndedAtTime(endedAtTime);
            return self();
        }

        /**
         * @param duration
         * @return
         */
        public T duration(String duration) {
            this.timePeriod.setDuration(duration);
            return self();
        }

        /**
         * Client invokes build method in order to create an immutable object.
         * @return a new instance of the Attempt.
         */
        public Attempt build() {
            return new Attempt(this);
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