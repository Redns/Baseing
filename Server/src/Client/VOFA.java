package Client;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

/**
 * VOFA+桌面上位机客户端, 循环接收来自服务器的数据
 */
public class VOFA extends Client{
    @Override
    public void start(Socket socket,
                      ArrayList<String> pkt,
                      InputStream inputStream,
                      OutputStream outputStream, ArrayList<Integer> DATA_NUMBER) throws IOException, InterruptedException {
        long DATA_SEND = 0, DATA_NUMBER_COPY;
        while(true){
            DATA_NUMBER_COPY = DATA_NUMBER.get(0);
            if(DATA_SEND < DATA_NUMBER_COPY){
                outputStream.write(pkt.get(0).getBytes(StandardCharsets.UTF_8));
                DATA_SEND = DATA_NUMBER_COPY;
            }
            Thread.sleep(10);
        }
    }

    @Override
    public void stop(Socket socket) throws IOException {
        socket.shutdownInput();
        socket.shutdownOutput();
        socket.close();
        System.out.println("[Client close successfully]");
    }
}
