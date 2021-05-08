import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class Server implements Runnable{
    private Socket socket;
    private ArrayList<String> pkt;

    public Server(Socket socket, ArrayList<String> pkt){
        this.socket = socket;
        this.pkt = pkt;
    }

    @Override
    public void run() {
        try {
            byte[] b = new byte[3000];
            InputStream input  = socket.getInputStream();
            OutputStream output = socket.getOutputStream();

            //通过IC码区分设备
            int len = input.read(b);
            String IC = new String(b, 0, len);

            //连接设备为VOFA+
            if(IC.equals("VOFA+")) {
                System.out.println("VOFA+ Collected!");
                while(true){
                    //500ms更新一次数据
                    Thread.sleep(480);

                    //准备发送数据
                    if(pkt.size() != 0){
                        output.write("READY".getBytes(StandardCharsets.UTF_8));
                        Thread.sleep(20);
                        output.write(pkt.get(0).getBytes(StandardCharsets.UTF_8));
                    }
                }
            }

            //连接设备为Phone
            else if(IC.equals("Phone")) {
                System.out.println("Phone Collected!");
                while (true){
                    //500ms更新一次数据
                    Thread.sleep(480);

                    if(pkt.size() != 0){
                        //准备发送数据
                        output.write("READY".getBytes(StandardCharsets.UTF_8));
                        Thread.sleep(20);
                        output.write(pkt.get(0).getBytes(StandardCharsets.UTF_8));
                    }
                }
            }

            //连接设备为下位机
            else if(IC.equals("Lower Computer")) {
                System.out.println("Lower Computer Collected!");
                while (true){
                    len = input.read(b);

                    //准备接收数据
                    if(new String(b, 0, len).equals("READY")){
                        len = input.read(b);

                        //移除旧数据，加入新数据
                        if (pkt.size() != 0) {
                            pkt.remove(0);
                        }
                        pkt.add(new String(b, 0, len));
                    }
                }
            }

            //无法识别
            else {
                System.out.println("Unrecognized device!");
            }

        } catch (IOException | InterruptedException e) {
            //连接已断开
            System.out.println("The collection is broken.");
        }
    }
}
