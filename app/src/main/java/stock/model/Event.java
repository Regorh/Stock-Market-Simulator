package stock.model;

public record Event(String name, String description) {
    public String get_name() { return this.name; }

    public String get_description() { return this.description; }
}
