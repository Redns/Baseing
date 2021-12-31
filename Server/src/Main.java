import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

/**
 * 1.主函数, 启动服务器并监听 SERVER_PORT 端口, 并为每个连接
 *   服务器的客户端创建线程。
 * 2.目前本项目的客户端包含 ESP32、Android、VOFA+ 桌面上位机,
 *   服务器对客户端的处理均调用 Client 文件夹下各个客户端子类
 *   中的 start 和 stop 方法实现。
 * 3.若需增添客户端, 请在 Client 文件夹下创建相应子类并继承
 *   Client 父类
 */
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
