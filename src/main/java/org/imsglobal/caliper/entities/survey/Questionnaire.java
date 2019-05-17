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

package org.imsglobal.caliper.entities.survey;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import org.imsglobal.caliper.entities.CaliperCollection;
import org.imsglobal.caliper.entities.EntityType;
import org.imsglobal.caliper.entities.resource.AbstractDigitalResource;

import javax.annotation.Nullable;
import java.util.List;

public class Questionnaire extends AbstractDigitalResource implements CaliperCollection<QuestionnaireItem> {

    @JsonProperty("items")
    private final ImmutableList<QuestionnaireItem> items;

    /**
     * @param builder apply builder object properties to the object.
     */
    protected Questionnaire(Builder<?> builder) {
        super(builder);

        this.items = ImmutableList.copyOf(builder.items);
    }

    /**
     * Return an immutable list of the Collection's items.
     * @return the items
     */
    @Override
    @Nullable
    public ImmutableList<QuestionnaireItem> getItems() {
        return items;
    }

    /**
     * Builder class provides a fluid interface for setting object properties.
     * @param <T> builder.
     */
    public static abstract class Builder<T extends Builder<T>> extends AbstractDigitalResource.Builder<T> {
        private List<QuestionnaireItem> items = Lists.newArrayList();

        /**
         * Constructor
         */
        public Builder() {
            super.type(EntityType.FORUM);
        }

        /**
         * @param items
         * @return builder.
         */
        public T items(List<QuestionnaireItem> items) {
            if(items != null) {
                this.items.addAll(items);
            }
            return self();
        }

        /**
         * @param item
         * @return builder.
         */
        public T item(QuestionnaireItem item) {
            this.items.add(item);
            return self();
        }

        /**
         * Client invokes build method in order to create an immutable object.
         * @return a new instance of the Questionnaire.
         */
        public Questionnaire build() {
            return new Questionnaire(this);
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