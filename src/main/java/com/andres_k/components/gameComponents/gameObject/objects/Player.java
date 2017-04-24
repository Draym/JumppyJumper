package com.andres_k.components.gameComponents.gameObject.objects;

import com.andres_k.components.eventComponent.input.EInput;
import com.andres_k.components.gameComponents.animations.AnimatorController;
import com.andres_k.components.gameComponents.collisions.PhysicalObject;
import com.andres_k.components.gameComponents.gameObject.EGameObject;
import com.andres_k.components.gameComponents.gameObject.commands.movement.EDirection;
import com.andres_k.utils.configs.GameConfig;
import org.newdawn.slick.SlickException;

/**
 * Created by kevin on 16/04/2017.
 */
public class Player extends PhysicalObject {

    protected Player(AnimatorController animatorController, EGameObject type, String id, float x, float y, float life, float damage, float moveSpeed, float gravitySpeed, float weight) {
        super(animatorController, type, id, x, y, life, damage, moveSpeed, gravitySpeed, weight);
        this.movement.setMoveDirection(EDirection.RIGHT);
        this.movement.setPushX(GameConfig.speedTravel);
        this.animatorController.setEyesDirection(EDirection.RIGHT);
    }

    @Override
    public void clear() {

    }

    @Override
    public void update() throws SlickException {
        this.animatorController.update();
        this.animatorController.updateAnimation(this);
        this.movement.update();
    }

    @Override
    public void eventPressed(EInput input) {

    }

    @Override
    public void eventReleased(EInput input) {

    }

    @Override
    public Object doTask(Object task) {
        return null;
    }
}
