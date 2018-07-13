package org.dxy.demoabt.util.abtest;

import com.google.common.hash.Hashing;
import org.springframework.util.Assert;

import java.nio.charset.Charset;

/**
 * 根据分区总数生成一个 Seed, Seed 的值会落在分区[0, max] 之间。
 */
public class Seed {

    /**
     * 默认分区数 [0-99]
     */
    public static final int PARTITION_NUM_DEFAULT = 100;

    private int seed;

    private int min = 0;
    private int max;

    /**
     * 通过 mc(机器码) 生成 Seed, 默认分区总数 {@link PARTITION_NUM_DEFAULT }
     *
     * @param mc
     * @return
     */
    public static Seed ofMc(String mc) {
        Seed seed = new Seed();

        seed.max = PARTITION_NUM_DEFAULT;
        seed.seed = Hashing.consistentHash(Hashing.sha256().hashString(mc, Charset.forName("utf-8")).asLong(), seed.max);

        return seed;
    }

    /**
     * 判断 seed 是否在指定的区间范围内 [l, h]
     *
     * @param l 区间最小值
     * @param h 区间最大值
     * @return
     */
    public boolean at(int l, int h) {
        validate(l, h);
        return seed >= l & seed <= h;
    }

    private void validate(int l, int h) {
        Assert.isTrue(l >= min, "");
        Assert.isTrue(h < max, "");
    }

}
