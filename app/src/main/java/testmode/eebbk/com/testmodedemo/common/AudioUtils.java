package testmode.eebbk.com.testmodedemo.common;

import java.nio.ByteOrder;

/**
 * 作者： jiazy
 * 日期： 2017/10/9.
 * 公司： 步步高教育电子有限公司
 * 描述： 音频处理工具类
 */
public class AudioUtils {
    private AudioUtils() {
    }

    /**
     * 获取pcm数据的分贝值
     * @param buffer 当前音频数据
     * @param readSize 数据大小
     * @return 分贝值
     */
    public static int calcDecibelLevel(byte[] buffer, int readSize) {
        double volume = 10 * Math.log10( (double) percentOfArray(buffer)/readSize );
        return (int) volume;
    }

    private static long percentOfArray(byte[] data) {
        if ( 0 == data.length ) {
            return 0;
        }
        short[] dataShort = bytes2Shorts(data);
        int startIdx = 0;
        long maxLen = 0;
        for (; startIdx < dataShort.length; startIdx++) {
            maxLen += (dataShort[startIdx]) * (dataShort[startIdx]);
        }
        return maxLen;
    }

    private static short[] bytes2Shorts(byte[] buf) {
        byte bLength = 2;
        short[] s = new short[buf.length / bLength];
        for (int iLoop = 0; iLoop < s.length; iLoop++) {
            byte[] temp = new byte[bLength];
            System.arraycopy(buf, iLoop * bLength, temp, 0, bLength);
            s[iLoop] = getShort(temp);
        }
        return s;
    }

    private static short getShort(byte[] buf, boolean bBigEnding) {
        if (buf == null) {
            throw new IllegalArgumentException("byte array is null!");
        }
        if (buf.length > 2) {
            throw new IllegalArgumentException("byte array size > 2 !");
        }
        short r = 0;
        if (bBigEnding) {
            for (byte aBuf : buf) {
                r <<= 8;
                r |= (aBuf & 0x00ff);
            }
        } else {
            for (int i = buf.length - 1; i >= 0; i--) {
                r <<= 8;
                r |= (buf[i] & 0x00ff);
            }
        }
        return r;
    }

    private static short getShort(byte[] buf) {
        return getShort(buf, testCPU());
    }

    private static boolean testCPU() {
        return ByteOrder.nativeOrder() == ByteOrder.BIG_ENDIAN;
    }
}
