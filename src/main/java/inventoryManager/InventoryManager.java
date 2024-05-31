package inventoryManager;

import models.Product;

public interface InventoryManager {
    boolean checkInventory(String productId, int quantity);
    void decreaseInventory(String productId, int quantity);
    void increaseInventory(Product product, int quantity) throws Exception;

    Product getProduct(String productId);
}
