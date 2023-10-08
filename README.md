# Stock Market Simulator
## Overview
Stock Market Simulator is a Java-based computer game that simulates the dynamics of the stock market. In this game, players take on the role of traders, making financial decisions, managing their portfolio, and navigating various challenges in the stock market environment.

## System Requirements
Java SE Development Kit (JDK) 19 or later

Git Bash 2.42 or Comparable version

Compatible with Windows, macOS, and Linux

Gradle 7.6.1 or Comparable version
## Getting Started
### Clone the Repository
```
git clone https://github.com/Regorh/Stock-Market-Simulator.git
```
### Navigate to the Project Folder
Use the cd (change directory) command to navigate to the proper folder for the project. An example is below:
```
cd stock-market-simulator
```
### Compile the Code Using Gradle
```
./gradlew build
```
### Run the Game Using Gradle
```
./gradlew run
```
### How to Play
Use the graphical user interface (GUI) to interact with the game.
Buy and sell stocks, manage your finances, and try to outperform the market while avoiding the consequences of your more nefarious actions.
## Features
Simulated stock market with various stocks and market dynamics.
Player attributes include money, stress, and debt.
Random events affect the market and the player's decisions.
SEC (Securities and Exchange Commission) investigations and legal consequences for player actions.
Player illnesses that influence decision-making.
User-friendly GUI for an engaging gameplay experience.

## High Level Design

Classes:
User:
This will act as the player for the game
This class shall have the following
* A float representing the amount of money the user has
* A vector/ tree/ data structure(TBD) to hold all of the stocks 
* A float representing suspicion of SEC
* A float representing current debt
* The user shall also have a list of illnesses. 
* The user shall also have a integer measure of stress, stress causes illnesses
* All of the former values shall have get/set functions
* The player will be able to sabotage and impact the market and specific stocks by shorting or paying specific amounts to a company to fulfill their desires. 
* If stress reaches a max, the player loses and goes into hiding,
* If debt reaches a max/ min payment cannot be met, the mob gets to the player, and the player loses



Illnesses:
* This represents the negative effects that can be applied to a player based on the player stress
* Each illness will have a different probability of happening. 
* These illnesses will change the way the player can see their portfolio and the decisions they make.
* The poison illness will make you read and understand the stock tickers incorrectly.  
* The anxiety attack illness will make you sell all your stock.
* The charabism illness will cause you to not sell your stock in good times due to disbelief it is occuring. 


Stocks:
These will act as the in-game counterpart to real life stocks, they shall have either real life names or randomized ones,rand prices, rand typing (Oil,Tech,Agriculture) and rand stability
For example, walmart stock stability vs penny stock stability
* These shall have a type as a string, or int value, that can be easily searched and sorted by market
* This shall have a price, each stock has a different prices that are changed via events
* The price can also be changed by the algorithm, via setter and getter functions
* The price shall be affected by events that occur each turn/year
* The Stock shall also have a stability rating, representing its market volatility

	

Market:
* This class shall act as The/the various stock markets available to invest in
* This class shall contain the following
* An integer to determine the stability of the market, and its stocks
* A market shall also contain stocks
* The market shall be able to search through its stocks, when prompted
* The market shall have its stability affected by events
* The stability of the market shall affect the stability of the stocks

Algorithm:
* This class shall be where events, markets, and stocks are taken into account and then based on what is put into the algorithm, randomization + events + weights shall edit and mold the stocks and markets based on a result. EX: if I give a market and an event of a crash to the algorithm, the algorithm shall then change the stability of the market, and the price of its stocks to a lower number than before, thus representing a real life crash. The algorithm in its current state represents multiple classes and functions to be broken up later
* The algorithm shall have the ability to take in events as a weight
* The algorithm shall have default weights to if a market will go up or down
* The magnitude of market change shall be determined in the algorithm given the stability of the market
* The algorithm, shall have the ability to change the price of a market’s stock, through weights on each stock, 
* The weights on each stock are affected by their type, stability, and current price
* The algorithm will edit the weights and stocks to create a semi unpredictable, market

Events per Year:
* The purpose of this class is to create random events/weights to the algorithm, such as a market crash, which will weigh the markets negatively. 
* This class shall hold multiple pre-decided events. 
* These events shall be cycled through randomly. 
* The events will be chosen randomly at most times, but there will be predetermined events that will occur if conditions in the market are met. 
* These events that are occurring will change the stability of our market. 
* Along with the stability, it will impact all or certain stocks depending on the event that is occurring. 
* When events are chosen and ready to be implemented into the market, it will make changes occur on the UI such as the stock prices listed, color of different user controls, and display of description. 
* These events will change the stability and more importantly the algorithm that is being implemented in our program. 
	



GUI
* The GUI will present the user with their trader profile.
* This profile will entail the stocks they hold, the markets they can trade in, and their overall profile. 
* The overall profile in the GUI will show the trader their debt, FCC awareness, and the amount of capital they hold. 
* It will have buttons which the trader can click to buy, sell, and perform other functions with their portfolio.
* It will also hold text boxes in which the trader can type in the amount they would like to buy or sell of a stock. 
* This will also show a list of events that have made an impact on the market


SEC:
* The purpose of the SEC is to investigate the player’s illegal actions, or massive success
* If the player is committing illegal acts/ massive success, the SEC will begin to apply pressure, the higher the pressure on a player the higher the stress, as pressure raises, more SEC related events shall occur, such as wealth seizure, temporary arrest, fines.
* This class shall receive information on players illegal actions, 
* The SEC shall act on information based on, level of illegality/cost and status of player
SEC can be halted with bribes/ donations/ events to lower pressure
* If SEC pressure hits a max, the player will be arrested, and the game will end






```classDiagram{
    Market <|-- Algorithm
    Events <|-- Algorithm
    EventLog <|-- Events
    Stocks <|-- Market
    Trader <|-- Controller
    Controller <|-- Market
    EventRoller <|-- EventModifier
    EventLog <|-- EventRoller
    Events <|-- EventRoller
    GUI <|-- Controller
    
    class Trader {
        +int money
        +float debt
        +float sec_awareness
        +Vector_Stocks stocks
        +Vector_String status_effects

    }
    class Controller {

    }
    class Events {
        
    }
    class EventLog {
        +Vector_String history
    }
    class Market {
        +Vector_Stocks markets
        +Stocks search(string name)
    }
    class Algorithm {
        +int curr_time
        +void event_input(Event event)
        +void advance_time()
    }
    class Stocks {
        +int id
        +string name
        +float price
        +StockType type
        +float availability
    }
    class EventModifier {

    }
    class EventRoller {

    }
    class GUI {
        +void draw_profile()
        +void draw_market()
        
    }
}
```

## Contributors
Ali Elnour ali.elnour@slu.edu

Om Patel om.patel.1@slu.edu

Noah Guzinski noah.guzinski@slu.edu (nguzinskidev@gmail.com)

Rob Helme robby.helme@slu.edu
