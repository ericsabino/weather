# Documentação da API - Weather API

## 🌐 Informações Gerais

### Base URL
```
Local: http://localhost:8080
Produção: https://sua-api-weather.herokuapp.com (exemplo)
```

### Autenticação
Esta API não requer autenticação do cliente. A autenticação é feita internamente com a OpenWeather API usando a chave configurada no servidor.

### Formatos Suportados
- **Request**: Parâmetros via Query String
- **Response**: `application/json; charset=UTF-8`
- **Encoding**: UTF-8

## 📋 Endpoints Disponíveis

### Weather API - Consulta Climática

#### Buscar Clima por Cidade
**Endpoint:** `GET /weather/v1/city`
**Descrição:** Retorna informações climáticas atuais para uma cidade específica

##### Parâmetros
| Parâmetro | Tipo | Localização | Obrigatório | Descrição |
|-----------|------|-------------|-------------|-----------|
| `name` | string | query | ✅ | Nome da cidade (aceita acentos e espaços) |

##### Response Sucesso
**Status:** `200 OK`
```json
{
  "id": 3448439,
  "name": "São Paulo",
  "cod": 200,
  "base": "stations",
  "visibility": 10000,
  "dt": 1640995200,
  "timezone": -10800,
  "coord": {
    "lat": -23.5489,
    "lon": -46.6388
  },
  "weather": [
    {
      "id": 800,
      "main": "Clear",
      "description": "céu limpo",
      "icon": "01d"
    }
  ],
  "main": {
    "temp": 22.5,
    "feelsLike": 23.1,
    "tempMin": 20.0,
    "tempMax": 25.0,
    "pressure": 1013,
    "humidity": 65
  },
  "wind": {
    "speed": 3.1,
    "deg": 180
  },
  "clouds": {
    "all": 0
  },
  "sys": {
    "type": 1,
    "id": 8394,
    "country": "BR",
    "sunrise": 1640941200,
    "sunset": 1640988000
  }
}
```

##### Campos da Resposta

###### Objeto Principal
| Campo | Tipo | Descrição |
|-------|------|-----------|
| `id` | number | ID da cidade na OpenWeather |
| `name` | string | Nome da cidade |
| `cod` | number | Código de status interno |
| `base` | string | Fonte dos dados |
| `visibility` | number | Visibilidade em metros |
| `dt` | number | Timestamp dos dados (Unix) |
| `timezone` | number | Fuso horário em segundos UTC |

###### Coordenadas (`coord`)
| Campo | Tipo | Descrição |
|-------|------|-----------|
| `lat` | number | Latitude |
| `lon` | number | Longitude |

###### Condições Climáticas (`weather[]`)
| Campo | Tipo | Descrição |
|-------|------|-----------|
| `id` | number | ID da condição climática |
| `main` | string | Grupo principal (Rain, Snow, Clear, etc.) |
| `description` | string | Descrição em português |
| `icon` | string | Código do ícone |

###### Dados Principais (`main`)
| Campo | Tipo | Unidade | Descrição |
|-------|------|---------|-----------|
| `temp` | number | °C | Temperatura atual |
| `feelsLike` | number | °C | Sensação térmica |
| `tempMin` | number | °C | Temperatura mínima |
| `tempMax` | number | °C | Temperatura máxima |
| `pressure` | number | hPa | Pressão atmosférica |
| `humidity` | number | % | Umidade relativa |

###### Vento (`wind`)
| Campo | Tipo | Unidade | Descrição |
|-------|------|---------|-----------|
| `speed` | number | m/s | Velocidade do vento |
| `deg` | number | graus | Direção do vento |

###### Nuvens (`clouds`)
| Campo | Tipo | Unidade | Descrição |
|-------|------|---------|-----------|
| `all` | number | % | Cobertura de nuvens |

###### Sistema (`sys`)
| Campo | Tipo | Descrição |
|-------|------|-----------|
| `country` | string | Código do país (ISO 3166) |
| `sunrise` | number | Horário do nascer do sol (Unix timestamp) |
| `sunset` | number | Horário do pôr do sol (Unix timestamp) |

##### Possíveis Erros
- **400 Bad Request**: Parâmetro `name` não fornecido ou inválido
- **404 Not Found**: Cidade não encontrada
- **401 Unauthorized**: API key inválida ou expirada (erro interno)
- **429 Too Many Requests**: Limite de requisições excedido
- **500 Internal Server Error**: Erro interno do servidor

##### Exemplo de Erro
```json
{
  "timestamp": "2023-12-31T12:00:00.000+00:00",
  "status": 404,
  "error": "Not Found",
  "message": "Cidade não encontrada",
  "path": "/weather/v1/city"
}
```

##### Exemplos Completos

