package testmode.eebbk.com.testmodedemo.common;

/**
 * @author LiXiaoFeng
 * @date 2018/4/9
 * <p>
 * 常量类
 */
public final class Constant {
    public static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

    public final static class Type {
        public static final int INDEX_TYPE_ALL = 0;
        public static final int INDEX_TYPE_SYSTEM_WAKE = 1;
        public static final int INDEX_TYPE_APP_WAKE = 2;
        public static final int INDEX_TYPE_AUDIO = 3;
        public static final int INDEX_TYPE_SPEECH = 4;
        public static final int INDEX_TYPE_SEMANTICS = 5;
        public static final int INDEX_TYPE_ORDER_DISTRIBUTION = 6;
        public static final int INDEX_TYPE_SERVER_SEARCH = 7;
        public static final int INDEX_TYPE_CONTENT = 8;
        public static final int INDEX_TYPE_DISPLAY = 9;
        public static final int INDEX_TYPE_HELPER = 10;

        public static final String[] TYPES = new String[]{
                "全部",
                "系统唤醒",
                "应用内唤醒",
                "音频",
                "语音识别",
                "语义理解",
                "APP指令分发",
                "后台搜索",
                "内容",
                "APP端页面展示",
                "作业助手"
        };
    }

    public final static class Module {
        public static final int INDEX_MODULE_SYSTEM_WAKE = 0;
        public static final int INDEX_MODULE_APP_WAKE = 1;
        public static final int INDEX_MODULE_AUDIO = 2;
        public static final int INDEX_MODULE_SPEECH = 3;
        public static final int INDEX_MODULE_SEMANTICS = 4;
        public static final int INDEX_MODULE_ORDER_DISTRIBUTION = 5;
        public static final int INDEX_MODULE_SERVER_SEARCH = 6;
        public static final int INDEX_MODULE_CONTENT = 7;
        public static final int INDEX_MODULE_DISPLAY = 8;
        public static final int INDEX_MODULE_HELPER = 9;

        public static final String[] MODULES = new String[]{
                "系统唤醒",
                "应用内唤醒",
                "音频",
                "语音识别",
                "语义理解",
                "APP指令分发",
                "后台搜索",
                "内容",
                "APP端页面展示",
                "作业助手"
        };
    }

    public final static class LogTarget {

        /**
         * @author LiXiaoFeng
         * @date 2018/4/11
         * <p>
         * 系统唤醒
         */
        public static final class SystemWake {
            public static final String PREFIX = "系统唤醒-";

            /**
             * @author LiXiaoFeng
             * @date 2018/4/11
             * <p>
             * 黑屏
             */
            public static final class Black {
                public static final String PREFIX = SystemWake.PREFIX + "黑屏-";

                /**
                 * @author LiXiaoFeng
                 * @date 2018/4/11
                 * <p>
                 * 语音唤醒
                 */
                public static final class Voice {
                    public static final String PREFIX = Black.PREFIX + "语音唤醒-";

                    public static final String SUCCESS = PREFIX + "成功";
                    public static final String FAIL = PREFIX + "失败";
                    public static final String MISTAKE = PREFIX + "误唤醒";
                    public static final String SYSTEM_DURATION = PREFIX + "系统处理耗时";
                    public static final String APP_DURATION = PREFIX + "应用响应耗时";
                    public static final String TOTAL_DURATION = PREFIX + "系统唤醒总耗时";
                }

                /**
                 * @author LiXiaoFeng
                 * @date 2018/4/11
                 * <p>
                 * 手势唤醒
                 */
                public static final class Gesture {
                    public static final String PREFIX = Black.PREFIX + "手势唤醒-";

                    public static final String SUCCESS = PREFIX + "成功";
                    public static final String FAIL = PREFIX + "失败";
                    public static final String MISTAKE = PREFIX + "误唤醒";
                    public static final String SYSTEM_DURATION = PREFIX + "系统处理耗时";
                    public static final String APP_DURATION = PREFIX + "应用响应耗时";
                    public static final String TOTAL_DURATION = PREFIX + "系统唤醒总耗时";
                }

            }

