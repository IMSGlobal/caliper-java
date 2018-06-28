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
import org.joda.time.DateTime;

import javax.annotation.Nullable;

/**
 * Reusable, composable object for decorating an AssignableDigitalResource with assignment-related properties.
 */
public class Assignment {

    @JsonProperty("dateToActivate")
    private DateTime dateToActivate;

    @JsonProperty("dateToShow")
    private DateTime dateToShow;

    @JsonProperty("dateToStartOn")
    private DateTime dateToStartOn;

    @JsonProperty("dateToSubmit")
    private DateTime dateToSubmit;

    @JsonProperty("maxAttempts")
    private int maxAttempts;

    @JsonProperty("maxSubmits")
    private int maxSubmits;

    @JsonProperty("maxScore")
    private double maxScore;

    /**
     * Constructor
     */
    public Assignment() {

    }

    /**
     * @return the dateToActivate
     */
    @Nullable
    public DateTime getDateToActivate() {
        return dateToActivate;
    }

    /**
     * Set the activation date for this assignment.
     * @param dateToActivate
     */
    public void setDateToActivate(DateTime dateToActivate) {
        this.dateToActivate = dateToActivate;
    }

    /**
     * @return the dateToShow
     */
    @Nullable
    public DateTime getDateToShow() {
        return dateToShow;
    }

    /**
     * Set show date for this assignment
     * @param dateToShow
     */
    public void setDateToShow(DateTime dateToShow) {
        this.dateToShow = dateToShow;
    }

    /**
     * @return the dateToStartOn
     */
    @Nullable
    public DateTime getDateToStartOn() {
        return dateToStartOn;
    }

    /**
     * Set the start date for this assignment.
     * @param dateToStartOn
     */
    public void setDateToStartOn(DateTime dateToStartOn) {
        this.dateToStartOn = dateToStartOn;
    }

    /**
     * @return the dateToSubmit
     */
    @Nullable
    public DateTime getDateToSubmit() {
        return dateToSubmit;
    }

    /**
     * Set the submission date for this assignment
     * @param dateToSubmit
     */
    public void setDateToSubmit(DateTime dateToSubmit) {
        this.dateToSubmit = dateToSubmit;
    }

    /**
     * @return the maxAttempts
     */
    @Nullable
    public int getMaxAttempts() {
        return maxAttempts;
    }

    /**
     * Set the maximum attempts permitted for this assignment.
     * @param maxAttempts
     */
    public void setMaxAttempts(int maxAttempts) {
        this.maxAttempts = maxAttempts;
    }

    /**
     * @return the maxSubmits
     */
    @Nullable
    public int getMaxSubmits() {
        return maxSubmits;
    }

    /**
     * Set the maximum number of submissions permitted for this assignment.
     * @param maxSubmits
     */
    public void setMaxSubmits(int maxSubmits) {
        this.maxSubmits = maxSubmits;
    }

    /**
     * @return the maxScore
     */
    @Nullable
    // @JsonSerialize(using=DoubleSerializer.class)
    public double getMaxScore() {
        return maxScore;
    }

    /**
     * Set the maximum score permitted for this assignment.
     * @param maxScore
     */
    public void setMaxScore(double maxScore) {
        this.maxScore = maxScore;
    }
}