package gavin.test.homework.infra.dto;

import java.util.Date;

public interface Session {

    String getSessionId();
    String getUserId();
    Date getCreateTime();

}
