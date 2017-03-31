package ru.timuruktus.febree.LocalPart;

import com.orm.SugarRecord;

public class Task extends SugarRecord {

    private int level;
    private String text;
    private boolean passed;
    private int points;
    private int uniqueId;
    private int version;

    public Task(int level, String text, boolean passed, int points, int uniqueId, int version) {
        this.level = level;
        this.text = text;
        this.passed = passed;
        this.points = points;
        this.uniqueId = uniqueId;
        this.version = version;
    }

    public Task() {
    }

    public int getUniqueId() {
        return uniqueId;
    }

    public void setUniqueId(int uniqueId) {
        this.uniqueId = uniqueId;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public boolean isPassed() {
        return passed;
    }

    public void setPassed(boolean passed) {
        this.passed = passed;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }


}
