package com.andres_k.gameToolsLib.components.networkComponents.networkSend.messageServer;

import com.andres_k.gameToolsLib.components.gameComponent.gameObject.objects.Player;
import com.andres_k.gameToolsLib.components.networkComponents.networkSend.MessageModel;

/**
 * Created by andres_k on 19/03/2016.
 */
public class MessageStatePlayer extends MessageModel {
    private float life;
    private float x;
    private float y;

    public MessageStatePlayer() {
    }

    public MessageStatePlayer(Player player) {
        this.life = player.getCurrentLife();
        this.x = player.getPosX();
        this.y = player.getPosY();
    }

    public float getX() {
        return this.x;
    }

    public float getY() {
        return this.y;
    }

    public float getLife() {
        return this.life;
    }

    public void setLife(float life) {
        this.life = life;
    }
}