            /**
             * @author LiXiaoFeng
             * @date 2018/4/11
             * <p>
             * 锁屏
             */
            public static final class Lock {
                public static final String PREFIX = SystemWake.PREFIX + "锁屏-";

                /**
                 * @author LiXiaoFeng
                 * @date 2018/4/11
                 * <p>
                 * 语音唤醒
                 */
                public static final class Voice {
                    public static final String PREFIX = Lock.PREFIX + "语音唤醒-";

                    public static final String SUCCESS = PREFIX + "成功";
                    public static final String FAIL = PREFIX + "失败";
                    public static final String MISTAKE = PREFIX + "误唤醒";
                    public static final String SYSTEM_DURATION = PREFIX + "系统处理耗时";
                    public static final String APP_DURATION = PREFIX + "应用响应耗时";
                    public static final String TOTAL_DURATION = PREFIX + "系统唤醒总耗时";
                }

            }

            /**
             * @author LiXiaoFeng
             * @date 2018/4/11
             * <p>
             * 亮屏
             */
            public static final class Bright {
                public static final String PREFIX = SystemWake.PREFIX + "亮屏-";

                /**
                 * @author LiXiaoFeng
                 * @date 2018/4/11
                 * <p>
                 * 语音唤醒
                 */
                public static final class Voice {
                    public static final String PREFIX = Bright.PREFIX + "语音唤醒-";

                    public static final String SUCCESS = PREFIX + "成功";
                    public static final String FAIL = PREFIX + "失败";
                    public static final String MISTAKE = PREFIX + "误唤醒";
                    public static final String SYSTEM_DURATION = PREFIX + "系统处理耗时";
                    public static final String APP_DURATION = PREFIX + "应用响应耗时";
                    public static final String TOTAL_DURATION = PREFIX + "系统唤醒总耗时";
                }

                /**
                 * @author LiXiaoFeng
                 * @date 2018/4/11
                 * <p>
                 * 按键唤醒
                 */
                public static final class Button {
                    public static final String PREFIX = Bright.PREFIX + "按键唤醒-";

                    public static final String SUCCESS = PREFIX + "成功";
                    public static final String FAIL = PREFIX + "失败";
                    public static final String MISTAKE = PREFIX + "误唤醒";
                    public static final String SYSTEM_DURATION = PREFIX + "系统处理耗时";
                    public static final String APP_DURATION = PREFIX + "应用响应耗时";
                    public static final String TOTAL_DURATION = PREFIX + "系统唤醒总耗时";
                }

            }

            public static final LogFilter FILTER = target -> target.startsWith(PREFIX);
        }

        /**
         * @author LiXiaoFeng
         * @date 2018/4/11
         * <p>
         * 应用内唤醒
         */
        public static final class AppWake {
            public static final String PREFIX = "应用内唤醒-";

            /**
             * @author LiXiaoFeng
             * @date 2018/4/11
             * <p>
             * 语音唤醒
             */
            public static final class Voice {
                public static final String PREFIX = AppWake.PREFIX + "语音唤醒-";

                public static final String SUCCESS = PREFIX + "成功";
                public static final String FAIL = PREFIX + "失败";
                public static final String MISTAKE = PREFIX + "误唤醒";
                public static final String DURATION = PREFIX + "应用内唤醒耗时";
            }

            /**
             * @author LiXiaoFeng
             * @date 2018/4/11
             * <p>
             * 按键唤醒
             */
            public static final class Button {
                public static final String PREFIX = AppWake.PREFIX + "按键唤醒-";

                public static final String SUCCESS = PREFIX + "成功";
                public static final String FAIL = PREFIX + "失败";
                public static final String MISTAKE = PREFIX + "误唤醒";
                public static final String DURATION = PREFIX + "应用内唤醒耗时";
            }

            public static final LogFilter FILTER = new LogFilter() {
                @Override
                public boolean accept(String target) {
                    return target.startsWith(PREFIX);
                }
            };
        }

        /**
         * @author LiXiaoFeng
         * @date 2018/4/11
         * <p>
         * 音频
         */
        public static final class Audio {
            public static final String PREFIX = "音频-";

            public static final String DECIBEL = PREFIX + "分贝";
            public static final String SAMPLING_RATE = PREFIX + "采样率";

