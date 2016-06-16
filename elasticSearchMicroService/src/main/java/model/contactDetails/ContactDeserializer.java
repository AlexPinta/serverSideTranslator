package model.contactDetails;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import java.io.IOException;


public class ContactDeserializer extends JsonDeserializer<UserContact> {

    @Override
    public UserContact deserialize(JsonParser jp, DeserializationContext ctxt)
            throws IOException, JsonProcessingException {
        final UserContact userContact = new UserContact();
        JsonNode node = jp.getCodec().readTree(jp);

        userContact.setId(node.get("id").asLong());
        userContact.setEmail(node.get("email").asText());
        userContact.setFirstName(node.get("firstName").asText());
        userContact.setGender(node.get("gender").asText());
        userContact.setIpAddress(node.get("ipAddress").asText());
        userContact.setLastName(node.get("lastName").asText());
        return userContact;
    }
}
