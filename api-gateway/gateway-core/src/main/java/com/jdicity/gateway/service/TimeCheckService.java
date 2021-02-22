package com.jdicity.gateway.service;

import com.google.common.base.Throwables;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.Date;

/**
 * Write description here.
 *
 * @author sunjianzhou
 * @date 2020/12/29 15:02
 */
@Slf4j
@Service
public class TimeCheckService {

    private final Long requestAllowSeconds = 300L;

    public boolean checkTimeStamp(String ts) {
        try {
            Date requestDate = DateUtils.parseDate(ts, "yyyyMMddHHmmssSSS");
            long requestTime = requestDate.getTime();
            long currentTime = System.currentTimeMillis();
            log.info("requestTime:{},currentTime:{},currentTime-requestTime:{}", requestTime, currentTime, currentTime - requestTime);
            if (currentTime - requestTime > requestAllowSeconds * 1000) {
                return false;
            }
        } catch (ParseException e) {
            log.error(Throwables.getStackTraceAsString(e));
            return false;
        }
        return true;
    }

}
