package jp.inaba.service.infrastructure.projector.lookupstock

import org.axonframework.config.EventProcessingConfigurer
import org.axonframework.eventhandling.PropagatingErrorHandler
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Configuration

@Configuration
class LookupStockProjectorEventProcessor {
    companion object {
        const val PROCESSOR_NAME = "lookup-stock-projector"
    }

    @Autowired
    fun config(epc: EventProcessingConfigurer) {
        epc.registerSubscribingEventProcessor(PROCESSOR_NAME)
            // LookupTableの書き込みに失敗した場合、Eventが発行されたこともなかったことにする。
            .registerListenerInvocationErrorHandler(PROCESSOR_NAME) { PropagatingErrorHandler.INSTANCE }
    }
}