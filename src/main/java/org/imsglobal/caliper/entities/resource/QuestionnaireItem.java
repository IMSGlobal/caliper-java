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

package org.imsglobal.caliper.entities.resource;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import org.imsglobal.caliper.entities.EntityType;
import org.imsglobal.caliper.entities.question.CaliperQuestion;

import javax.annotation.Nullable;
import java.util.List;

/**
 * Caliper representation of a QuestionnaireItem.
 */
public class QuestionnaireItem extends AbstractDigitalResource {

    @JsonProperty("categories")
    private final ImmutableList<String> categories;

    @JsonProperty("question")
    private final CaliperQuestion question;

    @JsonProperty("weight")
    private final double weight;

    /**
     * @param builder apply builder object properties to the QuestionnaireItem object.
     */
    private QuestionnaireItem(Builder<?> builder) {
        super(builder);
        this.categories = ImmutableList.copyOf(builder.categories);
        this.question = builder.question;
        this.weight = builder.weight;
    }

    /**
     * @return an immutable view of the categories list
     */
    @Nullable
    public ImmutableList<String> getCategories() {
        return categories;
    }

    /**
     * @return the question posed
     */
    @Nullable
    public CaliperQuestion getQuestion() { return question; }

    /**
     * @return the weight
     */
    @Nullable
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    public double getWeight() {
        return weight;
    }

    /**
     * Builder class provides a fluid interface for setting object properties.
     * @param <T> builder
     */
    public static abstract class Builder<T extends Builder<T>> extends AbstractDigitalResource.Builder<T>  {
        private List<String> categories = Lists.newArrayList();
        private CaliperQuestion question;
        private double weight;

        /**
         * Constructor
         */
        public Builder() {
            super.type(EntityType.QUESTIONNAIRE_ITEM);
        }

        /**
         * @param categories
         * @return builder.
         */
        public T categories(List<String> categories) {
            if(categories != null) {
                this.categories.addAll(categories);
            }
            return self();
        }

        /**
         * @param category
         * @return builder.
         */
        public T category(String category) {
            this.categories.add(category);
            return self();
        }

        /**
         * @param question
         * @return builder.
         */
        public T question(CaliperQuestion question) {
            this.question = question;
            return self();
        }

        /**
         * @param weight
         * @return builder.
         */
        public T weight(double weight) {
            this.weight = weight;
            return self();
        }

        /**
         * Client invokes build method in order to create an immutable object.
         * @return a new instance of QuestionnaireItem.
         */
        public QuestionnaireItem build() {
            return new QuestionnaireItem(this);
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