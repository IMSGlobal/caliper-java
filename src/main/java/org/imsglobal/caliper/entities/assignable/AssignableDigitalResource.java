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

package org.imsglobal.caliper.entities.assignable;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.imsglobal.caliper.entities.DigitalResource;
import org.imsglobal.caliper.entities.DigitalResourceType;
import org.imsglobal.caliper.entities.Type;
import org.imsglobal.caliper.validators.EntityValidator;
import org.joda.time.DateTime;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Assignable Digital Resource
 */
public class AssignableDigitalResource extends DigitalResource implements org.imsglobal.caliper.entities.assignable.Assignable {

    @JsonProperty("@type")
    private final Type type;

    @JsonProperty("dateToActivate")
    private DateTime dateToActivate;

    @JsonProperty("dateToShow")
    private DateTime dateToShow;

    @JsonProperty("dateToStartOn")
    private DateTime dateToStartOn;

    @JsonProperty("dateToSubmit")
    private DateTime dateToSubmit;

    @JsonProperty("maxAttempts")
    private int maxAttempts;

    @JsonProperty("maxSubmits")
    private int maxSubmits;

    @JsonProperty("maxScore")
    private double maxScore;

    /**
     * @param builder apply builder object properties to the Target object.
     */
    protected AssignableDigitalResource(Builder<?> builder) {
        super(builder);

        EntityValidator.checkType(builder.type, DigitalResourceType.ASSIGNABLE_DIGITAL_RESOURCE);

        this.type = builder.type;
        this.dateToActivate = builder.dateToActivate;
        this.dateToShow = builder.dateToShow;
        this.dateToStartOn = builder.dateToStartOn;
        this.dateToSubmit = builder.dateToSubmit;
        this.maxAttempts = builder.maxAttempts;
        this.maxSubmits = builder.maxSubmits;
        this.maxScore = builder.maxScore;
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
     * @return the dateToActivate
     */
    @Nullable
    public DateTime getDateToActivate() {
        return dateToActivate;
    }

    /**
     * @return the dateToShow
     */
    @Nullable
    public DateTime getDateToShow() {
        return dateToShow;
    }

    /**
     * @return the dateToStartOn
     */
    @Nullable
    public DateTime getDateToStartOn() {
        return dateToStartOn;
    }

    /**
     * @return the dateToSubmit
     */
    @Nullable
    public DateTime getDateToSubmit() {
        return dateToSubmit;
    }

    /**
     * @return the maxAttempts
     */
    @Nullable
    public int getMaxAttempts() {
        return maxAttempts;
    }

    /**
     * @return the maxSubmits
     */
    @Nullable
    public int getMaxSubmits() {
        return maxSubmits;
    }

    /**
     * @return the maxScore
     */
    @Nullable
    public double getMaxScore() {
        return maxScore;
    }

    /**
     * Builder class provides a fluid interface for setting object properties.
     * @param <T> builder
     */
    public static abstract class Builder<T extends Builder<T>> extends DigitalResource.Builder<T>  {
        private DigitalResourceType type;
        private DateTime dateToActivate, dateToShow, dateToStartOn, dateToSubmit;
        private int maxAttempts, maxSubmits;
        private double maxScore;

        /**
         * Initialize type with default value.
         */
        public Builder() {
            type(DigitalResourceType.ASSIGNABLE_DIGITAL_RESOURCE);
        }

        /**
         * @param type
         * @return builder.
         */
        private T type(DigitalResourceType type) {
            this.type = type;
            return self();
        }

        /**
         * @param dateToActivate
         * @return builder
         */
        public T dateToActivate(DateTime dateToActivate) {
            this.dateToActivate = dateToActivate;
            return self();
        }

        /**
         * @param dateToShow
         * @return builder
         */
        public T dateToShow(DateTime dateToShow) {
            this.dateToShow = dateToShow;
            return self();
        }

        /**
         * @param dateToStartOn
         * @return builder
         */
        public T dateToStartOn(DateTime dateToStartOn) {
            this.dateToStartOn = dateToStartOn;
            return self();
        }

        /**
         * @param dateToSubmit
         * @return builder
         */
        public T dateToSubmit(DateTime dateToSubmit) {
            this.dateToSubmit = dateToSubmit;
            return self();
        }

        /**
         * @param maxAttempts
         * @return builder
         */
        public T maxAttempts(int maxAttempts) {
            this.maxAttempts = maxAttempts;
            return self();
        }

        /**
         * @param maxSubmits
         * @return builder
         */
        public T maxSubmits(int maxSubmits) {
            this.maxSubmits = maxSubmits;
            return self();
        }

        /**
         * @param maxScore
         * @return builder
         */
        public T maxScore(double maxScore) {
            this.maxScore = maxScore;
            return self();
        }

        /**
         * Client invokes build method in order to create an immutable object.
         * @return a new instance of Target.
         */
        public AssignableDigitalResource build() {
            return new AssignableDigitalResource(this);
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