package com.andres_k.custom.component.gameComponent.gameObject;

import com.andres_k.gameToolsLib.components.gameComponent.animations.AnimatorController;
import com.andres_k.gameToolsLib.components.gameComponent.gameObject.GameObject;
import com.andres_k.gameToolsLib.components.gameComponent.movement.EDirection;
import com.andres_k.custom.component.gameComponent.gameObject.objects.entities.Bonus;
import com.andres_k.custom.component.gameComponent.gameObject.objects.entities.Gate;
import com.andres_k.custom.component.gameComponent.gameObject.objects.entities.Portal;
import com.andres_k.custom.component.gameComponent.gameObject.objects.entities.Trap;
import com.andres_k.gameToolsLib.components.gameComponent.gameObject.objects.map.MapObstacle;
import com.andres_k.gameToolsLib.components.gameComponent.gameObject.objects.obstacles.Border;
import com.andres_k.gameToolsLib.components.gameComponent.gameObject.objects.obstacles.Platform;
import com.andres_k.custom.component.gameComponent.gameObject.objects.players.Slime;
import com.andres_k.custom.component.graphicComponents.graphic.EnumWindow;
import com.andres_k.gameToolsLib.utils.configs.WindowConfig;

/**
 * Created by andres_k on 13/10/2015.
 */
public class GameObjectFactory {

    public static GameObject create(EGameObject type, AnimatorController animatorController, String id, float x, float y) {
        GameObject object = null;

        if (type.isIn(EGameObject.ANIMATED) && x > WindowConfig.get().centerPosX(EnumWindow.GAME, 0)) {
            animatorController.setEyesDirection(EDirection.LEFT);
        }
        if (type == EGameObject.SLIME) {
            object = new Slime(animatorController, id, x, y);
        } else if (type == EGameObject.PLATFORM) {
            object = new Platform(animatorController, id, x, y);
        } else if (type == EGameObject.BORDER) {
            object = new Border(animatorController, id, x, y);
        } else if (type == EGameObject.MAP) {
            object = new MapObstacle(animatorController, id, x, y);
        } else if (type.isIn(new EGameObject[]{EGameObject.PORTAL_ATTRACT, EGameObject.PORTAL_REPULSE})) {
            object = new Portal(animatorController, type, id, x, y);
        } else if (type == EGameObject.GATE) {
            object = new Gate(animatorController, id, x, y);
        } else if (type.isIn(EGameObject.BONUS)) {
            object = new Bonus(animatorController, type, id, x, y);
        } else if (type.isIn(EGameObject.TRAP)) {
            object = new Trap(animatorController, type, id, x, y, 1, 1);
        }
        return object;
    }
}
