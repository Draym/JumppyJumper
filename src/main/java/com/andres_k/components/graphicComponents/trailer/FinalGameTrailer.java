package com.andres_k.components.graphicComponents.trailer;

import com.andres_k.components.gameComponents.animations.AnimatorController;
import com.andres_k.components.gameComponents.animations.EAnimation;
import com.andres_k.components.gameComponents.gameObject.EGameObject;
import com.andres_k.components.resourceComponent.resources.ResourceManager;
import com.andres_k.components.resourceComponent.sounds.ESound;
import com.andres_k.components.resourceComponent.sounds.MusicController;
import com.andres_k.utils.stockage.Pair;
import com.andres_k.utils.tools.Console;
import com.andres_k.utils.tools.RandomTools;
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
            this.pos.setV1(this.pos.getV1() + 2f);
            this.pos.setV2(this.pos.getV2() + 0.2f);

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
