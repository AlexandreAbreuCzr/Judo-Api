# Judo-Candoi-MVP

Projeto fullstack separado em duas pastas, seguindo o padrao dos seus projetos:

- `Judo-Candoi-Api` -> backend Spring Boot 17 + JPA + PostgreSQL/H2
- `Judo-Candoi-Web` -> frontend React + Vite

## Subir backend
```powershell
cd Judo-Candoi-Api
$env:JAVA_HOME="C:\Program Files\Java\jdk-17"
$env:Path="$env:JAVA_HOME\\bin;$env:Path"
# opcional: usar PostgreSQL
# $env:DB_URL="jdbc:postgresql://localhost:5432/judo_candoi"
# $env:DB_USERNAME="postgres"
# $env:DB_PASSWORD="postgres"
./mvnw spring-boot:run
```

## Subir frontend
```powershell
cd Judo-Candoi-Web
npm install
npm run dev
```

## URLS
- Frontend: `http://localhost:5173`
- Backend: `http://localhost:8080`
- API de conteudo: `http://localhost:8080/api/v1/site/content`
- API de leads: `http://localhost:8080/api/v1/leads/experimental-class`
- Painel admin: `http://localhost:5173/?admin=1`
- API admin blog: `http://localhost:8080/api/v1/admin/blog-posts`
- API admin aluno destaque: `http://localhost:8080/api/v1/admin/pride-students`
- API admin configuracoes: `http://localhost:8080/api/v1/admin/site-settings`
