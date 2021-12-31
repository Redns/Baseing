package Client;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class Phone extends Client{
    /**
     * 客户端(手机)启动函数
     * @param socket tcp端口号
     * @param pkt 数据存储数组
     * @param inputStream tcp输入缓冲流
     * @param outputStream tcp输出缓冲流
     * @param DATA_NUMBER
     */
    @Override
    public void start(Socket socket,
                      ArrayList<String> pkt,
                      InputStream inputStream,
                      OutputStream outputStream, ArrayList<Integer> DATA_NUMBER) throws IOException {
        long DATA_SEND = 0;
        while(true){
            if(DATA_SEND < DATA_NUMBER.get(0)){
                outputStream.write(pkt.get(0).getBytes(StandardCharsets.UTF_8));
                DATA_SEND = DATA_NUMBER.get(0);
            }
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
