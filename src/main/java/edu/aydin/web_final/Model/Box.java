package edu.aydin.web_final.Model;

public class Box {
    private int boxId;
    private int userId;
    private int productId;

    // Constructor
    public Box(int boxId, int userId, int productId) {
        this.boxId = boxId;
        this.userId = userId;
        this.productId = productId;
    }

    public int getBoxId() {
        return boxId;
    }

    public void setBoxId(int boxId) {
        this.boxId = boxId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }
}
