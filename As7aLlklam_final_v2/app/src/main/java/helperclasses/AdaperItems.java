package helperclasses;

/**
 * Created by islam kamal on 12/1/2016.
 */
public class AdaperItems {
    public String name;
    public String user_id;
    public String picure_path;
    public String tweet_id;
    public String tweet_text;
    public String tweet_picture;
    public String tweet_date;

    public AdaperItems(String user_id,String name,String picure_path,String tweet_id,String tweet_text ,String tweet_picture,String tweet_date){
        this.name=name;
        this.user_id=user_id;
        this.picure_path=picure_path;
        this.tweet_id=tweet_id;
        this.tweet_text=tweet_text;
        this.tweet_picture=tweet_picture;
        this.tweet_date=tweet_date;
    }

}
