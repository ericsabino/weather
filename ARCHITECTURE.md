# Arquitetura do Sistema - Weather API

## 🎯 Visão Geral
Este projeto implementa uma **Clean Architecture** (Arquitetura Limpa) proposta por Robert C. Martin, organizando o código em camadas concêntricas com dependências direcionadas de fora para dentro. O objetivo é criar um sistema desacoplado, testável e independente de frameworks externos.

## 🏗️ Arquitetura Principal

### Diagrama de Alto Nível
```
┌─────────────────────────────────────────────────────────────────┐
│                        FRAMEWORKS & DRIVERS                     │
│  ┌─────────────────┐                    ┌─────────────────┐     │
│  │   Spring Boot   │                    │  OpenWeather    │     │
│  │   Controllers   │                    │      API        │     │
│  └─────────────────┘                    └─────────────────┘     │
│           │                                       │              │
├───────────┼───────────────────────────────────────┼──────────────┤
│           │              ADAPTERS                 │              │
│  ┌─────────────────┐                    ┌─────────────────┐     │
│  │      HTTP       │                    │   Datastore     │     │
│  │    Adapter      │                    │    Adapter      │     │
│  └─────────────────┘                    └─────────────────┘     │
│           │                                       │              │
├───────────┼───────────────────────────────────────┼──────────────┤
│           │           USE CASES (CORE)            │              │
│           │  ┌─────────────────────────────────┐  │              │
│           └──│    ConsultaClimaPorCidadeBiz    │──┘              │
│              └─────────────────────────────────┘                 │
├─────────────────────────────────────────────────────────────────┤
│                         ENTITIES (CORE)                         │
│    ┌─────────┐  ┌─────────┐  ┌─────────┐  ┌─────────┐          │
│    │  Clima  │  │ Weather │  │  Wind   │  │ Clouds  │   ...    │
│    └─────────┘  └─────────┘  └─────────┘  └─────────┘          │
└─────────────────────────────────────────────────────────────────┘
```

### Principais Componentes
- **Controllers (HTTP Adapter)**: Recebem requisições HTTP e delegam para os casos de uso
- **Use Cases (Business Logic)**: Contêm as regras de negócio da aplicação
- **Entities (Domain Models)**: Representam os conceitos centrais do domínio
- **Datastore (External Adapter)**: Fazem integração com APIs e serviços externos
- **Ports (Interfaces)**: Definem contratos entre camadas

## 📁 Estrutura do Projeto

```
src/main/java/br/com/ems/weather/
├── WeatherApplication.java                 # Ponto de entrada Spring Boot
├── adapter/                                # Camada de Adaptadores
│   ├── http/                              # Adaptadores de entrada (HTTP)
│   │   └── ConsultaClimaPorCidadeController.java  # Controller REST
│   └── datastore/                         # Adaptadores de saída (Dados)
│       ├── ClimaPorCidadeDatastore.java   # Implementação da Port
│       ├── dto/                           # DTOs para APIs externas
│       │   └── ClimaResponseDTO.java      # Response da OpenWeather
│       ├── mapper/                        # Mapeadores DTO ↔ Domain
│       │   └── ClimaMapper.java           # MapStruct interface
│       └── service/                       # Clientes HTTP
│           └── ClimaPorCidadeFeignClient.java  # Feign Client
├── config/                                # Configurações
│   └── JasyptConfiguration.java           # Config de criptografia
└── core/                                  # Núcleo da aplicação
    ├── ConsultaClimaPorCidadeBiz.java    # Caso de uso principal
    ├── model/                            # Entidades de domínio
    │   ├── Clima.java                    # Agregado principal
    │   ├── Weather.java                  # Condições climáticas
    │   ├── WeatherMain.java              # Dados principais
    │   ├── Wind.java                     # Dados do vento
    │   ├── Clouds.java                   # Dados das nuvens
    │   ├── Coord.java                    # Coordenadas
    │   └── Sys.java                      # Dados do sistema
    └── ports/                            # Interfaces (contratos)
        └── ClimaPorCidadePort.java       # Port para busca de clima
```

### Organização por Camadas

#### 🌐 **Camada de Adaptadores de Entrada** (`adapter/http`)
- **Responsabilidade**: Receber requisições HTTP e convertê-las para chamadas de casos de uso
- **Tecnologias**: Spring Web, Controllers REST
- **Componentes**:
  - `ConsultaClimaPorCidadeController`: Expõe endpoint `/weather/v1/city`

#### 🔧 **Camada de Casos de Uso** (`core`)
- **Responsabilidade**: Implementar regras de negócio e orquestrar fluxos
- **Tecnologias**: Java puro, sem dependências externas
- **Componentes**:
  - `ConsultaClimaPorCidadeBiz`: Orquestra a busca de clima por cidade

#### 🏛️ **Camada de Entidades** (`core/model`)
- **Responsabilidade**: Modelar os conceitos centrais do domínio
- **Tecnologias**: Java + Lombok para redução de boilerplate
- **Componentes**: Modelos de domínio (Clima, Weather, Wind, etc.)

