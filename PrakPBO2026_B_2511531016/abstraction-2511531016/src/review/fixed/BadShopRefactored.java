package review.fixed;

import java.util.ArrayList;
import java.util.List;

/*
 * T3 - VERSI REFACTOR dari review.BadShop
 * Perbaikan:
 *   Fix 1: interface besar dipecah menjadi interface kecil
 *   Fix 2: kurir berbasis String diganti hierarki class
 *   Fix 3: Address yang bisa berubah dan dipakai bersama dijadikan immutable
 *   Fix 4: instanceof di dalam default method dihilangkan
 *   Fix 5: inner class yang seharusnya static dijadikan static nested class
 *   Fix 6: uang memakai long (bukan double) dan ada validasi
 */

// Fix 1: interface kecil, menggantikan satu interface besar StoreOperations

interface PaymentMethod {
    boolean pay(long amount);

    String printInvoice();
}

interface RefundablePayment extends PaymentMethod {
    boolean refund(long amount);
}

/** Hanya barang fisik yang mengimplementasi ini; voucher tidak perlu. */
interface Shippable {
    long ship(int weightGrams);
}

// Fix 1 + 6: voucher hanya PaymentMethod, uang memakai long dan jumlah tidak sah ditolak

class GiftVoucher implements PaymentMethod {
    private long balance;

    GiftVoucher(long balance) {
        if (balance < 0) {
            throw new IllegalArgumentException("Balance must not be negative");
        }
        this.balance = balance;
    }

    @Override
    public boolean pay(long amount) {
        if (amount <= 0 || amount > balance) {
            return false;
        }
        balance -= amount;
        return true;
    }

    @Override
    public String printInvoice() {
        return "sisa Rp" + balance;
    }
}

// Fix 2: abstraksi kurir yang sebenarnya (tanpa String dan switch)

abstract class ShippingService {
    private final String name;

    protected ShippingService(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Name must not be blank");
        }
        this.name = name;
    }

    String getName() {
        return name;
    }

    /** Setiap kurir hanya menentukan tarifnya sendiri. */
    protected abstract long ratePerKg();

    /** Rumus ongkir dipakai bersama dan dikunci dengan final. */
    public final long cost(int kilograms) {
        if (kilograms <= 0) {
            return 0;
        }
        return kilograms * ratePerKg();
    }
}

class RegularShipping extends ShippingService {
    RegularShipping() {
        super("Reguler");
    }

    @Override
    protected long ratePerKg() {
        return 10_000;
    }
}

class ExpressShipping extends ShippingService {
    ExpressShipping() {
        super("Ekspres");
    }

    @Override
    protected long ratePerKg() {
        return 18_000;
    }
}

class SameDayShipping extends ShippingService {
    SameDayShipping() {
        super("Same Day");
    }

    @Override
    protected long ratePerKg() {
        return 30_000;
    }
}

// Fix 5: Item tidak butuh objek Cart, jadi dibuat static

class Cart {
    private final List<Item> items = new ArrayList<>();

    static class Item {
        private final String name;
        private final long price;
        private final int quantity;

        Item(String name, long price, int quantity) {
            if (name == null || name.isBlank()) {
                throw new IllegalArgumentException("Name must not be blank");
            }
            if (price < 0 || quantity <= 0) {
                throw new IllegalArgumentException("Invalid price or quantity");
            }
            this.name = name;
            this.price = price;
            this.quantity = quantity;
        }

        long subtotal() {
            return price * quantity;
        }

        String getName() {
            return name;
        }
    }

    void add(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("Item must not be null");
        }
        items.add(item);
    }

    long total() {
        long total = 0;
        for (Item item : items) {
            total += item.subtotal();
        }
        return total;
    }
}

// Fix 3: Address immutable (final class, field final, tanpa setter)

final class Address {
    private final String street;
    private final String city;

    Address(String street, String city) {
        if (street == null || street.isBlank() || city == null || city.isBlank()) {
            throw new IllegalArgumentException("Street and city must not be blank");
        }
        this.street = street;
        this.city = city;
    }

    String getStreet() {
        return street;
    }

    String getCity() {
        return city;
    }

    /** "Mengubah" kota membuat Address baru; Address yang lama tidak berubah. */
    Address withCity(String newCity) {
        return new Address(street, newCity);
    }

    @Override
    public String toString() {
        return street + ", " + city;
    }
}

class SimpleOrder {
    private final String id;
    private final Address address;

    SimpleOrder(String id, Address address) {
        if (id == null || id.isBlank() || address == null) {
            throw new IllegalArgumentException("Order id and address are required");
        }
        this.id = id;
        this.address = address;
    }

    Address getAddress() {
        return address;
    }

    @Override
    public String toString() {
        return id + " -> " + address;
    }
}

// Fix 4: tiap diskon menyebut persentasenya sendiri, tanpa instanceof

interface Discount {
    int percent();

    default long apply(long price) {
        return price - price * safePercent() / 100;
    }

    private int safePercent() {
        return Math.max(0, Math.min(100, percent()));
    }
}

class MemberDiscount implements Discount {
    @Override
    public int percent() {
        return 5;
    }
}

class FlashSaleDiscount implements Discount {
    @Override
    public int percent() {
        return 30;
    }
}

public class BadShopRefactored {
    public static void main(String[] args) {
        Cart cart = new Cart();
        cart.add(new Cart.Item("Kopi Gayo", 85_000, 2));
        System.out.println("Total cart: " + cart.total());

        ShippingService courier = new ExpressShipping();
        System.out.println("Ongkir " + courier.getName() + " 2 kg: " + courier.cost(2));

        Address home = new Address("Jl. Sudirman 10", "Padang");
        SimpleOrder first = new SimpleOrder("ORD-1", home);
        SimpleOrder second = new SimpleOrder("ORD-2", home.withCity("Bukittinggi"));
        System.out.println(first);
        System.out.println(second);

        Discount flashSale = new FlashSaleDiscount();
        System.out.println("Harga flash sale: " + flashSale.apply(100_000));

        PaymentMethod voucher = new GiftVoucher(100_000);
        System.out.println(voucher.pay(30_000) + " | " + voucher.printInvoice());
        // voucher.ship(100); // sekarang tidak bisa dikompilasi: voucher bukan Shippable
    }
}