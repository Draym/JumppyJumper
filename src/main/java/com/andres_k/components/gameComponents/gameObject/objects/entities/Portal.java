package com.andres_k.components.gameComponents.gameObject.objects.entities;

import com.andres_k.components.eventComponent.input.EInput;
import com.andres_k.components.gameComponents.animations.AnimatorController;
import com.andres_k.components.gameComponents.animations.EAnimation;
import com.andres_k.components.gameComponents.collisions.PhysicalObject;
import com.andres_k.components.gameComponents.gameObject.EGameObject;
import com.andres_k.components.gameComponents.gameObject.GameObject;
import com.andres_k.components.gameComponents.gameObject.objects.Obstacle;
import com.andres_k.components.gameComponents.gameObject.objects.players.Slime;
import com.andres_k.utils.tools.Console;
import org.newdawn.slick.SlickException;

/**
 * Created by kevin on 17/04/2017.
 */
public class Portal extends Obstacle {

    private boolean selected;

    public Portal(AnimatorController animatorController, String id, float x, float y) {
        super(animatorController, EGameObject.PORTAL, id, x, y, 0, 0, 0, 0);
    }

    @Override
    public void manageDoHit(GameObject enemy) {
        if (enemy.getType() == EGameObject.SLIME) {
            Slime slime = (Slime) enemy;

            double coeff = Math.tan(this.getAnimatorController().getRotateAngle());

            /*Console.write("GET HIT: coeff: " + coeff);
            slime.getMovement().setCoeffX(1);
            slime.getMovement().setCoeffY((float) coeff);*/
            try {
                if (slime.getAnimatorController().currentAnimationType() != EAnimation.JUMP) {
                    Console.write("GET HIT");
                    slime.getAnimatorController().changeAnimation(EAnimation.JUMP);
                    slime.setOnEarth(false);
                    slime.getMovement().resetGravity();
                }
            } catch (SlickException e) {
                e.printStackTrace();
            }
        }
    }

    public boolean isSelected() {
        return this.selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}
