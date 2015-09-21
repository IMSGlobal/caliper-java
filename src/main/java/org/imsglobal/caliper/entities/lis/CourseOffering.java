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
import org.imsglobal.caliper.entities.Entity;
import org.imsglobal.caliper.entities.EntityType;
import org.imsglobal.caliper.entities.w3c.Organization;
import org.imsglobal.caliper.validators.EntityValidator;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * A CourseOffering is the occurrence of a course in a specific term, semester, etc.  A Caliper
 * CourseOffering provides a subset of the CourseOffering properties specified in the IMS LTI 2.0
 * specification, which in turn, draws inspiration from the IMS LIS 1.0 specification.
 */
public class CourseOffering extends Entity implements Course {

    @JsonProperty("@type")
    private final EntityType type;

    @JsonProperty("courseNumber")
    private final String courseNumber;

    @JsonProperty("academicSession")
    private final String academicSession;

    @JsonProperty("subOrganizationOf")
    private final Organization subOrganizationOf;

    /**
     * @param builder apply builder object properties to the CourseSection object.
     */
    protected CourseOffering(Builder<?> builder) {
        super(builder);

        EntityValidator.checkType(builder.type, EntityType.COURSE_OFFERING);

        this.type = builder.type;
        this.courseNumber = builder.courseNumber;
        this.academicSession = builder.academicSession;
        this.subOrganizationOf = builder.subOrganizationOf;
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
     * The course number, such as "Biology 101". In general, this number is not simply a numeric value.
     * @return the course number.
     */
    @Nullable
    public String getCourseNumber() {
        return courseNumber;
    }

    /**
     * @return academic session
     */
    @Nullable
    public String getAcademicSession() {
        return academicSession;
    }

    /**
     * @return parent organization
     */
    @Nullable
    public Organization getSubOrganizationOf() {
        return subOrganizationOf;
    }

    /**
     * Builder class provides a fluid interface for setting object properties.
     * @param <T> builder
     */
    public static abstract class Builder<T extends Builder<T>> extends Entity.Builder<T>  {
        private EntityType type;
        private String courseNumber;
        private String academicSession;
        private Organization subOrganizationOf;

        /**
         * Initialize type with default value.
         */
        public Builder() {
            type(EntityType.COURSE_OFFERING);
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
         * @param courseNumber
         * @return builder.
         */
        public T courseNumber(String courseNumber) {
            this.courseNumber = courseNumber;
            return self();
        }

        /**
         * @param academicSession
         * @return builder.
         */
        public T academicSession(String academicSession) {
            this.academicSession = academicSession;
            return self();
        }

        /**
         * @param subOrganizationOf
         * @return builder.
         */
        public T subOrganizationOf(Organization subOrganizationOf) {
            this.subOrganizationOf = subOrganizationOf;
            return self();
        }

        /**
         * Client invokes build method in order to create an immutable object.
         * @return a new instance of the CourseOffering.
         */
        public CourseOffering build() {
            return new CourseOffering(this);
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