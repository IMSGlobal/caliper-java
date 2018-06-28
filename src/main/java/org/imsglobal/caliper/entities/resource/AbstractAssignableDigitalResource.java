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

package org.imsglobal.caliper.entities.resource;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.imsglobal.caliper.entities.EntityType;
import org.joda.time.DateTime;

import javax.annotation.Nullable;

/**
 * This class provides a skeletal implementation of the Assignable interface
 * in order to minimize the effort required to implement the interface.
 */
public abstract class AbstractAssignableDigitalResource extends AbstractDigitalResource implements CaliperAssignable {

    @JsonIgnore
    private Assignment assign = new Assignment();

    /**
     * @param builder apply builder object properties to the Target object.
     */
    protected AbstractAssignableDigitalResource(Builder<?> builder) {
        super(builder);

        this.assign.setDateToActivate(builder.assign.getDateToActivate());
        this.assign.setDateToShow(builder.assign.getDateToShow());
        this.assign.setDateToStartOn(builder.assign.getDateToStartOn());
        this.assign.setDateToSubmit(builder.assign.getDateToSubmit());
        this.assign.setMaxAttempts(builder.assign.getMaxAttempts());
        this.assign.setMaxSubmits(builder.assign.getMaxSubmits());
        this.assign.setMaxScore(builder.assign.getMaxScore());
    }

    /**
     * @return the dateToActivate
     */
    @Nullable
    public DateTime getDateToActivate() {
        return assign.getDateToActivate();
    }

    /**
     * @return the dateToShow
     */
    @Nullable
    public DateTime getDateToShow() {
        return assign.getDateToShow();
    }

    /**
     * @return the dateToStartOn
     */
    @Nullable
    public DateTime getDateToStartOn() {
        return assign.getDateToStartOn();
    }

    /**
     * @return the dateToSubmit
     */
    @Nullable
    public DateTime getDateToSubmit() {
        return assign.getDateToSubmit();
    }

    /**
     * @return the maxAttempts
     */
    @Nullable
    public int getMaxAttempts() {
        return assign.getMaxAttempts();
    }

    /**
     * @return the maxSubmits
     */
    @Nullable
    public int getMaxSubmits() {
        return assign.getMaxSubmits();
    }

    /**
     * @return the maxScore
     */
    @Nullable
    // @JsonSerialize(using=DoubleSerializer.class)
    public double getMaxScore() {
        return assign.getMaxScore();
    }

    /**
     * Builder class provides a fluid interface for setting object properties.
     * @param <T> builder
     */
    public static abstract class Builder<T extends Builder<T>> extends AbstractDigitalResource.Builder<T>  {
        private Assignment assign = new Assignment();

        /**
         * Initialize type with default value.
         */
        public Builder() {
            super.type(EntityType.ASSIGNABLE_DIGITAL_RESOURCE);
        }

        /**
         * @param dateToActivate
         * @return builder
         */
        public T dateToActivate(DateTime dateToActivate) {
            this.assign.setDateToActivate(dateToActivate);
            return self();
        }

        /**
         * @param dateToShow
         * @return builder
         */
        public T dateToShow(DateTime dateToShow) {
            this.assign.setDateToShow(dateToShow);
            return self();
        }

        /**
         * @param dateToStartOn
         * @return builder
         */
        public T dateToStartOn(DateTime dateToStartOn) {
            this.assign.setDateToStartOn(dateToStartOn);
            return self();
        }

        /**
         * @param dateToSubmit
         * @return builder
         */
        public T dateToSubmit(DateTime dateToSubmit) {
            this.assign.setDateToSubmit(dateToSubmit);
            return self();
        }

        /**
         * @param maxAttempts
         * @return builder
         */
        public T maxAttempts(int maxAttempts) {
            this.assign.setMaxAttempts(maxAttempts);
            return self();
        }

        /**
         * @param maxSubmits
         * @return builder
         */
        public T maxSubmits(int maxSubmits) {
            this.assign.setMaxSubmits(maxSubmits);
            return self();
        }

        /**
         * @param maxScore
         * @return builder
         */
        public T maxScore(double maxScore) {
            this.assign.setMaxScore(maxScore);
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