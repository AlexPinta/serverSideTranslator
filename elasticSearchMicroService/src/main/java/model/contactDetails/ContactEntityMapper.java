package model.contactDetails;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.data.elasticsearch.core.EntityMapper;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.io.IOException;

@Configurable
public class ContactEntityMapper implements EntityMapper {

    @Resource(name = "contactSerializer")
    JsonSerializer jsonSerializer;

    @Resource(name = "contactDeserializer")
    JsonDeserializer jsonDeserializer;

    private ObjectMapper objectMapper;

    public ContactEntityMapper() {}

    @PostConstruct
    private void beanInit() {
        objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, true);
        objectMapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
        SimpleModule module = new SimpleModule();
        module.addSerializer(UserContact.class, jsonSerializer);
        module.addDeserializer(UserContact.class, jsonDeserializer);
        objectMapper.registerModule(module);
    }

    @Override
    public String mapToString(Object object) throws IOException {
        return objectMapper.writeValueAsString(object);
    }

    @Override
    public <T> T mapToObject(String source, Class<T> clazz) throws IOException {
        return objectMapper.readValue(source, clazz);
    }
}
