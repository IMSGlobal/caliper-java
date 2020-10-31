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

package org.imsglobal.caliper.entities.use;

import javax.annotation.Nullable;

import org.imsglobal.caliper.entities.AbstractEntity;
import org.imsglobal.caliper.entities.EntityType;
import org.imsglobal.caliper.entities.TimePeriod;
import org.imsglobal.caliper.validators.EntityValidator;
import org.joda.time.DateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Representation of an AggregateMeasure.
 */
public class AggregateMeasure extends AbstractEntity {

    @JsonProperty("metric")
    private CaliperMetric metric;

    @JsonProperty("metricValue")
    private double metricValue;

    @JsonProperty("maxMetricValue")
    private double maxMetricValue;

    @JsonIgnore
    private TimePeriod timePeriod = new TimePeriod();

    /**
     * @param builder apply builder object properties to the object.
     */
    protected AggregateMeasure(Builder<?> builder) {
        super(builder);
        this.metric = builder.metric;
        this.metricValue = builder.metricValue;
        this.maxMetricValue = builder.maxMetricValue;

        EntityValidator.checkStartTime(builder.timePeriod.getStartedAtTime(), builder.timePeriod.getEndedAtTime());

        this.timePeriod.setStartedAtTime(builder.timePeriod.getStartedAtTime());
        this.timePeriod.setEndedAtTime(builder.timePeriod.getEndedAtTime());
    }

    /**
     * @return the metric
     */
    @Nullable
    public CaliperMetric getMetric() {
        return metric;
    }

    /**
     * @return the metricValue
     */
    @Nullable
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    public double getMetricValue() {
        return metricValue;
    }

    /**
     * @return the maxMetricValue
     */
    @Nullable
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    public double getMaxMetricValue() {
        return maxMetricValue;
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
     * Builder class provides a fluid interface for setting object properties.
     * @param <T> builder.
     */
    public static abstract class Builder<T extends Builder<T>> extends AbstractEntity.Builder<T> {
        private CaliperMetric metric;
        private double metricValue;
        private double maxMetricValue;
        private TimePeriod timePeriod = new TimePeriod();

        /**
         * Constructor
         */
        public Builder() {
            super.type(EntityType.AGGREGATE_MEASURE);
        }

        /**
         * @param metric
         * @return builder
         */
        public T metric(CaliperMetric metric) {
            this.metric = metric;
            return self();
        }

        /**
         * @param metricValue
         * @return builder
         */
        public T metricValue(double metricValue) {
            this.metricValue = metricValue;
            return self();
        }

        /**
         * @param maxMetricValue
         * @return builder
         */
        public T maxMetricValue(double maxMetricValue) {
            this.maxMetricValue = maxMetricValue;
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
         * Client invokes build method in order to create an immutable object.
         * @return a new instance of the AggregateMeasure.
         */
        public AggregateMeasure build() {
            return new AggregateMeasure(this);
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