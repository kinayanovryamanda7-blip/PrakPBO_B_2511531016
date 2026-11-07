package shop.notification;

public class SmsChannel extends AbstractChannel {

    private static final String ELLIPSIS = "...";
    private final int maxLength;

    public SmsChannel(int maxLength) {
        super("SMS");
        if (maxLength <= ELLIPSIS.length()) {
            throw new IllegalArgumentException("Max length must be greater than 3");
        }
        this.maxLength = maxLength;
    }

    @Override
    protected String resolveRecipient(CustomerContact contact) {
        String phone = contact.getPhone();
        return phone.startsWith("08") ? phone : null;
    }

    /** Longer messages are cut to exactly maxLength characters, ending with "...". */
    @Override
    protected String prepare(String message) {
        if (message.length() <= maxLength) {
            return message;
        }
        return message.substring(0, maxLength - ELLIPSIS.length()) + ELLIPSIS;
    }

    @Override
    protected boolean deliver(String recipient, String preparedMessage) {
        System.out.println("[SMS to " + recipient + "] " + preparedMessage);
        return true;
    }
}