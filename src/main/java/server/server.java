package server;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
public class server {
    public static void main(String[] args) throws InterruptedException {
        // 创建两个线程池：bossGroup监听是否有连接；group监听是否有数据读写传输
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            //声明服务端实例
            ServerBootstrap bootstrap = new ServerBootstrap();
            //绑定线程池
            bootstrap.group(group, bossGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new serverInitializer());
            ChannelFuture cf = bootstrap.bind(8888).sync(); // 服务器异步创建绑定
            System.out.println(" 启动正在监听： " + cf.channel().localAddress());
            cf.channel().closeFuture().sync(); // 关闭服务器通道
        }finally {
            // 释放线程池资源
            group.shutdownGracefully().sync();
            bossGroup.shutdownGracefully().sync();
        }
    }
}
