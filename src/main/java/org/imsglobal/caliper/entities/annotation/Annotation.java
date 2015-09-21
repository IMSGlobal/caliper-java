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

package org.imsglobal.caliper.entities.annotation;

import com.fasterxml.jackson.annotation.*;
import org.imsglobal.caliper.entities.DigitalResource;
import org.imsglobal.caliper.entities.Entity;
import org.imsglobal.caliper.entities.EntityType;
import org.imsglobal.caliper.entities.Type;
import org.imsglobal.caliper.validators.EntityValidator;

import javax.annotation.Nonnull;

/**
 * The super-class of all Annotation types.
 * 
 * Direct sub-types can include - Highlight, Attachment, etc. - all of
 * which are specified in the Caliper Annotation Metric Profile
 */
public abstract class Annotation extends Entity implements org.imsglobal.caliper.entities.Generatable {

    @JsonProperty("@type")
    private final Type type;

    @JsonProperty("annotated")
    private DigitalResource annotated;

    /**
     * @param builder apply builder object properties to the Annotation object.
     */
    protected Annotation(Builder<?> builder) {
        super(builder);

        EntityValidator.checkType(builder.type, EntityType.ANNOTATION);
        EntityValidator.checkId("annotated Id", builder.annotated.getId());

        this.type = builder.type;
        this.annotated = builder.annotated;
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
     * Serialization of DigitalResource associated with this Annotation is limited to
     * the identifying URI only.
     * @return the annotated object's identifier
     */
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "@id")
    @JsonIdentityReference(alwaysAsId = true)
    @Nonnull
    public DigitalResource getAnnotated() {
        return annotated;
    }


    /**
     * Builder class provides a fluid interface for setting object properties.
     * @param <T> builder
     */
    public static abstract class Builder<T extends Builder<T>> extends Entity.Builder<T>  {
        private Type type;
        private DigitalResource annotated;

        /**
         * Initialize type with default value.  Required if builder().type() is not set by user.
         */
        public Builder() {
            type(EntityType.ANNOTATION);
        }

        /**
         * @param type
         * @return builder.
         */
        private T type(Type type) {
            this.type = type;
            return self();
        }

        /**
         * @param annotated
         * @return builder.
         */
        public T annotated(DigitalResource annotated) {
            this.annotated = annotated;
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