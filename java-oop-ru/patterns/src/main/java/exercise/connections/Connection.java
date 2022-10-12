package exercise.connections;

public interface Connection {
    // BEGIN
    public void connect();
    public String getCurrentState();
    public void write(String s);
    public void disconnect();
    // END
}
