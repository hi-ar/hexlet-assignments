package exercise;

import exercise.connections.Connected;
import exercise.connections.Connection;
import exercise.connections.Disconnected;

// BEGIN
public class TcpConnection implements Connection { //нужно ли ему импл интерф?
    private Connection state;
    private String ipAddress = "";
    private int port = 0;

    public TcpConnection(String ipAddress, int port) { //конструктор
        this.state = new Disconnected(this);
        this.ipAddress = ipAddress;
        this.port = port;
    }

    public void connect() {
        this.getCurrentState().connect();
    }

    @Override
    public Connection getCurrentState() {
        return this.getCurrentState().getCurrentState();
    }

    @Override
    public void write(String data) {
        this.getCurrentState().write(data);
    }

    @Override
    public void disconnect() {
        this.getCurrentState().disconnect();
    }

    public void setState(Connection newState) {
        this.state = newState;
    }

    public int getPort() {
        return port;
    }

    public String getIpAddress() {
        return ipAddress;
    }
}
// END
