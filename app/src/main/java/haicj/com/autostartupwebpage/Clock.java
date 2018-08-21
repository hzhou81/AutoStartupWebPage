package haicj.com.autostartupwebpage;

import android.os.Handler;
import android.os.Message;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 时钟对象
 * @author 孙汇洲
 */
public final class Clock extends Thread {
    public static final int waitTime=15000;
    // 消息键值
    private static final int MESSAGE_KEY = 1;

    // 时钟实例
    private static Clock instance = null;

    // 电池状态事件监听器列表
    private List<OnClockListener> eventListeners = null;

    // 消息响应对象
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message message) {
            super.handleMessage(message);

            switch (message.what){
                case MESSAGE_KEY:
                    Date date = new Date(System.currentTimeMillis());

                    // 唤起事件监听器
                    if (!eventListeners.isEmpty()) {
                        for (int i=0,len=eventListeners.size(); i<len; i++) {
                            eventListeners.get(i).onChange(date);
                        }
                    }
                    break;

                default:
                    break;
            }
        }
    };


    // /////////////////////////////////////////////////////////////////////////////////
    //
    // 构造方法
    //
    // /////////////////////////////////////////////////////////////////////////////////

    /**
     * 构造方法
     * @param constructor 构造器
     */
    public Clock(InnerLimitationConstructor constructor) {
        eventListeners = new ArrayList<>();
    }

    /**
     * 获取时钟实例对象
     * @return 时钟实例对象
     */
    public static Clock getInstance() {
        if (instance == null) {
            instance = new Clock(new InnerLimitationConstructor());
        }
        return instance;
    }

    /**
     * 内部构造对象：防止外部构造实例
     */
    private static class InnerLimitationConstructor {}


    // /////////////////////////////////////////////////////////////////////////////////
    //
    // 重载方法
    //
    // /////////////////////////////////////////////////////////////////////////////////

    /**
     * @see Thread#run()
     */
    @Override
    public void run() {
        super.run();

        do{
            try {
                Thread.sleep(waitTime);

                Message message = new Message();
                message.what = MESSAGE_KEY;
                handler.sendMessage(message);

            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        } while (true);
    }


    // /////////////////////////////////////////////////////////////////////////////////
    //
    // 公有方法
    //
    // /////////////////////////////////////////////////////////////////////////////////



    /**
     * 添加事件监听器
     * @param listener 事件监听器
     */
    public void addListener(OnClockListener listener) {
        if (listener == null) {
            return;
        }
        eventListeners.add(listener);
    }

    /**
     * 删除事件监听器
     * @param listener 事件监听器
     */
    public final void removeListener(OnClockListener listener) {
        if (listener == null) {
            return;
        }
        eventListeners.remove(listener);
    }

    /**
     * 删除所有事件监听器
     */
    public final void removeAllListener() {
        eventListeners.clear();
    }
}
