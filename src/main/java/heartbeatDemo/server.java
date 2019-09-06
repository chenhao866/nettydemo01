package heartbeatDemo;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

//基本的心跳检查示例
public class server {
    public static void main(String[] args) throws InterruptedException {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(group, bossGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new serverInitializer());
            ChannelFuture cf = bootstrap.bind(8888).sync();
            System.out.println(" 启动正在监听： " + cf.channel().localAddress());
            cf.channel().closeFuture().sync();
        }finally {
            group.shutdownGracefully().sync();
            bossGroup.shutdownGracefully().sync();
        }
    }
}
