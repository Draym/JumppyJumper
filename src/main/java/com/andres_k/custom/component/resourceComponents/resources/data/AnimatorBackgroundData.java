package com.andres_k.custom.component.resourceComponents.resources.data;

import com.andres_k.gameToolsLib.components.gameComponent.animations.AnimatorController;
import com.andres_k.custom.component.resourceComponents.resources.ESprites;
import com.andres_k.custom.component.resourceComponents.resources.factory.AnimatorBackgroundFactory;
import com.andres_k.gameToolsLib.components.resourceComponents.resources.data.DataManager;
import com.andres_k.gameToolsLib.components.resourceComponents.resources.factory.AnimatorFactory;
import com.andres_k.custom.component.graphicComponents.background.EBackground;
import com.andres_k.gameToolsLib.utils.stockage.Pair;
import org.codehaus.jettison.json.JSONException;
import org.newdawn.slick.SlickException;

import java.util.HashMap;

/**
 * Created by andres_k on 13/03/2015.
 */
public class AnimatorBackgroundData extends DataManager {
    private AnimatorFactory animatorFactory;
    private HashMap<EBackground, AnimatorController> animator;


    public AnimatorBackgroundData() {
        this.animatorFactory = new AnimatorBackgroundFactory();
        this.animator = new HashMap<>();
    }

    @Override
    public void prerequisite() throws NoSuchMethodException, SlickException, JSONException {
        this.initialiseMethods();
        this.initLoadScreen();
    }

    @Override
    protected void initialiseMethods() throws NoSuchMethodException {
        this.methods.clear();
        this.methods.add(new Pair<>(false, this.getClass().getMethod("initHomeScreen")));
        this.methods.add(new Pair<>(false, this.getClass().getMethod("initFinalScreen")));
        this.methods.add(new Pair<>(false, this.getClass().getMethod("initMaps")));
    }

    @Override
    public String success() {
        return "> Background complete";
    }

    public void initLoadScreen() throws NoSuchMethodException, SlickException, JSONException {
        this.addAnimator(this.animatorFactory.getAnimator(ESprites.LOAD_SCREEN), EBackground.LOAD_SCREEN);
        this.addAnimator(this.animatorFactory.getAnimator(ESprites.LOGO), EBackground.LOGO);
    }

    public void initHomeScreen() throws NoSuchMethodException, SlickException, JSONException {
        this.addAnimator(this.animatorFactory.getAnimator(ESprites.HOME_SCREEN), EBackground.HOME_SCREEN);
    }

    public void initFinalScreen() throws NoSuchMethodException, SlickException, JSONException {
        this.addAnimator(this.animatorFactory.getAnimator(ESprites.FINAL_SCREEN), EBackground.FINAL_SCREEN);
        this.addAnimator(this.animatorFactory.getAnimator(ESprites.FINAL_SCREEN_CLOUD), EBackground.FINAL_SCREEN_CLOUD);
    }

    public void initMaps() throws NoSuchMethodException, SlickException, JSONException {
        this.addAnimator(this.animatorFactory.getAnimator(ESprites.MAP_1), EBackground.MAP_1);
        this.addAnimator(this.animatorFactory.getAnimator(ESprites.BACKGROUND_BLOCK_1), EBackground.BACKGROUND_BLOCK_1);
    }

    private void addAnimator(AnimatorController animatorController, EBackground type) {
        this.animator.put(type, animatorController);
    }


    // GETTERS
    public AnimatorController getAnimator(EBackground index) throws SlickException {
        if (this.animator.containsKey(index)) {
            return new AnimatorController(this.animator.get(index));
        }
        throw new SlickException("[ERROR]: The content of " + index.getValue() + " is missing.");
    }

}

