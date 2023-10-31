package stock.model;

public class EventLog {

    Algorithm algorithm_parent;

    public EventLog(Algorithm algorithm_parent){
        this.algorithm_parent = algorithm_parent;
    }

    public class Event{
        public:
            int affect;
            String desc;
            public Event(int affect,String desc){ this.affect = affect; this.desc = desc;}
    }

    public roll_event()
    
}
