package batch;

import model.contactDetails.UserContact;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import repository.ContactBroker;

public class ContactProcessor implements ItemProcessor<UserContact, Object> {
//        private Logger logger = getLogger(getClass());
    @Autowired
    ContactBroker contactBroker;

    @Override
    public Object process(UserContact item) throws Exception {
        contactBroker.addContact(item);
        return null;
    }
}
