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

package org.imsglobal.caliper.entities.assessment;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.imsglobal.caliper.entities.assignable.AssignableDigitalResource;
import org.imsglobal.caliper.entities.assignable.AssignableDigitalResourceType;
import org.imsglobal.caliper.validators.EntityValidator;

import javax.annotation.Nonnull;

/**
 * Caliper representation of an Assessment Item.  Part of the Assessment Metric Profile.
 */
public class AssessmentItem extends AssignableDigitalResource {

    @JsonProperty("@type")
    private final AssignableDigitalResourceType type;

    @JsonProperty("isTimeDependent")
    private final boolean isTimeDependent;

    /**
     * @param builder apply builder object properties to the AssessmentItem object.
     */
    protected AssessmentItem(Builder<?> builder) {
        super(builder);

        EntityValidator.checkType(builder.type, AssignableDigitalResourceType.ASSESSMENT_ITEM);

        this.type = builder.type;
        this.isTimeDependent = builder.isTimeDependent;
    }

    /**
     * @return the type
     */
    @Override
    @Nonnull
    public AssignableDigitalResourceType getType() {
        return type;
    }

    /**
     * Indicate whether or not the time taken to respond is important and must be recorded.
     * This could be used by responses which set a sequence of events to be completed
     * in a predefined period or where the response sequence is determined by the
     * time taken to complete certain responses.
     *
     * @return true/false
     */
    @Nonnull
    public boolean getIsTimeDependent() {
        return isTimeDependent;
    }

    /**
     * Builder class provides a fluid interface for setting object properties.
     * @param <T> builder
     */
    public static abstract class Builder<T extends Builder<T>> extends AssignableDigitalResource.Builder<T>  {
        private AssignableDigitalResourceType type;
        private boolean isTimeDependent = false;

        /**
         * Constructor
         */
        public Builder() {
            type(AssignableDigitalResourceType.ASSESSMENT_ITEM);
        }

        /**
         * @param type
         * @return builder.
         */
        private T type(AssignableDigitalResourceType type) {
            this.type = type;
            return self();
        }

        /**
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