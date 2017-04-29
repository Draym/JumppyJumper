package com.andres_k.components.gameComponents.gameObject;

import com.andres_k.components.gameComponents.animations.AnimatorController;
import com.andres_k.components.gameComponents.gameObject.commands.movement.EDirection;
import com.andres_k.components.gameComponents.gameObject.objects.entities.Bonus;
import com.andres_k.components.gameComponents.gameObject.objects.entities.Gate;
import com.andres_k.components.gameComponents.gameObject.objects.entities.Portal;
import com.andres_k.components.gameComponents.gameObject.objects.entities.Trap;
import com.andres_k.components.gameComponents.gameObject.objects.map.MapObstacle;
import com.andres_k.components.gameComponents.gameObject.objects.obstacles.Border;
import com.andres_k.components.gameComponents.gameObject.objects.obstacles.Platform;
import com.andres_k.components.gameComponents.gameObject.objects.players.Slime;
import com.andres_k.components.graphicComponents.graphic.EnumWindow;
import com.andres_k.utils.configs.WindowConfig;
import com.andres_k.utils.tools.Console;

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
        } else if (type.isIn(EGameObject.PORTAL)) {
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
