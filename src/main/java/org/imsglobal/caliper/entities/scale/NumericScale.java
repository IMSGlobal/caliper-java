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

package org.imsglobal.caliper.entities.scale;

import javax.annotation.Nullable;

import org.imsglobal.caliper.entities.AbstractEntity;
import org.imsglobal.caliper.entities.EntityType;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

public class NumericScale extends AbstractEntity implements CaliperScale {

    @JsonProperty("maxLabel")
    private final String maxLabel;

    @JsonProperty("maxValue")
    private final double maxValue;

    @JsonProperty("minLabel")
    private final String minLabel;

    @JsonProperty("minValue")
    private final double minValue;

    @JsonProperty("step")
    private final double step;

    /**
     * @param builder apply builder object properties to the object.
     */
    protected NumericScale(Builder<?> builder) {
        super(builder);
        this.maxLabel = builder.maxLabel;
        this.minLabel = builder.minLabel;
        this.maxValue = builder.maxValue;
        this.minValue = builder.minValue;
        this.step = builder.step;
    }

    /**
     * @return the label for the maximum value.
     */
    @Nullable
    public String getMaxLabel() {
        return maxLabel;
    }

    /**
     * @return the maximum value allowed.
     */
    @Nullable    
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    public double getMaxValue() {
        return maxValue;
    }

    /**
     * @return the label for the minimum value.
     */
    @Nullable
    public String getMinLabel() {
        return minLabel;
    }

    /**
     * @return the minimum value allowed.
     */
    @Nullable        
    public double getMinValue() {
        return minValue;
    }

    /**
     * @return the decimal step value
     */
    @Nullable    
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    public double getStep() {
        return step;
    }

    /**
     * Builder class provides a fluid interface for setting object properties.
     * @param <T> builder.
     */
    public static abstract class Builder<T extends Builder<T>> extends AbstractEntity.Builder<T> {
        private String maxLabel;
        private String minLabel;
        private double maxValue;
        private double minValue;
        private double step;

        /**
         * Constructor
         */
        public Builder() {
            super.type(EntityType.NUMERIC_SCALE);
        }

        /**
         * @param maxLabel
         * @return builder.
         */
        public T maxLabel(String maxLabel) {
            this.maxLabel = maxLabel;
            return self();
        }

        /**
         * @param maxValue
         * @return builder.
         */
        public T maxValue(double maxValue) {
            this.maxValue = maxValue;
            return self();
        }

        /**
         * @param minLabel
         * @return builder.
         */
        public T minLabel(String minLabel) {
            this.minLabel = minLabel;
            return self();
        }

        /**
         * @param minValue
         * @return builder.
         */
        public T minValue(double minValue) {
            this.minValue = minValue;
            return self();
        }

        /**
         * @param step
         * @return builder.
         */
        public T step(double step) {
            this.step = step;
            return self();
        }

        /**
         * Client invokes build method in order to create an immutable object.
         * @return a new instance of the NumericScale.
         */
        public NumericScale build() {
            return new NumericScale(this);
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