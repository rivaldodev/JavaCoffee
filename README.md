# Coffee Tracker API (Consumo de Café)

API REST em Spring Boot para registrar quantas vezes os usuários consumiram café.

## Stack
* Java 17
* Spring Boot (Web, Data JPA, Security, Validation)
* PostgreSQL
* Flyway
* JWT (jjwt)

## Endpoints Principais
| Método | Endpoint | Autenticação | Descrição |
|--------|----------|--------------|-----------|
| POST | /users | Não | Cria usuário |
| POST | /login | Não | Autentica e retorna token |
| GET | /users | Sim | Lista usuários (paginação 1-indexed) |
| GET | /users/{iduser} | Sim | Detalha usuário |
| PUT | /users/{iduser} | Sim (próprio) | Atualiza usuário (completo) |
| PATCH | /users/{iduser} | Sim (próprio) | Atualiza usuário (parcial) |
| DELETE | /users/{iduser} | Sim (próprio) | Remove usuário |
| POST | /users/{iduser}/drink | Sim (próprio) | Registra consumo |
| GET | /users/{iduser}/history?date=YYYY-MM-DD | Sim (próprio) | Histórico diário |
| GET | /users/ranking/day?date=YYYY-MM-DD | Sim | Ranking por dia |
| GET | /users/ranking/last?days=X | Sim | Ranking últimos X dias |

Header de autorização:
```

## Exemplos cURL

### Autenticação e Registro

#### Registrar novo usuário (POST /users)
```bash
curl -X POST http://localhost:8080/users \
  -H "Content-Type: application/json" \
  -d '{
    "name": "João Silva",
    "email": "joao@email.com",
    "password": "senha123"
  }'
```

#### Efetuar login (POST /login)
```bash
curl -X POST http://localhost:8080/login \
  -H "Content-Type: application/json" \
  -d '{
    "email": "joao@email.com",
    "password": "senha123"
  }'
```

### Gerenciamento de Usuário (Requer token)

#### Listar usuários (GET /users)
```bash
curl "http://localhost:8080/users?page=1" \
  -H "Authorization: Bearer <token>"
```

#### Detalhar perfil (GET /users/{id})
```bash
curl http://localhost:8080/users/1 \
  -H "Authorization: Bearer <token>"
```

#### Atualização Completa (PUT /users/{id})
```bash
curl -X PUT http://localhost:8080/users/1 \
  -H "Authorization: Bearer <token>" \
  -H "Content-Type: application/json" \
  -d '{
    "name": "João Silva",
    "email": "joao_novo@email.com",
    "password": "novasenhasegura"
  }'
```

#### Atualização Parcial (PATCH /users/{id})
```bash
curl -X PATCH http://localhost:8080/users/1 \
  -H "Authorization: Bearer <token>" \
  -H "Content-Type: application/json" \
  -d '{
    "name": "João da Silva"
  }'
```

#### Remover conta (DELETE /users/{id})
```bash
curl -X DELETE http://localhost:8080/users/1 \
  -H "Authorization: Bearer <token>"
```

### Consumo e Rankings (Requer token)

#### Registrar consumo de café (POST /users/{id}/drink)
```bash
curl -X POST http://localhost:8080/users/1/drink \
  -H "Authorization: Bearer <token>" \
  -H "Content-Type: application/json" \
  -d '{
    "drink": "Expresso"
  }'
```

#### Histórico diário (GET /users/{id}/history)
```bash
curl "http://localhost:8080/users/1/history?date=2024-03-20" \
  -H "Authorization: Bearer <token>"
```

#### Ranking Diário (GET /ranking/day)
```bash
curl "http://localhost:8080/ranking/day?date=2024-03-20" \
  -H "Authorization: Bearer <token>"
```

#### Ranking Últimos Dias (GET /ranking/last)
```bash
curl "http://localhost:8080/ranking/last?days=7" \
  -H "Authorization: Bearer <token>"
```

## Configuração
Editar `application.properties` com usuário e senha do PostgreSQL e um segredo JWT forte.

Executar migrations automaticamente ao subir a aplicação.

## Build & Run
Assumindo Maven instalado:
```bash
# Windows
.\mvnw.cmd spring-boot:run

# Linux/macOS
./mvnw spring-boot:run
```

## Observações
* `spring.jpa.hibernate.ddl-auto=validate` garante que apenas o schema gerenciado pelo Flyway é aceito.
* Ajuste índices e estratégias de cache conforme escala.
* Substitua `app.jwt.secret` por valor >= 256 bits (ou Base64) em produção.
