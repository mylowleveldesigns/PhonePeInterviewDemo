import cartManager.CartManager;
import inventoryManager.InventoryManager;
import models.Order;
import models.Product;
import models.User;
import models.CartItem;
import userManager.UserManager;

import java.util.List;

public class MarketPlace {
    private UserManager userManager;
    private CartManager cartManager;
    private InventoryManager inventoryManager;

    public MarketPlace(UserManager userManager, CartManager cartManager, InventoryManager inventoryManager) {
        this.userManager = userManager;
        this.cartManager = cartManager;
        this.inventoryManager = inventoryManager;
    }

    public void createUser(User user) throws Exception {
        if(!validateUser(user)){
            throw new Exception("Invalid User Details Provided");
        } else {
            userManager.createUser(user);
        }
    }

    public User getUser(String userId) throws Exception {
        User user =  userManager.getUser(userId);
        if(user == null) {
            throw new Exception("Unable to get User with provided userId, please check userId again");
        } else {
            return user;
        }
    }

    public Product getProduct(String productId) throws Exception {
        if(!validateProductPresence(productId)){
            throw new Exception("Invalid productId provided - " + productId);
        }
        return inventoryManager.getProduct(productId) ;
    }

    public void addToCart(String userId, String productId, int quantity) throws Exception {
        if( !validateUserPresence(userId) || !validateProductPresence(productId) ) {
            throw new Exception("Invalid userId or productId provided for userId - " + userId + " productId " + productId);
        }
        cartManager.addToCart(userId, productId, quantity);
    }

    public void addProduct(Product product) throws Exception {

        inventoryManager.increaseInventory(product, product.getQuantity());
    }


    public List<CartItem> getCart(String userId) throws Exception {
        if(!validateUserPresence(userId)) {
            throw new Exception("Invalid userId provided. User not present in system.");
        }
        return cartManager.getCart(userId);
    }

    public void checkout(String userId) throws Exception {
        if(!validateUserPresence(userId)) {
            throw new Exception("Invalid userId provided. User not present in system.");
        }
        cartManager.checkout(userId);
    }


    public List<Order> getOrderHistory(String userId) throws Exception {
        if(!validateUserPresence(userId)) {
            throw new Exception("Invalid userId provided. User not present in system.");
        }
        return cartManager.getOrderHistory(userId);
    }

    private boolean validateUser(User user) {

        if (userManager.getUser(user.getUserId()) != null) {
            System.out.println("User already exists  User not created.");
            return false;
        }

        if (user.getName() == null || user.getName().isEmpty()) {
            System.out.println("Name is required.");
            return false;
        }
        if (user.getEmail() == null || !isValidEmail(user.getEmail())) {
            System.out.println("Invalid email.");
            return false;
        }
        // @Todo Add more validation rules if needed

        return true;
    }

    private boolean isValidEmail(String email) {
        // @Todo - code and logic to verify valid email
        return email.contains("@");
    }

    private boolean validateUserPresence(String userId) {
        return userManager.getUser(userId) != null;
    }

    private boolean validateProductPresence(String productId) {
        return inventoryManager.getProduct(productId) != null;
    }
}
