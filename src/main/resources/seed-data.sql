-- Script para popular dados de teste no banco Oracle
-- Execute este script após criar todas as tabelas

-- Inserir cursos de exemplo
INSERT INTO TB_CURSO (ID_CURSO, NOME, DESCRICAO, AREA, NIVEL, CARGA_HORARIA, URL_EXTERNO, IMAGEM_URL) 
VALUES (SEQ_CURSO.NEXTVAL, 'Java Fundamentos', 'Aprenda os fundamentos da linguagem Java', 'Tecnologia', 'BASICO', 40.00, 
'https://exemplo.com/java-fundamentos', 'https://exemplo.com/img/java.jpg');

INSERT INTO TB_CURSO (ID_CURSO, NOME, DESCRICAO, AREA, NIVEL, CARGA_HORARIA, URL_EXTERNO, IMAGEM_URL) 
VALUES (SEQ_CURSO.NEXTVAL, 'Python para Data Science', 'Python aplicado em ciência de dados', 'Tecnologia', 'INTERMEDIARIO', 60.00, 
'https://exemplo.com/python-data-science', 'https://exemplo.com/img/python.jpg');

INSERT INTO TB_CURSO (ID_CURSO, NOME, DESCRICAO, AREA, NIVEL, CARGA_HORARIA, URL_EXTERNO, IMAGEM_URL) 
VALUES (SEQ_CURSO.NEXTVAL, 'Machine Learning Avançado', 'Técnicas avançadas de ML', 'Tecnologia', 'AVANCADO', 80.00, 
'https://exemplo.com/ml-avancado', 'https://exemplo.com/img/ml.jpg');

INSERT INTO TB_CURSO (ID_CURSO, NOME, DESCRICAO, AREA, NIVEL, CARGA_HORARIA, URL_EXTERNO, IMAGEM_URL) 
VALUES (SEQ_CURSO.NEXTVAL, 'Gestão de Projetos Ágeis', 'Metodologias ágeis para gestão', 'Gestao', 'INTERMEDIARIO', 30.00, 
'https://exemplo.com/agile', 'https://exemplo.com/img/agile.jpg');

INSERT INTO TB_CURSO (ID_CURSO, NOME, DESCRICAO, AREA, NIVEL, CARGA_HORARIA, URL_EXTERNO, IMAGEM_URL) 
VALUES (SEQ_CURSO.NEXTVAL, 'React Native Completo', 'Desenvolvimento mobile com React Native', 'Tecnologia', 'INTERMEDIARIO', 50.00, 
'https://exemplo.com/react-native', 'https://exemplo.com/img/react.jpg');

-- Inserir carreiras de exemplo
INSERT INTO TB_CARREIRA (ID_CARREIRA, TITULO_CARREIRA, DESCRICAO, AREA, NIVEL_ENTRADA)
VALUES (SEQ_CARREIRA.NEXTVAL, 'Desenvolvedor Full Stack', 'Desenvolvedor com conhecimento em front e backend', 'Tecnologia', 'INTERMEDIARIO');

INSERT INTO TB_CARREIRA (ID_CARREIRA, TITULO_CARREIRA, DESCRICAO, AREA, NIVEL_ENTRADA)
VALUES (SEQ_CARREIRA.NEXTVAL, 'Cientista de Dados', 'Profissional especializado em análise de dados e ML', 'Tecnologia', 'INTERMEDIARIO');

INSERT INTO TB_CARREIRA (ID_CARREIRA, TITULO_CARREIRA, DESCRICAO, AREA, NIVEL_ENTRADA)
VALUES (SEQ_CARREIRA.NEXTVAL, 'Gerente de Projetos', 'Gestão de projetos e equipes', 'Gestao', 'INTERMEDIARIO');

-- Inserir competências para cursos
INSERT INTO TB_COMPETENCIA_CURSO (ID_COMPETENCIA_CURSO, ID_CURSO, NOME_COMPETENCIA, NIVEL_ENSINADO)
SELECT SEQ_COMPETENCIA_CURSO.NEXTVAL, c.ID_CURSO, 'Java', 'BASICO'
FROM TB_CURSO c WHERE c.NOME = 'Java Fundamentos';

INSERT INTO TB_COMPETENCIA_CURSO (ID_COMPETENCIA_CURSO, ID_CURSO, NOME_COMPETENCIA, NIVEL_ENSINADO)
SELECT SEQ_COMPETENCIA_CURSO.NEXTVAL, c.ID_CURSO, 'POO', 'BASICO'
FROM TB_CURSO c WHERE c.NOME = 'Java Fundamentos';

INSERT INTO TB_COMPETENCIA_CURSO (ID_COMPETENCIA_CURSO, ID_CURSO, NOME_COMPETENCIA, NIVEL_ENSINADO)
SELECT SEQ_COMPETENCIA_CURSO.NEXTVAL, c.ID_CURSO, 'Python', 'INTERMEDIARIO'
FROM TB_CURSO c WHERE c.NOME = 'Python para Data Science';

INSERT INTO TB_COMPETENCIA_CURSO (ID_COMPETENCIA_CURSO, ID_CURSO, NOME_COMPETENCIA, NIVEL_ENSINADO)
SELECT SEQ_COMPETENCIA_CURSO.NEXTVAL, c.ID_CURSO, 'Pandas', 'INTERMEDIARIO'
FROM TB_CURSO c WHERE c.NOME = 'Python para Data Science';

INSERT INTO TB_COMPETENCIA_CURSO (ID_COMPETENCIA_CURSO, ID_CURSO, NOME_COMPETENCIA, NIVEL_ENSINADO)
SELECT SEQ_COMPETENCIA_CURSO.NEXTVAL, c.ID_CURSO, 'Machine Learning', 'AVANCADO'
FROM TB_CURSO c WHERE c.NOME = 'Machine Learning Avançado';

-- Commit das alterações
COMMIT;
