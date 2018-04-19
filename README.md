# TestModeDemo

## 关于

测试数据收集应用，用于收集、显示、统计TestModeTools应用测试模式支持库的测试数据。

## 特性

- 简单易用
- 可移植性强

## 功能列表

- 自定义测试模块
- 自定义统计信息
- 输出测试数据记录文件

## 用到的技术

- JExcelApi
- 悬浮窗
- XML

## 结构图

![](https://i.imgur.com/6w6M60A.png)

## 使用方式

1. 在`app`模块下的`AndroidManifest.xml`文件中，在`TestBroadCastReceiver`的声明中按如下规则填写：

        <receiver android:name=".TestBroadCastReceiver">
            <intent-filter>
                <!--将"com.eebbk.askhomework"部分替换为你所测试的应用包名-->
                <action android:name="com.eebbk.askhomework.testmode.action" />
            </intent-filter>
            <meta-data
                android:name="test_app_name"
                <!--将"问作业"部分替换为你所测试的应用名>
                android:value="问作业" />
        </receiver>

2. 将`app`模块下的`res/xml/log_module/xml`文件替换成你所需要的测试模块，XML文件的格式需要按照以下规则：
  
    - `<LogModule>`
  
        根标签
    
    - `<Module>`
  
        模块标签，属性：`name`——模块名
    
    - `<Target>`
  
        测试项标签，属性：`name`——测试项名，`tool`——是否工具标签（`true`为是，`false`为否，工具标签将在工具栏中显示，可手动进行自定义功能的处理）
  
    - `<Target>`必须位于`<Module>`中。
    - `<Module>`的直接子标签可以为`<Module>`或`<Target>`，但要么全部是`<Module>`，要么全部是`<Target>`，不可同时存在。
  
    示例：

    ```xml
    <?xml version="1.0" encoding="utf-8"?>
    <LogModule>
      <Module name="系统唤醒">
          <Module name="黑屏">
              <Module name="语音唤醒">
                  <Target
                      name="成功"
                      tool="false" />
                  <Target
                      name="失败"
                      tool="false" />
                  <Target
                      name="误唤醒"
                      tool="true" />
                  <Target
                      name="系统处理耗时"
                      tool="false" />
                  <Target
                      name="应用响应耗时"
                      tool="false" />
                  <Target
                      name="系统唤醒总耗时"
                      tool="false" />
              </Module>
              <Module name="手势唤醒">
                  <Target
                      name="成功"
                      tool="false" />
                  <Target
                      name="失败"
                      tool="false" />
                  <Target
                      name="误唤醒"
                      tool="true" />
                  <Target
                      name="系统处理耗时"
                      tool="false" />
                  <Target
                      name="应用响应耗时"
                      tool="false" />
                  <Target
                      name="系统唤醒总耗时"
                      tool="false" />
              </Module>
          </Module>
          <Module name="锁屏">
              <Module name="语音唤醒">
                  <Target
                      name="成功"
                      tool="false" />
                  <Target
                      name="失败"
                      tool="false" />
                  <Target
                      name="误唤醒"
                      tool="true" />
                  <Target
                      name="系统处理耗时"
                      tool="false" />
                  <Target
                      name="应用响应耗时"
                      tool="false" />
                  <Target
                      name="系统唤醒总耗时"
                      tool="false" />
              </Module>
          </Module>
          <Module name="亮屏">
              <Module name="语音唤醒">
                  <Target
                      name="成功"
                      tool="false" />
                  <Target
                      name="失败"
                      tool="false" />
                  <Target
                      name="误唤醒"
                      tool="true" />
                  <Target
                      name="系统处理耗时"
                      tool="false" />
                  <Target
                      name="应用响应耗时"
                      tool="false" />
                  <Target
                      name="系统唤醒总耗时"
                      tool="false" />
              </Module>
              <Module name="按键唤醒">
                  <Target
                      name="成功"
                      tool="false" />
                  <Target
                      name="失败"
                      tool="false" />
                  <Target
                      name="误唤醒"
                      tool="true" />
                  <Target
                      name="系统处理耗时"
                      tool="false" />
                  <Target
                      name="应用响应耗时"
                      tool="false" />
                  <Target
                      name="系统唤醒总耗时"
                      tool="false" />
              </Module>
          </Module>
      </Module>
      <Module name="应用内唤醒">
          <Module name="语音唤醒">
              <Target
                  name="成功"
                  tool="false" />
              <Target
                  name="失败"
                  tool="false" />
              <Target
                  name="误唤醒"
                  tool="true" />
              <Target
                  name="应用内唤醒耗时"
                  tool="false" />
          </Module>
          <Module name="按键唤醒">
              <Target
                  name="成功"
                  tool="false" />
              <Target
                  name="失败"
                  tool="false" />
              <Target
                  name="误唤醒"
                  tool="true" />
              <Target
                  name="应用内唤醒耗时"
                  tool="false" />
          </Module>
      </Module>
      <Module name="音频">
          <Target
              name="分贝"
              tool="true" />
          <Target
              name="采样率"
              tool="false" />
      </Module>
      <Module name="语音识别">
          <Target
              name="成功"
              tool="true" />
          <Target
              name="失败"
              tool="true" />
          <Target
              name="语音时长"
              tool="false" />
          <Target
              name="语音即时转换文本耗时"
              tool="false" />
      </Module>
      <Module name="语义理解">
          <Target
              name="成功"
              tool="true" />
          <Target
              name="失败"
              tool="true" />
          <Target
              name="语义理解耗时"
              tool="false" />
      </Module>
      <Module name="APP指令分发">
          <Target
              name="成功"
              tool="true" />
          <Target
              name="失败"
              tool="true" />
          <Target
              name="APP命令及参数解析平均耗时"
              tool="false" />
          <Target
              name="空中传输耗时"
              tool="false" />
      </Module>
      <Module name="后台搜索">
          <Target
              name="成功"
              tool="true" />
          <Target
              name="失败"
              tool="true" />
          <Target
              name="后台搜索耗时"
              tool="false" />
          <Target
              name="空中传输耗时"
              tool="false" />
      </Module>
      <Module name="内容">
          <Target
              name="成功"
              tool="true" />
          <Target
              name="失败"
              tool="true" />
      </Module>
      <Module name="APP端页面展示">
          <Target
              name="APP页面展示耗时"
              tool="false" />
      </Module>
      <Module name="作业助手">
          <Target
              name="成功"
              tool="true" />
          <Target
              name="失败"
              tool="true" />
          <Target
              name="其他应用响应平均耗时"
              tool="false" />
          <Target
              name="空中传输耗时"
              tool="false" />
      </Module>
    </LogModule>
    ```

3. 在`LogFilterFactory`中根据测试模块返回自定义`LogFilter`实现，示例：

    ```java
    public final class LogFilterFactory {

        private LogFilterFactory() {

        }

        public static LogFilter produceLogFilter(ModuleEntity moduleEntity) {
            if (moduleEntity.getParent() != null) {
                return null;
            }

            return logEntity -> logEntity.getTarget().startsWith(moduleEntity.getName());
        }
    }
    ```
  
4. 在`LogStatisticianFactory`中根据顶级测试模块返回自定义`LogStatistician`实现，示例：

    ```java
    public final class LogStatisticianFactory {
        private LogStatisticianFactory() {

        }

        public static LogStatistician produceLogStatistician(ModuleEntity moduleEntity) {
            if (moduleEntity.getParent() != null) {
                return null;
            }

            LogStatistician logStatistician = null;
            switch (moduleEntity.getName()) {
                case LogModule.SystemWake.FULL: {
                    logStatistician = new SystemWakeLogStatistician();
                    break;
                }
                case LogModule.AppWake.FULL: {
                    logStatistician = new AppWakeLogStatistician();
                    break;
                }
                case LogModule.Audio.FULL: {
                    logStatistician = new AudioLogStatistician();
                    break;
                }
                case LogModule.Speech.FULL: {
                    logStatistician = new SpeechLogStatistician();
                    break;
                }
                case LogModule.Semantic.FULL: {
                    logStatistician = new SemanticLogStatistician();
                    break;
                }
                case LogModule.OrderDistribution.FULL: {
                    logStatistician = new OrderDistributionLogStatistician();
                    break;
                }
                case LogModule.ServerSearch.FULL: {
                    logStatistician = new ServerSearchLogStatistician();
                    break;
                }
                case LogModule.Content.FULL: {
                    logStatistician = new ContentLogStatistician();
                    break;
                }
                case LogModule.Display.FULL: {
                    logStatistician = new DisplayLogStatistician();
                    break;
                }
                case LogModule.Helper.FULL: {
                    logStatistician = new HelperLogStatistician();
                    break;
                }
                default: {
                    break;
                }
            }
            return logStatistician;
        }
    }
    ```
    
5. 在`LogToolFactory`中根据指标项返回自定义`LogTool`实现，示例：

    ```java
    public class LogToolFactory {
        private LogToolFactory() {

        }

        public static LogTool produceTool(TargetEntity target) {
            if (target == null) {
                return null;
            }

            LogTool logTool;

            if (target.getFullName().equals(LogModule.Audio.DECIBEL)) {
                logTool = targetEntity -> {
                    Toast.makeText(TestModeApplication.getTestModeApplicationContext(), "功能待开发", Toast.LENGTH_SHORT).show();
                };
            } else {
                logTool = targetEntity -> {
                    LogEntity logEntity = new LogEntity();
                    logEntity.setTarget(targetEntity.getFullName());
                    logEntity.setDate(DateUtils.getCurTimeString(Constant.DATE_FORMAT));
                    DataRepository.getInstance().insertData(logEntity);
                }
                ;
            }

            return logTool;
        }
    }
    ```
    
6. 编写一个包含测试模块和测试项的常量类，用于供测试应用使用（也可供本应用使用）,根据上面的XML文件示例，可有如下常量类：

    ```java
    public final class LogModule {

        public static final class SystemWake {
            public static final String NAME = "系统唤醒";

            public static final class Black {
                public static final String FULL_NAME = LogModule.SystemWake.NAME + CONNECTOR + "黑屏";

                public static final class Voice {
                    public static final String FULL_NAME = LogModule.SystemWake.Black.FULL_NAME + CONNECTOR + "语音唤醒";

                    public static final String SUCCESS = FULL_NAME + CONNECTOR + "成功";
                    public static final String FAIL = FULL_NAME + CONNECTOR + "失败";
                    public static final String MISTAKE = FULL_NAME + CONNECTOR + "误唤醒";
                    public static final String SYSTEM_DURATION = FULL_NAME + CONNECTOR + "系统处理耗时";
                    public static final String APP_DURATION = FULL_NAME + CONNECTOR + "应用响应耗时";
                    public static final String TOTAL_DURATION = FULL_NAME + CONNECTOR + "系统唤醒总耗时";
                }

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

            public static final class Lock {
                public static final String FULL_NAME =LogModule.SystemWake.NAME + CONNECTOR + "锁屏";

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

            public static final class Bright {
                public static final String FULL_NAME = LogModule.SystemWake.NAME + CONNECTOR + "亮屏";

                public static final class Voice {
                    public static final String FULL_NAME = LogModule.SystemWake.Bright.FULL_NAME + CONNECTOR + "语音唤醒";

                    public static final String SUCCESS = FULL_NAME + CONNECTOR + "成功";
                    public static final String FAIL = FULL_NAME + CONNECTOR + "失败";
                    public static final String MISTAKE = FULL_NAME + CONNECTOR + "误唤醒";
                    public static final String SYSTEM_DURATION = FULL_NAME + CONNECTOR + "系统处理耗时";
                    public static final String APP_DURATION = FULL_NAME + CONNECTOR + "应用响应耗时";
                    public static final String TOTAL_DURATION = FULL_NAME + CONNECTOR + "系统唤醒总耗时";
                }

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

        public static final class AppWake {
            public static final String NAME = "应用内唤醒";

            public static final class Voice {
                public static final String FULL_NAME = LogModule.AppWake.NAME + CONNECTOR + "语音唤醒";

                public static final String SUCCESS = FULL_NAME + CONNECTOR + "成功";
                public static final String FAIL = FULL_NAME + CONNECTOR + "失败";
                public static final String MISTAKE = FULL_NAME + CONNECTOR + "误唤醒";
                public static final String DURATION = FULL_NAME + CONNECTOR + "应用内唤醒耗时";
            }

            public static final class Button {
                public static final String FULL_NAME = LogModule.AppWake.NAME + CONNECTOR + "按键唤醒";

                public static final String SUCCESS = FULL_NAME + CONNECTOR + "成功";
                public static final String FAIL = FULL_NAME + CONNECTOR + "失败";
                public static final String MISTAKE = FULL_NAME + CONNECTOR + "误唤醒";
                public static final String DURATION = FULL_NAME + CONNECTOR + "应用内唤醒耗时";
            }
        }

        public static final class Audio {
            public static final String NAME = "音频";

            public static final String DECIBEL = NAME + CONNECTOR + "分贝";
            public static final String SAMPLING_RATE = NAME + CONNECTOR + "采样率";
        }

        public static final class Speech {
            public static final String NAME = "语音识别";

            public static final String SUCCESS = NAME + CONNECTOR + "成功";
            public static final String FAIL = NAME + CONNECTOR + "失败";
            public static final String AUDIO_DURATION = NAME + CONNECTOR + "语音时长";
            public static final String CONVERTING_DURATION = NAME + CONNECTOR + "语音即时转换文本耗时";
        }

        public static final class Semantic {
            public static final String NAME = "语义理解";

            public static final String SUCCESS = NAME + CONNECTOR + "成功";
            public static final String FAIL = NAME + CONNECTOR + "失败";
            public static final String DURATION = NAME + CONNECTOR + "语义理解耗时";
        }

        public static final class OrderDistribution {
            public static final String NAME = "APP指令分发";

            public static final String SUCCESS = NAME + CONNECTOR + "成功";
            public static final String FAIL = NAME + CONNECTOR + "失败";
            public static final String APP_DURATION = NAME + CONNECTOR + "APP命令及参数解析平均耗时";
            public static final String TRANSPORT_DURATION = NAME + CONNECTOR + "空中传输耗时";
        }

        public static final class ServerSearch {
            public static final String NAME = "后台搜索";

            public static final String SUCCESS = NAME + CONNECTOR + "成功";
            public static final String FAIL = NAME + CONNECTOR + "失败";
            public static final String SERVER_DURATION = NAME + CONNECTOR + "后台搜索耗时";
            public static final String TRANSPORT_DURATION = NAME + CONNECTOR + "空中传输耗时";
        }

        public static final class Content {
            public static final String NAME = "内容";

            public static final String SUCCESS = NAME + CONNECTOR + "成功";
            public static final String FAIL = NAME + CONNECTOR + "失败";
        }

        public static final class Display {
            public static final String NAME = "APP端页面展示";

            public static final String DURATION = NAME + CONNECTOR + "APP页面展示耗时";
        }

        public static final class Helper {
            public static final String NAME = "作业助手";

            public static final String SUCCESS = NAME + CONNECTOR + "成功";
            public static final String FAIL = NAME + CONNECTOR + "失败";
            public static final String APP_DURATION = NAME + CONNECTOR + "其他应用响应平均耗时";
            public static final String TRANSPORT_DURATION = NAME + CONNECTOR + "空中传输耗时";
        }
    }
    ```
    
完成以上步骤之后，你就可以愉快地使用了！
