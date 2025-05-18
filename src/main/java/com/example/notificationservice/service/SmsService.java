package com.example.notificationservice.service;

import com.example.notificationservice.model.Notification;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class SmsService {

    @Value("${twilio.phone.number}")
    private String twilioNumber;

    public void send(Notification notif) {
        String toPhoneNumber = "+918651803465";
        try {
            Message message = Message.creator(
                    new PhoneNumber(toPhoneNumber),
                    new PhoneNumber(twilioNumber),
                    notif.getMessage()
            ).create();

            System.out.println("SMS sent to " + toPhoneNumber + ": SID = " + message.getSid());

        } catch (Exception e) {
            System.err.println("Failed to send SMS via Twilio: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Twilio SMS failed", e);
        }
    }
}
