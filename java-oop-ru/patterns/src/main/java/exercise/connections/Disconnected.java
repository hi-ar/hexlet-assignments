package exercise.connections;

import exercise.TcpConnection;

// BEGIN
public class Disconnected implements Connection {
    private TcpConnection tcpCon;

    public Disconnected(TcpConnection tcp) {
        this.tcpCon = tcp;
    }

    @Override
    public void connect() {
        System.out.println("Establishing connection with " + tcpCon.getIpAddress() + ":" + tcpCon.getPort());
        tcpCon.setState(new Connected(tcpCon));
    }

    @Override
    public Connection getCurrentState() {
        System.out.println("Disconnected");
        return tcpCon.getCurrentState();
    }

    @Override
    public void write(String data) {
        System.out.println("Error! Can't write data,  connection not established");
    }

    @Override
    public void disconnect() {
        System.out.println("Error! No connection established");
    }
}
// END
