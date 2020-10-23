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

package org.imsglobal.caliper.entities.outcome;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.imsglobal.caliper.entities.AbstractEntity;
import org.imsglobal.caliper.entities.CaliperGeneratable;
import org.imsglobal.caliper.entities.EntityType;
import org.imsglobal.caliper.entities.agent.CaliperAgent;

import javax.annotation.Nullable;

public class Result extends AbstractEntity implements CaliperGeneratable {

    @JsonProperty("attempt")
    private Attempt attempt;

    @JsonProperty("maxResultScore")
    private double maxResultScore;

    @JsonProperty("resultScore")
    private double resultScore;

    @JsonProperty("comment")
    private String comment;

    @JsonProperty("scoredBy")
    private CaliperAgent scoredBy;

    /**
     * @param builder apply builder object properties to the object.
     */
    private Result(Builder<?> builder) {
        super(builder);

        this.attempt = builder.attempt;
        this.maxResultScore = builder.maxResultScore;
        this.resultScore = builder.resultScore;
        this.comment = builder.comment;
        this.scoredBy = builder.scoredBy;
    }

    /**
     * @return attempt associated with the response;
     */
    @Nullable
    public Attempt getAttempt() {
        return attempt;
    }

    /**
     * @return the maxResultScore
     */
    @Nullable
    // @JsonSerialize(using=DoubleSerializer.class)
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    public double getMaxResultScore() {
        return maxResultScore;
    }

    /**
     * @return the resultScore
     */
    @Nullable
    // @JsonSerialize(using=DoubleSerializer.class)
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    public double getResultScore() {
        return resultScore;
    }

    /**
     * @return the comment
     */
    @Nullable
    public String getComment() {
        return comment;
    }

    /**
     * @return the agent who scored the result
     */
    @Nullable
    public CaliperAgent getScoredBy() {
        return scoredBy;
    }

    /**
     * Builder class provides a fluid interface for setting object properties.
     * @param <T> builder.
     */
    public static abstract class Builder<T extends Builder<T>> extends AbstractEntity.Builder<T> {
        private Attempt attempt;
        private double maxResultScore;
        private double resultScore;
        private String comment;
        private CaliperAgent scoredBy;

        /**
         * Constructor
         */
        public Builder() {
            super.type(EntityType.RESULT);
        }

        /**
         * @param attempt
         * @return builder.
         */
        public T attempt(Attempt attempt) {
            this.attempt = attempt;
            return self();
        }

        /**
         * @param maxResultScore
         * @return normal score.
         */
        public T maxResultScore(double maxResultScore) {
            this.maxResultScore = maxResultScore;
            return self();
        }

        /**
         * @param resultScore
         * @return penalty score.
         */
        public T resultScore(double resultScore) {
            this.resultScore = resultScore;
            return self();
        }

        /**
         * @param comment
         * @return comment.
         */
        public T comment(String comment) {
            this.comment = comment;
            return self();
        }

        /**
         * @param scoredBy
         * @return agent who scored the result.
         */
        public T scoredBy(CaliperAgent scoredBy) {
            this.scoredBy = scoredBy;
            return self();
        }

        /**
         * Client invokes build method in order to create an immutable object.
         * @return a new instance of the Result.
         */
        public Result build() {
            return new Result(this);
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