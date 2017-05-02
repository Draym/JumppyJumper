package com.andres_k.custom.component.resourceComponents.resources.data;

import com.andres_k.custom.component.resourceComponents.sounds.ESound;
import com.andres_k.gameToolsLib.components.resourceComponents.resources.data.DataManager;
import com.andres_k.gameToolsLib.components.resourceComponents.sounds.MusicController;
import com.andres_k.gameToolsLib.utils.stockage.Pair;
import org.codehaus.jettison.json.JSONException;
import org.newdawn.slick.SlickException;

/**
 * Created by andres_k on 21/01/2016.
 */
public class MusicData extends DataManager {

    @Override
    public void prerequisite() throws NoSuchMethodException, SlickException, JSONException {
        this.initialiseMethods();
        //MusicController.get().addMusic(ESound.BACKGROUND_LOAD);
    }

    @Override
    protected void initialiseMethods() throws NoSuchMethodException {
        this.methods.clear();
        this.methods.add(new Pair<>(false, this.getClass().getMethod("initBackgroundGame")));
        this.methods.add(new Pair<>(false, this.getClass().getMethod("initBackgroundWin")));/*
        this.methods.add(new Pair<>(false, this.getClass().getMethod("initBackgroundHome")));*/
    }

    @Override
    public String success() {
        return "> Music complete";
    }

    public void initBackgroundGame() throws NoSuchMethodException, SlickException, JSONException {
        MusicController.get().addMusic(ESound.BACKGROUND_GAME);
    }

    public void initBackgroundWin() throws NoSuchMethodException, SlickException, JSONException {
        MusicController.get().addMusic(ESound.BACKGROUND_WIN);
    }

    public void initBackgroundHome() throws NoSuchMethodException, SlickException, JSONException {
        MusicController.get().addMusic(ESound.BACKGROUND_HOME);
    }
}
