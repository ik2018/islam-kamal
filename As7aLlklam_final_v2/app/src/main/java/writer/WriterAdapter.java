package writer;

/**
 * Created by islam kamal on 2/26/2017.
 */
public class WriterAdapter {
    public String tweet;
    public String writer_name;
    public WriterAdapter(String tweet,String writer_name){
        this.tweet=tweet;
        this.writer_name=writer_name;
    }

    public String getTweet() {
        return tweet;
    }

    public void setTweet(String tweet) {
        this.tweet = tweet;
    }

    public String getWriter_name() {
        return writer_name;
    }

    public void setWriter_name(String writer_name) {
        this.writer_name = writer_name;
    }
}
