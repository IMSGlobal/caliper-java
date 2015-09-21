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

package org.imsglobal.caliper.entities.reading;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.imsglobal.caliper.entities.DigitalResource;
import org.imsglobal.caliper.entities.DigitalResourceType;
import org.imsglobal.caliper.entities.Targetable;
import org.imsglobal.caliper.validators.EntityValidator;

import javax.annotation.Nonnull;

public class Frame extends DigitalResource implements Targetable {

    @JsonProperty("@type")
    private final DigitalResourceType type;

    @JsonProperty("index")
    private int index;

    /**
     * @param builder apply builder object properties to the Frame object.
     */
    protected Frame(Builder<?> builder) {
        super(builder);

        EntityValidator.checkType(builder.type, DigitalResourceType.FRAME);

        this.type = builder.type;
        this.index = builder.index;
    }

    /**
     * @return the type
     */
    @Override
    @Nonnull
    public DigitalResourceType getType() {
        return type;
    }

    /**
     * @return numeric index of the location relative to sibling locations in the content
     */
    @Nonnull
    public int getIndex() {
        return index;
    }

    /**
     * Builder class provides a fluid interface for setting object properties.
     * @param <T> builder
     */
    public static abstract class Builder<T extends Builder<T>> extends DigitalResource.Builder<T>  {
        private DigitalResourceType type;
        private int index;

        /**
         * Initialize type with default value.
         */
        public Builder() {
            type(DigitalResourceType.FRAME);
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
         * @param index
         * @return builder.
         */
        public T index(int index) {
            this.index = index;
            return self();
        }

        /**
         * Client invokes build method in order to create an immutable object.
         * @return a new instance of Frame.
         */
        public Frame build() {
            return new Frame(this);
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

