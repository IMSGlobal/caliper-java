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

package org.imsglobal.caliper.entities.scale;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import org.imsglobal.caliper.entities.AbstractEntity;
import org.imsglobal.caliper.entities.EntityType;

import javax.annotation.Nullable;
import java.util.List;

public class MultiselectScale extends AbstractEntity implements CaliperScale {

    @JsonProperty("isOrderedSelection")
    private final boolean isOrderedSelection;

    @JsonProperty("itemLabels")
    private final ImmutableList<String> itemLabels;

    @JsonProperty("itemValues")
    private final ImmutableList<String> itemValues;

    @JsonProperty("maxSelections")
    private final int maxSelections;

    @JsonProperty("minSelections")
    private final int minSelections;

    @JsonProperty("scalePoints")
    private final int scalePoints;

    /**
     * @param builder apply builder object properties to the object.
     */
    protected MultiselectScale(Builder<?> builder) {
        super(builder);
        this.isOrderedSelection = builder.isOrderedSelection;
        this.itemLabels = ImmutableList.copyOf(builder.itemLabels);
        this.itemValues = ImmutableList.copyOf(builder.itemValues);
        this.maxSelections = builder.maxSelections;
        this.minSelections = builder.minSelections;
        this.scalePoints = builder.scalePoints;
    }

    /**
     * Return whether or not the list of items is ordered.
     * @return boolean value
     */
    @Nullable
    public Boolean getIsOrderedSelection() {
        return isOrderedSelection;
    }

    /**
     * Return an immutable list of item labels.
     * @return the items
     */
    @Nullable
    public ImmutableList<String> getItemLabels() {
        return itemLabels;
    }

    /**
     * Return an immutable list of item values.
     * @return the items
     */
    @Nullable
    public ImmutableList<String> getItemValues() {
        return itemValues;
    }

    /**
     * @return the maximum number of selections allowed.
     */
    @Nullable
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    public int getMaxSelections() {
        return maxSelections;
    }

    /**
     * @return the minimum number of selections allowed.
     */
    @Nullable
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    public int getMinSelections() {
        return minSelections;
    }

    /**
     * @return the scale points
     */
    @Nullable
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    public int getScalePoints() {
        return scalePoints;
    }

    /**
     * Builder class provides a fluid interface for setting object properties.
     * @param <T> builder.
     */
    public static abstract class Builder<T extends Builder<T>> extends AbstractEntity.Builder<T> {
        private boolean isOrderedSelection;
        private List<String> itemLabels = Lists.newArrayList();
        private List<String> itemValues = Lists.newArrayList();
        private int maxSelections;
        private int minSelections;
        private int scalePoints;

        /**
         * Constructor
         */
        public Builder() {
            super.type(EntityType.MULTISELECT_SCALE);
        }

        /**
         * @param isOrderedSelection
         * @return builder.
         */
        public T isOrderedSelection(boolean isOrderedSelection) {
            this.isOrderedSelection = isOrderedSelection;
            return self();
        }

        /**
         * @param itemLabels
         * @return builder.
         */
        public T itemLabels(List<String> itemLabels) {
            if(itemLabels != null) {
                this.itemLabels.addAll(itemLabels);
            }
            return self();
        }

        /**
         * @param itemLabel
         * @return builder.
         */
        public T itemLabel(String itemLabel) {
            this.itemLabels.add(itemLabel);
            return self();
        }

        /**
         * @param itemValues
         * @return builder.
         */
        public T itemValues(List<String> itemValues) {
            if(itemValues != null) {
                this.itemValues.addAll(itemValues);
            }
            return self();
        }

        /**
         * @param itemValue
         * @return builder.
         */
        public T itemValue(String itemValue) {
            this.itemValues.add(itemValue);
            return self();
        }

        /**
         * @param maxSelections
         * @return builder.
         */
        public T maxSelections(int maxSelections) {
            this.maxSelections = maxSelections;
            return self();
        }

        /**
         * @param minSelections
         * @return builder.
         */
        public T minSelections(int minSelections) {
            this.minSelections = minSelections;
            return self();
        }

        /**
         * @param scalePoints
         * @return builder.
         */
        public T scalePoints(int scalePoints) {
            this.scalePoints = scalePoints;
            return self();
        }

        /**
         * Client invokes build method in order to create an immutable object.
         * @return a new instance of the MultiselectScale.
         */
        public MultiselectScale build() {
            return new MultiselectScale(this);
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