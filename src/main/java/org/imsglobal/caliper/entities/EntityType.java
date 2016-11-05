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
    ANNOTATION("http://purl.imsglobal.org/caliper/v1/Annotation"),
    ASSESSMENT("http://purl.imsglobal.org/caliper/v1/Assessment"),
    ASSESSMENT_ITEM("http://purl.imsglobal.org/caliper/v1/AssessmentItem"),
    ASSIGNABLE_DIGITAL_RESOURCE("http://purl.imsglobal.org/caliper/v1/AssignableDigitalResource"),
    ATTEMPT("http://purl.imsglobal.org/caliper/v1/Attempt"),
    AUDIO_OBJECT("http://purl.imsglobal.org/caliper/v1/AudioObject"),
    BOOKMARK_ANNOTATION("http://purl.imsglobal.org/caliper/v1/BookmarkAnnotation"),
    CHAPTER("http://purl.imsglobal.org/caliper/v1/Chapter"),
    COURSE_OFFERING("http://purl.imsglobal.org/caliper/v1/CourseOffering"),
    COURSE_SECTION("http://purl.imsglobal.org/caliper/v1/CourseSection"),
    DIGITAL_RESOURCE("http://purl.imsglobal.org/caliper/v1/DigitalResource"),
    DOCUMENT("http://purl.imsglobal.org/caliper/v1/Document"),
    ENTITY("http://purl.imsglobal.org/caliper/v1/Entity"),
    EPUB_CHAPTER("http://purl.imsglobal.org/caliper/v1/EpubChapter"),
    EPUB_PART("http://purl.imsglobal.org/caliper/v1/EpubPart"),
    EPUB_SUB_CHAPTER("http://purl.imsglobal.org/caliper/v1/EpubSubChapter"),
    EPUB_VOLUME("http://purl.imsglobal.org/caliper/v1/EpubVolume"),
    FILLINBLANK("http://purl.imsglobal.org/caliper/v1/FillinBlankResponse"),
    FRAME("http://purl.imsglobal.org/caliper/v1/Frame"),
    GROUP("http://purl.imsglobal.org/caliper/v1/Group"),
    HIGHLIGHT_ANNOTATION("http://purl.imsglobal.org/caliper/v1/HighlightAnnotation"),
    IMAGE_OBJECT("http://purl.imsglobal.org/caliper/v1/ImageObject"),
    LEARNING_OBJECTIVE("http://purl.imsglobal.org/caliper/v1/LearningObjective"),
    MEDIA_LOCATION("http://purl.imsglobal.org/caliper/v1/MediaLocation"),
    MEDIA_OBJECT("http://purl.imsglobal.org/caliper/v1/MediaObject"),
    MEMBERSHIP("http://purl.imsglobal.org/caliper/v1/Membership"),
    MULTIPLECHOICE("http://purl.imsglobal.org/caliper/v1/MultipleChoiceResponse"),
    MULTIPLERESPONSE("http://purl.imsglobal.org/caliper/v1/MultipleResponseResponse"),
    PAGE("http://purl.imsglobal.org/caliper/v1/Page"),
    PERSON("http://purl.imsglobal.org/caliper/v1/Person"),
    ORGANIZATION("http://purl.imsglobal.org/caliper/v1/Organization"),
    READING("http://purl.imsglobal.org/caliper/v1/Reading"),
    RESPONSE("http://purl.imsglobal.org/caliper/v1/Response"),
    RESULT("http://purl.imsglobal.org/caliper/v1/Result"),
    SELECTTEXT("http://purl.imsglobal.org/caliper/v1/SelectTextResponse"),
    SESSION("http://purl.imsglobal.org/caliper/v1/Session"),
    SHARED_ANNOTATION("http://purl.imsglobal.org/caliper/v1/SharedAnnotation"),
    SOFTWARE_APPLICATION("http://purl.imsglobal.org/caliper/v1/SoftwareApplication"),
    TAG_ANNOTATION("http://purl.imsglobal.org/caliper/v1/TagAnnotation"),
    TRUEFALSE("http://purl.imsglobal.org/caliper/v1/TrueFalseResponse"),
    VIDEO_OBJECT("http://purl.imsglobal.org/caliper/v1/VideoObject"),
    WEB_PAGE("http://purl.imsglobal.org/caliper/v1/WebPage");

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