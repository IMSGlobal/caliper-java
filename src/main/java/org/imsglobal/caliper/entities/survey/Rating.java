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

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import org.imsglobal.caliper.entities.*;
import org.imsglobal.caliper.entities.agent.Person;
import org.imsglobal.caliper.entities.question.CaliperQuestion;

import javax.annotation.Nullable;
import java.util.List;

public class Rating extends AbstractEntity implements CaliperGeneratable {

    @JsonProperty("question")
    private final CaliperQuestion question;

    @JsonProperty("rater")
    private final Person rater;

    @JsonProperty("rated")
    private final CaliperEntity rated;

    @JsonProperty("ratingComment")
    private final Comment ratingComment;

    @JsonProperty("selections")
    private final ImmutableList<String> selections;

    /**
     * @param builder apply builder object properties to the object.
     */
    private Rating(Builder<?> builder) {
        super(builder);
        this.question = builder.question;
        this.rater = builder.rater;
        this.rated = builder.rated;
        this.ratingComment = builder.ratingComment;
        this.selections = ImmutableList.copyOf(builder.selections);
    }

    /**
     * @return the question
     */
    @Nullable
    public CaliperQuestion getQuestion() { return question; }

    /**
     * @return the rater
     */
    @Nullable
    public Person getRater() { return rater; }

    /**
     * @return the rated entity
     */
    @Nullable
    public CaliperEntity getRated() { return rated; }

    /**
     * @return the rating comment
     */
    @Nullable
    public Comment getRatingComment() { return ratingComment; }

    /**
     * Return an immutable list of selections.
     * @return the selections
     */
    @Nullable
    public ImmutableList<String> getSelections() {
        return selections;
    }

    /**
     * Builder class provides a fluid interface for setting object properties.
     * @param <T> builder.
     */
    public static abstract class Builder<T extends Builder<T>> extends AbstractEntity.Builder<T> {
        private CaliperQuestion question;
        private Person rater;
        private CaliperEntity rated;
        private Comment ratingComment;
        private List<String> selections = Lists.newArrayList();

        /**
         * Constructor
         */
        public Builder() {
            super.type(EntityType.RATING);
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
         * @param rater
         * @return builder.
         */
        public T rater(Person rater) {
            this.rater = rater;
            return self();
        }

        /**
         * @param rated
         * @return builder.
         */
        public T rated(CaliperEntity rated) {
            this.rated = rated;
            return self();
        }

        /**
         * @param ratingComment
         * @return builder.
         */
        public T ratingComment(Comment ratingComment) {
            this.ratingComment = ratingComment;
            return self();
        }

        /**
         * @param selection
         * @return builder.
         */
        public T selection(String selection) {
            this.selections.add(selection);
            return self();
        }

        /**
         * @param selections
         * @return builder.
         */
        public T selections(List<String> selections) {
            if(selections != null) {
                this.selections.addAll(selections);
            }
            return self();
        }

        /**
         * Client invokes build method in order to create an immutable object.
         * @return a new instance of the Rating.
         */
        public Rating build() {
            return new Rating(this);
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