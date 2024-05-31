package inventoryManager;

import models.Product;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class SimpleInventoryManager implements InventoryManager {
    private final Map<String, Product> productsInventoryMap = new ConcurrentHashMap<>();

    @Override
    public synchronized boolean checkInventory(String productId, int quantity) {
        Product product = productsInventoryMap.get(productId);
        return product != null && product.getQuantity() >= quantity;
    }

    @Override
    public synchronized void decreaseInventory(String productId, int quantity) {
        Product product = productsInventoryMap.get(productId);
        if (product != null) {
            product.setQuantity(product.getQuantity() - quantity);
        }
    }

    @Override
    public synchronized void increaseInventory(Product product, int quantity) throws Exception {
        if (product != null && quantity > 0) {
                Product existingProduct = productsInventoryMap.get(product.getProductId());
                if (existingProduct != null) {
                    existingProduct.setQuantity(existingProduct.getQuantity() + quantity);
                } else {
                    Product newProduct = new Product(
                            product.getProductId(),
                            product.getName(),
                            product.getPrice(),
                            product.getDescription(),
                            quantity
                    );
                    productsInventoryMap.put(product.getProductId(), newProduct);
                }
        }
    }

    public Product getProduct(String productId) {
        return productsInventoryMap.get(productId);
    }
}
