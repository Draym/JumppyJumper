package com.andres_k.custom.component.graphicComponents.trailer;

import com.andres_k.gameToolsLib.components.graphicComponents.trailer.TrailerComponent;
import com.andres_k.gameToolsLib.components.camera.CameraController;
import com.andres_k.gameToolsLib.components.gameComponent.animations.AnimatorController;
import com.andres_k.custom.component.gameComponent.animation.EAnimation;
import com.andres_k.custom.component.gameComponent.gameObject.EGameObject;
import com.andres_k.custom.component.resourceComponents.resources.ResourceManager;
import com.andres_k.custom.component.resourceComponents.sounds.ESound;
import com.andres_k.gameToolsLib.components.resourceComponents.sounds.MusicController;
import com.andres_k.gameToolsLib.utils.stockage.Pair;
import com.andres_k.gameToolsLib.utils.tools.Console;
import com.andres_k.gameToolsLib.utils.tools.RandomTools;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kevin on 26/04/2017.
 */
public class FinalGameTrailer extends TrailerComponent {
    private List<AnimatorController> slimes;
    private Pair<Float, Float> pos;
    private Pair<Float, Float> finalPos;
    private boolean stopMove;

    public FinalGameTrailer() {
        super();
        this.slimes = new ArrayList<>();
    }

    @Override
    public void reset() {
        super.reset();
        this.slimes.clear();
        this.pos = new Pair<>(-64f, 540f);
        this.finalPos= new Pair<>(600f, 630f);
    }

    public void initTrailer(int nbSlimes) throws SlickException {
        this.slimes.clear();
        Console.write("NB SLIMES! " + nbSlimes);
        for (int i = 0; i < nbSlimes; ++i) {
            this.slimes.add(ResourceManager.get().getGameAnimator(EGameObject.SLIME));
            this.slimes.get(i).changeAnimation(EAnimation.WIN);

            int max =  RandomTools.getInt(3);
            for (int i2 = 0; i2 < max; ++i2) {
                this.slimes.get(i).forceNextFrame();
            }
        }
    }

    public void launchTrailer() {
        this.started = true;
        this.running = true;
        this.stopMove = false;
        CameraController.get().init();
        MusicController.get().stop(ESound.BACKGROUND_GAME);
        MusicController.get().loop(ESound.BACKGROUND_WIN);
    }

    public void draw(Graphics g) throws SlickException {
        if (this.started) {
            for (int i = 0; i < this.slimes.size(); ++i) {
                this.slimes.get(i).draw(g, this.pos.getV1() - (i * 135), this.pos.getV2());
            }
        }
    }

    public void update() {
        if (!this.stopMove && this.started) {
            this.pos.setV1(this.pos.getV1() + 5f);
            this.pos.setV2(this.pos.getV2() + 0.4f);

            int nbMult = (this.slimes.size() > 3 ? this.slimes.size() - 3 : 0);
            if (this.pos.getV1() >= this.finalPos.getV1() + 200 + (nbMult * 100)) {
                this.stopMove = true;
            }
        }
        if (this.running) {
            if (this.pos.getV1() >= this.finalPos.getV1()) {
                this.finished = true;
                this.running = false;
            }
        }
        if (this.started) {
            this.slimes.forEach(AnimatorController::update);
        }
    }
}
