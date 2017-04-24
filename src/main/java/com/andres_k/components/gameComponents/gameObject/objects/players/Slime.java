package com.andres_k.components.gameComponents.gameObject.objects.players;

import com.andres_k.components.gameComponents.animations.AnimatorController;
import com.andres_k.components.gameComponents.animations.EAnimation;
import com.andres_k.components.gameComponents.gameObject.EGameObject;
import com.andres_k.components.gameComponents.gameObject.commands.movement.EDirection;
import com.andres_k.components.gameComponents.gameObject.objects.Player;
import org.newdawn.slick.SlickException;

/**
 * Created by andres_k on 13/10/2015.
 */
public class Slime extends Player {

    public Slime(AnimatorController animatorController, String id, float x, float y) {
        super(animatorController, EGameObject.SLIME, id, x, y, 1, 0, 100, 220, 15);
        try {
            this.animatorController.setEyesDirection(EDirection.RIGHT);
            this.animatorController.changeAnimation(EAnimation.RUN);
            this.movement.setMoveDirection(EDirection.RIGHT);
        } catch (SlickException e) {
            e.printStackTrace();
        }
    }
}
