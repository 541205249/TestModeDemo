package testmode.eebbk.com.testmodedemo.common;

import android.content.Context;
import android.content.res.XmlResourceParser;
import android.util.Log;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import testmode.eebbk.com.testmodedemo.R;
import testmode.eebbk.com.testmodedemo.model.ModuleEntity;
import testmode.eebbk.com.testmodedemo.model.TargetEntity;

/**
 * @author LiXiaoFeng
 * @date 2018/4/17
 */
public class XmlUtil {
    private static final String TAG = XmlUtil.class.getSimpleName();
    private static final String ATTRIBUTE_MODULE_NAME = "name";
    private static final String ATTRIBUTE_TARGET_NAME = "name";
    private static final String ATTRIBUTE_TARGET_ADDITION = "addition";
    private static final String TAG_ROOT = "LogModule";
    private static final String TAG_MODULE = "Module";
    private static final String TAG_TARGET = "Target";

    public static List<ModuleEntity> parse(Context context) throws LogModuleParseException {
        List<ModuleEntity> moduleEntities = new ArrayList<>();

        XmlResourceParser parser = context.getResources().getXml(R.xml.log_module);
        int eventType;
        ModuleEntity parent;
        try {
            for (eventType = parser.getEventType(), parent = null; eventType != XmlPullParser.END_DOCUMENT; eventType = parser.next()) {
                switch (eventType) {
                    case XmlPullParser.START_DOCUMENT: {
                        Log.i(TAG, "开始读取XML文档");
                        break;
                    }
                    case XmlPullParser.START_TAG: {
                        switch (parser.getName()) {
                            case TAG_ROOT: {
                                Log.i(TAG, "读取到开始标签：" + TAG_ROOT);
                                break;
                            }
                            case TAG_MODULE: {
                                String name = null;
                                for (int i = 0; i < parser.getAttributeCount(); i++) {
                                    if (parser.getAttributeName(i).equals(ATTRIBUTE_MODULE_NAME)) {
                                        name = parser.getAttributeValue(i);
                                        break;
                                    }
                                }
                                if (name == null) {
                                    throw new LogModuleParseException("log_entity文件编写出错了");
                                }

                                ModuleEntity moduleEntity = new ModuleEntity(name, parent);
                                if (parent != null) {
                                    parent.getModuleEntities().add(moduleEntity);
                                } else {
                                    moduleEntities.add(moduleEntity);
                                }
                                parent = moduleEntity;
                                Log.i(TAG, "读取到开始标签：" + TAG_MODULE + "，名称为：" + name);
                                break;
                            }
                            case TAG_TARGET: {
                                String name = null;
                                boolean isAddition = false;
                                for (int i = 0; i < parser.getAttributeCount(); i++) {
                                    String attributeName = parser.getAttributeName(i);
                                    if (attributeName.equals(ATTRIBUTE_TARGET_NAME)) {
                                        name = parser.getAttributeValue(i);
                                    } else if (attributeName.equals(ATTRIBUTE_TARGET_ADDITION)) {
                                        isAddition = parser.getAttributeBooleanValue(i, false);
                                    }
                                }

                                if (parent == null || name == null) {
                                    throw new LogModuleParseException("log_entity文件编写出错了");
                                }
                                StringBuilder fullName = new StringBuilder();
                                for (ModuleEntity currParent = parent; currParent != null; currParent = currParent.getParent()) {
                                    fullName.insert(0, currParent.getName() + "-");
                                }
                                fullName.append(name);
                                TargetEntity targetEntity = new TargetEntity(name, fullName.toString(), isAddition, parent);
                                parent.getTargetEntities().add(targetEntity);
                                Log.i(TAG, "读取到开始标签：" + TAG_TARGET + "，名称为：" + name + "，可否手动添加：" + isAddition);
                                break;
                            }
                            default: {
                                throw new LogModuleParseException("log_entity文件编写出错了");
                            }
                        }
                        break;
                    }
                    case XmlPullParser.END_TAG: {
                        switch (parser.getName()) {
                            case TAG_ROOT: {
                                Log.i(TAG, "读取到结束标签：" + TAG_ROOT);
                                break;
                            }
                            case TAG_MODULE: {
                                if (parent.getModuleEntities().isEmpty()) {
                                    parent.setEnd(true);
                                }
                                parent = parent.getParent();
                                Log.i(TAG, "读取到结束标签：" + TAG_MODULE);
                                break;
                            }
                            case TAG_TARGET: {
                                Log.i(TAG, "读取到结束标签：" + TAG_ROOT);
                                break;
                            }
                            default: {
                                throw new LogModuleParseException("log_entity文件编写出错了");
                            }
                        }
                        break;
                    }
                    default: {
                        break;
                    }
                }
            }
        } catch (XmlPullParserException e) {
            throw new LogModuleParseException("log_entity文件编写出错了", e);
        } catch (IOException e) {
            throw new LogModuleParseException("IO出错了", e);
        }

        return moduleEntities;
    }
}
