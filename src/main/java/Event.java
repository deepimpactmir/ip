public class Event extends Task {
  private DateTime dateTime;

  public Event(String description, boolean isDone, String by) {
    super(description, isDone);
    this.by = by;
  }

  public Event(String description, String by) {
    super(description);
    this.dateTime = new DateTime(by);
  }

  @Override
  public String toString() {
    return String.format("[E]%s (at: %s)", super.toString(), this.dateTime.toString());
  }

  @Override
  public String[] getPrintRepresentation() {
    String[] strArray = super.getPrintRepresentation();
    return new String[]{"Event", strArray[1], strArray[2], this.by};
  }
}
