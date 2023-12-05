package stock.controller;

import stock.ControllerInterface;
import stock.model.*;
import stock.view.*;


public class GameController implements ControllerInterface{
    startUI start;
    Gui game;
    GameManager gm;
    User player;
    Market market;
    

    public GameController(){
        this.start = new startUI(this);

    }

    public void userChoose(String mode){
        
        player = new User((float)100);
        
        

        if (mode.equals("easy")) {
            player.setcurrentDebt((float) 100.00);
        }else if (mode.equals("medium")){
            player.setcurrentDebt((float) 1000.00);
        }else if (mode.equals("hard")){
            player.setcurrentDebt((float) 10000.00);
        }
        player.setCapital((float) 1000.00);
        player.setsuspicionOfSEC(50);
        this.game = new Gui(this,player);
    }

    public void initializeState(){


    }
}
