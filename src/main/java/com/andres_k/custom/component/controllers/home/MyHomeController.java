package com.andres_k.custom.component.controllers.home;

import com.andres_k.custom.component.graphicComponents.background.EBackground;
import com.andres_k.gameToolsLib.components.graphicComponents.background.wallpaper.Wallpaper;
import com.andres_k.custom.component.resourceComponents.resources.ResourceManager;
import com.andres_k.gameToolsLib.components.controllers.home.HomeController;
import org.codehaus.jettison.json.JSONException;
import org.newdawn.slick.SlickException;

/**
 * Created by kevin on 03/05/2017.
 */
public class MyHomeController extends HomeController {
    public MyHomeController(int idWindow) throws JSONException, SlickException {
        super(idWindow);
    }

    @Override
    public void init() throws SlickException {
        super.init();
        this.backgroundManager.addComponent(EBackground.LOGO, new Wallpaper(ResourceManager.get().getBackgroundAnimator(EBackground.LOGO), 300, 20));
    }
}
