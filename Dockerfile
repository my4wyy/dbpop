FROM ubuntu:latest AS build

# Instalar dependências necessárias
RUN apt-get update && apt-get install -y openjdk-17-jdk maven

# Definir o diretório de trabalho
WORKDIR /app

# Copiar os arquivos do projeto para o container
COPY . .

# Compilar o projeto usando Maven
RUN mvn clean install -DskipTests=true -Dmaven.compiler.failOnError=false && ls -l target

# Etapa final: imagem com JDK apenas para execução
FROM openjdk:17-jdk-slim

# Expor a porta 8080
EXPOSE 8080

# Copiar o JAR gerado para a imagem final
COPY --from=build /app/target/*.jar app.jar

# Comando de inicialização
ENTRYPOINT [ "java", "-jar", "app.jar" ]
