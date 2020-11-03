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

import org.imsglobal.caliper.databind.JxnCoercibleSimpleModule;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.fasterxml.jackson.databind.util.StdDateFormat;
import com.fasterxml.jackson.datatype.joda.JodaModule;

public class TestUtils {

    /**
     * Create Caliper-friendly ObjectMapper.
     * @return ObjectMapper
     */
    public static ObjectMapper createCaliperObjectMapper() {
        SimpleFilterProvider provider = new SimpleFilterProvider()
            .setFailOnUnknownId(true);

        ObjectMapper mapper = new ObjectMapper()
            .setDateFormat(new StdDateFormat())
            .setSerializationInclusion(JsonInclude.Include.NON_EMPTY)
            .setFilterProvider(provider)
            .registerModules(new JodaModule(), new JxnCoercibleSimpleModule());

        return mapper;
    }
}