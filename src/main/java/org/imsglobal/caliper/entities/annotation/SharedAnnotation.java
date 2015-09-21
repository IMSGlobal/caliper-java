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

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import org.imsglobal.caliper.entities.foaf.Agent;
import org.imsglobal.caliper.validators.EntityValidator;

import javax.annotation.Nullable;
import java.util.List;

public class SharedAnnotation extends org.imsglobal.caliper.entities.annotation.Annotation {

    @JsonProperty("@type")
    private final AnnotationType type;

    @JsonProperty("withAgents")
    private final ImmutableList<Agent> withAgents;

    /**
     * @param builder apply builder object properties to the SharedAnnotation object.
     */
    protected SharedAnnotation(Builder<?> builder) {
        super(builder);

        EntityValidator.checkType(builder.type, AnnotationType.SHARED_ANNOTATION);

        this.type = builder.type;
        this.withAgents = ImmutableList.copyOf(builder.withAgents);
    }

    /**
     * @return the type
     */
    @Override
    public AnnotationType getType() {
        return type;
    }

    /**
     * Return an immutable view of the withAgents list.
     * @return the users
     */
    @Nullable
    public ImmutableList<Agent> getWithAgents() {
        return withAgents;
    }

    /**
     * Builder class provides a fluid interface for setting object properties.
     * @param <T> builder
     */
    public static abstract class Builder<T extends Builder<T>> extends Annotation.Builder<T>  {
        private AnnotationType type;
        private List<Agent> withAgents = Lists.newArrayList();

        /**
         * Initialize type with default value.  Required if builder().type() is not set by user.
         */
        public Builder() {
            type(AnnotationType.SHARED_ANNOTATION);
        }

        /**
         * @param type
         * @return builder.
         */
        private T type(AnnotationType type) {
            this.type = type;
            return self();
        }

        /**
         * @param withAgents
         * @return shared agents.
         */
        public T withAgents(List<Agent> withAgents) {
            this.withAgents = withAgents;
            return self();
        }

        /**
         * Client invokes build method in order to create an immutable object.
         * @return a new instance of SharedAnnotation.
         */
        public SharedAnnotation build() {
            return new SharedAnnotation(this);
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