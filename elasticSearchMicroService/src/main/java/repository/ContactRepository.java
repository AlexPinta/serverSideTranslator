package repository;
import model.contactDetails.UserContact;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface ContactRepository extends ElasticsearchRepository <UserContact,String> {
}
