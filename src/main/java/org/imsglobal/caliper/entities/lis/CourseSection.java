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

package org.imsglobal.caliper.entities.lis;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.imsglobal.caliper.entities.EntityType;
import org.imsglobal.caliper.validators.EntityValidator;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * A CourseSection is a way to represent a group of people associated with a course or class.
 * These groups may include everyone in the class or course, or may be subsets of that whole group.
 * CourseSections may have sub-sections (these are created as separate Group objects linked using
 * the relationship). Examples of a CourseSection are Lecture, Laboratory, Studio, Seminar, etc.
 * There may be several instances of a type of CourseSection e.g., multiple lectures.
 *
 * A Caliper CourseSection provides a subset of the CourseSection properties specified in the
 * IMS LTI 2.0 specification, which in turn, draws inspiration from the IMS LIS 1.0 specification.
 */
public class CourseSection extends CourseOffering {

    @JsonProperty("@type")
    private final EntityType type;

    @JsonProperty("category")
    private final String category;

    /**
     * @param builder apply builder object properties to the CourseSection object.
     */
    protected CourseSection(Builder<?> builder) {
        super(builder);

        EntityValidator.checkType(builder.type, EntityType.COURSE_SECTION);

        this.type = builder.type;
        this.category = builder.category;
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
     * @return the category descriptor of the course section (e.g., Lecture, laboratory, etc.).
     */
    @Nullable
    public String getCategory() {
        return category;
    }

    /**
     * Builder class provides a fluid interface for setting object properties.
     * @param <T> builder
     */
    public static abstract class Builder<T extends Builder<T>> extends CourseOffering.Builder<T>  {
        private EntityType type;
        private String category;

        /**
         * Initialize type with default value.
         */
        public Builder() {
            type(EntityType.COURSE_SECTION);
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
         * @param category
         * @return builder.
         */
        public T category(String category) {
            this.category = category;
            return self();
        }

        /**
         * Client invokes build method in order to create an immutable object.
         * @return a new instance of the CourseSection.
         */
        public CourseSection build() {
            return new CourseSection(this);
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