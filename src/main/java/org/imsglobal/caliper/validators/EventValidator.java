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

package org.imsglobal.caliper.validators;

import org.imsglobal.caliper.actions.Action;
import org.imsglobal.caliper.context.Context;
import org.imsglobal.caliper.entities.Generatable;
import org.imsglobal.caliper.entities.Targetable;
import org.imsglobal.caliper.entities.foaf.Agent;
import org.imsglobal.caliper.events.Event;
import org.imsglobal.caliper.events.EventType;
import org.imsglobal.caliper.events.SupportedActions;

import static com.google.common.base.Preconditions.checkArgument;

public class EventValidator {

    /**
     * Constructor
     */
    public EventValidator() {

    }

    /**
     * Check Event context.
     * @param context
     * @param expected
     * @throws IllegalArgumentException
     */
    public static void checkContext(Context context, Context expected) throws IllegalArgumentException {
        checkArgument(context == expected, "expected @context %s but was %s", expected.getValue(), context.getValue());
    }

    /**
     * Check Event type.
     * @param type
     * @param expected
     * @throws IllegalArgumentException
     */
    public static void checkType(EventType type, EventType expected) throws IllegalArgumentException {
        checkArgument(type == expected, "expected @type %s but was %s", expected.getValue(), type.getValue());
    }

    /**
     * Check actor type
     * @param actor
     * @param type
     * @throws IllegalArgumentException
     */
    public static void checkActorType(Agent actor, Class<?> type) throws IllegalArgumentException {
        TypeValidator.checkActorType(actor, type);
    }

    /**
     * Check action
     * @param action
     * @throws IllegalArgumentException
     */
    public static void checkAction(Action action, Class<? extends Event> clazz) throws IllegalArgumentException {
        checkArgument(action != null, "an action must be specified");

        SupportedActions actions = clazz.getAnnotation(SupportedActions.class);
        checkArgument(actions != null, "supported actions must be specified");

        boolean isSupported = false;
        for (Action supportedAction : actions.value()) {
            if (supportedAction.equals(action)) {
                isSupported = true;
                break;
            }
        }
        checkArgument(isSupported, "%s action is not supported", action.getValue());
    }

    /**
     * Check generated type.
     * @param generated
     * @param type
     * @return Validation result
     */
    public static void checkGeneratedType(Generatable generated, Class<?> type) throws IllegalArgumentException {
        TypeValidator.checkGeneratedType(generated, type);
    }

    /**
     * Check target type.
     * @param target
     * @param type
     * @return Validation result
     */
    public static void checkTargetType(Targetable target, Class<?> type) throws IllegalArgumentException {
        TypeValidator.checkTargetType(target, type);
    }

    /**
     * Check object type.
     * @param object
     * @param type
     * @return Validation result
     */
    public static void checkObjectType(Object object, Class<?> type) throws IllegalArgumentException {
        TypeValidator.checkObjectType(object, type);
    }
}