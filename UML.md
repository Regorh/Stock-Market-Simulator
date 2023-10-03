classDiagram
    Market -- Algorithm
    Event -- Algorithm
    Stocks -- Market
    Trader -- Controller
    Controller -- Market
    GUI -- Controller
    SEC -- Trader
    StatusEffect -- Trader
    EventLog -- Event

    class Trader {
        +int money
        +float debt
        +float sec_awareness
        +Vector_Stocks stocks
        +Vector_StatusEffect status_effects
    }
    class Controller {
        TBD
    }
    class Event {
        +EventLog log
        +void roll_event()
    }
    class StatusEffect {
        TBD
    }
    class SEC {
        TBD
    }
    class EventLog {
        +Vector_Event history
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
    class GUI {
        +void draw_profile()
        +void draw_market()
        
    }