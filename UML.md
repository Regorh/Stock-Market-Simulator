classDiagram
    Market -- Algorithm
    Events -- Algorithm
    EventLog -- Events
    Stocks -- Market
    Trader -- Controller
    Controller -- Market
    EventRoller -- EventModifier
    EventLog -- EventRoller
    Events -- EventRoller
    GUI -- Controller

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
