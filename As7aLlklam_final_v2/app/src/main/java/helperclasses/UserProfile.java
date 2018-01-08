package helperclasses;

/**
 * Created by islam kamal on 2/27/2017.
 */
public class UserProfile {
    private String name;
    private String email;
    private String picture_path;

    public UserProfile() {
    }

    public UserProfile(String name,String email ,String picture){
        this.name=name;
        this.email=email;
        this.picture_path=picture;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPicture() {
        return picture_path;
    }

    public void setPicture(String picture) {
        this.picture_path = picture;
    }
}
