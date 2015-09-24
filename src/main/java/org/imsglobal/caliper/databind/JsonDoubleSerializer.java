package org.imsglobal.caliper.databind;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.text.DecimalFormat;

/**
 * Overrides the global ObjectMapper serializationInclusion setting as
 * JsonInclude.Include.NON_EMPTY excludes properties of type double
 * whenever the value is set to 0.0.  Reference this custom serializer
 * by annotating the target property's accessor using @JsonSerialize annotation.
 */
public class JsonDoubleSerializer extends JsonSerializer<Double> {

    @Override
    public void serialize(Double value, JsonGenerator jgen, SerializerProvider provider)
                                            throws IOException, JsonGenerationException {
        if (value.isNaN()) {
            jgen.writeNull();
        } else {
            // Pattern syntax: "0" = always displayed; "#" = a digit, leading zeroes omitted; "." = decimal separator
            final String pattern = "##0.0#";
            final DecimalFormat formatter = new DecimalFormat(pattern);
            final String output = formatter.format(value);
            jgen.writeNumber(output);
        }
    }
}