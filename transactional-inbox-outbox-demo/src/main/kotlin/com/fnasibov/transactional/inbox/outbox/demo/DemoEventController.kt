package com.fnasibov.transactional.inbox.outbox.demo

import com.fnasibov.transactional.inbox.outbox.starter.r2dbc.api.model.EventStatus
import kotlinx.coroutines.reactor.awaitSingle
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.time.ZonedDateTime
import java.util.UUID

@RestController
@RequestMapping("/demo-events")
class DemoEventController(
    private val template: R2dbcEntityTemplate
) {

    @PostMapping
    suspend fun createEvent(
        @RequestBody request: CreateDemoEventRequest
    ): DemoEventResponse {
        val event = DemoEvent(
            id = UUID.randomUUID(),
            status = EventStatus.PENDING,
            createdAt = ZonedDateTime.now(),
            payload = request.payload
        )

        val saved = template.insert(event).awaitSingle()

        return DemoEventResponse(
            id = saved.id,
            status = saved.status,
            payload = saved.payload
        )
    }
}

data class CreateDemoEventRequest(
    val payload: String,
)

data class DemoEventResponse(
    val id: UUID,
    val status: EventStatus,
    val payload: String
)
