package ru.timuruktus.febree.LocalPart;

import com.orm.SugarRecord;

import java.io.Serializable;

public class Task extends SugarRecord implements Serializable {

    public int getBlock() {
        return block;
    }

    public void setBlock(int block) {
        this.block = block;
    }

    public int getStep() {
        return step;
    }

    public void setStep(int step) {
        this.step = step;
    }

    public int getNumInStep() {
        return numInStep;
    }

    public void setNumInStep(int numInStep) {
        this.numInStep = numInStep;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public int getUniqueId() {
        return uniqueId;
    }

    public void setUniqueId(int uniqueId) {
        this.uniqueId = uniqueId;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    private int block;
    private int step;
    private int numInStep;
    private String text;
    private String title;
    private boolean passed;
    private int points;
    private int uniqueId;
    private int version;


    public Task() {
    }

    public Task(int block, int step, int numInStep, String text, String title, boolean passed, int points, int uniqueId, int version) {
        this.block = block;
        this.step = step;
        this.numInStep = numInStep;
        this.text = text;
        this.title = title;
        this.passed = passed;
        this.points = points;
        this.uniqueId = uniqueId;
        this.version = version;
    }
}
