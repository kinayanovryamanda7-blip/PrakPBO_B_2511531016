package shop.exception;

/** Payment failed (bank down, balance not enough, ...). Checked. */
public class PaymentFailedException extends ShopException {
    private static final long serialVersionUID = 1L;

    public PaymentFailedException(String message) {
        super(message);
    }

    public PaymentFailedException(String message, Throwable cause) {
        super(message, cause);
    }
}
