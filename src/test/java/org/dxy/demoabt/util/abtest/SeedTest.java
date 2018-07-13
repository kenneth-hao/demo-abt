package org.dxy.demoabt.util.abtest;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Sets;
import com.google.common.hash.Hashing;
import org.junit.Test;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import java.io.IOException;
import java.util.Set;

public class SeedTest {

    @Test
    public void atTest() throws IOException {
        ResourceLoader loader = new DefaultResourceLoader();
        Resource resource = loader.getResource(DefaultResourceLoader.CLASSPATH_URL_PREFIX + "test/mc-all-0711");
        JSONObject o = JSON.parseObject(resource.getInputStream(), JSONObject.class);

        Set<String> mcs = Sets.newHashSet();
        o.getJSONObject("aggregations").getJSONObject("mc").getJSONArray("buckets").forEach(mcJo -> {
            mcs.add(((JSONObject)mcJo).getString("key"));
        });
        System.out.println("mc 样本总量：" + mcs.size());

        int l=0, r=0;
        for (String mc : mcs) {
            if (Seed.ofMc(mc).at(0, 49)) {
                ++l;
            } else {
                ++r;
            }
        }

        System.out.println("mc 样本分布：0% ~ 49%：" + l + " ===== 50% ~ 99%：流量" + r);
    }
}
