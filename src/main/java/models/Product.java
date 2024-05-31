package models;

public class Product {
    private String productId;
    private String name;
    private double price;
    private String description;
    private int quantity;

    public Product(String productId, String name, double price, String description, int quantity) {
        this.productId = productId;
        this.name = name;
        this.price = price;
        this.description = description;
        this.quantity = quantity;
    }

    public String getProductId() {
        return productId;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public String getDescription() {
        return description;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int i) {
        this.quantity=i;
    }
}
