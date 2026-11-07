package shop.notification;

/** Contract for every notification channel. */
public interface NotificationChannel {

    String channelName();

    /** @return true if the message was delivered */
    boolean send(CustomerContact contact, String message);
}