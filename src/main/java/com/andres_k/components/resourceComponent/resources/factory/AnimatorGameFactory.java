package com.andres_k.components.resourceComponent.resources.factory;

import com.andres_k.components.gameComponents.animations.AnimatorController;
import com.andres_k.components.gameComponents.animations.EAnimation;
import com.andres_k.components.gameComponents.animations.details.AnimationConfigItem;
import com.andres_k.components.gameComponents.gameObject.GameObject;
import com.andres_k.components.gameComponents.gameObject.commands.actionComponent.players.SlimeActions;
import com.andres_k.components.resourceComponent.resources.ESprites;
import com.andres_k.utils.configs.ConfigPath;
import com.andres_k.utils.tools.FilesTools;
import org.codehaus.jettison.json.JSONException;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

/**
 * Created by andres_k on 13/03/2015.
 */
public class AnimatorGameFactory extends AnimatorFactory {

    @Override
    public AnimatorController getAnimator(ESprites index) throws SlickException, JSONException, NoSuchMethodException {
        if (index.getIndex() == ESprites.PLAYER.getIndex()) {
            if (index == ESprites.SLIME) {
                return this.getSlimeAnimator();
            }
        } else if (index.getIndex() == ESprites.ITEM.getIndex()) {
            return this.getItemAnimator(index);
        }
        return null;
    }

    public AnimatorController getSlimeAnimator() throws SlickException, JSONException, NoSuchMethodException {
        AnimatorController animatorController = new AnimatorController();
        String id = "/player/slime";
        //IDLE
        animatorController.addAnimation(EAnimation.IDLE, 0, AnimationFactory.createAnimation(new SpriteSheet(ConfigPath.image_game + id + "/slimeIdle.png", 64, 64), EAnimation.IDLE.isLoop(), 0, 4, 0, 1, new int[]{200, 200, 200, 200}));
        animatorController.addCollision(EAnimation.IDLE, 0, FilesTools.readInput(getClass().getClassLoader().getResourceAsStream(ConfigPath.jsonCollision + id + "/slimeIdle.json")));
        animatorController.addConfig(EAnimation.IDLE, 0, new AnimationConfigItem(SlimeActions.class.getMethod("idle", GameObject.class), true));

        //FALL
        animatorController.addAnimation(EAnimation.FALL, 0, AnimationFactory.createAnimation(new SpriteSheet(ConfigPath.image_game + id + "/slimeIdle.png", 64, 64), EAnimation.FALL.isLoop(), 0, 4, 0, 1, new int[]{200, 200, 200, 200}));
        animatorController.addCollision(EAnimation.FALL, 0, FilesTools.readInput(getClass().getClassLoader().getResourceAsStream(ConfigPath.jsonCollision + id + "/slimeIdle.json")));
        animatorController.addConfig(EAnimation.FALL, 0, new AnimationConfigItem(SlimeActions.class.getMethod("fall", GameObject.class),  true, EAnimation.RECEIPT));

        //FALL
        animatorController.addAnimation(EAnimation.JUMP, 0, AnimationFactory.createAnimation(new SpriteSheet(ConfigPath.image_game + id + "/slimeIdle.png", 64, 64), EAnimation.JUMP.isLoop(), 0, 4, 0, 1, new int[]{200, 200, 200, 200}));
        animatorController.addCollision(EAnimation.JUMP, 0, FilesTools.readInput(getClass().getClassLoader().getResourceAsStream(ConfigPath.jsonCollision + id + "/slimeIdle.json")));
        animatorController.addConfig(EAnimation.JUMP, 0, new AnimationConfigItem(SlimeActions.class.getMethod("jump", GameObject.class), true, EAnimation.FALL));

        //FALL
        animatorController.addAnimation(EAnimation.RECEIPT, 0, AnimationFactory.createAnimation(new SpriteSheet(ConfigPath.image_game + id + "/slimeIdle.png", 64, 64), EAnimation.RECEIPT.isLoop(), 0, 4, 0, 1, new int[]{200, 200, 200, 200}));
        animatorController.addCollision(EAnimation.RECEIPT, 0, FilesTools.readInput(getClass().getClassLoader().getResourceAsStream(ConfigPath.jsonCollision + id + "/slimeIdle.json")));
        animatorController.addConfig(EAnimation.RECEIPT, 0, new AnimationConfigItem(SlimeActions.class.getMethod("run", GameObject.class), true, EAnimation.RUN));

        //RUN
        animatorController.addAnimation(EAnimation.RUN, 0, AnimationFactory.createAnimation(new SpriteSheet(ConfigPath.image_game + id + "/slimeIdle.png", 64, 64), EAnimation.RUN.isLoop(), 0, 4, 0, 1, new int[]{200, 200, 200, 200}));
        animatorController.addCollision(EAnimation.RUN, 0, FilesTools.readInput(getClass().getClassLoader().getResourceAsStream(ConfigPath.jsonCollision + id + "/slimeIdle.json")));
        animatorController.addConfig(EAnimation.RUN, 0, new AnimationConfigItem(SlimeActions.class.getMethod("run", GameObject.class), true));
        return animatorController;
    }


    public AnimatorController getItemAnimator(ESprites index) throws SlickException, JSONException {
        AnimatorController animatorController = new AnimatorController();
        if (index == ESprites.GROUND) {
            animatorController.addCollision(EAnimation.IDLE, 0, FilesTools.readInput(getClass().getClassLoader().getResourceAsStream(ConfigPath.jsonCollision + "/map/ground.json")));
        }
        if (index == ESprites.WALL) {
            animatorController.addCollision(EAnimation.IDLE, 0, FilesTools.readInput(getClass().getClassLoader().getResourceAsStream(ConfigPath.jsonCollision + "/map/wall.json")));
        }
        if (index == ESprites.PORTAL) {
            animatorController.addAnimation(EAnimation.IDLE, 0, AnimationFactory.createAnimation(new SpriteSheet(ConfigPath.image_game + "/entity/portal.png", 100, 21), EAnimation.IDLE.isLoop(), 0, 2, 0, 1, new int[]{70, 70}));
            animatorController.addCollision(EAnimation.IDLE, 0, FilesTools.readInput(getClass().getClassLoader().getResourceAsStream(ConfigPath.jsonCollision + "/entity/portal.json")));
        }
        return animatorController;
    }
}
