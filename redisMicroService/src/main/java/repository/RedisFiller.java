package repository;

import model.UserAccount;

import javax.xml.stream.XMLStreamException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 6/16/2016.
 */
public class RedisFiller {

    public void putToRedis() {
        ContactRedisRepository repository = new ContactRedisRepository();
        List<UserAccount> userAccounts = new ArrayList<>();
        try {
            userAccounts = repository.getStreamData();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (XMLStreamException e) {
            e.printStackTrace();
        }

        repository.putStreamData(userAccounts);
    }
}
