package heartbeatDemo;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleStateEvent;

public class serverHandler extends ChannelInboundHandlerAdapter{
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        //如果当前事件是心跳处理Handler
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent event = (IdleStateEvent) evt;
            String evenType = "";
            switch (event.state()) {
                case READER_IDLE:
                    evenType = "读空闲";
                    ctx.channel().close();//如果超时关闭连接
                    break;
                case WRITER_IDLE:
                    evenType = "写空闲";
                    ctx.channel().close();
                    break;
                case ALL_IDLE:
                    evenType = "度写空闲";
                    ctx.channel().close();
                    break;
            }
            System.out.println(ctx.channel().remoteAddress() + "当前超时事件: " + evenType);
            ctx.channel().close();
            super.userEventTriggered(ctx, evt);
        }
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf buf = (ByteBuf) msg;
        byte[] buffer = new byte[buf.readableBytes()];
        buf.readBytes(buffer, 0, buffer.length);
        String rev =new String( buffer);
        System.out.println("服务端收到数据:" + rev);
        ctx.writeAndFlush("Fan Hui Xin Tiao");
        super.channelRead(ctx, msg);
    }
}
