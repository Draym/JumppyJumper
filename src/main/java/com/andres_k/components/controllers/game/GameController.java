package com.andres_k.components.controllers.game;

import com.andres_k.components.camera.CameraController;
import com.andres_k.components.controllers.EMode;
import com.andres_k.components.controllers.WindowController;
import com.andres_k.components.eventComponent.input.EInput;
import com.andres_k.components.eventComponent.input.InputGame;
import com.andres_k.components.gameComponents.gameObject.EGameObject;
import com.andres_k.components.gameComponents.gameObject.GameObject;
import com.andres_k.components.gameComponents.gameObject.GameObjectController;
import com.andres_k.components.gameComponents.gameObject.objects.Player;
import com.andres_k.components.graphicComponents.background.EBackground;
import com.andres_k.components.graphicComponents.background.wallpaper.Wallpaper;
import com.andres_k.components.graphicComponents.background.wallpaper.WallpaperSliding;
import com.andres_k.components.graphicComponents.trailer.FinalGameTrailer;
import com.andres_k.components.graphicComponents.userInterface.elementGUI.elements.ElementFactory;
import com.andres_k.components.networkComponents.networkGame.NetworkController;
import com.andres_k.components.networkComponents.networkGame.NetworkProfile;
import com.andres_k.components.networkComponents.networkSend.MessageModel;
import com.andres_k.components.networkComponents.networkSend.messageServer.*;
import com.andres_k.components.resourceComponent.fonts.EFont;
import com.andres_k.components.resourceComponent.resources.ResourceManager;
import com.andres_k.components.resourceComponent.sounds.ESound;
import com.andres_k.components.resourceComponent.sounds.SoundController;
import com.andres_k.components.taskComponent.CentralTaskManager;
import com.andres_k.components.taskComponent.ELocation;
import com.andres_k.components.taskComponent.ETaskType;
import com.andres_k.components.taskComponent.TaskFactory;
import com.andres_k.components.taskComponent.utils.TaskComponent;
import com.andres_k.utils.configs.GameConfig;
import com.andres_k.utils.stockage.Pair;
import com.andres_k.utils.tools.ColorTools;
import com.andres_k.utils.tools.Console;
import com.andres_k.utils.tools.DateTools;
import org.codehaus.jettison.json.JSONException;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

import java.util.*;

/**
 * Created by andres_k on 08/07/2015.
 */
public class GameController extends WindowController {
    private InputGame inputGame;
    private List<EGameObject> playerTypes;
    private SpectatorGodController spectatorGodController;
    private FinalGameTrailer finalTrailer;
    private boolean pause;
    private boolean gameStarted;
    private boolean gameFinish;

    public GameController(int idWindow) throws JSONException {
        super(ELocation.GAME_CONTROLLER, idWindow);
        this.inputGame = new InputGame();
        this.spectatorGodController = new SpectatorGodController();
        this.playerTypes = new ArrayList<>();
        this.finalTrailer = new FinalGameTrailer();
    }

