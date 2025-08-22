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
| GET | /users | Sim | Lista usuários (paginação) |
| GET | /users/{iduser} | Sim | Detalha usuário |
| PUT | /users/{iduser} | Sim (próprio) | Atualiza usuário |
| DELETE | /users/{iduser} | Sim (próprio) | Remove usuário |
| POST | /users/{iduser}/drink | Sim (próprio) | Registra consumo |
| GET | /users/{iduser}/history?date=YYYY-MM-DD | Sim (próprio) | Histórico diário |
| GET | /users/ranking/day?date=YYYY-MM-DD | Sim | Ranking por dia |
| GET | /users/ranking/last?days=X | Sim | Ranking últimos X dias |

Header de autorização:
```
Authorization: Bearer <token>
```

## Configuração
Editar `application.properties` com usuário e senha do PostgreSQL e um segredo JWT forte.

Executar migrations automaticamente ao subir a aplicação.

## Build & Run
Assumindo Maven instalado:
```
mvn spring-boot:run
```

## Observações
* `spring.jpa.hibernate.ddl-auto=validate` garante que apenas o schema gerenciado pelo Flyway é aceito.
* Ajuste índices e estratégias de cache conforme escala.
* Substitua `app.jwt.secret` por valor >= 256 bits (ou Base64) em produção.
