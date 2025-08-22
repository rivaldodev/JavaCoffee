-- Tabela de usuários
CREATE TABLE users (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL
);

-- Tabela de registros de consumo de café
CREATE TABLE coffee_log (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL,
    drink_timestamp TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    quantity INTEGER NOT NULL,
    CONSTRAINT fk_user FOREIGN KEY(user_id) REFERENCES users(id) ON DELETE CASCADE
);

CREATE INDEX idx_coffee_log_user_time ON coffee_log(user_id, drink_timestamp);
