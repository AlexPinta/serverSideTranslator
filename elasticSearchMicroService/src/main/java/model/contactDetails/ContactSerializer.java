package model.contactDetails;


import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

public class ContactSerializer extends JsonSerializer<UserContact> {

    @Override
    public void serialize(UserContact value, JsonGenerator jgen, SerializerProvider provider) throws IOException, JsonProcessingException {
        jgen.writeStartObject();
        jgen.writeNumberField("id", value.getId());
        jgen.writeStringField("gender", value.getGender());
        jgen.writeStringField("firstName", value.getFirstName());
        jgen.writeStringField("lastName", value.getLastName());
        jgen.writeStringField("email", value.getEmail());
        jgen.writeStringField("ipAddress", value.getIpAddress());
        jgen.writeEndObject();
    }
}
