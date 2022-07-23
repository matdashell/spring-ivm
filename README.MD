## IVM

## Propósito Principal:

Invocar métodos através de requisições via REST

## Iniciando

1. Executar o comando `mvn clean install` na lib atual para que a mesma vá para o `m2`.
2. Adicionar a dependência no `pom.xml` do projeto.
    ```
        <dependency>
            <groupId>inspect.invoke</groupId>
            <artifactId>ivm-controller</artifactId>
            <version>1.0.0</version>
        </dependency>
    ```
3. Após adicionado, executar o comando `mvn clean install`.

## Utilização:

Com a api no ar, realize uma requisição GET no endereço *localhost: {port} / invoke* passando os seguintes dados no
corpo da requisição:

    {
        "classLocation": * package completo da classe a executar *,
        "methodName": * nome do método *,
        "args": [
            {
                "classLocation": * package completo do objeto *,
                "arg": * json que irá compor o objeto *
            }
        ]
    }

## Exemplos:

1. Service:
    ```
    package com.example.orch.services;
   
    @Component
    public class MyService {
    
        @Autowired
        private ProducerTemplate producerTemplate;
    
        public void start(String message) {
            producerTemplate.sendBody(START_ROUTE, message);
        }
    
        public void processarCompra(Compra compra) {
            producerTemplate.sendBody(PROCESSAR_COMPRA_ROUTE, compra);
        }
   
        public void processarCompras(List<Compra> compras) {
            producerTemplate.sendBody(PROCESSAR_COMPRA_ROUTE, compras);
        }
   
        public void settarValor(Compra compra, double valor) {
            compra.setValor(valor)
        }
    }
   ```
   
2. Objeto Compra:

    ```
    package com.example.orch.domain;
   
    public class Compra implements Serializable {
    
        private Integer idTransacao;
        private String nomeProduto;
        private Double valor;
        private Double quantidade;
        private Double valorTotal;
        private Double valorDesconto = BigDecimal.ZERO;
    
    }
   ```    
   
3. Requisição necessária para executar o método *processarCompra* :

```
    {
        "classLocation": "com.example.orch.services.MyService",
        "methodName": "processarCompra",
        "args": [
            {
                "classLocation": "com.example.orch.domain.Compra",
                "arg": {idTransacao:0 ,nomeProduto:'nome', valor:'0.0', quantidade:10.0}
            }
        ]
    }
```

4. Requisição necessária para executar o método *processarCompras* :

```
    {
        "classLocation": "com.example.orch.services.MyService",
        "methodName": "processarCompras",
        "args": [
            {
                "classLocation": "com.example.orch.domain.Compra",
                "arg": {idTransacao:0 ,nomeProduto:'nome', valor:'0.0', quantidade:10.0}
            },
            {
                "classLocation": "com.example.orch.domain.Compra",
                "arg": {idTransacao:0 ,nomeProduto:'nome', valor:'0.0', quantidade:10.0}
            },
            {
                "classLocation": "com.example.orch.domain.Compra",
                "arg": {idTransacao:0 ,nomeProduto:'nome', valor:'0.0', quantidade:10.0}
            }
        ]
    }
```

5. Requisição necessária para executar o método *start* :

```
    {
        "classLocation": "com.example.orch.services.MyService",
        "methodName": "start",
        "args": [
            {
                "classLocation": "java.lang.String",
                "arg": "route"
            }
        ]
    }
```

6. Requisição necessária para executar o método *settarValor* :

```
    {
        "classLocation": "com.example.orch.services.MyService",
        "methodName": "start",
        "args": [
            {
                "classLocation": "com.example.orch.domain.Compra",
                "arg": {idTransacao:0 ,nomeProduto:'nome', valor:'0.0', quantidade:10.0}
            },
            {
                "classLocation": "java.lang.Double",
                "arg": 9999999999
            }
        ]
    }
```
