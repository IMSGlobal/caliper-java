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

package org.imsglobal.caliper.entities.question;

import javax.annotation.Nullable;

import org.imsglobal.caliper.entities.EntityType;
import org.joda.time.DateTime;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DateTimeQuestion extends AbstractQuestion {

    @JsonProperty("minDateTime")
    private final DateTime minDateTime;

    @JsonProperty("minLabel")
    private final String minLabel;

    @JsonProperty("maxDateTime")
    private final DateTime maxDateTime;

    @JsonProperty("maxLabel")
    private final String maxLabel;

    /**
     * @param builder apply builder object properties to the object.
     */
    private DateTimeQuestion(Builder<?> builder) {
        super(builder);
        this.minDateTime = builder.minDateTime;
        this.minLabel = builder.minLabel;
        this.maxDateTime = builder.maxDateTime;
        this.maxLabel = builder.maxLabel;
    }

    /**
     * @return minimum date and time
     */
    @Nullable
    public DateTime getMinDateTime() {
        return minDateTime;
    }

    /**
     * @return natural language label for minimum date and time
     */
    @Nullable
    public String getMinLabel() {
        return minLabel;
    }

    /**
     * @return maximum date and time
     */
    @Nullable
    public DateTime getMaxDateTime() {
        return maxDateTime;
    }

    /**
     * @return natural language label for maximum date and time
     */
    @Nullable
    public String getMaxLabel() {
        return maxLabel;
    }

    /**
     * Builder class provides a fluid interface for setting object properties.
     * @param <T> builder.
     */
    public static abstract class Builder<T extends Builder<T>> extends AbstractQuestion.Builder<T> {
        private DateTime minDateTime;
        private String minLabel;
        private DateTime maxDateTime;
        private String maxLabel;

        /**
         * Constructor
         */
        public Builder() {
            super.type(EntityType.DATE_TIME_QUESTION);
        }

        public T minDateTime(DateTime minDateTime) {
            this.minDateTime = minDateTime;
            return self();
        }

        public T minLabel(String minLabel) {
            this.minLabel = minLabel;
            return self();
        }

        public T maxDateTime(DateTime maxDateTime) {
            this.maxDateTime = maxDateTime;
            return self();
        }

        public T maxLabel(String maxLabel) {
            this.maxLabel = maxLabel;
            return self();
        }

        /**
         * Client invokes build method in order to create an immutable object.
         * @return a new instance of the Question.
         */
        public DateTimeQuestion build() {
            return new DateTimeQuestion(this);
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