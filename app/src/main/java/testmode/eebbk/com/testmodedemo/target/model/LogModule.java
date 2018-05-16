package testmode.eebbk.com.testmodedemo.target.model;

import static testmode.eebbk.com.testmodedemo.util.Constant.CONNECTOR;

/**
 * @author LiXiaoFeng
 * @date 2018/4/18
 */
public final class LogModule {
    /**
     * @author LiXiaoFeng
     * @date 2018/4/11
     * <p>
     * 系统唤醒
     */
    public static final class SystemWake {
        public static final String NAME = "系统唤醒";

        /**
         * @author LiXiaoFeng
         * @date 2018/4/11
         * <p>
         * 黑屏
         */
        public static final class Black {
            public static final String FULL_NAME = LogModule.SystemWake.NAME + CONNECTOR + "黑屏";

            /**
             * @author LiXiaoFeng
             * @date 2018/4/11
             * <p>
             * 语音唤醒
             */
            public static final class Voice {
                public static final String FULL_NAME = LogModule.SystemWake.Black.FULL_NAME + CONNECTOR + "语音唤醒";

                public static final String SUCCESS = FULL_NAME + CONNECTOR + "成功";
                public static final String FAIL = FULL_NAME + CONNECTOR + "失败";
                public static final String MISTAKE = FULL_NAME + CONNECTOR + "误唤醒";
                public static final String SYSTEM_DURATION = FULL_NAME + CONNECTOR + "系统处理耗时";
                public static final String APP_DURATION = FULL_NAME + CONNECTOR + "应用响应耗时";
                public static final String TOTAL_DURATION = FULL_NAME + CONNECTOR + "系统唤醒总耗时";
            }

            /**
             * @author LiXiaoFeng
             * @date 2018/4/11
             * <p>
             * 手势唤醒
             */
            public static final class Gesture {
                public static final String FULL_NAME = LogModule.SystemWake.Black.FULL_NAME + CONNECTOR + "手势唤醒";

                public static final String SUCCESS = FULL_NAME + CONNECTOR + "成功";
                public static final String FAIL = FULL_NAME + CONNECTOR + "失败";
                public static final String MISTAKE = FULL_NAME + CONNECTOR + "误唤醒";
                public static final String SYSTEM_DURATION = FULL_NAME + CONNECTOR + "系统处理耗时";
                public static final String APP_DURATION = FULL_NAME + CONNECTOR + "应用响应耗时";
                public static final String TOTAL_DURATION = FULL_NAME + CONNECTOR + "系统唤醒总耗时";
            }

        }

        /**
         * @author LiXiaoFeng
         * @date 2018/4/11
         * <p>
         * 锁屏
         */
        public static final class Lock {
            public static final String FULL_NAME =LogModule.SystemWake.NAME + CONNECTOR + "锁屏";

            /**
             * @author LiXiaoFeng
             * @date 2018/4/11
             * <p>
             * 语音唤醒
             */
            public static final class Voice {
                public static final String FULL_NAME = LogModule.SystemWake.Lock.FULL_NAME + CONNECTOR + "语音唤醒";

                public static final String SUCCESS = FULL_NAME + CONNECTOR + "成功";
                public static final String FAIL = FULL_NAME + CONNECTOR + "失败";
                public static final String MISTAKE = FULL_NAME + CONNECTOR + "误唤醒";
                public static final String SYSTEM_DURATION = FULL_NAME + CONNECTOR + "系统处理耗时";
                public static final String APP_DURATION = FULL_NAME + CONNECTOR + "应用响应耗时";
                public static final String TOTAL_DURATION = FULL_NAME + CONNECTOR + "系统唤醒总耗时";
            }

        }

        /**
         * @author LiXiaoFeng
         * @date 2018/4/11
         * <p>
         * 亮屏
         */
        public static final class Bright {
            public static final String FULL_NAME = LogModule.SystemWake.NAME + CONNECTOR + "亮屏";

            /**
             * @author LiXiaoFeng
             * @date 2018/4/11
             * <p>
             * 语音唤醒
             */
            public static final class Voice {
                public static final String FULL_NAME = LogModule.SystemWake.Bright.FULL_NAME + CONNECTOR + "语音唤醒";

                public static final String SUCCESS = FULL_NAME + CONNECTOR + "成功";
                public static final String FAIL = FULL_NAME + CONNECTOR + "失败";
                public static final String MISTAKE = FULL_NAME + CONNECTOR + "误唤醒";
                public static final String SYSTEM_DURATION = FULL_NAME + CONNECTOR + "系统处理耗时";
                public static final String APP_DURATION = FULL_NAME + CONNECTOR + "应用响应耗时";
                public static final String TOTAL_DURATION = FULL_NAME + CONNECTOR + "系统唤醒总耗时";
            }

            /**
             * @author LiXiaoFeng
             * @date 2018/4/11
             * <p>
             * 按键唤醒
             */
            public static final class Button {
                public static final String FULL_NAME = LogModule.SystemWake.Bright.FULL_NAME + CONNECTOR + "按键唤醒";

