/* Copyright © 2021 syske. All rights reserved. */
package io.github.syske.springbootbucketlimit.config;

import io.github.syske.springbootbucketlimit.bucket.LeakyBucket;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author syske
 * @version 1.0
 * @date 2021-11-01 22:24
 */
@Configuration
public class LeakyBucketConfig {

    @Bean("leakyBucket")
    public LeakyBucket leakyBucket() {
        return new LeakyBucket(5);
    }
}
