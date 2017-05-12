package ru.timuruktus.febree.LocalPart;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import java.util.ArrayList;

import ru.timuruktus.febree.R;

import static ru.timuruktus.febree.LocalPart.StepCreator.StepTasksCount.*;

public class StepCreator {

    public final static int BLOCK_COUNT = 2;
    public final static int BLOCK_TASKS_COUNT = 5;

    public static void setFirstLaunchSteps(Context context){
        for(int block = 1; block <= BLOCK_COUNT; block++){
            String[] namesArray = getNamesByBlockNumber(context, block);
            for(int taskInBlock = 1; taskInBlock <= BLOCK_TASKS_COUNT; taskInBlock++){
                Log.d("mytag", "StepCreator.setFirstLaunchSteps() Level 2");
                Log.d("mytag", "StepCreator.setFirstLaunchSteps() taskInBlock = " + taskInBlock);
                if(block == 1 && taskInBlock == 1){
                    Step firstStep = new Step(block, TASKS_IN_1_STEP, 0, Step.STATUS_IN_PROGRESS, taskInBlock,
                            block * taskInBlock, namesArray[taskInBlock - 1]);
                    firstStep.save();
                    Log.d("mytag", "StepCreator.setFirstLaunchSteps() first Step Saved");
                }else{
                    Step step = new Step(block, TASKS_OTHER_STEPS, 0, Step.STATUS_CLOSED, taskInBlock,
                            block * taskInBlock, namesArray[taskInBlock - 1]);
                    step.save();
                    Log.d("mytag", "StepCreator.setFirstLaunchSteps() Step saved" );
                }
            }
        }
    }

    @NonNull
    private static String[] getNamesByBlockNumber(Context context, int blockNum){
        int arrayPath = context.getResources().getIdentifier( "block_" + blockNum + "_names", "array",
                context.getPackageName());
        return context.getResources().getStringArray(arrayPath);
    }

    public class StepTasksCount{
        public final static int TASKS_IN_1_STEP = 10;
        public final static int TASKS_OTHER_STEPS = 5;
    }

}
