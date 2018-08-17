package mypromoguide.pro.novatechsolutions.app.promo_api.entity;

import java.io.Serializable;

public class Store implements Serializable {

    private int id;
    private String name;
    private String address;
    private String ot_weekdays;
    private String ot_sundays;
    private String ot_saturdays;
    private String ot_public_holidays;
    private String avatar;
    private int promotion_count;
    private String start_date;
    private String end_date;
    private String shopping_center;
    private double lat;
    private double lng;
    private String distance;
    private int parent_id;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public int getPromotion_count() {
        return promotion_count;
    }

    public void setPromotion_count(int promotion_count) {
        this.promotion_count = promotion_count;
    }

    public String getStart_date() {
        return start_date;
    }

    public void setStart_date(String start_date) {
        this.start_date = start_date;
    }

    public String getEnd_date() {
        return end_date;
    }

    public void setEnd_date(String end_date) {
        this.end_date = end_date;
    }

    public String getShopping_center() {
        return shopping_center;
    }

    public void setShopping_center(String shopping_center) {
        this.shopping_center = shopping_center;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getOt_weekdays() {
        return ot_weekdays;
    }

    public void setOt_weekdays(String ot_weekdays) {
        this.ot_weekdays = ot_weekdays;
    }

    public String getOt_sundays() {
        return ot_sundays;
    }

    public void setOt_sundays(String ot_sundays) {
        this.ot_sundays = ot_sundays;
    }

    public String getOt_saturdays() {
        return ot_saturdays;
    }

    public void setOt_saturdays(String ot_saturdays) {
        this.ot_saturdays = ot_saturdays;
    }

    public String getOt_public_holidays() {
        return ot_public_holidays;
    }

    public void setOt_public_holidays(String ot_public_holidays) {
        this.ot_public_holidays = ot_public_holidays;
    }

    public int getParent_id() {
        return parent_id;
    }

    public void setParent_id(int parent_id) {
        this.parent_id = parent_id;
    }
}
