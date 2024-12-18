package jp.inaba.identity.service.configuration

import jp.inaba.identity.share.domain.user.UserIdFactory
import jp.inaba.identity.share.domain.user.UserIdFactoryImpl
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class CommonConfiguration {
    @Bean
    fun userIdFactory(): UserIdFactory {
        return UserIdFactoryImpl()
    }
}
