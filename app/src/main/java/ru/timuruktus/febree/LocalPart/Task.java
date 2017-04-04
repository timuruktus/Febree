package ru.timuruktus.febree.LocalPart;

import com.orm.SugarRecord;

public class Task extends SugarRecord {

    private int level;
    private String text;
    private boolean passed;
    private long points;
    private int uniqueId;
    private int version;
    private boolean skipped;

    public Task() {
    }

    public Task(int level, String text, boolean passed, long points, int uniqueId, int version, boolean skipped) {
        this.level = level;
        this.text = text;
        this.passed = passed;
        this.points = points;
        this.uniqueId = uniqueId;
        this.version = version;
        this.skipped = skipped;
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

    public long getPoints() {
        return points;
    }

    public void setPoints(long points) {
        this.points = points;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public boolean isSkipped() {
        return skipped;
    }

    public void setSkipped(boolean skipped) {
        this.skipped = skipped;
    }



}
