public class EBook extends Book {
    private String downloadLink;

    public EBook(int id, String title, String author, String downloadLink) {
    super(id, title, author); // Call the constructor of Book
    this.downloadLink = downloadLink;
    }

    public void getDetails() {
        System.out.println("ID: " + id + ", Title: " + title + ", Author: " + author + ", URL: " + downloadLink);
    }

        public String getdownloadLink() {
        return downloadLink;
    }
}