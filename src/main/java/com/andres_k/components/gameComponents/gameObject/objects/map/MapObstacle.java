package com.andres_k.components.gameComponents.gameObject.objects.map;

import com.andres_k.components.gameComponents.animations.AnimatorController;
import com.andres_k.components.gameComponents.gameObject.EGameObject;
import com.andres_k.components.gameComponents.gameObject.GameObject;
import com.andres_k.components.gameComponents.gameObject.objects.Obstacle;

/**
 * Created by kevin on 23/04/2017.
 */
public class MapObstacle extends Obstacle {
    public MapObstacle(AnimatorController animatorController, String id, float posX, float posY) {
        super(animatorController, EGameObject.MAP, id, posX, posY, 1, 1, 0, 0);
    }
}
