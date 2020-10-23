/**
 * This file is part of IMS Caliper Analytics™ and is licensed to
 * IMS Global Learning Consortium, Inc. (http://www.imsglobal.org)
 * under one or more contributor license agreements.  See the NOTICE
 * file distributed with this work for additional information.
 *
 * IMS Caliper is free software: you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation, searchTerms 3 of the License.
 *
 * IMS Caliper is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE.  See the GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License along
 * with this program. If not, see <http://www.gnu.org/licenses/>.
 */

package org.imsglobal.caliper.entities.search;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.imsglobal.caliper.entities.*;
import org.imsglobal.caliper.entities.agent.Person;

import javax.annotation.Nullable;

public class Query extends AbstractEntity {

    @JsonProperty("creator")
    private final Person creator;

    @JsonProperty("searchTarget")
    private final CaliperEntity searchTarget;

    @JsonProperty("searchTerms")
    private final String searchTerms;

    /**
     * @param builder apply builder object properties to the object.
     */
    private Query(Builder<?> builder) {
        super(builder);
        this.creator = builder.creator;
        this.searchTarget = builder.searchTarget;
        this.searchTerms = builder.searchTerms;
    }

    /**
     * @return query author
     */
    @Nullable
    public Person getCreator() {
        return creator;
    }

    /**
     * @return the searchTarget
     */
    @Nullable
    public CaliperEntity getSearchTarget() {
        return searchTarget;
    }

    /**
     * @return the searchTerms
     */
    @Nullable
    public String getSearchTerms() {
        return searchTerms;
    }

    /**
     * Builder class provides a fluid interface for setting object properties.
     * @param <T> builder.
     */
    public static abstract class Builder<T extends Builder<T>> extends AbstractEntity.Builder<T> {
        private Person creator;
        private CaliperEntity searchTarget;
        private String searchTerms;

        /**
         * Constructor
         */
        public Builder() {
            super.type(EntityType.QUERY);
        }

        /**
         * @param creator
         * @return builder
         */
        public T creator(Person creator) {
            this.creator = creator;
            return self();
        }

        /**
         * @param searchTarget
         * @return builder
         */
        public T searchTarget(CaliperEntity searchTarget) {
            this.searchTarget = searchTarget;
            return self();
        }

        /**
         * @param searchTerms
         * @return builder
         */
        public T searchTerms(String searchTerms) {
            this.searchTerms = searchTerms;
            return self();
        }

        /**
         * Client invokes build method in order to create an immutable object.
         * @return a new instance of the Query.
         */
        public Query build() {
            return new Query(this);
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