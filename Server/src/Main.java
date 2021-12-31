import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Main {
    public static final int SERVER_PORT = 8888;     // 服务器端口号
    public static void main(String[] args) throws IOException {
        // 建立服务器
        ServerSocket server = new ServerSocket(SERVER_PORT);
        ArrayList<String> pkt = new ArrayList<>();
        ArrayList<Integer> DATA_NUMBER = new ArrayList<>();

        // 为每个客户端单独开设线程
        DATA_NUMBER.add(0);
        while(true){
            Socket socket = server.accept();
            new Thread(new Server(socket, pkt, DATA_NUMBER)).start();
        }
    }
}
