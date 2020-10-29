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

package org.imsglobal.caliper.config;

import org.imsglobal.caliper.validators.SensorValidator;

/**
 * Caliper Sensor config settings.  Review default constants and update placeholder entries as required.
 */
public class Config {
    private final DataFormat dataFormat;
    private final String dataVersion;
    private final int uuidVersion;

    /**
     * Default data format.
     */
    public static final DataFormat DATA_FORMAT = DataFormat.CALIPER_JSONLD;

    /**
     * Default version
     */
    public static final String DATA_VERSION = "http://purl.imsglobal.org/ctx/caliper/v1p2";

    /**
     * Default UUID version to use when minting Event UUIDs.
     */
    public static final int UUID_VERSION = 4;

    /**
     * Constructor
     * @param builder
     */
    private Config(ConfigBuilder builder) {

        this.dataFormat = (builder.dataFormat != null) ? builder.dataFormat : DATA_FORMAT;
        this.dataVersion = SensorValidator.chkStrValue(builder.dataVersion, DATA_VERSION);
        this.uuidVersion = SensorValidator.chkIntValue(builder.uuidVersion, UUID_VERSION);
    }

    /**
     * Get the data format.
     * @return data format
     */
    public DataFormat getDataFormat() {
        return dataFormat;
    }

    /**
     * Get the data version.
     * @return data version
     */
    public String getDataVersion() {
        return dataVersion;
    }

    /**
     * Get the UUID version to be used when minting Event UUIDs.
     * @return UUID version
     */
    private int getUuidVersion() {
        return uuidVersion;
    }

    /**
     * Builder class provides a fluid interface for config settings.
     */
    public static class ConfigBuilder {
        private DataFormat dataFormat;
        private String dataVersion;
        private int uuidVersion = 4;

        /**
         * Constructor
         */
        public ConfigBuilder() {

        }

        /**
         * @param dataFormat
         * @return builder
         */
        public ConfigBuilder dataFormat(final DataFormat dataFormat) {
            this.dataFormat = dataFormat;
            return this;
        }

        /**
         * @param dataVersion
         * @return builder
         */
        public ConfigBuilder dataVersion(final String dataVersion) {
            this.dataVersion = dataVersion;
            return this;
        }

        /**
         * @param uuidVersion
         * @return builder
         */
        public ConfigBuilder uuidVersion(final int uuidVersion) {
            this.uuidVersion = uuidVersion;
            return this;
        }

        /**
         * Client invokes build method in order to create an immutable object.
         * @return a new instance of Config.
         */
        public Config build() {
            return new Config(this);
        }
    }

    /**
     * Static Factory method.
     * @return new builder instance
     */
    public static ConfigBuilder builder() {
        return new ConfigBuilder();
    }
}