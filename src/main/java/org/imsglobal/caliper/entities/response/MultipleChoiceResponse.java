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

import com.fasterxml.jackson.annotation.JsonProperty;
import org.imsglobal.caliper.entities.EntityType;
import javax.annotation.Nullable;

/**
 * Represents a response to a multiple choice question that permits a single option to be selected.
 */
public class MultipleChoiceResponse extends AbstractResponse {

    @JsonProperty("value")
    private String value;

    /**
     * @param builder apply builder object properties to the Response object.
     */
    private MultipleChoiceResponse(Builder<?> builder) {
        super(builder);
        this.value = builder.value;
    }

    /**
     * @return response value
     */
    @Nullable
    public String getValue() {
        return value;
    }

    /**
     * Builder class provides a fluid interface for setting object properties.
     * @param <T> builder
     */
    public static abstract class Builder<T extends Builder<T>> extends AbstractResponse.Builder<T>  {
        private String value;

        /**
         * Initialize type with default value.
         */
        public Builder() {
            super.type(EntityType.MULTIPLECHOICE);
        }

        /**
         * @param value
         * @return builder.
         */
        public T value(String value) {
            this.value = value;
            return self();
        }

        /**
         * Client invokes build method in order to create an immutable object.
         * @return a new instance of MultipleChoiceResponse.
         */
        public MultipleChoiceResponse build() {
            return new MultipleChoiceResponse(this);
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