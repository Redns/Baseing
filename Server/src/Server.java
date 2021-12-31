import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

/**
 * 线程实现类
 * 调用 Client 文件夹下的各个子类
 */
public class Server implements Runnable{

    private Socket socket;                      // tcp端口
    private InputStream input = null;           // 输入流
    private OutputStream output = null;         // 输出流
    private ArrayList<String> pkt;              // 数据数组
    private ArrayList<Integer> DATA_NUMBER;     // 数据更新标志位

    private String ID = null;                   // 客户端ID

    private Class clientClass;                  // 客户端子类
    private Object clientObject;                // 客户端实例
    private Method client_start, client_stop;   // 客户端方法

    public Server(Socket socket, ArrayList<String> pkt, ArrayList<Integer> DATA_NUMBER){
        this.socket = socket;
        this.pkt = pkt;
        this.DATA_NUMBER = DATA_NUMBER;
    }


    @Override
    public void run() {
        try {
            // 获取输入、输出流
            getStream();

            // 获取客户端ID
            ID = getIdentify();

            // 获取相应子类和方法
            clientClass = Class.forName("Client." + ID);
            Object clientObject = clientClass.getConstructor().newInstance();
            client_start = clientObject.getClass().getDeclaredMethod("start", new Class[]{Socket.class, ArrayList.class, InputStream.class, OutputStream.class, ArrayList.class});
            client_stop  = clientObject.getClass().getDeclaredMethod("stop", new Class[]{Socket.class});

            client_start.invoke(clientObject, socket, pkt, input, output, DATA_NUMBER);

        }  catch (ClassNotFoundException e) {
            System.out.println("[ERROR]未在Client文件夹下找到相应类!");
        } catch (InstantiationException e) {
            System.out.println("[ERROR]创建实例失败, 请检查Client下的子类!");
        } catch (InvocationTargetException e) {
            System.out.println("[ERROR]" + ID + " connection is broken.");
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取输入、输出流
     */
    public void getStream() throws IOException {
        input = socket.getInputStream();
        output = socket.getOutputStream();
    }

    /**
     * 获取客户端 ID
     * @return 客户端 ID
     */
    public String getIdentify() throws IOException {
        int len = 0;
        String ID = null;
        byte[] b = new byte[100];

        if(input == null || output == null){
            System.out.println("[ERROR]获取ID失败，请重试！");
            return null;
        }

        else {
            // 向客户端请求ID
            output.write("ID".getBytes(StandardCharsets.UTF_8));
            // 接收客户端发送的ID
            len = input.read(b);
            ID = new String(b, 0, len);
            System.out.println("[Get ID successfully]" + ID);
            return ID;
        }
    }
}
