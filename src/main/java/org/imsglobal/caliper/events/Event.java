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

package org.imsglobal.caliper.events;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.imsglobal.caliper.actions.Action;
import org.imsglobal.caliper.validators.EventValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Concrete implementation of a generic Event.
 */
@SupportedActions({
    Action.ABANDONED, Action.ACTIVATED, Action.ADDED, Action.ARCHIVED, Action.ATTACHED, Action.BOOKMARKED,
    Action.CHANGED_RESOLUTION, Action.CHANGED_SIZE, Action.CHANGED_SPEED, Action.CHANGED_VOLUME,
    Action.CLASSIFIED, Action.CLOSED_POPOUT, Action.COMMENTED, Action.COMPLETED, Action.COPIED, Action.CREATED,
    Action.DEACTIVATED, Action.DELETED, Action.DESCRIBED, Action.DISABLED_CLOSED_CAPTIONING, Action.DISLIKED,
    Action.DOWNLOADED, Action.ENABLED_CLOSED_CAPTIONING, Action.ENDED, Action.ENTERED_FULLSCREEN,
    Action.EXITED_FULLSCREEN, Action.FORWARDED_TO, Action.GRADED, Action.HID, Action.HIGHLIGHTED, Action.IDENTIFIED,
    Action.JUMPED_TO, Action.LIKED, Action.LINKED, Action.LOGGED_IN, Action.LOGGED_OUT, Action.MARKED_AS_READ,
    Action.MARKED_AS_UNREAD, Action.MODIFIED, Action.MUTED, Action.NAVIGATED_TO, Action.OPENED_POPOUT,
    Action.PAUSED, Action.POSTED, Action.PRINTED, Action.PUBLISHED, Action.QUESTIONED, Action.RANKED, Action.RECOMMENDED,
    Action.REPLIED, Action.RESET, Action.RESTARTED, Action.RESTORED, Action.RESUMED, Action.RETRIEVED, Action.REVIEWED,
    Action.REWOUND, Action.SAVED, Action.SEARCHED, Action.SHARED, Action.SHOWED, Action.SKIPPED, Action.STARTED,
    Action.SUBMITTED, Action.SUBSCRIBED, Action.TAGGED, Action.TIMED_OUT, Action.UNMUTED, Action.UNPUBLISHED,
    Action.UNSUBSCRIBED, Action.UPLOADED, Action.USED, Action.VIEWED
})
public class Event extends AbstractEvent {

    @JsonIgnore
    private static final Logger log = LoggerFactory.getLogger(Event.class);

    /**
     * Utilize builder to construct BasicEvent.  Validate View object copy rather than the
     * View builder.  This approach protects the class against parameter changes from another
     * thread during the "window of vulnerability" between the time the parameters are checked
     * until when they are copied.
     *
     * @param builder
     */
    private Event(Builder<?> builder) {
        super(builder);
    }

    /**
     * Initialize default parameter values in the builder.
     * @param <T> builder
     */
    public static abstract class Builder<T extends Builder<T>> extends AbstractEvent.Builder<T>  {

        /*
         * Constructor
         */
        public Builder() {
            super.type(EventType.EVENT);
        }

        /**
         * Client invokes build method in order to create an immutable profile object.
         * @return a new BasicEvent instance.
         */
        public Event build() {
            return new Event(this);
        }
    }

    /**
     * Self-reference that permits sub-classing of builder.
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