            public static final LogFilter FILTER = new LogFilter() {
                @Override
                public boolean accept(String target) {
                    return target.startsWith(PREFIX);
                }
            };
        }

        /**
         * @author LiXiaoFeng
         * @date 2018/4/11
         * <p>
         * 语音识别
         */
        public static final class Speech {
            public static final String PREFIX = "语音识别-";

            public static final String SUCCESS = PREFIX + ":成功";
            public static final String FAIL = PREFIX + "失败";
            public static final String AUDIO_DURATION = PREFIX + ":语音时长";
            public static final String CONVERTING_DURATION = PREFIX + ":语音即时转换文本耗时";

            public static final LogFilter FILTER = new LogFilter() {
                @Override
                public boolean accept(String target) {
                    return target.startsWith(PREFIX);
                }
            };
        }

        /**
         * @author LiXiaoFeng
         * @date 2018/4/11
         * <p>
         * 语义理解
         */
        public static final class Semantic {
            public static final String PREFIX = "语义理解-";

            public static final String SUCCESS = PREFIX + "成功";
            public static final String FAIL = PREFIX + "失败";
            public static final String DURATION = PREFIX + "语义理解耗时";

            public static final LogFilter FILTER = new LogFilter() {
                @Override
                public boolean accept(String target) {
                    return target.startsWith(PREFIX);
                }
            };
        }

        /**
         * @author LiXiaoFeng
         * @date 2018/4/11
         * <p>
         * APP指令分发
         */
        public static final class OrderDistribution {
            public static final String PREFIX = "APP指令分发-";

            public static final String SUCCESS = PREFIX + "成功";
            public static final String FAIL = PREFIX + "失败";
            public static final String APP_DURATION = PREFIX + "APP命令及参数解析平均耗时";
            public static final String TRANSPORT_DURATION = PREFIX + "空中传输耗时";

            public static final LogFilter FILTER = new LogFilter() {
                @Override
                public boolean accept(String target) {
                    return target.startsWith(PREFIX);
                }
            };
        }

        /**
         * @author LiXiaoFeng
         * @date 2018/4/11
         * <p>
         * 后台搜索
         */
        public static final class ServerSearch {
            public static final String PREFIX = "后台搜索-";

            public static final String SUCCESS = PREFIX + "成功";
            public static final String FAIL = PREFIX + "失败";
            public static final String SERVER_DURATION = PREFIX + "后台搜索耗时";
            public static final String TRANSPORT_DURATION = PREFIX + "空中传输耗时";

            public static final LogFilter FILTER = new LogFilter() {
                @Override
                public boolean accept(String target) {
                    return target.startsWith(PREFIX);
                }
            };
        }

        /**
         * @author LiXiaoFeng
         * @date 2018/4/11
         * <p>
         * 内容
         */
        public static final class Content {
            public static final String PREFIX = "内容-";

            public static final String SUCCESS = PREFIX + "成功";
            public static final String FAIL = PREFIX + "失败";

            public static final LogFilter FILTER = new LogFilter() {
                @Override
                public boolean accept(String target) {
                    return target.startsWith(PREFIX);
                }
            };
        }

        /**
         * @author LiXiaoFeng
         * @date 2018/4/11
         * <p>
         * APP端页面展示
         */
        public static final class Display {
            public static final String PREFIX = "APP端页面展示-";

            public static final String DURATION = PREFIX + ":APP页面展示耗时";

            public static final LogFilter FILTER = new LogFilter() {
                @Override
                public boolean accept(String target) {
                    return target.startsWith(PREFIX);
                }
            };
        }

        /**
         * @author LiXiaoFeng
         * @date 2018/4/11
         * <p>
         * 作业助手
         */
        public static final class Helper {
            public static final String PREFIX = "作业助手-";

            public static final String SUCCESS = PREFIX + "成功";
            public static final String FAIL = PREFIX + "失败";
            public static final String APP_DURATION = PREFIX + "其他应用响应平均耗时";
            public static final String TRANSPORT_DURATION = PREFIX + "空中传输耗时";

            public static final LogFilter FILTER = target -> target.startsWith(PREFIX);
        }
    }
}
