package shop.notification;

import java.util.ArrayList;
import java.util.List;

public class NotificationService {

    // Depends on the interface, so any new channel (even anonymous) can be registered.
    private final List<NotificationChannel> channels = new ArrayList<>();

    public void register(NotificationChannel channel) {
        if (channel == null) {
            throw new IllegalArgumentException("Channel must not be null");
        }
        channels.add(channel);
    }

    /** @return number of channels that delivered the message successfully */
    public int broadcast(CustomerContact contact, String message) {
        int success = 0;
        for (NotificationChannel channel : channels) {
            if (channel.send(contact, message)) {
                success++;
            }
        }
        return success;
    }
}