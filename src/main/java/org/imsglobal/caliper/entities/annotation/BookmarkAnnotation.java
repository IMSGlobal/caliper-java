package org.imsglobal.caliper.entities.annotation;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.imsglobal.caliper.validators.EntityValidator;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

@JsonPropertyOrder({
    "@id",
    "@type",
    "name",
    "description",
    "extensions",
    "dateCreated",
    "dateModified",
    "annotatedId",
    "bookmarkNotes" })
public class BookmarkAnnotation extends org.imsglobal.caliper.entities.annotation.Annotation {

    @JsonProperty("@type")
    private final AnnotationType type;

    @JsonProperty("bookmarkNotes")
    private String bookmarkNotes;

    /**
     * @param builder apply builder object properties to the BookmarkAnnotation object.
     */
    protected BookmarkAnnotation(Builder<?> builder) {
        super(builder);

        EntityValidator.checkType(builder.type, AnnotationType.BOOKMARK_ANNOTATION);

        this.type = builder.type;
        this.bookmarkNotes = builder.bookmarkNotes;
    }

    /**
     * @return the type
     */
    @Override
    @Nonnull
    public AnnotationType getType() {
        return type;
    }

    /**
     * @return the bookmarkNotes
     */
    @Nullable
    public String getBookmarkNotes() {
        return bookmarkNotes;
    }

    /**
     * Builder class provides a fluid interface for setting object properties.
     * @param <T> builder
     */
    public static abstract class Builder<T extends Builder<T>> extends Annotation.Builder<T>  {
        private AnnotationType type;
        private String bookmarkNotes;

        /**
         * Initialize type with default value.
         */
        public Builder() {
            type(AnnotationType.BOOKMARK_ANNOTATION);
        }

        /**
         * @param type
         * @return builder.
         */
        private T type(AnnotationType type) {
            this.type = type;
            return self();
        }

        /**
         * @param bookmarkNotes
         * @return annotation bookmark notes.
         */
        public T bookmarkNotes(String bookmarkNotes) {
            this.bookmarkNotes = bookmarkNotes;
            return self();
        }

        /**
         * Client invokes build method in order to create an immutable object.
         * @return a new instance of BookmarkAnnotation.
         */
        public BookmarkAnnotation build() {
            return new BookmarkAnnotation(this);
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