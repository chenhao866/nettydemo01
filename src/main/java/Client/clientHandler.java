package Client;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
public class clientHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf buf = (ByteBuf) msg;
        byte[] buffer = new byte[buf.readableBytes()];
        buf.readBytes(buffer, 0, buffer.length);
        String rev = new String(buffer);
        System.out.println("客戶端收到数据:" + rev);
       // ctx.writeAndFlush(rev);//项服务器返回信息
        //ctx.channel().close();//如果客户端要直接退出，需要关闭连接，这样就退出不会报错了。
        super.channelRead(ctx, msg);
    }
}
