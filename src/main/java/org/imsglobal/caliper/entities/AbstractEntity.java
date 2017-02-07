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

package org.imsglobal.caliper.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import org.imsglobal.caliper.Context;
import org.imsglobal.caliper.Type;
import org.imsglobal.caliper.validators.EntityValidator;
import org.joda.time.DateTime;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

public abstract class AbstractEntity implements Entity {

    @JsonProperty("@context")
    protected final Context context;

    @JsonProperty("id")
    protected final String id;

    @JsonProperty("type")
    private final Type type;

    @JsonProperty("name")
    private final String name;

    @JsonProperty("description")
    private final String description;

    @JsonProperty("dateCreated")
    private final DateTime dateCreated;

    @JsonProperty("dateModified")
    private final DateTime dateModified;

    @JsonProperty("extensions")
    private final ImmutableList<Object> extensions;

    /**
     * @param builder apply builder object properties to the object.
     */
    protected AbstractEntity(Builder<?> builder) {

        EntityValidator.checkContext(builder.context, Context.REMOTE_CALIPER_JSONLD_CONTEXT);
        EntityValidator.checkId("id", builder.id);

        this.context = builder.context;
        this.id = builder.id;
        this.type = builder.type;
        this.name = builder.name;
        this.description = builder.description;
        this.dateCreated = builder.dateCreated;
        this.dateModified = builder.dateModified;
        this.extensions = ImmutableList.copyOf(builder.extensions);
    }

    /**
     * @return the context.
     */
    @Nonnull
    public Context getContext() {
        return context;
    }

    /**
     * @return the id.
     */
    @Nonnull
    public String getId() {
        return id;
    }

    /**
     * @return the type.
     */
    @Nonnull
    public Type getType() {
        return type;
    }

    /**
     * @return name.
     */
    @Nullable
    public String getName() {
        return name;
    }

    /**
     * @return description.
     */
    @Nullable
    public String getDescription() {
        return description;
    }

    /**
     * @return date created.
     */
    @Nullable
    public DateTime getDateCreated()
    {
        return dateCreated;
    }

    /**
     * @return the date modified.
     */
    @Nullable
    public DateTime getDateModified() {
        return dateModified;
    }

    /**
     * @return custom extensions object.
     */
    @Nullable
    public ImmutableList<Object> getExtensions() {
        return extensions;
    }

    /**
     * Builder class provides a fluid interface for setting object properties.
     * @param <T> builder.
     */
    public static abstract class Builder<T extends Builder<T>> {
        private Context context;
        private String id;
        private Type type;
        private String name;
        private String description;
        private DateTime dateCreated;
        private DateTime dateModified;
        private List<Object> extensions = Lists.newArrayList();

        /**
         * Constructor
         */
        public Builder() {
            context(Context.REMOTE_CALIPER_JSONLD_CONTEXT);
            type(EntityType.ENTITY);
        }

        protected abstract T self();

        /**
         * @param context
         * @return builder.
         */
        public T context(Context context) {
            this.context = context;
            return self();
        }

        /**
         * @param id
         * @return builder.
         */
        public T id(String id) {
            this.id = id;
            return self();
        }

        /**
         * @param type
         * @return builder.
         */
        public T type(Type type) {
            this.type = type;
            return self();
        }

        /**
         * @param name
         * @return builder.
         */
        public T name(String name) {
            this.name = name;
            return self();
        }

        /**
         * @param description
         * @return builder.
         */
        public T description(String description) {
            this.description = description;
            return self();
        }

        /**
         * @param dateCreated
         * @return builder.
         */
        public T dateCreated(DateTime dateCreated) {
            this.dateCreated = dateCreated;
            return self();
        }

        /**
         * @param dateModified
         * @return builder.
         */
        public T dateModified(DateTime dateModified) {
            this.dateModified = dateModified;
            return self();
        }

        /**
         * @param extensions
         * @return builder.
         */
        public T extensions(List<Object> extensions) {
            this.extensions = extensions;
            return self();
        }

        /**
         * @param extension
         * @return builder.
         */
        public T extension(Object extension) {
            this.extensions.add(extension);
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