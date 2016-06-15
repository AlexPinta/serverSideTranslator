package repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController("/contacts")
public class ElasticFiller {
    @Autowired
    private ContactRepository repository;

    @Autowired
    private ElasticsearchTemplate template;

    @RequestMapping(value = "/streaming")
    @ResponseBody
    public void getStreamData() {
        repository.getStreamData();
    }
}
