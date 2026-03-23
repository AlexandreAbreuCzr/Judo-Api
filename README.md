# Judo-Candoi-Api

API em Spring Boot (Java 17) para alimentar o site institucional do Judo Candoi.

## Stack
- Java 17+
- Spring Boot 3.5.x
- Spring Web + Validation + Spring Data JPA
- PostgreSQL (producao) e H2 (fallback local)

## Endpoints
- `GET /api/v1/site/content`
  - Retorna o conteudo da landing page.
- `POST /api/v1/leads/experimental-class`
  - Cria lead de aula experimental.
- `GET /api/v1/leads/experimental-class`
  - Lista leads gravados em banco.
- `GET /api/v1/uploads/images/{id}` (ou `/api/v1/uploads/images/{id}/{nome-arquivo}`)
  - Retorna imagem enviada no painel admin.

### Painel admin (protegido por senha em `X-Admin-Password`)
- `GET /api/v1/admin/auth/check`
- `GET /api/v1/admin/site-settings`
- `PUT /api/v1/admin/site-settings`
- `GET /api/v1/admin/blog-posts`
- `POST /api/v1/admin/blog-posts`
- `PUT /api/v1/admin/blog-posts/{id}`
- `DELETE /api/v1/admin/blog-posts/{id}`
- `GET /api/v1/admin/pride-students`
- `POST /api/v1/admin/pride-students`
- `PUT /api/v1/admin/pride-students/{id}`
- `DELETE /api/v1/admin/pride-students/{id}`
- `POST /api/v1/admin/uploads/images`
  - Upload de imagem para uso em blog/alunos destaque/patrocinadores (persistido em banco).

### Conteudo publico mutavel no site
- `blogPosts` em `GET /api/v1/site/content` vem dos posts ativos do blog
- `prideStudents` em `GET /api/v1/site/content` vem dos alunos destaque ativos

## Executar localmente
1. Configure Java 17 no terminal (se seu Java padrao estiver em 8):

```powershell
$env:JAVA_HOME="C:\Program Files\Java\jdk-17"
$env:Path="$env:JAVA_HOME\\bin;$env:Path"
```

2. Rode a API:

```powershell
./mvnw spring-boot:run
```

A API sobe em `http://localhost:8080`.

## Variaveis uteis
- `DB_URL_JUDO` ou `DB_URL` (ex: `jdbc:postgresql://localhost:5432/judo_candoi`)
- `DB_USERNAME`
- `DB_PASSWORD`
- `JPA_DDL_AUTO` (default: `update`)
- `JPA_SHOW_SQL` (default: `false`)
- `CORS_ALLOWED_ORIGINS` (default: `http://localhost:5173`)
- `ADMIN_PASSWORD` (senha compartilhada do painel admin)
- `WHATSAPP_NUMBER`
- `INSTAGRAM_HANDLE`
- `ACADEMY_ADDRESS`
- `GOOGLE_MAPS_EMBED`

## Exemplo rapido (PostgreSQL)
```powershell
$env:DB_URL="jdbc:postgresql://localhost:5432/judo_candoi"
$env:DB_USERNAME="postgres"
$env:DB_PASSWORD="postgres"
./mvnw spring-boot:run
```
