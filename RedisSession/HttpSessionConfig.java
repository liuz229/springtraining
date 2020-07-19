package jp.co.ssd.demo.web.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.session.data.redis.config.ConfigureRedisAction;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

/**
 * セッション初期化用コンフィグ <br>
 * ※maxInactiveIntervalInSecondsにはセッションタイムアウトまでの時間を秒単位で設定します。
 *
 * @author ハイマックス 大松
 *
 */
@Configuration
@EnableRedisHttpSession(maxInactiveIntervalInSeconds = 1800)
public class HttpSessionConfig {
}