#### 🔌 **Camada de Adaptadores de Saída** (`adapter/datastore`)
- **Responsabilidade**: Integrar com sistemas externos (APIs, BDs, etc.)
- **Tecnologias**: OpenFeign, MapStruct, Jasypt
- **Componentes**:
  - `ClimaPorCidadeDatastore`: Implementa a port de busca
  - `ClimaPorCidadeFeignClient`: Cliente HTTP para OpenWeather
  - `ClimaMapper`: Conversão DTO ↔ Domain

#### 📋 **Camada de Portas** (`core/ports`)
- **Responsabilidade**: Definir contratos entre camadas
- **Tecnologias**: Interfaces Java
- **Componentes**:
  - `ClimaPorCidadePort`: Interface para busca de dados climáticos

## 🔄 Fluxo de Dados

### Fluxo Principal: Consulta de Clima
```
1. [HTTP Request] → ConsultaClimaPorCidadeController
   ↓ (converte parâmetros)
   
2. [String cidade] → ConsultaClimaPorCidadeBiz.buscarClimaPorCidade()
   ↓ (aplica regras de negócio)
   
3. [Port Interface] → ClimaPorCidadeDatastore.buscarClimaPorCidade()
   ↓ (implementa integração)
   
4. [Feign Client] → OpenWeather API (HTTP external call)
   ↓ (recebe ClimaResponseDTO)
   
5. [MapStruct] → ClimaMapper.ofClima() (DTO → Domain)
   ↓ (converte para modelo de domínio)
   
6. [Domain Model] → Clima (retorna pela cadeia)
   ↓ (propaga resposta)
   
7. [JSON Response] → Cliente HTTP
```

### Fluxo de Dados Detalhado
1. **Entrada**: Cliente faz GET `/weather/v1/city?name=São Paulo`
2. **Controller**: Valida parâmetros e chama caso de uso
3. **Use Case**: Aplica regras (se houver) e delega para port
4. **Datastore**: Prepara chamada para API externa com configurações
5. **Feign Client**: Executa HTTP GET para OpenWeather com parâmetros
6. **Mapeamento**: MapStruct converte DTO externo para modelo interno
7. **Resposta**: Modelo de domínio é serializado como JSON para cliente

## 🎨 Padrões de Design

### Padrões Implementados

#### **Dependency Inversion Principle (DIP)**
- **Onde**: Core depende de abstrações (ports), não de implementações
- **Por que**: Permite substituir implementações sem afetar regras de negócio
- **Exemplo**: `ConsultaClimaPorCidadeBiz` usa `ClimaPorCidadePort`, não `ClimaPorCidadeDatastore`

#### **Adapter Pattern**
- **Onde**: `ConsultaClimaPorCidadeController` e `ClimaPorCidadeDatastore`
- **Por que**: Isola o core de detalhes de frameworks (Spring, Feign)
- **Exemplo**: Controller adapta HTTP para chamadas Java

#### **Port/Adapter (Hexagonal Architecture)**
- **Onde**: `ClimaPorCidadePort` define contrato, `ClimaPorCidadeDatastore` implementa
- **Por que**: Flexibilidade para trocar fontes de dados
- **Exemplo**: Poderia trocar OpenWeather por AccuWeather sem afetar core

#### **Data Transfer Object (DTO)**
- **Onde**: `ClimaResponseDTO` para comunicação com API externa
- **Por que**: Isola mudanças na API externa dos modelos internos
- **Exemplo**: DTO tem estrutura da OpenWeather, Domain tem estrutura própria

#### **Mapper Pattern**
- **Onde**: `ClimaMapper` usando MapStruct
- **Por que**: Separar conversões de dados da lógica de negócio
- **Exemplo**: Conversão automática DTO → Domain com validações

#### **Builder Pattern**
- **Onde**: Todos os modelos de domínio (via Lombok @Builder)
- **Por que**: Construção fluente e imutabilidade dos objetos
- **Exemplo**: `Clima.builder().name("São Paulo").temp(25.0).build()`

### Princípios Seguidos

#### **Single Responsibility Principle (SRP)**
- **Controller**: Apenas recebe HTTP e delega
- **Use Case**: Apenas regras de negócio
- **Datastore**: Apenas integração externa
- **Mapper**: Apenas conversões

#### **Open/Closed Principle (OCP)**
- **Ports**: Abertas para extensão (novas implementações)
- **Core**: Fechado para modificação de frameworks

#### **Interface Segregation Principle (ISP)**
- **Ports específicas**: `ClimaPorCidadePort` tem apenas método necessário
- **Não há interfaces "gordas" com métodos não relacionados

#### **Dependency Inversion Principle (DIP)**
- **Alto nível** (Use Cases) não dependem de **baixo nível** (Frameworks)
- **Ambos dependem de abstrações** (Ports/Interfaces)

## 🔌 Integrações Externas

