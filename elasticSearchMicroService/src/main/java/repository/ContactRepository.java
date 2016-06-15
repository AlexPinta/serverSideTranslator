package repository;
import model.UserContactData;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface ContactRepository extends ElasticsearchRepository { //<UserContactData,String> {
//    @Query
    List getStreamData();
}
