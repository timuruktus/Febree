package ru.timuruktus.febree.LocalPart;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;


public class StepCreator {

    public final static int BLOCK_COUNT = 2;
    public final static int BLOCK_STEPS_COUNT = 5;
    public final static int TASKS_IN_STEPS = 5;

    public static void setFirstLaunchSteps(Context context){
        String[][] allPathsToIcons = initAllPaths();
        for(int block = 0; block < BLOCK_COUNT; block++){
            String[] namesArray = getNamesByBlockNumber(context, block);
            for(int stepInBlock = 0; stepInBlock < BLOCK_STEPS_COUNT; stepInBlock++){
                if(block == 0 && stepInBlock == 0){
                    Step firstStep = new Step(block, TASKS_IN_STEPS, 0, Step.STATUS_IN_PROGRESS, stepInBlock,
                            block * stepInBlock, namesArray[stepInBlock], allPathsToIcons[block][stepInBlock], false);
                    firstStep.save();
                }else{
                    Step step = new Step(block, TASKS_IN_STEPS, 0, Step.STATUS_CLOSED, stepInBlock,
                            block * stepInBlock, namesArray[stepInBlock],  allPathsToIcons[block][stepInBlock], false);
                    step.save();
                }
            }
        }
    }

    private static String[][] initAllPaths(){
        String[][] allPathsToIcons = new String[BLOCK_COUNT][BLOCK_STEPS_COUNT];
        allPathsToIcons[0][0] = "ic_task_start";
        allPathsToIcons[0][1] = "ic_task_confirm";
        allPathsToIcons[0][2] = "ic_task_speaking";
        allPathsToIcons[0][3] = "ic_task_confirm";
        allPathsToIcons[0][4] = "ic_task_society";
        allPathsToIcons[1][0] = "ic_task_rounds";
        allPathsToIcons[1][1] = "ic_task_21";
        allPathsToIcons[1][2] = "ic_task_superman";
        allPathsToIcons[1][3] = "ic_task_target";
        allPathsToIcons[1][4] = "ic_task_zozh";
        return  allPathsToIcons;
    }

    @NonNull
    private static String[] getNamesByBlockNumber(Context context, int blockNum){
        int arrayPath = context.getResources().getIdentifier( "block_" + blockNum + "_names", "array",
                context.getPackageName());
        return context.getResources().getStringArray(arrayPath);
    }

}