    @Override
    public void enter() throws SlickException {
        this.gameFinish = false;
        this.gameStarted = false;
        this.pause = false;
        CameraController.get().init();
        this.spectatorGodController.reset();
        this.resetFinalTrailer();
        GameObjectController.get().enter();
        this.createPlayerForGame();
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                SoundController.get().play(ESound.EFFECT_FIGHT);
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
    public void leave() {
        this.pause = false;
        this.gameStarted = false;
        this.gameFinish = true;
        GameObjectController.get().leave();
        if (NetworkController.get().isConnected()) {
            NetworkController.get().disconnect();
        }
    }

    @Override
    public void init() throws SlickException {
        GameObjectController.get().init();
        this.spectatorGodController.init();
        this.backgroundManager.addComponent(EBackground.MAP_1, new WallpaperSliding(ResourceManager.get().getBackgroundAnimator(EBackground.BACKGROUND_BLOCK_1), 30, true, true));
         this.backgroundManager.addComponent(EBackground.FINAL_SCREEN_CLOUD, new WallpaperSliding(ResourceManager.get().getBackgroundAnimator(EBackground.FINAL_SCREEN_CLOUD), 10, true, true, 1));
        this.backgroundManager.addComponent(EBackground.FINAL_SCREEN, new Wallpaper(ResourceManager.get().getBackgroundAnimator(EBackground.FINAL_SCREEN)));
        this.backgroundManager.getComponent(EBackground.FINAL_SCREEN_CLOUD).activate(false);
        this.backgroundManager.getComponent(EBackground.FINAL_SCREEN).activate(false);
    }

    @Override
    public void renderWindow(Graphics g) throws SlickException {
        this.backgroundManager.draw(g);

        if (!this.finalTrailer.isStarted()) {
            this.spectatorGodController.draw(g);
            GameObjectController.get().draw(g);
        } else {
            this.finalTrailer.draw(g);
        }
    }

    @Override
    public void update(GameContainer gameContainer) throws SlickException {
        if (!this.pause) {
            this.backgroundManager.update();
            if (GameObjectController.get().isTheEndOfTheGame() && !this.finalTrailer.isStarted()) {
                this.launchFinalTrailer();
            }
        }
        if (!this.gameFinish && !this.pause && this.gameStarted) {
            this.spectatorGodController.update();
            GameObjectController.get().update(!this.pause);
        }
        if (this.finalTrailer.isFinished()) {
            this.endOfTheGame();
        }
        // if final trailer started
        this.finalTrailer.update();
    }

    @Override
    public void beforeEnter() {
        this.playerTypes.clear();
        this.playerTypes.addAll(GameConfig.typePlayer);
    }

    @Override
    public void keyPressed(int key, char c) {
        if (!this.pause) {
            EInput result = this.inputGame.checkInput(key);
            if (!this.spectatorGodController.keyPressed(result)) {
                GameObjectController.get().event(EInput.KEY_PRESSED, result);
            }
        }
    }

    @Override
    public void keyReleased(int key, char c) {
        if (key == Input.KEY_ESCAPE && this.gameStarted) {
            this.pause = !this.pause;
        }
        if (!this.pause) {
            EInput result = this.inputGame.checkInput(key);
            if (!this.spectatorGodController.keyReleased(result)) {
                GameObjectController.get().event(EInput.KEY_RELEASED, result);
            }
        }
    }

    @Override
    public void eventControllerReceived(EInput event, EInput input) {
        GameObjectController.get().event(event, input);
    }

    @Override
    public void mousePressed(int button, int x, int y) {
        if (!this.pause) {
            this.spectatorGodController.mousePressed(button, x, y);
        }
    }

    @Override
    public void mouseReleased(int button, int x, int y) {
        if (!this.pause) {
            this.spectatorGodController.mouseReleased(button, x, y);
        }
    }

    @Override
    public void update(Observable o, Object arg) {
        if (arg instanceof TaskComponent) {
            Console.write("GAME CONTROLLER  -> " + arg);
            TaskComponent received = (TaskComponent) arg;
            if (received.getTarget().equals(this.location)) {
                if (received.getTask() instanceof MessageModel) {
                    try {
                        this.resolveNetworkTask((MessageModel) received.getTask());
                    } catch (SlickException e) {
                        e.printStackTrace();
                    }
                } else if (received.getTask() instanceof Pair) {
                    if (((Pair) received.getTask()).getV1() == ETaskType.CREATE && ((Pair) received.getTask()).getV2() instanceof GameObject) {
                        GameObjectController.get().addEntity((GameObject) ((Pair) received.getTask()).getV2());
                        return;
                    } else if (((Pair) received.getTask()).getV1() instanceof String) {
                        GameObjectController.get().taskForPlayer((String) ((Pair) received.getTask()).getV1(), ((Pair) received.getTask()).getV2());
                        return;
                    }
                } else if (received.getTask() instanceof  ETaskType) {
                    if (received.getTask() == ETaskType.START_ACTIVITY) {
                        this.pause = false;
                    }
                }
            }
        }
        super.update(o, arg);
    }

    private void launchFinalTrailer() throws SlickException {
        if (GameObjectController.get().getWinnerSlimes() > 0) {
            this.finalTrailer.initTrailer(GameObjectController.get().getWinnerSlimes());
            this.finalTrailer.launchTrailer();
            this.backgroundManager.getComponent(EBackground.MAP_1).activate(false);
            this.backgroundManager.getComponent(EBackground.FINAL_SCREEN).activate(true);
            this.backgroundManager.getComponent(EBackground.FINAL_SCREEN_CLOUD).activate(true);
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

    private void endOfTheGame() {
        if (!this.gameFinish) {
            CentralTaskManager.get().sendRequest(TaskFactory.createTask(this.location, ELocation.GAME_GUI_PanelQuit, ETaskType.START_ACTIVITY));

            this.gameFinish = true;
            int winners = GameObjectController.get().getWinnerSlimes();
            if (winners > 0) {
                NetworkController.get().sendMessage("player1", new MessageGameEnd("", ""));
                if (GameConfig.mode == EMode.ONLINE) {
                    this.endOfTheGameForcedByNetwork("player1", "unknown", "");
                } else {
                    CentralTaskManager.get().sendRequest(TaskFactory.createTask(this.location, ELocation.GAME_GUI_PanelQuit_Details, new Pair<>(ETaskType.ADD, ElementFactory.createText("Good Job ! ", ColorTools.get(ColorTools.Colors.GUI_GREEN), EFont.MODERN, 50, 70, 5))));
                    CentralTaskManager.get().sendRequest(TaskFactory.createTask(this.location, ELocation.GAME_GUI_PanelQuit_Details, new Pair<>(ETaskType.ADD, ElementFactory.createText(winners + " slimes survived", ColorTools.get(ColorTools.Colors.GUI_BLUE), EFont.MODERN, 15, 100, 30))));
                    CentralTaskManager.get().sendRequest(TaskFactory.createTask(this.location, ELocation.GAME_GUI_PanelQuit_Details, new Pair<>(ETaskType.ADD, ElementFactory.createText("you get " + GameObjectController.get().bonusPoint + " super coin!", ColorTools.get(ColorTools.Colors.GUI_YELLOW_BLAND), EFont.MODERN, 15, 100, 45))));
                    CentralTaskManager.get().sendRequest(TaskFactory.createTask(this.location, ELocation.GAME_GUI_PanelQuit_Details, new Pair<>(ETaskType.ADD, ElementFactory.createText("Total score : " + GameObjectController.get().bonusPoint, ColorTools.get(ColorTools.Colors.GUI_YELLOW_BLAND), EFont.MODERN, 20, 110, 70))));
                }
            } else {
                CentralTaskManager.get().sendRequest(TaskFactory.createTask(this.location, ELocation.GAME_GUI_PanelQuit_Details, new Pair<>(ETaskType.ADD, ElementFactory.createText("Game Over !", ColorTools.get(ColorTools.Colors.GUI_RED), EFont.MODERN, 50, 60, 5))));
                CentralTaskManager.get().sendRequest(TaskFactory.createTask(this.location, ELocation.GAME_GUI_PanelQuit_Details, new Pair<>(ETaskType.ADD, ElementFactory.createText("none of your slimes survived", ColorTools.get(ColorTools.Colors.GUI_ORANGE), EFont.MODERN, 15, 100, 30))));
                CentralTaskManager.get().sendRequest(TaskFactory.createTask(this.location, ELocation.GAME_GUI_PanelQuit_Details, new Pair<>(ETaskType.ADD, ElementFactory.createText("you get " + GameObjectController.get().bonusPoint + " super coin!", ColorTools.get(ColorTools.Colors.GUI_YELLOW_BLAND), EFont.MODERN, 15, 100, 45))));
                CentralTaskManager.get().sendRequest(TaskFactory.createTask(this.location, ELocation.GAME_GUI_PanelQuit_Details, new Pair<>(ETaskType.ADD, ElementFactory.createText("Total score : " + GameObjectController.get().bonusPoint, ColorTools.get(ColorTools.Colors.GUI_YELLOW_BLAND), EFont.MODERN, 20, 110, 70))));
            }
        }
    }

    private void endOfTheGameForcedByNetwork(String id, String pseudo, String type) {
        if (!this.gameFinish) {
            this.gameFinish = true;
            CentralTaskManager.get().sendRequest(TaskFactory.createTask(this.location, ELocation.GAME_GUI_PanelQuit, ETaskType.START_ACTIVITY));
            if (NetworkProfile.get().itsMyNetworkProfile(id) || NetworkProfile.get().itsMyGameProfile(id)) {
                CentralTaskManager.get().sendRequest(TaskFactory.createTask(this.location, ELocation.GAME_GUI_PanelQuit_Details, new Pair<>(ETaskType.ADD, ElementFactory.createText("YOU WIN !", ColorTools.get(ColorTools.Colors.GUI_GREEN), EFont.MODERN, 30, 30, 40))));
            } else {
                CentralTaskManager.get().sendRequest(TaskFactory.createTask(this.location, ELocation.GAME_GUI_PanelQuit_Details, new Pair<>(ETaskType.ADD, ElementFactory.createText("YOU LOOSE !", ColorTools.get(ColorTools.Colors.GUI_RED), EFont.MODERN, 30, 30, 40))));
            }
            CentralTaskManager.get().sendRequest(TaskFactory.createTask(this.location, ELocation.GAME_GUI_PanelQuit_Details, new Pair<>(ETaskType.ADD, ElementFactory.createText("Winner : " + pseudo + " with " + type, ColorTools.get(ColorTools.Colors.GUI_BLUE), EFont.MODERN, 20, 20, 80))));
        }
    }

    public void resolveNetworkTask(MessageModel task) throws SlickException {
        Console.write("Task: " + task + "\n");
        if (task instanceof MessageNewPlayer) {
            if (NetworkProfile.get().itsMyNetworkProfile(task.getId())) {
                GameObjectController.get().createPlayer(EGameObject.getEnumByValue(((MessageNewPlayer) task).getPlayerType()), ((MessageNewPlayer) task).getGameId(), 0, (int) ((MessageNewPlayer) task).getX(), 0, (int) ((MessageNewPlayer) task).getY(), true);
                CentralTaskManager.get().sendRequest(new TaskComponent(ELocation.SERVER, ELocation.BATTLE_CONNECTION_GUI_StatusList, new Pair<>(ETaskType.ADD, ElementFactory.createText(DateTools.getCurrentDate("HH:mm:ss") + " : you are connected as " + ((MessageNewPlayer) task).getPlayerType(), ColorTools.get(ColorTools.Colors.GUI_BLUE), EFont.BASIC, 20, 5, 0))));
                CentralTaskManager.get().sendRequest(new TaskComponent(ELocation.SERVER, ELocation.BATTLE_CONNECTION_GUI_StatusList, new Pair<>(ETaskType.ADD, ElementFactory.createText(DateTools.getCurrentDate("HH:mm:ss") + " : waiting " + (4 - GameObjectController.get().getNumberPlayers()) + " players(s)", ColorTools.get(ColorTools.Colors.GUI_BLUE), EFont.BASIC, 20, 5, 0))));
            } else {
                GameObjectController.get().createEntity(EGameObject.getEnumByValue(((MessageNewPlayer) task).getPlayerType()), task.getId(), 0, (int) ((MessageNewPlayer) task).getX(), 0, (int) ((MessageNewPlayer) task).getY());
                CentralTaskManager.get().sendRequest(new TaskComponent(ELocation.SERVER, ELocation.BATTLE_CONNECTION_GUI_StatusList, new Pair<>(ETaskType.ADD, ElementFactory.createText(DateTools.getCurrentDate("HH:mm:ss") + " : another players is connected as " + ((MessageNewPlayer) task).getPlayerType(), ColorTools.get(ColorTools.Colors.GUI_BLUE), EFont.BASIC, 20, 5, 0))));
                CentralTaskManager.get().sendRequest(new TaskComponent(ELocation.SERVER, ELocation.BATTLE_CONNECTION_GUI_StatusList, new Pair<>(ETaskType.ADD, ElementFactory.createText(DateTools.getCurrentDate("HH:mm:ss") + " : waiting " + (4 - GameObjectController.get().getNumberPlayers()) + " players(s)", ColorTools.get(ColorTools.Colors.GUI_BLUE), EFont.BASIC, 20, 5, 0))));
            }
        } else if (task instanceof MessageDeletePlayer) {
            GameObjectController.get().deleteEntity(task.getId());
        } else if (task instanceof MessageActionPlayer) {
            GameObject player = GameObjectController.get().getObjectById(task.getId());
            if (player != null && player.getAnimatorController() != null) {
                player.getAnimatorController().forceCurrentAnimationType(((MessageActionPlayer) task).getAction());
                player.getAnimatorController().forceCurrentAnimationIndex(((MessageActionPlayer) task).getIndex());
            }
        } else if (task instanceof MessageStatePlayer) {
            GameObject object = GameObjectController.get().getObjectById(task.getId());
            if (object != null && object instanceof Player) {
                Player player = (Player) object;
                MessageStatePlayer state = (MessageStatePlayer) task;
                player.setCurrentLife(state.getLife());
                player.getMovement().setPositions(state.getX(), state.getY());
            }
        } else if (task instanceof MessageInputPlayer) {
            GameObject object = GameObjectController.get().getObjectById(task.getId());

            if (object != null) {
                if (((MessageInputPlayer) task).getEvent() == EInput.KEY_RELEASED) {
                    object.eventReleased(((MessageInputPlayer) task).getInput());
                } else if (((MessageInputPlayer) task).getEvent() == EInput.KEY_PRESSED) {
                    object.eventPressed(((MessageInputPlayer) task).getInput());
                }
            }
        } else if (task instanceof MessageGameEnd) {
            this.endOfTheGameForcedByNetwork(task.getId(), ((MessageGameEnd) task).getWinner(), ((MessageGameEnd) task).getWinnerType());
        }
    }

    public void createPlayerForGame() throws SlickException {
        this.playerTypes.clear();
        this.playerTypes.add(EGameObject.SLIME);
        this.playerTypes.add(EGameObject.SLIME);
        this.playerTypes.add(EGameObject.SLIME);
        this.playerTypes.add(EGameObject.SLIME);
        GameObjectController.get().createPlayers(this.playerTypes);
    }
}
