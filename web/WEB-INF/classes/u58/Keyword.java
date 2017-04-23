package u58;

/**
 * Created by Gradey Cullins on 3/15/17.
 */
public class Keyword {
    public int kid; // primary key
    public int tid; // foreign key -> th
    public String word;
    public String language;

    public Keyword(int kid, int tid, String word, String language) {
        this.kid = kid;
        this.tid = tid;
        this.word = word;
        this.language = language;
    }
}
