package com.limingliang.io;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;

public class IOClient {
    public static void main(String[] args) throws IOException {
        Socket socket = new Socket();
        SocketAddress socketAddress = new InetSocketAddress(10101);
        socket.connect(socketAddress);

        //发送数据
        BufferedOutputStream bos = new BufferedOutputStream(socket.getOutputStream());
        bos.write("reqData".getBytes("GBK"));
        bos.flush();

        //接收返回数据
        BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        String respStr = br.readLine();
        System.out.println("respStr is:>>>>>>"+respStr);

    }
}