### OpenWeather API
- **URL Base**: `http://api.openweathermap.org/`
- **Endpoint**: `data/2.5/weather/`
- **Autenticação**: API Key via parâmetro `appid`
- **Rate Limit**: 1000 calls/day (plano gratuito)
- **Timeout**: 30s (configurável)

### Configurações de Integração
```properties
# Configuração da integração OpenWeather
open-weather-service.ribbon.listOfServers=http://api.openweathermap.org/
open.weather.api.url=data/2.5/weather/
open.weather.api.appid=ENC(chave_criptografada)
open.weather.api.lang=pt_br
open.weather.api.units=metric
```

## 💾 Persistência de Dados
- **Tipo**: Stateless (sem persistência local)
- **Cache**: Não implementado (cada request = nova chamada API)
- **Configuração**: Apenas em `application.properties`
- **Dados sensíveis**: API key criptografada com Jasypt

## 🚀 Deployment e Infraestrutura

### Estratégias de Deploy
1. **JAR Standalone**: `java -jar weather-0.0.1-SNAPSHOT.jar`
2. **Docker Container**: Build via Dockerfile
3. **Cloud Platform**: Heroku, AWS, Google Cloud

### Variáveis de Ambiente
```bash
# Essenciais
OPEN_WEATHER_API_KEY=sua_api_key
JASYPT_ENCRYPTOR_PASSWORD=senha_para_descriptografia

# Opcionais
SERVER_PORT=8080
OPEN_WEATHER_LANG=pt_br
OPEN_WEATHER_UNITS=metric
```

## 📊 Monitoramento e Observabilidade

### Health Checks
- **Endpoint implícito**: Spring Boot Actuator (se habilitado)
- **Verificação básica**: GET `/weather/v1/city?name=London`
- **Logs**: Console output (configurável para arquivo)

### Métricas Sugeridas (não implementadas)
- Latência das chamadas OpenWeather
- Taxa de sucesso/erro das requisições
- Cidades mais consultadas
- Uso de rate limit da API

### Logs
```java
// Exemplo de logs úteis para implementar
log.info("Consultando clima para cidade: {}", cidade);
log.debug("Resposta OpenWeather: {}", climaResponseDTO);
log.error("Erro ao consultar OpenWeather para cidade {}: {}", cidade, exception);
```

## 🧪 Testabilidade

### Vantagens da Arquitetura para Testes

#### **Testes Unitários Isolados**
```java
// Use Case pode ser testado sem frameworks
@Test
void deveConsultarClimaPorCidade() {
    // Given
    ClimaPorCidadePort mockPort = mock(ClimaPorCidadePort.class);
    ConsultaClimaPorCidadeBiz useCase = new ConsultaClimaPorCidadeBiz(mockPort);
    
    // When/Then
    when(mockPort.buscarClimaPorCidade("São Paulo")).thenReturn(climaEsperado);
    // ...
}
```

#### **Testes de Integração por Camada**
```java
// Controller pode ser testado com MockMvc
@WebMvcTest(ConsultaClimaPorCidadeController.class)
class ControllerTest {
    @MockBean
    private ConsultaClimaPorCidadeBiz useCase;
    // ...
}

// Datastore pode ser testado com WireMock
@Test
void deveIntegrarComOpenWeather() {
    // Mock da API externa
    // Teste da implementação real
}
```

## 🔄 Evolução e Extensibilidade

### Pontos de Extensão
1. **Novas fontes de dados**: Implementar `ClimaPorCidadePort`
2. **Novos casos de uso**: Criar novos business classes
3. **Novos endpoints**: Adicionar controllers
4. **Cache**: Implementar em `ClimaPorCidadeDatastore`
5. **Validações**: Adicionar no controller ou use case

### Mudanças Futuras Típicas
- **Adicionar cache Redis**: Implementar decorator da port
- **Múltiplas APIs clima**: Strategy pattern nas implementações
- **Autenticação**: Interceptor no controller
- **Rate limiting**: Filtro ou interceptor
- **Métricas**: Aspects ou interceptors

## 📈 Análise de Qualidade

### Pontos Fortes
✅ **Separação clara de responsabilidades**
✅ **Testabilidade alta** (dependências injetadas)
✅ **Baixo acoplamento** entre camadas
✅ **Flexibilidade** para mudanças
✅ **Independência de frameworks** no core

### Pontos de Melhoria
🔄 **Cache não implementado** (performance)
🔄 **Tratamento de erro limitado** 
🔄 **Validações básicas**
🔄 **Monitoramento ausente**
🔄 **Documentação OpenAPI** não configurada

### Métricas de Qualidade (Sugeridas)
- **Cobertura de testes**: > 80%
- **Complexidade ciclomática**: < 10 por método
- **Acoplamento aferente/eferente**: Baixo
- **Violações arquiteturais**: Zero (via ArchUnit)

Esta arquitetura fornece uma base sólida para evolução e manutenção, seguindo as melhores práticas da Clean Architecture e SOLID principles.
