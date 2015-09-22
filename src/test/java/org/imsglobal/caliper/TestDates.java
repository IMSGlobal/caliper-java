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

package org.imsglobal.caliper;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.Period;
import org.joda.time.format.ISOPeriodFormat;

/**
 * Test dates used in construction of Event tests.
 */
public class TestDates {

    /**
     * Constructor
     */
    public TestDates() {

    }

    /**
     * August 1, 2015, 06:00:00.000 GMT
     * @return return date created
     */
    public static DateTime getDefaultDateCreated() {
        return new DateTime(2015, 8, 1, 6, 0, 0, 0, DateTimeZone.UTC);
    }

    /**
     * September 2, 2015, 11:30:00.000 GMT
     * @return return date modified
     */
    public static DateTime getDefaultDateModified() {
        return new DateTime(2015, 9, 2, 11, 30, 0, 0, DateTimeZone.UTC);
    }

    /**
     * August 15, 2015, 09:30:00.000 GMT
     * @return return date published
     */
    public static DateTime getDefaultDatePublished(){
        return new DateTime(2015, 8, 15, 9, 30, 0, 0, DateTimeZone.UTC);
    }

    /**
     * September 16, 2015, 05:00:00.000 GMT
     * @return date to activate
     */
    public static DateTime getDefaultDateToActivate(){
        return new DateTime(2015, 8, 16, 5, 0, 0, 0, DateTimeZone.UTC);
    }

    /**
     * Same date as activate date
     * @return date to show
     */
    public static DateTime getDefaultDateToShow() {
        return getDefaultDateToActivate();
    }

    /**
     * Same date as activate date
     * @return date to start on
     */
    public static DateTime getDefaultDateToStartOn() {
        return getDefaultDateToActivate();
    }

    /**
     * August 28, 2015, 11:59:59.000 GMT
     * @return date to submit
     */
    public static DateTime getDefaultDateToSubmit() {
        return new DateTime(2015, 9, 28, 11, 59, 59, 0, DateTimeZone.UTC);
    }

    /**
     * September 15, 2015, 10:15:00.000 GMT
     * @return event time
     */
    public static DateTime getDefaultEventTime() {
        return new DateTime(2015, 9, 15, 10, 15, 0, 0, DateTimeZone.UTC);
    }

    /**
     * September 15, 2015, 10:15:00.000 GMT
     * @return started at time
     */
    public static DateTime getDefaultStartedAtTime() {
        return new DateTime(2015, 9, 15, 10, 15, 0, 0, DateTimeZone.UTC);
    }

    /**
     * September 15, 2015, 11:05:00.000 GMT
     * @return ended at time
     */
    public static DateTime getDefaultEndedAtTime() {
        return new DateTime(2015, 9, 15, 11, 05, 0, 0, DateTimeZone.UTC);
    }


    /**
     * September 15 2015, 10:15:11:05:01.000 GMT
     * @return
     */
    public static DateTime getDefaultSendTime() {
        return new DateTime(2015, 9, 15, 11, 05, 1, 0, DateTimeZone.UTC);
    }

    /**
     * PT3000s
     * @return period
     */
    public static String getDefaultPeriod() {
        return new Period(0, 0, 3000, 0).toString(ISOPeriodFormat.standard());
    }
}
