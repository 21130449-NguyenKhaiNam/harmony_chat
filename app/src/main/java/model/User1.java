package model;

public class User1 {
    private String id;
    private String imageID;
    private String name;

    public User1(String id, String imageID, String name) {
        this.id = id;
        this.imageID = imageID;
        this.name = name;
    }

    public String getImageID() {
        return imageID;
    }

    public void setImageID(String imageID) {
        this.imageID = imageID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "User1{" +
                "imageID=" + imageID +
                ", name='" + name + '\'' +
                '}';
    }


}
