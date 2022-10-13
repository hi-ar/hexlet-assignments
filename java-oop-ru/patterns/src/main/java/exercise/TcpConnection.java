package exercise;

import exercise.connections.Connection;
import exercise.connections.Disconnected;

import java.util.ArrayList;
import java.util.List;

// BEGIN
public class TcpConnection implements Connection { //connect, discon, write, getState
    private Connection state; //Disconnected(this tcpCon), Connected(this tcpCon)
    private String ipAddress;
    private int port;

    private List<String> buffer = new ArrayList<>();

    public TcpConnection(String ipAddress, int port) { //конструктор создает Disconnected(this tcpCon)
        this.state = new Disconnected(this);
        this.ipAddress = ipAddress;
        this.port = port;
    }

    public void addToBuffer(String data) {
        buffer.add(data);
    }

    public void connect() {
        this.getState().connect();
    }

    public Connection getState() {
        return this.state;
    }

    public String getCurrentState() {
        return this.getState().getCurrentState();
    }

    @Override
    public void write(String data) {
        this.getState().write(data);
    }

    @Override
    public void disconnect() {
        this.getState().disconnect();
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
