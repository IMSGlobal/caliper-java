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

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import org.imsglobal.caliper.entities.DigitalResource;
import org.imsglobal.caliper.entities.Entity;
import org.imsglobal.caliper.entities.EntityType;
import org.imsglobal.caliper.entities.Generatable;
import org.imsglobal.caliper.entities.foaf.Agent;
import org.imsglobal.caliper.validators.EntityValidator;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Representation of a Result. Result's are generated as
 * part of an interaction represented by an OutcomeEvent.
 */
public class Result extends Entity implements Generatable {

    @JsonProperty("@type")
    private final EntityType type;

    @JsonProperty("assignable")
    private final DigitalResource assignable;

    @JsonProperty("actor")
    private final Agent actor;

    @JsonProperty("normalScore")
    private double normalScore;

    @JsonProperty("penaltyScore")
    private double penaltyScore;

    @JsonProperty("extraCreditScore")
    private double extraCreditScore;

    @JsonProperty("totalScore")
    private double totalScore;

    @JsonProperty("curvedTotalScore")
    private double curvedTotalScore;

    @JsonProperty("curveFactor")
    private double curveFactor;

    @JsonProperty("comment")
    private String comment;

    @JsonProperty("scoredBy")
    private Agent scoredBy;

    /**
     * @param builder apply builder object properties to the Result object.
     */
    protected Result(Builder<?> builder) {
        super(builder);

        EntityValidator.checkType(builder.type, EntityType.RESULT);
        EntityValidator.checkId("assignable Id", builder.assignable.getId());
        EntityValidator.checkId("actor Id", builder.actor.getId());

        this.type = builder.type;
        this.assignable = builder.assignable;
        this.actor = builder.actor;
        this.normalScore = builder.normalScore;
        this.penaltyScore = builder.penaltyScore;
        this.extraCreditScore = builder.extraCreditScore;
        this.totalScore = builder.totalScore;
        this.curvedTotalScore = builder.curvedTotalScore;
        this.curveFactor = builder.curveFactor;
        this.comment = builder.comment;
        this.scoredBy = builder.scoredBy;
    }

    /**
     * @return the type
     */
    @Override
    @Nonnull
    public EntityType getType() {
        return type;
    }

    /**
     * Serialization of Assignable associated with this Result is limited to
     * the identifying URI only.
     * @return the assignable identifier
     */
    @Nonnull
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "@id")
    @JsonIdentityReference(alwaysAsId = true)
    public DigitalResource getAssignable() {
        return assignable;
    }

    /**
     * Serialization of Agent associated with this Result is limited to
     * the identifying URI only.
     * @return the actor identifier
     */
    @Nonnull
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "@id")
    @JsonIdentityReference(alwaysAsId = true)
    public Agent getActor() {
        return actor;
    }

    /**
     * @return the normalScore
     */
    @Nullable
    public double getNormalScore() {
        return normalScore;
    }

    /**
     * @return the penaltyScore
     */
    @Nullable
    public double getPenaltyScore() {
        return penaltyScore;
    }

    /**
     * @return the extraCreditScore
     */
    @Nullable
    public double getExtraCreditScore() {
        return extraCreditScore;
    }

    /**
     * @return the totalScore
     */
    @Nullable
    public double getTotalScore() {
        return totalScore;
    }

    /**
     * @return the curvedTotalScore
     */
    @Nullable
    public double getCurvedTotalScore() {
        return curvedTotalScore;
    }

    /**
     * @return the curveFactor
     */
    @Nullable
    public double getCurveFactor() {
        return curveFactor;
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
    public Agent getScoredBy() {
        return scoredBy;
    }


    /**
     * Builder class provides a fluid interface for setting object properties.
     * @param <T> builder
     */
    public static abstract class Builder<T extends Builder<T>> extends Entity.Builder<T>  {
        private EntityType type;
        private DigitalResource assignable;
        private Agent actor;
        private double normalScore;
        private double penaltyScore;
        private double extraCreditScore;
        private double totalScore;
        private double curvedTotalScore;
        private double curveFactor;
        private String comment;
        private Agent scoredBy;

        /**
         * Initialize type with default value.  Required if builder().type() is not set by user.
         */
        public Builder() {
            type(EntityType.RESULT);
        }

        /**
         * @param type
         * @return builder.
         */
        private T type(EntityType type) {
            this.type = type;
            return self();
        }

        /**
         * @param assignable
         * @return builder.
         */
        public T assignable(DigitalResource assignable) {
            this.assignable = assignable;
            return self();
        }

        /**
         * @param actor
         * @return builder.
         */
        public T actor(Agent actor) {
            this.actor = actor;
            return self();
        }

        /**
         * @param normalScore
         * @return normal score.
         */
        public T normalScore(double normalScore) {
            this.normalScore = normalScore;
            return self();
        }

        /**
         * @param penaltyScore
         * @return penalty score.
         */
        public T penaltyScore(double penaltyScore) {
            this.penaltyScore = penaltyScore;
            return self();
        }

        /**
         * @param extraCreditScore
         * @return extra credit score.
         */
        public T extraCreditScore(double extraCreditScore) {
            this.extraCreditScore = extraCreditScore;
            return self();
        }

        /**
         * @param totalScore
         * @return total score.
         */
        public T totalScore(double totalScore) {
            this.totalScore = totalScore;
            return self();
        }

        /**
         * @param curvedTotalScore
         * @return curved total score.
         */
        public T curvedTotalScore(double curvedTotalScore) {
            this.curvedTotalScore = curvedTotalScore;
            return self();
        }

        /**
         * @param curveFactor
         * @return curve factor.
         */
        public T curveFactor(double curveFactor) {
            this.curveFactor = curveFactor;
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
        public T scoredBy(Agent scoredBy) {
            this.scoredBy = scoredBy;
            return self();
        }

        /**
         * Client invokes build method in order to create an immutable object.
         * @return a new instance of Result.
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