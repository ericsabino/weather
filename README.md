# Weather
Esta API faz integração com a Open Weather para buscar o clima por cidade


## Tecnologias

**Maven** - Para gerenciar dependência.

**Lombok** - para produtividade, ele fica responsável por gerar os Getter e Setter; Builder; Construtor, entre outros.

**MapStruct** - Para fazer o mapper entre meu DTO e minha Classe Modelo.

**Jasypt** - Para fazer a decriptografia do meu appId.

**Springboot** - Com configurações rápidas, você consegue, disponibilizar uma aplicação baseada no Spring. No caso disponibilizamos uma API.


##Arquitetura - Clean Arch

<img src="/img/Clean-Arch.jpg" alt="Clean Arch"/>


##Como Utilizar

Assim que clonar o projeto para sua máquina é preciso rodar o **mvn install** para que o **mapStruct** consiga gerar a implementação do mapper automaticamente.

Após isso basta executar a classe **WeatherApplication** 



##Collection Postman
<https://www.getpostman.com/collections/d25a19a3f95bd98a545e>

Se preferir, pode executar diretamente do browser (Sendo %20 um caracter de espaço)

<http://localhost:8080/weather/v1/city?name=São%20Paulo>