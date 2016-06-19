package repository;

import model.UserAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import javax.xml.namespace.QName;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 6/16/2016.
 */
public class ContactRedisRepository {
    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    public List<UserAccount> getStreamData() throws IOException, XMLStreamException {
        List<UserAccount> userAccounts = new ArrayList<>();

        String uri = "C:\\redis-data.xml"; // path to the file
        URL url = new URL(uri);
        InputStream input = url.openStream();
        XMLInputFactory factory = XMLInputFactory.newInstance();
        XMLStreamReader r = factory.createXMLStreamReader(input);
        try {
            int event = r.getEventType();
            while (true) {
                switch (event) {
                    case XMLStreamConstants.START_DOCUMENT:
                        System.out.println("Start Document.");
                        break;
                    case XMLStreamConstants.START_ELEMENT:
                        System.out.println("Start Element: " + r.getName());
                        int id = -1;
                        String bitcoin = "";
                        for(int i = 0, n = r.getAttributeCount(); i < n; ++i) {
                            System.out.println("Attribute: " + r.getAttributeName(i)
                                    + "=" + r.getAttributeValue(i));

                            String attributeName = String.valueOf(r.getAttributeName(i));
                            if (attributeName.equalsIgnoreCase("id")) {
                                id = Integer.valueOf(r.getAttributeValue(i));
                            } else if (attributeName.equalsIgnoreCase("id")) {
                                bitcoin = r.getAttributeValue(i);
                            }
                        }

                        if (id != -1) {
                            userAccounts.add(new UserAccount(id, bitcoin));
                        }

                        break;
                    case XMLStreamConstants.CHARACTERS:
                        if (r.isWhiteSpace())
                            break;

                        System.out.println("Text: " + r.getText());
                        break;
                    case XMLStreamConstants.END_ELEMENT:
                        System.out.println("End Element:" + r.getName());
                        break;
                    case XMLStreamConstants.END_DOCUMENT:
                        System.out.println("End Document.");
                        break;
                }

                if (!r.hasNext())
                    break;

                event = r.next();
            }
        } finally {
            r.close();
        }

        return userAccounts;
    }

    public void putStreamData(List<UserAccount> userAccounts) {
        for (UserAccount userAccount : userAccounts) {
            redisTemplate.opsForList().leftPush(String.valueOf(userAccount.getId()), userAccount.getBitcoin());
        }
    }

    public RedisTemplate<String, String> getRedisTemplate() {
        return redisTemplate;
    }

    public void setRedisTemplate(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }
}
