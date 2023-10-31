package stock.controller;

public class EventLog {

    private Algorithm algorithm_parent;

    public EventLog(Algorithm algorithm_parent){
        this.algorithm_parent = algorithm_parent;
    }

    public class Event{
        public
            int affect;
            string desc;
            public Event(int affect,String desc){ this.affect = affect; this.desc = desc;}
    }

    public void roll_event(){

        //is this.algorithm_parent.
    }
    
}
