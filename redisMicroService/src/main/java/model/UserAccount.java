package model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import java.io.Serializable;

/**
 * Created by Administrator on 6/16/2016.
 */

public class UserAccount implements Serializable {
    private int id;
    private String bitcoin;

    public UserAccount(int id, String bitcoin) {
        this.id = id;
        this.bitcoin = bitcoin;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBitcoin() {
        return bitcoin;
    }

    public void setBitcoin(String bitcoin) {
        this.bitcoin = bitcoin;
    }
}