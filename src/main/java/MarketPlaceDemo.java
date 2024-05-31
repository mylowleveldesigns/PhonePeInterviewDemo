import cartManager.CartManager;
import cartManager.SimpleCartManager;
import inventoryManager.InventoryManager;
import inventoryManager.SimpleInventoryManager;
import userManager.MapBasedUserManager;
import userManager.UserManager;
import models.*;

import java.util.Arrays;
import java.util.List;

public class MarketPlaceDemo {
    public static void main(String[] args) {
        try {
            // Create UserManager, CartManager, and InventoryManager instances
            UserManager userManager = new MapBasedUserManager();
            InventoryManager inventoryManager = new SimpleInventoryManager();
            CartManager cartManager = new SimpleCartManager(inventoryManager);

            // Create a Marketplace instance with the managers
            MarketPlace marketplace = new MarketPlace(userManager, cartManager, inventoryManager);

            // Sample user data
            User user1 = new User("user1", "Umang Malhotra", "umang@example.com", "password1");
            User user2 = new User("user2", "Aman Malhotra", "aman@example.com", "password2");

            // Sample product data
            Product product1 = new Product("product1", "Laptop", 50000.00, "High-performance laptop", 10);
            Product product2 = new Product("product2", "Samsung Smartphone", 12000.00, "Latest smartphone", 15);
            Product product3 = new Product("product3", "Alexa Echo", 5000.00, "Amazon Alexa echo with voice control", 20);
            Product product4 = new Product("product4", "Smart Watch", 3000.00, "smartwatch with steps count", 15);


            // Create users and add products
            marketplace.createUser(user1);
            marketplace.createUser(user2);
            marketplace.addProduct(product1);
            marketplace.addProduct(product2);
            marketplace.addProduct(product3);
            marketplace.addProduct(product4);


            // getUser call -
            displayUser(marketplace.getUser("user1"));
            displayUser(marketplace.getUser("user2"));


            // Add items to the cart
            marketplace.addToCart("user1", "product1", 2);
            marketplace.addToCart("user1", "product2", 3);

            marketplace.addToCart("user2", "product4", 1);

            // Display user carts
            displayUserCarts(marketplace, "user1");
            displayUserCarts(marketplace, "user2");


            // Checkout
            marketplace.checkout("user1");
            marketplace.checkout("user2");

            // Display order history
            displayOrderHistory(marketplace, "user1");
            displayOrderHistory(marketplace, "user2");

//            new Thread(() -> {
//               marketplace.
//            });

        } catch (Exception e){
            System.out.println("Exception Occured - " + e.getMessage() + Arrays.toString(e.getStackTrace()));
        }

    }

    private static void displayUserCarts(MarketPlace marketplace, String userId) throws Exception {
        System.out.println("Cart for User: " + userId);
        List<CartItem> cart = marketplace.getCart(userId);
        for (CartItem item : cart) {
            Product product = marketplace.getProduct(item.getProductId());
            System.out.println("Product: " + product.getName() + ", Quantity: " + item.getQuantity());
        }
        System.out.println();
    }

    private static void displayOrderHistory(MarketPlace marketplace, String userId) throws Exception {
        System.out.println("Order History for User: " + userId);
        List<Order> orders = marketplace.getOrderHistory(userId);
        for (Order order : orders) {
            System.out.println("Order ID: " + order.getOrderId());
            System.out.println("Total Price: " + order.getTotalPrice());
            System.out.println("Order Date: " + order.getOrderDate());
            System.out.println("Status: " + order.getStatus());
            System.out.println("================");
        }
    }

    private static void displayUser(User user) {
        System.out.println("User Details for userId " + user.getUserId() + " " + user.toString() );
    }
}
