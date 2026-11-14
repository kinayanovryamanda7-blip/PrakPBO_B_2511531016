package shop.notification;

public class WhatsAppChannel extends AbstractChannel {

    public WhatsAppChannel() {
        super("WhatsApp");
    }

    @Override
    protected String resolveRecipient(CustomerContact contact) {
        String phone = contact.getPhone();
        boolean validPrefix = phone.startsWith("08") || phone.startsWith("+62");
        return (validPrefix && phone.length() >= 10) ? phone : null;
    }

    @Override
    protected String prepare(String message) {
        return "[Toko UMKM] " + message;
    }

    @Override
    protected boolean deliver(String recipient, String preparedMessage) {
        System.out.println("[WA to " + recipient + "] " + preparedMessage);
        return true;
    }
}