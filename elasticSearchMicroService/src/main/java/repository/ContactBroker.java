package repository;

import model.contactDetails.UserContact;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedDeque;

@Repository
public class ContactBroker {
    private Deque<UserContact> userContactQueue;

    public ContactBroker() {
        this.userContactQueue = new ConcurrentLinkedDeque<>();
    }

    public void addContact(UserContact userContacts) {
        this.userContactQueue.addLast(userContacts);
    }

    public List<UserContact> getContacts(int elementsCount) {
        List<UserContact> userContactList = new ArrayList<>(elementsCount);
        UserContact userContact;
        //TODO need to refactored on stream
        for (int counter=0; counter<elementsCount; counter++) {
            userContact = this.userContactQueue.pollFirst();
            if (userContact == null) break;

            userContactList.add(userContact);
        }
        return userContactList;
    }

}
