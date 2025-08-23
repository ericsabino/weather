# Guia de Instalação - Weather API

## 🎯 Ambientes Suportados
- Windows 10/11
- macOS 10.14+
- Linux (Ubuntu 18.04+, CentOS 7+)
- Docker (qualquer SO com Docker)

## 🔧 Pré-requisitos Detalhados

### Software Obrigatório
- **Java 8 ou superior** 
  - Download: https://adoptium.net/temurin/releases/
  - Verificação: `java -version`
  - Exemplo de saída: `openjdk version "1.8.0_xxx"`

- **Maven 3.6+**
  - Download: https://maven.apache.org/download.cgi
  - Verificação: `mvn -version`
  - Exemplo de saída: `Apache Maven 3.6.x`

- **Git** (para clonar o repositório)
  - Download: https://git-scm.com/downloads
  - Verificação: `git --version`

### Configurações do Sistema
- **Memória RAM**: Mínimo 512MB livres
- **Espaço em disco**: 200MB para dependências Maven
- **Porta 8080**: Deve estar disponível (ou configure outra)

## 🚀 Opções de Instalação

### Opção 1: Docker (Recomendado)
```bash
# 1. Clone o repositório
git clone https://github.com/seu-usuario/weather-api.git
cd weather-api

# 2. Configure ambiente
cp .env.example .env
# Edite o arquivo .env e adicione sua API key do OpenWeather

# 3. Build e execute
docker build -t weather-api .
docker run -p 8080:8080 --env-file .env weather-api

# 4. Verifique se funcionou
curl http://localhost:8080/weather/v1/city?name=London
```

### Opção 2: Instalação Local (Desenvolvimento)
```bash
# 1. Clone e acesse o diretório
git clone https://github.com/seu-usuario/weather-api.git
cd weather-api

# 2. Instale dependências e compile
mvn clean install

# 3. Configure variáveis de ambiente (veja seção abaixo)
# Edite src/main/resources/application.properties

# 4. Execute o projeto
mvn spring-boot:run

# 5. Teste a instalação
curl "http://localhost:8080/weather/v1/city?name=São%20Paulo"
```

### Opção 3: Executar JAR Compilado
```bash
# 1. Clone e compile
git clone https://github.com/seu-usuario/weather-api.git
cd weather-api
mvn clean package

# 2. Execute o JAR
java -jar target/weather-0.0.1-SNAPSHOT.jar

# 3. Configure via variáveis de ambiente (opcional)
export OPEN_WEATHER_API_KEY=sua_api_key_aqui
java -jar target/weather-0.0.1-SNAPSHOT.jar
```

## ⚙️ Configuração

### 1. Obtenha uma API Key do OpenWeather
1. Acesse: https://openweathermap.org/api
2. Crie uma conta gratuita
3. Vá para "API keys" no seu dashboard
4. Copie sua API key (ex: `abc123def456...`)

### 2. Configure a API Key

#### Método 1: Criptografada (Produção - Recomendado)
```bash
# Download do Jasypt CLI
wget https://github.com/jasypt/jasypt/releases/download/jasypt-1.9.3/jasypt-1.9.3-dist.zip
unzip jasypt-1.9.3-dist.zip

# Criptografe sua API key
java -cp jasypt-1.9.3/lib/jasypt-1.9.3.jar org.jasypt.intf.cli.JasyptPBEStringEncryptionCLI \
  input="SUA_API_KEY_AQUI" \
  password="mySecretKey" \
  algorithm="PBEWithMD5AndDES"

# Copie o resultado e cole no application.properties
# open.weather.api.appid=ENC(resultado_criptografado)
```

#### Método 2: Texto Plano (Desenvolvimento)
```properties
# Em src/main/resources/application.properties
# Substitua a linha existente por:
open.weather.api.appid=SUA_API_KEY_AQUI
```

#### Método 3: Variável de Ambiente
```bash
# Linux/macOS
export OPEN_WEATHER_API_KEY=sua_api_key_aqui

# Windows
set OPEN_WEATHER_API_KEY=sua_api_key_aqui

# No application.properties
open.weather.api.appid=${OPEN_WEATHER_API_KEY}
```

### Variáveis de Ambiente Disponíveis
| Variável | Descrição | Valor Padrão | Obrigatório |
|----------|-----------|--------------|-------------|
| `OPEN_WEATHER_API_KEY` | Chave da API OpenWeather | - | ✅ |
| `SERVER_PORT` | Porta do servidor | 8080 | ❌ |
| `OPEN_WEATHER_LANG` | Idioma das respostas | pt_br | ❌ |
| `OPEN_WEATHER_UNITS` | Unidade de medida | metric | ❌ |
| `JASYPT_ENCRYPTOR_PASSWORD` | Senha para descriptografia | mySecretKey | ❌ |

