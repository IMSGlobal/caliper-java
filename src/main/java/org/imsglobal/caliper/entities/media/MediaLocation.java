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

package org.imsglobal.caliper.entities.media;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.imsglobal.caliper.entities.DigitalResource;
import org.imsglobal.caliper.entities.DigitalResourceType;
import org.imsglobal.caliper.entities.Targetable;
import org.imsglobal.caliper.validators.EntityValidator;

/**
 * Media Location
 */
public class MediaLocation extends DigitalResource implements Targetable {

    @JsonProperty("@type")
    private final DigitalResourceType type;

    @JsonProperty("currentTime")
    private long currentTime;

    /**
     * @param builder apply builder object properties to the MediaLocation object.
     */
    protected MediaLocation(Builder<?> builder) {
        super(builder);

        EntityValidator.checkType(builder.type, DigitalResourceType.MEDIA_LOCATION);

        this.type = builder.type;
        this.currentTime = builder.currentTime;
    }

    /**
     * @return the type
     */
    @Override
    public DigitalResourceType getType() {
        return type;
    }

    /**
     * @return the currentTime
     */
    public long getCurrentTime() {
        return currentTime;
    }

    /**
     * Builder class provides a fluid interface for setting object properties.
     * @param <T> builder
     */
    public static abstract class Builder<T extends Builder<T>> extends DigitalResource.Builder<T>  {
        private DigitalResourceType type;
        private long currentTime;

        /**
         * Initialize type with default values.
         */
        public Builder() {
            type(DigitalResourceType.MEDIA_LOCATION);
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
         * @param currentTime
         * @return builder
         */
        public T currentTime(long currentTime) {
            this.currentTime = currentTime;
            return self();
        }

        /**
         * Client invokes build method in order to create an immutable object.
         * @return a new instance of MediaLocation.
         */
        public MediaLocation build() {
            return new MediaLocation(this);
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