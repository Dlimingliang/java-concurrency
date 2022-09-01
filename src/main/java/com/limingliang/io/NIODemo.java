package com.limingliang.io;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

public class NIODemo {
    private Selector selector;

    public void  initServer(int port) throws IOException {
        //创建费阻塞通道，绑定端口
        ServerSocketChannel server = ServerSocketChannel.open();
        server.configureBlocking(false);
        server.socket().bind(new InetSocketAddress(port));
        //创建通过管理器
        this.selector = Selector.open();
        //？
        //将select和通道绑定，并注册OP_ACCEPT时间，注册时间过，当事件到达时，select会返回，
        // 如果该事件没有到达，select.select会一直阻塞
        server.register(selector, SelectionKey.OP_ACCEPT);
    }

    public void listen() throws IOException {
        System.out.println("服务端启动成功！");
        while (true) {
            selector.select();
            Iterator<SelectionKey> iterator = this.selector.selectedKeys().iterator();
            while (iterator.hasNext()) {
                SelectionKey key = iterator.next();
                iterator.remove();
                handler(key);
            }
        }
    }

    public void handler(SelectionKey selectionKey) throws IOException {
        if (selectionKey.isAcceptable()) {
            handlerAccept(selectionKey);
        } else if (selectionKey.isReadable()) {
            handlerRead(selectionKey);
        }
    }

    public void handlerAccept(SelectionKey selectionKey) throws IOException {
        ServerSocketChannel server = (ServerSocketChannel) selectionKey.channel();
        SocketChannel channel = server.accept();
        channel.configureBlocking(false);
        System.out.println("新的客户端连接");
        channel.register(this.selector, SelectionKey.OP_READ);
    }

    public void handlerRead(SelectionKey selectionKey) throws IOException {
        SocketChannel channel = (SocketChannel) selectionKey.channel();
        // 创建读取的缓冲区
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        int read = channel.read(buffer);
        if(read > 0){
            byte[] data = buffer.array();
            String msg = new String(data).trim();
            System.out.println("服务端收到信息：" + msg);

            //回写数据
            ByteBuffer outBuffer = ByteBuffer.wrap("好的".getBytes());
            channel.write(outBuffer);// 将消息回送给客户端
        }else {
            System.out.println("客户端关闭");
            selectionKey.cancel();
        }
    }

    public static void main(String[] args) throws IOException {
        NIODemo server = new NIODemo();
        server.initServer(10101);
        server.listen();
    }
}
