package com.andres_k.components.gameComponents.gameObject.objects.entities;

import com.andres_k.components.gameComponents.animations.AnimatorController;
import com.andres_k.components.gameComponents.animations.EAnimation;
import com.andres_k.components.gameComponents.gameObject.EGameObject;
import com.andres_k.components.gameComponents.gameObject.GameObject;
import com.andres_k.components.gameComponents.gameObject.commands.movement.EDirection;
import com.andres_k.components.gameComponents.gameObject.objects.Obstacle;
import com.andres_k.components.gameComponents.gameObject.objects.players.Slime;
import com.andres_k.utils.tools.Console;
import com.andres_k.utils.tools.MathTools;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Shape;

import java.awt.geom.AffineTransform;

/**
 * Created by kevin on 17/04/2017.
 */
public class Portal extends Obstacle {

    private boolean selected;

    public Portal(AnimatorController animatorController, EGameObject type, String id, float x, float y) {
        super(animatorController, type, id, x, y, 0, 0, 0, 0);
    }

    @Override
    public void manageDoHit(GameObject enemy) {
        if (enemy.getType() == EGameObject.SLIME) {
            Slime slime = (Slime) enemy;

            double maxX = 1;
            double maxY = 2.5;
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
            Shape enemyS = enemy.getBody().getFlippedBody(enemy.getAnimatorController().getEyesDirection().isHorizontalFlip(), enemy.getPosX(), enemy.getPosY(), enemy.getAnimatorController().getRotateAngle());
            Console.write("posP: " + enemyS.getCenterY() + "    rect: " + rect.getCenterY());
            boolean positionUp;

            double[] pt1 = {rect.getCenterX() + 100, rect.getCenterY()};
            AffineTransform.getRotateInstance(Math.toRadians(this.getAnimatorController().getRotateAngle() + 180), rect.getCenterX(), rect.getCenterY()).transform(pt1, 0, pt1, 0, 1);
            double[] pt2 = {rect.getCenterX() + 100, rect.getCenterY()}; //new double[]{rect.getCenterX(), rect.getCenterY()}
            AffineTransform.getRotateInstance(Math.toRadians(this.getAnimatorController().getRotateAngle()), rect.getCenterX(), rect.getCenterY()).transform(pt2, 0, pt2, 0, 1);
            positionUp = MathTools.isUpper(pt1, pt2, new double[]{enemy.getPosX(), enemy.getPosY()});
            Console.write("posUP: " + positionUp + " (" + pt1[0] + ", " + pt1[1] + ") {" + pt2[0] + ", " + pt2[1] + "} [" + enemy.getPosX() + ", " + enemy.getPosY() + "]");

            try {
                slime.getAnimatorController().changeAnimation(EAnimation.JUMP);
                slime.getMovement().resetGravity();

                if (this.type == EGameObject.PORTAL_ATTRACT) {
                    if (positionUp) {
                        enemy.getMovement().setCoeffX((float) coeffX);
                        enemy.getMovement().setCoeffY((float) coeffY);
                    } else {
                        enemy.getMovement().setCoeffX((float) coeffX);
                        enemy.getMovement().setCoeffY(-(float) coeffY);
                    }
                } else if (this.type == EGameObject.PORTAL_REPULSE) {
                    if (positionUp) {
                        enemy.getMovement().setCoeffX((float) coeffX);
                        enemy.getMovement().setCoeffY(-(float) coeffY);
                    } else {
                        enemy.getMovement().setCoeffX((float) coeffX);
                        enemy.getMovement().setCoeffY((float) coeffY);
                    }
                }

                if (coeff > 0.00f) {
                    if ((this.type == EGameObject.PORTAL_REPULSE && !positionUp) || (this.type == EGameObject.PORTAL_ATTRACT && positionUp)) {
                        enemy.getMovement().setCoeffX(enemy.getMovement().getCoeffX() * -1);
                        enemy.getAnimatorController().setEyesDirection(EDirection.LEFT);
                    }
                } else {
                    if ((this.type == EGameObject.PORTAL_REPULSE && positionUp) || (this.type == EGameObject.PORTAL_ATTRACT && !positionUp)) {
                        enemy.getMovement().setCoeffX(enemy.getMovement().getCoeffX() * -1);
                        enemy.getAnimatorController().setEyesDirection(EDirection.LEFT);
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
