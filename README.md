# Weather API

![Java](https://img.shields.io/badge/Java-1.8-orange) ![Spring Boot](https://img.shields.io/badge/Spring%20Boot-2.3.7-brightgreen) ![Maven](https://img.shields.io/badge/Maven-Build-blue) ![License](https://img.shields.io/badge/License-MIT-yellow)

## 📖 Sobre o Projeto
API REST que integra com OpenWeather para fornecer dados climáticos em tempo real de qualquer cidade do mundo, implementada seguindo os princípios da Clean Architecture.

## ⚡ Quick Start

### Pré-requisitos
- Java 8 ou superior
- Maven 3.6+
- Chave de API do OpenWeather

### Instalação Rápida
```bash
git clone https://github.com/seu-usuario/weather-api.git
cd weather-api
mvn install
mvn spring-boot:run
```

### Primeiro Uso
- Acesse: http://localhost:8080/weather/v1/city?name=São%20Paulo
- Teste também: http://localhost:8080/weather/v1/city?name=London

## 🎯 Principais Funcionalidades
- ✅ **Consulta Climática** - Dados em tempo real via OpenWeather API
- ✅ **Múltiplas Cidades** - Suporte a qualquer cidade mundial
- ✅ **Dados Localizados** - Respostas em português brasileiro
- ✅ **Clean Architecture** - Código organizado e testável
- ✅ **Segurança** - API Key criptografada com Jasypt

## 📚 Documentação Completa

| Documento | Descrição | Para quem |
|-----------|-----------|-----------|
| [Installation Guide](./INSTALLATION.md) | Setup detalhado e configuração | Desenvolvedores |
| [API Documentation](./API_DOCS.md) | Endpoints e exemplos de uso | Integradores |
| [Architecture Guide](./ARCHITECTURE.md) | Visão técnica e padrões | Arquitetos/Devs |
| [Code Analysis](./CODE_ANALYSIS.md) | Análise técnica detalhada | Desenvolvedores |

## 🏗️ Tecnologias Principais
- **Java 8**: Linguagem principal - Compatibilidade e estabilidade
- **Spring Boot 2.3.7**: Framework principal - Configuração rápida e produtividade
- **OpenFeign**: Cliente HTTP - Integração simplificada com APIs REST
- **MapStruct 1.3.0**: Mapeamento de objetos - Performance e type-safety
- **Lombok**: Produtividade - Redução de boilerplate code
- **Jasypt**: Segurança - Criptografia de dados sensíveis
- **Maven**: Gerenciamento de dependências

## 🎨 Arquitetura
Este projeto implementa **Clean Architecture** com separação clara de responsabilidades:

```
┌─────────────────┐    ┌─────────────────┐    ┌─────────────────┐
│   Controllers   │────│   Use Cases     │────│   Data Sources  │
│   (Adapter)     │    │     (Core)      │    │   (Adapter)     │
└─────────────────┘    └─────────────────┘    └─────────────────┘
```

## 🔧 Configuração Rápida

1. **Obtenha uma API Key do OpenWeather:**
   - Registre-se em: https://openweathermap.org/api
   - Copie sua API key

2. **Configure a chave criptografada:**
   ```bash
   # Execute para criptografar sua API key
   java -cp jasypt-1.9.3.jar org.jasypt.intf.cli.JasyptPBEStringEncryptionCLI \
   input="SUA_API_KEY" password="mySecretKey" algorithm="PBEWithMD5AndDES"
   ```

3. **Atualize o application.properties:**
   ```properties
   open.weather.api.appid=ENC(sua_chave_criptografada)
   ```

## 📋 Exemplo de Resposta
```json
{
  "id": 3448439,
  "name": "São Paulo",
  "main": {
    "temp": 22.5,
    "humidity": 65,
    "pressure": 1013
  },
  "weather": [{
    "main": "Clear",
    "description": "céu limpo"
  }],
  "wind": {
    "speed": 3.1
  }
}
```

## 🤝 Contribuindo
1. Faça um fork do projeto
2. Crie uma branch para sua feature (`git checkout -b feature/AmazingFeature`)
3. Commit suas mudanças (`git commit -m 'Add some AmazingFeature'`)
4. Push para a branch (`git push origin feature/AmazingFeature`)
5. Abra um Pull Request

## 📄 Licença
Distribuído sob a licença MIT. Veja `LICENSE` para mais informações.

## 👥 Equipe / Contato
- Desenvolvido por: [Seu Nome]
- Email: [seu.email@example.com]
- LinkedIn: [seu-linkedin]
