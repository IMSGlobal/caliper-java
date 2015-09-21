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
import org.imsglobal.caliper.validators.EntityValidator;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * An audio object embedded in a web page.
 */
public class AudioObject extends MediaObject implements org.imsglobal.caliper.entities.schemadotorg.AudioObject {

    @JsonProperty("@type")
    private final MediaObjectType type;

    @JsonProperty("volumeMin")
    private String volumeMin;

    @JsonProperty("volumeMax")
    private String volumeMax;

    @JsonProperty("volumeLevel")
    private String volumeLevel;

    @JsonProperty("muted")
    private boolean muted;

    /**
     * @param builder apply builder object properties to the AudioObject object.
     */
    protected AudioObject(Builder<?> builder) {
        super(builder);

        EntityValidator.checkType(builder.type, MediaObjectType.AUDIO_OBJECT);

        this.type = builder.type;
        this.volumeMin = builder.volumeMin;
        this.volumeMax = builder.volumeMax;
        this.volumeLevel = builder.volumeLevel;
        this.muted = builder.muted;
    }

    /**
     * @return the type
     */
    @Override
    @Nonnull
    public MediaObjectType getType() {
        return type;
    }

    /**
     * @return minimum volume
     */
    @Nullable
    public String getVolumeMin() {
        return volumeMin;
    }

    /**
     * @return maximum volume
     */
    @Nullable
    public String getVolumeMax() {
        return volumeMax;
    }

    /**
     * @return volume level
     */
    @Nullable
    public String getVolumeLevel() {
        return volumeLevel;
    }

    /**
     * @return muted status (true/false)
     */
    @Nullable
    public boolean getMuted() {
        return muted;
    }

    /**
     * Builder class provides a fluid interface for setting object properties.
     * @param <T> builder
     */
    public static abstract class Builder<T extends Builder<T>> extends MediaObject.Builder<T>  {
        private MediaObjectType type;
        private String volumeMin;
        private String volumeMax;
        private String volumeLevel;
        private boolean muted;

        /**
         * Initialize type with default value.  Required if builder().type() is not set by user.
         */
        public Builder() {
            type(MediaObjectType.AUDIO_OBJECT);
        }

        /**
         * @param type
         * @return builder.
         */
        private T type(MediaObjectType type) {
            this.type = type;
            return self();
        }

        /**
         * @param volumeMin
         * @return builder
         */
        public T volumeMin(String volumeMin) {
            this.volumeMin = volumeMin;
            return self();
        }

        /**
         * @param volumeMax
         * @return builder
         */
        public T volumeMax(String volumeMax) {
            this.volumeMax = volumeMax;
            return self();
        }

        /**
         * @param volumeLevel
         * @return builder
         */
        public T volumeLevel(String volumeLevel) {
            this.volumeLevel = volumeLevel;
            return self();
        }

        /**
         * @param muted
         * @return builder
         */
        public T muted (boolean muted) {
            this.muted = muted;
            return self();
        }

        /**
         * Client invokes build method in order to create an immutable object.
         * @return a new instance of AudioObject.
         */
        public AudioObject build() {
            return new AudioObject(this);
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