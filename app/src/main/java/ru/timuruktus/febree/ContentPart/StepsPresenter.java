package ru.timuruktus.febree.ContentPart;

import android.util.Log;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.backendless.persistence.DataQueryBuilder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import ru.timuruktus.febree.ContentPart.Interfaces.BaseStepsFragment;
import ru.timuruktus.febree.ContentPart.Interfaces.BaseStepsModel;
import ru.timuruktus.febree.ContentPart.Interfaces.BaseStepsPresenter;
import ru.timuruktus.febree.LocalPart.Settings;
import ru.timuruktus.febree.LocalPart.Step;
import ru.timuruktus.febree.LocalPart.Task;
import ru.timuruktus.febree.MainPart.MainActivity;
import ru.timuruktus.febree.MainPart.MainPresenter;
import ru.timuruktus.febree.ProjectUtils.CustomDialog1;
import ru.timuruktus.febree.ProjectUtils.CustomLoadingDialog;
import ru.timuruktus.febree.R;
import ru.timuruktus.febree.TaskPart.TasksFragment;
import rx.Observable;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static android.widget.Toast.LENGTH_SHORT;
import static ru.timuruktus.febree.BaseModel.BUTTON_TEXT;
import static ru.timuruktus.febree.BaseModel.FIRST_TEXT;
import static ru.timuruktus.febree.BaseModel.SECOND_TEXT;
import static ru.timuruktus.febree.BaseModel.SECOND_TEXT_COLOR;
import static ru.timuruktus.febree.BaseModel.TITLE_COLOR;
import static ru.timuruktus.febree.BaseModel.TITLE_TEXT;
import static ru.timuruktus.febree.MainPart.MainPresenter.ADD_TO_BACKSTACK;
import static ru.timuruktus.febree.MainPart.MainPresenter.HIDE_TOOLBAR;
import static ru.timuruktus.febree.MainPart.MainPresenter.REFRESH;
import static ru.timuruktus.febree.ProjectUtils.CustomLoadingDialog.DEFAULT_TEXT_OFFSET_APPEARANCE;
import static ru.timuruktus.febree.TaskPart.TasksFragment.ARGS_BLOCK;
import static ru.timuruktus.febree.TaskPart.TasksFragment.ARGS_STEP;

public class StepsPresenter implements BaseStepsPresenter {

    private BaseStepsFragment view;
    private BaseStepsModel model;
    private int offset = 0;
    private ArrayList<Task> finalTasks = null;
    private static final int WAITING_TEXT_OFFSET_APPEARANCE = 5000;

    public StepsPresenter(BaseStepsFragment view){
        this.view = view;
        model = new StepsModel();
    }

