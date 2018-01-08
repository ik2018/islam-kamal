package classfication.fragments;

/**
 * Created by islam kamal on 2/26/2017.
 */
public class CharactersAdapter {
    public int id;
    public String name;
    public int picture;
    public CharactersAdapter(int id,String name,int picture){
        this.id=id;
        this.name=name;
        this.picture=picture;
    }

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

    public int getPicture() {
        return picture;
    }

    public void setPicture(int picture) {
        this.picture = picture;
    }
}
