package shop.service;

import java.util.List;
import shop.model.Product;
import shop.payment.PaymentMethod;
import shop.payment.RefundablePayment;
import shop.shipping.Shippable;
import shop.shipping.ShippingService;

public class CheckoutService {

    public long subtotal(List<Product> items) {
        long total = 0;
        for (Product product : items) {
            total += product.getFinalPrice();
        }
        return total;
    }

    public int totalWeight(List<Product> items) {
        int grams = 0;
        for (Product product : items) {
            if (product instanceof Shippable shippable) { // a capability, not a class
                grams += shippable.getweightGrams();
            }
        }
        return grams;
    }

    /** subtotal + shipping cost from totalWeight(items). */
    public long total(List<Product> items, ShippingService shipping) {
        return subtotal(items) + shipping.calculateCost(totalWeight(items));
    }

    /** T1.d: total + admin fee of the chosen payment method. */
    public long grandTotal(List<Product> items, ShippingService shipping, PaymentMethod payment) {
        long total = total(items, shipping);
        return total + payment.adminFee(total);
    }

    /** Program to an interface: only knows PaymentMethod and ShippingService. */
    public boolean checkout(List<Product> items, ShippingService shipping, PaymentMethod payment) {
        if (items == null || items.isEmpty()) {
            return false;
        }
        return payment.pay(grandTotal(items, shipping, payment));
    }

    /** Only payment methods that are RefundablePayment are accepted by the compiler. */
    public boolean refund(RefundablePayment payment, long amount) {
        return payment.refund(amount);
    }
}