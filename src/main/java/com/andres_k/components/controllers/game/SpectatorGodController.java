package com.andres_k.components.controllers.game;

import com.andres_k.components.camera.CameraController;
import com.andres_k.components.eventComponent.events.MouseController;
import com.andres_k.components.eventComponent.input.EInput;
import com.andres_k.components.gameComponents.animations.AnimatorController;
import com.andres_k.components.gameComponents.gameObject.EGameObject;
import com.andres_k.components.gameComponents.gameObject.GameObject;
import com.andres_k.components.gameComponents.gameObject.GameObjectController;
import com.andres_k.components.gameComponents.gameObject.GameObjectFactory;
import com.andres_k.components.gameComponents.gameObject.objects.entities.Portal;
import com.andres_k.components.resourceComponent.resources.ResourceManager;
import com.andres_k.utils.stockage.Pair;
import com.andres_k.utils.tools.Console;
import com.andres_k.utils.tools.MathTools;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

import java.util.List;

/**
 * Created by kevin on 24/04/2017.
 */
public class SpectatorGodController {
    private AnimatorController indicePortal;
    private Pair<Integer, Integer> spriteSizes;
    private Pair<Integer, Integer> startClick;
    private boolean clicked;

    public SpectatorGodController() {
        this.clicked = false;
        this.startClick = new Pair<>(-1, -1);
        this.indicePortal = null;
        this.spriteSizes = new Pair<>(0, 0);
    }

    public void init() throws SlickException {
        this.indicePortal = ResourceManager.get().getGameAnimator(EGameObject.PORTAL);
        this.spriteSizes.setV1(this.indicePortal.currentAnimation().getWidth());
        this.spriteSizes.setV2(this.indicePortal.currentAnimation().getHeight());
    }

    public void reset() {
        this.clicked = false;
    }

    public void draw(Graphics g) throws SlickException {
        if (this.clicked && this.indicePortal != null)
            this.indicePortal.draw(g, this.startClick.getV1() - (this.spriteSizes.getV1() / 2), this.startClick.getV2() - (this.spriteSizes.getV2() / 2));
    }

    public void update() {
        if (this.clicked && this.indicePortal != null) {
            this.indicePortal.update();
            this.indicePortal.setRotateAngle(MathTools.getAngle(this.startClick.getV1(), this.startClick.getV2(), MouseController.get().getMouseX(), MouseController.get().getMouseY()));

            //Console.write("coeff: " + Math.tan(MathTools.toRadian(this.indicePortal.getRotateAngle())) + "    angle: " + this.indicePortal.getRotateAngle());
        }
    }

    public boolean keyPressed(EInput result) {
        return false;
    }

    public boolean keyReleased(EInput result) {
        return false;
    }

    public void mousePressed(int button, int x, int y) {
        this.clicked = (button == 0);
        this.startClick.setV1(x + (int)CameraController.get().getCamX());
        this.startClick.setV2(y + (int)CameraController.get().getCamY());
    }

    public void mouseReleased(int button, int x, int y) {
        if (button == 0 && this.clicked) {
            this.clicked = false;
            try {
                AnimatorController animator = ResourceManager.get().getGameAnimator(EGameObject.PORTAL);
                animator.setRotateAngle(MathTools.getAngle(this.startClick.getV1(), this.startClick.getV2(), x + (int)CameraController.get().getCamX(), y + (int)CameraController.get().getCamY()));
                GameObjectController.get().addEntity(GameObjectFactory.create(EGameObject.PORTAL, animator, "portal", this.startClick.getV1(), this.startClick.getV2()));
            } catch (SlickException e) {
                e.printStackTrace();
            }
        } else {
            List<GameObject> entities = GameObjectController.get().getEntities();

            entities.stream().filter(entity -> entity.getType() == EGameObject.PORTAL).filter(entity -> entity.getBody().getFlippedBody(entity.getAnimatorController().getEyesDirection().isHorizontalFlip(), CameraController.get().getTransformPosX(entity.getPosX()),
                    CameraController.get().getTransformPosY(entity.getPosY()), entity.getAnimatorController().getRotateAngle()).contains(x, y)).forEachOrdered(entity -> {
                if (((Portal) entity).isSelected()) {
                    entity.getAnimatorController().setDeleted(true);
                } else {
                    ((Portal) entity).setSelected(true);
                }
            });
        }
    }
}