    public void onCreate(){
        configureLevelBar();
        addSubscription(model.stepsQuery()
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                step -> view.setImageAndText(step),
                                throwable -> Log.d("mytag", "StepsFragment.onCreateView() error =" + throwable)));
    }

    private void configureLevelBar(){
        int progress = (int) (Settings.getPoints() - (Settings.getLevel() - 1) * 100);
        view.setLevelBarProgress(progress);
    }

    @Override
    public void onStepClick(int blockNum, int stepNum) {
        Step step = model.getStepByNum(blockNum, stepNum);
        if(step.getStatus() == Step.STATUS_CLOSED){
            Toast.makeText(MainActivity.getInstance(), R.string.step_is_unavailable, LENGTH_SHORT).show();
            return;
        }
        if(blockNum == 0 && stepNum == 0 && Settings.isFirstOpenedStep()){
            openWelcomeDialog();
        }else if(step.isDownloaded()){
            openStep(blockNum, stepNum);
        }else if(!step.isDownloaded()){
            openLoadingDialog(blockNum, stepNum, step);
        }
    }

    private void openWelcomeDialog(){
        HashMap<String, String> textForDialog = model.getTextFor1Dialog();
        HashMap<String, Integer> colorsForDialog = model.getColorsFor1Dialog();
        buildDialogForFirstStep(textForDialog, colorsForDialog);
    }

    private void openStep(int blockNum, int stepNum){
        HashMap<String, Integer> info = new HashMap<>();
        info.put(ARGS_BLOCK, blockNum);
        info.put(ARGS_STEP, stepNum);
        MainPresenter.changeFragmentWithInfo(new TasksFragment(), ADD_TO_BACKSTACK, REFRESH,
                HIDE_TOOLBAR, info);
    }

    private void openLoadingDialog(int blockNum, int stepNum, Step step){
        CustomLoadingDialog loadingDialog = new CustomLoadingDialog();
        HashMap<String, String> textForDialog = model.getTextForLoadingDialog();
        HashMap<String, Integer> colorsForDialog = model.getColorsForLoadingDialog();
        loadingDialog.buildDialog(MainActivity.getInstance())
                .setFirstText(textForDialog.get(FIRST_TEXT))
                .setTitle(textForDialog.get(TITLE_TEXT))
                .setTitleColor(colorsForDialog.get(TITLE_COLOR))
                .setDismissOnClick(false)
                .addTextAfterPause(textForDialog.get(SECOND_TEXT), DEFAULT_TEXT_OFFSET_APPEARANCE)
                .show();
        downloadTasksInStep(getLoadingListener(loadingDialog, step, blockNum, stepNum), blockNum, stepNum);
    }

    private Observer<Task> getLoadingListener(CustomLoadingDialog loadingDialog, Step step,
                                              int blockNum, int stepNum){
        return new Observer<Task>() {
            @Override
            public void onCompleted() {
                step.setDownloaded(true);
                step.save();
                loadingDialog.dismiss();
                onStepClick(blockNum, stepNum);
            }

            @Override
            public void onError(Throwable e) {
                Log.d("mytag", "Error while downloading tasks in step" + e.getMessage());
            }

            @Override
            public void onNext(Task task) {
                task.save();
            }
        };
    }




    public void buildDialogForFirstStep(HashMap<String, String> textForDialog,
                                        HashMap<String, Integer> colorsForDialog){
        CustomDialog1 dialog = new CustomDialog1();
        dialog.buildDialog(MainActivity.getInstance())
                .setTitle(textForDialog.get(TITLE_TEXT))
                .setFirstText(textForDialog.get(FIRST_TEXT))
                .setSecondText(textForDialog.get(SECOND_TEXT))
                .setButtonText(textForDialog.get(BUTTON_TEXT))
                .setTitleColor(colorsForDialog.get(TITLE_COLOR))
                .setSecondTextColor(colorsForDialog.get(SECOND_TEXT_COLOR))
                .setDismissOnClick(true)
                .setOnClickListener(v -> {
                    Settings.setFirstOpenedStep(false);
                    onStepClick(0, 0);
                    dialog.dismiss();
                })
                .show();
    }

    @Override
    public void onDestroy() {
        allSubscriptions.unsubscribe();
        Log.d("mytag", "onDestroy() in StepsPresenter");
    }

    private void downloadTasksInStep(Observer<Task> observer, int blockNum, int stepNum){
        if(finalTasks == null) {
            finalTasks = new ArrayList<>();
        }
        String whereClause = "block = " + blockNum + " and step = " + stepNum;
        Log.d("mytag", "WhereClause in steps downloading = " + whereClause);
        DataQueryBuilder queryBuilder = DataQueryBuilder.create();
        queryBuilder.setWhereClause(whereClause);
        queryBuilder.setPageSize(100);
        queryBuilder.setOffset(offset);
        Backendless.Persistence.of(Task.class).find(queryBuilder,
                new AsyncCallback<List<Task>>(){
                    @Override
                    public void handleResponse(List<Task> foundTasks) {
                        if(foundTasks == null) Log.d("mytag", "Found tasks is null");
                        int size = foundTasks.size();
                        Log.d("mytag", "Found tasks size = " + size);
                        ArrayList<Task> tasks = (ArrayList<Task>) foundTasks;
                        if(size > 0){
                            offset += tasks.size();
                            finalTasks.addAll(tasks);
                            downloadTasksInStep(observer, blockNum, stepNum);
                        }else {
                            offset = 0;
                            Observable.from(finalTasks)
                                    .subscribeOn(Schedulers.io())
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .subscribe(observer);
                        }
                    }
                    @Override
                    public void handleFault(BackendlessFault fault) {
                        observer.onError(new DownloadError(fault.getCode() + fault.getMessage()));
                    }
                });
    }

    public void addSubscription(Subscription subscription){
        allSubscriptions.add(subscription);

    }



}
