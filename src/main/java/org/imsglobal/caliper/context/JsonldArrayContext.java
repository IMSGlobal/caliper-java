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

package org.imsglobal.caliper.context;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.google.common.collect.ImmutableList;
import org.imsglobal.caliper.context.JsonldArrayContext.ArrayContextSerializer;

import java.io.IOException;
import java.util.List;

@JsonSerialize (using = ArrayContextSerializer.class)
public class JsonldArrayContext implements JsonldContext {

    @JsonProperty("@context")
    private ImmutableList<Object> contexts;

    /**
     * Constructor.  Private to force use of factory methods.
     */
    private JsonldArrayContext(List<Object> contexts) {
        this.contexts = ImmutableList.copyOf(contexts);
    }

    /**
     * Factory method
     * @param contexts
     * @return JsonldArrayContext
     */
    public static JsonldArrayContext create(List<Object> contexts) {
        return new JsonldArrayContext(contexts);
    }
    
    public static final class ArrayContextSerializer extends StdSerializer<JsonldArrayContext> {

    	public ArrayContextSerializer() {
			this(null);
		}
    	
		protected ArrayContextSerializer(Class<JsonldArrayContext> t) {
			super(t);
		}
		
		@Override
		public void serialize(JsonldArrayContext value, JsonGenerator gen, SerializerProvider provider)
				throws IOException {
			
			gen.writeStartArray();
			for(Object ctx : value.contexts) {
				gen.writeObject(ctx);
			}
			gen.writeEndArray();
						
		}
		private static final long serialVersionUID = -9083233562305314477L;
		    	
    }
}