package org.imsglobal.caliper.metrics;

import java.util.ArrayList;
import java.util.List;

public class AssessmentProfile extends BaseProfile {
    private int maxAttemptsAllowed;
    private int attemptCount;
    private int maxSubmissionsAllowed;
    private int submissionCount;
    private int maxTimeAllowed;
    private int timeTaken;
    private List<String> assets = new ArrayList<String>();
    //private String outcome;

    public static abstract class Builder<T extends Builder<T>> extends BaseProfile.Builder<T>  {
        private int maxAttemptsAllowed;
        private int attemptCount;
        private int maxSubmissionsAllowed;
        private int submissionCount;
        private int maxTimeAllowed;
        private int timeTaken;
        private List<String> assets = new ArrayList<String>();
        //private String outcome;

        /**
         * @param maxAttemptsAllowed
         * @return Assessment setting specifying the maximum number of allowed attempts.
         */
        public T maxAttemptsAllowed(int maxAttemptsAllowed) {
            this.maxAttemptsAllowed = maxAttemptsAllowed;
            return self();
        }

        /**
         * @param attemptCount
         * @return current number of attempts.
         */
        public T attemptCount(int attemptCount) {
            this.attemptCount = attemptCount;
            return self();
        }

        /**
         * @param maxSubmissionsAllowed
         * @return Assessment setting specifying the maximum number of allowed submissions.
         */
        public T maxSubmissionsAllowed(int maxSubmissionsAllowed) {
            this.maxSubmissionsAllowed = maxSubmissionsAllowed;
            return self();
        }

        /**
         * @param submissionCount
         * @return current count of submissions.
         */
        public T submissionCount(int submissionCount) {
            this.submissionCount = submissionCount;
            return self();
        }

        /**
         * TODO time interval: Fractional hours, minutes?
         * @param maxTimeAllowed
         * @return Assessment setting specifying the maximum time allowed to complete the assessment.
         */
        public T maxTimeAllowed(int maxTimeAllowed) {
            this.maxTimeAllowed = maxTimeAllowed;
            return self();
        }

        /**
         * @param timeTaken
         * @return time spent on assessment.
         */
        public T timeTaken(int timeTaken) {
            this.timeTaken = timeTaken;
            return self();
        }

        /**
         * @param assets
         * @return URI list of item assets:, hint, model answer, etc.
         */
        public T assets(List<String> assets) {
            this.assets = assets;
            return self();
        }

        /**
         * Client invokes the build method sans parameters in order to create an immutable profile object.
         * @return a new instance of AssessmentProfile.
         */
        public AssessmentProfile build() {
            return new AssessmentProfile(this);
        }
    }

    private static class Builder2 extends Builder<Builder2> {
        @Override
        protected Builder2 self() {
            return this;
        }
    }

    public static Builder<?> builder() {
        return new Builder2();
    }

    /**
     * Copy parameter settings from the builder to the profile object.
     * @param builder
     */
    protected AssessmentProfile(Builder<?> builder) {
        super(builder);
        this.maxAttemptsAllowed = builder.maxAttemptsAllowed;
        this.attemptCount = builder.attemptCount;
        this.maxSubmissionsAllowed = builder.maxSubmissionsAllowed;
        this.submissionCount = builder.submissionCount;
        this.maxTimeAllowed = builder.maxTimeAllowed;
        this.timeTaken = builder.timeTaken;
        this.assets = builder.assets;
    }
}