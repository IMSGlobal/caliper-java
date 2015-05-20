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

package org.imsglobal.caliper.entities;

import com.fasterxml.jackson.annotation.JsonValue;

public enum EntityType implements Type {
    AGENT("http://purl.imsglobal.org/caliper/v1/Agent"),
    ANNOTATION("http://purl.imsglobal.org/caliper/v1/Annotation"),
    ATTEMPT("http://purl.imsglobal.org/caliper/v1/Attempt"),
    COURSE_OFFERING("http://purl.imsglobal.org/caliper/v1/lis/CourseOffering"),
    COURSE_SECTION("http://purl.imsglobal.org/caliper/v1/lis/CourseSection"),
    DIGITAL_RESOURCE("http://purl.imsglobal.org/caliper/v1/DigitalResource"),
    ENTITY("http://purl.imsglobal.org/caliper/v1/Entity"),
    FEDERATED_SESSION("http://purl.imsglobal.org/caliper/v1/lti/FederatedSession"),
    GENERATED("http://purl.imsglobal.org/caliper/v1/Generated"),
    GROUP("http://purl.imsglobal.org/caliper/v1/lis/Group"),
    LEARNING_OBJECTIVE("http://purl.imsglobal.org/caliper/v1/LearningObjective"),
    MEDIA_OBJECT("http://purl.imsglobal.org/caliper/v1/MediaObject"),
    MEMBERSHIP("http://purl.imsglobal.org/caliper/v1/lis/Membership"),
    PERSON("http://purl.imsglobal.org/caliper/v1/lis/Person"),
    ORGANIZATION("http://purl.imsglobal.org/caliper/v1/w3c/Organization"),
    RESPONSE("http://purl.imsglobal.org/caliper/v1/Response"),
    RESULT("http://purl.imsglobal.org/caliper/v1/Result"),
    SESSION("http://purl.imsglobal.org/caliper/v1/Session"),
    SOFTWARE_APPLICATION("http://purl.imsglobal.org/caliper/v1/SoftwareApplication"),
    TARGET("http://purl.imsglobal.org/caliper/v1/Target"),
    VIEW("http://purl.imsglobal.org/caliper/v1/View");

    private final String value;

    /**
     * Private constructor
     * @param value
     */
    private EntityType(final String value) {
        this.value = value;
    }

    /**
     * @return URI string
     */
    @JsonValue
    public String getValue() {
        return value;
    }
}