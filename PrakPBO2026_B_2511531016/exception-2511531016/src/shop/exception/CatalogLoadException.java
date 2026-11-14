package shop.exception;

/** The catalog file could not be read. Always wraps the original cause. */
public class CatalogLoadException extends ShopException {
    private static final long serialVersionUID = 1L;

    public CatalogLoadException(String message, Throwable cause) {
        super(message, cause);
    }
}
