package testmode.eebbk.com.testmodedemo.model;

import static testmode.eebbk.com.testmodedemo.common.Constant.CONNECTOR;

/**
 * @author LiXiaoFeng
 * @date 2018/4/18
 * 【实现的主要功能】
 * 【修改者，修改日期，修改内容】
 */
public final class LogModule {
    /**
     * @author LiXiaoFeng
     * @date 2018/4/11
     * <p>
     * 系统唤醒
     */
    public static final class SystemWake {
        public static final String FULL = "系统唤醒";

        /**
         * @author LiXiaoFeng
         * @date 2018/4/11
         * <p>
         * 黑屏
         */
        public static final class Black {
            public static final String FULL = LogModule.SystemWake.FULL + CONNECTOR + "黑屏";

            /**
             * @author LiXiaoFeng
             * @date 2018/4/11
             * <p>
             * 语音唤醒
             */
            public static final class Voice {
                public static final String FULL = LogModule.SystemWake.Black.FULL + CONNECTOR + "语音唤醒";

                public static final String SUCCESS = FULL + CONNECTOR + "成功";
                public static final String FAIL = FULL + CONNECTOR + "失败";
                public static final String MISTAKE = FULL + CONNECTOR + "误唤醒";
                public static final String SYSTEM_DURATION = FULL + CONNECTOR + "系统处理耗时";
                public static final String APP_DURATION = FULL + CONNECTOR + "应用响应耗时";
                public static final String TOTAL_DURATION = FULL + CONNECTOR + "系统唤醒总耗时";
            }

            /**
             * @author LiXiaoFeng
             * @date 2018/4/11
             * <p>
             * 手势唤醒
             */
            public static final class Gesture {
                public static final String FULL = LogModule.SystemWake.Black.FULL + CONNECTOR + "手势唤醒";

                public static final String SUCCESS = FULL + CONNECTOR + "成功";
                public static final String FAIL = FULL + CONNECTOR + "失败";
                public static final String MISTAKE = FULL + CONNECTOR + "误唤醒";
                public static final String SYSTEM_DURATION = FULL + CONNECTOR + "系统处理耗时";
                public static final String APP_DURATION = FULL + CONNECTOR + "应用响应耗时";
                public static final String TOTAL_DURATION = FULL + CONNECTOR + "系统唤醒总耗时";
            }

        }

        /**
         * @author LiXiaoFeng
         * @date 2018/4/11
         * <p>
         * 锁屏
         */
        public static final class Lock {
            public static final String FULL =LogModule.SystemWake.FULL + CONNECTOR + "锁屏";

            /**
             * @author LiXiaoFeng
             * @date 2018/4/11
             * <p>
             * 语音唤醒
             */
            public static final class Voice {
                public static final String FULL = LogModule.SystemWake.Lock.FULL + CONNECTOR + "语音唤醒";

                public static final String SUCCESS = FULL + CONNECTOR + "成功";
                public static final String FAIL = FULL + CONNECTOR + "失败";
                public static final String MISTAKE = FULL + CONNECTOR + "误唤醒";
                public static final String SYSTEM_DURATION = FULL + CONNECTOR + "系统处理耗时";
                public static final String APP_DURATION = FULL + CONNECTOR + "应用响应耗时";
                public static final String TOTAL_DURATION = FULL + CONNECTOR + "系统唤醒总耗时";
            }

        }

        /**
         * @author LiXiaoFeng
         * @date 2018/4/11
         * <p>
         * 亮屏
         */
        public static final class Bright {
            public static final String FULL = LogModule.SystemWake.FULL + CONNECTOR + "亮屏";

            /**
             * @author LiXiaoFeng
             * @date 2018/4/11
             * <p>
             * 语音唤醒
             */
            public static final class Voice {
                public static final String FULL = LogModule.SystemWake.Bright.FULL + CONNECTOR + "语音唤醒";

                public static final String SUCCESS = FULL + CONNECTOR + "成功";
                public static final String FAIL = FULL + CONNECTOR + "失败";
                public static final String MISTAKE = FULL + CONNECTOR + "误唤醒";
                public static final String SYSTEM_DURATION = FULL + CONNECTOR + "系统处理耗时";
                public static final String APP_DURATION = FULL + CONNECTOR + "应用响应耗时";
                public static final String TOTAL_DURATION = FULL + CONNECTOR + "系统唤醒总耗时";
            }

            /**
             * @author LiXiaoFeng
             * @date 2018/4/11
             * <p>
             * 按键唤醒
             */
            public static final class Button {
                public static final String FULL = LogModule.SystemWake.Bright.FULL + CONNECTOR + "按键唤醒";

                public static final String SUCCESS = FULL + CONNECTOR + "成功";
                public static final String FAIL = FULL + CONNECTOR + "失败";
                public static final String MISTAKE = FULL + CONNECTOR + "误唤醒";
                public static final String SYSTEM_DURATION = FULL + CONNECTOR + "系统处理耗时";
                public static final String APP_DURATION = FULL + CONNECTOR + "应用响应耗时";
                public static final String TOTAL_DURATION = FULL + CONNECTOR + "系统唤醒总耗时";
            }

        }
    }

