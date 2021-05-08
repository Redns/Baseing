import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

/*
* Target: 模拟下位机，向服务器发送数据包
* Author: Redns
* Date: 2021/5/8 19:24
* */
public class Test {
    public static void main(String[] args) throws IOException, InterruptedException {
        final int SERVER_PORT = 8888;
        final String SERVER_IP = "localhost";

        Socket socket = new Socket(SERVER_IP, SERVER_PORT);
        OutputStream output = socket.getOutputStream();

        //发送ID码识别身份
        output.write("Lower Computer".getBytes(StandardCharsets.UTF_8));

        //发送数据
        String pkt;
        float w = 1.0f;

        while (true){
            //500ms更新一次数据
            Thread.sleep(500);

            //发送READY信号
            output.write("READY".getBytes(StandardCharsets.UTF_8));

            //发送数据包
            pkt = "1kHz " + String.valueOf(save2(w)) + "V " + String.valueOf(save2(w * 2)) + "V";
            for(int i = 0; i < 200; i++){
                pkt = pkt + " " + String.valueOf((int)( 2 * (Math.sin(0.1 * i) + 0.3 * Math.cos(0.2 * i) + 0.1 * Math.sin(0.4 * i)) * 100) / 100.0);
            }
            output.write(pkt.getBytes(StandardCharsets.UTF_8));

            //控制台检查
            System.out.println(pkt);
            w = w + 0.2f;
        }
    }

    public static float save2(float f){
        return (float)((int)(f * 100) / 100.0);
    }
}
