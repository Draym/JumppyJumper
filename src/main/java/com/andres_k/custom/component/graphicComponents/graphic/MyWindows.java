package com.andres_k.custom.component.graphicComponents.graphic;

import com.andres_k.gameToolsLib.components.graphicComponents.graphic.Windows;
import org.codehaus.jettison.json.JSONException;
import org.newdawn.slick.SlickException;

/**
 * Created by kevin on 03/05/2017.
 */
public class MyWindows extends Windows {
    public MyWindows(String name) throws JSONException, SlickException {
        super(name);
    }

    @Override
    protected void addWindows() {
    }
}
