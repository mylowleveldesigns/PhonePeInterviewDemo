package cartManager;

import inventoryManager.InventoryManager;
import models.CartItem;
import models.Order;
import models.Product;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class SimpleCartManager implements CartManager {
    private Map<String, List<CartItem>> shoppingCarts = new ConcurrentHashMap<>();
    private InventoryManager inventoryManager;

    private List<Order> orderHistory;


    public SimpleCartManager(InventoryManager inventoryManager) {
        this.inventoryManager = inventoryManager;
        this.orderHistory = new ArrayList<>();
    }

    @Override
    public void addToCart(String userId, String productId, int quantity) throws Exception {
        if (!inventoryManager.checkInventory(productId, quantity)) {
            throw new Exception("Cannot Add productId - " + productId + " to the cart. Because it is out of stock");
        } else {
            List<CartItem> cart = shoppingCarts.computeIfAbsent(userId, k -> new ArrayList<>());
            CartItem cartItem = new CartItem(productId, quantity);
            cart.add(cartItem);
        }
    }

    @Override
    public List<CartItem> getCart(String userId) {
        return shoppingCarts.getOrDefault(userId, new ArrayList<>());
    }

    @Override
    public synchronized void checkout(String userId) throws Exception {
        List<CartItem> cart = shoppingCarts.get(userId);
        if (cart == null || cart.isEmpty()) {
            throw new Exception("Cannot initiate checkout, no iterm is there is cart");
        } else {
            double totalPrice = 0;
            List<Order> orders = new ArrayList<>();

            // @Todo - validation can be added before decreasingInventory or Locking would be a prefered solution
            // @Todo But cannot do because of short time.

            List<CartItem> succesFulCartItems = new ArrayList<>();
            for (CartItem cartItem : cart) {
                String productId = cartItem.getProductId();
                int quantity = cartItem.getQuantity();

                Product product = inventoryManager.getProduct(productId);

                if (product != null && inventoryManager.checkInventory(productId, quantity)) {
                    inventoryManager.decreaseInventory(productId, quantity);

                    totalPrice += product.getPrice() * quantity;
                    succesFulCartItems.add(cartItem);
                } else {
                    System.out.println("productId - " + productId  + " was not added to order for checkout, because it got sold out.");
                }
            }

            Order order = new Order(UUID.randomUUID().toString(), userId, succesFulCartItems, totalPrice, new Date(), "NEW");
            orders.add(order);
            orderHistory.addAll(orders);

            cart.clear();
        }
    }

    @Override
    public List<Order> getOrderHistory(String userId) {
        return orderHistory.stream()
                .filter(order -> order.getUserId().equals(userId))
                .collect(Collectors.toList());
    }
}