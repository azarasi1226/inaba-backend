package jp.inaba.service.application.saga.unregisterproduct

import org.axonframework.config.EventProcessingConfigurer
import org.axonframework.eventhandling.PropagatingErrorHandler
import org.axonframework.eventhandling.TrackingEventProcessorConfiguration
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Configuration

@Configuration
class UnRegisterProductSagaEventProcessor {
    companion object {
        const val PROCESSOR_NAME = "un-register-product-saga"
        private const val PROCESSOR_COUNT = 2
    }

    @Autowired
    fun config(epc: EventProcessingConfigurer) {
        epc.registerTrackingEventProcessorConfiguration(PROCESSOR_NAME) {
            TrackingEventProcessorConfiguration
                .forParallelProcessing(PROCESSOR_COUNT)
        }
            .registerListenerInvocationErrorHandler(PROCESSOR_NAME) { PropagatingErrorHandler.INSTANCE }
    }
}