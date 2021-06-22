package ma.eat.Model;

import java.io.Serializable;

public class RestaurantModel implements Serializable {



    private String name;
    private String tags;
    private String services;
    private boolean verified;
    private String location;
    private String city;
    private String phoneNumber;
    private String mainPicture;
    private String[] menuPictures;
    private float review;
    private int reviewCount;


    public RestaurantModel(String name, String tags, String services, boolean verified, String location,
                           String city, String phoneNumber, String mainPicture, String[] menuPictures,
                           float review, int reviewCount){

        this.name = name;
        this.tags = tags;
        this.services = services;
        this.verified = verified;
        this.location = location;
        this.city = city;
        this.phoneNumber = phoneNumber;
        this.mainPicture = mainPicture;
        this.menuPictures = menuPictures;
        this.review = review;
        this.reviewCount = reviewCount;

    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getServices() {
        return services;
    }

    public void setServices(String services) {
        this.services = services;
    }

    public boolean isVerified() {
        return verified;
    }

    public void setVerified(boolean verified) {
        this.verified = verified;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getMainPicture() {
        return mainPicture;
    }

    public void setMainPicture(String mainPicture) {
        this.mainPicture = mainPicture;
    }

    public String[] getMenuPictures() {
        return menuPictures;
    }

    public void setMenuPictures(String[] menuPictures) {
        this.menuPictures = menuPictures;
    }

    public float getReview() {
        return review;
    }

    public void setReview(float review) {
        this.review = review;
    }

    public int getReviewCount() {
        return reviewCount;
    }

    public void setReviewCount(int reviewCount) {
        this.reviewCount = reviewCount;
    }
}
