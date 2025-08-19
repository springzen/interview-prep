## How to view the diagram in the browser

1. Go to https://structurizr.com/dsl
2. Delete the current diagram
3. Copy the diagram code between the tick marks``` ``` 
4. Paste the diagram code in the editor/dsl window
5. Click the Render button

```structurizr
workspace {

    model {
        user = person "User"
        softwareSystem = softwareSystem "Software System - Spring Cloud/Spring Boot 3" {
            orgservice = container "Organization Service" {
                feignclient = component "Declarative REST Client"
                openapiclient = component "Declarative API Definition"
                zipkin = component "Distributed Tracing"
                eurekaconfig = component "Enable Netflix Eureka Discovery"
            }
            
            deptservice = container "Department Service" {
                feignclient2 = component "Declarative REST Client"
                openapiclient2 = component "Declarative API Definition"
                zipkin2 = component "Distributed Tracing"
                eurekaconfig2 = component "Enable Netflix Eureka Discovery"
            }
            
            empservice = container "Employee Service" {
                feignclient3 = component "Declarative REST Client"
                openapiclient3 = component "Declarative API Definition"
                zipkin3 = component "Distributed Tracing"
                eurekaconfig3 = component "Enable Netflix Eureka Discovery"
            }
            
            configservice = container "Spring Cloud Config" {
            }
            
            
            discoveryservice = container "Spring Cloud Netflix Eureka" {
            }
            
            
            gateway = container "API Gateway" {
                user -> this "Uses"
                discoveryclient = component "Spring Cloud Netflix Eureka Client"
                openapiclient4 = component "Declarative API Definition"
                zipkin4 = component "Distributed Tracing"
            }


            # relationships to/from components
            gateway -> orgservice "Route to Organization Service"
            gateway -> deptservice "Route to Department Service"
            gateway -> empservice "Route to Employee Service"
            gateway -> discoveryservice "Discover Services"
            orgservice ->  configservice "Resolve Configuration Remodely"
            deptservice ->  configservice "Resolve Configuration Remodely"
            empservice ->  configservice "Resolve Configuration Remodely"
        }
    }

    views {
        systemContext softwareSystem {
            include *
            autolayout tb
        }

        container softwareSystem {
            include *
            autolayout tb
        }

        component orgservice {
            include *
            autolayout tb
        }

        component deptservice {
            include *
            autolayout tb
        }

        component empservice {
            include *
            autolayout tb
        }

        component gateway {
            include *
            autolayout tb
        }

        styles {
            element "Database" {
                shape Cylinder
            }

            element "Web Application" {
                shape WebBrowser
            }
        }

        theme default
    }

}
```