    /**
     * @author LiXiaoFeng
     * @date 2018/4/11
     * <p>
     * 应用内唤醒
     */
    public static final class AppWake {
        public static final String FULL = "应用内唤醒";

        /**
         * @author LiXiaoFeng
         * @date 2018/4/11
         * <p>
         * 语音唤醒
         */
        public static final class Voice {
            public static final String FULL = LogModule.AppWake.FULL + CONNECTOR + "语音唤醒";

            public static final String SUCCESS = FULL + CONNECTOR + "成功";
            public static final String FAIL = FULL + CONNECTOR + "失败";
            public static final String MISTAKE = FULL + CONNECTOR + "误唤醒";
            public static final String DURATION = FULL + CONNECTOR + "应用内唤醒耗时";
        }

        /**
         * @author LiXiaoFeng
         * @date 2018/4/11
         * <p>
         * 按键唤醒
         */
        public static final class Button {
            public static final String FULL = LogModule.AppWake.FULL + CONNECTOR + "按键唤醒";

            public static final String SUCCESS = FULL + CONNECTOR + "成功";
            public static final String FAIL = FULL + CONNECTOR + "失败";
            public static final String MISTAKE = FULL + CONNECTOR + "误唤醒";
            public static final String DURATION = FULL + CONNECTOR + "应用内唤醒耗时";
        }
    }

    /**
     * @author LiXiaoFeng
     * @date 2018/4/11
     * <p>
     * 音频
     */
    public static final class Audio {
        public static final String FULL = "音频";

        public static final String DECIBEL = FULL + CONNECTOR + "分贝";
        public static final String SAMPLING_RATE = FULL + CONNECTOR + "采样率";
    }

    /**
     * @author LiXiaoFeng
     * @date 2018/4/11
     * <p>
     * 语音识别
     */
    public static final class Speech {
        public static final String FULL = "语音识别";

        public static final String SUCCESS = FULL + CONNECTOR + "成功";
        public static final String FAIL = FULL + CONNECTOR + "失败";
        public static final String AUDIO_DURATION = FULL + CONNECTOR + "语音时长";
        public static final String CONVERTING_DURATION = FULL + CONNECTOR + "语音即时转换文本耗时";
    }

    /**
     * @author LiXiaoFeng
     * @date 2018/4/11
     * <p>
     * 语义理解
     */
    public static final class Semantic {
        public static final String FULL = "语义理解";

        public static final String SUCCESS = FULL + CONNECTOR + "成功";
        public static final String FAIL = FULL + CONNECTOR + "失败";
        public static final String DURATION = FULL + CONNECTOR + "语义理解耗时";
    }

    /**
     * @author LiXiaoFeng
     * @date 2018/4/11
     * <p>
     * APP指令分发
     */
    public static final class OrderDistribution {
        public static final String FULL = "APP指令分发";

        public static final String SUCCESS = FULL + CONNECTOR + "成功";
        public static final String FAIL = FULL + CONNECTOR + "失败";
        public static final String APP_DURATION = FULL + CONNECTOR + "APP命令及参数解析平均耗时";
        public static final String TRANSPORT_DURATION = FULL + CONNECTOR + "空中传输耗时";
    }

    /**
     * @author LiXiaoFeng
     * @date 2018/4/11
     * <p>
     * 后台搜索
     */
    public static final class ServerSearch {
        public static final String FULL = "后台搜索";

        public static final String SUCCESS = FULL + CONNECTOR + "成功";
        public static final String FAIL = FULL + CONNECTOR + "失败";
        public static final String SERVER_DURATION = FULL + CONNECTOR + "后台搜索耗时";
        public static final String TRANSPORT_DURATION = FULL + CONNECTOR + "空中传输耗时";
    }

    /**
     * @author LiXiaoFeng
     * @date 2018/4/11
     * <p>
     * 内容
     */
    public static final class Content {
        public static final String FULL = "内容";

        public static final String SUCCESS = FULL + CONNECTOR + "成功";
        public static final String FAIL = FULL + CONNECTOR + "失败";
    }

    /**
     * @author LiXiaoFeng
     * @date 2018/4/11
     * <p>
     * APP端页面展示
     */
    public static final class Display {
        public static final String FULL = "APP端页面展示";

        public static final String DURATION = FULL + CONNECTOR + "APP页面展示耗时";
    }

    /**
     * @author LiXiaoFeng
     * @date 2018/4/11
     * <p>
     * 作业助手
     */
    public static final class Helper {
        public static final String FULL = "作业助手";

        public static final String SUCCESS = FULL + CONNECTOR + "成功";
        public static final String FAIL = FULL + CONNECTOR + "失败";
        public static final String APP_DURATION = FULL + CONNECTOR + "其他应用响应平均耗时";
        public static final String TRANSPORT_DURATION = FULL + CONNECTOR + "空中传输耗时";
    }
}
