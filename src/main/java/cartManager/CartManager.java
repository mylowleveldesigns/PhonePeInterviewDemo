package cartManager;

import models.CartItem;
import models.Order;

import java.util.List;

public interface CartManager {
    void addToCart(String userId, String productId, int quantity) throws Exception;
    List<CartItem> getCart(String userId);
    void checkout(String userId) throws Exception;

    List<Order> getOrderHistory(String userId);
}
