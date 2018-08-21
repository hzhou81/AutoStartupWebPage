package haicj.com.autostartupwebpage;

import java.util.Date;

/**
 * 时钟事件监听器
 * @author 孙汇洲
 */
public interface OnClockListener {

    /**
     * 响应电池状态变更事件
     * @param date 日期对象
     */
    void onChange(Date date);
}
