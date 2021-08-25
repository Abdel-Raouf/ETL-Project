package com.ripple.etl;

public interface AlertService {
    void sendAlert(String subject, String body);

    void sendAlert(Throwable throwable);
}
