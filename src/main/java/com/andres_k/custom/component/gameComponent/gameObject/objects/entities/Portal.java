package com.andres_k.custom.component.gameComponent.gameObject.objects.entities;

import com.andres_k.gameToolsLib.components.gameComponent.animations.AnimatorController;
import com.andres_k.custom.component.gameComponent.animation.EAnimation;
import com.andres_k.custom.component.gameComponent.gameObject.EGameObject;
import com.andres_k.gameToolsLib.components.gameComponent.gameObject.GameObject;
import com.andres_k.gameToolsLib.components.gameComponent.movement.EDirection;
import com.andres_k.gameToolsLib.components.gameComponent.gameObject.objects.Obstacle;
import com.andres_k.gameToolsLib.utils.tools.Console;
import com.andres_k.gameToolsLib.utils.tools.MathTools;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Shape;

/**
 * Created by kevin on 17/04/2017.
 */
public class Portal extends Obstacle {

    private boolean selected;

    public Portal(AnimatorController animatorController, EGameObject type, String id, float x, float y) {
        super(animatorController, type, id, x, y, 0, 0, 0, 0);
    }

    @Override
    public void manageDoHit(GameObject slime) {
        if (slime.getType() == EGameObject.SLIME && slime.isLastAttacker(this.getId())) {
            double maxX = 1;
            double maxY = 3;
            double coeff = (Math.tan(Math.toRadians(this.getAnimatorController().getRotateAngle())));

            double coeffY = MathTools.abs((float) (-1 / coeff));
            double coeffX;

            coeffY = (coeffY > 1000000 ? 1000000 : coeffY);
            if (coeffY > maxY) {
                coeffX = 1f - 1f * (((coeffY - maxY) * 100f / coeffY) / 100);
            } else {
                coeffX = 1f + (maxX * (((coeffY) * 100f / maxY) / 100));
            }
            coeffY += 1;
            coeffY = (coeffY > maxY ? maxY : coeffY);

            Console.write("coeff: " + coeff + " -> [" + coeffX + ", " + coeffY + "]");
            Shape rect = this.getBody().getFlippedBody(this.getAnimatorController().getEyesDirection().isHorizontalFlip(), this.getPosX(), this.getPosY(), this.getAnimatorController().getRotateAngle());
            Shape enemyRect = slime.getBody().getFlippedBody(slime.getAnimatorController().getEyesDirection().isHorizontalFlip(), slime.getPosX(), slime.getPosY(), slime.getAnimatorController().getRotateAngle());
            Console.write("posP: " + enemyRect.getCenterY() + "    rect: " + rect.getCenterY());

            boolean positionUp = MathTools.isUpper(rect, this.animatorController.getRotateAngle(), enemyRect);
            Console.write("posUP: " + positionUp);

            try {
                slime.getAnimatorController().changeAnimation(EAnimation.JUMP);
                slime.getAnimatorController().setEyesDirection(EDirection.RIGHT);
                slime.getMovement().resetGravity();

                if (this.type == EGameObject.PORTAL_ATTRACT) {
                    if (positionUp) {
                        slime.getMovement().setCoeffX((float) coeffX * 1.5f);
                        slime.getMovement().setCoeffY((float) coeffY);
                    } else {
                        slime.getMovement().setCoeffX((float) coeffX * 1.5f);
                        slime.getMovement().setCoeffY(-(float) coeffY);
                    }
                } else if (this.type == EGameObject.PORTAL_REPULSE) {
                    if (positionUp) {
                        slime.getMovement().setCoeffX((float) coeffX);
                        slime.getMovement().setCoeffY(-(float) coeffY);
                    } else {
                        slime.getMovement().setCoeffX((float) coeffX);
                        slime.getMovement().setCoeffY((float) coeffY);
                    }
                }
                if (coeff > 0.00f) {
                    if ((this.type == EGameObject.PORTAL_REPULSE && !positionUp) || (this.type == EGameObject.PORTAL_ATTRACT && positionUp)) {
                        slime.getMovement().setCoeffX(slime.getMovement().getCoeffX() * -1);
                        slime.getAnimatorController().setEyesDirection(EDirection.LEFT);
                    }
                } else {
                    if ((this.type == EGameObject.PORTAL_REPULSE && positionUp) || (this.type == EGameObject.PORTAL_ATTRACT && !positionUp)) {
                        slime.getMovement().setCoeffX(slime.getMovement().getCoeffX() * -1);
                        slime.getAnimatorController().setEyesDirection(EDirection.LEFT);
                    }
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
