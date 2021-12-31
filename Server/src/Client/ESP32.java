package Client;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;

public class ESP32 extends Client{
    @Override
    public void start(Socket socket,
                      ArrayList<String> pkt,
                      InputStream inputStream,
                      OutputStream outputStream, ArrayList<Integer> DATA_NUMBER) throws IOException {
        int len = 0, n = 0;
        byte b[] = new byte[Client.BUFFER_SIZE];

        while(true){
            // 接收数据
            len = inputStream.read(b);

            //移除旧数据，加入新数据
            if (pkt.size() != 0) {
                pkt.remove(0);
            }
            pkt.add(new String(b, 0, len));
            n = DATA_NUMBER.get(0);
            DATA_NUMBER.set(0, ++n);
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
