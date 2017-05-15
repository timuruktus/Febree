package ru.timuruktus.febree.LocalPart;

import android.content.Context;
import android.widget.ImageView;

import com.orm.SugarRecord;

import static weborb.util.ThreadContext.context;

public class Step extends SugarRecord {

    public final static int STATUS_CLOSED = 0;
    public final static int STATUS_IN_PROGRESS = 1;
    public final static int STATUS_COMPLETED = 2;

    public Step() {
    }

    public Step(int block, int numOfTasks, int completedTasks, int status, int idInBlock,
                String name) {
        this.block = block;
        this.numOfTasks = numOfTasks;
        this.completedTasks = completedTasks;
        this.status = status;
        this.idInStep = idInBlock;
        this.name = name;
    }

    public Step(int block, int numOfTasks, int completedTasks, int status, int idInStep,
                int imageResId, String name) {
        this.block = block;
        this.numOfTasks = numOfTasks;
        this.completedTasks = completedTasks;
        this.status = status;
        this.idInStep = idInStep;
        this.imageResId = imageResId;
        this.name = name;
    }

    public Step(int block, int numOfTasks, int completedTasks, int status, int idInStep, int imageResId, String name, String path) {
        this.block = block;
        this.numOfTasks = numOfTasks;
        this.completedTasks = completedTasks;
        this.status = status;
        this.idInStep = idInStep;
        this.imageResId = imageResId;
        this.name = name;
        this.path = path;
    }

    public int getBlock() {
        return block;
    }

    public void setBlock(int block) {
        this.block = block;
    }

    public int getNumOfTasks() {
        return numOfTasks;
    }

    public void setNumOfTasks(int numOfTasks) {
        this.numOfTasks = numOfTasks;
    }

    public int getCompletedTasks() {
        return completedTasks;
    }

    public void setCompletedTasks(int completedTasks) {
        this.completedTasks = completedTasks;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getIdInBlock() {
        return idInStep;
    }

    public void setIdInBlock(int idInStep) {
        this.idInStep = idInStep;
    }

    public int getImageResId() {
        return imageResId;
    }

    public void setImageResId(int imageResId) {
        this.imageResId = imageResId;
    }

    public int getFullPath(Context context){
        String endOfPath;
        if(status == STATUS_COMPLETED){
            endOfPath = "_gold";
        }else if(status == STATUS_IN_PROGRESS){
            endOfPath = "_blue";
        }else{
            endOfPath = "_gray";
        }
        return context.getResources().getIdentifier(path + endOfPath, "drawable", context.getPackageName());
    }



    private int block;
    private int numOfTasks;
    private int completedTasks;
    private int status;
    private int idInStep;
    private int imageResId;
    private String name;
    private String path;

}
