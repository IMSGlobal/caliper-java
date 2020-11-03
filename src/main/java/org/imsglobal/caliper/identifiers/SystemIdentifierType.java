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

package org.imsglobal.caliper.identifiers;

import com.fasterxml.jackson.annotation.JsonValue;

public enum SystemIdentifierType {
    ACCOUNT_USER_NAME("AccountUserName"),
    CASE_ITEM_URI("CaseItemUri"),
    EMAIL_ADDRESS("EmailAddress"),
    LIS_SOURCED_ID("LisSourcedId"),
    LTI_CONTEXT_ID("LtiContextId"),
    LTI_DEPLOYMENT_ID("LtiDeploymentId"),
    LTI_PLATFORM_ID("LtiPlatformId"),
    LTI_TOOL_ID("LtiToolId"),
    LTI_USER_ID("LtiUserId"),
    ONE_ROSTER_SOURCED_ID("OneRosterSourcedId"),
    OTHER("Other"),
    SIS_SOURCED_ID("SisSourcedId"),
    SYSTEM_ID("SystemId");

    private final String value;

    /**
     * Private constructor
     * @param value
     */
    private SystemIdentifierType(final String value) {
        this.value = value;
    }

    /**
     * @return URI string
     */
    @JsonValue
    public String value() {
        return value;
    }
}