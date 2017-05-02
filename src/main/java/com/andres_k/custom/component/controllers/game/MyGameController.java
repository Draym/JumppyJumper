package com.andres_k.custom.component.controllers.game;

import com.andres_k.custom.component.gameComponent.gameObject.EGameObject;
import com.andres_k.gameToolsLib.components.gameComponent.gameObject.GameObjectController;
import com.andres_k.custom.component.graphicComponents.background.EBackground;
import com.andres_k.gameToolsLib.components.graphicComponents.background.wallpaper.Wallpaper;
import com.andres_k.gameToolsLib.components.graphicComponents.background.wallpaper.WallpaperSliding;
import com.andres_k.custom.component.graphicComponents.trailer.FinalGameTrailer;
import com.andres_k.gameToolsLib.components.graphicComponents.userInterface.elementGUI.elements.ElementFactory;
import com.andres_k.gameToolsLib.components.resourceComponents.fonts.EFont;
import com.andres_k.custom.component.resourceComponents.resources.ResourceManager;
import com.andres_k.gameToolsLib.components.taskComponent.CentralTaskManager;
import com.andres_k.custom.component.taskComponents.ELocation;
import com.andres_k.custom.component.taskComponents.ETaskType;
import com.andres_k.gameToolsLib.components.taskComponent.TaskFactory;
import com.andres_k.custom.component.eventComponents.EInput;
import com.andres_k.gameToolsLib.components.controllers.game.GameController;
import com.andres_k.gameToolsLib.utils.stockage.Pair;
import com.andres_k.gameToolsLib.utils.tools.ColorTools;
import org.codehaus.jettison.json.JSONException;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by kevin on 03/05/2017.
 */
public class MyGameController extends GameController {
    private SpectatorGodController spectatorGodController;
    protected FinalGameTrailer finalTrailer;

    public MyGameController(int idWindow) throws JSONException {
        super(idWindow, true);
        this.spectatorGodController = new SpectatorGodController();
        this.finalTrailer = new FinalGameTrailer();
    }

    @Override
    public void init() throws SlickException {
        super.init();
        this.spectatorGodController.init();
        this.backgroundManager.addComponent(EBackground.MAP_1, new WallpaperSliding(ResourceManager.get().getBackgroundAnimator(EBackground.BACKGROUND_BLOCK_1), 30, true, true));
        this.backgroundManager.addComponent(EBackground.FINAL_SCREEN_CLOUD, new WallpaperSliding(ResourceManager.get().getBackgroundAnimator(EBackground.FINAL_SCREEN_CLOUD), 10, true, true, 1));
        this.backgroundManager.addComponent(EBackground.FINAL_SCREEN, new Wallpaper(ResourceManager.get().getBackgroundAnimator(EBackground.FINAL_SCREEN)));
        this.backgroundManager.getComponent(EBackground.FINAL_SCREEN_CLOUD).activate(false);
        this.backgroundManager.getComponent(EBackground.FINAL_SCREEN).activate(false);
    }

    @Override
    public void renderWindow(Graphics g) throws SlickException {
        super.renderWindow(g);

        /* your code */
        if (this.gameIsDrawable()) {
            this.spectatorGodController.draw(g);
        } else {
            this.finalTrailer.draw(g);
        }
    }

    @Override
    public void update(GameContainer gameContainer) throws SlickException {
        super.update(gameContainer);

        /* your code */
        if (this.gameIsPlayable()) {
            this.spectatorGodController.update();
            if (GameObjectController.get().getGameDesign().isTheEndOfTheGame() && !this.finalTrailer.isStarted()) {
                this.launchFinalTrailer();
            }
        }
        if (this.finalTrailer.isFinished()) {
            this.endOfTheGame();
        }
        // if final trailer started
        this.finalTrailer.update();
    }

    @Override
    public void keyPressed(int key, char c) {
        if (this.gameIsPlayable()) {
            EInput result = this.inputGame.checkInput(key);
            if (!this.spectatorGodController.keyPressed(result) && this.gameIsRunning()) {
                GameObjectController.get().event(EInput.KEY_PRESSED, result);
            }
        }
    }

    @Override
    public void keyReleased(int key, char c) {
        if (key == Input.KEY_ESCAPE && this.gameStarted) {
            this.pause = !this.pause;
        }
        if (this.gameIsPlayable()) {
            EInput result = this.inputGame.checkInput(key);
            if (result == EInput.PAUSE) {
                this.technicalPause = !this.technicalPause;
            } else if (!this.spectatorGodController.keyReleased(result) && this.gameIsRunning()) {
                GameObjectController.get().event(EInput.KEY_RELEASED, result);
            }
        }
    }

    @Override
    public void mousePressed(int button, int x, int y) {
        if (this.gameIsPlayable()) {
            this.spectatorGodController.mousePressed(button, x, y);
        }
    }

