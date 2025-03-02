package gavin.test.homework.infra.dto;

import lombok.Setter;

import java.util.Date;

@Setter
public class DefaultSession implements Session{

    private String sessionId;
    private String userId;
    private Date createDate;


    @Override
    public String getSessionId() {
        return sessionId;
    }

    @Override
    public String getUserId() {
        return userId;
    }

    @Override
    public Date getCreateTime() {
        return createDate;
    }

}
