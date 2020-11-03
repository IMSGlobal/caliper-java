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

package org.imsglobal.caliper.entities.agent;

import com.fasterxml.jackson.annotation.JsonValue;
import com.google.common.collect.ImmutableMap;

import java.util.HashMap;
import java.util.Map;

/**
 * IMS LTI/LIS roles.
 */
public enum Role {
	ADMINISTRATOR("Administrator"),
    ADMINISTRATOR_ADMINISTRATOR("Administrator#Administrator"),
    ADMINISTRATOR_DEVELOPER("Administrator#Developer"),
    ADMINISTRATOR_EXTERNAL_DEVELOPER("Administrator#ExternalDeveloper"),
    ADMINISTRATOR_EXTERNAL_SUPPORT("Administrator#ExternalSupport"),
    ADMINISTRATOR_EXTERNAL_SYSTEM_ADMINISTRATOR("Administrator#ExternalSystemAdministrator"),
    ADMINISTRATOR_SUPPORT("Administrator#Support"),
    ADMINISTRATOR_SYSTEM_ADMINISTRATOR("Administrator#SystemAdministrator"),

    CONTENT_DEVELOPER("ContentDeveloper"),
    CONTENT_DEVELOPER_CONTENT_DEVELOPER("ContentDeveloper#ContentDeveloper"),
    CONTENT_DEVELOPER_CONTENT_EXPERT("ContentDeveloper#ContentExpert"),
    CONTENT_DEVELOPER_EXTERNAL_CONTENT_EXPERT("ContentDeveloper#ExternalContentExpert"),
    CONTENT_DEVELOPER_LIBRARIAN("ContentDeveloper#Librarian"),

    INSTRUCTOR("Instructor"),
    INSTRUCTOR_EXTERNAL_INSTRUCTOR("Instructor#ExternalInstructor"),
    INSTRUCTOR_GRADER("Instructor#Grader"),
    INSTRUCTOR_GUEST_INSTRUCTOR("Instructor#GuestInstructor"),
    INSTRUCTOR_INSTRUCTOR("Instructor#Instructor"),
    INSTRUCTOR_LECTURER("Instructor#Lecturer"),
    INSTRUCTOR_PRIMARY_INSTRUCTOR("Instructor#PrimaryInstructor"),
    INSTRUCTOR_SECONDARY_INSTRUCTOR("Instructor#SecondaryInstructor"),
    INSTRUCTOR_TEACHING_ASSISTANT("Instructor#TeachingAssistant"),
    INSTRUCTOR_TEACHING_ASSISTANT_GROUP("Instructor#TeachingAssistantGroup"),
    INSTRUCTOR_TEACHING_ASSISTANT_SECTION("Instructor#TeachingAssistantSection"),
    INSTRUCTOR_TEACHING_ASSISTANT_OFFERING("Instructor#TeachingAssistantOffering"),
    INSTRUCTOR_TEACHING_ASSISTANT_TEMPLATE("Instructor#TeachingAssistantTemplate"),
    
    LEARNER("Learner"),
    LEARNER_EXTERNAL_LEARNER("Learner#ExternalLearner"),
    LEARNER_GUEST_LEARNER("Learner#GuestLearner"),
    LEARNER_NONCREDIT_LEARNER("Learner#NonCreditLearner"),
    LEARNER_LEARNER("Learner#Learner"),
    
    MANAGER("Manager"),
    MANAGER_AREA_MANAGER("Manager#AreaManager"),
    MANAGER_COURSE_COORDINATOR("Manager#CourseCoordinator"),
    MANAGER_EXTERNAL_OBSERVER("Manager#ExternalObserver"),
    MANAGER_MANAGER("Manager#Manager"),
    MANAGER_OBSERVER("Manager#Observer"),
    
    MEMBER("Member"),
    MEMBER_MEMBER("Member#Member"),

    MENTOR("Mentor"),
    MENTOR_ADVISOR("Mentor#Advisor"),
    MENTOR_EXTERNAL_ADVISOR("Mentor#ExternalAdvisor"),
    MENTOR_EXTERNAL_AUDITOR("Mentor#ExternalAuditor"),
    MENTOR_EXTERNAL_LEARNING_FACILITATOR("Mentor#ExternalLearningFacilitator"),
    MENTOR_EXTERNAL_MENTOR("Mentor#ExternalMentor"),
    MENTOR_EXTERNAL_REVIEWER("Mentor#ExternalReviewer"),
    MENTOR_EXTERNAL_TUTOR("Mentor#ExternalTutor"),    
    MENTOR_LEARNING_FACILITATOR("Mentor#LearningFacilitator"),    
    MENTOR_MENTOR("Mentor#Mentor"),
    MENTOR_REVIEWER("Mentor#Reviewer"),    
    MENTOR_TUTOR("Mentor#Tutor"),

    OFFICER("Officer"),
    OFFICER_CHAIR("Officer#Chair"),
    OFFICER_SECRETARY("Officer#Secretary"),
    OFFICER_TREASURER("Officer#Treasurer"),
    OFFICER_VICECHAIR("Officer#Vice-Chair");	
    		
	/* 1.1 values: 
	TEACHING_ASSISTANT("TeachingAssistant"),
    TEACHING_ASSISTANT_TEACHING_ASSISTANT("TeachingAssistant#TeachingAssistant"),
    TEACHING_ASSISTANT_GRADER("TeachingAssistant#Grader"),
    TEACHING_ASSISTANT_TEACHING_ASSISTANT_SECTION("TeachingAssistant#TeachingAssistantSection"),
    TEACHING_ASSISTANT_TEACHING_ASSISTANT_SECTION_ASSOCIATION("TeachingAssistant#TeachingAssistantSectionAssociation"),
    TEACHING_ASSISTANT_TEACHING_ASSISTANT_OFFERING("TeachingAssistant#TeachingAssistantOffering"),
    TEACHING_ASSISTANT_TEACHING_ASSISTANT_TEMPLATE("TeachingAssistant#TeachingAssistantTemplate"),
    TEACHING_ASSISTANT_TEACHING_ASSISTANT_GROUP("TeachingAssistant#TeachingAssistantGroup");
	*/
	
    private final String value;
    private static Map<String, Role> lookup;

    /**
     * Create reverse lookup hash map
     */
    static {
        Map<String, Role> map = new HashMap<String, Role>();
        for (Role constants : Role.values()) {
            map.put(constants.value(), constants);
        }
        lookup = ImmutableMap.copyOf(map);
    }

    /**
     * Private constructor
     * @param value
     */
    private Role(final String value) {
        this.value = value;
    }

    /**
     * @param key
     * @return true if lookup returns a key match; false otherwise.
     */
    public static boolean hasKey(String key) {
        return lookup.containsKey(key);
    }

    /**
     * @param value
     * @return a Role if one exists with the supplied value, else null.
     */
    public static Role fromValue(String value) {
    	return lookup.get(value);
    }
    
    /**
     * @return the URI value
     */
    @JsonValue
    public String value() {
        return value;
    }
}