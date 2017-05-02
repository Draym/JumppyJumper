package com.andres_k.custom.component.resourceComponents.resources.data;

import com.andres_k.custom.component.resourceComponents.resources.ESprites;
import com.andres_k.gameToolsLib.components.gameComponent.animations.AnimatorController;
import com.andres_k.custom.component.gameComponent.gameObject.EGameObject;
import com.andres_k.gameToolsLib.components.resourceComponents.resources.data.DataManager;
import com.andres_k.gameToolsLib.components.resourceComponents.resources.factory.AnimatorFactory;
import com.andres_k.custom.component.resourceComponents.resources.factory.AnimatorGameFactory;
import com.andres_k.gameToolsLib.utils.stockage.Pair;
import org.codehaus.jettison.json.JSONException;
import org.newdawn.slick.SlickException;

import java.util.HashMap;

/**
 * Created by andres_k on 13/03/2015.
 */
public class AnimatorGameData extends DataManager {
    private AnimatorFactory animatorFactory;
    private HashMap<EGameObject, AnimatorController> playerAnimator;
    private HashMap<EGameObject, AnimatorController> itemAnimator;

    public AnimatorGameData() {
        this.animatorFactory = new AnimatorGameFactory();
        this.playerAnimator = new HashMap<>();
        this.itemAnimator = new HashMap<>();
    }

    @Override
    public void prerequisite() throws NoSuchMethodException, SlickException, JSONException {
        this.initialiseMethods();
    }

    @Override
    protected void initialiseMethods() throws NoSuchMethodException {
        this.methods.clear();
        this.methods.add(new Pair<>(false, this.getClass().getMethod("initPlayers")));
        this.methods.add(new Pair<>(false, this.getClass().getMethod("initItems")));
        this.methods.add(new Pair<>(false, this.getClass().getMethod("initItems2")));
    }

    @Override
    public String success() {
        return "> Game complete";
    }

    public void initPlayers() throws NoSuchMethodException, SlickException, JSONException {
        this.addPlayerAnimator(this.animatorFactory.getAnimator(ESprites.SLIME), EGameObject.SLIME);
    }


    public void initItems() throws NoSuchMethodException, SlickException, JSONException {
        this.addItemAnimator(this.animatorFactory.getAnimator(ESprites.GROUND), EGameObject.GROUND);
        this.addItemAnimator(this.animatorFactory.getAnimator(ESprites.WALL), EGameObject.WALL);
        this.addItemAnimator(this.animatorFactory.getAnimator(ESprites.PORTAL_ATTRACT), EGameObject.PORTAL_ATTRACT);
        this.addItemAnimator(this.animatorFactory.getAnimator(ESprites.PORTAL_REPULSE), EGameObject.PORTAL_REPULSE);
        this.addItemAnimator(this.animatorFactory.getAnimator(ESprites.GATE), EGameObject.GATE);
    }

    public void initItems2() throws NoSuchMethodException, SlickException, JSONException {
        this.addItemAnimator(this.animatorFactory.getAnimator(ESprites.HEART), EGameObject.HEART);
        this.addItemAnimator(this.animatorFactory.getAnimator(ESprites.COIN), EGameObject.COIN);
        this.addItemAnimator(this.animatorFactory.getAnimator(ESprites.STEEL_WHEEL), EGameObject.STEEL_WHEEL);
        this.addItemAnimator(this.animatorFactory.getAnimator(ESprites.STEEL_PIKE), EGameObject.STEEL_PIKE);
        this.addItemAnimator(this.animatorFactory.getAnimator(ESprites.FIRE_GUN), EGameObject.FIRE_GUN);
    }

    private void addPlayerAnimator(AnimatorController animatorController, EGameObject type) {
        this.playerAnimator.put(type, animatorController);
    }

    private void addItemAnimator(AnimatorController animatorController, EGameObject type) {
        this.itemAnimator.put(type, animatorController);
    }


    // GETTERS
    public AnimatorController getAnimator(EGameObject index) throws SlickException {
        if (this.playerAnimator.containsKey(index)) {
            return new AnimatorController(this.playerAnimator.get(index));
        } else if (this.itemAnimator.containsKey(index)) {
            return new AnimatorController(this.itemAnimator.get(index));
        }
        throw new SlickException("[ERROR]: The content of " + index.getValue() + " is missing.");
    }
}

