/**
 * This file is part of IMS Caliper Analytics™ and is licensed to
 * IMS Global Learning Consortium, Inc. (http://www.imsglobal.org)
 * under one or more contributor license agreements.  See the NOTICE
 * file distributed with this work for additional information.
 *
 * IMS Caliper is free software: you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation, searchResultsItemCount 3 of the License.
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
import org.imsglobal.caliper.entities.agent.SoftwareApplication;

import javax.annotation.Nullable;

public class SearchResponse extends AbstractEntity implements CaliperGeneratable {

    @JsonProperty("searchProvider")
    private final SoftwareApplication searchProvider;

    @JsonProperty("searchTarget")
    private final CaliperEntity searchTarget;

    @JsonProperty("query")
    private final Query query;

    @JsonProperty("searchResultsItemCount")
    private final int searchResultsItemCount;

    /**
     * @param builder apply builder object properties to the object.
     */
    private SearchResponse(Builder<?> builder) {
        super(builder);
        this.searchProvider = builder.searchProvider;
        this.searchTarget = builder.searchTarget;
        this.query = builder.query;
        this.searchResultsItemCount = builder.searchResultsItemCount;
    }

    /**
     * @return query author
     */
    @Nullable
    public SoftwareApplication getSearchProvider() {
        return searchProvider;
    }

    /**
     * @return the searchTarget
     */
    @Nullable
    public CaliperEntity getSearchTarget() {
        return searchTarget;
    }

    /**
     * @return the query
     */
    @Nullable
    public Query getQuery() {
        return query;
    }

    /**
     * @return the searchResultsItemCount
     */
    @Nullable
    public int getSearchResultsItemCount() {
        return searchResultsItemCount;
    }

    /**
     * Builder class provides a fluid interface for setting object properties.
     * @param <T> builder.
     */
    public static abstract class Builder<T extends Builder<T>> extends AbstractEntity.Builder<T> {
        private SoftwareApplication searchProvider;
        private CaliperEntity searchTarget;
        private Query query;
        private int searchResultsItemCount;

        /**
         * Constructor
         */
        public Builder() {
            super.type(EntityType.SEARCHRESPONSE);
        }

        /**
         * @param searchProvider
         * @return builder
         */
        public T searchProvider(SoftwareApplication searchProvider) {
            this.searchProvider = searchProvider;
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
         * @param query
         * @return builder
         */
        public T query(Query query) {
            this.query = query;
            return self();
        }

        /**
         * @param searchResultsItemCount
         * @return builder
         */
        public T searchResultsItemCount(int searchResultsItemCount) {
            this.searchResultsItemCount = searchResultsItemCount;
            return self();
        }

        /**
         * Client invokes build method in order to create an immutable object.
         * @return a new instance of the SearchResponse.
         */
        public SearchResponse build() {
            return new SearchResponse(this);
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