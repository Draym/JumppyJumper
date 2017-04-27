package com.andres_k.components.gameComponents.gameObject.objects.entities;

import com.andres_k.components.gameComponents.animations.AnimatorController;
import com.andres_k.components.gameComponents.gameObject.EGameObject;
import com.andres_k.components.gameComponents.gameObject.GameObject;
import com.andres_k.components.gameComponents.gameObject.GameObjectController;
import com.andres_k.components.gameComponents.gameObject.objects.Obstacle;
import org.newdawn.slick.SlickException;

import java.util.List;

/**
 * Created by kevin on 25/04/2017.
 */
public class Bonus extends Obstacle {
    public Bonus(AnimatorController animatorController, EGameObject type, String id, float x, float y) {
        super(animatorController, type, id, x, y, 0, 0, 0, 0);
    }

    @Override
    public void manageDoHit(GameObject enemy) {
        if (this.alive && enemy.getType() == EGameObject.SLIME) {
            this.getAnimatorController().setDeleted(true);
            if (this.getType() == EGameObject.COIN) {
                GameObjectController.get().bonusPoint += 1;
            } else if (this.getType() == EGameObject.HEART) {
                List<GameObject> players = GameObjectController.get().getPlayers();/*
                float lowX = 0;
                int saveI = 0;

                for (int i = 0; i < players.size(); ++i) {
                    if (lowX == 0 || players.get(i).getPosX() < lowX) {
                        saveI = i;
                        lowX = players.get(i).getPosX();
                    }
                }*/
                try {
                    GameObjectController.get().createPlayer(EGameObject.SLIME, "player_slime:" + (players.size() + 1), enemy.getPosX() - 40, 100, enemy.getPosY() - 20, -100, true);
                } catch (SlickException e) {
                    e.printStackTrace();
                }
            }
            this.alive = false;
        }
    }
}
