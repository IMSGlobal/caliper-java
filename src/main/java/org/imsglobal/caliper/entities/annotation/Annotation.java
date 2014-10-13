package org.imsglobal.caliper.entities.annotation;

import org.imsglobal.caliper.entities.CaliperEntity;
import org.imsglobal.caliper.entities.schemadotorg.Thing;

/**
 * The super-class of all Annotation types.
 * 
 * Direct sub-types can include - Highlight, Attachment, etc. - all of
 * which are specified in the Caliper Annotation Metric Profile
 */
public class Annotation extends CaliperEntity implements Thing {

    public enum Type {
        ANNOTATION("http://purl.imsglobal.org/caliper/v1/Annotation"),
        BOOKMARK_ANNOTATON("http://purl.imsglobal.org/caliper/v1/BookmarkAnnotation"),
        HIGHLIGHT_ANNOTATON("http://purl.imsglobal.org/caliper/v1/HighlightAnnotation"),
        SHARED_ANNOTATON("http://purl.imsglobal.org/caliper/v1/SharedAnnotation"),
        TAG_ANNOTATON("http://purl.imsglobal.org/caliper/v1/TagAnnotation");

        private final String uri;

        /**
         * Private constructor
         * @param uri
         */
        private Type(final String uri) {
            this.uri = uri;
        }

        /**
         * @return URI string
         */
        public String uri() {
            return uri;
        }
    }

    /**
     * Target object.
     */
    private Object target;

    /**
     * @param builder apply builder object properties to the Annotation object.
     */
    protected Annotation(Builder<?> builder) {
        super(builder);
        this.target = builder.target;
    }

    /**
     * @return the target
     */
    public Object getTarget() {
        return target;
    }


    /**
     * Builder class provides a fluid interface for setting object properties.
     * @param <T> builder
     */
    public static abstract class Builder<T extends Builder<T>> extends CaliperEntity.Builder<T>  {
        private Object target;

        /**
         * Initialize type with default value.  Required if builder().type() is not set by user.
         */
        public Builder() {
            type(Annotation.Type.ANNOTATION.uri());
        }

        /**
         * @param target
         * @return annotation target.
         */
        public T target(Object target) {
            this.target = target;
            return self();
        }

        /**
         * Client invokes build method in order to create an immutable object.
         * @return a new instance of Annotation.
         */
        public Annotation build() {
            return new Annotation(this);
        }
    }

    /**
     *
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