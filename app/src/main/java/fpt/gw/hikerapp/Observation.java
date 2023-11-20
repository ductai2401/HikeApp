package fpt.gw.hikerapp;

public class Observation {
    private int Id;
    private int HikeId;
    private String Type;
    private String Description;
    private String Date;
    private String Comment;

    public Observation(int id, int hikeId, String type, String description, String date, String comment) {
        Id = id;
        HikeId = hikeId;
        Type = type;
        Description = description;
        Date = date;
        Comment = comment;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public int getHikeId() {
        return HikeId;
    }

    public void setHikeId(int hikeId) {
        HikeId = hikeId;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getComment() {
        return Comment;
    }

    public void setComment(String comment) {
        Comment = comment;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }
}
