package capstoneproject.androidnanodegree.com.cochelper.models;


public class Snippet {
    private String title;
    private String description;
    public Thumbnails getThumbnails() {
        return thumbnails;
    }

    public String getDescription() {
        return description;
    }

    public void setThumbnails(Thumbnails thumbnails) {
        this.thumbnails = thumbnails;
    }

    private Thumbnails thumbnails;
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
