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
import org.imsglobal.caliper.entities.Type;
import org.imsglobal.caliper.validators.EntityValidator;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * An image, video, or audio object embedded in a web page.
 */
public abstract class MediaObject extends DigitalResource implements org.imsglobal.caliper.entities.schemadotorg.MediaObject {

    @JsonProperty("@type")
    private final Type type;

    @JsonProperty("duration")
    private long duration;

    /**
     * @param builder apply builder object properties to the MediaObject object.
     */
    protected MediaObject(Builder<?> builder) {
        super(builder);

        EntityValidator.checkType(builder.type, DigitalResourceType.MEDIA_OBJECT);

        this.type = builder.type;
        this.duration = builder.duration;
    }

    /**
     * @return the type
     */
    @Override
    @Nonnull
    public Type getType() {
        return type;
    }

    /**
     * @return duration
     */
    @Nullable
    public long getDuration() {
        return duration;
    }

    /**
     * Initialize default parameter values in the builder (not in the outer profile class).  Given the abstract nature
     * of Profile, the builder's .build() method is omitted.
     * @param <T> builder
     */
    public static abstract class Builder<T extends Builder<T>> extends DigitalResource.Builder<T> {
        private DigitalResourceType type;
        private long duration;

        protected abstract T self();

        /**
         * Initialize type with default value.  Required if builder().type() is not set by user.
         */
        public Builder() {
            type(DigitalResourceType.MEDIA_OBJECT);
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
         * @param duration
         * @return duration
         */
        public T duration(long duration) {
            this.duration = duration;
            return self();
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
}