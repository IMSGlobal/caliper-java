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
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import org.imsglobal.caliper.entities.EntityType;
import org.imsglobal.caliper.entities.TimePeriod;
import javax.annotation.Nullable;

import java.util.List;

/**
 * Representation of a response to a multiple choice question that permits one or more
 * options to be selected.
 */
public class MultipleResponseResponse extends AbstractResponse {

    @JsonProperty("values")
    private ImmutableList<String> values;

    /**
     * @param builder apply builder object properties to the Response object.
     */
    private MultipleResponseResponse(Builder<?> builder) {
        super(builder);
        this.values = ImmutableList.copyOf(builder.values);
    }

    /**
     * @return response values
     */
    @Nullable
    public ImmutableList<String> getValues() {
        return values;
    }

    /**
     * Builder class provides a fluid interface for setting object properties.
     * @param <T> builder
     */
    public static abstract class Builder<T extends Builder<T>> extends AbstractResponse.Builder<T>  {
        private List<String> values = Lists.newArrayList();
        private TimePeriod timePeriod = new TimePeriod();

        /**
         * Initialize type with default value.
         */
        public Builder() {
            super.type(EntityType.MULTIPLERESPONSE);
        }

        /**
         * @param values
         * @return builder.
         */
        public T values(List<String> values) {
            if(values != null) {
                this.values.addAll(values);
            }
            return self();
        }

        /**
         * @param value
         * @return builder.
         */
        public T value(String value) {
            this.values.add(value);
            return self();
        }

        /**
         * Client invokes build method in order to create an immutable object.
         * @return a new instance of MultipleResponseResponse.
         */
        public MultipleResponseResponse build() {
            return new MultipleResponseResponse(this);
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