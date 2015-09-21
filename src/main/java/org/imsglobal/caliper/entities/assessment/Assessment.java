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
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.google.common.collect.Lists;
import org.imsglobal.caliper.entities.assignable.AssignableDigitalResource;
import org.imsglobal.caliper.entities.assignable.AssignableDigitalResourceType;
import org.imsglobal.caliper.validators.EntityValidator;

import javax.annotation.Nonnull;
import java.util.List;

/**
 * Caliper representation of an Assessment.  Part of the Assessment Metric Profile
 */

public class Assessment extends AssignableDigitalResource {

    @JsonProperty("@type")
    private final AssignableDigitalResourceType type;

    /**
     * @param builder apply builder object properties to the CaliperAssessment object.
     */
    protected Assessment(Builder<?> builder) {
        super(builder);

        EntityValidator.checkType(builder.type, AssignableDigitalResourceType.ASSESSMENT);

        this.type = builder.type;
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
     * Builder class provides a fluid interface for setting object properties.
     * @param <T> builder
     */
    public static abstract class Builder<T extends Builder<T>> extends AssignableDigitalResource.Builder<T>  {
        private AssignableDigitalResourceType type;
        private List<AssessmentItem> assessmentItems = Lists.newArrayList();

        /**
         * Initialize type with default value.
         */
        public Builder() {
            type(AssignableDigitalResourceType.ASSESSMENT);
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
         * Client invokes build method in order to create an immutable object.
         * @return a new instance of CaliperAssessment.
         */
        public Assessment build() {
            return new Assessment(this);
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