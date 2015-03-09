package org.imsglobal.caliper.validators.events;

import org.imsglobal.caliper.entities.Generatable;
import org.imsglobal.caliper.entities.assessment.AssessmentItem;
import org.imsglobal.caliper.entities.assignable.Attempt;
import org.imsglobal.caliper.events.AssessmentItemEvent;
import org.imsglobal.caliper.profiles.AssessmentItemProfile;
import org.imsglobal.caliper.response.Response;
import org.imsglobal.caliper.validators.ValidatorResult;

import javax.annotation.Nonnull;

public class AssessmentItemEventValidator extends EventValidator<AssessmentItemEvent> {

    /**
     * Constructor
     */
     private AssessmentItemEventValidator(String actionKey) {
        super(actionKey);
     }

    /**
     * Static factory method that sets the action key for validator comparison checks.
     * @return a new instance of AssessmentEventValidator.
     */
     public static AssessmentItemEventValidator action(String actionKey) {
        return new AssessmentItemEventValidator(actionKey);
     }

    /**
     * Convenience method that provides a rollup of AssessmentEvent property validators.
     * @param event
     * @return
     */
    @Override
    public ValidatorResult validate(@Nonnull AssessmentItemEvent event) {
        ValidatorResult result = new ValidatorResult();
        String context = event.getClass().getSimpleName();

        ValidatorResult contextURI = validateContextURI(context, event.getContext());
        if (!contextURI.isValid()) {
            result.errorMessage().appendViolation(contextURI.errorMessage().toString());
        }

        ValidatorResult typeURI = validateTypeURI(context, event.getType());
        if (!typeURI.isValid()) {
            result.errorMessage().appendViolation(typeURI.errorMessage().toString());
        }

        ValidatorResult actor = validateActorIsPerson(context, event.getActor());
        if (!actor.isValid()) {
            result.errorMessage().appendViolation(actor.errorMessage().toString());
        }

        ValidatorResult object = validateObjectIsAssessmentItem(context, event.getObject());
        if (!object.isValid()) {
            result.errorMessage().appendViolation(object.errorMessage().toString());
        }

        if (actionKey.equals(AssessmentItemProfile.Actions.COMPLETED.key())) {
            ValidatorResult generated = validateGeneratedIsResponse(context, event.getGenerated());
            if (!generated.isValid()) {
                result.errorMessage().appendViolation(generated.errorMessage().toString());
            }
        } else {
            ValidatorResult generated = validateGeneratedIsAttempt(context, event.getGenerated());
            if (!generated.isValid()) {
                result.errorMessage().appendViolation(generated.errorMessage().toString());
            }
        }

        ValidatorResult start = validateStartTime(context, event.getStartedAtTime(), event.getEndedAtTime());
        if (!start.isValid()) {
            result.errorMessage().appendViolation(start.errorMessage().toString());
        }

        ValidatorResult duration = validateDuration(context, event.getStartedAtTime(), event.getEndedAtTime(), event.getDuration());
        if (!duration.isValid()) {
            result.errorMessage().appendViolation(duration.errorMessage().toString());
        }

        if (result.errorMessage().length() == 0) {
            result.setIsValid(true);
        } else {
            result.errorMessage().endMessage("Caliper AssessmentItemEvent conformance:");
        }

        return result;
    }

    /**
     * Validate object is an assessment item.
     * @param context
     * @param object
     * @return Validator result
     */
    public ValidatorResult validateObjectIsAssessmentItem(@Nonnull String context, @Nonnull Object object) {
        return EventValidatorUtils.context(context).validateType(object, AssessmentItem.class);
    }

    /**
     * Validate that the generated object is an attempt.
     * @param context
     * @param generated
     * @return Validator result
     */
    public ValidatorResult validateGeneratedIsAttempt(@Nonnull String context, @Nonnull Generatable generated) {
        return EventValidatorUtils.context(context).validateType(generated, Attempt.class);
    }

    /**
     * Validate that the generated object is a response.
     * @param context
     * @param generated
     * @return Validator result
     */
    public ValidatorResult validateGeneratedIsResponse(@Nonnull String context, @Nonnull Generatable generated) {
        return EventValidatorUtils.context(context).validateType(generated, Response.class);
    }
}