# SkillBridge API

API REST desenvolvida em Java com Quarkus para plataforma de recomendação de cursos com IA.

## 📋 Descrição

SkillBridge é uma plataforma B2C de aprendizado que utiliza inteligência artificial para recomendar trilhas de cursos personalizadas baseadas no perfil profissional do usuário.

### Funcionalidades Principais

- **Cadastro e Autenticação**: Criação de conta e login de usuários
- **Perfil Profissional**: Gerenciamento de competências, objetivos de carreira e experiências
- **Catálogo de Cursos**: Navegação e busca de cursos por área e nível
- **Inscrições**: Inscrição em cursos com acompanhamento de progresso
- **Trilhas Personalizadas**: Criação e gestão de trilhas de aprendizado
- **Recomendações com IA**: Sistema de recomendação baseado em machine learning

## 🛠️ Tecnologias Utilizadas

- **Java 17**
- **Quarkus 3.6.4** - Framework reativo e cloud-native
- **Hibernate ORM com Panache** - Persistência simplificada
- **Oracle Database** - Banco de dados relacional
- **BCrypt** - Hash de senhas
- **JAX-RS** - RESTful web services
- **Bean Validation** - Validação de dados
- **OpenAPI/Swagger** - Documentação da API
- **Lombok** - Redução de boilerplate

## 📦 Estrutura do Projeto

```
src/main/java/br/com/fiap/skillbridge/
├── config/          # Configurações (CORS, etc)
├── dto/             # Data Transfer Objects
├── exception/       # Tratamento de exceções
├── model/           # Entidades JPA
├── repository/      # Camada de acesso a dados
├── resource/        # Controllers REST
└── service/         # Lógica de negócio
```

## 🗃️ Modelo de Dados

### Entidades Principais

- **Usuario**: Dados básicos e autenticação
- **Perfil**: Informações profissionais do usuário
- **Curso**: Catálogo de cursos disponíveis
- **Inscricao**: Relacionamento usuário-curso com progresso
- **Trilha**: Sequências personalizadas de cursos
- **Recomendacao**: Sugestões geradas pela IA
- **Competencia**: Tags de habilidades (usuário, curso, carreira)

## 🚀 Como Executar

### Pré-requisitos

- Java 17 ou superior
- Maven 3.8+
- Oracle Database (acesso ao oracle.fiap.com.br)

### Configuração

1. Clone o repositório
2. Configure as credenciais do banco de dados em `application.properties`:

```properties
quarkus.datasource.username=seu_usuario
quarkus.datasource.password=sua_senha
quarkus.datasource.jdbc.url=jdbc:oracle:thin:@oracle.fiap.com.br:1521:ORCL
```

### Executar em Modo Dev

```bash
./mvnw quarkus:dev
```

A aplicação estará disponível em: `http://localhost:8080`

### Build para Produção

```bash
./mvnw clean package
java -jar target/quarkus-app/quarkus-run.jar
```

## 📚 Documentação da API

Acesse a documentação interativa Swagger UI:

```
http://localhost:8080/swagger-ui
```

## 🔌 Principais Endpoints

### Usuários
- `POST /api/usuarios` - Criar usuário
- `POST /api/usuarios/login` - Fazer login
- `GET /api/usuarios/{id}` - Buscar usuário
- `PUT /api/usuarios/{id}` - Atualizar usuário
- `DELETE /api/usuarios/{id}` - Deletar usuário

### Perfil
- `GET /api/usuarios/{usuarioId}/perfil` - Buscar perfil
- `PUT /api/usuarios/{usuarioId}/perfil` - Atualizar perfil

### Cursos
- `GET /api/cursos` - Listar cursos
- `GET /api/cursos/{id}` - Buscar curso
- `GET /api/cursos/area/{area}` - Buscar por área
- `GET /api/cursos/nivel/{nivel}` - Buscar por nível
- `GET /api/cursos/pesquisar?nome=` - Pesquisar por nome
- `POST /api/cursos` - Criar curso
- `PUT /api/cursos/{id}` - Atualizar curso
- `DELETE /api/cursos/{id}` - Inativar curso

### Inscrições
- `GET /api/inscricoes/usuario/{usuarioId}` - Listar inscrições
- `GET /api/inscricoes/usuario/{usuarioId}/em-andamento` - Cursos em andamento
- `GET /api/inscricoes/usuario/{usuarioId}/concluidos` - Cursos concluídos
- `POST /api/inscricoes` - Criar inscrição
- `PUT /api/inscricoes/{id}/progresso` - Atualizar progresso
- `PUT /api/inscricoes/{id}/concluir` - Marcar como concluído
- `DELETE /api/inscricoes/{id}` - Cancelar inscrição

## 🔐 Segurança

- Senhas armazenadas com hash BCrypt
- Validação de dados com Bean Validation
- Tratamento global de exceções

## 🎯 Padrões de Design

- **Repository Pattern**: Separação da lógica de acesso a dados
- **DTO Pattern**: Transferência de dados entre camadas
- **Service Layer**: Lógica de negócio centralizada
- **RESTful API**: Endpoints seguindo convenções REST

## 👥 Equipe FIAP

- Bruno - RM: 566366
- João Pedro Bitencourt Goldoni – RM: 564339
- Marina Tamagnini Magalhães – RM: 561786