                public static final String SUCCESS = FULL_NAME + CONNECTOR + "成功";
                public static final String FAIL = FULL_NAME + CONNECTOR + "失败";
                public static final String MISTAKE = FULL_NAME + CONNECTOR + "误唤醒";
                public static final String SYSTEM_DURATION = FULL_NAME + CONNECTOR + "系统处理耗时";
                public static final String APP_DURATION = FULL_NAME + CONNECTOR + "应用响应耗时";
                public static final String TOTAL_DURATION = FULL_NAME + CONNECTOR + "系统唤醒总耗时";
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
        public static final String NAME = "应用内唤醒";

        /**
         * @author LiXiaoFeng
         * @date 2018/4/11
         * <p>
         * 语音唤醒
         */
        public static final class Voice {
            public static final String FULL_NAME = LogModule.AppWake.NAME + CONNECTOR + "语音唤醒";

            public static final String SUCCESS = FULL_NAME + CONNECTOR + "成功";
            public static final String FAIL = FULL_NAME + CONNECTOR + "失败";
            public static final String MISTAKE = FULL_NAME + CONNECTOR + "误唤醒";
            public static final String DURATION = FULL_NAME + CONNECTOR + "应用内唤醒耗时";
        }

        /**
         * @author LiXiaoFeng
         * @date 2018/4/11
         * <p>
         * 按键唤醒
         */
        public static final class Button {
            public static final String FULL_NAME = LogModule.AppWake.NAME + CONNECTOR + "按键唤醒";

            public static final String SUCCESS = FULL_NAME + CONNECTOR + "成功";
            public static final String FAIL = FULL_NAME + CONNECTOR + "失败";
            public static final String MISTAKE = FULL_NAME + CONNECTOR + "误唤醒";
            public static final String DURATION = FULL_NAME + CONNECTOR + "应用内唤醒耗时";
        }
    }

    /**
     * @author LiXiaoFeng
     * @date 2018/4/11
     * <p>
     * 音频
     */
    public static final class Audio {
        public static final String NAME = "音频";

        public static final String DECIBEL = NAME + CONNECTOR + "分贝";
        public static final String SAMPLING_RATE = NAME + CONNECTOR + "采样率";
    }

    /**
     * @author LiXiaoFeng
     * @date 2018/4/11
     * <p>
     * 语音识别
     */
    public static final class Speech {
        public static final String NAME = "语音识别";

        public static final String SUCCESS = NAME + CONNECTOR + "成功";
        public static final String FAIL = NAME + CONNECTOR + "失败";
        public static final String AUDIO_DURATION = NAME + CONNECTOR + "语音时长";
        public static final String CONVERTING_DURATION = NAME + CONNECTOR + "语音即时转换文本耗时";
    }

    /**
     * @author LiXiaoFeng
     * @date 2018/4/11
     * <p>
     * 语义理解
     */
    public static final class Semantic {
        public static final String NAME = "语义理解";

        public static final String SUCCESS = NAME + CONNECTOR + "成功";
        public static final String FAIL = NAME + CONNECTOR + "失败";
        public static final String DURATION = NAME + CONNECTOR + "语义理解耗时";
    }

    /**
     * @author LiXiaoFeng
     * @date 2018/4/11
     * <p>
     * APP指令分发
     */
    public static final class OrderDistribution {
        public static final String NAME = "APP指令分发";

        public static final String SUCCESS = NAME + CONNECTOR + "成功";
        public static final String FAIL = NAME + CONNECTOR + "失败";
        public static final String APP_DURATION = NAME + CONNECTOR + "APP命令及参数解析平均耗时";
        public static final String TRANSPORT_DURATION = NAME + CONNECTOR + "空中传输耗时";
    }

    /**
     * @author LiXiaoFeng
     * @date 2018/4/11
     * <p>
     * 后台搜索
     */
    public static final class ServerSearch {
        public static final String NAME = "后台搜索";

        public static final String SUCCESS = NAME + CONNECTOR + "成功";
        public static final String FAIL = NAME + CONNECTOR + "失败";
        public static final String SERVER_DURATION = NAME + CONNECTOR + "后台搜索耗时";
        public static final String TRANSPORT_DURATION = NAME + CONNECTOR + "空中传输耗时";
    }

    /**
     * @author LiXiaoFeng
     * @date 2018/4/11
     * <p>
     * 内容
     */
    public static final class Content {
        public static final String NAME = "内容";

        public static final String SUCCESS = NAME + CONNECTOR + "成功";
        public static final String FAIL = NAME + CONNECTOR + "失败";
    }

    /**
     * @author LiXiaoFeng
     * @date 2018/4/11
     * <p>
     * APP端页面展示
     */
    public static final class Display {
        public static final String NAME = "APP端页面展示";

        public static final String DURATION = NAME + CONNECTOR + "APP页面展示耗时";
    }

    /**
     * @author LiXiaoFeng
     * @date 2018/4/11
     * <p>
     * 作业助手
     */
    public static final class Helper {
        public static final String NAME = "作业助手";

        public static final String SUCCESS = NAME + CONNECTOR + "成功";
        public static final String FAIL = NAME + CONNECTOR + "失败";
        public static final String APP_DURATION = NAME + CONNECTOR + "其他应用响应平均耗时";
        public static final String TRANSPORT_DURATION = NAME + CONNECTOR + "空中传输耗时";
    }
}
