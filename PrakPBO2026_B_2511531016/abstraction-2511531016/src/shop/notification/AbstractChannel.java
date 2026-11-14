package shop.notification;

/**
 * Template Method: send(...) is final and fixes the order of steps.
 * Each channel only fills in the steps that differ.
 */
public abstract class AbstractChannel implements NotificationChannel {

    private final String channelName;
    private int sentCount;
    private String lastDelivered = "";

    protected AbstractChannel(String channelName) {
        if (channelName == null || channelName.isBlank()) {
            throw new IllegalArgumentException("Channel name must not be blank");
        }
        this.channelName = channelName;
    }

    @Override
    public String channelName() { return channelName; }

    @Override
    public final boolean send(CustomerContact contact, String message) {
        // 1. reject empty message / contact
        if (contact == null || message == null || message.isBlank()) {
            return false;
        }
        // 2. find the destination address
        String recipient = resolveRecipient(contact);
        if (recipient == null) {
            return false;
        }
        // 3. prepare the message
        String prepared = prepare(message);
        // 4. deliver
        if (!deliver(recipient, prepared)) {
            return false;
        }
        // 5. log
        sentCount++;
        lastDelivered = prepared;
        return true;
    }

    /** @return the destination address, or null if this contact cannot receive it. */
    protected abstract String resolveRecipient(CustomerContact contact);

    /** Actually sends the (already prepared) message. */
    protected abstract boolean deliver(String recipient, String preparedMessage);

    /** Hook: channels may override to change the message. Default: unchanged. */
    protected String prepare(String message) {
        return message;
    }

    public int getSentCount() { return sentCount; }

    public String getLastDelivered() { return lastDelivered; }
}