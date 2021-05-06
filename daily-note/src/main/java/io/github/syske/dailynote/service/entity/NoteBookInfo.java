package io.github.syske.dailynote.service.entity;

/**
 * @program: daily-note
 * @description: 读书笔记entity
 * @author: syske
 * @date: 2021-05-02 8:52
 */
public class NoteBookInfo {
    /**
     * id
     */
    private Long id;
    /**
     * 书名
     */
    private String bookTitle;
    /**
     * 作者
     */
    private String author;
    /**
     * 笔记内容
     */
    private String noteContent;
    /**
     * 配图地址
     */
    private String bannerPicUrl;

    public NoteBookInfo() {
    }

    public NoteBookInfo(Long id, String bookTitle, String author, String noteContent, String bannerPicUrl) {
        this.id = id;
        this.bookTitle = bookTitle;
        this.author = author;
        this.noteContent = noteContent;
        this.bannerPicUrl = bannerPicUrl;
    }

    public Long getId() {
        return id;
    }

    public NoteBookInfo setId(Long id) {
        this.id = id;
        return this;
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public NoteBookInfo setBookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
        return this;
    }

    public String getAuthor() {
        return author;
    }

    public NoteBookInfo setAuthor(String author) {
        this.author = author;
        return this;
    }

    public String getNoteContent() {
        return noteContent;
    }

    public NoteBookInfo setNoteContent(String noteContent) {
        this.noteContent = noteContent;
        return this;
    }

    public String getBannerPicUrl() {
        return bannerPicUrl;
    }

    public NoteBookInfo setBannerPicUrl(String bannerPicUrl) {
        this.bannerPicUrl = bannerPicUrl;
        return this;
    }

    @Override
    public String toString() {
        return "NoteBookInfo{" +
                "id=" + id +
                ", bookTitle='" + bookTitle + '\'' +
                ", author='" + author + '\'' +
                ", noteContent='" + noteContent + '\'' +
                ", bannerPicUrl='" + bannerPicUrl + '\'' +
                '}';
    }
}
