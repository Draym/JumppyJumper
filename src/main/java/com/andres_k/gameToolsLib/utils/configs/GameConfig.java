package com.andres_k.gameToolsLib.utils.configs;

import com.andres_k.gameToolsLib.components.controllers.EMode;
import com.andres_k.custom.component.gameComponent.gameObject.EGameObject;
import com.andres_k.custom.component.graphicComponents.userInterface.elementGUI.EGuiElement;

import java.util.ArrayList;

/**
 * Created by andres_k on 05/02/2016.
 */
public class GameConfig {

    public static EMode mode = EMode.NONE;

    public static final int maxFps = 60;
    public static long timeLoop = 30;
    public static long currentTimeLoop = 30;

    public static final boolean animatedHome = true;
    public static final boolean characterLogoOff = true;

    public static ArrayList<EGameObject> typePlayer = new ArrayList<>();

    public static float speedTravel = 1f;
    public static float speedJump = 4f;

    public static float globalMapWidth = 4980;
    public static float globalMapHeight = 830;

    public static final EGuiElement playerChoiceGui[] = new EGuiElement[] {EGuiElement.AVATAR_SLIME};
    public static final EGameObject playerChoiceType[] = new EGameObject[] {EGameObject.SLIME};
}
