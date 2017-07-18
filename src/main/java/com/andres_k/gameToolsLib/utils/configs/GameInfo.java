package com.andres_k.gameToolsLib.utils.configs;

import com.andres_k.gameToolsLib.utils.tools.FilesTools;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

/**
 * Created by kevin on 18/07/2017.
 */
public class GameInfo {
    private String name = "";
    private String version = "";

    private GameInfo() {
        try {
            JSONObject gameInfo = new JSONObject(FilesTools.readFile(ConfigPath.game_info));
            this.name = gameInfo.getString("name");
            this.version = gameInfo.getString("version");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private static class SingletonHolder {
        private final static GameInfo instance = new GameInfo();
    }

    public static GameInfo get() {
        return SingletonHolder.instance;
    }

    public String getName() {
        return this.name;
    }

    public String getVersion() {
        return this.version;
    }
}