### Arquivo de Configuração Principal
**Localização:** `src/main/resources/application.properties`

```properties
# Configuração do servidor
server.port=8080

# Configuração Jasypt
jasypt.encryptor.bean=encryptorBean

# Configuração OpenWeather
open-weather-service.ribbon.listOfServers=http://api.openweathermap.org/
open.weather.api.url=data/2.5/weather/
open.weather.api.appid=ENC(sua_chave_criptografada_aqui)
open.weather.api.lang=pt_br
open.weather.api.units=metric
```

## 🔍 Verificação da Instalação

### Health Check Básico
```bash
# 1. Verificar se o serviço está rodando
curl http://localhost:8080/weather/v1/city?name=London

# 2. Resposta esperada (exemplo):
{
  "id": 2643743,
  "name": "London",
  "main": {
    "temp": 15.2,
    "humidity": 72
  },
  "weather": [{
    "main": "Clouds",
    "description": "nuvens dispersas"
  }]
}
```

### Testes com Diferentes Cidades
```bash
# Teste com cidade brasileira
curl "http://localhost:8080/weather/v1/city?name=Rio%20de%20Janeiro"

# Teste com cidade internacional
curl "http://localhost:8080/weather/v1/city?name=Tokyo"

# Teste com acentos
curl "http://localhost:8080/weather/v1/city?name=São%20Paulo"
```

### Logs da Aplicação
- **Console**: Logs aparecem no terminal onde executou a aplicação
- **Arquivo**: Por padrão não há arquivo de log (apenas console)
- **Nível**: INFO por padrão

## 🐛 Troubleshooting

### Problemas Comuns

#### Erro: "Invalid API key"
**Sintoma:** `{"cod":401,"message":"Invalid API key"}`
**Causa:** API key incorreta ou não configurada
**Solução:**
1. Verifique se copiou a API key corretamente
2. Confirme se a key está ativa no dashboard OpenWeather
3. Se usando criptografia, verifique se a descriptografia está funcionando

#### Erro: "Connection refused" / "Timeout"
**Sintoma:** Erro de conexão ao tentar acessar OpenWeather
**Causa:** Problemas de rede ou firewall
**Solução:**
```bash
# Teste conectividade direta
curl "http://api.openweathermap.org/data/2.5/weather?q=London&appid=SUA_API_KEY"

# Verifique proxy/firewall se necessário
```

#### Erro: "Port already in use"
**Sintoma:** `Port 8080 was already in use`
**Causa:** Outra aplicação usando a porta 8080
**Solução:**
```bash
# Opção 1: Use outra porta
mvn spring-boot:run -Dspring-boot.run.arguments=--server.port=8081

# Opção 2: Encontre e pare o processo usando a porta
# Windows:
netstat -ano | findstr :8080
taskkill /PID <PID> /F

# Linux/macOS:
lsof -ti:8080 | xargs kill -9
```

#### Erro: MapStruct implementation not found
**Sintoma:** `No implementation was created for ClimaMapper`
**Causa:** Maven não executou o processamento de annotations
**Solução:**
```bash
# Execute clean install para forçar recompilação
mvn clean install

# Se persistir, verifique se tem a configuração correta no pom.xml
```

### Debugging Avançado

#### Ativar Debug Logs
Adicione ao `application.properties`:
```properties
logging.level.br.com.ems.weather=DEBUG
logging.level.org.springframework.cloud.openfeign=DEBUG
```

#### Verificar Configuração Jasypt
```bash
# Teste manual de criptografia/descriptografia
java -cp jasypt-1.9.3.jar org.jasypt.intf.cli.JasyptPBEStringDecryptionCLI \
  input="valor_criptografado" \
  password="mySecretKey" \
  algorithm="PBEWithMD5AndDES"
```

#### Monitorar Requisições HTTP
```bash
# Execute com proxy para ver requisições
java -Dhttp.proxyHost=localhost -Dhttp.proxyPort=8888 -jar target/weather-0.0.1-SNAPSHOT.jar
```

## 📊 Próximos Passos

Após instalação bem-sucedida:
1. Leia a [Documentação da API](./API_DOCS.md) para conhecer todos os endpoints
2. Consulte o [Guia de Arquitetura](./ARCHITECTURE.md) para entender o código
3. Veja a [Análise de Código](./CODE_ANALYSIS.md) para detalhes técnicos

## 🆘 Precisa de Ajuda?

- **Issues**: Reporte problemas no GitHub Issues
- **Documentação**: Consulte os demais arquivos de documentação
- **Contato**: [seu.email@example.com]
