package de.interaapps.pastefy.model.database;

import org.javawebstack.orm.Model;
import org.javawebstack.orm.Repo;
import org.javawebstack.orm.annotation.*;
import org.javawebstack.webutils.util.RandomUtil;

import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

@Dates
@Table("pastes")
public class Paste extends Model {
    @Column
    private int id;

    @Column(size = 8)
    @Searchable
    @Filterable
    private String key;

    @Column
    @Searchable
    private String title;

    @Column(size = 16777215)
    @Searchable
    private String content;

    @Column(size = 8)
    @Filterable
    private String userId;


    @Column(size = 8)
    @Filterable
    private String forkedFrom;

    @Column
    @Filterable
    private boolean encrypted = false;

    @Column(size = 8)
    @Searchable
    @Filterable
    public String folder;

    @Column
    @Searchable
    @Filterable
    private Type type = Type.PASTE;

    @Column
    @Filterable
    private Visibility visibility;

    @Column
    public Timestamp expireAt = null;

    @Column
    @Searchable
    public Timestamp createdAt;

    @Column
    public Timestamp updatedAt;

    @Column
    private StorageType storageType = StorageType.DATABASE;

    public Paste() {
        key = RandomUtil.string(8);
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String content) {
        this.title = content;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getId() {
        return id;
    }

    public Visibility getVisibility() {
        return visibility == null ? Visibility.UNLISTED : visibility;
    }


    public boolean isPublic() {
        return visibility == Visibility.PUBLIC;
    }

    public boolean isPrivate() {
        return visibility == Visibility.PRIVATE;
    }

    public String getForkedFrom() {
        return forkedFrom;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getKey() {
        return key;
    }

    public boolean isEncrypted() {
        return encrypted;
    }

    public void setEncrypted(boolean encrypted) {
        this.encrypted = encrypted;
    }

    public Folder getFolder() {
        return Repo.get(Folder.class).where("key", folder).first();
    }

    public String getFolderId() {
        return folder;
    }

    public void setFolder(String folder) {
        this.folder = folder;
    }

    public void setFolder(Folder folder) {
        this.folder = folder.getKey();
    }

    public void setType(Type type) {
        this.type = type;
    }


    public void setExpireAt(String timeString) {
        this.expireAt = Timestamp.valueOf(timeString);
    }

    public Type getType() {
        return type;
    }

    public List<String> getTags() {
        return Repo.get(PasteTag.class).where("paste", key).get().stream().map(t -> t.tag).collect(Collectors.toList());
    }

    public void setForkedFrom(String forkedFrom) {
        this.forkedFrom = forkedFrom;
    }

    public void setVisibility(Visibility visibility) {
        this.visibility = visibility;
    }

    @Override
    public void save() {
        super.save();
    }

    public enum Type {
        PASTE,
        MULTI_PASTE
    }

    public enum Visibility {
        UNLISTED,
        PUBLIC,
        PRIVATE
    }

    public enum StorageType {
        DATABASE,
        S3,
        HTTP
    }
}
