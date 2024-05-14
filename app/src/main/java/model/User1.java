package model;

public class User1 {
    private int imageID;
    private String name;

    public User1(int imageID, String name) {
        this.imageID = imageID;
        this.name = name;
    }

    public User1() {
    }

    public int getImageID() {
        return imageID;
    }

    public void setImageID(int imageID) {
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
