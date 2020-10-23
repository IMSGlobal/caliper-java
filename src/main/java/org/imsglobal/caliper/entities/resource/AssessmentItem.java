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

import com.fasterxml.jackson.annotation.JsonProperty;
import org.imsglobal.caliper.entities.EntityType;

import javax.annotation.Nullable;

/**
 * Caliper representation of an Assessment Item.  Part of the Assessment Metric Profile.
 */
public class AssessmentItem extends AbstractAssignableDigitalResource implements CaliperAssessable {

    @JsonProperty("isTimeDependent")
    private final Boolean isTimeDependent;

    /**
     * @param builder apply builder object properties to the AssessmentItem object.
     */
    private AssessmentItem(Builder<?> builder) {
        super(builder);
        this.isTimeDependent = builder.isTimeDependent;
    }

    /**
     * Indicate whether or not the time taken to respond is important and must be recorded.
     * This could be used by responses which set a sequence of events to be completed
     * in a predefined period or where the response sequence is determined by the
     * time taken to complete certain responses.
     *
     * A Boolean object rather than a boolean primitive data type is utilized to avoid inadvertently
     * serializing the boolean primitive's default value (false).
     *
     * @return true/false
     */
    @Nullable
    public Boolean getIsTimeDependent() {
        return isTimeDependent;
    }

    /**
     * Builder class provides a fluid interface for setting object properties.
     * @param <T> builder
     */
    public static abstract class Builder<T extends Builder<T>> extends AbstractAssignableDigitalResource.Builder<T>  {
        private Boolean isTimeDependent;

        /**
         * Constructor
         */
        public Builder() {
            super.type(EntityType.ASSESSMENT_ITEM);
        }

        /**
         * Wrap primitive in Boolean object to ensure that serializing this property only occurs if set by user.
         * @param isTimeDependent
         * @return builder.
         */
        public T isTimeDependent(boolean isTimeDependent) {
            this.isTimeDependent = isTimeDependent;
            return self();
        }

        /**
         * Client invokes build method in order to create an immutable object.
         * @return a new instance of AssessmentItem.
         */
        public AssessmentItem build() {
            return new AssessmentItem(this);
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