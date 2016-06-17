package batch;

import model.contactDetails.UserContact;
import org.springframework.batch.item.data.AbstractPaginatedDataItemReader;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.FacetedPage;
import org.springframework.data.elasticsearch.core.query.StringQuery;
import org.springframework.stereotype.Component;
import org.springframework.util.ClassUtils;
import repository.ContactRepository;

import javax.annotation.PostConstruct;
import java.util.Iterator;

@Component
public class ContactItemReader extends AbstractPaginatedDataItemReader<UserContact> implements InitializingBean {
    @Autowired
    private ContactRepository contactRepository;
    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;
    @Value("${batch.size}")
    int batchSize;

    private Pageable contactPageable;

    @PostConstruct
    public void init() {
        setName(ClassUtils.getShortName(getClass()));
        this.contactPageable = new PageRequest(0, batchSize);
    }

    @Override
    protected Iterator<UserContact> doPageRead() {
        final String QUERY_FIND_ALL = "{query : { match_all : {} } }";
        final StringQuery stringQuery = new StringQuery(QUERY_FIND_ALL, this.contactPageable);
        final FacetedPage<UserContact> userContacts = elasticsearchTemplate.queryForPage(stringQuery, UserContact.class);
//        Page<UserContact> userContacts = contactRepository.findAll(this.contactPageable);
        contactPageable = contactPageable.next();
        return userContacts.iterator();

    }

    @Override
    public void afterPropertiesSet() throws Exception {
    }
}
