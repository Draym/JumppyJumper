package com.andres_k.custom.component.gameComponent.gameObject;

import com.andres_k.custom.component.graphicComponents.background.EBackground;
import com.andres_k.custom.component.resourceComponents.resources.ResourceManager;
import com.andres_k.gameToolsLib.components.controllers.EMode;
import com.andres_k.gameToolsLib.components.gameComponent.animations.AnimatorController;
import com.andres_k.gameToolsLib.components.gameComponent.gameObject.GameDesign;
import com.andres_k.gameToolsLib.components.gameComponent.gameObject.GameObject;
import com.andres_k.gameToolsLib.components.gameComponent.gameObject.GameObjectController;
import com.andres_k.gameToolsLib.components.gameComponent.gameObject.objects.Character;
import com.andres_k.gameToolsLib.components.gameComponent.movement.EDirection;
import com.andres_k.gameToolsLib.utils.configs.GameConfig;
import com.andres_k.gameToolsLib.utils.tools.Console;
import org.newdawn.slick.SlickException;

/**
 * Created by andres_k on 10/07/2015.
 */
public final class MyGameDesign extends GameDesign {

    public MyGameDesign() {
    }


    // INIT
    @Override
    public void initWorld() throws SlickException {
        GameObjectController.get().addEntity(GameObjectFactory.create(EGameObject.MAP, ResourceManager.get().getBackgroundAnimator(EBackground.MAP_1), "Map_1:1", 1245, 415));
        GameObject m1 = GameObjectFactory.create(EGameObject.MAP, ResourceManager.get().getBackgroundAnimator(EBackground.MAP_1), "Map_1:2", 3735, 415);
        m1.getAnimatorController().forceCurrentAnimationIndex(1);
        GameObjectController.get().addEntity(m1);

        // GameObjectController.get().addEntity(GameObjectFactory.create(EGameObject.GATE, ResourceManager.get().getGameAnimator(EGameObject.GATE), "gate:0", 900, 600));
        // MAP 1
        GameObjectController.get().addEntity(GameObjectFactory.create(EGameObject.COIN, ResourceManager.get().getGameAnimator(EGameObject.COIN), "coin:1", 800, 600));
        GameObjectController.get().addEntity(GameObjectFactory.create(EGameObject.COIN, ResourceManager.get().getGameAnimator(EGameObject.COIN), "coin:2", 1783, 455));
        GameObjectController.get().addEntity(GameObjectFactory.create(EGameObject.COIN, ResourceManager.get().getGameAnimator(EGameObject.COIN), "coin:3", 2290, 289));
        GameObjectController.get().addEntity(GameObjectFactory.create(EGameObject.COIN, ResourceManager.get().getGameAnimator(EGameObject.COIN), "coin:4", 2290, 372));
        GameObjectController.get().addEntity(GameObjectFactory.create(EGameObject.COIN, ResourceManager.get().getGameAnimator(EGameObject.COIN), "coin:5", 2290, 455));
        GameObjectController.get().addEntity(GameObjectFactory.create(EGameObject.HEART, ResourceManager.get().getGameAnimator(EGameObject.HEART), "heart:1", 1285, 372));

        GameObject gf1 = GameObjectFactory.create(EGameObject.FIRE_GUN, ResourceManager.get().getGameAnimator(EGameObject.FIRE_GUN), "fireGun:1", 1370, 685);
        GameObject gf2 = GameObjectFactory.create(EGameObject.FIRE_GUN, ResourceManager.get().getGameAnimator(EGameObject.FIRE_GUN), "fireGun:2", 1450, 685);

        gf1.getAnimatorController().setEyesDirection(EDirection.RIGHT);
        gf1.getAnimatorController().setRotateAngle(-90);
        gf2.getAnimatorController().setEyesDirection(EDirection.RIGHT);
        gf2.getAnimatorController().setRotateAngle(-90);
        gf2.getAnimatorController().forceNextFrame();
        GameObjectController.get().addEntity(gf1);
        GameObjectController.get().addEntity(gf2);

        // MAP 2
        GameObjectController.get().addEntity(GameObjectFactory.create(EGameObject.STEEL_WHEEL, ResourceManager.get().getGameAnimator(EGameObject.STEEL_WHEEL), "wheel:1", 630 + 2490, 370));
        GameObjectController.get().addEntity(GameObjectFactory.create(EGameObject.STEEL_WHEEL, ResourceManager.get().getGameAnimator(EGameObject.STEEL_WHEEL), "wheel:2", 950 + 2490, 455));
        GameObjectController.get().addEntity(GameObjectFactory.create(EGameObject.GATE, ResourceManager.get().getGameAnimator(EGameObject.GATE), "gate:1", 2410 + 2490, 260));

        GameObjectController.get().addEntity(GameObjectFactory.create(EGameObject.HEART, ResourceManager.get().getGameAnimator(EGameObject.HEART), "heart:1", 123 + 2490, 621));
        GameObjectController.get().addEntity(GameObjectFactory.create(EGameObject.HEART, ResourceManager.get().getGameAnimator(EGameObject.HEART), "heart:1", 246 + 2490, 621));
        GameObjectController.get().addEntity(GameObjectFactory.create(EGameObject.HEART, ResourceManager.get().getGameAnimator(EGameObject.HEART), "heart:1", 369 + 2490, 621));
        GameObjectController.get().addEntity(GameObjectFactory.create(EGameObject.HEART, ResourceManager.get().getGameAnimator(EGameObject.HEART), "heart:1", 787 + 2490, 455));
        GameObjectController.get().addEntity(GameObjectFactory.create(EGameObject.HEART, ResourceManager.get().getGameAnimator(EGameObject.HEART), "heart:1", 873 + 2590, 372));

        AnimatorController pk = ResourceManager.get().getGameAnimator(EGameObject.STEEL_PIKE);
        pk.setRotateAngle(180);
        GameObjectController.get().addEntity(GameObjectFactory.create(EGameObject.STEEL_PIKE, pk, "pike:1", 1550 + 2490, 650));
        //GameObjectController.get().addEntity(GameObjectFactory.create(EGameObject.STEEL_PIKE, new AnimatorController(pk), "pike:2", 1677 + 2490, 650));
        //GameObjectController.get().addEntity(GameObjectFactory.create(EGameObject.STEEL_PIKE, new AnimatorController(pk), "pike:2", 1804 + 2490, 650));
        //GameObjectController.get().addEntity(GameObjectFactory.create(EGameObject.STEEL_PIKE, new AnimatorController(pk), "pike:2", 1931 + 2490, 650));
        //GameObjectController.get().addEntity(GameObjectFactory.create(EGameObject.STEEL_PIKE, new AnimatorController(pk), "pike:2", 2058 + 2490, 650));

        AnimatorController gfCtrl = ResourceManager.get().getGameAnimator(EGameObject.FIRE_GUN);
        //  gfCtrl.setEyesDirection(EDirection.RIGHT);
        gfCtrl.setRotateAngle(-90);
        AnimatorController gfCtrl2 = new AnimatorController(gfCtrl);
        gfCtrl2.forceNextFrame();
        gfCtrl2.forceNextFrame();
        GameObjectController.get().addEntity(GameObjectFactory.create(EGameObject.FIRE_GUN, gfCtrl, "fireGun:3", 1535 + 2490, 352));
        //GameObjectController.get().addEntity(GameObjectFactory.create(EGameObject.FIRE_GUN, gfCtrl2, "fireGun:4", 1615 + 2490, 352));
        GameObjectController.get().addEntity(GameObjectFactory.create(EGameObject.FIRE_GUN, new AnimatorController(gfCtrl2), "fireGun:5", 1695 + 2490, 352));
        // GameObjectController.get().addEntity(GameObjectFactory.create(EGameObject.FIRE_GUN, new AnimatorController(gfCtrl2), "fireGun:6", 1775 + 2490, 352));
        GameObjectController.get().addEntity(GameObjectFactory.create(EGameObject.FIRE_GUN, new AnimatorController(gfCtrl), "fireGun:7", 1855 + 2490, 352));
        //GameObjectController.get().addEntity(GameObjectFactory.create(EGameObject.FIRE_GUN, new AnimatorController(gfCtrl2), "fireGun:8", 1935 + 2490, 352));
        GameObjectController.get().addEntity(GameObjectFactory.create(EGameObject.FIRE_GUN, new AnimatorController(gfCtrl2), "fireGun:9", 2015 + 2490, 352));
        // GameObjectController.get().addEntity(GameObjectFactory.create(EGameObject.FIRE_GUN, new AnimatorController(gfCtrl2), "fireGun:10", 2095 + 2490, 352));
    }

    // FUNCTIONS

    @Override
    public void enter() throws SlickException {
        this.initWorld();
    }

    @Override
    public void addWinner(String id) {
        this.point += 1;
    }

    @Override
    public void thisPlayerIsDead(Character character) {
        Console.write("\n A Slime is dead");/*
        if (character.getId().equals(CameraController.get().getIdOwner())) {
        }*/
    }

    @Override
    public boolean isTheEndOfTheGame() {
        return (GameConfig.mode != EMode.SOLO && GameObjectController.get().getNumberPlayers() == 0);
    }
    
    // GETTERS
    @Override
    public int getWinnersNumber() {
        return this.point;
    }

    @Override
    public int getTotalScore() {
        int winners = this.point;

        int totalScore = (winners * 1000) + this.bonusPoint * 10;

        if (this.bonusPoint > 0) {
            totalScore *= this.bonusPoint;
        }
        return totalScore;
    }

    @Override
    public boolean didIWin() {
        return this.point > 0;
    }
}
