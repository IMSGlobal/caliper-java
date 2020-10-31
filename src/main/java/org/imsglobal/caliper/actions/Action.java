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

package org.imsglobal.caliper.actions;

import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Caliper action vocabulary.
 */
public enum Action implements CaliperAction {
    ABANDONED("Abandoned"),
    ACCEPTED("Accepted"),
    ACTIVATED("Activated"),
    ADDED("Added"),
    ARCHIVED("Archived"),
    ATTACHED("Attached"),
    BOOKMARKED("Bookmarked"),
    CHANGED_RESOLUTION("ChangedResolution"),
    CHANGED_SIZE("ChangedSize"),
    CHANGED_SPEED("ChangedSpeed"),
    CHANGED_VOLUME("ChangedVolume"),
    CLASSIFIED("Classified"),
    CLOSED_POPOUT("ClosedPopout"),
    COMMENTED("Commented"),
    COMPLETED("Completed"),
    COPIED("Copied"),
    CREATED("Created"),
    DEACTIVATED("Deactivated"),
    DECLINED("Declined"),
    DELETED("Deleted"),
    DESCRIBED("Described"),
    DISABLED_CLOSED_CAPTIONING("DisabledClosedCaptioning"),
    DISLIKED("Disliked"),
    DOWNLOADED("Downloaded"),
    ENABLED_CLOSED_CAPTIONING("EnabledClosedCaptioning"),
    ENDED("Ended"),
    ENTERED_FULLSCREEN("EnteredFullScreen"),
    EXITED_FULLSCREEN("ExitedFullScreen"),
    FORWARDED_TO("ForwardedTo"),
    GRADED("Graded"),
    HID("Hid"),
    HIGHLIGHTED("Highlighted"),    
    IDENTIFIED("Identified"),
    JUMPED_TO("JumpedTo"),
    LAUNCHED("Launched"),
    LIKED("Liked"),
    LINKED("Linked"),
    LOGGED_IN("LoggedIn"),
    LOGGED_OUT("LoggedOut"),
    MARKED_AS_READ("MarkedAsRead"),
    MARKED_AS_UNREAD("MarkedAsUnread"),
    MODIFIED("Modified"),
    MUTED("Muted"),
    NAVIGATED_TO("NavigatedTo"),
    OPENED_POPOUT("OpenedPopout"),
    OPTED_IN("OptedIn"),
    OPTED_OUT("OptedOut"),
    PAUSED("Paused"),
    POSTED("Posted"),
    PRINTED("Printed"),
    PUBLISHED("Published"),
    QUESTIONED("Questioned"),
    RANKED("Ranked"),
    RECOMMENDED("Recommended"),
    REMOVED("Removed"),
    REPLIED("Replied"),
    RESET("Reset"),
    RESTARTED("Restarted"),
    RESTORED("Restored"),
    RESUMED("Resumed"),
    RETRIEVED("Retrieved"),
    RETURNED("Returned"),
    REVIEWED("Reviewed"),
    REWOUND("Rewound"),
    SAVED("Saved"),
    SEARCHED("Searched"),
    SENT("Sent"),
    SHARED("Shared"),
    SHOWED("Showed"),
    SKIPPED("Skipped"),
    STARTED("Started"),
    SUBMITTED("Submitted"),
    SUBSCRIBED("Subscribed"),
    TAGGED("Tagged"),
    TIMED_OUT("TimedOut"),
    UNMUTED("Unmuted"),
    UNPUBLISHED("Unpublished"),
    UNSUBSCRIBED("Unsubscribed"),
    UPLOADED("Uploaded"),
    USED("Used"),
    VIEWED("Viewed");

    private String value;

    /**
     * Constructor
     * @param value
     */
    private Action(String value){
        this.value = value;
    }

    /**
     * Enum string value.
     * @return string value.
     */
    @Override
    @JsonValue
    public String value() {
        return value;
    }
}
