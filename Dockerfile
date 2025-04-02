# Etapa 1: Construção do projeto usando Maven
FROM maven:3.8-openjdk-11-slim AS build

# Definir o diretório de trabalho dentro do container
WORKDIR /app

# Copiar o arquivo pom.xml e baixar as dependências do Maven
COPY pom.xml .
RUN mvn dependency:go-offline

# Copiar o restante do código-fonte e construir o projeto
COPY src /app/src
RUN mvn clean package -DskipTests

# Etapa 2: Construção da imagem final (com a aplicação empacotada)
FROM openjdk:11-jre-slim

# Definir o diretório de trabalho para a aplicação
WORKDIR /app

# Copiar o arquivo JAR gerado para o container
COPY --from=build /app/target/seu-arquivo.jar /app/seu-arquivo.jar

# Expor a porta em que a API irá rodar (ajuste conforme a configuração da sua API)
EXPOSE 8080

# Comando para rodar a aplicação Java
CMD ["java", "-jar", "/app/seu-arquivo.jar"]
