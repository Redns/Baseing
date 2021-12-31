package Client;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;

/**
 * 用户创建的子类请继承 Client 父类并实现方法。客户端
 * 连接服务器后, 服务器将直接调用 start 方法; 断开连
 * 接后, 服务器将调用 stop 方法。
 */
abstract class Client {
    public static final int BUFFER_SIZE = 1024; // 缓冲区大小(字节)

    /**
     * 客户端启动函数
     * @param socket tcp端口号
     * @param pkt 数据存储数组
     * @param inputStream tcp输入缓冲流
     * @param outputStream tcp输出缓冲流
     * @param DATA_NUMBER
     */
    public abstract void start(Socket socket,
                        ArrayList<String> pkt,
                        InputStream inputStream,
                        OutputStream outputStream,
                        ArrayList<Integer> DATA_NUMBER) throws IOException, InterruptedException;


    /**
     * 客户端停止函数
     * @param socket tcp端口号
     * @throws IOException
     */
    public abstract void stop(Socket socket) throws IOException;
}
