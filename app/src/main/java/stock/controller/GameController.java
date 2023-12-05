package stock.controller;

import stock.ControllerInterface;
import stock.model.*;
import stock.view.*;
import java.util.ArrayList;
import java.util.Random;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;


public class GameController implements ControllerInterface{
    startUI start;
    Gui game;
    GameManager gm;
    User user;
    Market market;
    EventRoller roller;
    

    public GameController(){
        this.start = new startUI(this);
        this.gm = new GameManager();
        

    }

    public void userChoose(String mode){

        File file = new File("events.txt");
        ArrayList<ArrayList<String>> events = new ArrayList<ArrayList<String>>();

        //creating the 3 lists, for the 3 kinds of event types
        for(int i = 0 ; i <2 ;i++ ){
            ArrayList<String> a = new ArrayList<String>();
            events.add(a);
        }
        try {

            //read the file and parse each line, checking if it should be added to events
            BufferedReader br = new BufferedReader(new FileReader(file));
            String event;
            while((event = br.readLine()) != null){
                int x = 0;
                switch(event) {

                    case "#MarketEvents":
                            x = 0;
                        break;
                    case "#UserEvents":
                            x = 1;
                        break;
                    default:
                        break;
                }
                    //adds events to their proper list, only reads in if it is longer than length 2
                    event = br.readLine();
                    while(event.length() >= 2){
                        events.get(x).add(event);
                        event = br.readLine();
                    }
            }
        }
        catch (Exception e){
            System.out.println("FILE DOES NOT EXIST, EVENTS CANNOT LOAD");
        }


        this.roller = new EventRoller(events);
        this.gm = new GameManager();

        this.user = new User(gm.get_avg());
        
        

        if (mode.equals("easy")) {
            user.setcurrentDebt((float) 100.00);
        }else if (mode.equals("medium")){
            user.setcurrentDebt((float) 1000.00);
        }else if (mode.equals("hard")){
            user.setcurrentDebt((float) 10000.00);
        }
        user.setCapital((float) 1000.00);
        user.setsuspicionOfSEC(50);
        this.game = new Gui(this,gm, roller);
    }

    public Market getMarket(){
        return gm.getMarket();
    }

    public User getUser(){
        return this.user;
    }

    public boolean buy(String ticker, float price, int amount){
        
        return user.buyStock(ticker, price, amount);
       
    }

    public boolean sell(String ticker, float price, int amount){
         return user.sellStock(ticker, price, amount);
    }


    public void nextday(){
        ArrayList<String> currentEvents = roller.roll_out();
        gm.next_day(currentEvents.get(0));
        user.process_event(currentEvents.get(1));
        game.update();



    }


}
