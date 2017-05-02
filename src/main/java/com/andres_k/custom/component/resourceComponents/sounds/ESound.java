package com.andres_k.custom.component.resourceComponents.sounds;

import com.andres_k.custom.component.gameComponent.gameObject.EGameObject;
import com.andres_k.gameToolsLib.utils.configs.ConfigPath;

/**
 * Created by andres_k on 07/07/2015.
 */
public enum ESound {
    NOTHING(""),
    //musics
    BACKGROUND_LOAD(ConfigPath.sound_musics + "/loadTheme.ogg"),
    BACKGROUND_HOME(ConfigPath.sound_musics + "/homeTheme.ogg"),
    BACKGROUND_GAME(ConfigPath.sound_musics + "/gameTheme.ogg"),
    BACKGROUND_WIN(ConfigPath.sound_musics + "/winTheme.ogg"),

    //soundsGame

    //soundsMenu
    BUTTON_CLICK(ConfigPath.sound_gui + "/buttonClick.wav"),
    BUTTON_FOCUS(ConfigPath.sound_gui + "/buttonFocus.wav"),
    VALIDATE(ConfigPath.sound_gui + "/validate.wav"),
    UNVALIDATE(ConfigPath.sound_gui + "/unValidate.wav");


    private String path;
    private EGameObject object;

    ESound(String path){
        this.path = path;
        this.object = EGameObject.NULL;
    }

    ESound(EGameObject object, String path){
        this.path = path;
        this.object = object;
    }

    public String getPath(){
        return this.path;
    }

    public EGameObject getObject(){
        return this.object;
    }

    public static ESound getSound(EGameObject object){
        ESound[] sounds = ESound.values();

        for (ESound sound : sounds) {
            if (sound.getObject() == object) {
                return sound;
            }
        }
        return NOTHING;
    }
}