    @Override
    public void mouseReleased(int button, int x, int y) {
        if (this.gameIsPlayable()) {
            this.spectatorGodController.mouseReleased(button, x, y);
        }
    }

    @Override
    protected void applyWinOnGui() {
        CentralTaskManager.get().sendRequest(TaskFactory.createTask(this.location, ELocation.GAME_GUI_PanelQuit_Details, new Pair<>(ETaskType.ADD, ElementFactory.createText(GameObjectController.get().getGameDesign().getWinnersNumber() + " slimes survived", ColorTools.get(ColorTools.Colors.GUI_BLUE), EFont.MODERN, 15, 100, 30))));
        CentralTaskManager.get().sendRequest(TaskFactory.createTask(this.location, ELocation.GAME_GUI_PanelQuit_Details, new Pair<>(ETaskType.ADD, ElementFactory.createText("you get " + GameObjectController.get().getGameDesign().getBonusPoint() + " super coin!", ColorTools.get(ColorTools.Colors.GUI_YELLOW_BLAND), EFont.MODERN, 15, 100, 45))));
        CentralTaskManager.get().sendRequest(TaskFactory.createTask(this.location, ELocation.GAME_GUI_PanelQuit_Details, new Pair<>(ETaskType.ADD, ElementFactory.createText("Total score : " + GameObjectController.get().getGameDesign().getTotalScore(), ColorTools.get(ColorTools.Colors.GUI_YELLOW_BLAND), EFont.MODERN, 20, 110, 70))));
    }

    @Override
    protected void applyLooseOnGui() {
        CentralTaskManager.get().sendRequest(TaskFactory.createTask(this.location, ELocation.GAME_GUI_PanelQuit_Details, new Pair<>(ETaskType.ADD, ElementFactory.createText("none of your slimes survived", ColorTools.get(ColorTools.Colors.GUI_ORANGE), EFont.MODERN, 15, 100, 30))));
        CentralTaskManager.get().sendRequest(TaskFactory.createTask(this.location, ELocation.GAME_GUI_PanelQuit_Details, new Pair<>(ETaskType.ADD, ElementFactory.createText("you get " + GameObjectController.get().getGameDesign().getBonusPoint() + " super coin!", ColorTools.get(ColorTools.Colors.GUI_YELLOW_BLAND), EFont.MODERN, 15, 100, 45))));
        CentralTaskManager.get().sendRequest(TaskFactory.createTask(this.location, ELocation.GAME_GUI_PanelQuit_Details, new Pair<>(ETaskType.ADD, ElementFactory.createText("Total score : " + GameObjectController.get().getGameDesign().getTotalScore(), ColorTools.get(ColorTools.Colors.GUI_YELLOW_BLAND), EFont.MODERN, 20, 110, 70))));
    }

    @Override
    protected void launchGamePlay() {
        this.spectatorGodController.enter();
        this.resetFinalTrailer();
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                //SoundController.get().play(ESound.EFFECT_FIGHT);
                CentralTaskManager.get().sendRequest(TaskFactory.createTask(location, ELocation.GAME_GUI_AnimStart, new Pair<>(ETaskType.START_TIMER, 2000)));
                backgroundManager.run();
            }
        }, 2000);
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                gameStarted = true;
            }
        }, 3500);
    }

    @Override
    protected void leaveGamePlay() {
        this.spectatorGodController.leave();
        this.backgroundManager.getComponent(EBackground.MAP_1).activate(false);
        this.backgroundManager.getComponent(EBackground.FINAL_SCREEN).activate(true);
        this.backgroundManager.getComponent(EBackground.FINAL_SCREEN_CLOUD).activate(true);
    }

    @Override
    protected void addPlayerForGame() {
        this.playerTypes.add(EGameObject.SLIME);
        this.playerTypes.add(EGameObject.SLIME);
        this.playerTypes.add(EGameObject.SLIME);
        this.playerTypes.add(EGameObject.SLIME);
    }

    private void launchFinalTrailer() throws SlickException {
        if (GameObjectController.get().getGameDesign().getWinnersNumber() > 0) {
            this.leaveGamePlay();
            this.finalTrailer.initTrailer(GameObjectController.get().getGameDesign().getWinnersNumber());
            this.finalTrailer.launchTrailer();
        } else {
            this.endOfTheGame();
        }
    }

    private void resetFinalTrailer() {
        this.finalTrailer.reset();
        this.backgroundManager.getComponent(EBackground.MAP_1).activate(true);
        this.backgroundManager.getComponent(EBackground.FINAL_SCREEN).activate(false);
        this.backgroundManager.getComponent(EBackground.FINAL_SCREEN_CLOUD).activate(false);
    }
}
