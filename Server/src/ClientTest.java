import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class ClientTest {
    public static void main(String[] args) throws IOException, InterruptedException {
        final int SERVER_PORT = 8888;
        final String SERVER_IP = "localhost";

        Socket socket = new Socket(SERVER_IP, SERVER_PORT);
        InputStream  input  = socket.getInputStream();
        OutputStream output = socket.getOutputStream();

        byte b[] = new byte[100];
        int len = input.read(b);
        if(new String(b, 0, len).equals("ID")){
            output.write("ESP32".getBytes(StandardCharsets.UTF_8));
        }

        //发送数据
        String pkt = null;
        float w = 1.0f;

        while (true){
            //500ms更新一次数据
            Thread.sleep(500);

            //发送数据包
            for(int i = 0; i < 100; i++){
                pkt = ":" + String.valueOf((int)( 2 * (Math.sin(0.1 * i)) * 100) / 100.0) + "\n";
                output.write(pkt.getBytes(StandardCharsets.UTF_8));
                System.out.println(pkt);
                Thread.sleep(1000);
            }
        }
    }

    public static float save2(float f){
        return (float)((int)(f * 100) / 100.0);
    }
}
