package com.andres_k.components.resourceComponent.resources.factory;

import com.andres_k.components.gameComponents.animations.AnimatorController;
import com.andres_k.components.gameComponents.animations.EAnimation;
import com.andres_k.components.resourceComponent.resources.ESprites;
import com.andres_k.utils.configs.ConfigPath;
import com.andres_k.utils.configs.GameConfig;
import com.andres_k.utils.tools.FilesTools;
import org.codehaus.jettison.json.JSONException;
import org.newdawn.slick.Animation;
import org.newdawn.slick.SlickException;

/**
 * Created by andres_k on 20/01/2016.
 */
public class AnimatorBackgroundFactory extends AnimatorFactory {
    @Override
    public AnimatorController getAnimator(ESprites index) throws SlickException, JSONException, NoSuchMethodException {
        if (index.getIndex() == ESprites.SCREEN_BACKGROUND.getIndex()) {
            return this.getMenuBackgroundAnimator(index);
        } else if (index.getIndex() == ESprites.MAP_BACKGROUND.getIndex()) {
            return this.getMapBackgroundAnimator(index);
        }
        return null;
    }

    public AnimatorController getMenuBackgroundAnimator(ESprites index) throws SlickException {
        AnimatorController animatorController = new AnimatorController();

        if (index == ESprites.LOAD_SCREEN) {
            animatorController.addAnimation(EAnimation.IDLE, 0, AnimationFactory.createStaticUniqueFrame(ConfigPath.image_background + "/backgroundLoad.png"));
        } else if (index == ESprites.LOGO) {
            animatorController.addAnimation(EAnimation.IDLE, 0, AnimationFactory.createStaticUniqueFrame(ConfigPath.image_background + "/logo.png"));
        } else if (index == ESprites.HOME_SCREEN) {
            animatorController.addAnimation(EAnimation.IDLE, 0, AnimationFactory.createStaticUniqueFrame(ConfigPath.image_background + "/backgroundHome.png"));
        }
        return animatorController;
    }

    public AnimatorController getMapBackgroundAnimator(ESprites index) throws SlickException, JSONException {
        AnimatorController animatorController = new AnimatorController();

        if (index == ESprites.MAP_1) {
            animatorController.addAnimation(EAnimation.IDLE, 0, AnimationFactory.createStaticUniqueFrame(ConfigPath.image_map + "/map1.png"));
            animatorController.addCollision(EAnimation.IDLE, 0, FilesTools.readInput(getClass().getClassLoader().getResourceAsStream(ConfigPath.jsonCollision + "/map/map1.json")));
            animatorController.addAnimation(EAnimation.IDLE, 1, AnimationFactory.createStaticUniqueFrame(ConfigPath.image_map + "/map1.png"));
            animatorController.addCollision(EAnimation.IDLE, 1, FilesTools.readInput(getClass().getClassLoader().getResourceAsStream(ConfigPath.jsonCollision + "/map/map1.json")));
        } else if (index == ESprites.BACKGROUND_BLOCK_1) {
            animatorController.addAnimation(EAnimation.IDLE, 0, AnimationFactory.createStaticUniqueFrame(ConfigPath.image_map + "/backgroundBlock_1.png"));
        }
        return animatorController;
    }
}