**Exemplo 1: Cidade Brasileira**
```bash
# Request
curl "http://localhost:8080/weather/v1/city?name=Rio%20de%20Janeiro"

# Response
{
  "id": 3451190,
  "name": "Rio de Janeiro",
  "main": {
    "temp": 28.5,
    "humidity": 70,
    "pressure": 1012
  },
  "weather": [{
    "main": "Rain",
    "description": "chuva leve"
  }],
  "wind": {
    "speed": 4.2
  }
}
```

**Exemplo 2: Cidade Internacional**
```bash
# Request
curl "http://localhost:8080/weather/v1/city?name=London"

# Response
{
  "id": 2643743,
  "name": "London",
  "main": {
    "temp": 8.5,
    "humidity": 85,
    "pressure": 1018
  },
  "weather": [{
    "main": "Clouds",
    "description": "nuvens dispersas"
  }],
  "sys": {
    "country": "GB"
  }
}
```

**Exemplo 3: Cidade com Acentos**
```bash
# Request
curl "http://localhost:8080/weather/v1/city?name=São%20Paulo"

# Response
{
  "id": 3448439,
  "name": "São Paulo",
  "main": {
    "temp": 22.0,
    "humidity": 60
  },
  "weather": [{
    "main": "Clear",
    "description": "céu limpo"
  }]
}
```

## 📊 Códigos de Status

| Código | Significado | Quando Usar |
|--------|-------------|-------------|
| 200 | OK | Dados climáticos retornados com sucesso |
| 400 | Bad Request | Parâmetro `name` ausente ou formato inválido |
| 404 | Not Found | Cidade não encontrada na base da OpenWeather |
| 429 | Too Many Requests | Limite de requisições da API excedido |
| 500 | Internal Server Error | Erro de configuração ou problema na OpenWeather API |

## 🧪 Testando a API

### Via cURL
```bash
# Teste básico
curl "http://localhost:8080/weather/v1/city?name=London"

# Com verbose para debug
curl -v "http://localhost:8080/weather/v1/city?name=Tokyo"

# Salvar resposta em arquivo
curl "http://localhost:8080/weather/v1/city?name=Paris" > weather_paris.json
```

### Via Postman
1. Importe a collection: https://www.getpostman.com/collections/d25a19a3f95bd98a545e
2. Configure a baseUrl como variável: `http://localhost:8080`
3. Execute as requisições de exemplo

### Via JavaScript (Fetch)
```javascript
fetch('http://localhost:8080/weather/v1/city?name=São Paulo')
  .then(response => response.json())
  .then(data => {
    console.log('Temperatura:', data.main.temp, '°C');
    console.log('Descrição:', data.weather[0].description);
  })
  .catch(error => console.error('Erro:', error));
```

### Via Python (requests)
```python
import requests

response = requests.get(
    'http://localhost:8080/weather/v1/city',
    params={'name': 'São Paulo'}
)

if response.status_code == 200:
    data = response.json()
    print(f"Temperatura: {data['main']['temp']}°C")
    print(f"Descrição: {data['weather'][0]['description']}")
else:
    print(f"Erro: {response.status_code}")
```

## 📝 Casos de Uso Comuns

### 1. Dashboard de Clima Múltiplas Cidades
```javascript
const cities = ['São Paulo', 'Rio de Janeiro', 'Brasília'];
const weatherPromises = cities.map(city => 
  fetch(`/weather/v1/city?name=${encodeURIComponent(city)}`)
    .then(r => r.json())
);

Promise.all(weatherPromises)
  .then(results => {
    // Exibir dados de todas as cidades
    results.forEach(weather => {
      console.log(`${weather.name}: ${weather.main.temp}°C`);
    });
  });
```

### 2. Alerta de Temperatura
```javascript
fetch('/weather/v1/city?name=Manaus')
  .then(response => response.json())
  .then(data => {
    if (data.main.temp > 35) {
      alert('Temperatura alta em Manaus!');
    }
  });
```

### 3. Comparação de Cidades
```bash
# Script bash para comparar temperaturas
cities=("London" "Paris" "Berlin")

for city in "${cities[@]}"; do
  temp=$(curl -s "http://localhost:8080/weather/v1/city?name=$city" | jq '.main.temp')
  echo "$city: ${temp}°C"
done
```

## 🔒 Limitações e Considerações

### Rate Limiting
- A API usa o plano gratuito da OpenWeather (1000 calls/day)
- Não há rate limiting implementado na aplicação
- Em produção, considere implementar cache para reduzir chamadas

### Performance
- Timeout padrão: 30 segundos para chamadas à OpenWeather
- Resposta típica: 200-500ms dependendo da localização
- Cache não implementado (cada request faz nova chamada)

### Precisão dos Dados
- Dados atualizados a cada 10 minutos pela OpenWeather
- Precisão da localização depende da base da OpenWeather
- Cidades pequenas podem não ser encontradas

## 📞 Suporte

- **Issues no GitHub**: Para bugs e melhorias
- **Email**: [seu.email@example.com]
- **Documentação**: Veja outros arquivos da documentação
