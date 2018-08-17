package mypromoguide.pro.novatechsolutions.app.promo_api.entity;

import java.io.Serializable;

public class Product implements Serializable {

    private int promotion_id;
    private String group;
    private double was_price;
    private double now_price;
    private String buy_product;
    private String get_product;
    private double buy;
    private double save;
    private double product_price;
    private String product_no;
    private String promo_type;
    private int promo_type_id;
    private String product_name;
    private String description;
    private String avatar;
    private String category_name;
    private String brand_name;

    public int getPromotion_id() {
        return promotion_id;
    }

    public void setPromotion_id(int promotion_id) {
        this.promotion_id = promotion_id;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public double getWas_price() {
        return was_price;
    }

    public void setWas_price(double was_price) {
        this.was_price = was_price;
    }

    public double getNow_price() {
        return now_price;
    }

    public void setNow_price(double now_price) {
        this.now_price = now_price;
    }

    public String getBuy_product() {
        return buy_product;
    }

    public void setBuy_product(String buy_product) {
        this.buy_product = buy_product;
    }

    public String getGet_product() {
        return get_product;
    }

    public void setGet_product(String get_product) {
        this.get_product = get_product;
    }

    public double getBuy() {
        return buy;
    }

    public void setBuy(double buy) {
        this.buy = buy;
    }

    public double getSave() {
        return save;
    }

    public void setSave(double save) {
        this.save = save;
    }

    public double getProduct_price() {
        return product_price;
    }

    public void setProduct_price(double product_price) {
        this.product_price = product_price;
    }

    public String getProduct_no() {
        return product_no;
    }

    public void setProduct_no(String product_no) {
        this.product_no = product_no;
    }

    public String getPromo_type() {
        return promo_type;
    }

    public void setPromo_type(String promo_type) {
        this.promo_type = promo_type;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getCategory_name() {
        return category_name;
    }

    public void setCategory_name(String category_name) {
        this.category_name = category_name;
    }

    public String getBrand_name() {
        return brand_name;
    }

    public void setBrand_name(String brand_name) {
        this.brand_name = brand_name;
    }

    public int getPromo_type_id() {
        return promo_type_id;
    }

    public void setPromo_type_id(int promo_type_id) {
        this.promo_type_id = promo_type_id;
    }
}
