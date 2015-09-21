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

import com.fasterxml.jackson.annotation.*;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import org.imsglobal.caliper.entities.schemadotorg.CreativeWork;
import org.imsglobal.caliper.validators.EntityValidator;
import org.joda.time.DateTime;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

/**
 * Caliper representation of a CreativeWork (https://schema.org/CreativeWork).
 */
public class DigitalResource extends Entity implements org.imsglobal.caliper.entities.schemadotorg.CreativeWork,
                                                       org.imsglobal.caliper.entities.Targetable {

    @JsonProperty("@type")
    private final Type type;

    @JsonProperty("objectType")
    private final ImmutableList<String> objectTypes;

    @JsonProperty("alignedLearningObjective")
    private final ImmutableList<LearningObjective> learningObjectives;

    @JsonProperty("keywords")
    private final ImmutableList<String> keywords;

    @JsonProperty("isPartOf")
    private final CreativeWork isPartOf;

    @JsonProperty("datePublished")
    private final DateTime datePublished;

    @JsonProperty("version")
    private final String version;

    /**
     * @param builder apply builder object properties to the profile object.
     */
    protected DigitalResource(Builder<?> builder) {
        super(builder);

        EntityValidator.checkType(builder.type, EntityType.DIGITAL_RESOURCE);

        this.type = builder.type;
        this.objectTypes = ImmutableList.copyOf(builder.objectTypes);
        this.learningObjectives = ImmutableList.copyOf(builder.learningObjectives);
        this.keywords = ImmutableList.copyOf(builder.keywords);
        this.isPartOf = builder.isPartOf;
        this.datePublished = builder.datePublished;
        this.version = builder.version;
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
     * Return an immutable view of the objectTypes list.
     * @return the objectTypes
     */
    @Nullable
    public ImmutableList<String> getObjectTypes() {
        return objectTypes;
    }

    /**
     * Return an immutable view of the learningObjectives list.
     * @return the learning objectives
     */
    @Nullable
    public ImmutableList<LearningObjective> getLearningObjectives() {
        return learningObjectives;
    }

    /**
     * Return an immutable view of the keywords list.
     * @return the keywords
     */
    @Nullable
    public ImmutableList<String> getKeywords() {
        return keywords;
    }

    /**
     * Serialization of the DigitalResource parent is limited to the identifying URI only.
     * @return the parent reference.
     */
    @Nullable
    public CreativeWork getIsPartOf() {
        return isPartOf;
    }

    /**
     * @return date published
     */
    @Nullable
    public DateTime getDatePublished() {
        return datePublished;
    }

    /**
     * @return the version
     */
    @Nullable
    public String getVersion() {
        return version;
    }

    /**
     * Initialize default parameter values in the builder.
     * @param <T> builder
     */
    public static abstract class Builder<T extends Builder<T>> extends Entity.Builder<T>  {
        private EntityType type;
        private List<String> objectTypes = Lists.newArrayList();
        private List<LearningObjective> learningObjectives = Lists.newArrayList();
        private List<String> keywords = Lists.newArrayList();
        private CreativeWork isPartOf;
        private DateTime datePublished;
        private String version;

        /*
         * Constructor
         */
        public Builder() {
            type(EntityType.DIGITAL_RESOURCE);
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
         * @param objectTypes
         * @return builder.
         */
        public T objectTypes(List<String> objectTypes) {
            this.objectTypes = objectTypes;
            return self();
        }

        /**
         * @param objectType
         * @return builder.
         */
        public T objectType(String objectType) {
            this.objectTypes.add(objectType);
            return self();
        }

        /**
         * @param learningObjectives
         * @return builder.
         */
        public T learningObjectives(List<LearningObjective> learningObjectives) {
            this.learningObjectives = learningObjectives;
            return self();
        }

        /**
         * @param learningObjective
         * @return builder.
         */
        public T learningObjective(LearningObjective learningObjective) {
            this.learningObjectives.add(learningObjective);
            return self();
        }

        /**
         * @param keywords
         * @return builder.
         */
        public T keywords(List<String> keywords) {
            this.keywords = keywords;
            return self();
        }

        /**
         * @param keyword
         * @return builder.
         */
        public T keyword(String keyword) {
            this.keywords.add(keyword);
            return self();
        }

        /**
         * @param isPartOf
         * @return builder.
         */
        public T isPartOf(CreativeWork isPartOf) {
            this.isPartOf = isPartOf;
            return self();
        }

        /**
         * @param datePublished
         * @return builder.
         */
        public T datePublished(DateTime datePublished) {
            this.datePublished = datePublished;
            return self();
        }

        /**
         * @param version
         * @return builder.
         */
        public T version(String version) {
            this.version = version;
            return self();
        }

        /**
         * Client invokes build method in order to create an immutable profile object.
         * @return a new instance of the AssessmentProfile.
         */
        public DigitalResource build() {
            return new DigitalResource(this);
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