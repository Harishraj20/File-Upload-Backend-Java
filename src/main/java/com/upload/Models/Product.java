package com.upload.Models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "product_table")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String productName;
    private String productDescription;
    private int quantity;
    private String capacity;
    private int starRating;
    private String technology;
    private String brand;
    private String seller;
    private int discount;
    private String offer;
    private int mrp;

    public String getStockStatus() {
        return stockStatus;
    }

    public void setStockStatus(String stockStatus) {
        this.stockStatus = stockStatus;
    }

    private String imagePath;
    private String recommendation;
    private int deliveryday;
    private String stockStatus;

    public Product(String productName, String productDescription, int quantity, String capacity, int starRating,
            String technology, String brand, String seller, int discount, String offer, int mrp, String imagePath,
            String recommendation, int deliveryday) {
        this.productName = productName;
        this.productDescription = productDescription;
        this.quantity = quantity;
        this.capacity = capacity;
        this.starRating = starRating;
        this.technology = technology;
        this.brand = brand;
        this.seller = seller;
        this.discount = discount;
        this.offer = offer;
        this.mrp = mrp;
        this.imagePath = imagePath;
        this.recommendation = recommendation;
        this.deliveryday = deliveryday;
    }

    public String getRecommendation() {
        return recommendation;
    }

    public void setRecommendation(String recommendation) {
        this.recommendation = recommendation;
    }

    public Product() {
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getCapacity() {
        return capacity;
    }

    public void setCapacity(String capacity) {
        this.capacity = capacity;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public int getStarRating() {
        return starRating;
    }

    public void setStarRating(int starRating) {
        this.starRating = starRating;
    }

    public String getTechnology() {
        return technology;
    }

    public void setTechnology(String technology) {
        this.technology = technology;
    }

    public String getSeller() {
        return seller;
    }

    public void setSeller(String seller) {
        this.seller = seller;
    }

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }

    public String getOffer() {
        return offer;
    }

    public void setOffer(String offer) {
        this.offer = offer;
    }

    public int getMrp() {
        return mrp;
    }

    public void setMrp(int mrp) {
        this.mrp = mrp;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public int getDeliveryday() {
        return deliveryday;
    }

    public void setDeliveryday(int deliveryday) {
        this.deliveryday = deliveryday;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Product{");
        sb.append(", productName=").append(productName);
        sb.append(", productDescription=").append(productDescription);
        sb.append(", quantity=").append(quantity);
        sb.append(", capacity=").append(capacity);
        sb.append(", starRating=").append(starRating);
        sb.append(", technology=").append(technology);
        sb.append(", brand=").append(brand);
        sb.append(", seller=").append(seller);
        sb.append(", discount=").append(discount);
        sb.append(", offer=").append(offer);
        sb.append(", mrp=").append(mrp);
        sb.append(", imagePath=").append(imagePath);
        sb.append(", recommendation=").append(recommendation);
        sb.append(", deliveryday=").append(deliveryday);
        sb.append(", stock status=").append(stockStatus);
        sb.append('}');
        return sb.toString();
    }

}
