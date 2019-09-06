package Client;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import java.io.BufferedReader;
import java.io.InputStreamReader;
//客户端
public class client {
    public static void main(String[] args) throws Exception {
        //声明一个线程组，监听服务端数据来玩
        EventLoopGroup eventLoopGroup = new NioEventLoopGroup();
        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(eventLoopGroup).channel(NioSocketChannel.class)
                    .handler(new clientInitializer());
//            ChannelFuture channelFuture = bootstrap.connect("127.0.0.1",8888).sync();
//            channelFuture.channel().closeFuture().sync();
            //监听获取控制台输入，用于返回服务器
            Channel channel = bootstrap.connect("127.0.0.1",8888).sync().channel();
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            for (;;){
                channel.writeAndFlush(br.readLine());
            }
        }finally {
            eventLoopGroup.shutdownGracefully();
        }
    }
}
