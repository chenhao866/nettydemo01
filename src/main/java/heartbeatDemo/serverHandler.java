package heartbeatDemo;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleStateEvent;

public class serverHandler extends ChannelInboundHandlerAdapter{
    //用于触发心跳检查事件
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        //如果当前事件是心跳处理Handler
        if (evt instanceof IdleStateEvent){
            IdleStateEvent event = (IdleStateEvent) evt;
            String evenType = "";
            switch (event.state()){
                case READER_IDLE:
                    evenType="读空闲";
                    break;
                case WRITER_IDLE:
                    evenType="写空闲";
                    break;
                case ALL_IDLE:
                    evenType="度写空闲";
                    break;
            }
            System.out.println(ctx.channel().remoteAddress()+"当前超时事件: "+evenType);
            ctx.channel().close();
        }
        super.userEventTriggered(ctx, evt);
    }
}
