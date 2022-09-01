package com.limingliang.io;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class IODemo {
    public static void main(String[] args) throws IOException {
        ExecutorService cacheTHreadPool = Executors.newCachedThreadPool();

        //创建socketserver
        ServerSocket server = new ServerSocket(10101);
        System.out.println("服务器启动");
        while (true) {
            final Socket socket = server.accept();
            System.out.println("来一个新客户端");
            cacheTHreadPool.execute(new Runnable() {
                @Override
                public void run() {
                    handler(socket);
                }
            });
        }
    }

    public static void handler(Socket socket) {
        try {
            byte[] bytes = new byte[1024];
            //第三步
            InputStream inputStream = null;
            inputStream = socket.getInputStream();
            while(true){
                //第四步  读取数据（阻塞）
                int read = inputStream.read(bytes);
                if(read != -1){
                    System.out.println(new String(bytes, 0, read));
                }else{
                    break;
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }finally {
            try {
                System.out.println("socket关闭");
                socket.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
