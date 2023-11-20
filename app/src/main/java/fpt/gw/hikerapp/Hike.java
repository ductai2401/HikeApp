package fpt.gw.hikerapp;

public class Hike {

    private int IdHike;
    private String HikeName;
    private String Location;
    private String LengthHike;
    private String DateStart;
    private String DateEnd;
    private String Park;
    private String Difficulty;
    private String Description;

    public Hike(int idHike, String hikeName, String location, String lengthHike,String difficulty, String dateStart, String dateEnd, String park, String description) {
        this.IdHike = idHike;
        this.HikeName = hikeName;
        this.Location = location;
        this.LengthHike = lengthHike;
        this.Difficulty = difficulty;
        this.DateStart = dateStart;
        this.DateEnd = dateEnd;
        this.Park = park;
        this.Description = description;
    }


    public int getIdHike() {
        return IdHike;
    }

    public void setIdHike(int idHike) {
        IdHike = idHike;
    }

    public String getHikeName() {
        return HikeName;
    }

    public void setTripName(String hikeName) {
        this.HikeName = hikeName;
    }

    public String getLocation() {
        return Location;
    }

    public void setLocation(String location) {
        Location = location;
    }

    public String getLengthHike() {
        return LengthHike;
    }

    public void setLengthHike(String lengthHike) {
        LengthHike = lengthHike;
    }

    public String getDifficulty() { return Difficulty; }

    public void setDifficulty(String difficulty) { Difficulty = difficulty; }

    public String getDateStart() {
        return DateStart;
    }

    public void setDateStart(String dateStart) {
        DateStart = dateStart;
    }

    public String getDateEnd() {
        return DateEnd;
    }

    public void setDateEnd(String dateEnd) {
        DateEnd = dateEnd;
    }

    public String getPark() {
        return Park;
    }

    public void setPark(String park) {
        Park = park;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }


}
