INSERT INTO demo_events (
    id,
    status,
    created_at,
    retry_count,
    priority,
    payload
) VALUES (
    '00000000-0000-0000-0000-000000000001',
    'PENDING',
    CURRENT_TIMESTAMP,
    0,
    10,
    'Hello from the transactional inbox/outbox demo'
)
ON CONFLICT (id) DO NOTHING;
