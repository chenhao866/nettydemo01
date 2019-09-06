package Client;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.string.StringEncoder;
import java.nio.charset.Charset;
public class clientInitializer extends ChannelInitializer<SocketChannel> {
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        socketChannel.pipeline().addLast(new StringEncoder(Charset.forName("GBK")));
        //socketChannel.pipeline().addLast(new StringEncoder(Charset.forName("UTF-8")));
        socketChannel.pipeline().addLast(new clientHandler()); // 客户端触发操作,处理客户端回调
    }
}
