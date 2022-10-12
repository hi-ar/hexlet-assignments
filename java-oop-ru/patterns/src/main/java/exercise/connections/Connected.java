package exercise.connections;

import exercise.TcpConnection;

// BEGIN
public class Connected implements Connection {
    private TcpConnection tcpCon;

    public Connected(TcpConnection tcp) {
        this.tcpCon = tcp;
    }

    @Override
    public void connect() {
        System.out.println("Error! Try to connect when connection already established.");
    }

    @Override
    public String getCurrentState() {
        return "connected";
    }

    @Override
    public void write(String data) {
        System.out.println(tcpCon.getIpAddress() + ":" + tcpCon.getPort()
                + " writing " + data);
    }

    @Override
    public void disconnect() {
        tcpCon.setState(new Disconnected(tcpCon));
        System.out.println("Disconnected");
    }
}
// END
