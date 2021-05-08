import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

/*
 * Target: 无限示波表服务器，从下位机接受数据，并将数据分发给电脑上位机VOFA+、手机上位机WO.
 *         数据传输协议:
 *                      1.设备在连接服务器后需要发送ID码以供服务器区分身份
 *                      2.在发送数据前需要发送 "READY" 告知接收方
 *                      3.每个数据包由203个数据组成，相邻数据间用空格隔开
 *                      4.数据包内容为: "Freq Vrms Vpp point1 point2 ... point200"
 * Author: Redns
 * Date: 2021/5/8 18:50
 * */
public class Server_WO {
    public static void main(String[] args) throws IOException {
        final int SERVER_PORT = 8888;

        //建立服务器
        ServerSocket server = new ServerSocket(SERVER_PORT);
        ArrayList<String> pkt = new ArrayList<>();

        while(true){
            Socket socket = server.accept();
            new Thread(new Server(socket, pkt)).start();
        }
    }
}
