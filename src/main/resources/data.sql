-- src/main/resources/data.sql

-- Insertar usuario de prueba
INSERT INTO users (id, email, password, nombre, apellidos, activo, fecha_creacion, fecha_actualizacion)
VALUES (1, 'demo@finanzas.com', '$2a$12$LQv3c1yqBWVHxkd0LHAkCOYz6TtxMQJqhN8/LewY5vQpQqXpW1Kti', 'Usuario', 'Demo', true, datetime('now'), datetime('now'));

-- Insertar gastos de prueba
INSERT INTO gastos (concepto, monto, categoria, fecha, metodo_pago, notas, user_id, fecha_creacion)
VALUES
    ('Supermercado Soriana', 1500.00, 'Alimentación', '2024-12-24', 'Tarjeta de Crédito', 'Despensa semanal', 1, datetime('now')),
    ('Gasolina Pemex', 800.00, 'Transporte', '2024-12-23', 'Efectivo', 'Tanque lleno', 1, datetime('now')),
    ('Netflix', 199.00, 'Entretenimiento', '2024-12-22', 'Tarjeta de Débito', 'Suscripción mensual', 1, datetime('now')),
    ('Restaurante Sanborns', 450.00, 'Alimentación', '2024-12-21', 'Tarjeta de Crédito', 'Comida familiar', 1, datetime('now')),
    ('Amazon - Libro', 320.00, 'Educación', '2024-12-20', 'Tarjeta de Crédito', 'Libro de programación', 1, datetime('now')),
    ('Uber', 150.00, 'Transporte', '2024-12-19', 'Tarjeta de Débito', 'Viaje al trabajo', 1, datetime('now')),
    ('Spotify Premium', 115.00, 'Entretenimiento', '2024-12-18', 'Tarjeta de Crédito', 'Suscripción mensual', 1, datetime('now')),
    ('Farmacia Guadalajara', 280.00, 'Salud', '2024-12-17', 'Efectivo', 'Medicamentos', 1, datetime('now'));