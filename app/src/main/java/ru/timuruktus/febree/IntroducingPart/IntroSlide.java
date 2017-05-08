package ru.timuruktus.febree.IntroducingPart;

import android.content.Context;
import android.content.res.Resources;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import ru.timuruktus.febree.MainPart.MainActivity;
import ru.timuruktus.febree.R;
import ru.timuruktus.febree.MainPart.MainActivity.*;


public class IntroSlide {

    int image;
    String text;


    public IntroSlide(int image, String text) {
        this.image = image;
        this.text = text;
    }

    private ArrayList<IntroSlide> slides;

    public static ArrayList<IntroSlide> initializeData(Context context){
        ArrayList<IntroSlide> slides = new ArrayList<>();
        slides.add(new IntroSlide(R.drawable.intro_2, context.getString(R.string.intro_1_text)));
        slides.add(new IntroSlide(R.drawable.intro_3, context.getString(R.string.intro_2_text)));
        slides.add(new IntroSlide(R.drawable.intro_5, context.getString(R.string.intro_3_text)));
        slides.add(new IntroSlide(R.drawable.intro_4, context.getString(R.string.intro_4_text)));
        return slides;
    }
}
